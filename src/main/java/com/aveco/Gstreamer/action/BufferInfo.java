package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


@SuppressWarnings("serial")
public class BufferInfo extends AbstractCtrlAction {

    private VideoPlayerCtrl ctrl;
    public static final Logger logger = LoggerFactory.getLogger(BufferInfo.class);


    public BufferInfo(VideoPlayerCtrl ctrl) {
        super("Buf info");
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        ctrl.bufferInfo();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Print some information from buffer";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");

    }

}
