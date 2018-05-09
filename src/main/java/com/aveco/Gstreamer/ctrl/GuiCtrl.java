package com.aveco.Gstreamer.ctrl;

import java.net.URI;

public interface GuiCtrl {

        
    /**
     * display volume level
     * @param levels
     */
    void updateVolume(double[] levels);
    
    
    /**
     * Update button
     */
    void updateButton();       
    
}
