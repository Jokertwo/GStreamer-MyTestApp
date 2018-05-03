package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class StepForward extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    private CommandBuffer commandBuffer;
    private static final Logger logger = LoggerFactory.getLogger(StepForward.class);


    public StepForward(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super(">|");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public StepForward(VideoPlayerCtrl ctrl) {

        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        if (argument.length > 1) {
            int number;
            if ((number = getNumber(argument[1], logger)) > 0) {
                ctrl.stepForward(number);
            }
        } else {
            ctrl.stepForward(1);
        }
    }


    @Override
    public String help() {
        return "Perform one frame step forward (default 1 or value of argument)";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.STEP_FRONT);
    }

}
