package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class Exit extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    private CommandBuffer commandBuffer;


    public Exit(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super("Exit");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public Exit(VideoPlayerCtrl ctrl) {
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.exit();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Close application";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.EXIT);
    }

}
