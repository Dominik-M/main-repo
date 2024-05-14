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
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.GameData;
import pokemon.Spieler;
import pokemon.Trainer;
import utils.Constants;
import utils.IO;
import utils.Text;

/**
 * Displays an overview of Pokemons of a Trainer. Provides functions for Pokemon
 * selections e.g. for switch in fight or item use.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 16.08.2016
 */
public class PokemonMenuPanel extends MainPanel
{

    private enum State
    {
        SELECT_POK,
        SELECT_POK_FIGHT,
        OPTIONS,
        OPTIONS_FIGHT,
        SWITCHING,
        CLOSED;
    }

    private final Trainer owner;
    private final MainPanel parent;
    private int selectedIndex = 0, switchIndex = 0;
    private int selectedOptionIndex = 0;
    private final Text[] options;
    private State state;

    public PokemonMenuPanel(MainPanel parent, boolean isInFight)
    {
        this(parent, GameData.getCurrentGame().getPlayer(), isInFight);
    }

    public PokemonMenuPanel(MainPanel parent, Trainer owner, boolean isInFight)
    {
        this.owner = owner;
        this.parent = parent;
        this.state = isInFight ? State.SELECT_POK_FIGHT : State.SELECT_POK;
        if (isInFight)
        {
            options = new Text[]
            {
                Text.SELECT, Text.STATUS, Text.BACK
            };
        }
        else
        {
            options = new Text[]
            {
                Text.STATUS, Text.SWITCH, Text.BACK
            };
        }
    }

    public static int getPokemonSelection(MainPanel parent, Spieler player, boolean isInFight)
    {
        PokemonMenuPanel menu = new PokemonMenuPanel(parent, player, isInFight);
        MainFrame.FRAME.setMainPanel(menu);
        while (menu.state != State.CLOSED)
        {
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException ex)
            {
                IO.printException(ex);
            }
        }
        return menu.selectedIndex;
    }

    @Override
    protected void drawGUI(Graphics2D g)
    {
        for (int i = 0; i < owner.getPoks().length; i++)
        {
            if (owner.getPoks()[i] != null)
            {
                int height = Constants.GRAPHIC_HEIGHT / owner.getPoks().length;
                int yOffset = i * height;
                if (i == selectedIndex)
                {
                    drawFrame(g, new Rectangle(0, yOffset, Constants.GRAPHIC_WIDTH, height));
                }
                g.drawImage(ImageIO.getSprite(owner.getPoks()[i].getBasis().getBaseName().toLowerCase() + "Icon.png").getImage().getScaledInstance(height, height, 0), 0, yOffset, null);
                drawPokStatusBar(g, owner.getPoks()[i], new Rectangle(Constants.POK_SPRITE_WIDTH, yOffset + 10, 64, height));
            }
        }
        if (state == State.OPTIONS || state == State.OPTIONS_FIGHT)
        {
            Rectangle bounds = new Rectangle(2 * Constants.GRAPHIC_WIDTH / 3, 2 * Constants.GRAPHIC_HEIGHT / 3, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 3);
            drawFrame(g, bounds);
            int textHeight = bounds.height / options.length;
            for (int i = 0; i < options.length; i++)
            {
                if (i == selectedOptionIndex)
                {
                    drawFrame(g, new Rectangle(bounds.x + 10, bounds.y + i * textHeight, bounds.width - 20, textHeight));
                }
                g.setColor(Color.BLACK);
                g.drawString(options[i].toString(), bounds.x + 20, bounds.y + (1 + i) * textHeight - 10);
            }
        }
    }

    private void close()
    {
        MainFrame.FRAME.setMainPanel(parent);
        state = State.CLOSED;
    }

    private void performSelectedOption()
    {
        switch (options[selectedOptionIndex])
        {
            case BACK:
                if (state == State.OPTIONS)
                {
                    state = State.SELECT_POK;
                }
                else if (state == State.OPTIONS_FIGHT)
                {
                    state = State.SELECT_POK_FIGHT;
                }
                break;
            case SELECT:
                if (state == State.OPTIONS_FIGHT && owner.getPoks()[selectedIndex].isBsg())
                {
                    IO.println(owner.getPoks()[selectedIndex].toString() + " " + Text.POK_KO, IO.MessageType.IMPORTANT);
                    break;
                }
                close();
                break;
            case SWITCH:
                switchIndex = selectedIndex;
                state = State.SWITCHING;
                break;
            case STATUS:
                MainFrame.FRAME.setMainPanel(new PokemonStatusPanel(this, owner, owner.getPoks()[selectedIndex]));
                break;
        }
    }

    @Override
    public boolean keyPressed(InputConfig.Key key)
    {
        switch (key)
        {
            case A:
                switch (state)
                {
                    case SELECT_POK:
                        state = State.OPTIONS;
                        break;
                    case SELECT_POK_FIGHT:
                        state = State.OPTIONS_FIGHT;
                        break;
                    case OPTIONS:
                    case OPTIONS_FIGHT:
                        performSelectedOption();
                        break;
                    case SWITCHING:
                        owner.tausche(selectedIndex, switchIndex);
                        state = State.SELECT_POK;
                        break;
                }
                break;
            case B:
                switch (state)
                {
                    case SELECT_POK:
                        close();
                        break;
                    case SELECT_POK_FIGHT:
                        IO.println(Text.SELECTION_EMPTY, IO.MessageType.IMPORTANT);
                        break;
                    case OPTIONS:
                    case SWITCHING:
                        state = State.SELECT_POK;
                        break;
                    case OPTIONS_FIGHT:
                        state = State.SELECT_POK_FIGHT;
                        break;
                }

                break;
            case DOWN:
                switch (state)
                {
                    case SELECT_POK:
                    case SELECT_POK_FIGHT:
                    case SWITCHING:
                        if (selectedIndex + 1 < Constants.POKE_ANZ && owner.getPoks()[selectedIndex + 1] != null)
                        {
                            selectedIndex++;
                        }
                        break;
                    case OPTIONS:
                    case OPTIONS_FIGHT:
                        if (selectedOptionIndex + 1 < options.length)
                        {
                            selectedOptionIndex++;
                        }
                        break;
                }
                break;

            case UP:
                switch (state)
                {
                    case SELECT_POK:
                    case SELECT_POK_FIGHT:
                    case SWITCHING:
                        if (selectedIndex > 0)
                        {
                            selectedIndex--;
                        }
                        break;
                    case OPTIONS:
                    case OPTIONS_FIGHT:
                        if (selectedOptionIndex > 0)
                        {
                            selectedOptionIndex--;
                        }
                        break;
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
