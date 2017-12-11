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

import language.Text;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum Ability {

    ADLERAUGE(Text.ABILITY_TOOLTIP_ADLERAUGE),
    AQUAHÜLLE(Text.ABILITY_TOOLTIP_AQUAHÜLLE),
    EXPIDERMIS(Text.ABILITY_TOOLTIP_EXPIDERMIS),
    STATIK(Text.ABILITY_TOOLTIP_STATIK),
    IMMUNITÄT(Text.ABILITY_TOOLTIP_IMMUNITÄT),
    FEUERPANZER(Text.ABILITY_TOOLTIP_FEUERPANZER),
    FLEXIBILITÄT(Text.ABILITY_TOOLTIP_FLEXIBILITÄT),
    KONZENTRATION(Text.ABILITY_TOOLTIP_KONZENTRATION),
    MUNTERKEIT(Text.ABILITY_TOOLTIP_MUNTERKEIT),
    SCHWEBE(Text.ABILITY_TOOLTIP_SCHWEBE),
    WUNDERWACHE(Text.ABILITY_TOOLTIP_WUNDERWACHE),
    NOTDÜNGER(Text.ABILITY_TOOLTIP_NOTDÜNGER); // unter 1/3 der maxKP um 50% stärker

    public final Text TOOLTIP;

    Ability(Text beschreibung) {
        TOOLTIP = beschreibung;
    }
}
