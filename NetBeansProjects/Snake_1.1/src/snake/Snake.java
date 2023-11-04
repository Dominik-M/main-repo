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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Dominik
 */
public class Snake {

    public static enum Level {

        LEVEL_EMPTY("Empty"), LEVEL_FORT("Fort"), LEVEL_CROSS("Cross"), LEVEL3(
                "Level3"), LEVEL4("Level4"), LEVEL5("Level5"), LEVEL_LAB(
                        "Labyrinth");

        int[] highscores;
        private final String name;

        Level(String name) {
            highscores = new int[10];
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public void addScore(int score) {
            for (int i = 0; i < highscores.length; i++) {
                if (score > highscores[i]) {
                    for (int j = highscores.length - 1; j > i; j--) {
                        highscores[j] = highscores[j - 1];
                    }
                    highscores[i] = score;
                    break;
                }
            }
        }
    }

    public enum Direction {

        UP, RIGHT, LEFT, DOWN;
    }

    public enum Status {

        NORMAL, INVINCIBLE, ANGRY;
    }

    public static final File HIGHSCORE_FILE = new File("scores.dat");
    public static final int WIDTH = 40, HEIGHT = 40,
            DEFAULT_POWERUP_DURATION = 60;

    private final PositionList lastspots, blockedspots;
    public final Level currentLevel;
    private Position foodPos;
    private int score, grow;
    private Direction direction;
    private boolean dirChanged;
    private Status state, powerup;
    private Position powerupPos;
    private int powerupDuration, breaks;

    public Snake(Level level) {
        lastspots = new PositionList();
        if (level == Level.LEVEL_FORT) {
            blockedspots = createLevelFort();
        } else if (level == Level.LEVEL_CROSS) {
            blockedspots = createLevelCross();
        } else if (level == Level.LEVEL3) {
            blockedspots = createLevel3();
        } else if (level == Level.LEVEL4) {
            blockedspots = createLevel4();
        } else if (level == Level.LEVEL_LAB) {
            blockedspots = createLevelLabyrinth();
        } else if (level == Level.LEVEL5) {
            blockedspots = createLevel5();
        } else {
            blockedspots = new PositionList();
        }
        powerupPos = null;
        powerup = null;
        powerupDuration = 0;
        breaks = 0;
        state = Status.NORMAL;
        score = 0;
        grow = 0;
        currentLevel = level;
        dirChanged = false;
        direction = Direction.RIGHT;
        foodPos = createFood();
        lastspots.add(new Position(8, 12));
        lastspots.add(new Position(7, 12));
        lastspots.add(new Position(6, 12));
        lastspots.add(new Position(5, 12));
    }

    public Position getHeadPosition() {
        return lastspots.getFirst();
    }

    private int getMaxBreaks() {
        if (Profile.getCurrentProfile() != null) {
            return Profile.getCurrentProfile().getPreferences().breaks;
        } else {
            return 1;
        }
    }

    private int getMaxPowerupDuration() {
        if (Profile.getCurrentProfile() != null) {
            return Profile.getCurrentProfile().getPreferences().powerupDuration;
        } else {
            return DEFAULT_POWERUP_DURATION;
        }
    }

    private double getPowerupChance() {
        return 1.0;
    }

