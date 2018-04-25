package com.aveco.Gstreamer;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.freedesktop.gstreamer.Gst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;
import com.aveco.Gstreamer.ctrl.TestControler;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.gui.CommandTextField;
import com.aveco.Gstreamer.gui.LogInfo;
import com.aveco.Gstreamer.gui.MyGWindow;
import com.aveco.Gstreamer.log.CreateLogger;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.playBin.VideoPlayer;
import com.aveco.Gstreamer.tag.TagPlayBin;


public class Main {

    public static final String PATH = "\\com\\aveco\\res\\a.mp4";

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private ExecutorService execService;
    private LogInfo logInfo;


    public Main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
        new CreateLogger();
        SwingUtilities.invokeAndWait(()-> logInfo = new LogInfo());

        execService = Executors.newSingleThreadExecutor();

        logger.info("Begin of app for testing video.");

        logger.trace("Inicialize of Gsreamer");
        args = Gst.init("FirstAppGst", args);

        CommandBuffer commandBuffer = new CommandBuffer();

        IVideoPlayer videoPlayer = new VideoPlayer(getURI());
        ITestControler ctrlTest = new TestControler(videoPlayer);
        IVideoPlayerCtrl ctrlVP = new VideoPlayerCtrl(videoPlayer, ctrlTest);

        
        execService.execute(new TagPlayBin(getURI()));
        execService.execute(new CommandLine(ctrlVP, commandBuffer));

        // start gui
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(videoPlayer.getSimpleVideoCompoment(), BorderLayout.CENTER);
            new MyGWindow(panel, logInfo, new CommandTextField(commandBuffer));
        });

        execService.shutdown();

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


    public File getFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        return file;
    }


    public URL getURL() {
        URL url = null;
        url = this.getClass().getClassLoader().getResource(PATH);
        return url;
    }


    public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
        new Main(args);
    }

}
