package com.aveco.Gstreamer.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class BufferInfo implements CtrlAction {

    private IVideoPlayerCtrl ctrl;
    public static final Logger logger = LoggerFactory.getLogger(BufferInfo.class);


    public BufferInfo(IVideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt() {
        ctrl.bufferInfo();
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Print some information from buffer";
    }

}
