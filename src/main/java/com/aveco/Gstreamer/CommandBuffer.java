package com.aveco.Gstreamer;

import java.util.LinkedList;


public class CommandBuffer {

    private LinkedList<String> commandBuffer;


    public CommandBuffer() {
        commandBuffer = new LinkedList<>();
    }


    public synchronized String getCommand() {
        String command;

        while (commandBuffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        command = commandBuffer.getFirst();
        commandBuffer.removeFirst();

        return command;
    }


    public synchronized void addCommand(String command) {

        commandBuffer.addLast(command);
        notify();

    }
}
