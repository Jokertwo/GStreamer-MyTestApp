package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class TestAction implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public TestAction(IVideoPlayerCtrl ctrl) {
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

}
