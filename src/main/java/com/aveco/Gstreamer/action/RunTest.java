package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class RunTest extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;


    public RunTest(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.runTest();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Start test";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");
        
    }

}
