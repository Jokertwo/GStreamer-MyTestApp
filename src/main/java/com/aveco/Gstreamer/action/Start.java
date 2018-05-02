package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;

@SuppressWarnings("serial")
public class Start extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    
    
    public Start(VideoPlayerCtrl ctrl) {
        super("|<<");
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.rewindToStart();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind to begin";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.rewindToStart();       
    }
    
    

}
