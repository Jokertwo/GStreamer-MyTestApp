package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;

@SuppressWarnings("serial")
public class Sleep extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    
    public Sleep(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.sleep(500);
    }


    @Override
    public String help() {
       return "Pause between executing the command (500ms)";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");
        
    }

}
