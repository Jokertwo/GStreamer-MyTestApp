package com.aveco.Gstreamer;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.freedesktop.gstreamer.Gst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.TestControler;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrlImpl;
import com.aveco.Gstreamer.gui.ButtonPanel;
import com.aveco.Gstreamer.gui.CommandTextField;
import com.aveco.Gstreamer.gui.LogInfo;
import com.aveco.Gstreamer.gui.MyGWindow;
import com.aveco.Gstreamer.log.CreateLogger;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.playBin.VideoPlayer;
import com.aveco.Gstreamer.videoInfo.ParseVideoPlayBinFindEnd;
import com.aveco.Gstreamer.videoInfo.ParseVideoPlayBinTag;
import com.aveco.Gstreamer.videoInfo.VideoInfo;
import com.aveco.Gstreamer.videoInfo.VideoInfoImpl;


public class Main {
    private static final String[] PATHS = new String[10];
    static {
        PATHS[0] = "\\com\\aveco\\res\\a.mp4";
        PATHS[1] = "\\com\\aveco\\res\\b.mp4";
        PATHS[2] = "\\com\\aveco\\res\\c.mp4";
        PATHS[3] = "\\com\\aveco\\res\\tc_25fps_01min.avi";
        PATHS[4] = "\\com\\aveco\\res\\tc_25fps_01min.m2v";
        PATHS[5] = "\\com\\aveco\\res\\tc_29.97fps_df_1min.avi";
        PATHS[6] = "\\com\\aveco\\res\\tc_29.97fps_df_1min.mov";
        PATHS[7] = "\\com\\aveco\\res\\tc_30fps_ndf_01min.avi";
        PATHS[8] = "\\com\\aveco\\res\\tc_30fps_ndf_01min.m2v";
        PATHS[9] = "\\com\\aveco\\res\\tc_29.97fps_df_20min.avi";

    }

    public static final String PATH = PATHS[6];

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private LogInfo logInfo;
    private IVideoPlayer videoPlayer;
    private VideoInfo videoInfo;
    private ExecutorService executor;
    private VideoPlayerCtrl ctrlVideo;


    public Main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
        new CreateLogger();
        SwingUtilities.invokeAndWait(() -> logInfo = new LogInfo());

        logger.info("Begin of app for testing video.");

        logger.trace("Inicialize of Gsreamer");
        args = Gst.init("FirstAppGst", args);
        executor = Executors.newSingleThreadExecutor();

        CommandBuffer commandBuffer = new CommandBuffer();
        videoInfo = new VideoInfoImpl();

        Future<IVideoPlayer> futureVideo = executor.submit(new VideoPlayer(getURI()));
        executor.execute(new ParseVideoPlayBinTag(getURI(), videoInfo));
        executor.execute(new ParseVideoPlayBinFindEnd(getURI(), videoInfo));

        try {
            videoPlayer = futureVideo.get();
            if (videoPlayer == null) {
                throw new NullPointerException("Video player was null");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ctrlVideo = new VideoPlayerCtrlImpl(videoPlayer, new TestControler(videoPlayer, videoInfo), videoInfo);

        executor.execute(new CommandLine(ctrlVideo, commandBuffer));

        // start gui
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(videoPlayer.getSimpleVideoCompoment(), BorderLayout.CENTER);
            new MyGWindow(panel, logInfo, new CommandTextField(commandBuffer), new ButtonPanel(ctrlVideo));
        });

        executor.shutdown();

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
