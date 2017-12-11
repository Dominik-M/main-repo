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
import pokemon.Trainer;
import pokemon.Typ;
import pokemon.attacks.Attack;
import utils.Constants;
import utils.IO;

/**
 * Displays an overview of Pokemons of a Trainer. Provides functions for Pokemon
 * selections e.g. for switch in fight or item use.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 13.05.2017
 */
public class PokemonStatusPanel extends MainPanel
{

    private final Trainer OWNER;
    private final Pokemon POK;
    private final MainPanel PARENT;
    private final int[] selectedIndexInTab = new int[]
    {
        0, 0
    };
    private final int[] ROWS_IN_TAB = new int[]
    {
        9, 4
    };
    private final int TAB_STATUS = 0, TAB_ATTACKS = 1;
    private final String[] TAB_TITLES = new String[]
    {
        "STATUS", "ATTACKS"
    };
    private int tabIndex = 0, switchIndex = -1;

    public PokemonStatusPanel(MainPanel parent)
    {
        this(parent, GameData.getCurrentGame().getPlayer(), GameData.getCurrentGame().getPlayer().getFirst());
    }

    public PokemonStatusPanel(MainPanel parent, Trainer owner, Pokemon pok)
    {
        this.OWNER = owner;
        this.PARENT = parent;
        this.POK = pok;
    }

    @Override
    protected void drawGUI(Graphics2D g)
    {
        if (POK != null)
        {
            drawFrame(g, new Rectangle(0, 0, Constants.GRAPHIC_WIDTH, Constants.GRAPHIC_HEIGHT));
            int height = Constants.GRAPHIC_HEIGHT / 4;
            g.drawImage(ImageIO.getSprite(POK.getBasis().getBaseName().toLowerCase() + "Icon.png").getImage().getScaledInstance(height, height, 0), 0, 0, null);
            drawPokStatusBar(g, POK, new Rectangle(Constants.POK_SPRITE_WIDTH, 10, 64, height));
            int x1 = Constants.GRAPHIC_WIDTH - 56;
            int x2 = Constants.GRAPHIC_WIDTH - 56;
            drawTypImage(g, POK.getBasis().getTyp1(), new Rectangle(x1, 10, 48, 18));
            if (POK.getBasis().getTyp2() != null && POK.getBasis().getTyp2() != Typ.NORMAL)
            {
                drawTypImage(g, POK.getBasis().getTyp2(), new Rectangle(x2, 32, 48, 18));
            }
            int yOffset = height;
            x1 = Constants.GRAPHIC_WIDTH / 6;
            x2 = Constants.GRAPHIC_WIDTH / 2;
            g.setColor(Color.BLACK);
            g.drawString(TAB_TITLES[tabIndex], x1, yOffset);

            if (tabIndex == TAB_STATUS) // Status tab
            {
                height = 20;
                drawFrame(g, new Rectangle(20, yOffset + height * selectedIndexInTab[tabIndex], Constants.GRAPHIC_WIDTH - 40, height + 5));
                g.setColor(Color.BLACK);
                g.drawString(IO.translate("BASE") + ": ", x1, height + yOffset);
                g.drawString(POK.getBasis().toString(), x2, height + yOffset);
                yOffset += height;
                g.drawString(IO.translate("WESEN") + ": ", x1, height + yOffset);
                g.drawString(POK.getWesen().toString(), x2, height + yOffset);
                yOffset += height;
                g.drawString(IO.translate("ABILITY") + ": ", x1, height + yOffset);
                g.drawString(POK.getBasis().ABILITY.toString(), x2, height + yOffset);
                yOffset += height;
                x2 = 3 * Constants.GRAPHIC_WIDTH / 5;
                for (Pokemon.Stat stat : Pokemon.Stat.values())
                {
                    g.drawString(stat + ": ", x1, height + yOffset);
                    g.drawString(POK.getStateValue(stat) + "", x2, height + yOffset);
                    yOffset += height;
                }
            }
            else if (tabIndex == TAB_ATTACKS) // Attack tab
            {
                height = 40;
                drawFrame(g, new Rectangle(20, yOffset + height * selectedIndexInTab[tabIndex], Constants.GRAPHIC_WIDTH - 40, height + 5));
                g.setColor(Color.BLACK);
                for (Attack att : POK.getAtacks())
                {
                    yOffset += height / 2;
                    if (att == null)
                    {
                        g.drawString(IO.translate("DEFAULT_TEXT"), x1, yOffset);
                        yOffset += height / 2;
                    }
                    else
                    {
                        g.drawString(att.toString(), x1, yOffset);
                        // g.drawString(att.BASE.TYP.toString(), x2, yOffset);
                        drawTypImage(g, att.BASE.TYP, new Rectangle(x2, yOffset - 16, 48, 18));
                        yOffset += height / 2;
                        if (att.isPhys())
                        {
                            g.drawString(IO.translate("PHYS") + " " + IO.translate("DAMAGE") + ": " + att.getDmg() + " " + IO.translate("GENA") + ": " + att.BASE.GENA, x1, yOffset);
                        }
                        else
                        {
                            g.drawString(IO.translate("SPEZ") + " " + IO.translate("DAMAGE") + ": " + att.getDmg() + " " + IO.translate("GENA") + ": " + att.BASE.GENA, x1, yOffset);
                        }

                    }
                }
            }
        }
    }

