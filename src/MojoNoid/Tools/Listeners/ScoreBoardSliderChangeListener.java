package MojoNoid.Tools.Listeners;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ScoreBoardSliderChangeListener extends GraphicListener implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        getGraphicalAgent().slideRequest();
    }
}
