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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import pokemon.Fight;
import pokemon.FightAction;
import pokemon.GameData;
import pokemon.attacks.Attack;
import sound.Sound;
import utils.Constants;
import utils.IO;
import utils.Settings;
import utils.Text;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 22.07.2016
 */
public class FightPanel extends MainPanel
{

    /*
     * ATTACK1   ATTACK2
     * ATTACK3   ATTACK4
     */
    private final InputOption ATTACK_1 = new InputOption(Text.DEFAULT_TEXT, new Rectangle(Constants.OUTPUT_BOUNDS.x + Constants.BORDER_SIZE, Constants.OUTPUT_BOUNDS.y, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2)),
            ATTACK_2 = new InputOption(Text.DEFAULT_TEXT, new Rectangle(Constants.OUTPUT_BOUNDS.x + Constants.OUTPUT_BOUNDS.width / 2, Constants.OUTPUT_BOUNDS.y, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2)),
            ATTACK_3 = new InputOption(Text.DEFAULT_TEXT, new Rectangle(Constants.OUTPUT_BOUNDS.x + Constants.BORDER_SIZE, Constants.OUTPUT_BOUNDS.y + Constants.OUTPUT_BOUNDS.height / 2, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2)),
            ATTACK_4 = new InputOption(Text.DEFAULT_TEXT, new Rectangle(Constants.OUTPUT_BOUNDS.x + Constants.OUTPUT_BOUNDS.width / 2, Constants.OUTPUT_BOUNDS.y + Constants.OUTPUT_BOUNDS.height / 2, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2));
    /*
     * ATTACK   PKMN
     * ITEM     FLEE
     */
    private final InputOption ATTACK = new InputOption(Text.OPTION_ATTACK, new Rectangle(Constants.OUTPUT_BOUNDS.x + Constants.OUTPUT_BOUNDS.width / 3, Constants.OUTPUT_BOUNDS.y, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2)),
            PKMN = new InputOption(Text.OPTION_PKMN, new Rectangle(Constants.OUTPUT_BOUNDS.x + 2 * Constants.OUTPUT_BOUNDS.width / 3, Constants.OUTPUT_BOUNDS.y, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2)),
            ITEM = new InputOption(Text.OPTION_ITEM, new Rectangle(Constants.OUTPUT_BOUNDS.x + Constants.OUTPUT_BOUNDS.width / 3, Constants.OUTPUT_BOUNDS.y + Constants.OUTPUT_BOUNDS.height / 2, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2)),
            FLEE = new InputOption(Text.OPTION_FLEE, new Rectangle(Constants.OUTPUT_BOUNDS.x + 2 * Constants.OUTPUT_BOUNDS.width / 3, Constants.OUTPUT_BOUNDS.y + Constants.OUTPUT_BOUNDS.height / 2, Constants.OUTPUT_BOUNDS.width / 4, Constants.OUTPUT_BOUNDS.height / 2));

    private final Fight fight;
    private InputOption selectedOption, preferredAttackOption;
    private final InputOption[] mainmenu = new InputOption[]
    {
        ATTACK, PKMN, ITEM, FLEE
    };
    private final InputOption[] attackmenu = new InputOption[]
    {
        ATTACK_1, ATTACK_2, ATTACK_3, ATTACK_4
    };
    private final Rectangle playerStatusBounds = new Rectangle(Constants.POK_SPRITE_WIDTH + 48, Constants.OUTPUT_BOUNDS.y - 40, 64, 32),
            opponentStatusBounds = new Rectangle(16, 16, 64, 32), attackInfoBounds = new Rectangle(Constants.BORDER_SIZE, Constants.OUTPUT_BOUNDS.y - 32, 2 * Constants.GRAPHIC_WIDTH / 3, 48);
    private boolean attackMenuOpen = false;

