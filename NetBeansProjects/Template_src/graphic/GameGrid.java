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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import pokemon.GameData;
import utils.Constants;
import utils.IO;
import utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 22.07.2016
 */
public class GameGrid extends MainPanel {

    public static final int WIDTH = Constants.WIDTH, HEIGHT = Constants.HEIGHT;
    public static final int SPOT = 16;
    private static GameGrid gamegrid;

    private GameData gameData;
    private int viewX, viewY, creditsProgress;
    private Timer repaintClock;

    private GameGrid() {
        try {
            init();
        } catch (Exception ex) {
            IO.printException(ex);
        }
    }

    private void init() {
        viewX = 0;
        viewY = 0;
        creditsProgress = -1;
        repaintClock = new Timer(1000 / Settings.getSettings().fps, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }

    public static GameGrid getInstance() {
        if (gamegrid == null) {
            synchronized (GameGrid.class) {
                if (gamegrid == null) {
                    gamegrid = new GameGrid();
                }
            }
        }
        return gamegrid;
    }

    public void setViewport(int x, int y) {
        viewX = x;
        viewY = y;
    }

    @Override
    public void onSelect() {
        requestFocus();
        repaint();
    }

    @Override
    public void onDisselect() {
        setPaused(true);
    }

    @Override
    public BufferedImage createGUI() {
        BufferedImage gui = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gui.createGraphics();
        g.setColor(Settings.getSettings().backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.translate(-viewX, -viewY);
        drawWorld(g);
        g.translate(viewX, viewY);
        drawInterface(g);
        drawOutput(g);
        if (creditsProgress >= 0) {
            drawCredits(g);
        }
        return gui;
    }

    private void drawWorld(Graphics2D g) {
        // TODO draw world
    }

    private void drawInterface(Graphics2D g) {
        // TODO draw Interface
    }

    private void drawCredits(Graphics g) {
        int x = WIDTH / 5;
        int y = HEIGHT - creditsProgress;
        g.setFont(Settings.getSettings().font);
        g.setColor(Settings.getSettings().fontColor);
        g.drawString("CREDITS", x, y);
        y += g.getFont().getSize() * 3;
        g.drawString("Author", x, y);
        y += g.getFont().getSize() * 3;
        g.drawString(Constants.AUTHOR, x, y);
        y += g.getFont().getSize() * 3;
        g.drawString("Co producer", x, y);
        y += g.getFont().getSize() * 3;
        for (int i = 0; i < Constants.COAUTHORS.length; i++) {
            g.drawString(Constants.COAUTHORS[i], x, y);
            y += g.getFont().getSize() * 3;
        }
        creditsProgress++;
    }

    private void setPaused(boolean pause) {
        if (!isPaused() && pause) {
            repaintClock.stop();
        } else if (isPaused() && !pause) {
            repaintClock.start();
        }
    }

    public void showCredits() {
        creditsProgress = 0;
        setPaused(false);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null) {
            switch (key) {
                case A:
                    //TODO interact
                    break;
                case B:
                    //TODO start sprint
                    break;
                case LEFT:
                    //TODO move left
                    break;
                case RIGHT:
                    //TODO move right
                    break;
                case UP:
                    //TODO move up
                    break;
                case DOWN:
                    //TODO move down
                    break;
                case SELECT:
                    //TODO do something
                    break;
                case START:
                    setPaused(!isPaused());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null) {
            switch (key) {
                case B:
                    // TODO stop sprint
                    break;
                case LEFT:
                case RIGHT:
                case UP:
                case DOWN:
                    // TODO stop walking
                    break;
                default:
                    break;
            }
        }
    }

    private boolean isPaused() {
        return repaintClock.isRunning();
    }
}
