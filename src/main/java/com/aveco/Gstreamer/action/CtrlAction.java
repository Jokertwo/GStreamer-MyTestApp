package com.aveco.Gstreamer.action;

import org.slf4j.Logger;


public interface CtrlAction {

    void doIt(String[] argument);


    String help();


    default int getNumber(String argument, Logger logger) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            logger.error("Cannot parse '" + argument + "' to number.");
        }
        return -1;
    }
}
