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
package template.graphic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import template.graphic.InputConfig.Key;
import template.utils.Constants;
import template.utils.IO;
import template.utils.Settings;

/**
 * Superclass for main GUI content of the application.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 09.03.2016
 */
public abstract class MainPanel extends JPanel implements KeyListener {

    private static final long serialVersionUID = -8680633574099657487L;
    public static final int WIDTH = Constants.WIDTH, HEIGHT = Constants.HEIGHT;

    protected final java.util.LinkedList<String> printQueue = new java.util.LinkedList<String>();

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

    public boolean isPrinting() {
        return !printQueueIsEmpty();
    }

    public boolean printQueueIsEmpty() {
        return printQueue.size() == 0;
    }

    public boolean printNext() {
        printQueue.pollFirst();
        return this.printQueueIsEmpty();
    }

    public abstract void drawGUI(Graphics2D g);

    // Superclass Methods
    @Override
    public final void paintComponent(Graphics g) {
        BufferedImage gui = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = gui.createGraphics();
        g2D.setColor(Settings.getSettings().backgroundColor);
        g2D.fillRect(0, 0, WIDTH, HEIGHT);
        drawGUI(g2D);
        g.drawImage(gui.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0),
                0, 0, this);
    }

    // Interface Methods
    @Override
    public final void keyPressed(KeyEvent evt) {
        Key k = InputConfig.translateKeyCode(evt.getKeyCode());
        if (k != null) {
            keyPressed(k);
        } else {
            IO.println("Unsupported keypress: " + evt.getKeyChar(), IO.MessageType.DEBUG);
        }
    }

    @Override
    public final void keyReleased(KeyEvent evt) {
        Key k = InputConfig.translateKeyCode(evt.getKeyCode());
        if (k != null) {
            keyReleased(k);
        } else {
            //nothing todo
        }
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }

    public abstract void keyPressed(Key key);

    public abstract void keyReleased(Key key);
}
