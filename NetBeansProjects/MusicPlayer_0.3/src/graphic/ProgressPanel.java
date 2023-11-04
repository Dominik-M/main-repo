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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import utils.IO;
import utils.Settings;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 14.03.2016
 */
public class ProgressPanel extends MainPanel {

	private static final long serialVersionUID = -8324042476193095793L;

	private static ProgressPanel activePanel;
	private static javax.swing.JDialog activeDialog;

	public static ProgressPanel getInstance() {
		if (activePanel == null) {
			synchronized (ProgressPanel.class) {
				if (activePanel == null)
					activePanel = new ProgressPanel();
			}
		}
		return activePanel;
	}

	public static void setProgress(int percent) {
		if (activePanel != null && percent >= 0 && percent <= 100) {
			activePanel.progressBar.setValue(percent);
		}
	}

	public static void setText(String txt) {
		if (activePanel != null) {
			IO.println("ProgressPanel - Text changed to: " + txt, IO.MessageType.DEBUG);
			activePanel.text.setText(txt);
		}
	}

	public static void showProgressDialog(java.awt.Frame parent) {
		activeDialog = new javax.swing.JDialog(parent);
		activeDialog.setModal(true);
		activeDialog.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
		activeDialog.setLayout(new java.awt.BorderLayout());
		activeDialog.add(getInstance(), java.awt.BorderLayout.CENTER);
		activeDialog.pack();
		activeDialog.setLocation(parent.getLocation());
		activeDialog.setVisible(true);
	}

	public static void closeDialog() {
		if (activeDialog != null)
			activeDialog.dispose();
	}

	private javax.swing.JProgressBar progressBar;
	private javax.swing.JLabel text;

	private ProgressPanel() {
		init();
	}

	private void init() {
		text = new javax.swing.JLabel("Loading...");
		progressBar = new javax.swing.JProgressBar();

		text.setBounds(WIDTH / 4, HEIGHT / 4, WIDTH / 2, Settings.settings.font.getSize() + 5);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(WIDTH / 4, HEIGHT / 2, WIDTH / 2, 50);
	}

	@Override
	public void paintComponent(Graphics g) {
		BufferedImage screen = createGUI();
		g.drawImage(screen.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0),
		0, 0, this);
	}

	public BufferedImage createGUI() {
		BufferedImage gui = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = gui.createGraphics();
		g.setColor(Settings.settings.backgroundColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		progressBar.setString(progressBar.getValue() + "%");
		progressBar.paint(g.create(progressBar.getX(), progressBar.getY(), progressBar.getWidth(),
		progressBar.getHeight()));
		text.setFont(Settings.settings.font);
		text.setForeground(Settings.settings.fontColor);
		text.paint(g.create(text.getX(), text.getY(), text.getWidth(), text.getHeight()));
		return gui;
	}

	@Override
	public void onSelect() {
	}

	@Override
	public void onDisselect() {
	}

	@Override
	public void keyPressed(KeyEvent evt) {
	}

	@Override
	public void keyReleased(KeyEvent evt) {
	}
}
