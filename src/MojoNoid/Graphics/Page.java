package MojoNoid.Graphics;

import MojoNoid.Models.GameState;
import MojoNoid.Tools.Listeners.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

public class Page extends JFrame {
    JPanel mainMenuPanel, pauseMenuPanel, scoreBoardPanel;
    GamePanel gamePanel;
    ScorePanel scorePanel;
    JButton newGameButton, loadButton, scoreBoardButton, exitButton, backButton;
    JButton continueButton, restartButton, saveButton, quitButton;
    JSlider slider;
    JLabel[] nameLabel;
    LinkedHashMap<String,Integer> scoreBoard;
    int index;

    public Page(){
        setTitle("MojoNoid");
        setIconImage(new ImageIcon("resources/images/fire.png").getImage());
        setLayout(null);
        setResizable(false);
        initializeMainMenuPage();
        initializePauseMenuPage();
        initializeScoreBoardPage();
        initializeGamePage();
        switchToMainMenuPage();
        addKeyListener(new GamePanelKeyListener());
        setVisible(true);
    }

    private void initializeMainMenuPage(){
        newGameButton = new JButton("NEW GAME");
        newGameButton.setBounds(50,50,300,75);
        newGameButton.setFont(new Font("",Font.BOLD,20));
        newGameButton.setFocusable(false);
        newGameButton.addActionListener(new NewGameButtonListener());

        loadButton = new JButton("LOAD A GAME");
        loadButton.setBounds(50,150,300,75);
        loadButton.setFont(new Font("",Font.BOLD,20));
        loadButton.setFocusable(false);
        loadButton.addActionListener(new LoadButtonListener());

        scoreBoardButton = new JButton("SCORE BOARD");
        scoreBoardButton.setBounds(50,250,300,75);
        scoreBoardButton.setFont(new Font("",Font.BOLD,20));
        scoreBoardButton.setFocusable(false);
        scoreBoardButton.addActionListener(new ScoreBoardButtonListener());

        exitButton = new JButton("EXIT");
        exitButton.setBounds(50,350,300,75);
        exitButton.setFont(new Font("",Font.BOLD,20));
        exitButton.setFocusable(false);
        exitButton.addActionListener(new ExitButtonListener());

        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(null);
        mainMenuPanel.setBounds(0,0,425,510);
        mainMenuPanel.setBackground(Color.GRAY);
        mainMenuPanel.add(newGameButton);
        mainMenuPanel.add(loadButton);
        mainMenuPanel.add(scoreBoardButton);
        mainMenuPanel.add(exitButton);
        this.add(mainMenuPanel);
    }
    private void initializePauseMenuPage(){
        continueButton = new JButton("CONTINUE");
        continueButton.setBounds(50,50,300,75);
        continueButton.setFont(new Font("",Font.BOLD,20));
        continueButton.setFocusable(false);
        continueButton.addActionListener(new ContinueButtonListener());

        restartButton = new JButton("RESTART");
        restartButton.setBounds(50,150,300,75);
        restartButton.setFont(new Font("",Font.BOLD,20));
        restartButton.setFocusable(false);
        restartButton.addActionListener(new NewGameButtonListener());

        saveButton = new JButton("SAVE");
        saveButton.setBounds(50,250,300,75);
        saveButton.setFont(new Font("",Font.BOLD,20));
        saveButton.setFocusable(false);
        saveButton.addActionListener(new SaveButtonListener());

        quitButton = new JButton("QUIT TO MAIN MENU");
        quitButton.setBounds(50,350,300,75);
        quitButton.setFont(new Font("",Font.BOLD,20));
        quitButton.setFocusable(false);
        quitButton.addActionListener(new QuitButtonListener());

        pauseMenuPanel = new JPanel();
        pauseMenuPanel.setLayout(null);
        pauseMenuPanel.setBounds(0,0,425,510);
        pauseMenuPanel.setBackground(Color.GRAY);
        pauseMenuPanel.add(continueButton);
        pauseMenuPanel.add(restartButton);
        pauseMenuPanel.add(saveButton);
        pauseMenuPanel.add(quitButton);
        this.add(pauseMenuPanel);
    }
    private void initializeScoreBoardPage(){
        slider = new JSlider();
        slider.setBounds(50,50,50,400);
        slider.setOrientation(SwingConstants.VERTICAL);
        slider.setBackground(Color.DARK_GRAY);
        slider.setInverted(true);
        slider.setFocusable(false);
        slider.addChangeListener(new ScoreBoardSliderChangeListener());

        nameLabel = new JLabel[10];
        for(int i=0;i<10;i++){
            nameLabel[i] = new JLabel();
            nameLabel[i].setBounds(125,50+40*i,500,40);
            nameLabel[i].setFont(new Font("",Font.BOLD,20));
            nameLabel[i].setOpaque(true);
        }

        backButton = new JButton("BACK");
        backButton.setBounds(50,485,125,50);
        backButton.setFont(new Font("",Font.BOLD,20));
        backButton.setFocusable(false);
        backButton.addActionListener(new QuitButtonListener());

        scoreBoardPanel = new JPanel();
        scoreBoardPanel.setLayout(null);
        scoreBoardPanel.setBounds(0,0,675,575);
        scoreBoardPanel.setBackground(Color.GRAY);
        scoreBoardPanel.add(slider);
        for(int i=0;i<10;i++) scoreBoardPanel.add(nameLabel[i]);
        scoreBoardPanel.add(backButton);
        this.add(scoreBoardPanel);
    }
    private void initializeGamePage(){
        gamePanel = new GamePanel();
        scorePanel = new ScorePanel();
        this.add(gamePanel);
        this.add(scorePanel);
    }

