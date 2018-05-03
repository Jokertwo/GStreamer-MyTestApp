package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class Pause extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    private CommandBuffer commandBuffer;


    public Pause(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super("||");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public Pause(VideoPlayerCtrl ctrl) {

        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.pause();
    }


    @Override
    public String help() {
        return "Pause video";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.PAUSE);
    }

}
