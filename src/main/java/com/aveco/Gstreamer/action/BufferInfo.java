package com.aveco.Gstreamer.action;

import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Sample;
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

        Sample sample = ctrl.getSimpleVideoComponent().getAppSink().pullPreroll();
        Buffer buf = sample.getBuffer();
        logger.info("Buffer getDuration: " + buf.getDuration());
        logger.info("Buffer getDecodeTimestamp: " + buf.getDecodeTimestamp());
        logger.info("Buffer getPresentationTimestamp: " + buf.getPresentationTimestamp());
        logger.info("Buffer getOfset: " + buf.getOffset());
        logger.info("Buffer getOfsetEnd: " + buf.getOffsetEnd());
    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Print some information from buffer";
    }

}
