package com.aveco.Gstreamer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.gstreamer.Gst;
import com.aveco.Gstreamer.ctrl.ITestControler;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;
import com.aveco.Gstreamer.ctrl.TestControler;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;
import com.aveco.Gstreamer.gui.LogInfo;
import com.aveco.Gstreamer.gui.MyGVideoPlayer;
import com.aveco.Gstreamer.gui.MyGWindow;


public class Main {

    private static final String PATH = "\\com\\aveco\\res\\timecode.mp4";
    static {
        System.setProperty("log4j.configurationFile",
            "log/log_config.xml");
    }
    
    private static final Logger logger = LogManager.getLogger();

    public Main(String[] args) throws IOException {
        LogInfo logInfo = new LogInfo();
        logger.info("Begin of app for testing video.");
        
        logger.trace("Inicialize of Gsreamer");
        args = Gst.init("FirstAppGst", args);

        
        IMyGVideoPlayer videoPlayer = new MyGVideoPlayer(getURI());
        ITestControler ctrlTest = new TestControler(videoPlayer);
        IVideoPlayerCtrl ctrlVP = new VideoPlayerCtrl(videoPlayer, ctrlTest);

        // start gui
        SwingUtilities.invokeLater(() -> new MyGWindow(videoPlayer.getPanel(), logInfo));

        // start command line
        new Thread(new CommandLine(ctrlVP)).start();

    }


    public URI getURI() {
        URI uri = null;
        try {
            uri = this.getClass().getClassLoader().getResource(PATH).toURI();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return uri;
    }


    public static void main(String[] args) throws IOException {
        new Main(args);
    }
}