    private final Timer animationTimer = new javax.swing.Timer(1000 / Settings.getSettings().fps, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            repaint();
        }
    });

    public FightPanel(Fight f)
    {
        fight = f;
        init();
    }

    private void init()
    {
        setSelectedOption(ATTACK);
        preferredAttackOption = ATTACK_1;
        ATTACK.setLower(ITEM);
        ATTACK.setNext(PKMN);
        ATTACK.setPrev(FLEE);
        ATTACK.setUpper(ITEM);

        PKMN.setLower(FLEE);
        PKMN.setNext(ITEM);
        PKMN.setPrev(ATTACK);
        PKMN.setUpper(FLEE);

        ITEM.setLower(ATTACK);
        ITEM.setNext(FLEE);
        ITEM.setPrev(PKMN);
        ITEM.setUpper(ATTACK);

        FLEE.setLower(PKMN);
        FLEE.setNext(ATTACK);
        FLEE.setPrev(ITEM);
        FLEE.setUpper(PKMN);

        ATTACK_1.setLower(ATTACK_3);
        ATTACK_1.setNext(ATTACK_2);
        ATTACK_1.setPrev(ATTACK_4);
        ATTACK_1.setUpper(ATTACK_3);

        ATTACK_2.setLower(ATTACK_4);
        ATTACK_2.setNext(ATTACK_3);
        ATTACK_2.setPrev(ATTACK_1);
        ATTACK_2.setUpper(ATTACK_4);

        ATTACK_3.setLower(ATTACK_1);
        ATTACK_3.setNext(ATTACK_4);
        ATTACK_3.setPrev(ATTACK_2);
        ATTACK_3.setUpper(ATTACK_1);

        ATTACK_4.setLower(ATTACK_2);
        ATTACK_4.setNext(ATTACK_1);
        ATTACK_4.setPrev(ATTACK_3);
        ATTACK_4.setUpper(ATTACK_2);
    }

    public void setAttackMenuOpen(boolean menuOpen)
    {
        if (menuOpen != attackMenuOpen)
        {
            if (menuOpen)
            {
                Attack[] attacks = fight.getPlayersPok().getAtacks();
                for (int i = 0; i < 4; i++)
                {
                    if (attacks.length > i && attacks[i] != null)
                    {
                        attackmenu[i].setText(attacks[i].BASE.NAME);
                    }
                    else
                    {
                        attackmenu[i].setText(Text.DEFAULT_TEXT);
                    }
                }
                setSelectedOption(preferredAttackOption);
            }
            else
            {
                ATTACK_1.setText(Text.DEFAULT_TEXT);
                ATTACK_2.setText(Text.DEFAULT_TEXT);
                ATTACK_3.setText(Text.DEFAULT_TEXT);
                ATTACK_4.setText(Text.DEFAULT_TEXT);
                preferredAttackOption = selectedOption;
                setSelectedOption(ATTACK);
            }
            attackMenuOpen = menuOpen;
        }
    }

    public void sendAttackAction(int index)
    {
        Attack att = fight.getPlayersPok().getAttack(index);
        if (att != null)
        {
            if (att.getAP() > 0)
            {
                sendAction(FightAction.ActionType.ATTACK, index);
            }
            else
            {
                IO.println(Text.NO_AP, IO.MessageType.IMPORTANT);
            }
        }
        else
        {
            IO.println(Text.SELECTION_FAILED, IO.MessageType.IMPORTANT);
        }
    }

    public void sendAction(FightAction.ActionType type, int index)
    {
        fight.setPlayerAction(new FightAction(type, index));
        fight.doStep();
    }

    public void setSelectedOption(InputOption o)
    {
        if (selectedOption != null)
        {
            selectedOption.setSelected(false);
        }
        selectedOption = o;
        selectedOption.setSelected(true);
    }

    private void promptPokSelection()
    {
        final FightPanel instance = this;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                int index = PokemonMenuPanel.getPokemonSelection(instance, fight.getPlayer(), true);
                if (index > Constants.INVALID_INDEX)
                {
                    sendAction(FightAction.ActionType.SWITCH, index);
                }
                else
                {
                    IO.println(Text.SELECTION_FAILED, IO.MessageType.IMPORTANT);
                }
                instance.repaint();
            }

        }).start();
    }

    /**
     *
     */
    @Override
    public void drawGUI(Graphics2D g)
    {
        g.setColor(Settings.getSettings().backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        drawInterface(g);
    }

    private void drawInterface(Graphics2D g)
    {
        ImageIO.getSprite(fight.getPlayersPok().getBasis().getBaseName().toLowerCase() + "IconBack.png").draw(g, 32, Constants.OUTPUT_BOUNDS.y - Constants.POK_SPRITE_HEIGHT, 0);
        drawPokStatusBar(g, fight.getPlayersPok(), playerStatusBounds);
        ImageIO.getSprite(fight.getOpponentPok().getBasis().getBaseName().toLowerCase() + "Icon.png").draw(g, WIDTH - Constants.POK_SPRITE_WIDTH - 32, 32, 0);
        drawPokStatusBar(g, fight.getOpponentPok(), opponentStatusBounds);
        drawFrame(g, Constants.OUTPUT_BOUNDS);
        if (fight.isPlayersTurn())
        {
            if (attackMenuOpen && !isPrinting())
            {
                for (InputOption o : attackmenu)
                {
                    o.draw(g);
                }
                Attack selAttack = null;
                if (selectedOption.ID == ATTACK_1.ID)
                {
                    selAttack = fight.getPlayersPok().getAttack(0);
                }
                else if (selectedOption.ID == ATTACK_2.ID)
                {
                    selAttack = fight.getPlayersPok().getAttack(1);
                }
                else if (selectedOption.ID == ATTACK_3.ID)
                {
                    selAttack = fight.getPlayersPok().getAttack(2);
                }
                else if (selectedOption.ID == ATTACK_4.ID)
                {
                    selAttack = fight.getPlayersPok().getAttack(3);
                }
                if (selAttack != null)
                {
                    drawFrame(g, attackInfoBounds);
                    g.setColor(Settings.getSettings().fontColor);
                    g.setFont(Settings.getSettings().font);
                    g.drawString("AP: " + selAttack.getAP() + "/" + selAttack.BASE.AP, attackInfoBounds.x + Constants.BORDER_SIZE, attackInfoBounds.y + Constants.BORDER_SIZE + 16);
                    g.drawString("DMG:" + selAttack.getDmg() + " Gen:" + selAttack.BASE.GENA, attackInfoBounds.x + Constants.BORDER_SIZE, attackInfoBounds.y + Constants.BORDER_SIZE + 32);
                }
            }
            else
            {
                for (InputOption o : mainmenu)
                {
                    o.draw(g);
                }
            }
        }
    }

    @Override
    public boolean keyPressed(InputConfig.Key key)
    {
        boolean result = false;
        if (fight.isPlayersTurn())
        {
            switch (key)
            {
                case A:
                    result = true;
                    if (selectedOption.ID == ATTACK.ID)
                    {
                        setAttackMenuOpen(true);
                    }
                    else if (selectedOption.ID == PKMN.ID)
                    {
                        promptPokSelection();
                    }
                    else if (selectedOption.ID == ITEM.ID)
                    {
                        //TODO item selection
                        IO.println("Du hast keine Items", IO.MessageType.IMPORTANT);
                    }
                    else if (selectedOption.ID == FLEE.ID)
                    {
                        sendAction(FightAction.ActionType.FLEE, 0);
                    }
                    else if (selectedOption.ID == ATTACK_1.ID)
                    {
                        sendAttackAction(0);
                    }
                    else if (selectedOption.ID == ATTACK_2.ID)
                    {
                        sendAttackAction(1);
                    }
                    else if (selectedOption.ID == ATTACK_3.ID)
                    {
                        sendAttackAction(2);
                    }
                    else if (selectedOption.ID == ATTACK_4.ID)
                    {
                        sendAttackAction(3);
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                case B:
                    if (attackMenuOpen)
                    {
                        setAttackMenuOpen(false);
                        return true;
                    }
                    break;
                case LEFT:
                    setSelectedOption(selectedOption.getPrev());
                    break;
                case RIGHT:
                    setSelectedOption(selectedOption.getNext());
                    break;
                case UP:
                    setSelectedOption(selectedOption.getUpper());
                    break;
                case DOWN:
                    setSelectedOption(selectedOption.getLower());
                    break;
                default:
                    break;
            }
            repaint();
        }
        else
        {
            if (fight.isEnd())
            {
                MainFrame.FRAME.setMainPanel(GameGrid.getInstance());
                Sound.stop();
                sound.Sound.playSoundClip(new java.io.File(GameData.getCurrentGame().getPos().getSoundfilename()), Constants.SOUND_LOOP_CONTINUOUSLY);
            }
            if (key == InputConfig.Key.A || key == InputConfig.Key.B || key == InputConfig.Key.START)
            {
                Fight.State oldstate;
                do
                {
                    oldstate = fight.getState();
                    fight.doStep();
                } while (oldstate != fight.getState() && !isPrinting());
            }
            if (fight.getState() == Fight.State.PLAYER_KO && !isPrinting())
            {
                promptPokSelection();
            }
        }
        return result;
    }

    @Override
    public void keyReleased(InputConfig.Key key)
    {

    }
}
