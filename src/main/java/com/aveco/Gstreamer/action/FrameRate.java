package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class FrameRate extends AbstractCtrlAction {
    private VideoPlayerCtrl ctrl;


    public FrameRate(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.frameRate();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Video frame rate";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!"); 
    }

}
