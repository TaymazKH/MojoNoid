package MojoNoid.Tools.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonListener extends GraphicListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        getGraphicalAgent().saveRequest();
    }
}
