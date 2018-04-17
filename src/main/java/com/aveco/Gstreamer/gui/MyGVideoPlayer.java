package com.aveco.Gstreamer.gui;

import java.awt.BorderLayout;
import java.net.URI;
import javax.swing.JPanel;
import org.freedesktop.gstreamer.Bus.EOS;
import org.freedesktop.gstreamer.Bus.ERROR;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;


@SuppressWarnings("serial")
public class MyGVideoPlayer extends JPanel implements IMyGVideoPlayer {

    private static final Logger logger = LogManager.getLogger();

    private SimpleVideoComponent vCmp;
//    private VideoComponent vCmp;
    private PlayBin playBin;
//    private PlayBin2 playBin;


    public MyGVideoPlayer(URI uri) {
        playBin = new PlayBin("VideoPlayer");
        logger.trace("PlayBin was created");
//        playBin = new PlayBin2("VideoPlayer");

        vCmp = new SimpleVideoComponent();
        logger.trace("SimpleVideoComponent was created");
//        vCmp = new VideoComponent();

        playBin.setVideoSink(vCmp.getElement());
        playBin.setURI(uri);

        setLayout(new BorderLayout());
        add(vCmp, BorderLayout.CENTER);

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


    @Override
    public PlayBin getPlayBin() {
        return playBin;
    }


    @Override
    public SimpleVideoComponent getSimpleVideoCompoment() {
        return vCmp;
    }


    @Override
    public JPanel getPanel() {
        return this;
    }

//    public PlayBin2 getPlayBin() {
//        return playBin;

}
