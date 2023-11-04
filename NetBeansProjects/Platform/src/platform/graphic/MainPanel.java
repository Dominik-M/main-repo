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
package platform.graphic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import platform.utils.IO;
import platform.utils.Settings;

/**
 * Superclass for main GUI content of the application.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 09.03.2016
 */
public abstract class MainPanel extends JPanel implements KeyListener
{

    private static final long serialVersionUID = -8680633574099657487L;
    private boolean blockOnPrint = true, autoScale = true;

    protected final java.util.LinkedList<String> printQueue = new java.util.LinkedList<String>();

    public static int getScreenWidth()
    {
        Object setting = Settings.get("screenWidth");
        if (setting != null)
        {
            return (int) setting;
        }
        return 900;
    }

    public static int getScreenHeight()
    {
        Object setting = Settings.get("screenHeight");
        if (setting != null)
        {
            return (int) setting;
        }
        return 600;
    }

    /**
     * Default Constructor.
     */
    public MainPanel()
    {
        init();
    }

    /**
     * Initializes Form design and event handling.
     */
    private void init()
    {
        this.setPreferredSize(new java.awt.Dimension(getScreenWidth(), getScreenHeight()));
        this.addKeyListener(this);
    }

    /**
     * Returns the value of blockOnPrint flag. If this flag is set and the
     * printQueue is not empty (so it is printing) no key events will pass to
     * the subclass (blocked). If it is not set all events will pass
     * unconditionally but are not handled here and print progress (printNext()
     * calls) has to be controlled external.
     *
     * @return true if events are blocked while printing, false if all events
     * are passed unconditionally.
     */
    public boolean isBlockedOnPrint()
    {
        return blockOnPrint;
    }

    /**
     * Sets the value of blockOnPrint flag. If this flag is set and the
     * printQueue is not empty (so it is printing) no key events will pass to
     * the subclass (blocked). If it is not set all events will pass
     * unconditionally but are not handled here and print progress (printNext()
     * calls) has to be controlled external.
     *
     */
    public void setBlockOnPrint(boolean blockOnPrint)
    {
        this.blockOnPrint = blockOnPrint;
    }

    /**
     * Adds a String to the printQueue. Usually called by utils.IO class. You
     * might use utils.IO.println() instead.
     *
     * @param text a String to print.
     */
    public void addToPrintQueue(String text)
    {
        printQueue.addLast(text);
    }

    /**
     * Returns the negation of printQueueIsEmpty(), i.e. there are Strings to
     * print in the queue.
     *
     * @return true if the printQueue is not empty, false otherwise.
     */
    public boolean isPrinting()
    {
        return !printQueueIsEmpty();
    }

    /**
     * Returns the value of printQueue.size() == 0, i.e. if the queue is empty.
     *
     * @return true if the printQueue is empty, false otherwise.
     */
    public boolean printQueueIsEmpty()
    {
        return printQueue.size() == 0;
    }

    /**
     * Removes the first element of the printQueue. Returns true if the queue is
     * empty afterwards.
     *
     * @return true if the queue is empty, false otherwise.
     */
    public boolean printNext()
    {
        printQueue.pollFirst();
        return this.printQueueIsEmpty();
    }

    public boolean isAutoScaled()
    {
        return autoScale;
    }

    public void setAutoScaled(boolean autoScale)
    {
        this.autoScale = autoScale;
    }

    // Superclass Methods
    @Override
    public final void paintComponent(Graphics g)
    {
        if (autoScale)
        {
            BufferedImage gui = new BufferedImage(getScreenWidth(), getScreenHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2D = gui.createGraphics();
            drawGUI(g2D);
            g
                    .drawImage(gui.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0),
                            0, 0, this);
        }
        else
        {
            super.paintComponent(g);
        }
    }

    // Interface Methods
    @Override
    public void keyPressed(KeyEvent evt)
    {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null)
        {
            if (blockOnPrint && isPrinting())
            {
                switch (key)
                {
                    case KEY_A:
                    case KEY_B:
                    case KEY_ENTER:
                        printNext();
                    default:
                        break;
                }
            }
            else
            {
                keyPressed(key);
            }
        }
        else
        {
            IO.println("unsupported keypress", IO.MessageType.DEBUG);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt)
    {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null)
        {
            if (blockOnPrint && isPrinting())
            {
                // nothing to do here
            }
            else
            {
                keyReleased(key);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent evt)
    {
    }

    // abstract Methods
    public abstract void onSelect();

    public abstract void onDisselect();

    public abstract void drawGUI(Graphics2D g);

    public abstract void keyPressed(InputConfig.Key key);

    public abstract void keyReleased(InputConfig.Key key);
}
