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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class MainPanel extends JPanel implements KeyListener {

    private static final long serialVersionUID = -8680633574099657487L;
    private static final int WIDTH = Constants.WIDTH, HEIGHT = Constants.HEIGHT;

    private final java.util.LinkedList<String> printQueue = new java.util.LinkedList<String>();

    public MainPanel() {
        this.setPreferredSize(new java.awt.Dimension(Constants.WIDTH, Constants.HEIGHT));
        this.addKeyListener(this);
    }

    public void onSelect() {
        requestFocus();
        // TODO actions when component is added to GUI
    }

    public void onDisselect() {
        // TODO actions when component is removed from GUI
    }

    public void addToPrintQueue(String text) {
        printQueue.addLast(text);
    }

    public boolean printQueueIsEmpty() {
        return printQueue.size() == 0;
    }

    public boolean printNext() {
        printQueue.pollFirst();
        return this.printQueueIsEmpty();
    }

    public BufferedImage createGUI() {
        BufferedImage gui = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gui.createGraphics();
        g.setColor(Settings.getSettings().backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        drawOutput(g);
        return gui;
    }

    public void drawOutput(Graphics2D g) {
        if (!this.printQueueIsEmpty()) {
            g.setColor(Constants.OUTPUT_BACKGROUND);
            g.fillRect(Constants.OUTPUT_BOUNDS.x, Constants.OUTPUT_BOUNDS.y, Constants.OUTPUT_BOUNDS.width, Constants.OUTPUT_BOUNDS.height);
            g.setColor(Settings.getSettings().fontColor);
            g.setFont(Settings.getSettings().font);
            g.drawString(printQueue.getFirst(), Constants.OUTPUT_BOUNDS.x + 10, Constants.OUTPUT_BOUNDS.y + Settings.getSettings().font.getSize() + 10);
        }
    }

    // Superclass Methods
    @Override
    public final void paintComponent(Graphics g) {
        BufferedImage screen = createGUI();
        g.drawImage(screen.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0),
                0, 0, this);
    }

    // Interface Methods
    @Override
    public void keyPressed(KeyEvent evt) {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null) {
            switch (key) {
                case A:
                case B:
                    printNext();
                    break;
                default:
                    break;
            }
        } else {
            IO.println("unsupported key (" + evt.toString() + ")", IO.MessageType.DEBUG);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }

    public void drawStatusBar(Graphics2D g, Pokemon p) {
        //TODO graphic methods
    }
}
