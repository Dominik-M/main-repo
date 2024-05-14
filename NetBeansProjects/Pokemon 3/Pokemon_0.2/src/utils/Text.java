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
public enum Text
{

    /*
     ********************************
     * System Messages              *
     ********************************
     */
    /**
     * "Hallo Welt" - Hello World text.
     */
    HELLO("Hallo Welt"),
    /**
     * "----------" - If you don't know what to print, print this.
     */
    DEFAULT_TEXT("-------"),
    /**
     * "Diese Attacke hat keine AP mehr"
     */
    NO_AP("Diese Attacke hat keine AP mehr"),
    /**
     * "Änderungen Anwenden".
     */
    APPLY("Änderungen Anwenden"),
    /**
     * Constants.APP_NAME + " wirklich beenden?".
     */
    EXIT_QUESTION(Constants.APP_NAME + " wirklich beenden?"),
    /**
     * "Beenden bestätigen".
     */
    CONFIRM_EXIT("Beenden bestätigen"),
    /**
     * "Suche nach Sound Dateien...".
     */
    SEARCHING_SOUNDFILES("Suche nach Sound Dateien..."),
    /**
     * "Lade Sounds...".
     */
    LOADING_SOUNDFILES("Lade Sounds..."),
    /**
     * "Lade Bilder...".
     */
    LOADING_IMAGES("Lade Bilder..."),
    /**
     * "Fertig".
     */
    READY("Fertig"),
    /**
     * "Optionen".
     */
    OPTIONS("Optionen"),
    /**
     * "Sprache".
     */
    LANGUAGE("Sprache"),
    /**
     * "Eingabe konfigurieren".
     */
    INPUTCONFIG("Eingabe konfigurieren"),
    /**
     * "Logs löschen".
     */
    CLEARLOGS("Logs löschen"),
    /**
     * "Zurücksetzen".
     */
    RESET("Zurücksetzen"),
    /**
     * "Wahr".
     */
    TRUE("Wahr"),
    /**
     * "Falsch".
     */
    FALSE("Falsch"),
    /**
     * "Speichern".
     */
    SAVE("Speichern"),
    /**
     * "Spielstand gesichert".
     */
    SAVED("Spielstand gesichert"),
    /**
     * "Kein Item ausgewählt".
     */
    SELECTION_EMPTY("Kein Item ausgewählt"),
    /**
     * "Pokedex".
     */
    POKEDEX("Pokedex"),
    /**
     * "Pokemon".
     */
    POKEMON("Pokemon"),
    /**
     * "Items".
     */
    ITEMS("Items"),
    /**
     * "Zurück"
     */
    BACK("Zurück"),
    /**
     * "Wählen"
     */
    SELECT("Wählen"),
    /**
     * "Status"
     */
    STATUS("Status"),
    /**
     * "Tausch"
     */
    SWITCH("Tausch"),
    /*
     ********************************
     * Fight Messages               *
     ********************************
     */
    /**
     * "Herausforderung von ".
     */
    START_FIGHT("Herausforderung von "),
    /**
     * "Ein wildes ".
     */
    START_FIGHT_WILD("Ein wildes "),
    /**
     * "Gegn. ".
     */
    OPPONENTS("Gegn. "),
    /**
     * "KAMPF".
     */
    OPTION_ATTACK("KAMPF"),
    /**
     * "PKMN".
     */
    OPTION_PKMN("PKMN"),
    /**
     * "ITEM".
     */
    OPTION_ITEM("ITEM"),
    /**
     * "FLUCHT".
     */
    OPTION_FLEE("FLUCHT"),
    /**
     * "Auswahl abgebrochen".
     */
    SELECTION_FAILED("Auswahl abgebrochen"),
    /**
     * " ist geflüchtet".
     */
    FLEED(" ist geflüchtet"),
    /**
     * "Flucht nicht möglich"
     */
    CANNOT_FLEE("Flucht nicht möglich"),
    /**
     * " ist aufgewacht"
     */
    WAKEUP(" ist aufgewacht"),
    /**
     * " schläft tief und fest".
     */
    SLEEPING(" schläft tief und fest"),
    /**
     * " ist festgefroren".
     */
    FROZEN(" ist festgefroren"),
    /**
     * " ist paralysiert".
     */
    PARALYZED(" ist paralysiert"),
    /**
     * " benutzt ".
     */
    USES("benutzt"),
    /**
     * "Volltreffer!".
     */
    CRITICAL_HIT("Volltreffer!"),
    /**
     * "Das hat keine Wirkung auf ".
     */
    NO_EFFECT_ON("Das hat keine Wirkung auf "),
    /**
     * "Die Attacke geht daneben".
     */
    ATTACK_MISSED("Die Attacke geht daneben"),
    /**
     * "wurde besiegt".
     */
    POK_KO("wurde besiegt"),
    /**
     * "leidet an Vergiftung".
     */
    POISON_DAMAGE("leidet an Vergiftung"),
    /**
     * "leidet an Verbrennungen".
     */
    BURN_DAMAGE("leidet an Verbrennungen"),
    /**
     * "Komm zurück".
     */
    FIGHT_POK_RETREAT("Komm zurück"),
    /**
     * "Los".
     */
    GO("Los"),
    /**
     * "Du bist dran".
     */
    FIGHT_POK_SWITCH("Du bist dran"),
    /**
     * "erreicht Lvl"
     */
    LVL_UP("erreicht Lvl"),
    /**
     * "Das ist sehr effektiv!".
     */
    VERY_EFFECTIVE("Das ist sehr effektiv!"),
    /**
     * "Das ist nicht sehr effektiv...".
     */
    NOT_EFFECTIVE("Das ist nicht sehr effektiv..."),
    /*
     ********************************
     * Pokemon names                *
     ********************************
     */
    /**
     * "Bisasam".
     */
    BISASAM("Bisasam"),
    /**
     * "Bisaknosp".
     */
    BISAKNOSP("Bisaknosp"),
    /**
     * "Bisaflor".
     */
    BISAFLOR("Bisaflor"),
    /**
     * "Glumanda"
     */
    GLUMANDA("Glumanda"),
    /*
     ********************************
     * Attack names and descriptions*
     ********************************
     */
    /**
     * "Tackle".
     */
    TACKLE("Tackle"),
    /**
     * "Angriff mit vollem Körpereinsatz".
     */
    TACKLE_DESCR("Angriff mit vollem Körpereinsatz"),
    /**
     * "Kratzer".
     */
    KRATZER("Kratzer"),
    /**
     * "Angriff mit scharfen Klauen".
     */
    KRATZER_DESCR("Angriff mit scharfen Klauen"),
    /**
     * "Heuler".
     */
    HEULER("Heuler"),
    /**
     * "Der Anwender nimmt den Gegner für sich ein und senkt dessen
     * Angriffs-Wert.".
     */
    HEULER_DESCR("Der Anwender nimmt den Gegner für sich ein und senkt dessen Angriffs-Wert."),
    /**
     * "Glut".
     */
    GLUT("Glut"),
    /**
     * "Schwache Feuer-Attacke. Verbrennt Gegner evtl".
     */
    GLUT_DESCR("Schwache Feuer-Attacke. Verbrennt Gegner evtl"),
    /*
     ********************************
     * Effect Messages              *
     ********************************
     */
    /**
     * "Senkt den Angriffswert des Gegners".
     */
    ATK_DOWN("Senkt den Angriffswert des Gegners"),
    /**
     * "ATK wurde gesenkt".
     */
    ATK_DOWN_APPLIED("ATK wurde gesenkt"),
    /**
     * "Das Ziel erleidet evtl. Verbrennungen".
     */
    BURN("Das Ziel erleidet evtl. Verbrennungen"),
    /**
     * "Das hat keinen Effekt...".
     */
    NO_EFFECT("Das hat keinen Effekt..."),
    /*
     ********************************
     * Location names               *
     ********************************
     */
    /**
     * "Alabastia".
     */
    ALABASTIA("Alabastia"),
    /**
     * "Route 1".
     */
    ROUTE1("Route 1"),
    /**
     * "Vertania".
     */
    VERTANIA_CITY("Vertania"),
    /*
     **********************************
     * Ability names and descriptions *
     **********************************
     */
    /**
     * "Adlerauge"
     */
    ABILITY_ADLERAUGE("Adlerauge"),
    /**
     * "Aquahülle"
     */
    ABILITY_AQUAHÜLLE("Aquahülle"),
    /**
     * "Expidermis"
     */
    ABILITY_EXPIDERMIS("Expidermis"),
    /**
     * "Statik"
     */
    ABILITY_STATIK("Statik"),
    /**
     * "Immunität"
     */
    ABILITY_IMMUNITÄT("Immunität"),
    /**
     * "Feuerpanzer"
     */
    ABILITY_FEUERPANZER("Feuerpanzer"),
    /**
     * "Flexibilität"
     */
    ABILITY_FLEXIBILITÄT("Flexibilität"),
    /**
     * "Konzentration"
     */
    ABILITY_KONZENTRATION("Konzentration"),
    /**
     * "Munterkeit"
     */
    ABILITY_MUNTERKEIT("Munterkeit"),
    /**
     * "Schwebe"
     */
    ABILITY_SCHWEBE("Schwebe"),
    /**
     * "Wunderwache"
     */
    ABILITY_WUNDERWACHE("Wunderwache"),
    /**
     * "Notdünger"
     */
    ABILITY_NOTDÜNGER("Notdünger"),
    // Ability tooltips

