package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class StopTest implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public StopTest(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.stopTest();
    }


    @Override
    public String help() {
        return "Stop all tests";
    }

}
