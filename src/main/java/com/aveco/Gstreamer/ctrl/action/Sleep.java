package com.aveco.Gstreamer.ctrl.action;

import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;

public class Sleep implements CtrlAction {

    private IVideoPlayerCtrl ctrl;
    
    public Sleep(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.sleep(500);
    }


    @Override
    public String help() {
       return "Pause between executing the command (500ms)";
    }

}
