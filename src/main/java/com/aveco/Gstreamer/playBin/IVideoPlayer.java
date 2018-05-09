package com.aveco.Gstreamer.playBin;

import java.net.URI;
import org.freedesktop.gstreamer.elements.PlayBin;
import com.aveco.Gstreamer.ctrl.GuiCtrl;


public interface IVideoPlayer {

    PlayBin getPlayBin();


    VideoComponent getVideoCompoment();


    void setUri(URI uri);


    void setGuiCtrl(GuiCtrl guiCtrl);

}
