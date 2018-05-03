package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class RewindFront extends AbstractCtrlAction {

    private static final Logger logger = LoggerFactory.getLogger(RewindFront.class);
    private VideoPlayerCtrl ctrl;
    private CommandBuffer commandBuffer;


    public RewindFront(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super(">>");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public RewindFront(VideoPlayerCtrl ctrl) {
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
        return "Rewind forward (default one sec) / argument sec";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.PLUS1);
    }

}
