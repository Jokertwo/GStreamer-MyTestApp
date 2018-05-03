package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;

@SuppressWarnings("serial")
public class Start extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    private CommandBuffer commandBuffer;
    
    public Start(VideoPlayerCtrl ctrl, CommandBuffer commandBuffer) {
        super("|<<");
        this.ctrl = ctrl;
        this.commandBuffer = commandBuffer;
    }


    public Start(VideoPlayerCtrl ctrl) {
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.rewindToStart();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Rewind to begin";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        commandBuffer.addCommand(ActionConstant.START);     
    }
    
    

}
