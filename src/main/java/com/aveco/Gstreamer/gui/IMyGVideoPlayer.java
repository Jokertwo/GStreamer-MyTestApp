package com.aveco.Gstreamer.gui;

import javax.swing.JPanel;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;


public interface IMyGVideoPlayer {

    PlayBin getPlayBin();


    SimpleVideoComponent getSimpleVideoCompoment();


    JPanel getPanel();
}
