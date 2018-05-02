package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class PlayFrameBack extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    private static final Logger logger = LoggerFactory.getLogger(PlayFrameBack.class);


    public PlayFrameBack(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        if (argument.length > 1) {
            int number;
            if ((number = getNumber(argument[1], logger)) > 0) {
                ctrl.playFrameBack(number);
            }
        } else {
            ctrl.playFrameBack(1);
        }
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Play frame back (default 1 or value of argument)";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.playFrameBack(1);
    }

}
