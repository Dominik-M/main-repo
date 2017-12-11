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

import image.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import pokemon.GameData;
import pokemon.Pokemon;
import pokemon.Spieler;
import pokemon.Trainer;
import utils.Constants;

/**
 * Displays an overview of Pokemons of a Trainer. Provides functions for Pokemon
 * selections e.g. for switch in fight or item use.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 13.05.2017
 */
public class PokemonStatusPanel extends MainPanel
{

    private final Trainer owner;
    private final Pokemon pok;
    private final MainPanel parent;
    private int selectedIndex = 0;

    public PokemonStatusPanel(MainPanel parent)
    {
        this(parent, GameData.getCurrentGame().getPlayer(), GameData.getCurrentGame().getPlayer().getFirst());
    }

    public PokemonStatusPanel(MainPanel parent, Trainer owner, Pokemon pok)
    {
        this.owner = owner;
        this.parent = parent;
        this.pok = pok;
    }

    @Override
    protected void drawGUI(Graphics2D g)
    {
        if (pok != null)
        {
            drawFrame(g, new Rectangle(0, 0, Constants.GRAPHIC_WIDTH, Constants.GRAPHIC_HEIGHT));
            int height = Constants.GRAPHIC_HEIGHT / 4;
            g.drawImage(ImageIO.getSprite(pok.getBasis().getBaseName().toLowerCase() + "Icon.png").getImage().getScaledInstance(height, height, 0), 0, 0, null);
            drawPokStatusBar(g, pok, new Rectangle(Constants.POK_SPRITE_WIDTH, 10, 64, height));
            int yOffset = height;
            height = 20;
            g.setColor(Color.BLACK);
            g.drawString("MAX KP: ", Constants.GRAPHIC_WIDTH / 2, height + yOffset);
            g.drawString(pok.getMaxKp() + "", 4 * Constants.GRAPHIC_WIDTH / 5, height + yOffset);
            yOffset += height;
            for (Pokemon.Stat stat : Pokemon.Stat.values())
            {
                g.drawString(stat + ": ", Constants.GRAPHIC_WIDTH / 2, height + yOffset);
                g.drawString(pok.getStateValue(stat) + "", 4 * Constants.GRAPHIC_WIDTH / 5, height + yOffset);
                yOffset += height;
            }
        }
    }

    @Override
    public boolean keyPressed(InputConfig.Key key)
    {
        switch (key)
        {
            case A:
                break;
            case B:
                MainFrame.FRAME.setMainPanel(parent);
                MainFrame.FRAME.repaint();
                break;
        }
        return true;
    }

    @Override
    public void keyReleased(InputConfig.Key key)
    {

    }
}
