package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class StepEventAction implements CtrlAction {

    private IVideoPlayerCtrl ctrl;
    public StepEventAction(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.stepEvent();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Perform one frame step -> i hope";
    }

}
