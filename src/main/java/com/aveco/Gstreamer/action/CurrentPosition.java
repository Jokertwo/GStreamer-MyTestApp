package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class CurrentPosition implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public CurrentPosition(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.currentPosition();

    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Move to current positon";
    }

}
