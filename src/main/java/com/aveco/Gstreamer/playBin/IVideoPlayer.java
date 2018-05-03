package com.aveco.Gstreamer.playBin;

import java.net.URI;
import org.freedesktop.gstreamer.elements.PlayBin;


public interface IVideoPlayer {

    PlayBin getPlayBin();


    VideoComponent getVideoCompoment();


    void setUri(URI uri);

}