    @Override
    public boolean keyPressed(InputConfig.Key key)
    {
        switch (key)
        {
            case A:
                enterSelectedOption();
                break;
            case B:
                MainFrame.FRAME.setMainPanel(PARENT);
                MainFrame.FRAME.repaint();
                break;
            case UP:
                if (selectedIndexInTab[tabIndex] > 0)
                {
                    selectedIndexInTab[tabIndex]--;
                }
                break;
            case DOWN:
                if (selectedIndexInTab[tabIndex] + 1 < ROWS_IN_TAB[tabIndex])
                {
                    selectedIndexInTab[tabIndex]++;
                }
                break;
            case LEFT:
                if (tabIndex > 0)
                {
                    tabIndex--;
                }
                break;
            case RIGHT:
                if (tabIndex + 1 < TAB_TITLES.length)
                {
                    tabIndex++;
                }
                break;
            case SELECT:
                if (tabIndex == TAB_ATTACKS)
                {
                    if (switchIndex < 0)
                    {
                        switchIndex = selectedIndexInTab[tabIndex];
                    }
                    else
                    {
                        Attack mem = POK.getAttack(switchIndex);
                        POK.getAtacks()[switchIndex] = POK.getAtacks()[selectedIndexInTab[tabIndex]];
                        POK.getAtacks()[selectedIndexInTab[tabIndex]] = mem;
                        switchIndex = -1;
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

    private void enterSelectedOption()
    {
        switch (tabIndex)
        {
            case 0:
                switch (selectedIndexInTab[tabIndex])
                {
                    case 0: // Pokemon base
                        IO.println(POK.getBasis().toString(), IO.MessageType.IMPORTANT);
                        break;
                    case 1: // Wesen
                        IO.println(IO.translate(POK.getWesen().TOOLTIP), IO.MessageType.IMPORTANT);
                        break;
                    case 2: // Ability
                        IO.println(POK.getBasis().ABILITY.getTooltip(), IO.MessageType.IMPORTANT);
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        Pokemon.Stat stat = Pokemon.Stat.values()[selectedIndexInTab[tabIndex] - 3];
                        IO.println(IO.translate("BASE") + " " + stat.toString() + ": " + POK.getBasis().getStateValue(stat), IO.MessageType.IMPORTANT);
                        IO.println(IO.translate("DV") + ": " + POK.getDV(stat), IO.MessageType.IMPORTANT);
                        IO.println(IO.translate("FP") + ": " + POK.getFP(stat), IO.MessageType.IMPORTANT);
                        break;
                }
                break;
            case 1:
                Attack att = POK.getAtacks()[selectedIndexInTab[tabIndex]];
                if (att != null)
                {
                    IO.println(IO.translate(att.BASE.DESCR), IO.MessageType.IMPORTANT);
                }
                break;
            default:
                IO.println("Invalid Tabindex " + tabIndex, IO.MessageType.DEBUG);
                break;
        }
    }
}
