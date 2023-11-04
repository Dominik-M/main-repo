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
package actors;

import main.Item;
import main.SpaceInvader;
import platform.gamegrid.Actor;
import platform.utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 23.02.2017
 */
public class ItemObject extends Actor
{

    private Item[] items;

    public ItemObject(Item... items)
    {
        super("cargoBox.png");
        this.items = items;
    }

    @Override
    protected void act()
    {
        if (!this.isInvalid() && SpaceInvader.getInstance().getMShip().getBounds().contains(this.getBounds()))
        {
            // collect
            for (Item item : items)
            {
                SpaceInvader.getInstance().addLoadedItem(item);
                IO.println(IO.translate("GOTITEM") + " " + item.getName() + " x " + item.getNumber(), IO.MessageType.IMPORTANT);
            }
            invalidate();
        }
    }

    public Item[] getItems()
    {
        return items;
    }

    public void setItems(Item[] items)
    {
        this.items = items;
    }

}
