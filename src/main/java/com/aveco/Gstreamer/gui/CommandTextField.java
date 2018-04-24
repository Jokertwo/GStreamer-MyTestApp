package com.aveco.Gstreamer.gui;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JTextField;
import com.aveco.Gstreamer.CommandBuffer;


@SuppressWarnings("serial")
public class CommandTextField extends JTextField {

    private CommandBuffer buffer;
    private static final int MAX_MEMORY_SIZE = 10;
    private LinkedList<String> memory = new LinkedList<>();
    private int position = 0;


    public CommandTextField(CommandBuffer buffer) {
        super();
        this.buffer = buffer;
        addAction();
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
    }


    private void addAction() {

        addActionListener(e -> {
            if (!getText().trim().isEmpty()) {
                String command = getText();
                buffer.addCommand(command);
                addToMemory(command);
                position = 0;
                setText("");
            }
        });

        addKeyListener(new ArrowKeyEvent());
    }


    private void addToMemory(String command) {
        if (memory.isEmpty()) {
            memory.addFirst(command);
        } else if (!memory.getFirst().equals(command)) {
            if (memory.size() < MAX_MEMORY_SIZE) {
                memory.addFirst(command);
            } else {
                memory.removeLast();
                memory.addFirst(command);
            }
        }
    }

    private class ArrowKeyEvent implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }


        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (!memory.isEmpty() && position < memory.size()) {
                        setText(memory.get(position));
                        if (position < memory.size() - 1) {
                            position++;
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!memory.isEmpty() && position > 0) {
                        setText(memory.get(position));
                        position--;
                    }
                    break;
            }

        }


        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

    }
}
