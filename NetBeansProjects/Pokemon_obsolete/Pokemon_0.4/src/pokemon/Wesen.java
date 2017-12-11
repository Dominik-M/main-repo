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

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum Wesen {
  TAPFER("Geringerer Angriff, starke Verteidigung"),
  FORSCH("Weniger Angriff, höherer Spezialangriff"),
  SCHEU("Wenig Angriff, höhere Spezialverteidigung"),
  FLINK("Schwacher Angriff, hohe Initiative"),
  BRUTAL("Wenig Verteidigung, höhere Angriffswerte"),
  NAIV("Schwächere Verteidigung, hoher Spezialangriff"),
  MUTIG("Geringe Verteidigung, hohe Spezialverteidigung"),
  EIFRIG("Weniger Verteidigung, hohe Initiative"),
  HART("Weniger Spezialangriff, stärkerer Angriff"),
  ROBUST("Geringer Spezialangriff, hohe Verteidigung"),
  ZAGHAFT("Wenig Spezialangriff, hohe Spezialverteidigung"),
  FROH("Geringerer Spezialangriff, höhere Initiative"),
  HITZIG("Geringere Spezialverteidigung, hoher Angriff"),
  PFIFFIG("Wenig Spezialverteidigung, hohe Verteidigung"),
  FIES("Geringe Spezialverteidigung, hoher Spezialangriff"),
  LOCKER("Wenig Spezialverteidigung, höhere Initiative"),
  ERNST("Geringere Initiative, höherer Angriff"),
  KAUZIG("Wenig Initiative, starke Verteidigung"),
  STUR("Wenig Initiative, starker Spezialangriff"),
  RUHIG("Geringe Initiative, hohe Spezialverteidigung"),
  SANFT("Neutral"),
  MILD("Neutral"),
  SACHT("Neutral"),
  STILL("Neutral"),
  KÜHN("Hohe Spezialwerte"),
  STOLZ("Besonders hohe Werte");
    
  public final String tooltip;
  
  private Wesen(String s){
    tooltip=s;
  }
  
  public static Wesen gibZufallWesen(){
    int index=(int)(Math.random()*values().length);
    return values()[index];
  }
}
