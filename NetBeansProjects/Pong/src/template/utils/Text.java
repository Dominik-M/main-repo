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

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public enum Text {

    HELLO("Hello World"),
    APPLY("Apply changes"),
    EXIT_QUESTION("Exit " + Constants.APP_NAME + "?"),
    CONFIRM_EXIT("Confirm exit"),
    SEARCHING_SOUNDFILES("Searching Soundfiles..."),
    LOADING_SOUNDFILES("Loading Sounds..."),
    LOADING_IMAGES("Loading Images..."),
    READY("Ready"),
    OPTIONS("Options"),
    LANGUAGE("Language"),
    INPUTCONFIG("Input Config"),
    CLEARLOGS("Clear Logs"),
    RESET("Reset"),
    PAUSED("PAUSED"),
    SAVED("Gamestate saved"),
    PONG_SCORED("Pong scored"),
    PING_SCORED("Ping scored");

    private String text;

    Text(String txt) {
        text = txt;
    }

    @Override
    public String toString() {
        return text;
    }

    private static template.utils.Constants.Language currentLanguage = template.utils.Constants.DEFAULT_LANGUAGE;

    public static template.utils.Constants.Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static boolean switchLanguage(template.utils.Constants.Language lang) {
        try {
            java.io.File file = new java.io.File(lang.getFileName());
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            boolean[] found = new boolean[Text.values().length];
            for (int i = 0; i < found.length; i++) {
                found[i] = false;
            }
            // reader.skip(1); // Skip SOF
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(" ") < 0) {
                    continue;
                }
                String name = line.substring(0, line.indexOf(" "));
                String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                for (int i = 0; i < Text.values().length; i++) {
                    if (Text.values()[i].name().equals(name)) {
                        Text.values()[i].text = value;
                        found[i] = true;
                        break;
                    }
                }
            }
            reader.close();
            currentLanguage = lang;
            template.graphic.MainFrame.FRAME.repaintLabels();
            IO.println("Language switched to " + currentLanguage.toString(), IO.MessageType.DEBUG);
            for (int i = 0; i < found.length; i++) {
                if (!found[i]) {
                    IO.println("Representation for " + Text.values()[i].name() + " not found in "
                            + file.getName(), IO.MessageType.ERROR);
                }
            }
            return true;
        } catch (Exception ex) {
            IO.println("failed to load Language: " + ex.toString(), IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    private static boolean saveLanguageFile(String filename) {
        try {
            java.io.File file = new java.io.File(filename);
            java.io.PrintWriter writer = new java.io.PrintWriter(file);
            for (Text t : Text.values()) {
                writer.println(t.name() + " = " + "\"" + t.text + "\"");
            }
            writer.close();
            IO.println("saved language file " + filename, IO.MessageType.DEBUG);
            return true;
        } catch (Exception ex) {
            IO.println("failed to save Language: " + ex.toString(), IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    public static boolean saveLanguageFile() {
        return saveLanguageFile(currentLanguage.getFileName());
    }

    public static void createAllLanguageFiles() {
        for (template.utils.Constants.Language lang : template.utils.Constants.Language.values()) {
            if (!new java.io.File(lang.getFileName()).exists()) {
                saveLanguageFile(lang.getFileName());
            }
        }
    }
}