    /**
     * "Verhindert Verlust von Genauigkeit".
     */
    ABILITY_TOOLTIP_ADLERAUGE("Verhindert Verlust von Genauigkeit"),
    /**
     * "Verhindert Verbrennung".
     */
    ABILITY_TOOLTIP_AQUAHÜLLE("Verhindert Verbrennung"),
    /**
     * "Heilt Statusprobleme".
     */
    ABILITY_TOOLTIP_EXPIDERMIS("Heilt Statusprobleme"),
    /**
     * "Paralysiert Gegner evtl. bei Berührung".
     */
    ABILITY_TOOLTIP_STATIK("Paralysiert Gegner evtl. bei Berührung"),
    /**
     * "Verhindert Vergiftung".
     */
    ABILITY_TOOLTIP_IMMUNITÄT("Verhindert Vergiftung"),
    /**
     * "Verhindert Einfrieren".
     */
    ABILITY_TOOLTIP_FEUERPANZER("Verhindert Einfrieren"),
    /**
     * "Verhindert Paralyse".
     */
    ABILITY_TOOLTIP_FLEXIBILITÄT("Verhindert Paralyse"),
    /**
     * "Verhindert Verwirrung".
     */
    ABILITY_TOOLTIP_KONZENTRATION("Verhindert Verwirrung"),
    /**
     * "Verhindert Einschlafen".
     */
    ABILITY_TOOLTIP_MUNTERKEIT("Verhindert Einschlafen"),
    /**
     * "Immunität gegen Boden-Attacken".
     */
    ABILITY_TOOLTIP_SCHWEBE("Immunität gegen Boden-Attacken"),
    /**
     * "Nur sehr effektive Attacken schaden".
     */
    ABILITY_TOOLTIP_WUNDERWACHE("Nur sehr effektive Attacken schaden"),
    /**
     * "Verstärkt Pflanzen-Attacken in Not".
     */
    ABILITY_TOOLTIP_NOTDÜNGER("Verstärkt Pflanzen-Attacken in Not"),
    /*
     **********************************
     * Wesen names and descriptions *
     **********************************
     */
    /**
     * "Tapfer"
     */
    WESEN_NAME_TAPFER("Tapfer"),
    /**
     * "Forsch"
     */
    WESEN_NAME_FORSCH("Forsch"),
    /**
     * "Scheu"
     */
    WESEN_NAME_SCHEU("Scheu"),
    /**
     * "Flink"
     */
    WESEN_NAME_FLINK("Flink"),
    /**
     * "Brutal"
     */
    WESEN_NAME_BRUTAL("Brutal"),
    /**
     * "Naiv"
     */
    WESEN_NAME_NAIV("Naiv"),
    /**
     * "Mutig"
     */
    WESEN_NAME_MUTIG("Mutig"),
    /**
     * "Eifrig"
     */
    WESEN_NAME_EIFRIG("Eifrig"),
    /**
     * "Hart"
     */
    WESEN_NAME_HART("Hart"),
    /**
     * "Robust"
     */
    WESEN_NAME_ROBUST("Robust"),
    /**
     * "Zaghaft"
     */
    WESEN_NAME_ZAGHAFT("Zaghaft"),
    /**
     * "Froh"
     */
    WESEN_NAME_FROH("Froh"),
    /**
     * "Hitzig"
     */
    WESEN_NAME_HITZIG("Hitzig"),
    /**
     * "Pfiffig"
     */
    WESEN_NAME_PFIFFIG("Pfiffig"),
    /**
     * "Fies"
     */
    WESEN_NAME_FIES("Fies"),
    /**
     * "Locker"
     */
    WESEN_NAME_LOCKER("Locker"),
    /**
     * "Ernst"
     */
    WESEN_NAME_ERNST("Ernst"),
    /**
     * "Kauzig"
     */
    WESEN_NAME_KAUZIG("Kauzig"),
    /**
     * "Stur"
     */
    WESEN_NAME_STUR("Stur"),
    /**
     * "Ruhig"
     */
    WESEN_NAME_RUHIG("Ruhig"),
    /**
     * "Sanft"
     */
    WESEN_NAME_SANFT("Sanft"),
    /**
     * "Mild"
     */
    WESEN_NAME_MILD("Mild"),
    /**
     * "Sacht"
     */
    WESEN_NAME_SACHT("Sacht"),
    /**
     * "Still"
     */
    WESEN_NAME_STILL("Still"),
    /**
     * "Kühn"
     */
    WESEN_NAME_KÜHN("Kühn"),
    /**
     * "Stolz"
     */
    WESEN_NAME_STOLZ("Stolz"),
    /**
     * "Geringerer Angriff, starke Verteidigung"
     */
    WESEN_TOOLTIP_TAPFER("Geringerer Angriff, starke Verteidigung"),
    /**
     * "Weniger Angriff, höherer Spezialangriff"
     */
    WESEN_TOOLTIP_FORSCH("Weniger Angriff, höherer Spezialangriff"),
    /**
     * "Wenig Angriff, höhere Spezialverteidigung"
     */
    WESEN_TOOLTIP_SCHEU("Wenig Angriff, höhere Spezialverteidigung"),
    /**
     * "Schwacher Angriff, hohe Initiative"
     */
    WESEN_TOOLTIP_FLINK("Schwacher Angriff, hohe Initiative"),
    /**
     * "Wenig Verteidigung, höhere Angriffswerte"
     */
    WESEN_TOOLTIP_BRUTAL("Wenig Verteidigung, höhere Angriffswerte"),
    /**
     * "Schwächere Verteidigung, hoher Spezialangriff"
     */
    WESEN_TOOLTIP_NAIV("Schwächere Verteidigung, hoher Spezialangriff"),
    /**
     * "Geringe Verteidigung, hohe Spezialverteidigung"
     */
    WESEN_TOOLTIP_MUTIG("Geringe Verteidigung, hohe Spezialverteidigung"),
    /**
     * "Weniger Verteidigung, hohe Initiative"
     */
    WESEN_TOOLTIP_EIFRIG("Weniger Verteidigung, hohe Initiative"),
    /**
     * "Weniger Spezialangriff, stärkerer Angriff"
     */
    WESEN_TOOLTIP_HART("Weniger Spezialangriff, stärkerer Angriff"),
    /**
     * "Geringer Spezialangriff, hohe Verteidigung"
     */
    WESEN_TOOLTIP_ROBUST("Geringer Spezialangriff, hohe Verteidigung"),
    /**
     * "Wenig Spezialangriff, hohe Spezialverteidigung"
     */
    WESEN_TOOLTIP_ZAGHAFT("Wenig Spezialangriff, hohe Spezialverteidigung"),
    /**
     * "Geringerer Spezialangriff, höhere Initiative"
     */
    WESEN_TOOLTIP_FROH("Geringerer Spezialangriff, höhere Initiative"),
    /**
     * "Geringere Spezialverteidigung, hoher Angriff"
     */
    WESEN_TOOLTIP_HITZIG("Geringere Spezialverteidigung, hoher Angriff"),
    /**
     * "Wenig Spezialverteidigung, hohe Verteidigung"
     */
    WESEN_TOOLTIP_PFIFFIG("Wenig Spezialverteidigung, hohe Verteidigung"),
    /**
     * "Geringe Spezialverteidigung, hoher Spezialangriff"
     */
    WESEN_TOOLTIP_FIES("Geringe Spezialverteidigung, hoher Spezialangriff"),
    /**
     * "Wenig Spezialverteidigung, höhere Initiative"
     */
    WESEN_TOOLTIP_LOCKER("Wenig Spezialverteidigung, höhere Initiative"),
    /**
     * "Geringere Initiative, höherer Angriff"
     */
    WESEN_TOOLTIP_ERNST("Geringere Initiative, höherer Angriff"),
    /**
     * "Wenig Initiative, starke Verteidigung"
     */
    WESEN_TOOLTIP_KAUZIG("Wenig Initiative, starke Verteidigung"),
    /**
     * "Wenig Initiative, starker Spezialangriff"
     */
    WESEN_TOOLTIP_STUR("Wenig Initiative, starker Spezialangriff"),
    /**
     * "Geringe Initiative, hohe Spezialverteidigung"
     */
    WESEN_TOOLTIP_RUHIG("Geringe Initiative, hohe Spezialverteidigung"),
    /**
     * "Neutral"
     */
    WESEN_TOOLTIP_SANFT("Neutral"),
    /**
     * "Neutral"
     */
    WESEN_TOOLTIP_MILD("Neutral"),
    /**
     * "Neutral"
     */
    WESEN_TOOLTIP_SACHT("Neutral"),
    /**
     * "Neutral"
     */
    WESEN_TOOLTIP_STILL("Neutral"),
    /**
     * "Hohe Spezialwerte"
     */
    WESEN_TOOLTIP_KÜHN("Hohe Spezialwerte"),
    /**
     * "Besonders hohe Werte"
     */
    WESEN_TOOLTIP_STOLZ("Besonders hohe Werte"),
    /*
     ********************************
     * Dialogues                    *
     ********************************
     */
    /**
     * "Hallo!".
     */
    DIALOG_PETER_1("Hallo!"),
    /**
     * "Wie gehts?".
     */
    DIALOG_PETER_2("Wie gehts?"),
    /**
     * "Die meisten Menschen sind sehr unhöflich und wollen dich mit ihren
     * Pokemon töten.".
     */
    DIALOG_PETER_3("Die meisten Menschen sind "
            + "sehr unhöflich und wollen dich mit ihren "
            + "Pokemon töten."),
    /**
     * "Sei auf der Hut!".
     */
    DIALOG_PETER_4("Sei auf der Hut!"),
    /*
     ********************************
     * Dynamic valus                *
     ********************************
     */
    /**
     * Players name.
     */
    PLAYER("")
    {
        @Override
        public String toString()
        {
            return GameData.getCurrentGame().getPlayer().toString();
        }
    },
    /**
     * Rivals name.
     */
    RIVALE("")
    {
        @Override
        public String toString()
        {
            return GameData.getCurrentGame().getRivale().toString();
        }
    },
    /**
     * PLAYER + "s Haus".
     */
    SIGN_PLAYERSHOUSE("s Haus")
    {
        @Override
        public String toString()
        {
            return PLAYER + super.toString();
        }
    },
    /**
     * RIVALE + "s Haus".
     */
    SIGN_RIVALSHOUSE("s Haus")
    {
        @Override
        public String toString()
        {
            return RIVALE + super.toString();
        }
    };

