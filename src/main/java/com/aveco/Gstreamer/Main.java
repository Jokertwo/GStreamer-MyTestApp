package com.aveco.Gstreamer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.SwingUtilities;
import org.freedesktop.gstreamer.Gst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;
import com.aveco.Gstreamer.ctrl.TestControler;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.gui.CommandTextField;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;
import com.aveco.Gstreamer.gui.LogInfo;
import com.aveco.Gstreamer.gui.MyGVideoPlayer;
import com.aveco.Gstreamer.gui.MyGWindow;
import com.aveco.Gstreamer.log.CreateLogger;


public class Main {

    public static final String PATH = "\\com\\aveco\\res\\a.mp4";

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public Main(String[] args) throws IOException {
        new CreateLogger();
        LogInfo logInfo = new LogInfo();
        logger.info("Begin of app for testing video.");

        logger.trace("Inicialize of Gsreamer");
        args = Gst.init("FirstAppGst", args);

        CommandBuffer commandBuffer = new CommandBuffer();

        IMyGVideoPlayer videoPlayer = new MyGVideoPlayer(getURI());
        ITestControler ctrlTest = new TestControler(videoPlayer);
        IVideoPlayerCtrl ctrlVP = new VideoPlayerCtrl(videoPlayer, ctrlTest);

        // start gui
        SwingUtilities
            .invokeLater(() -> new MyGWindow(videoPlayer.getPanel(), logInfo, new CommandTextField(commandBuffer)));

        // start command line
        new Thread(new CommandLine(ctrlVP, commandBuffer)).start();

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
//        installShutdownHook();
        new Main(args);
    }


    private static void installShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            System.out.println("ShotDown GStreamer");

            Gst.deinit();

        }, "Shutdown-thread"));
    }
}
