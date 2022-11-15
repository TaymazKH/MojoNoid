package MojoNoid.Agents;

import MojoNoid.Models.GameState;
import MojoNoid.Tools.Timers.GameTimer;

import java.util.LinkedHashMap;

public class LogicalAgent {
    private DataAgent dataAgent;
    private GraphicalAgent graphicalAgent;
    private GameState gameState;
    private GameTimer timer;
    private boolean allowMovement, allowSpace, allowEsc;

    public LogicalAgent(){
        dataAgent = new DataAgent();
        graphicalAgent = new GraphicalAgent(this);
        timer = new GameTimer(this);
        allowMovement = false;
        allowSpace = false;
        allowEsc = false;
    }

    public void startNewGame(){
        gameState = new GameState();
        switchToGamePage();
        graphicalAgent.update(gameState);
        allowMovement = false;
        allowSpace = true;
        allowEsc = true;
    }
    public void startLoadedGame(){
        switchToGamePage();
        graphicalAgent.update(gameState);
        allowMovement = false;
        allowSpace = true;
        allowEsc = true;
    }

    public void loadRequest(String name){
        try{
            gameState = dataAgent.loadGame(name);
            if(gameState!=null) startLoadedGame();
            else graphicalAgent.showFileNotFoundDialog(name);
        } catch(Exception e){
            graphicalAgent.showCouldNotReadFileDialog(name);
        }
    }
    public void saveRequest(String name){
        dataAgent.saveGame(name,gameState);
        graphicalAgent.showSaveProcessSuccessDialog(name);
    }
    public void scoreBoardRequest(){
        switchToScoreBoardPage(dataAgent.loadScoreBoard());
    }

    public void moveRequest(int direction){
        if(allowMovement) gameState.moveRequest(direction);
    }
    public void inGamePause(){
        if(allowSpace){
            if(allowMovement) timer.stop();
            else timer.start();
            allowMovement = !allowMovement;
        }
    }
    public void completePause(){
        if(allowEsc){
            if(allowSpace){
                allowMovement = false;
                timer.stop();
                switchToPauseMenuPage();
            }
            else{
                switchToGamePage();
            }
            allowSpace = !allowSpace;
        }
    }
    public void quit(){
        allowEsc = false;
        switchToMainMenuPage();
    }
    public void update(){
        gameState.update();
        graphicalAgent.update(gameState);
        checkForEndGame();
    }
    private void checkForEndGame(){
        if(gameState.gameHasEnded()){
            timer.stop();
            allowMovement = false;
            allowSpace = false;
            allowEsc = false;
            dataAgent.saveScoreBoard(graphicalAgent.showNameInputDialog(), gameState.getScore());
            switchToMainMenuPage();
        }
    }

    public void switchToMainMenuPage(){
        graphicalAgent.switchToMainMenuPage();
    }
    public void switchToPauseMenuPage(){
        graphicalAgent.switchToPauseMenuPage();
    }
    public void switchToScoreBoardPage(LinkedHashMap<String,Integer> scoreBoard){
        graphicalAgent.switchToScoreBoardPage(scoreBoard);
    }
    public void switchToGamePage(){
        graphicalAgent.switchToGamePage();
    }
}
