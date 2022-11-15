package MojoNoid.Models;

import MojoNoid.Models.Cells.*;
import MojoNoid.Models.Prizes.Prize;

import java.util.LinkedList;
import java.util.Random;

public class Board {
    private LinkedList<Cell> cells;
    private LinkedList<Ball> balls;
    private LinkedList<Prize> prizes;
    private Paddle paddle;
    private int initialGenerationTime,generationTime; // ( 0.05 seconds )

    public Board(LinkedList<Cell> cells, LinkedList<Ball> balls, LinkedList<Prize> prizes, Paddle paddle, int initialGenerationTime, int generationTime) {
        this.cells = cells;
        this.balls = balls;
        this.prizes = prizes;
        this.paddle = paddle;
        this.initialGenerationTime = initialGenerationTime;
        this.generationTime = generationTime;
    }
    public Board(){
        cells = new LinkedList<>();
        balls = new LinkedList<>();
        prizes = new LinkedList<>();
        for(int i=0;i<3;i++) generateNewRow();
        balls.add(new Ball());
        paddle = new Paddle();
        initialGenerationTime=400;
        generationTime=400;
    }

    public LinkedList<Cell> getCells() {
        return cells;
    }
    public LinkedList<Ball> getBalls() {
        return balls;
    }
    public LinkedList<Prize> getPrizes() {
        return prizes;
    }
    public Paddle getPaddle() {
        return paddle;
    }
    public int getInitialGenerationTime() {
        return initialGenerationTime;
    }
    public int getGenerationTime() {
        return generationTime;
    }

    public void setCells(LinkedList<Cell> cells) {
        this.cells = cells;
    }
    public void setBalls(LinkedList<Ball> balls) {
        this.balls = balls;
    }
    public void setPrizes(LinkedList<Prize> prizes) {
        this.prizes = prizes;
    }
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
    public void setInitialGenerationTime(int initialGenerationTime) {
        this.initialGenerationTime = initialGenerationTime;
    }
    public void setGenerationTime(int generationTime) {
        this.generationTime = generationTime;
    }

    private void generateNewRow(){
        for(Cell c: cells) c.setY(c.getY()+Cell.HEIGHT+45);
        Random random = new Random();
        boolean thereIsPrizeCell = false;
        int type;
        for(int i=0;i<8;i++){
            do{
                type = random.nextInt(5)+1;
            } while(thereIsPrizeCell && type==5);
            if(type==5) thereIsPrizeCell = true;
            switch(type){
                case 1-> cells.add(new GlassCell((Cell.WIDTH+30)*i+30,45));
                case 2-> cells.add(new WoodenCell((Cell.WIDTH+30)*i+30,45));
                case 3-> cells.add(new BlinkingCell((Cell.WIDTH+30)*i+30,45));
                case 4-> cells.add(new InvisibleCell((Cell.WIDTH+30)*i+30,45));
                case 5-> cells.add(new PrizeCell((Cell.WIDTH+30)*i+30,45));
            }
        }
    }
    public void addNewBalls(){
        if(balls.size()<5){
            balls.add(new Ball(paddle.getX()+(paddle.getWidth()/2),paddle.getY()-Ball.HEIGHT));
            if(balls.size()<5){
                balls.add(new Ball(paddle.getX()+(paddle.getWidth()/2)-(Ball.WIDTH), paddle.getY()-Ball.HEIGHT,
                        -1*Ball.DEFAULT_X_VELOCITIES[3], -1*Ball.DEFAULT_Y_VELOCITIES[3],0 , false));
            }
        }
    }

    public void updateGenerationTime(){
        generationTime--;
        if(generationTime==0){
            if(initialGenerationTime>100) initialGenerationTime-=2;
            generateNewRow();
            generationTime = initialGenerationTime;
        }
    }

