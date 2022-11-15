package MojoNoid.Agents;

import MojoNoid.Models.Ball;
import MojoNoid.Models.Board;
import MojoNoid.Models.Cells.*;
import MojoNoid.Models.GameState;
import MojoNoid.Models.Paddle;
import MojoNoid.Models.Prizes.Prize;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataAgent {
    private final File savedGames, scoreBoardFile;

    public DataAgent(){
        savedGames = new File("resources/saved_games");
        scoreBoardFile = new File("resources/scoreBoard.txt");
    }

    public void saveGame(String name, GameState gameState){
        try{
            int type;
            FileWriter saveFile = new FileWriter(savedGames.getPath()+"/"+name+".txt");

            saveFile.append(String.valueOf(gameState.getLives())).append(" ")
                    .append(String.valueOf(gameState.getScore())).append("\n");

            saveFile.append(String.valueOf(gameState.getActivePrizes().size())).append("\n");
            for(Map.Entry<Integer,Integer> m: gameState.getActivePrizes().entrySet())
                saveFile.append(String.valueOf(m.getKey())).append(" ")
                        .append(String.valueOf(m.getValue())).append("\n");

            saveFile.append(String.valueOf(gameState.getBoard().getCells().size())).append("\n");
            for(Cell cell: gameState.getBoard().getCells()){
                type = cell.getType();
                saveFile.append(String.valueOf(type)).append(" ")
                        .append(String.valueOf(cell.getX())).append(" ")
                        .append(String.valueOf(cell.getY()));
                if(type==2)
                    saveFile.append(" ").append(String.valueOf(((WoodenCell) cell).getHealth()));
                else if(type==3)
                    saveFile.append(" ").append(String.valueOf(((BlinkingCell) cell).isVisible()))
                            .append(" ").append(String.valueOf(((BlinkingCell) cell).getToggleTime()));
                saveFile.append("\n");
            }

            saveFile.append(String.valueOf(gameState.getBoard().getBalls().size())).append("\n");
            for(Ball ball: gameState.getBoard().getBalls()){
                saveFile.append(String.valueOf(ball.getX())).append(" ")
                        .append(String.valueOf(ball.getY())).append(" ")
                        .append(String.valueOf(ball.getXVelocity())).append(" ")
                        .append(String.valueOf(ball.getYVelocity())).append(" ")
                        .append(String.valueOf(ball.getSpeed())).append(" ")
                        .append(String.valueOf(ball.isFireBall())).append("\n");
            }

            saveFile.append(String.valueOf(gameState.getBoard().getPrizes().size())).append("\n");
            for(Prize prize: gameState.getBoard().getPrizes()){
                saveFile.append(String.valueOf(prize.getType())).append(" ")
                        .append(String.valueOf(prize.getX())).append(" ")
                        .append(String.valueOf(prize.getY())).append("\n");
            }

            Paddle paddle = gameState.getBoard().getPaddle();
            saveFile.append(String.valueOf(paddle.getX())).append(" ")
                    .append(String.valueOf(paddle.getY())).append(" ")
                    .append(String.valueOf(paddle.getWidth())).append(" ")
                    .append(String.valueOf(paddle.getSpeed())).append("\n");

            saveFile.append(String.valueOf(gameState.getBoard().getInitialGenerationTime())).append(" ")
                    .append(String.valueOf(gameState.getBoard().getGenerationTime()));

            saveFile.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public GameState loadGame(String name) throws Exception {
        File saveFile = new File(savedGames.getPath()+"/"+name+".txt");
        GameState gameState = null;
        if(saveFile.exists()){
            Scanner scanner = new Scanner(saveFile);
            int count,type,lives,score,x,y,initialGenerationTime,generationTime;
            LinkedList<Cell> cells = new LinkedList<>();
            LinkedList<Ball> balls = new LinkedList<>();
            LinkedList<Prize> prizes = new LinkedList<>();
            HashMap<Integer,Integer> activePrizes = new HashMap<>();
            Paddle paddle;

            lives = scanner.nextInt();
            score = scanner.nextInt();

            count = scanner.nextInt();
            for(int i=0;i<count;i++)
                activePrizes.put(scanner.nextInt(), scanner.nextInt());

            count = scanner.nextInt();
            for(int i=0;i<count;i++){
                type = scanner.nextInt(); x = scanner.nextInt(); y = scanner.nextInt();
                switch(type){
                    case 1-> cells.add(new GlassCell(x,y));
                    case 2-> cells.add(new WoodenCell(x,y,scanner.nextInt()));
                    case 3-> cells.add(new BlinkingCell(x,y,scanner.nextBoolean(),scanner.nextInt()));
                    case 4-> cells.add(new InvisibleCell(x,y));
                    case 5-> cells.add(new PrizeCell(x,y));
                }
            }

            count = scanner.nextInt();
            for(int i=0;i<count;i++){
                balls.add(new Ball(scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextBoolean()));
            }

            count = scanner.nextInt();
            for(int i=0;i<count;i++){
                prizes.add(new Prize(scanner.nextInt(),scanner.nextInt(),scanner.nextInt()));
            }

            paddle = new Paddle(scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt());

            initialGenerationTime = scanner.nextInt();
            generationTime = scanner.nextInt();

            gameState = new GameState(new Board(cells,balls,prizes,paddle,initialGenerationTime,generationTime),lives,score,activePrizes);
            scanner.close();
        }
        return gameState;
    }

    public void saveScoreBoard(String name, int score){
        try{
            LinkedHashMap<String,Integer> scoreBoard = loadScoreBoard();
            LinkedHashMap<String,Integer> sortedScoreBoard = new LinkedHashMap<>();
            if(!scoreBoard.containsKey(name) || score>scoreBoard.get(name)){
                scoreBoard.put(name,score);
                LinkedList<Integer> values = new LinkedList<>(scoreBoard.values());
                values.sort(Collections.reverseOrder());
                Iterator<Integer> valueIt = values.iterator();
                while(valueIt.hasNext()){
                    score = valueIt.next();
                    Iterator<String> keyIt = new LinkedList<>(scoreBoard.keySet()).iterator();
                    while(keyIt.hasNext()){
                        name = keyIt.next();
                        int val2 = scoreBoard.get(name);
                        if(score==val2){
                            scoreBoard.remove(name);
                            sortedScoreBoard.put(name,score);
                            break;
                        }
                    }
                }
                FileWriter saveFile = new FileWriter(scoreBoardFile);
                saveFile.append(String.valueOf(sortedScoreBoard.size())).append("\n");
                for(Map.Entry<String,Integer> m: sortedScoreBoard.entrySet())
                    saveFile.append(String.valueOf(m.getValue())).append(" ").append(m.getKey()).append("\n");
                saveFile.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public LinkedHashMap<String,Integer> loadScoreBoard(){
        LinkedHashMap<String,Integer> scoreBoard = new LinkedHashMap<>();
        try{
            if(!scoreBoardFile.createNewFile()){
                Scanner scanner = new Scanner(scoreBoardFile);
                int count = scanner.nextInt(),score;
                if(count>0){
                    for(int i=0;i<count;i++){
                        score = scanner.nextInt();
                        scoreBoard.put(scanner.nextLine().substring(1),score);
                    }
                }
                scanner.close();
            }
            else{
                FileWriter saveFile = new FileWriter(scoreBoardFile);
                saveFile.append("0");
                saveFile.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return scoreBoard;
    }
}
