package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class State extends AbstractCtrlAction {
    private VideoPlayerCtrl ctrl;


    public State(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.state();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Actual state";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");
        
    }

}
