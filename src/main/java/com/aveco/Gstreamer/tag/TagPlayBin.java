package com.aveco.Gstreamer.tag;

import java.net.URI;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Bus.ASYNC_DONE;
import org.freedesktop.gstreamer.Bus.TAG;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TagPlayBin implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(TagPlayBin.class);
    private URI uri;


    public TagPlayBin(URI uri) {
        super();
        this.uri = uri;
    }

    @Override
    public void run() {
        logger.info("Thread for get TAG -> start");
        if (Gst.isInitialized()) {
            Thread.currentThread().setName("tag-finder");
            PlayBin tagFinder = new PlayBin("TagFinder");
            tagFinder.setVideoSink(ElementFactory.make("fakesink", "videosink"));
            tagFinder.setURI(uri);

            Bus.TAG tag = new TAG() {

                @Override
                public void tagsFound(GstObject source, TagList tagList) {
                    TagInfo.getInstance().parse(tagList);
                }
            };
            tagFinder.getBus().connect(tag);
            Bus.ASYNC_DONE asyn = new ASYNC_DONE() {

                @Override
                public void asyncDone(GstObject source) {
                    dispose(tagFinder, this, tag);

                }
            };

            tagFinder.getBus().connect(asyn);
            sleep(500);
            tagFinder.setState(State.PAUSED);
        } else {
            logger.error("GStreamer is not inicialized");
        }
        logger.info("Thread for get TAG -> end");
    }
    
    private void sleep(int count){
        try {
            Thread.sleep(count);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void dispose(PlayBin tagFinder, Bus.ASYNC_DONE asyn, Bus.TAG tag) {
        tagFinder.getBus().disconnect(asyn);
        tagFinder.getBus().disconnect(tag);
        tagFinder.setState(State.NULL);
        tagFinder.dispose();
    }

}
