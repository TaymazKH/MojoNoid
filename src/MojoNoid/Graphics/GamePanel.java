package MojoNoid.Graphics;

import MojoNoid.Models.Ball;
import MojoNoid.Models.Cells.BlinkingCell;
import MojoNoid.Models.Cells.Cell;
import MojoNoid.Models.Cells.WoodenCell;
import MojoNoid.Models.GameState;
import MojoNoid.Models.Paddle;
import MojoNoid.Models.Prizes.Prize;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    private GameState gameState;

    public GamePanel(){
        setLayout(null);
        setBounds(0,0,870,700);
        setBackground(Color.DARK_GRAY);
    }

    public void update(GameState gameState){
        this.gameState = gameState;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
//        Random random = new Random();
//        setBackground(new Color(random.nextInt(206)+50,random.nextInt(206)+50,random.nextInt(206)+50));
        for(Cell cell: gameState.getBoard().getCells()){
            switch(cell.getType()){
                case 1-> g2.setPaint(Color.CYAN);
                case 2->{
                    if(((WoodenCell) cell).getHealth()==2) g2.setPaint(new Color(165,133,78));
                    else g2.setPaint(new Color(87,65,47));
                }
                case 3->{
                    if(cell.getY()>480) g2.setPaint(Color.RED);
                    else{
                        if(((BlinkingCell) cell).isVisible()) g2.setPaint(Color.PINK);
                        else g2.setPaint(Color.DARK_GRAY);
                    }
                }
                case 4->{
                    if(cell.getY()>480) g2.setPaint(Color.RED);
                    else g2.setPaint(Color.DARK_GRAY);
                }
                case 5-> g2.setPaint(Color.YELLOW);
            }
            g2.fillRect(cell.getX(), cell.getY(), Cell.WIDTH, Cell.HEIGHT);
        }
        for(Ball ball: gameState.getBoard().getBalls()){
            if(ball.isFireBall()) g2.setPaint(Color.RED);
            else g2.setPaint(Color.ORANGE);
            g2.fillOval(ball.getX(), ball.getY(), Ball.WIDTH, Ball.HEIGHT);
        }
        for(Prize prize: gameState.getBoard().getPrizes()){
            switch(prize.getType()){
                case 1-> g2.drawImage(new ImageIcon("resources/images/fire.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                case 2-> g2.drawImage(new ImageIcon("resources/images/multi.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                case 3-> g2.drawImage(new ImageIcon("resources/images/big.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                case 4-> g2.drawImage(new ImageIcon("resources/images/small.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                case 5-> g2.drawImage(new ImageIcon("resources/images/fast.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                case 6-> g2.drawImage(new ImageIcon("resources/images/slow.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                case 7-> g2.drawImage(new ImageIcon("resources/images/dizzy.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                case 8->{
                    switch(new Random().nextInt(7)+1){
                        case 1-> g2.drawImage(new ImageIcon("resources/images/fire.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                        case 2-> g2.drawImage(new ImageIcon("resources/images/multi.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                        case 3-> g2.drawImage(new ImageIcon("resources/images/big.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                        case 4-> g2.drawImage(new ImageIcon("resources/images/small.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                        case 5-> g2.drawImage(new ImageIcon("resources/images/fast.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                        case 6-> g2.drawImage(new ImageIcon("resources/images/slow.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                        case 7-> g2.drawImage(new ImageIcon("resources/images/dizzy.png").getImage(),prize.getX(),prize.getY(),Prize.WIDTH,Prize.HEIGHT,null);
                    }
                }
            }
        }
        g2.setPaint(new Color(90,39,41));
        Paddle paddle = gameState.getBoard().getPaddle();
        g2.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), Paddle.HEIGHT);
    }
}
