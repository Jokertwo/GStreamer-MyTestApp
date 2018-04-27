package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class RunTest implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public RunTest(IVideoPlayerCtrl ctrl) {
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

}
