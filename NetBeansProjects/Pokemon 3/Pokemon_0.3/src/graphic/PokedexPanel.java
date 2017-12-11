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
package graphic;

import static graphic.MainPanel.drawFrame;
import static graphic.MainPanel.drawPokStatusBar;
import image.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import pokemon.PokemonBasis;
import utils.Constants;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 19.05.2017
 */
public class PokedexPanel extends MainPanel
{

    private final int MAX_INDEX = PokemonBasis.values().length, NUM_ROWS = 4;
    private final MainPanel parent;
    private int selectedIndex = 0, firstIndex = 0;

    public PokedexPanel(MainPanel parent)
    {
        this.parent = parent;
    }

    @Override
    protected void drawGUI(Graphics2D g)
    {
        for (int i = firstIndex; i < firstIndex + NUM_ROWS; i++)
        {
            int height = Constants.GRAPHIC_HEIGHT / NUM_ROWS;
            Rectangle bounds = new Rectangle(0, height * (i - firstIndex), Constants.GRAPHIC_WIDTH, height);
            if (selectedIndex == i)
            {
                drawFrame(g, bounds);
            }
            g.setColor(Color.BLACK);
            if (i == MAX_INDEX)
            {
                g.drawString(IO.translate("BACK"), 10, bounds.y + 20);
            }
            else
            {
                PokemonBasis pok = PokemonBasis.values()[i];
                g.drawString(pok.toString(), 10, bounds.y + 20);
            }
        }
    }

    private void close()
    {
        MainFrame.FRAME.setMainPanel(parent);
    }

    @Override
    public boolean keyPressed(InputConfig.Key key)
    {
        switch (key)
        {
            case A:
                if (selectedIndex == MAX_INDEX)
                {
                    close();
                }
                break;
            case B:
                close();
                break;
            case DOWN:
                if (selectedIndex + 1 <= MAX_INDEX)
                {
                    selectedIndex++;
                    if (firstIndex + NUM_ROWS <= selectedIndex)
                    {
                        firstIndex++;
                    }
                }

                break;
            case UP:
                if (selectedIndex > 0)
                {
                    selectedIndex--;
                    if (firstIndex > selectedIndex)
                    {
                        firstIndex--;
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void keyReleased(InputConfig.Key key)
    {

    }
}
