/**
 * Copyright (C) 2017 Dominik Messerschmidt
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
package monopoly;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 14.02.2017
 */
public class Field implements java.io.Serializable
{

    public enum Type
    {
        GO,
        STREET,
        PLANT,
        STATION,
        EVENT,
        COMMUNITY,
        TAXES,
        PRISON,
        GOTOPRISON,
        FREEPARKING;
    }

    private static int idcounter = 0;

    public final int ID;
    public final Type TYPE;
    private String title;
    private int value;
    public final boolean BUYABLE;
    private Player owner;

    public Field(Type type, String title, int value, boolean buyable)
    {
        ID = idcounter++;
        TYPE = type;
        this.title = title;
        this.value = value;
        this.BUYABLE = buyable;
        owner = null;
        System.out.println("new Field(): ID = " + ID + " type = " + TYPE + " title = " + title + " value = " + value);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public boolean isBuyable()
    {
        return BUYABLE;
    }

    public boolean hasOwner()
    {
        return owner != null;
    }

    public void setOwner(Player p)
    {
        if (BUYABLE)
        {
            this.owner = p;
            System.out.println(this + ".setOwner(): set to " + p);
        }
        else
        {
            System.out.println(this + ".setOwner(): is not buyable!");
        }
    }
}
