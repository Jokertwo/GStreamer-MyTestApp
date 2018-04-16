package com.aveco.Gstreamer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.freedesktop.gstreamer.Gst;


public class Main {

    private static final String PATH = "\\com\\aveco\\res\\timecode.mp4";


    public Main(String[] args) throws IOException {

//        args = Gst.init("FirstAppGst", args);
        Gst.init("CameraTest", args);
        
        Gst.deinit();

//        IMyGVideoPlayer videoPlayer = new MyGVideoPlayer(getURI());
//        ITestControler ctrlTest = new TestControler(videoPlayer);
//        IVideoPlayerCtrl ctrlVP = new VideoPlayerCtrl(videoPlayer, ctrlTest);
//
//        //start gui
//        SwingUtilities.invokeLater(() -> new MyGWindow(videoPlayer.getPanel()));
//        
//        //start command line
//        new Thread(new CommandLine(ctrlVP)).start();

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
