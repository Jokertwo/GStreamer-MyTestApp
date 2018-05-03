package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class StepBack extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    private static final Logger logger = LoggerFactory.getLogger(StepBack.class);
    private CommandBuffer commandBuffer;


    public StepBack(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super("|<");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public StepBack(VideoPlayerCtrl ctrl) {

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


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.STEP_BACK);
    }

}