    private String text;

    Text(String txt)
    {
        text = txt;
    }

    @Override
    public String toString()
    {
        return text;
    }

    private static utils.Constants.Language currentLanguage = utils.Constants.DEFAULT_LANGUAGE;

    /**
     *
     * @return
     */
    public static utils.Constants.Language getCurrentLanguage()
    {
        return currentLanguage;
    }

    /**
     *
     * @param lang
     * @return
     */
    public static boolean switchLanguage(utils.Constants.Language lang)
    {
        try
        {
            java.io.File file = new java.io.File(lang.getFileName());
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            boolean[] found = new boolean[Text.values().length];
            for (int i = 0; i < found.length; i++)
            {
                found[i] = false;
            }
            // reader.skip(1); // Skip SOF
            while ((line = reader.readLine()) != null)
            {
                if (!line.contains(" "))
                {
                    continue;
                }
                String name = line.substring(0, line.indexOf(" "));
                String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                for (int i = 0; i < Text.values().length; i++)
                {
                    if (Text.values()[i].name().equals(name))
                    {
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
            for (int i = 0; i < found.length; i++)
            {
                if (!found[i])
                {
                    IO.println("Representation for " + Text.values()[i].name() + " not found in "
                            + file.getName(), IO.MessageType.ERROR);
                }
            }
            return true;
        } catch (Exception ex)
        {
            IO.println("failed to load Language: " + ex.toString(), IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    private static boolean saveLanguageFile(String filename)
    {
        try
        {
            java.io.File file = new java.io.File(filename);
            java.io.PrintWriter writer = new java.io.PrintWriter(file);
            for (Text t : Text.values())
            {
                writer.println(t.name() + " = " + "\"" + t.text + "\"");
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
     *
     * @return
     */
    public static boolean saveLanguageFile()
    {
        return saveLanguageFile(currentLanguage.getFileName());
    }

    /**
     *
     */
    public static void createAllLanguageFiles()
    {
        for (utils.Constants.Language lang : utils.Constants.Language.values())
        {
            if (!new java.io.File(lang.getFileName()).exists())
            {
                saveLanguageFile(lang.getFileName());
            }
        }
    }
}