    public void switchToMainMenuPage(){
        gamePanel.setVisible(false);
        scorePanel.setVisible(false);
        pauseMenuPanel.setVisible(false);
        scoreBoardPanel.setVisible(false);
        mainMenuPanel.setVisible(true);
        this.setBounds(500,100,425,510);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void switchToPauseMenuPage(){
        gamePanel.setVisible(false);
        scorePanel.setVisible(false);
        mainMenuPanel.setVisible(false);
        scoreBoardPanel.setVisible(false);
        pauseMenuPanel.setVisible(true);
        this.setBounds(500,100,425,510);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    public void switchToScoreBoardPage(LinkedHashMap<String,Integer> scoreBoard){
        this.scoreBoard = scoreBoard;
        slider.setValue(0);
        slide();
        if(scoreBoard.size()<=10) slider.setEnabled(false);
        else{
            slider.setMinimum(0);
            slider.setMaximum(scoreBoard.size()-10);
            slider.setEnabled(true);
        }
        Random random = new Random();
        for(int i=0;i<10;i++)
            nameLabel[i].setBackground(new Color(random.nextInt(206)+50,random.nextInt(206)+50,random.nextInt(206)+50));
        gamePanel.setVisible(false);
        scorePanel.setVisible(false);
        mainMenuPanel.setVisible(false);
        pauseMenuPanel.setVisible(false);
        scoreBoardPanel.setVisible(true);
        this.setBounds(400,100,685,610);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    public void switchToGamePage(){
        mainMenuPanel.setVisible(false);
        pauseMenuPanel.setVisible(false);
        scoreBoardPanel.setVisible(false);
        gamePanel.setVisible(true);
        scorePanel.setVisible(true);
        this.setBounds(300,10,880,785);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    public void slide(){
        index = slider.getValue();
        LinkedList<String> names = new LinkedList<>(scoreBoard.keySet());
        LinkedList<Integer> scores = new LinkedList<>(scoreBoard.values());
        for(int i=index;i<index+10;i++){
            if(names.size()>i){
                nameLabel[i-index].setText("  "+(i+1)+". "+names.get(i)+"  |  "+scores.get(i));
            }
        }
    }

    public void update(GameState gameState){
        gamePanel.update(gameState);
        scorePanel.update(gameState);
    }
}
