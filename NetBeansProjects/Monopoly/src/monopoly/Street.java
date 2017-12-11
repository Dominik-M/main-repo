/*
 * Copyright (C) 2017 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
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
package monopoly;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 14.02.2017
 */
public class Street extends Field
{

    private int color;
    private int houseCount;
    private int houseCost;

    public Street(String title, int value, int color, int baseCost, int houseCost)
    {
        super(Type.STREET, title, value, true);
        this.color = color;
        this.houseCost = houseCost;
        houseCount = 0;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public int getHouseCount()
    {
        return houseCount;
    }

    public void setHouseCount(int houseCount)
    {
        this.houseCount = houseCount;
    }

    public int getHouseCost()
    {
        return houseCost;
    }

    public void setHouseCost(int houseCost)
    {
        this.houseCost = houseCost;
    }

}
