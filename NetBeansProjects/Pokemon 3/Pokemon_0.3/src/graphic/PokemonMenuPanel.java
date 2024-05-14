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
import pokemon.Spieler;
import pokemon.Trainer;
import utils.Constants;
import utils.IO;

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
    private final String[] options;
    private State state;
    private final boolean cancelable;

    public PokemonMenuPanel(MainPanel parent)
    {
        this(parent, GameData.getCurrentGame().getPlayer(), State.SELECT_POK, true);
    }

    private PokemonMenuPanel(MainPanel parent, Trainer owner, State initState, boolean cancelable)
    {
        this.cancelable = cancelable;
        this.owner = owner;
        this.parent = parent;
        this.state = initState;
        if (initState == State.SELECT_POK_FIGHT)
        {
            options = new String[]
            {
                "SELECT", "STATUS", "BACK"
            };
        }
        else
        {
            options = new String[]
            {
                "STATUS", "SWITCH", "BACK"
            };
        }
    }

    public static int getPokemonSelection(MainPanel parent, Spieler player, boolean isInFight, boolean cancelable)
    {
        PokemonMenuPanel menu = new PokemonMenuPanel(parent, player, isInFight ? State.SELECT_POK_FIGHT : State.SELECT_POK, cancelable);
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
                g.drawString(IO.translate(options[i]), bounds.x + 20, bounds.y + (1 + i) * textHeight - 10);
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
            case "BACK":
                if (state == State.OPTIONS)
                {
                    state = State.SELECT_POK;
                }
                else if (state == State.OPTIONS_FIGHT)
                {
                    state = State.SELECT_POK_FIGHT;
                }
                break;
            case "SELECT":
                if (state == State.OPTIONS_FIGHT && owner.getPoks()[selectedIndex].isBsg())
                {
                    IO.println(owner.getPoks()[selectedIndex].toString() + " " + IO.translate("POK_KO"), IO.MessageType.IMPORTANT);
                    break;
                }
                else if (state == State.OPTIONS)
                {
                    //TODO use item
                    IO.println(owner.getPoks()[selectedIndex].toString() + " selected.", IO.MessageType.DEBUG);
                }
                close();
                break;
            case "SWITCH":
                switchIndex = selectedIndex;
                state = State.SWITCHING;
                break;
            case "STATUS":
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
                    case SELECT_POK_FIGHT:
                        if (cancelable)
                        {
                            selectedIndex = Constants.INVALID_INDEX;
                            close();
                        }
                        else
                        {
                            IO.println(IO.translate("SELECTION_EMPTY"), IO.MessageType.IMPORTANT);
                        }
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
