package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class End extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    private CommandBuffer commandBuffer;


    public End(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super(">>|");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public End(VideoPlayerCtrl ctrl) {
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.rewindToEnd();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind to end";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.END);
    }

}
