package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class StepBack implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public StepBack(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.stepBack();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Perform step back by frame";
    }

}
