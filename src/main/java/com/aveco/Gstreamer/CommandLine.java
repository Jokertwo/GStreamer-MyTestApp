package com.aveco.Gstreamer;

import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.action.Action;
import com.aveco.Gstreamer.action.ActionConstant;
import com.aveco.Gstreamer.action.ActualFrame;
import com.aveco.Gstreamer.action.BufferInfo;
import com.aveco.Gstreamer.action.End;
import com.aveco.Gstreamer.action.Exit;
import com.aveco.Gstreamer.action.FrameRate;
import com.aveco.Gstreamer.action.Pause;
import com.aveco.Gstreamer.action.Play;
import com.aveco.Gstreamer.action.PlayFrameBack;
import com.aveco.Gstreamer.action.PlayFrameFront;
import com.aveco.Gstreamer.action.RewindBack;
import com.aveco.Gstreamer.action.RewindFront;
import com.aveco.Gstreamer.action.RunTest;
import com.aveco.Gstreamer.action.Seek;
import com.aveco.Gstreamer.action.Start;
import com.aveco.Gstreamer.action.State;
import com.aveco.Gstreamer.action.StepBack;
import com.aveco.Gstreamer.action.StepForward;
import com.aveco.Gstreamer.action.StopTest;
import com.aveco.Gstreamer.action.TestAction;
import com.aveco.Gstreamer.action.Time;
import com.aveco.Gstreamer.action.TimeCode;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


public class CommandLine implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    private final String help = "man";
    private Map<String, Action> actions;
    private CommandBuffer buffer;
    private boolean run = true;


    public CommandLine(VideoPlayerCtrl ctrl, CommandBuffer buffer) {
        super();
        this.buffer = buffer;
        initActions(ctrl);
    }


    @Override
    public void run() {
        logger.info("Thread for command line begin run");
        Thread.currentThread().setName("Command-console");
        String commands;
        while (run) {
            commands = buffer.getCommand();

            if (commands.trim().isEmpty()) {
                continue;
            }
            // print help
            if (commands.equals(help)) {
                printHelp();
                continue;
            }

            if (commands.equals(ActionConstant.EXIT)) {
                run = false;
            }

            String[] splitCommand = commands.split(" ");
            // check command
            if (actions.containsKey(splitCommand[0])) {
                try {
                    actions.get(splitCommand[0]).doIt(splitCommand);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Exception in commandLine");
                }
            } else {
                unknownCoomand(commands);
            }
        }
        logger.info("Thread for command line was stoped");
    }


    public void stop() {
        run = false;
    }


    private void printHelp() {
        for (Map.Entry<String, Action> entry : actions.entrySet()) {
            logger.info(entry.getKey() + "\t" + entry.getValue().help());
        }
    }


    private void unknownCoomand(String command) {
        logger.warn("Unknown command '" + command + "'");
        logger.info("For help type '" + help + "'");
    }


    private void initActions(VideoPlayerCtrl ctrl) {
        actions = new TreeMap<>();
        actions.put(ActionConstant.PLAY, new Play(ctrl));
        actions.put(ActionConstant.PAUSE, new Pause(ctrl));
        actions.put(ActionConstant.PLUS1, new RewindFront(ctrl));
        actions.put(ActionConstant.MINU1, new RewindBack(ctrl));
        actions.put(ActionConstant.START, new Start(ctrl));
        actions.put(ActionConstant.END, new End(ctrl));
        actions.put(ActionConstant.STATE, new State(ctrl));
        actions.put(ActionConstant.TIME, new Time(ctrl));
        actions.put(ActionConstant.ACTUAL_FRAME, new ActualFrame(ctrl));
        actions.put(ActionConstant.VIDEO_FRAME_RATE, new FrameRate(ctrl));
        actions.put(ActionConstant.EXIT, new Exit(ctrl));
        actions.put(ActionConstant.RUN_TEST, new RunTest(ctrl));
        actions.put(ActionConstant.TIME_CODE, new TimeCode(ctrl));
        actions.put(ActionConstant.STOP_TEST, new StopTest(ctrl));
        actions.put(ActionConstant.BUFFER_INFO, new BufferInfo(ctrl));
        actions.put(ActionConstant.STEP_FRONT, new StepForward(ctrl));
        actions.put(ActionConstant.STEP_BACK, new StepBack(ctrl));
        actions.put(ActionConstant.TEST_ACTION, new TestAction(ctrl));
        actions.put(ActionConstant.PLAY_FRAME_FRONT, new PlayFrameFront(ctrl));
        actions.put(ActionConstant.PLAY_FRAME_BACK, new PlayFrameBack(ctrl));
        actions.put(ActionConstant.SEEK, new Seek(ctrl));
        logger.trace("Actions of command lind were inicialized");
    }

}
