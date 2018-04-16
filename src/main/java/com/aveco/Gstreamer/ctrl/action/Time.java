package com.aveco.Gstreamer.ctrl.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class Time implements CtrlAction {
    private IVideoPlayerCtrl ctrl;


    public Time(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.time();

    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Info about time";
    }

}
