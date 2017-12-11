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
package utils;

import graphic.InputConfig;
import graphic.MainFrame;
import java.awt.Rectangle;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 31.05.2017
 */
public class Decision
{

    public static final Decision YES_NO = new Decision(
            new Rectangle(Constants.GRAPHIC_WIDTH * 2 / 3, Constants.OUTPUT_BOUNDS.y - Constants.GRAPHIC_HEIGHT / 4, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 4),
            "YES", "NO");
    public static final int INVALID_INDEX = -1;
    private Rectangle bounds;
    private String[] texts;
    private int rows, cols, selectedIndex;
    private DecisionCallback callback;
    private final Runnable CALLBACK_HANDLER = new Runnable()
    {
        @Override
        public void run()
        {
            if (callback != null)
            {
                callback.decisionEntered(selectedIndex);
                callback = null;
            }
        }
    };

    public Decision(Rectangle bounds, String... values)
    {
        this(bounds, values.length, 1, values);
    }

    public Decision(Rectangle bounds, int rows, int cols, String... values)
    {
        this.bounds = bounds;
        this.rows = rows;
        this.cols = cols;
        texts = values;
        selectedIndex = 0;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    public String[] getTexts()
    {
        return texts;
    }

    public void setTexts(String[] texts)
    {
        this.texts = texts;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

    public int getCols()
    {
        return cols;
    }

    public void setCols(int cols)
    {
        this.cols = cols;
    }

    public DecisionCallback getCallback()
    {
        return callback;
    }

    public void setCallback(DecisionCallback callback)
    {
        this.callback = callback;
    }

    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    public boolean keyPressed(InputConfig.Key key)
    {
        switch (key)
        {
            case B:
                selectedIndex = INVALID_INDEX;
            case A:
                MainFrame.FRAME.promptDecision(null);
                new Thread(CALLBACK_HANDLER).start();
                return true;
            case UP:
                if (selectedIndex - cols >= 0)
                {
                    selectedIndex -= cols;
                }
                break;
            case DOWN:
                if (selectedIndex + cols < texts.length)
                {
                    selectedIndex += cols;
                }
                break;
            case LEFT:
                if (selectedIndex - 1 >= 0)
                {
                    selectedIndex -= 1;
                }
                break;
            case RIGHT:
                if (selectedIndex + 1 < texts.length)
                {
                    selectedIndex += 1;
                }
                break;
        }
        return false;
    }
}
