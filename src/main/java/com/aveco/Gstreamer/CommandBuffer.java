package com.aveco.Gstreamer;

import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandBuffer {

    private LinkedList<String> commandBuffer;
    private static final Logger logger = LoggerFactory.getLogger(CommandBuffer.class);

    public CommandBuffer() {
        commandBuffer = new LinkedList<>();
        logger.trace("Command buffer was inicialzed.");
    }


    public synchronized String getCommand() {
        String command;

        while (commandBuffer.isEmpty()) {
            try {
                logger.trace(Thread.currentThread().getName() + " have nothing to do -> wait");
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        command = commandBuffer.getFirst();
        commandBuffer.removeFirst();
        logger.trace(Thread.currentThread().getName() + " pickUp command from buffer.");
        return command;
    }


    public synchronized void addCommand(String command) {

        commandBuffer.addLast(command);
        notify();
        logger.trace(Thread.currentThread().getName() + " store value to buffer and call notify");

    }
}
