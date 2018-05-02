package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class TestAction extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;


    public TestAction(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.TestAction();

    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Action for test new thing";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");
        
    }

}
