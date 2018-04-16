package com.aveco.Gstreamer.ctrl.action;

public class Exit implements CtrlAction {

    @Override
    public void doIt() {
        System.exit(0);

    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Close application";
    }

}
