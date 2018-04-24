package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class PlayOneFrameBack implements CtrlAction {

    private IVideoPlayerCtrl ctrl;


    public PlayOneFrameBack(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.playOneFrameBack();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Play one frame back";
    }

}
