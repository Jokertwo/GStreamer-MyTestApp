package com.aveco.Gstreamer.playBin;

import java.net.URI;
import javax.swing.SwingUtilities;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Bus.EOS;
import org.freedesktop.gstreamer.Bus.ERROR;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Message;
import org.freedesktop.gstreamer.MessageType;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.GuiCtrl;


public class VideoPlayer2 implements IVideoPlayer {

    private static final Logger logger = LoggerFactory.getLogger(VideoPlayer2.class);
    private PlayBin playBin;
    private VideoComponent vComponent;
    private GuiCtrl guiCtrl;


    public VideoPlayer2() {
        if (Gst.isInitialized()) {
            logger.debug("Create VideoPlayer");
            playBin = new PlayBin("VideoPlayer2");
            vComponent = new VideoComponent();
            addListeners();
            playBin.setVideoSink(vComponent.getElement());

            Element level = ElementFactory.make("level", "level");
            playBin.set("audio-filter", level);

        } else {
            logger.error("GStreamer is not inicialized");
        }
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
                logger.error("GstObject: " + source);
                logger.error("Error code: " + code);
                logger.error("Message: " + message);
            }
        });
        logger.trace("ERROR listener was add to playBin");

        playBin.getBus().connect(new Bus.MESSAGE() {

            @Override
            public void busMessage(Bus arg0, Message message) {
                Structure struct = message.getStructure();
                if (message.getType() == MessageType.ELEMENT
                        && message.getSource().getName().startsWith("level")) {
                    // We can get either rms or peak
                    double[] levels = struct.getDoubles("peak");
                    // Calculate the time offset required to get the level
                    // information in sync with the video display
                    SwingUtilities.invokeLater(() -> {
                        if (guiCtrl != null) {
                            if(playBin.isPlaying()){
                            guiCtrl.updateVolume(levels);
                            }else{
                                guiCtrl.updateVolume(new double[0]);
                            }
                        }
                    });
                }
            }
        });

    }


    @Override
    public PlayBin getPlayBin() {
        return playBin;
    }


    @Override
    public VideoComponent getVideoCompoment() {
        return vComponent;
    }


    @Override
    public void setUri(URI uri) {
        playBin.stop();
        playBin.setURI(uri);
        playBin.pause();
    }


    @Override
    public void setGuiCtrl(GuiCtrl guiCtrl) {
        this.guiCtrl = guiCtrl;
    }

}
