package MojoNoid.Tools.Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanelKeyListener extends GraphicListener implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case 37-> getGraphicalAgent().moveRequest(-1);
            case 39-> getGraphicalAgent().moveRequest(1);
            case 32-> getGraphicalAgent().inGamePause();
            case 27-> getGraphicalAgent().completePause();
        }
    }
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}
}
