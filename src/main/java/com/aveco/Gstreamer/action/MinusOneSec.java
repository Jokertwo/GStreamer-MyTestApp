package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class MinusOneSec implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public MinusOneSec(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.rewindOneBack();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind one sec back";
    }

}
