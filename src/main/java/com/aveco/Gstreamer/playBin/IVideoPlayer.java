package com.aveco.Gstreamer.playBin;

import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;


public interface IVideoPlayer {

    PlayBin getPlayBin();


    SimpleVideoComponent getSimpleVideoCompoment();

}
