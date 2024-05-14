/*
 * Copyright (C) 2015 Dundun
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
package graphic;

/**
 *
 * @author Dundun
 */
public class Delay {
    public final double MS; // delay in milliseconds
    public final double SECONDS;
    
    public Delay(){
      throw new java.lang.IllegalAccessError("Not allowed to instanciate Delay here.");
    }
    
    Delay(double millis){
        MS=millis;
        SECONDS=millis/1000;
    }
}
