package com.aveco.Gstreamer.ctrl.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class PlusOneSec implements CtrlAction {

    
    private IVideoPlayerCtrl ctrl;
    
    public PlusOneSec(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }

    @Override
    public void doIt() {
        ctrl.rewindOneFront();
    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind one sec forward";
    }

}
