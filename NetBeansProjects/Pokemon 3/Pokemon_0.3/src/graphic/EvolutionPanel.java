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
package graphic;

import image.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import pokemon.GameData;
import pokemon.Pokemon;
import pokemon.PokemonBasis;
import utils.Constants;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 26.06.2017
 */
public class EvolutionPanel extends MainPanel
{

    private final MainPanel parent;
    private final Pokemon POK;
    private final PokemonBasis NEXT;
    private final Image img1, img2;
    private int t1 = 1000, t2 = 100;
    private boolean showImageOne = true, finished = false;
    private final Timer toggleTimer = new Timer(t1, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            showImageOne = !showImageOne;
            if (showImageOne)
            {
                if (t1 <= 100)
                {
                    IO.println(POK.toString() + " entwickelt sich zu " + NEXT.toString() + "!", IO.MessageType.IMPORTANT);
                    finished = true;
                    showImageOne = false;
                    POK.evolve(NEXT);
                }
                else
                {
                    t1 -= t1 / 10;
                    toggleTimer.setInitialDelay(t1);
                    toggleTimer.restart();
                }
            }
            else
            {
                toggleTimer.setInitialDelay(t2);
                toggleTimer.restart();
            }
            repaint();
        }
    });

    public EvolutionPanel(MainPanel parent, Pokemon pok, PokemonBasis next)
    {
        this.parent = parent;
        POK = pok;
        NEXT = next;
        img1 = ImageIO.getSprite(POK.getBasis().getBaseName().toLowerCase() + "Icon.png").getImage().getScaledInstance(Constants.GRAPHIC_WIDTH / 2, Constants.GRAPHIC_HEIGHT / 2, 0);
        img2 = ImageIO.getSprite(NEXT.getBaseName().toLowerCase() + "Icon.png").getImage().getScaledInstance(Constants.GRAPHIC_WIDTH / 2, Constants.GRAPHIC_HEIGHT / 2, 0);
        toggleTimer.setRepeats(false);
    }

    @Override
    protected void drawGUI(Graphics2D g)
    {
        int x = Constants.GRAPHIC_WIDTH / 4;
        int y = Constants.GRAPHIC_HEIGHT / 5;
        if (showImageOne)
        {
            g.drawImage(img1, x, y, null);
        }
        else
        {
            g.drawImage(img2, x, y, null);
        }
        if (!finished)
        {
            drawFrame(g, Constants.OUTPUT_BOUNDS);
            g.setColor(Color.BLACK);
            g.drawString("Hey! " + POK + " entwickelt sich!", Constants.OUTPUT_BOUNDS.x + 10, Constants.OUTPUT_BOUNDS.y + 20);
        }
    }

    @Override
    public boolean keyPressed(InputConfig.Key key)
    {
        if (key == InputConfig.Key.A && finished)
        {
            MainFrame.FRAME.setMainPanel(parent);
            sound.Sound.playSoundClip(new java.io.File(GameData.getCurrentGame().getPos().getSoundfilename()), Constants.SOUND_LOOP_CONTINUOUSLY);
        }
        else if (key == InputConfig.Key.B)
        {
            toggleTimer.stop();
            showImageOne = true;
            finished = true;
            IO.println("Entwicklung abgebrochen!", IO.MessageType.IMPORTANT);
        }
        return false;
    }

    @Override
    public void keyReleased(InputConfig.Key key)
    {

    }

    @Override
    public void onSelect()
    {
        super.onSelect();
        toggleTimer.start();
    }
}
