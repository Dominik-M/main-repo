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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import pokemon.Pokemon;
import utils.Constants;
import utils.IO;
import utils.Settings;

/**
 * Superclass for main GUI content of the application.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 09.03.2016
 */
public abstract class MainPanel extends JPanel implements KeyListener, MouseListener
{

    private static final long serialVersionUID = -8680633574099657487L;
    static final int WIDTH = Constants.GRAPHIC_WIDTH, HEIGHT = Constants.GRAPHIC_HEIGHT;

    private final java.util.LinkedList<String> printQueue = new java.util.LinkedList<String>();

    /**
     *
     */
    public MainPanel()
    {
        this.setPreferredSize(new java.awt.Dimension(Constants.GRAPHIC_WIDTH, Constants.GRAPHIC_HEIGHT));
        this.addKeyListener(this);
        this.addMouseListener(this);
    }

    /**
     *
     */
    public void onSelect()
    {
        requestFocus();
        // TODO actions when component is added to GUI
    }

    /**
     *
     */
    public void onDisselect()
    {
        // TODO actions when component is removed from GUI
    }

    /**
     *
     * @param text
     */
    public void addToPrintQueue(String text)
    {
        // format text
        String line;
        String[] words = text.split(" ");
        int linenumber = 0, i = 0;
        while (i < words.length)
        {
            line = "";
            while (i < words.length && line.length() + words[i].length() < Constants.LINEWIDTH)
            {
                line += words[i] + " ";
                i++;
            }
            printQueue.addLast(line);
            linenumber++;
        }
        // fill lines
        while (linenumber % Constants.OUTPUT_LINES != 0)
        {
            printQueue.addLast("");
            linenumber++;
        }
    }

    /**
     *
     * @return
     */
    public boolean printQueueIsEmpty()
    {
        return printQueue.isEmpty();
    }

    public boolean isPrinting()
    {
        return !printQueueIsEmpty();
    }

    /**
     *
     * @return
     */
    public boolean printNext()
    {
        for (int i = 0; i < Constants.OUTPUT_LINES && printQueue.size() > 0; i++)
        {
            printQueue.pollFirst();
        }
        return this.printQueueIsEmpty();
    }

    /**
     * #DesignPattern #TemplateMethod
     *
     * @param g Graphics context
     */
    protected abstract void drawGUI(Graphics2D g);

    private BufferedImage createGUI()
    {
        BufferedImage gui = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gui.createGraphics();
        g.setColor(Settings.getSettings().backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        drawGUI(g);
        drawOutput(g);
        return gui;
    }

    /**
     *
     * @param g
     */
    private void drawOutput(Graphics2D g)
    {
        if (!this.printQueueIsEmpty())
        {
            drawFrame(g, Constants.OUTPUT_BOUNDS);
            g.setColor(Settings.getSettings().fontColor);
            g.setFont(Settings.getSettings().font);
            for (int i = 0; i < Constants.OUTPUT_LINES; i++)
            {
                if (i < printQueue.size())
                {
                    g.drawString(printQueue.get(i), Constants.OUTPUT_BOUNDS.x + 10, Constants.OUTPUT_BOUNDS.y + (i + 1) * Settings.getSettings().font.getSize() + Constants.BORDER_SIZE);
                }
            }
        }
    }

    // Superclass Methods
    @Override
    public final void paintComponent(Graphics g)
    {
        BufferedImage screen = createGUI();
        g.drawImage(screen.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0),
                0, 0, this);
    }

    // Interface Methods
    @Override
    public final void keyPressed(KeyEvent evt)
    {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null)
        {
            if (isPrinting())
            {
                switch (key)
                {
                    case A:
                    case B:
                        printNext();
                        break;
                    default:
                        break;
                }
            }
            else
            {
                // let the subclass handle this event
                keyPressed(key);
            }
        }
        else
        {
            IO.println("unsupported key (" + evt.toString() + ")", IO.MessageType.DEBUG);
        }
    }

    @Override
    public final void keyReleased(KeyEvent evt)
    {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null)
        {
            keyReleased(key);
        }
    }

    public abstract boolean keyPressed(InputConfig.Key key);

    public abstract void keyReleased(InputConfig.Key key);

    @Override
    public void keyTyped(KeyEvent evt)
    {
    }

    @Override
    public final void mouseClicked(MouseEvent e)
    {
        this.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    /*
     **************************************
     * Graphic functions                  *
     **************************************
     */
    /**
     *
     * @param g
     * @param bounds
     */
    public static void drawFrame(Graphics2D g, Rectangle bounds)
    {
        g.setColor(Settings.getSettings().fontColor);
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, Constants.BORDER_SIZE * 2, Constants.BORDER_SIZE * 2);
        g.setColor(Constants.OUTPUT_BACKGROUND);
        g.fillRoundRect(bounds.x + Constants.BORDER_SIZE / 2, bounds.y + Constants.BORDER_SIZE / 2, bounds.width - Constants.BORDER_SIZE, bounds.height - Constants.BORDER_SIZE, Constants.BORDER_SIZE * 2, Constants.BORDER_SIZE * 2);
    }

    /**
     * Draws name, level, status, hp and exp bar of the given Pokemon onto the
     * bounds in the graphics.
     *
     * @param g
     * @param pok
     * @param bounds
     */
    public static void drawPokStatusBar(Graphics2D g, Pokemon pok, Rectangle bounds)
    {
        if (pok != null)
        {
            g.setColor(Settings.getSettings().fontColor);
            g.drawString(pok.toString(), bounds.x, bounds.y + bounds.height / 6);
            g.drawString("Lvl: " + pok.getLvl(), bounds.x + bounds.width, bounds.y + bounds.height / 6);
            String kpLabel = pok.getKp() + "/" + pok.getMaxKp();
            g.drawString(kpLabel, bounds.width + bounds.x, bounds.y + bounds.height / 2);
            g.drawString(pok.getStatus().toString(), bounds.width + bounds.x + 7 * kpLabel.length(), bounds.y + bounds.height / 2);
            g.setColor(Color.red);
            g.fillRect(bounds.x, bounds.y + bounds.height / 5, bounds.width, bounds.height / 5);
            g.setColor(Color.green);
            g.fillRect(bounds.x, bounds.y + bounds.height / 5, bounds.width * pok.getKp() / pok.getMaxKp(), bounds.height / 5);
            g.setColor(Color.black);
            //g.drawRect(bounds.x, bounds.y + 2 * bounds.height / 5, bounds.width, bounds.height / 6);
            g.setColor(Color.blue);
            g.fillRect(bounds.x, bounds.y + 2 * bounds.height / 5, bounds.width * pok.getXp() / pok.getBenXp(), bounds.height / 6);
        }
    }
}
