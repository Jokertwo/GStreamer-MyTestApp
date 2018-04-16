package com.aveco.Gstreamer.ctrl.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class TimeCode implements CtrlAction {
    private IVideoPlayerCtrl ctrl; 
    
    public TimeCode(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.timeCode();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Print actual timeCode";
    }

}
