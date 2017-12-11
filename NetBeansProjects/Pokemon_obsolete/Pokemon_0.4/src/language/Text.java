/*
 * Copyright (C) 2016 Dominik
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
package language;

import spiel.IO;
import spiel.Spielwelt;

/**
 *
 * @author Dominik
 */
public enum Text {

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
    // Dialogs
    DIALOG_PETER_1("Hallo!"),
    DIALOG_PETER_2("Wie gehts?"),
    DIALOG_PETER_3("Die meisten Menschen sind "
            + "sehr unhöflich und wollen dich mit ihren "
            + "Pokemon töten."),
    DIALOG_PETER_4("Sei auf der Hut!"),
    // Signs
    SIGN_PLAYERSHOUSE("s Haus") {
                @Override
                public String toString() {
                    return Spielwelt.getCurrentWorld().getSpieler().toString() + super.toString();
                }
            },
    SIGN_RIVALSHOUSE("s Haus") {
                @Override
                public String toString() {
                    return Spielwelt.getCurrentWorld().getRivale().toString() + super.toString();
                }
            };

    public static final java.io.File LANG_FILE_DE = new java.io.File("lang_DE.txt"),
            LANG_FILE_EN = new java.io.File("lang_EN.txt");

    private String value;

    Text(String text) {
        value = text;
    }

    void setValue(String text) {
        value = text;
    }

    @Override
    public String toString() {
        return value;
    }

    public static void printValues() {
        for (Text t : Text.values()) {
            IO.IOMANAGER.println(t.name() + " = " + "\"" + t.value + "\"", IO.MessageType.NORMAL);
        }
    }

    public static void writeLanguageFile(java.io.File file) throws java.io.FileNotFoundException {
        java.io.PrintWriter writer = new java.io.PrintWriter(file);
        for (Text t : Text.values()) {
            writer.println(t.name() + " = " + "\"" + t.value + "\"");
        }
        writer.close();
    }

    public static void readLanguageFile(java.io.File file) throws java.io.IOException {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
        String line;
        reader.skip(1); // Skip SOF
        while ((line = reader.readLine()) != null) {
            String name = line.substring(0, line.indexOf(" "));
            String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
            for (Text t : Text.values()) {
                if (t.name().equals(name)) {
                    t.setValue(value);
                    break;
                }
            }
        }
        reader.close();
    }
}
