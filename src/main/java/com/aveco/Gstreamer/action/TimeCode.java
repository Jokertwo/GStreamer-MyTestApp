package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class TimeCode extends AbstractCtrlAction {
    private VideoPlayerCtrl ctrl;


    public TimeCode(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.timeCode();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Print actual timeCode";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");       
    }

}
