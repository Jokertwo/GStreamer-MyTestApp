package com.aveco.Gstreamer.ctrl;

import static org.junit.Assert.*;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.freedesktop.gstreamer.Gst;
import org.junit.Before;
import org.junit.Test;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;
import com.aveco.Gstreamer.gui.MyGVideoPlayer;


public class TestControlerTest {

    ITestControler ctrl;
    
    String frameRate = "29.97002997002997";
    String a = "13286094433 00:00:13:08";
    String b = "12256000692 00:00:12:07";
    String c = "10245085074 00:00:10:07";
    String d = "9744117739  00:00:09:22";
   


    @Before
    public void setUp() throws Exception {
        Gst.init();
        ctrl = new TestControler((IMyGVideoPlayer) EasyMock.mock(IVideoPlayerCtrl.class));        
    }


   

}
