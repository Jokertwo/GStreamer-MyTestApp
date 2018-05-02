package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class End extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;


    public End(VideoPlayerCtrl ctrl) {
        super(">>|");
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.rewindToEnd();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind to end";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.rewindToEnd();
    }

}
