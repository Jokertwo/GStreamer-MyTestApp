package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;

@SuppressWarnings("serial")
public class Pause extends AbstractCtrlAction {

    
    private VideoPlayerCtrl ctrl;
    
    public Pause(VideoPlayerCtrl ctrl) {
        super("||");
        this.ctrl = ctrl;
    }

    @Override
    public void doIt(String[] argument) {
        ctrl.pause();
    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Pause video";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.pause();        
    }

}
