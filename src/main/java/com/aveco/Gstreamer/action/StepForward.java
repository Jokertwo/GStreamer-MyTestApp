package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class StepForward implements CtrlAction {

    private IVideoPlayerCtrl ctrl;
    public StepForward(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.stepForward();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Perform one frame step -> i hope";
    }

}
