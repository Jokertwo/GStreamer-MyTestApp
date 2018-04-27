package com.aveco.Gstreamer.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class PlayFrameFront implements CtrlAction {

    private IVideoPlayerCtrl ctrl;
    private static final Logger logger = LoggerFactory.getLogger(PlayFrameFront.class);


    public PlayFrameFront(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        if (argument.length > 1) {
            int number;
            if ((number = getNumber(argument[1], logger)) > 0) {
                ctrl.playFrameFront(number);
            }
        } else {
            ctrl.playFrameFront(1);
        }
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Play frame forward (default 1 or value of argument)";
    }

}