    public void moveBalls(){
        for(Ball ball: balls){
            switch(ball.getSpeed()){
                case -1->{
                    ball.setX(ball.getX()+(int)(0.8*(ball.getXVelocity())));
                    ball.setY(ball.getY()+(int)(0.8*(ball.getYVelocity())));
                }
                case 0->{
                    ball.setX(ball.getX()+ball.getXVelocity());
                    ball.setY(ball.getY()+ball.getYVelocity());
                }
                case 1->{
                    ball.setX(ball.getX()+(int)(1.4*(ball.getXVelocity())));
                    ball.setY(ball.getY()+(int)(1.4*(ball.getYVelocity())));
                }
            }
        }
    }
    public void movePrizes(){
        for(Prize prize: prizes){
            prize.setY(prize.getY()+Prize.SPEED);
        }
    }
    public void updateBlinkingCells(){
        for(Cell cell: cells){
            if(cell.getType()==3){
                ((BlinkingCell) cell).updateTime();
            }
        }
    }
    public int checkBallAndCellInteraction(){
        LinkedList<Cell> hitCells;
        boolean hit;
        int score=0;
        for(Ball ball: balls){
            hitCells = new LinkedList<>();
            hit = false;
            for(Cell cell: cells){
                if(ball.getX()>=cell.getX() && ball.getX()<=cell.getX()+Cell.WIDTH && ball.getY()>=cell.getY() && ball.getY()<=cell.getY()+Cell.HEIGHT) hitCells.add(cell);
                else if(ball.getX()+Ball.WIDTH>=cell.getX() && ball.getX()+Ball.WIDTH<=cell.getX()+Cell.WIDTH && ball.getY()>=cell.getY() && ball.getY()<=cell.getY()+Cell.HEIGHT) hitCells.add(cell);
                else if(ball.getX()>=cell.getX() && ball.getX()<=cell.getX()+Cell.WIDTH && ball.getY()+Ball.HEIGHT>=cell.getY() && ball.getY()+Ball.HEIGHT<=cell.getY()+Cell.HEIGHT) hitCells.add(cell);
                else if(ball.getX()+Ball.WIDTH>=cell.getX() && ball.getX()+Ball.WIDTH<=cell.getX()+Cell.WIDTH && ball.getY()+Ball.HEIGHT>=cell.getY() && ball.getY()+Ball.HEIGHT<=cell.getY()+Cell.HEIGHT) hitCells.add(cell);
            }
            for(Cell cell: hitCells){
                switch(cell.getType()){
                    case 1,4->{
                        hit = true;
                        cells.remove(cell);
                        score++;
                    }
                    case 2->{
                        hit = true;
                        ((WoodenCell) cell).setHealth(((WoodenCell) cell).getHealth()-1);
                        if(((WoodenCell) cell).getHealth()==0){
                            cells.remove(cell);
                        }
                        else if(ball.isFireBall()){
                            cells.remove(cell);
                            score++;
                        }
                        score++;
                    }
                    case 3->{
                        if(((BlinkingCell) cell).isVisible()){
                            hit = true;
                            cells.remove(cell);
                            score++;
                        }
                    }
                    case 5->{
                        hit = true;
                        cells.remove(cell);
                        score++;
                        prizes.add(new Prize(new Random().nextInt(8)+1, cell.getX()+Cell.WIDTH/2,cell.getY()));
                    }
                }
            }
            if(hit && !ball.isFireBall()) ball.setYVelocity(-1*ball.getYVelocity());
        }
        return score;
    }
    public int checkBallAndWallAndPaddleInteraction(){
        LinkedList<Ball> deadBalls = new LinkedList<>();
        int hearts=0;
        for(Ball ball: balls){
            if(ball.getX()<=0) ball.setXVelocity(Math.abs(ball.getXVelocity()));
            else if(ball.getX()+Ball.WIDTH>=870) ball.setXVelocity(-1*Math.abs(ball.getXVelocity()));
            if(ball.getY()<=0) ball.setYVelocity(Math.abs(ball.getYVelocity()));
            else if((ball.getX()>=paddle.getX() && ball.getX()<=paddle.getX()+paddle.getWidth()
                    && ball.getY()+Ball.HEIGHT>=paddle.getY() && ball.getY()+Ball.HEIGHT<=paddle.getY()+Paddle.HEIGHT)
                    || (ball.getX()+Ball.WIDTH>=paddle.getX() && ball.getX()+Ball.WIDTH<=paddle.getX()+paddle.getWidth()
                    && ball.getY()+Ball.HEIGHT>=paddle.getY() && ball.getY()+Ball.HEIGHT<=paddle.getY()+Paddle.HEIGHT)){
                int oneThirteenthOfPaddleLength = paddle.getWidth()/13;
                int ballCenterX = ball.getX()+Ball.WIDTH/2;
                int section = (ballCenterX-paddle.getX())/oneThirteenthOfPaddleLength; // 0-12
                if(ballCenterX<paddle.getX()){
                    ball.setXVelocity(-1*Ball.DEFAULT_X_VELOCITIES[0]);
                    ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[0]);
                }
                else if(ballCenterX>=paddle.getX()+paddle.getWidth()){
                    ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[0]);
                    ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[0]);
                }
                else{
                    switch(section){
                        case 0->{
                            ball.setXVelocity(-1*Ball.DEFAULT_X_VELOCITIES[0]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[0]);
                        }
                        case 1->{
                            ball.setXVelocity(-1*Ball.DEFAULT_X_VELOCITIES[1]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[1]);
                        }
                        case 2->{
                            ball.setXVelocity(-1*Ball.DEFAULT_X_VELOCITIES[2]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[2]);
                        }
                        case 3->{
                            ball.setXVelocity(-1*Ball.DEFAULT_X_VELOCITIES[3]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[3]);
                        }
                        case 4->{
                            ball.setXVelocity(-1*Ball.DEFAULT_X_VELOCITIES[4]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[4]);
                        }
                        case 5->{
                            ball.setXVelocity(-1*Ball.DEFAULT_X_VELOCITIES[5]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[5]);
                        }
                        case 6->{
                            ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[6]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[6]);
                        }
                        case 7->{
                            ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[5]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[5]);
                        }
                        case 8->{
                            ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[4]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[4]);
                        }
                        case 9->{
                            ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[3]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[3]);
                        }
                        case 10->{
                            ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[2]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[2]);
                        }
                        case 11->{
                            ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[1]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[1]);
                        }
                        case 12->{
                            ball.setXVelocity(Ball.DEFAULT_X_VELOCITIES[0]);
                            ball.setYVelocity(-1*Ball.DEFAULT_Y_VELOCITIES[0]);
                        }
                    }
                }
            }
            else if(ball.getY()+Ball.HEIGHT>=700) deadBalls.add(ball);
        }
        for(Ball ball: deadBalls) balls.remove(ball);
        if(balls.isEmpty()){
            balls.add(new Ball(paddle.getX()+(paddle.getWidth()/2)-(Ball.WIDTH/2),paddle.getY()-Ball.HEIGHT));
            hearts=-1;
        }
        return hearts;
    }
    public LinkedList<Prize> CheckPrizeAndWallAndPaddleInteraction(){
        LinkedList<Prize> caughtPrizes = new LinkedList<>(), lostPrizes = new LinkedList<>();
        for(Prize prize: prizes){
            if((prize.getX()>=paddle.getX() && prize.getX()<=paddle.getX()+paddle.getWidth() && prize.getY()+Prize.HEIGHT>=paddle.getY() && prize.getY()+Prize.HEIGHT<=paddle.getY()+Paddle.HEIGHT)
                    || (prize.getX()+Prize.WIDTH>=paddle.getX() && prize.getX()+Prize.WIDTH<=paddle.getX()+paddle.getWidth() && prize.getY()+Prize.HEIGHT>=paddle.getY() && prize.getY()+Prize.HEIGHT<=paddle.getY()+Paddle.HEIGHT))
                caughtPrizes.add(prize);
            else if(prize.getY()+Prize.HEIGHT>=700) lostPrizes.add(prize);
        }
        for(Prize prize: caughtPrizes) prizes.remove(prize);
        for(Prize prize: lostPrizes) prizes.remove(prize);
        return caughtPrizes;
    }
}
