/**
 * Copyright (C) 2016 Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package template.utils;

import template.graphic.MainFrame;
import java.time.LocalDateTime;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class IO {

    public enum MessageType {
        /**
         * Writes the text to the logilfe and if DEBUG_ENABLE prints the text
         * via System.out.println().
         */
        DEBUG,
        /**
         * Prints the text "normally" with System.out.println() and writes the
         * text to the logilfe.
         */
        NORMAL,
        /**
         * Prints the text in the GUI and additionaly as NORMAL.
         */
        IMPORTANT,
        /**
         * Writes the text to the error logilfe and if DEBUG_ENABLE is set it
         * will print the text additionaly via System.err.println()
         */
        ERROR;
    }

    private static java.io.PrintWriter logWriter, errorLogWriter;

    private IO() {
    }

    public static void initLogs() {
        if (Settings.getSettings().loggingEnabled) {
            try {
                if (logWriter != null) {
                    logWriter.close();
                }
                if (errorLogWriter != null) {
                    errorLogWriter.close();
                }
                java.io.File logDir = new java.io.File(Constants.LOGGING_DIRECTORY);
                if (!logDir.exists()) {
                    logDir.mkdir();
                }
                logWriter = new java.io.PrintWriter(new java.io.File(Constants.LOGGING_DIRECTORY
                        + Constants.LOGGING_FILENAME));
                errorLogWriter = new java.io.PrintWriter(new java.io.File(
                        Constants.LOGGING_DIRECTORY + Constants.LOGGING_ERROR_FILENAME));
                IO.println("Logging started", MessageType.DEBUG);
            } catch (Exception ex) {
                System.err.println("Failed to initialize logging: " + ex);
                if (Constants.DEBUG_ENABLE) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void clearLogs() {
        closeLogs();
        try {
            java.io.File logDir = new java.io.File(Constants.LOGGING_DIRECTORY);
            if (logDir.exists()) {
                for (java.io.File logfile : logDir.listFiles()) {
                    logfile.delete();
                }
            }
        } catch (Exception ex) {
            IO.println("failed to delete log files: " + ex, MessageType.ERROR);
            if (Constants.DEBUG_ENABLE) {
                ex.printStackTrace();
            }
        }
        initLogs();
    }

    public static void closeLogs() {
        if (logWriter != null) {
            logWriter.close();
        }
        if (errorLogWriter != null) {
            errorLogWriter.close();
        }
        IO.println("Logging stopped", MessageType.DEBUG);
    }

    public static void println(String text, MessageType messagetype) {
        switch (messagetype) {
            case ERROR:
                if (Constants.DEBUG_ENABLE) {
                    System.err.println(text);
                }
                writeToErrorLog(text);
                break;
            case IMPORTANT:
                template.graphic.MainFrame.FRAME.println(text);
            case NORMAL:
                System.out.println(text);
                template.graphic.MainFrame.FRAME.writeToConsole(text);
            case DEBUG:
                if (messagetype == MessageType.DEBUG && Constants.DEBUG_ENABLE) {
                    System.out.println(text);
                }
                writeToLog("[" + messagetype.name().charAt(0) + "] " + text);
                break;
        }
    }

    public static String preamble() {
        return LocalDateTime.now().format(Constants.DATETIMEFORMAT) + ">> ";
    }

    public static void writeToLog(String text) {
        if (logWriter != null) {
            logWriter.append(preamble() + text + "\r\n");
        }
    }

    public static void writeToErrorLog(String text) {
        if (errorLogWriter != null) {
            errorLogWriter.append(preamble() + text + "\r\n");
        }
    }

    public static void printException(Exception ex) {
        if (errorLogWriter != null) {
            ex.printStackTrace(errorLogWriter);
        }
    }

    public static void makeToast(String msg, int msgType) {
        javax.swing.JOptionPane.showMessageDialog(MainFrame.FRAME, msg, "Message", msgType);
        if (msgType == javax.swing.JOptionPane.ERROR_MESSAGE) {
            println(msg, MessageType.ERROR);
        } else {
            println(msg, MessageType.DEBUG);
        }
    }
}
