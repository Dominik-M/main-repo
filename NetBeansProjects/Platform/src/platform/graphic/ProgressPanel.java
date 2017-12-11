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
package platform.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import platform.graphic.InputConfig.Key;
import platform.utils.IO;
import platform.utils.Settings;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 14.03.2016
 */
public class ProgressPanel extends MainPanel {

	private static final long serialVersionUID = -8324042476193095793L;

	private static ProgressPanel activePanel;

	public static ProgressPanel getInstance() {
		if (activePanel == null) {
			activePanel = new ProgressPanel();
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

	private javax.swing.JProgressBar progressBar;
	private javax.swing.JLabel text;

	private ProgressPanel() {
		init();
	}

	private void init() {
		text = new javax.swing.JLabel("Loading...");
		progressBar = new javax.swing.JProgressBar();

		text.setBounds(WIDTH / 4, HEIGHT / 4, WIDTH / 2,
		((Font) (Settings.get("font"))).getSize() + 5);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(WIDTH / 4, HEIGHT / 2, WIDTH / 2, 50);
	}

	@Override
	public void drawGUI(Graphics2D g) {
		g.setColor((Color) Settings.get("backgroundColor"));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		progressBar.setString(progressBar.getValue() + "%");
		progressBar.paint(g.create(progressBar.getX(), progressBar.getY(), progressBar.getWidth(),
		progressBar.getHeight()));
		text.setFont((Font) Settings.get("font"));
		text.setForeground((Color) Settings.get("fontColor"));
		text.paint(g.create(text.getX(), text.getY(), text.getWidth(), text.getHeight()));
	}

	@Override
	public void onSelect() {
		requestFocusInWindow();
	}

	@Override
	public void onDisselect() {
	}

	@Override
	public void keyPressed(Key key) {

	}

	@Override
	public void keyReleased(Key key) {

	}
}
