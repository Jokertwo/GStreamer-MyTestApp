package com.aveco.Gstreamer.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class RewindFront implements CtrlAction {

    private static final Logger logger = LoggerFactory.getLogger(RewindFront.class);
    private IVideoPlayerCtrl ctrl;


    public RewindFront(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        if (argument.length > 1) {
            int number;
            if ((number = getNumber(argument[1], logger)) > 0) {
                ctrl.rewindFront(number);
            }
        } else {
            ctrl.rewindFront(1);
        }

    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind forward (default one sec) / argument sec";
    }

}
