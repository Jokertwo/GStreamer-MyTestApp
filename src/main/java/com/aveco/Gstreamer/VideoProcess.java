package com.aveco.Gstreamer;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.videoInfo.ParseVideoPlayBinFindEnd;
import com.aveco.Gstreamer.videoInfo.ParseVideoPlayBinTag;
import com.aveco.Gstreamer.videoInfo.VideoInfo;
import com.aveco.Gstreamer.videoInfo.VideoInfoImpl;


public class VideoProcess {

    private static final Logger logger = LoggerFactory.getLogger(VideoProcess.class);

    private ExecutorService executor;
    private IVideoPlayer videoPlayer;
    private ITestControler testControler;
    private VideoPlayerCtrl playerCtrl;


    public VideoProcess(ExecutorService executor,
                        IVideoPlayer videoPlayer,
                        ITestControler testControler,
                        VideoPlayerCtrl playerCtrl) {
        super();
        this.executor = executor;
        this.videoPlayer = videoPlayer;
        this.testControler = testControler;
        this.playerCtrl = playerCtrl;
    }


    public void preprocess(URI uri) {
        executor.execute(() -> {
            logger.info("Video procesing start");
            VideoInfo newInfo = getVideoInfo(uri);
            videoPlayer.setUri(uri);
            testControler.setVideoInfo(newInfo);
            playerCtrl.setVideoInfo(newInfo);
            logger.info("VideoProcesing was done");

        });
    }


    private VideoInfo getVideoInfo(URI uri) {
        VideoInfo videoInfo = new VideoInfoImpl();
        new ParseVideoPlayBinTag(uri, videoInfo);
        new ParseVideoPlayBinFindEnd(uri, videoInfo);
        return videoInfo;
    }

}
