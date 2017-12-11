/*
 * Copyright (C) 2016 Dominik
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
package spiel;

import graphic.MainFrame;
import graphic.ProgressPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.Timer;
import language.Text;
import static spiel.InputListener.INVALID;
import welt.Ort;

/**
 *
 * @author Dominik
 */
public final class Spielwelt implements java.io.Serializable, InputListener {

    //static
    public static final String FILENAME = "game.dat";
    private static Spielwelt spiel;
    public static final int CLOCK_DELAY = 100, DEFAULT_WALK_DELAY = 5;
    private static final Timer clock = new Timer(CLOCK_DELAY, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            spiel.clock();
        }
    });

    // current position
    private Ort pos;
    // declaration of all Orts
    private Ort alabastia, route1, vertania;
    private int directionKey;
    private Spieler spieler;
    private Trainer rivale;
    private int walkCounter = 0, walkDelay = DEFAULT_WALK_DELAY;

    private Spielwelt(Spieler spieler) {
        this.spieler = spieler;
        rivale = new Trainer("Rivale");
        directionKey = INVALID;
    }

    public static Spielwelt newWorld(String playername) {
        spiel = new Spielwelt(new Spieler(playername));
        spiel.initOrte();
        Ort start = spiel.getOrt(Text.ALABASTIA);
        spiel.ortwechsel(start, start.STARTX, start.STARTY);
        return spiel;
    }

    public static Spielwelt loadFile(String filename) {
        Spielwelt oldgame = null;
        try {
            File datei = new File(FILENAME);
            ObjectInputStream ois;
            try (FileInputStream fis = new FileInputStream(datei)) {
                ois = new ObjectInputStream(fis);
                oldgame = (Spielwelt) ois.readObject();
            }
            ois.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        if (oldgame != null) {
            spiel = oldgame;
            spiel.reloadOrte();
            IO.IOMANAGER.println("Spielstand geladen", IO.MessageType.DEBUG);
        }
        return spiel;
    }

    public static boolean save(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(FILENAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(spiel);
            fos.close();
            oos.close();
        } catch (Exception ex) {
            IO.IOMANAGER.println("Failed to save file", IO.MessageType.ERROR);
            ex.printStackTrace();
            return false;
        }
        IO.IOMANAGER.println("Spielstant gesichert", IO.MessageType.IMPORTANT);
        return true;
    }

    public static Spielwelt getCurrentWorld() {
        return spiel;
    }

    public static void setCurrentWorld(Spielwelt world) {
        spiel = world;
    }

    public void setPaused(boolean paused) {
        if (paused && isRunning()) {
            clock.stop();
            sound.Sound.stopSound();
        } else if (!paused && !isRunning()) {
            clock.start();
            sound.Sound.startSound();
        }
    }

    public boolean isRunning() {
        return clock.isRunning();
    }

    public int getDirectionKey() {
        return directionKey;
    }

    public void setDirectionKey(int key) {
        directionKey = key;
    }

    public void ortwechsel(Ort neu, int xNeu, int yNeu) {
        pos = neu;
        spieler.setPos(xNeu, yNeu);
        sound.Sound.playSoundClip(pos.getSoundFilename(), -1);
    }

    public Ort getOrt() {
        return pos;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setWalkDelay(int delay) {
        walkDelay = delay;
    }

    private void clock() {
        if (directionKey != INVALID && !IO.IOMANAGER.isPrinting()) {
            if (directionKey != spieler.getDirection()) {
                walkCounter = 0;
                spieler.setDirection(directionKey);
            }
            walkCounter++;
            if (walkCounter >= walkDelay) {
                walkCounter = 0;
                //TODO walk
                int x = spieler.getX(), y = spieler.getY();
                if (spieler.getDirection() == InputListener.UP) {
                    y--;
                } else if (spieler.getDirection() == InputListener.RIGHT) {
                    x++;
                } else if (spieler.getDirection() == InputListener.DOWN) {
                    y++;
                } else if (spieler.getDirection() == InputListener.LEFT) {
                    x--;
                }
                if (pos.isPassable(x, y)) {
                    spieler.setPos(x, y);
                    pos.get(x, y).betreten();
                }
            }
        }
        // TODO notify animated objects
    }

    @Override
    public void ButtonPressed(int button) {
        switch (button) {
            case A:

                int x = spieler.getX(),
                 y = spieler.getY();
                if (spieler.getDirection() == InputListener.UP) {
                    y--;
                } else if (spieler.getDirection() == InputListener.RIGHT) {
                    x++;
                } else if (spieler.getDirection() == InputListener.DOWN) {
                    y++;
                } else if (spieler.getDirection() == InputListener.LEFT) {
                    x--;
                }
                pos.get(x, y).benutzt();
                break;
            case B:
                setWalkDelay(DEFAULT_WALK_DELAY / 2);
                break;
            case UP:
                // TODO switch dialog value OR set direction
                if (MainFrame.WINDOW.isAsking()) {
                    // switch selected input value up
                } else {
                    setDirectionKey(button);
                }
                break;
            case RIGHT:
                // TODO switch dialog value OR set direction
                if (MainFrame.WINDOW.isAsking()) {
                    // switch selected input value up
                } else {
                    setDirectionKey(button);
                }
                break;
            case DOWN:
                // TODO switch dialog value OR set direction
                if (MainFrame.WINDOW.isAsking()) {
                    // switch selected input value up
                } else {
                    setDirectionKey(button);
                }
                break;
            case LEFT:
                // TODO switch dialog value OR set direction
                if (MainFrame.WINDOW.isAsking()) {
                    // switch selected input value up
                } else {
                    setDirectionKey(button);
                }
                break;
        }
    }

    @Override
    public void ButtonReleased(int button) {
        if (button == getDirectionKey()) {
            setDirectionKey(INVALID);
        } else if (button == B) {
            setWalkDelay(DEFAULT_WALK_DELAY);
        }
    }

    public Ort getOrt(language.Text name) {
        for (Ort o : getOrte()) {
            if (o.NAME == name) {
                return o;
            }
        }
        return null;
    }

    public Ort[] getOrte() {
        return new Ort[]{alabastia, route1, vertania};
    }

    /**
     * Notifies all Orts that the game was reloaded. This means not
     * re-initialising the Orts by creating new Objects but adjusting all
     * Objekts of all Orts at the current game state by calling their
     * Objekt.init() method.
     */
    public void reloadOrte() {
        for (Ort o : getOrte()) {
            o.reload();
        }
    }

    /**
     * Creates a complete new world by initialising all Orts. Old Objekts will
     * just be overidden. To keep the current game state with its Objekts you
     * may use reloadOrte() instead.
     */
    private void initOrte() {
        int orteAnz = 3, i = 0;
        ProgressPanel.setText("Creating World...");
        ProgressPanel.setProgress(i * 100 / orteAnz);
        alabastia = Ort.initAlabastia();
        i++;
        ProgressPanel.setProgress(i * 100 / orteAnz);
        route1 = Ort.initRoute1();
        i++;
        ProgressPanel.setProgress(i * 100 / orteAnz);
        vertania = Ort.initVertania();
        i++;
        ProgressPanel.setProgress(i * 100 / orteAnz);
    }

    public Trainer getRivale() {
        return rivale;
    }
}
