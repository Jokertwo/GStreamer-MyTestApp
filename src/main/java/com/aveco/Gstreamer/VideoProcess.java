package com.aveco.Gstreamer;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.videoInfo.ParseVideoPlayBinFindEnd;
import com.aveco.Gstreamer.videoInfo.ParseVideoPlayBinTag;
import com.aveco.Gstreamer.videoInfo.VideoInfo;


public class VideoProcess {

    private static final Logger logger = LoggerFactory.getLogger(VideoProcess.class);

    private ExecutorService executor;
    private IVideoPlayer videoPlayer;


    public VideoProcess(ExecutorService executor,
                        IVideoPlayer videoPlayer) {
        super();
        this.executor = executor;
        this.videoPlayer = videoPlayer;
    }


    public VideoInfo preprocess(URI uri) {
        VideoInfo videoInfo = new VideoInfo();
        Future<VideoInfo> futureVideoInfoTag = executor.submit(new ParseVideoPlayBinTag(uri, videoInfo));
        try {
            videoInfo = futureVideoInfoTag.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Future<VideoInfo> futureVideoInfoEnd = executor.submit(new ParseVideoPlayBinFindEnd(uri, videoInfo));
        try {
            videoInfo = futureVideoInfoEnd.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        videoPlayer.setUri(uri);
        return videoInfo;
    }

}
