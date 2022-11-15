package MojoNoid.Models;

import MojoNoid.Models.Cells.Cell;
import MojoNoid.Models.Prizes.Prize;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class GameState {
    private Board board;
    private int lives,score;
    private HashMap<Integer,Integer> activePrizes;

    public GameState(Board board, int lives, int score, HashMap<Integer, Integer> activePrizes){
        this.board = board;
        this.lives = lives;
        this.score = score;
        this.activePrizes = activePrizes;
    }
    public GameState(){
        this.board = new Board();
        this.lives=3;
        this.score=0;
        this.activePrizes = new HashMap<>();
    }

    public Board getBoard() {
        return board;
    }
    public int getLives() {
        return lives;
    }
    public int getScore() {
        return score;
    }
    public HashMap<Integer, Integer> getActivePrizes() {
        return activePrizes;
    }

    public void moveRequest(int direction){
        switch(direction){
            case 1-> board.getPaddle().setX(board.getPaddle().getX()+board.getPaddle().getSpeed());
            case -1-> board.getPaddle().setX(board.getPaddle().getX()-board.getPaddle().getSpeed());
        }
        if(board.getPaddle().getX()<0)
            board.getPaddle().setX(0);
        else if(board.getPaddle().getX()+board.getPaddle().getWidth()>870)
            board.getPaddle().setX(870-board.getPaddle().getWidth());
    }
    public void update(){
        board.updateGenerationTime();
        board.updateBlinkingCells();
        board.moveBalls();
        board.movePrizes();
        score += 5*board.checkBallAndCellInteraction();
        lives += board.checkBallAndWallAndPaddleInteraction();
        HashMap<Integer,Integer> updatedActivePrizes = new HashMap<>();
        for(Map.Entry<Integer,Integer> m: activePrizes.entrySet()){
            if(m.getValue()==1) disablePrize(m.getKey());
            else updatedActivePrizes.put(m.getKey(), m.getValue()-1);
        }
        activePrizes = updatedActivePrizes;
        LinkedList<Prize> caughtPrizes = board.CheckPrizeAndWallAndPaddleInteraction();
        for(Prize prize: caughtPrizes){
            enablePrize(prize.getType());
        }
    }
    public boolean gameHasEnded(){
        if(lives==0) return true;
        for(Cell cell: board.getCells()){
            if(cell.getY()>600) return true;
        }
        return false;
    }

    private void enablePrize(int type){
        switch(type){
            case 1->{
                for(Ball ball: board.getBalls()) ball.setFireBall(true);
                activePrizes.put(1,400);
            }
            case 2-> board.addNewBalls();
            case 3->{
                board.getPaddle().setWidth(175);
                if(activePrizes.containsKey(4)){
                    board.getPaddle().setX(board.getPaddle().getX()-50);
                    activePrizes.remove(4);
                }
                else if(!activePrizes.containsKey(3)){
                    board.getPaddle().setX(board.getPaddle().getX()-25);
                }
                if(board.getPaddle().getX()<0)
                    board.getPaddle().setX(0);
                else if(board.getPaddle().getX()+board.getPaddle().getWidth()>870)
                    board.getPaddle().setX(870-board.getPaddle().getWidth());
                activePrizes.put(3,400);
            }
            case 4->{
                board.getPaddle().setWidth(75);
                if(activePrizes.containsKey(3)){
                    board.getPaddle().setX(board.getPaddle().getX()+50);
                    activePrizes.remove(3);
                }
                else if(!activePrizes.containsKey(4)){
                    board.getPaddle().setX(board.getPaddle().getX()+25);
                }
                activePrizes.put(4,400);
            }
            case 5->{
                for(Ball ball: board.getBalls()) ball.setSpeed(1);
                activePrizes.put(5,400);
                activePrizes.remove(6);
            }
            case 6->{
                for(Ball ball: board.getBalls()) ball.setSpeed(-1);
                activePrizes.put(6,400);
                activePrizes.remove(5);
            }
            case 7->{
                board.getPaddle().setSpeed(-1*Math.abs(board.getPaddle().getSpeed()));
                activePrizes.put(7,400);
            }
            case 8-> enablePrize(new Random().nextInt(7)+1);
        }
    }
    private void disablePrize(int type){
        switch(type){
            case 1->{
                for(Ball ball: board.getBalls()) ball.setFireBall(false);
            }
            case 3->{
                board.getPaddle().setWidth(125);
                board.getPaddle().setX(board.getPaddle().getX()+25);
            }
            case 4->{
                board.getPaddle().setWidth(125);
                board.getPaddle().setX(board.getPaddle().getX()-25);
                if(board.getPaddle().getX()<0)
                    board.getPaddle().setX(0);
                else if(board.getPaddle().getX()+board.getPaddle().getWidth()>870)
                    board.getPaddle().setX(870-board.getPaddle().getWidth());
            }
            case 5,6->{
                for(Ball ball: board.getBalls()) ball.setSpeed(0);
            }
            case 7-> board.getPaddle().setSpeed(Math.abs(board.getPaddle().getSpeed()));
        }
    }
}
