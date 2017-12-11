/**
 * Copyright (C) 2016 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import utils.Constants;

/**
 * Superclass for main GUI content of the application.
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 09.03.2016
 */
public abstract class MainPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = -8680633574099657487L;
	public static final int WIDTH = Constants.WIDTH, HEIGHT = Constants.HEIGHT;

	protected final java.util.LinkedList<String> printQueue = new java.util.LinkedList<String>();

	public MainPanel() {
		init();
	}

	private void init() {
		this.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
	}

	public abstract void onSelect();

	public abstract void onDisselect();

	public void addToPrintQueue(String text) {
		printQueue.addLast(text);
		if (printQueue.size() > Constants.MAX_MESSAGE_COUNT)
			printNext();
	}

	public boolean printQueueIsEmpty() {
		return printQueue.size() == 0;
	}

	public boolean printNext() {
		printQueue.pollFirst();
		return this.printQueueIsEmpty();
	}

	// Interface Methods

	@Override
	public abstract void keyPressed(KeyEvent evt);

	@Override
	public abstract void keyReleased(KeyEvent evt);

	@Override
	public void keyTyped(KeyEvent evt) {
	}
}
