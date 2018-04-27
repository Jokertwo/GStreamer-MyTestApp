package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class ActualFrame implements CtrlAction {
    private IVideoPlayerCtrl ctrl;


    public ActualFrame(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.actualFrame();
    }


    @Override
    public String help() {
        return "Print number of actual frame";

    }

}
