package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class Pause implements CtrlAction {

    
    private IVideoPlayerCtrl ctrl;
    
    public Pause(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }

    @Override
    public void doIt() {
        ctrl.pause();
    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Pause video";
    }

}
