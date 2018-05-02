package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class Play extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;


    public Play(VideoPlayerCtrl ctrl) {
        super(">");
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.play();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Play video";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.play();       
    }

}
