package MojoNoid.Agents;

import MojoNoid.Graphics.Page;
import MojoNoid.Models.GameState;
import MojoNoid.Tools.Listeners.GraphicListener;

import javax.swing.*;
import java.util.LinkedHashMap;

public class GraphicalAgent {
    private LogicalAgent logicalAgent;
    private Page currentPage;

    public GraphicalAgent(LogicalAgent logicalAgent){
        this.logicalAgent = logicalAgent;
        GraphicListener.setGraphicalAgent(this);
        currentPage = new Page();
    }

    public void startNewGame(){
        logicalAgent.startNewGame();
    }

    public boolean validateFileName(String name){
        return !name.isBlank() && !name.isEmpty() && !name.contains("/") && !name.contains("\\") && !name.contains(":") && !name.contains("*") && !name.contains("?") && !name.contains("\"") && !name.contains("<") && !name.contains(">") && !name.contains("|") && !name.contains(".");
    }

    public void loadRequest(){
        String name;
        do{
            name = JOptionPane.showInputDialog("Enter saved game name:");
            if(name==null) break;
        } while(!validateFileName(name));
        if(name!=null) logicalAgent.loadRequest(name);
    }
    public void saveRequest(){
        String name;
        do{
            name = JOptionPane.showInputDialog("Enter name to save:");
            if(name==null) break;
        } while(!validateFileName(name));
        if(name!=null) logicalAgent.saveRequest(name);
    }
    public void scoreBoardRequest(){
        logicalAgent.scoreBoardRequest();
    }
    public void slideRequest(){
        currentPage.slide();
    }

    public void showFileNotFoundDialog(String name){
        JOptionPane.showMessageDialog(null,"Could not find save file: "+name,"File Not Found",JOptionPane.WARNING_MESSAGE);
    }
    public void showCouldNotReadFileDialog(String name){
        JOptionPane.showMessageDialog(null,"Could not read save file: "+name,"Could Not Read File",JOptionPane.WARNING_MESSAGE);
    }
    public void showSaveProcessSuccessDialog(String name){
        JOptionPane.showMessageDialog(null,"Saved game as file: "+name,"Save Successful",JOptionPane.INFORMATION_MESSAGE);
    }
    public String showNameInputDialog(){
        String name;
        do{
            name = JOptionPane.showInputDialog("Enter name to for the score board:");
        } while(name==null || !validateFileName(name));
        return name;
    }

    public void moveRequest(int direction){
        logicalAgent.moveRequest(direction);
    }
    public void inGamePause(){
        logicalAgent.inGamePause();
    }
    public void completePause(){
        logicalAgent.completePause();
    }
    public void quit(){
        logicalAgent.quit();
    }
    public void update(GameState gameState){
        currentPage.update(gameState);
    }

    public void switchToMainMenuPage(){
        currentPage.switchToMainMenuPage();
    }
    public void switchToPauseMenuPage(){
        currentPage.switchToPauseMenuPage();
    }
    public void switchToScoreBoardPage(LinkedHashMap<String,Integer> scoreBoard){
        currentPage.switchToScoreBoardPage(scoreBoard);
    }
    public void switchToGamePage(){
        currentPage.switchToGamePage();
    }
}
