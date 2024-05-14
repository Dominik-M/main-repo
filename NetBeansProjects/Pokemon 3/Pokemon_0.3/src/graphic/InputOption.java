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
package graphic;

import java.awt.Graphics;
import java.awt.Rectangle;
import utils.Constants;
import utils.IO;
import utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 30.08.2016
 */
public class InputOption
{

    private static int lastID = 0;
    private String txt;
    public final int ID;
    public final Rectangle BOUNDS;
    private InputOption upper, lower, next, prev;
    private boolean selected;

    /**
     * Selectable menu options.
     *
     */
    InputOption(String text, Rectangle bounds)
    {
        txt = text;
        BOUNDS = bounds;
        ID = lastID++;
    }

    @Override
    public String toString()
    {
        return IO.translate(txt);
    }

    public void setText(String text)
    {
        txt = text;
    }

    public InputOption getPrev()
    {
        if (prev != null)
        {
            return prev;
        }
        else
        {
            return this;
        }
    }

    public InputOption getNext()
    {
        if (next != null)
        {
            return next;
        }
        else
        {
            return this;
        }
    }

    public InputOption getUpper()
    {
        if (upper != null)
        {
            return upper;
        }
        else
        {
            return this;
        }
    }

    public InputOption getLower()
    {
        if (lower != null)
        {
            return lower;
        }
        else
        {
            return this;
        }
    }

    public void setUpper(InputOption upper)
    {
        this.upper = upper;
    }

    public void setLower(InputOption lower)
    {
        this.lower = lower;
    }

    public void setNext(InputOption next)
    {
        this.next = next;
    }

    public void setPrev(InputOption prev)
    {
        this.prev = prev;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public void draw(Graphics g)
    {
        g.setFont(Settings.getSettings().font);
        g.setColor(Settings.getSettings().fontColor);
        g.drawString(toString(), BOUNDS.x + Constants.SELECTION_MARKER_SIZE + 2, BOUNDS.y + 2 * BOUNDS.height / 3);
        if (selected)
        {
            int cY = BOUNDS.y + BOUNDS.height / 2;
            g.fillPolygon(new java.awt.Polygon(new int[]
            {
                BOUNDS.x, BOUNDS.x, BOUNDS.x + Constants.SELECTION_MARKER_SIZE
            }, new int[]
            {
                cY + Constants.SELECTION_MARKER_SIZE / 2, cY - Constants.SELECTION_MARKER_SIZE / 2, cY
            }, 3));
        }
    }
}
