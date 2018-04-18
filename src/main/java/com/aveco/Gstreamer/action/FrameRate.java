package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class FrameRate implements CtrlAction {
    private IVideoPlayerCtrl ctrl;


    public FrameRate(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.frameRate();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Video frame rate";
    }

}
