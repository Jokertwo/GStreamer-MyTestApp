package com.aveco.Gstreamer.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class PlayOneFrameFront implements CtrlAction {
    
    
    private IVideoPlayerCtrl ctrl;


    public PlayOneFrameFront(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.playOneFrameFront();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Play one frame front";
    }

}
