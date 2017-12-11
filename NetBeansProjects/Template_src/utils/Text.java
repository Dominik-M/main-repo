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

import pokemon.GameData;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public enum Text {

    HELLO("Hallo Welt"),
    APPLY("Änderungen Anwenden"),
    EXIT_QUESTION(Constants.APP_NAME + " wirklich beenden?"),
    CONFIRM_EXIT("Beenden bestätigen"),
    SEARCHING_SOUNDFILES("Suche nach Sound Dateien..."),
    LOADING_SOUNDFILES("Lade Sounds..."),
    LOADING_IMAGES("Lade Bilder..."),
    READY("Fertig"),
    OPTIONS("Optionen"),
    LANGUAGE("Sprache"),
    INPUTCONFIG("Eingabe konfigurieren"),
    CLEARLOGS("Logs löschen"),
    RESET("Zurücksetzen"),
    TRUE("Wahr"),
    FALSE("Falsch"),
    SAVED("Spielstand gesichert"),
    //Pokemon
    BISASAM("Bisasam"),
    BISAKNOSP("Bisaknosp"),
    BISAFLOR("Bisaflor"),
    // Orte
    ALABASTIA("Alabastia"),
    ROUTE1("Route 1"),
    VERTANIA_CITY("Vertania"),
    // Ablilities
    ABILITY_ADLERAUGE("Adlerauge"),
    ABILITY_AQUAHÜLLE("Aquahülle"),
    ABILITY_EXPIDERMIS("Expidermis"),
    ABILITY_STATIK("Statik"),
    ABILITY_IMMUNITÄT("Immunität"),
    ABILITY_FEUERPANZER("Feuerpanzer"),
    ABILITY_FLEXIBILITÄT("Flexibilität"),
    ABILITY_KONZENTRATION("Konzentration"),
    ABILITY_MUNTERKEIT("Munterkeit"),
    ABILITY_SCHWEBE("Schwebe"),
    ABILITY_WUNDERWACHE("Wunderwache"),
    ABILITY_NOTDÜNGER("Notdünger"),
    // Ability tooltips
    ABILITY_TOOLTIP_ADLERAUGE("Verhindert Verlust von Genauigkeit"),
    ABILITY_TOOLTIP_AQUAHÜLLE("Verhindert Verbrennung"),
    ABILITY_TOOLTIP_EXPIDERMIS("Heilt Statusprobleme"),
    ABILITY_TOOLTIP_STATIK("Paralysiert Gegner evtl. bei Berührung"),
    ABILITY_TOOLTIP_IMMUNITÄT("Verhindert Vergiftung"),
    ABILITY_TOOLTIP_FEUERPANZER("Verhindert Einfrieren"),
    ABILITY_TOOLTIP_FLEXIBILITÄT("Verhindert Paralyse"),
    ABILITY_TOOLTIP_KONZENTRATION("Verhindert Verwirrung"),
    ABILITY_TOOLTIP_MUNTERKEIT("Verhindert Einschlafen"),
    ABILITY_TOOLTIP_SCHWEBE("Immunität gegen Boden-Attacken"),
    ABILITY_TOOLTIP_WUNDERWACHE("Nur sehr effektive Attacken schaden"),
    ABILITY_TOOLTIP_NOTDÜNGER("Verstärkt Pflanzen-Attacken in Not"),

    // Wesens
    // Attacks
    TACKLE("Tackle"),
    // Attack descriptions
    TACKLE_DESCR("Basisangriff"),
    // Dialogs
    DIALOG_PETER_1("Hallo!"),
    DIALOG_PETER_2("Wie gehts?"),
    DIALOG_PETER_3("Die meisten Menschen sind "
            + "sehr unhöflich und wollen dich mit ihren "
            + "Pokemon töten."),
    DIALOG_PETER_4("Sei auf der Hut!"),
    SIGN_PLAYERSHOUSE("s Haus") {
        @Override
        public String toString() {
            return GameData.getCurrentGame().getSpieler().toString() + super.toString();
        }
    },
    SIGN_RIVALSHOUSE("s Haus") {
        @Override
        public String toString() {
            return GameData.getCurrentGame().getRivale().toString() + super.toString();
        }
    };

    private String text;

    Text(String txt) {
        text = txt;
    }

    @Override
    public String toString() {
        return text;
    }

    private static utils.Constants.Language currentLanguage = utils.Constants.DEFAULT_LANGUAGE;

    public static utils.Constants.Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static boolean switchLanguage(utils.Constants.Language lang) {
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
            graphic.MainFrame.FRAME.repaintLabels();
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
        for (utils.Constants.Language lang : utils.Constants.Language.values()) {
            if (!new java.io.File(lang.getFileName()).exists()) {
                saveLanguageFile(lang.getFileName());
            }
        }
    }
}
