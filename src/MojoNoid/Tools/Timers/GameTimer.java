package MojoNoid.Tools.Timers;

import MojoNoid.Agents.LogicalAgent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer extends Timer {
    public GameTimer(LogicalAgent logicalAgent){
        super(50, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                logicalAgent.update();
            }
        });
    }
}
