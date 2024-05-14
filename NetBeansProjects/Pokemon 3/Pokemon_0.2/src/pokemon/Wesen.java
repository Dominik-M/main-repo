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

import utils.Text;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum Wesen {

    /**
     * "Geringerer Angriff, starke Verteidigung".
     */
    TAPFER(Text.WESEN_NAME_TAPFER, Text.WESEN_TOOLTIP_TAPFER, -1, 1, 0, 0, 0),
    /**
     * "Weniger Angriff, höherer Spezialangriff".
     */
    FORSCH(Text.WESEN_NAME_FORSCH, Text.WESEN_TOOLTIP_FORSCH, -1, 0, 1, 0, 0),
    /**
     * "Wenig Angriff, höhere Spezialverteidigung".
     */
    SCHEU(Text.WESEN_NAME_SCHEU, Text.WESEN_TOOLTIP_SCHEU, -1, 0, 0, 1, 0),
    /**
     * "Schwacher Angriff, hohe Initiative".
     */
    FLINK(Text.WESEN_NAME_FLINK, Text.WESEN_TOOLTIP_FLINK, -1, 0, 0, 0, 1),
    /**
     * "Wenig Verteidigung, höhere Angriffswerte".
     */
    BRUTAL(Text.WESEN_NAME_BRUTAL, Text.WESEN_TOOLTIP_BRUTAL, 1, -1, 0, 0, 0),
    /**
     * "Schwächere Verteidigung, hoher Spezialangriff".
     */
    NAIV(Text.WESEN_NAME_NAIV, Text.WESEN_TOOLTIP_NAIV, 0, -1, 1, 0, 0),
    /**
     * "Geringe Verteidigung, hohe Spezialverteidigung".
     */
    MUTIG(Text.WESEN_NAME_MUTIG, Text.WESEN_TOOLTIP_MUTIG, 0, -1, 0, 1, 0),
    /**
     * "Weniger Verteidigung, hohe Initiative".
     */
    EIFRIG(Text.WESEN_NAME_EIFRIG, Text.WESEN_TOOLTIP_EIFRIG, 0, -1, 0, 0, 1),
    /**
     * "Weniger Spezialangriff, stärkerer Angriff".
     */
    HART(Text.WESEN_NAME_HART, Text.WESEN_TOOLTIP_HART, 1, 0, -1, 0, 0),
    /**
     * "Geringer Spezialangriff, hohe Verteidigung".
     */
    ROBUST(Text.WESEN_NAME_ROBUST, Text.WESEN_TOOLTIP_ROBUST, 0, 1, -1, 0, 0),
    /**
     * "Wenig Spezialangriff, hohe Spezialverteidigung".
     */
    ZAGHAFT(Text.WESEN_NAME_ZAGHAFT, Text.WESEN_TOOLTIP_ZAGHAFT, 0, 0, -1, 1, 0),
    /**
     * "Geringerer Spezialangriff, höhere Initiative".
     */
    FROH(Text.WESEN_NAME_FROH, Text.WESEN_TOOLTIP_FROH, 0, 0, -1, 0, 1),
    /**
     * "Geringere Spezialverteidigung, hoher Angriff".
     */
    HITZIG(Text.WESEN_NAME_HITZIG, Text.WESEN_TOOLTIP_HITZIG, 1, 0, 0, -1, 0),
    /**
     * "Wenig Spezialverteidigung, hohe Verteidigung".
     */
    PFIFFIG(Text.WESEN_NAME_PFIFFIG, Text.WESEN_TOOLTIP_PFIFFIG, 0, 1, 0, -1, 0),
    /**
     * "Geringe Spezialverteidigung, hoher Spezialangriff".
     */
    FIES(Text.WESEN_NAME_FIES, Text.WESEN_TOOLTIP_FIES, 0, 0, 1, -1, 0),
    /**
     * "Wenig Spezialverteidigung, höhere Initiative".
     */
    LOCKER(Text.WESEN_NAME_LOCKER, Text.WESEN_TOOLTIP_LOCKER, 0, 0, 0, -1, 1),
    /**
     * "Geringere Initiative, höherer Angriff".
     */
    ERNST(Text.WESEN_NAME_ERNST, Text.WESEN_TOOLTIP_ERNST, 1, 0, 0, 0, -1),
    /**
     * "Wenig Initiative, starke Verteidigung".
     */
    KAUZIG(Text.WESEN_NAME_KAUZIG, Text.WESEN_TOOLTIP_KAUZIG, 0, 1, 0, 0, -1),
    /**
     * "Wenig Initiative, starker Spezialangriff".
     */
    STUR(Text.WESEN_NAME_STUR, Text.WESEN_TOOLTIP_STUR, 0, 0, 1, 0, -1),
    /**
     * "Geringe Initiative, hohe Spezialverteidigung".
     */
    RUHIG(Text.WESEN_NAME_RUHIG, Text.WESEN_TOOLTIP_RUHIG, -1, 0, 0, 1, 0),
    /**
     * "Neutral".
     */
    SANFT(Text.WESEN_NAME_SANFT, Text.WESEN_TOOLTIP_SANFT, 0, 0, 0, 0, 0),
    /**
     * "Neutral".
     */
    MILD(Text.WESEN_NAME_MILD, Text.WESEN_TOOLTIP_MILD, 0, 0, 0, 0, 0),
    /**
     * "Neutral".
     */
    SACHT(Text.WESEN_NAME_SACHT, Text.WESEN_TOOLTIP_SACHT, 0, 0, 0, 0, 0),
    /**
     * "Neutral".
     */
    STILL(Text.WESEN_NAME_STILL, Text.WESEN_TOOLTIP_STILL, 0, 0, 0, 0, 0),
    /**
     * "Hohe Spezialwerte".
     */
    KÜHN(Text.WESEN_NAME_KÜHN, Text.WESEN_TOOLTIP_KÜHN, 0, 0, 1, 1, 0),
    /**
     * "Besonders hohe Werte".
     */
    STOLZ(Text.WESEN_NAME_STOLZ, Text.WESEN_TOOLTIP_STOLZ, 1, 1, 1, 1, 1);

    public final Text tooltip;
    public final Text name;
    private final int[] statEffects;

    private Wesen(Text name, Text descr, int atkEffect, int vertEffect, int spezAtkEffect, int spezVertEffect, int initEffect) {
        this.name = name;
        this.tooltip = descr;
        statEffects = new int[]{atkEffect, vertEffect, spezAtkEffect, spezVertEffect, initEffect};
    }

    @Override
    public String toString() {
        return name.toString();
    }

    public double getFactor(Pokemon.Stat stat) {
        int val = statEffects[stat.ordinal()];
        if (val < 0) {
            return 0.9;
        }
        if (val > 0) {
            return 1.1;
        }
        return 1;
    }

    /**
     *
     * @return
     */
    public static Wesen gibZufallWesen() {
        int index = (int) (Math.random() * values().length);
        return values()[index];
    }
}
