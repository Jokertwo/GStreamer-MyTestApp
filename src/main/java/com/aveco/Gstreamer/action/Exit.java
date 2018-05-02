package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


public class Exit extends AbstractCtrlAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private VideoPlayerCtrl ctrl;


    public Exit(VideoPlayerCtrl ctrl) {
        super("Exit");
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.exit();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Close application";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.exit();      
    }

}
