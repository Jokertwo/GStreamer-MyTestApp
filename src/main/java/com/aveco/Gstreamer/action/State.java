package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class State implements CtrlAction {
    private IVideoPlayerCtrl ctrl;


    public State(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.state();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Actual state";
    }

}
