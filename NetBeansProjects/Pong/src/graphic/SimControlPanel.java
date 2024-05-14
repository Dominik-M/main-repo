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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Pong;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 28.03.2016
 */
public class SimControlPanel extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = -6885400479662214128L;
	private JButton startstopBtn;
	private JButton stepBtn;
	private JComboBox<String> modeCBox;
	private JSlider fpsSlider;
	private final PongPanel pong;

	public SimControlPanel(PongPanel parent) {
		pong = parent;
		init();
	}

	private void init() {
		startstopBtn = new JButton("Start");
		startstopBtn.addActionListener(this);
		startstopBtn.setFocusable(false);
		stepBtn = new JButton("Step");
		stepBtn.addActionListener(this);
		stepBtn.setFocusable(false);
		fpsSlider = new JSlider();
		fpsSlider.setPaintLabels(true);
		fpsSlider.setPaintTicks(true);
		fpsSlider.setMinimum(10);
		fpsSlider.setMaximum(100);
		fpsSlider.setMajorTickSpacing(20);
		fpsSlider.setValue(pong.getFps());
		fpsSlider.setMinorTickSpacing(10);
		fpsSlider.addChangeListener(this);
		fpsSlider.setFocusable(false);
		modeCBox = new JComboBox<String>();
		modeCBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
		new String[] { Pong.Mode.CPU_ONLY.name(), Pong.Mode.ONE_PLAYER.name(),
				Pong.Mode.TWO_PLAYER.name() }));
		modeCBox.addActionListener(this);
		modeCBox.setFocusable(false);
		modeCBox.setSelectedIndex(pong.PONG.getMode().ordinal());
		this.setLayout(new FlowLayout());
		this.add(stepBtn);
		this.add(startstopBtn);
		this.add(fpsSlider);
		this.add(modeCBox);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(startstopBtn)) {
			pong.setRunning(pong.isPaused());
			if (pong.isPaused())
				startstopBtn.setText("Start");
			else
				startstopBtn.setText("Stop");
		} else if (e.getSource().equals(stepBtn)) {
			pong.doStep();
		} else if (e.getSource().equals(modeCBox)) {
			switch (modeCBox.getSelectedIndex()) {
				case 1:
					pong.PONG.setMode(Pong.Mode.ONE_PLAYER);
					break;
				case 2:
					pong.PONG.setMode(Pong.Mode.TWO_PLAYER);
					break;
				default:
					pong.PONG.setMode(Pong.Mode.CPU_ONLY);
					break;
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		pong.setFps(fpsSlider.getValue());
	}
}
