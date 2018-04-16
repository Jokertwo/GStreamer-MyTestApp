package com.aveco.Gstreamer.ctrl.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class End implements CtrlAction {

    
    private IVideoPlayerCtrl ctrl; 
    
    
    public End(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.rewindToEnd();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind to end";
    }

}
