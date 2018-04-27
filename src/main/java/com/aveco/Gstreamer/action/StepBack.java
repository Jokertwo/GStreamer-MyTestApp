package com.aveco.Gstreamer.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class StepBack implements CtrlAction {

    private IVideoPlayerCtrl ctrl;
    private static final Logger logger = LoggerFactory.getLogger(StepBack.class);

    public StepBack(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        if (argument.length > 1) {
            int number;
            if ((number = getNumber(argument[1], logger)) > 0) {
                ctrl.stepBack(number);
            }
        } else {
            ctrl.stepBack(1);
        }
        
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Perform step back by frame (default 1 or value of argument)";
    }

}
