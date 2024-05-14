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
package utils;

import java.time.LocalDateTime;

import graphic.MainFrame;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class IO
{

    /**
     * Defines available languages for this application. Created 10.03.2016
     *
     * @author Dominik Messerschmidt
     * (dominik.messerschmidt@continental-corporation.com)
     *
     */
    public enum Language
    {
        /**
         * English
         */
        EN("English"),

        /**
         * German
         */
        DE("Deutsch");

        private String name;

        Language(String name)
        {
            this.name = name;
        }

        public String getFileName()
        {
            return Constants.DATA_DIRECTORY + "lang_" + name().toLowerCase() + ".txt";
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    public enum MessageType
    {
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
    private static Language currentLanguage = Constants.DEFAULT_LANGUAGE;
    private static final Dictionary<String, String> DICTIONARY = new Dictionary<String, String>();

    private IO()
    {
    }

    /**
     * Initialize logfiles.
     */
    public static void initLogs()
    {
        if (Settings.getSettings().loggingEnabled)
        {
            try
            {
                if (logWriter != null)
                {
                    logWriter.close();
                }
                if (errorLogWriter != null)
                {
                    errorLogWriter.close();
                }
                java.io.File logDir = new java.io.File(Constants.LOGGING_DIRECTORY);
                if (!logDir.exists())
                {
                    logDir.mkdir();
                }
                logWriter = new java.io.PrintWriter(new java.io.File(Constants.LOGGING_DIRECTORY
                        + Constants.LOGGING_FILENAME));
                errorLogWriter = new java.io.PrintWriter(new java.io.File(
                        Constants.LOGGING_DIRECTORY + Constants.LOGGING_ERROR_FILENAME));
                IO.println("IO.initLogs(): Logging started", MessageType.DEBUG);
            } catch (Exception ex)
            {
                System.err.println("Failed to initialize logging: " + ex);
                ex.printStackTrace();
            }
        }
    }

    /**
     * Delete all files in logging directory.
     */
    public static void clearLogs()
    {
        // closeLogs();
        try
        {
            java.io.File logDir = new java.io.File(Constants.LOGGING_DIRECTORY);
            if (logDir.exists())
            {
                for (java.io.File logfile : logDir.listFiles())
                {
                    logfile.delete();
                }
            }
            IO.println("Logs cleared", IO.MessageType.DEBUG);
        } catch (Exception ex)
        {
            IO.println("failed to delete log files: " + ex, MessageType.ERROR);
            ex.printStackTrace();
        }
        // initLogs();
    }

    /**
     * Stops logging and closes logfiles.
     */
    public static void closeLogs()
    {
        if (logWriter != null)
        {
            logWriter.close();
        }
        if (errorLogWriter != null)
        {
            errorLogWriter.close();
        }
        IO.println("Logging stopped", MessageType.DEBUG);
    }

    /**
     * Prints a textline. The given MessageType defines where the String is
     * printed. See IO.MessageType for details.
     *
     * @param text a String to print
     * @param messagetype defines where the String is printed.
     */
    public static void println(String text, MessageType messagetype)
    {
        String preamble = preamble(messagetype);
        switch (messagetype)
        {
            case ERROR:
                System.err.println(text);
                writeToErrorLog(preamble + text);
                break;
            case IMPORTANT:
                MainFrame.FRAME.println(text);
            case NORMAL:
            // well there is actually no difference between normal and debug
            case DEBUG:
                System.out.println(text);
                // writeToLog(preamble + text);
                break;
        }
    }

    public static void promptDecision(Decision d, DecisionCallback callback)
    {
        d.setCallback(callback);
        MainFrame.FRAME.promptDecision(d);
    }

    /**
     * Returns a preamble for printed textlines. It is a String containing
     * current timestamp + [short message type] + >>.
     *
     * @param messagetype MessageType of printed text.
     * @return current Preamble String.
     */
    public static String preamble(MessageType messagetype)
    {
        return LocalDateTime.now().format(Constants.DATETIMEFORMAT) + "["
                + messagetype.name().charAt(0) + "]>> ";
    }

    /**
     * Appends the given text + cr + lf to the current logfile if logging is
     * enabled.
     *
     * @param text String to print.
     */
    public static void writeToLog(String text)
    {
        if (logWriter != null)
        {
            logWriter.append(text + "\r\n");
        }
    }

    /**
     * Appends the given text +cr +lf to the current error logfile if logging is
     * enabled.
     *
     * @param text String to print.
     */
    public static void writeToErrorLog(String text)
    {
        if (errorLogWriter != null)
        {
            errorLogWriter.append(text + "\r\n");
        }
    }

    /**
     * Prints a String representation of the given Exception as defined by the
     * MessageType ERROR and additionally prints the stack trace in the error
     * log if logging is enabled.
     *
     * @param ex
     */
    public static void printException(Exception ex)
    {
        println(ex.toString(), MessageType.ERROR);
        ex.printStackTrace();
        if (errorLogWriter != null)
        {
            ex.printStackTrace(errorLogWriter);
        }
    }

    public static void makeToast(String msg, MessageType mType)
    {
        int msgType = javax.swing.JOptionPane.PLAIN_MESSAGE;
        String header = "MESSAGE";
        if (null != mType)
        {
            switch (mType)
            {
                case ERROR:
                    msgType = javax.swing.JOptionPane.ERROR_MESSAGE;
                    header = "ERROR";
                    break;
                case IMPORTANT:
                    msgType = javax.swing.JOptionPane.WARNING_MESSAGE;
                    header = "WARNING";
                    break;
                case NORMAL:
                    msgType = javax.swing.JOptionPane.INFORMATION_MESSAGE;
                    header = "INFO";
                    break;
                default:
                    break;
            }
        }

        javax.swing.JOptionPane.showMessageDialog(MainFrame.FRAME, msg, translate(header), msgType);
        println(msg, mType);
    }

    /**
     * Returns the language currently set.
     *
     * @return current language
     */
    public static Language getCurrentLanguage()
    {
        return currentLanguage;
    }

    /**
     * Loads the file corresponding to the given language and sets it as current
     * language.
     *
     * @param lang language to switch to.
     * @return true if the language was switched succesfully, false otherwise.
     */
    public static boolean loadLanguageFile(Language lang)
    {
        try
        {
            java.io.File file = new java.io.File(lang.getFileName());
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            // reader.skip(1); // Skip SOF
            DICTIONARY.clear();
            while ((line = reader.readLine()) != null)
            {
                String name = line.substring(0, line.indexOf(" "));
                String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                DICTIONARY.add(name, value);
            }
            reader.close();
            currentLanguage = lang;
            IO.println("Language switched to " + currentLanguage.toString(), IO.MessageType.DEBUG);
            return true;
        } catch (Exception ex)
        {
            IO.println("failed to load Language: " + ex.toString(), IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    /**
     * Stores all text definitions of the current language in a file with the
     * given name. The language files are stored in data directory by default
     * and this is appended to the filename. The written data has the following
     * format: key + " = " + "value" + \n. So a value may changed by simply
     * editing the file and changing the text in "".
     *
     * @param filename name of the written file
     * @return true if it was succesful, false if an error occured.
     */
    private static boolean saveLanguageFile(String filename)
    {
        try
        {
            java.io.File file = new java.io.File(filename);
            java.io.PrintWriter writer = new java.io.PrintWriter(file);
            for (String key : DICTIONARY.getKeys())
            {
                writer.println(key + " = " + "\"" + DICTIONARY.get(key) + "\"");
            }
            writer.close();
            IO.println("saved language file " + filename, IO.MessageType.DEBUG);
            return true;
        } catch (Exception ex)
        {
            IO.println("failed to save Language: " + ex.toString(), IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    /**
     * Stores all text definitions of the current language in the file
     * associated with the current language. The language files are stored in
     * data directory by default.
     *
     * @return true if it was succesful, false if an error occured.
     */
    public static boolean saveLanguageFile()
    {
        return saveLanguageFile(currentLanguage.getFileName());
    }

    /**
     * Creates a corresponding file for each defined language. Does not override
     * existing files.
     */
    public static void createAllLanguageFiles()
    {
        for (Language lang : Language.values())
        {
            if (!new java.io.File(lang.getFileName()).exists())
            {
                saveLanguageFile(lang.getFileName());
            }
        }
    }

    /**
     * Retrieves a translation for the given key in the current language. If the
     * given text is not a key in the dictionary of the current language, the
     * text is returned unmodified.
     *
     * @param text key String to translate.
     * @return corresponding value in the current language or the given text if
     * no translation defined.
     */
    public static String translate(String text)
    {
        if (DICTIONARY.containsKey(text))
        {
            return DICTIONARY.get(text);
        }
        else
        {
            IO.println("IO.translate(): Added key: " + text, MessageType.DEBUG);
            DICTIONARY.add(text, text);
            return text;
        }
    }

    /**
     * Retrieves a translation for the given key in the current language. If the
     * given text is not a key in the dictionary of the current language, the
     * text is returned unmodified.
     *
     * @param key key String.
     * @param text translated String.
     */
    public static void setTranslation(String key, String text)
    {
        if (DICTIONARY.containsKey(text))
        {
            DICTIONARY.set(key, text);
        }
        else
        {
            if (DICTIONARY.add(key, text))
            {
                IO.println("IO.translate(): Added translation: " + key + " = " + text,
                        MessageType.DEBUG);
            }
        }
    }
}
