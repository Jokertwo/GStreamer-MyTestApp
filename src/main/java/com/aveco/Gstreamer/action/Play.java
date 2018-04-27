package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class Play implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public Play(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.play();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Play video";
    }

}