    public boolean clock() {
        if (move()) {
            if (getHeadPosition().equals(foodPos)) {
                grow++;
                score += 10;
                foodPos = createFood();
            }
            if (getHeadPosition().equals(powerupPos)) {
                state = powerup;
                if (state == Status.ANGRY) {
                    breaks = getMaxBreaks();
                }
                powerupDuration = getMaxPowerupDuration();
                powerup = null;
                powerupPos = null;
            }
            if (powerup == null && powerupDuration == 0
                    && Profile.getCurrentProfile() != null
                    && (Math.random() * 100) < getPowerupChance()) {
                createRandomPowerup();
            }
            if (powerupDuration > 0) {
                powerupDuration--;
                if (powerupDuration == 0) {
                    state = Status.NORMAL;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean move() {
        Position head = getHeadPosition();
        Position next;
        if (direction == Direction.UP) {
            if (head.Y == 0) {
                next = new Position(head.X, HEIGHT - 1);
            } else {
                next = new Position(head.X, head.Y - 1);
            }
        } else if (direction == Direction.RIGHT) {
            if (head.X >= WIDTH - 1) {
                next = new Position(0, head.Y);
            } else {
                next = new Position(head.X + 1, head.Y);
            }
        } else if (direction == Direction.DOWN) {
            if (head.Y >= HEIGHT - 1) {
                next = new Position(head.X, 0);
            } else {
                next = new Position(head.X, head.Y + 1);
            }
        } else {// LEFT
            if (head.X == 0) {
                next = new Position(WIDTH - 1, head.Y);
            } else {
                next = new Position(head.X - 1, head.Y);
            }
        }
        if (isFree(next) || state == Status.INVINCIBLE) {
            if (grow > 0) {
                grow--;
            } else {
                lastspots.removeLast();
            }
            lastspots.addFirst(next);
            dirChanged = false;
            return true;
        } else if (state == Status.ANGRY && blockedspots.contains(next)) {
            blockedspots.remove(next);
            breaks--;
            if (breaks <= 0) {
                state = Status.NORMAL;
                powerupDuration = 0;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isFree(Position pos) {
        // if (pos.X < 0 || pos.X >= WIDTH || pos.Y < 0 || pos.Y >= HEIGHT) {
        // return false;
        // }
        for (Position p : lastspots) {
            if (p.equals(pos)) {
                return false;
            }
        }
        for (Position p : blockedspots) {
            if (p.equals(pos)) {
                return false;
            }
        }
        return true;
    }

    public final Position createFood() {
        Position newPos;
        do {
            newPos = new Position((int) (Math.random() * WIDTH),
                    (int) (Math.random() * HEIGHT));
        } while (!isFree(newPos) || newPos.equals(foodPos));
        return newPos;
    }

    public Position getFoodPos() {
        return foodPos;
    }

    public void setFoodPos(Position foodPosNeu) {
        foodPos = foodPosNeu;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int scoreNeu) {
        score = scoreNeu;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction directionNeu) {
        if (!dirChanged
                && ((direction == Direction.UP && directionNeu != Direction.DOWN)
                || (direction == Direction.LEFT && directionNeu != Direction.RIGHT)
                || (direction == Direction.RIGHT && directionNeu != Direction.LEFT) || (direction == Direction.DOWN && directionNeu != Direction.UP))) {
            direction = directionNeu;
            dirChanged = true;
        }
    }

    public Position[] getBlockedPositions() {
        Position[] p = new Position[blockedspots.size()];
        for (int i = 0; i < p.length; i++) {
            p[i] = blockedspots.get(i);
        }
        return p;
    }

    public Position[] getSnakePositions() {
        Position[] p = new Position[lastspots.size()];
        for (int i = 0; i < p.length; i++) {
            p[i] = lastspots.get(i);
        }
        return p;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status stateNeu) {
        state = stateNeu;
    }

    public Status getPowerup() {
        return powerup;
    }

    public Position getPowerupPos() {
        return powerupPos;
    }

    public int getPowerupDuration() {
        return powerupDuration;
    }

    public void createRandomPowerup() {
        Position newPos;
        do {
            newPos = new Position((int) (Math.random() * WIDTH),
                    (int) (Math.random() * HEIGHT));
        } while (!isFree(newPos) || newPos.equals(foodPos));
        int rand = (int) (Math.random() * 2);
        if (rand < 1) {
            powerup = Status.INVINCIBLE;
        } else {
            powerup = Status.ANGRY;
        }
        powerupPos = newPos;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed"
        // desc=" Look and feel setting code (optional) ">
		/*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase
         * /tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        InputConfig.loadConfig(InputConfig.CONFIG_FILE);
        boolean profilesLoaded = Profile.loadProfiles(Profile.PROFILES_FILE);
        MenuPanel menu = new MenuPanel();
        Frame.FRAME.setMainPanel(menu);
        Frame.FRAME.setVisible(true);
        Profile.addProfileListener(Frame.FRAME);
        Profile.addProfileListener(menu);
        if (!profilesLoaded) {
            String name = javax.swing.JOptionPane.showInputDialog(Frame.FRAME,
                    "Please enter your name.", "Create new Profile",
                    javax.swing.JOptionPane.PLAIN_MESSAGE);
            Profile.addProfile(name);
        } else {
            Profile.switchProfile(Profile.getProfiles()[0]);
        }
    }

    public static void restart(Level level) {
        Frame.FRAME.setMainPanel(new SnakePanel(new Snake(level)));
    }

    public static void saveHighscores(File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Level[] levels = Level.values();
            int[][] scores = new int[levels.length][];
            for (int i = 0; i < scores.length; i++) {
                scores[i] = levels[i].highscores;
            }
            oos.writeObject(scores);
            fos.close();
            oos.close();
        } catch (Exception ex) {
            System.err.println("failed to save highscore File");
            ex.printStackTrace();
        }
    }

    public static void loadHighscores(File f) {
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            int[][] scores = (int[][]) ois.readObject();
            Level[] levels = Level.values();
            for (int i = 0; i < scores.length && i < levels.length; i++) {
                System.arraycopy(scores[i], 0, levels[i].highscores, 0,
                        levels[i].highscores.length);
            }
            fis.close();
            ois.close();
        } catch (Exception ex) {
            System.err.println("Cannot load highscore file");
            ex.printStackTrace();
        }
    }

    public static PositionList createLevelFort() {
        PositionList blocks = new PositionList();
        for (int x = 0; x < WIDTH; x++) {
            blocks.add(new Position(x, 0));
            blocks.add(new Position(x, HEIGHT - 1));
        }
        for (int y = 1; y < HEIGHT - 1; y++) {
            blocks.add(new Position(0, y));
            blocks.add(new Position(WIDTH - 1, y));
        }
        return blocks;
    }

    public static PositionList createLevelCross() {
        PositionList blocks = new PositionList();
        for (int x = 0; x < WIDTH; x++) {
            blocks.add(new Position(x, HEIGHT / 2));
        }
        for (int y = 1; y < HEIGHT - 1; y++) {
            blocks.add(new Position(WIDTH / 2, y));
        }
        return blocks;
    }

    public static PositionList createLevel3() {
        PositionList blocks = new PositionList();
        // upper left corner
        blocks.add(new Position(0, 0));
        blocks.add(new Position(0, 1));
        blocks.add(new Position(0, 2));
        blocks.add(new Position(1, 0));
        blocks.add(new Position(2, 0));
        // upper right corner
        blocks.add(new Position(WIDTH - 1, 0));
        blocks.add(new Position(WIDTH - 1, 1));
        blocks.add(new Position(WIDTH - 1, 2));
        blocks.add(new Position(WIDTH - 2, 0));
        blocks.add(new Position(WIDTH - 3, 0));
        // lower left corner
        blocks.add(new Position(0, HEIGHT - 1));
        blocks.add(new Position(0, HEIGHT - 2));
        blocks.add(new Position(0, HEIGHT - 3));
        blocks.add(new Position(1, HEIGHT - 1));
        blocks.add(new Position(2, HEIGHT - 1));
        // lower right corner
        blocks.add(new Position(WIDTH - 1, HEIGHT - 1));
        blocks.add(new Position(WIDTH - 1, HEIGHT - 2));
        blocks.add(new Position(WIDTH - 1, HEIGHT - 3));
        blocks.add(new Position(WIDTH - 2, HEIGHT - 1));
        blocks.add(new Position(WIDTH - 3, HEIGHT - 1));
        // center
        for (int x = WIDTH / 2 - 6; x < WIDTH / 2 + 4; x++) {
            blocks.add(new Position(x, HEIGHT / 2 - 3));
            blocks.add(new Position(x, HEIGHT / 2 + 1));
        }
        return blocks;
    }

    public static PositionList createLevel4() {
        PositionList blocks = new PositionList();
        for (int i = 0; i < 15; i++) {
            blocks.add(new Position(2 + i, HEIGHT - 10));
            blocks.add(new Position(WIDTH - i, 8));
            blocks.add(new Position(WIDTH / 2 - 2, i));
            blocks.add(new Position(WIDTH / 2 + 3, HEIGHT - i - 1));
        }
        return blocks;
    }

    public static PositionList createLevel5() {
        PositionList blocks = new PositionList();
        for (int x = 0; x < WIDTH; x++) {
            blocks.add(new Position(x, 0));
            blocks.add(new Position(x, HEIGHT - 1));
        }
        for (int y = 1; y < HEIGHT - 1; y++) {
            if (y < HEIGHT / 2 - 2 || y > HEIGHT / 2 + 1) {
                blocks.add(new Position(0, y));
                blocks.add(new Position(WIDTH - 1, y));
            }
        }
        for (int y = 5; y < HEIGHT - 5; y++) {
            blocks.add(new Position(WIDTH / 2 - 6, y));
            blocks.add(new Position(WIDTH / 2 + 4, y));
        }
        return blocks;
    }

    public static PositionList createLevelLabyrinth() {
        PositionList blocks = new PositionList();
        blocks.add(new Position(0, 0));
        blocks.add(new Position(1, 0));
        blocks.add(new Position(2, 0));
        blocks.add(new Position(3, 0));
        blocks.add(new Position(0, 1));
        blocks.add(new Position(0, 2));
        for (int x = 7; x < WIDTH - 4; x++) {
            blocks.add(new Position(x, 0));
        }
        for (int x = 0; x < WIDTH; x++) {
            if (x < WIDTH / 2 - 3 || x > WIDTH / 2 + 1) {
                blocks.add(new Position(x, HEIGHT / 3));
            }
        }
        for (int y = 0; y < HEIGHT / 3; y++) {
            blocks.add(new Position(WIDTH / 2 - 4, y));
        }
        for (int x = 0; x < WIDTH; x++) {
            blocks.add(new Position(x, 2 * HEIGHT / 3));
        }
        for (int y = 2 * HEIGHT / 3 + 1; y < HEIGHT; y++) {
            blocks.add(new Position(WIDTH / 2, y));
        }
        return blocks;
    }
}
