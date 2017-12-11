/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package spiel;

import static graphic.MainFrame.WINDOW;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class IO implements InputListener, OutputListener {

    public static final boolean DEBUG_MODE = true;

    public enum MessageType {

        /**
         * lowest priority, marks a message to only be printed by
         * System.out.println() if DEBUG_MODE is set
         */
        DEBUG,
        /**
         * marks a message to print "normal" by System.out.println()
         */
        NORMAL,
        /**
         * marks a message for printing "normal" AND in the UI
         */
        IMPORTANT,
        /**
         * marks a message for printing over standard error stream by
         * System.err.println() AND if DEBUG_MODE is set it should also printed
         * like text marked with the IMPORTANT MessageType
         */
        ERROR;
    }
    private final CopyOnWriteArrayList<InputListener> inputlistener = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<OutputListener> outputlistener = new CopyOnWriteArrayList<>();

    public static final IO IOMANAGER = new IO(); // Singleton

    private IO() {
    }

    /**
     * Indicates if the main window is showing a text on its output panel. That
     * means if the window is printing a text it may also wait for an input to
     * print the next message (see printNext()) or stop printing by clearing the
     * message queue (see flush()).
     *
     * @return true if text is displayed on the main text panel, false if not.
     */
    public boolean isPrinting() {
        return WINDOW.isPrinting();
    }

    /**
     * Prints a string as indicated by the given MessageType.
     *
     * @param txt the text to be printed
     * @param msgtype the type of message indicating the priority for the
     * message as defined in the enum MessageType.
     */
    public void println(String txt, MessageType msgtype) {
        switch (msgtype) {
            case ERROR:
                System.err.println(txt);
                if (!DEBUG_MODE) {
                    break;
                }
            case IMPORTANT:
                WINDOW.print(txt);
            case NORMAL:
                System.out.println(txt);
                break;
            case DEBUG:
                if (DEBUG_MODE) {
                    System.out.println(txt);
                }
                break;
        }
    }

    /**
     * Notifies the main window to print the next string in its message queue.
     * The window may do nothing or stop printing if the queue is empty.
     */
    public void printNext() {
        WINDOW.printNext();
    }

    public void writeToLog(String txt) {
        if (DEBUG_MODE) {
            // TODO write to log file
        }
    }

    /**
     * Clears the message queue of the main window. After this method had been
     * processed the window should have stopped printing. The OutputListeners
     * will not be notified.
     */
    public void flush() {
        outputlistener.clear();
        while (WINDOW.isPrinting()) {
            WINDOW.printNext();
        }
    }

    /**
     * Adds an InputListener to the list of InputListeners if the given instance
     * isnt already contained.
     *
     * @param l the InputListener to be added
     * @return true if l was added to the list, false if it is already
     * contained.
     */
    public boolean addInputListener(InputListener l) {
        if (!inputlistener.contains(l)) {
            inputlistener.add(l);
            return true;
        }
        return false;
    }

    public boolean removeOutputListener(OutputListener l) {
        return outputlistener.remove(l);
    }

    public void addOutputListener(OutputListener l) {
        if (!outputlistener.contains(l)) {
            outputlistener.add(l);
        }
    }

    public boolean removeInputListener(InputListener l) {
        return inputlistener.remove(l);
    }

    /**
     * IO manager notifies all his listeners that a button was pressed. Just
     * calls ButtonPressed(button) method of all InputListeners in the list.
     * Does nothing if the list is empty. Once a button was pressed the
     * ButtonReleased() method of this instance MUST be called for this button.
     *
     * @param button the ID of the pressed button as defined in the
     * InputListener interface.
     */
    @Override
    public void ButtonPressed(int button) {
        if (isPrinting()) {
            if (button == InputListener.A || button == InputListener.B) {
                IO.IOMANAGER.printNext();
            }
        } else {
            for (InputListener l : inputlistener) {
                l.ButtonPressed(button);
            }
        }
    }

    /**
     * IO manager notifies all his listeners that a button was released. Just
     * calls ButtonReleased(button) method of all InputListeners in the list.
     * Does nothing if the list is empty. The ButtonPressed() method of this
     * instance SHOULD be called for this button before to prevent issues.
     *
     * @param button the ID of the released button as defined in the
     * InputListener interface.
     */
    @Override
    public void ButtonReleased(int button) {
        for (InputListener l : inputlistener) {
            l.ButtonReleased(button);
        }
    }

    /**
     * IO manager notifies all his OutputListeners that the last output process
     * has finished. Just calls outputEnd() method of all OutputListeners in the
     * list. Does nothing if the list is empty.
     *
     */
    @Override
    public void outputEnd() {
        for (OutputListener l : outputlistener) {
            l.outputEnd();
        }
    }
}
