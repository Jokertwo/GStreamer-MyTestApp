package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class Exit implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public Exit(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.exit();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Close application";
    }

}
