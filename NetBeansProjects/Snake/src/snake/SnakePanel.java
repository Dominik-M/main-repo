/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
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
package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import snake.InputConfig.Key;

/**
 * 
 * @author Dominik
 */
@SuppressWarnings("serial")
public class SnakePanel extends javax.swing.JPanel {

	public static final int CLOCK_DELAY_SLOW = 500, CLOCK_DELAY_NORMAL = 300,
			CLOCK_DELAY_FAST = 150, CLOCK_DELAY_INSANE = 75;
	public static int initialDelay = CLOCK_DELAY_NORMAL;
	private final Snake game;
	private BufferedImage screen;
	private final javax.swing.Timer clock;
	private boolean gameover;

	/**
	 * Creates new form SnakePanel
	 * 
	 * @param snake
	 *            The new game to run
	 */
	public SnakePanel(Snake snake) {
		initComponents();
		game = snake;
		gameover = false;
		clock = new javax.swing.Timer(initialDelay,
				new java.awt.event.ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						clock();
					}

				});
		this.addKeyListener(new java.awt.event.KeyListener() {

			public void keyTyped(KeyEvent ke) {
			}

			public void keyPressed(KeyEvent ke) {
				int key = ke.getKeyCode();
				if (key == Key.KEY_DOWN.keycode) {
					game.setDirection(Snake.Direction.DOWN);
				} else if (key == Key.KEY_RIGHT.keycode) {
					game.setDirection(Snake.Direction.RIGHT);
				} else if (key == Key.KEY_LEFT.keycode) {
					game.setDirection(Snake.Direction.LEFT);
				} else if (key == Key.KEY_UP.keycode) {
					game.setDirection(Snake.Direction.UP);
				} else if (key == Key.KEY_A.keycode) {
					setPaused(!isPaused());
				} else if (key == Key.KEY_B.keycode) {
					// TODO implement this case
				} else if (key == Key.KEY_ENTER.keycode) {
					// TODO implement this case
				} else if (key == Key.KEY_SELECT.keycode) {
					// TODO implement this case
				}
			}

			public void keyReleased(KeyEvent ke) {
			}
		});
	}

	public void setDelay(int ms) {
		clock.setDelay(ms);
		if (!isPaused()) {
			clock.restart();
			if (ms <= CLOCK_DELAY_INSANE) {
				Profile.getCurrentProfile()
						.getArchievement(
								Profile.Archievement.ArchievementName.INSANE)
						.unlock();
			}
		}
	}

	public void setPaused(boolean paused) {
		if (isPaused() && !paused && !gameover) {
			clock.start();
		} else if (!isPaused() && paused) {
			clock.stop();
		}
	}

	public boolean isPaused() {
		return !clock.isRunning();
	}

	public void clock() {
		if (!game.clock()) {
			gameover();
		}
		repaint();
	}

	public void gameover() {
		setPaused(true);
		if (!gameover) {
			gameover = true;
			game.currentLevel.addScore(game.getScore());
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		screen = createGUI();
		g.drawImage(
				screen.getScaledInstance(g.getClipBounds().width,
						g.getClipBounds().height, 0), 0, 0, this);
		g.setFont(new java.awt.Font("Consolas", 0, 18));
		g.drawString("Score: " + game.getScore(), 10, 20);
		if (game.getPowerupDuration() > 0) {
			g.drawString(game.getState().name(), 10, 40);
			if (game.getPowerupDuration() <= 10) {
				g.setColor(Color.red);
			}
			g.drawString("Powerup duration: " + game.getPowerupDuration(), 10,
					60);
		}
	}

	public BufferedImage createGUI() {
		BufferedImage gui = new BufferedImage(Snake.WIDTH, Snake.HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = gui.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Snake.WIDTH, Snake.HEIGHT);
		g.setColor(Color.BLACK);
		for (Position p : game.getBlockedPositions()) {
			g.fillRect(p.X, p.Y, 1, 1);
		}
		if (game.getState() == Snake.Status.ANGRY) {
			g.setColor(Color.red);
		} else if (game.getState() == Snake.Status.INVINCIBLE) {
			g.setColor(new Color(160 + (int) (Math.random() * 80), 0,
					160 + (int) (Math.random() * 80)));
		} else {
			g.setColor(Color.GREEN);
		}
		for (Position p : game.getSnakePositions()) {
			g.fillRect(p.X, p.Y, 1, 1);
		}
		Position pos = game.getFoodPos();
		g.setColor(Color.YELLOW);
		g.fillRect(pos.X, pos.Y, 1, 1);
		if (game.getPowerup() == Snake.Status.ANGRY) {
			g.setColor(Color.red);
		} else if (game.getPowerup() == Snake.Status.INVINCIBLE) {
			g.setColor(new Color(160 + (int) (Math.random() * 80), 0,
					160 + (int) (Math.random() * 80)));
		}
		pos = game.getPowerupPos();
		if (pos != null) {
			g.fillRect(pos.X, pos.Y, 1, 1);
		}
		g.setColor(Color.BLACK);
		if (isPaused()) {
			if (gameover) {
				g.setFont(new java.awt.Font("Consolas", 0, Snake.HEIGHT / 5));
				g.drawString("GAME OVER", 0, Snake.HEIGHT / 2);
			} else {
				g.setFont(new java.awt.Font("Consolas", 0, Snake.HEIGHT / 4));
				g.drawString("PAUSED", 2, Snake.HEIGHT / 2);
			}
		}
		return gui;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300,
				Short.MAX_VALUE));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
}
