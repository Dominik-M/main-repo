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
package pokemon;

import utils.IO;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum Ability
{

    /**
     * "Verhindert Verlust von Genauigkeit".
     */
    ADLERAUGE("ABILITY_TOOLTIP_ADLERAUGE"),
    /**
     * "Verhindert Verbrennung".
     */
    AQUAHÜLLE("ABILITY_TOOLTIP_AQUAHÜLLE"),
    /**
     * "Heilt Statusprobleme".
     */
    EXPIDERMIS("ABILITY_TOOLTIP_EXPIDERMIS"),
    /**
     * "Paralysiert Gegner evtl. bei Berührung".
     */
    STATIK("ABILITY_TOOLTIP_STATIK"),
    /**
     * "Verhindert Vergiftung".
     */
    IMMUNITÄT("ABILITY_TOOLTIP_IMMUNITÄT"),
    /**
     * "Verhindert Einfrieren".
     */
    FEUERPANZER("ABILITY_TOOLTIP_FEUERPANZER"),
    /**
     * "Verhindert Paralyse".
     */
    FLEXIBILITÄT("ABILITY_TOOLTIP_FLEXIBILITÄT"),
    /**
     * "Verhindert Verwirrung".
     */
    KONZENTRATION("ABILITY_TOOLTIP_KONZENTRATION"),
    /**
     * "Verhindert Einschlafen".
     */
    MUNTERKEIT("ABILITY_TOOLTIP_MUNTERKEIT"),
    /**
     * "Immunität gegen Boden-Attacken".
     */
    SCHWEBE("ABILITY_TOOLTIP_SCHWEBE"),
    /**
     * "Nur sehr effektive Attacken schaden".
     */
    WUNDERWACHE("ABILITY_TOOLTIP_WUNDERWACHE"),
    /**
     * "Verstärkt Pflanzen-Attacken in Not".
     */
    NOTDÜNGER("ABILITY_TOOLTIP_NOTDÜNGER"); // unter 1/3 der maxKP um 50% stärker
    //TODO add abilities

    /**
     *
     */
    public final String TOOLTIP;

    Ability(String beschreibung)
    {
        TOOLTIP = beschreibung;
    }

    @Override
    public String toString()
    {
        return IO.translate(name());
    }

    public String getTooltip()
    {
        return IO.translate(TOOLTIP);
    }
}
