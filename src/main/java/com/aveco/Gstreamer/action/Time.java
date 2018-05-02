package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class Time extends AbstractCtrlAction {
    private VideoPlayerCtrl ctrl;


    public Time(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.time();

    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Info about time";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");
        
    }

}
