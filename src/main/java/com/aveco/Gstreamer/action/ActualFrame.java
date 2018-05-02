package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class ActualFrame extends AbstractCtrlAction {
    private VideoPlayerCtrl ctrl;


    public ActualFrame(VideoPlayerCtrl ctrl) {
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.actualFrame();
    }


    @Override
    public String help() {
        return "Print number of actual frame";

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");
    }

}
