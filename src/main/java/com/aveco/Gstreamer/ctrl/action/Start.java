package com.aveco.Gstreamer.ctrl.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class Start implements CtrlAction {

    private IVideoPlayerCtrl ctrl;
    
    
    public Start(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.rewindToStart();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind to begin";
    }
    
    

}
