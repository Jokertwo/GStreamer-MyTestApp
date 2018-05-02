package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class StopTest extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;


    public StopTest(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.stopTest();
    }


    @Override
    public String help() {
        return "Stop all tests";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");
        
    }

}
