package MojoNoid.Graphics;

import MojoNoid.Models.GameState;
import MojoNoid.Models.Prizes.Prize;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ScorePanel extends JPanel {
    private GameState gameState;
    JLabel scoreLabel, heartsLabel, activePrizesLabel;

    public ScorePanel(){
        heartsLabel = new JLabel();
        heartsLabel.setBounds(25,0,200,50);
        heartsLabel.setFont(new Font("",Font.BOLD,20));

        scoreLabel = new JLabel();
        scoreLabel.setBounds(225,0,200,50);
        scoreLabel.setFont(new Font("",Font.BOLD,20));

        activePrizesLabel = new JLabel();
        activePrizesLabel.setBounds(425,0,200,50);
        activePrizesLabel.setFont(new Font("",Font.BOLD,20));

        setLayout(null);
        setBounds(0,700,870,50);
        setBackground(Color.ORANGE);
        add(heartsLabel);
        add(scoreLabel);
        add(activePrizesLabel);
    }

    public void update(GameState gameState){
        this.gameState = gameState;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        switch(gameState.getLives()){
            case 0-> heartsLabel.setText("LIVES: X");
            case 1-> heartsLabel.setText("LIVES: ♥");
            case 2-> heartsLabel.setText("LIVES: ♥♥");
            case 3-> heartsLabel.setText("LIVES: ♥♥♥");
        }
        scoreLabel.setText("SCORE: "+gameState.getScore());
        int count=0;
        for(Map.Entry<Integer,Integer> m: gameState.getActivePrizes().entrySet()){
            switch(m.getKey()){
                case 1-> g2.drawImage(new ImageIcon("resources/images/fire.png").getImage(),610+35*count,10,Prize.WIDTH,Prize.HEIGHT,null);
                case 3-> g2.drawImage(new ImageIcon("resources/images/big.png").getImage(),610+35*count,10,Prize.WIDTH,Prize.HEIGHT,null);
                case 4-> g2.drawImage(new ImageIcon("resources/images/small.png").getImage(),610+35*count,10,Prize.WIDTH,Prize.HEIGHT,null);
                case 5-> g2.drawImage(new ImageIcon("resources/images/fast.png").getImage(),610+35*count,10,Prize.WIDTH,Prize.HEIGHT,null);
                case 6-> g2.drawImage(new ImageIcon("resources/images/slow.png").getImage(),610+35*count,10,Prize.WIDTH,Prize.HEIGHT,null);
                case 7-> g2.drawImage(new ImageIcon("resources/images/dizzy.png").getImage(),610+35*count,10,Prize.WIDTH,Prize.HEIGHT,null);
            }
            count++;
        }
        if(count>0) activePrizesLabel.setText("ACTIVE EFFECTS: ");
        else activePrizesLabel.setText("");
    }
}
