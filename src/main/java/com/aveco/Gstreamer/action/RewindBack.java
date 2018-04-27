package com.aveco.Gstreamer.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class RewindBack implements CtrlAction {

    private IVideoPlayerCtrl ctrl;

    private static final Logger logger = LoggerFactory.getLogger(RewindBack.class);


    public RewindBack(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        if (argument.length > 1) {
            int number;
            if ((number = getNumber(argument[1], logger)) > 0) {
                ctrl.rewindOneBack(number);
            }
        } else {
            ctrl.rewindOneBack(1);
        }

    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind one sec back";
    }

}
