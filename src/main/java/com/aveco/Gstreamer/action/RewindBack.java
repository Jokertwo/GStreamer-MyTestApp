package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class RewindBack extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;

    private static final Logger logger = LoggerFactory.getLogger(RewindBack.class);

    private CommandBuffer commandBuffer;
    
   


    public RewindBack(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super("<<");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public RewindBack(VideoPlayerCtrl ctrl) {        
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


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.MINU1);       
    }

}
