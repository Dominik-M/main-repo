/**
 * Copyright (C) 2016 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
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
package utils;

/**
 * Created 09.03.2016
 * 
 * @author Dominik Messerschmidt (dominik.messerschmidt@continental-corporation.com)
 * 
 */
public class IO {
	public enum MessageType {
		/**
		 * Writes the text to the logilfe and if DEBUG_ENABLE prints the text via System.out.println().
		 */
		DEBUG,

		/**
		 * Prints the text "normally" with System.out.println() and writes the text to the logilfe.
		 */
		NORMAL,

		/**
		 * Prints the text in the GUI and additionaly as NORMAL.
		 */
		IMPORTANT,

		/**
		 * Writes the text to the error logilfe and if DEBUG_ENABLE is set it will print the text additionaly via
		 * System.err.println()
		 */
		ERROR;
	}

	private static java.io.PrintWriter logWriter, errorLogWriter;

	private IO() {
	}

	public static void initLogs() {
		if (Settings.settings.loggingEnabled) {
			try {
				if (logWriter != null)
					logWriter.close();
				if (errorLogWriter != null)
					errorLogWriter.close();
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
				if (Constants.DEBUG_ENABLE)
					ex.printStackTrace();
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
			if (Constants.DEBUG_ENABLE)
				ex.printStackTrace();
		}
		initLogs();
	}

	public static void closeLogs() {
		if (logWriter != null)
			logWriter.close();
		if (errorLogWriter != null)
			errorLogWriter.close();
		IO.println("Logging stopped", MessageType.DEBUG);
	}

	public static void println(String text, MessageType messagetype) {
		switch (messagetype) {
			case ERROR:
				if (Constants.DEBUG_ENABLE) {
					System.err.println(text);
				}
				if (errorLogWriter != null)
					errorLogWriter.append(text + "\r\n");
				break;
			case IMPORTANT:
				graphic.MainFrame.FRAME.println(text);
			case NORMAL:
				System.out.println(text);
			case DEBUG:
				if (messagetype == MessageType.DEBUG && Constants.DEBUG_ENABLE)
					System.out.println(text);
				if (logWriter != null)
					logWriter.append(text + "\r\n");
				break;
		}
	}
}
