package com.aveco.Gstreamer.playBin;

import java.awt.EventQueue;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.Bus.EOS;
import org.freedesktop.gstreamer.Bus.ERROR;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Event;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Message;
import org.freedesktop.gstreamer.MessageType;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.Pad.EVENT_PROBE;
import org.freedesktop.gstreamer.PadProbeReturn;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.videoInfo.ParseVideoPlayBinTag;


public class VideoPlayer implements Callable<IVideoPlayer>, IVideoPlayer {

    private static final Logger logger = LoggerFactory.getLogger(ParseVideoPlayBinTag.class);
    private VideoComponent vCmp;
    private PlayBin playBin;
    private URI uri;


    public VideoPlayer(URI uri) {
        super();
        this.uri = uri;
//        run();
    }


    @Override
    public IVideoPlayer call() throws Exception {
        if (Gst.isInitialized()) {

            playBin = new PlayBin("VideoPlayer");

            logger.trace("PlayBin was created");

            vCmp = new VideoComponent();
            logger.trace("SimpleVideoComponent was created");

            addListeners();

            playBin.setVideoSink(vCmp.getElement());
            playBin.setURI(uri);

            playBin.setState(State.PAUSED);
            logger.trace("PlayBin for play was set PAUSED state");
            return this;

        } else {
            logger.error("GStreamer is not inicialized");
        }
        return null;
    }


    private void addListeners() {

        playBin.getBus().connect(new EOS() {
            @Override
            public void endOfStream(GstObject source) {
                playBin.pause();
                logger.debug("End of video -> video was paused");
            }
        });
        logger.trace("EOS listener was add to playBin");

        playBin.getBus().connect(new ERROR() {

            @Override
            public void errorMessage(GstObject source, int code, String message) {
                System.out.println("GstObject: " + source);
                System.out.println("Error code: " + code);
                System.out.println("Message: " + message);
            }
        });
        logger.trace("ERROR listener was add to playBin");

    }


    @SuppressWarnings("unused")
    private void eventProbe() {

        Pad videoSinkPad = vCmp.getElement().getStaticPad("sink");
        videoSinkPad.addEventProbe(new EVENT_PROBE() {

            @Override
            public PadProbeReturn eventReceived(Pad pad, Event event) {

//                if (event instanceof TagEvent) {
////                    List<String> names = ((TagEvent) event).getTagList().getTagNames();
////
////                    for (String name : names) {
////                        System.out.println(name + " : " + ((TagEvent) event).getTagList().getValue(name, 0));
////
////                    }
////                    TagInfo.getInstance().parse(((TagEvent) event).getTagList());
//                }
//                else if (event instanceof ReconfigureEvent) {
//                    ReconfigureEvent reg = (ReconfigureEvent) event;
//                    Structure struct = reg.getStructure();
//                    System.out.println("ReconfigureEvent structure: " + struct);
//                }
//                else if (event instanceof LatencyEvent) {
//                    LatencyEvent latency = (LatencyEvent) event;
//                    System.out.println("LatencyEvent :" + latency.getLatency().toNanos());
//                }
//                else if (event instanceof SegmentEvent) {
//                    SegmentEvent segment = (SegmentEvent) event;
//                    System.out.println(segment.getSegment().toString());
//                    
//                }
//                else {
                System.out.println(event);
//                }
                return PadProbeReturn.OK;
            }
        });

    }


    @Override
    public PlayBin getPlayBin() {
        return playBin;
    }


    @Override
    public VideoComponent getVideoCompoment() {
        return vCmp;
    }


    @Override
    public void setUri(URI uri) {
        // TODO Auto-generated method stub

    }

}
