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

import actors.Actor;
import actors.Carrier;
import actors.Cruiser;
import actors.Explosion;
import actors.Projectile;
import actors.Ship;
import actors.Shooting;
import ais.WingmanAI;
import armory.Factory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.Timer;
import main.GameData;
import main.Map;
import utils.Constants;
import utils.Constants.Team;
import utils.IO;
import utils.Settings;
import utils.Text;
import utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 25.03.2016
 */
public class GameGrid extends MainPanel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 189721991640899668L;
    private static GameGrid gameGrid;
    private final CopyOnWriteArrayList<Actor> actors = new CopyOnWriteArrayList<Actor>();
    private Ship mShip;
    private int mShipIndex;
    private final CopyOnWriteArrayList<ais.WingmanAI> fleet = new CopyOnWriteArrayList<>();
    private GameData gameData;
    private int nextLvlDelay = -1;
    private Image miniMapBG;
    private double miniMapScaleFactorX, miniMapScaleFactorY;
    private int viewX, viewY;
    private int creditsProgress = 0;
    private int saveslot;
    private Rectangle bounds;
    private String hint;
    private boolean gameover = false;
    private int messageTimer = Constants.TIMEOUT_MESSAGE;
    private final Timer clock = new Timer(1000 / utils.Settings.getSettings().fps, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            long starttime = System.nanoTime();
            doStep();
            double diffMs = (System.nanoTime() - starttime) / 1000000.0;
            double fps = 1000 / diffMs;
            if (fps < Settings.getSettings().fps) {
                IO.println(Text.FRAMERATE_WARNING + " (" + fps + " fps)", IO.MessageType.ERROR);
            }
        }
    });

    private GameGrid() {
        init();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private void init() {
        gameData = new GameData();
        setMShip(gameData.mShip);
        setMap(gameData.currentMap);
    }

    public void start(GameData.Mode mode, int slot) {
        saveslot = slot;
        gameData.mode = mode;
        MainFrame.FRAME.setMainPanel(this);
        MainFrame.FRAME.getContentPane().add(new graphic.SimControlPanel(),
                java.awt.BorderLayout.SOUTH);
        MainFrame.FRAME.pack();
        IO.println(Text.HELLO.toString(), IO.MessageType.IMPORTANT);
    }

    public void setFps(int fps) {
        Settings s = Settings.getSettings();
        s.fps = fps;
        Settings.setSettings(s);
        clock.setDelay(1000 / fps);
    }

    public void setPaused(boolean paused) {
        if (isPaused() && !paused) {
            clock.start();
            IO.println("clock started", IO.MessageType.DEBUG);
        } else if (!isPaused() && paused) {
            clock.stop();
            IO.println("clock stopped", IO.MessageType.DEBUG);
        }
    }

    public boolean isPaused() {
        return !clock.isRunning();
    }

    public void addActor(Actor a) {
        if (!actors.contains(a)) {
            actors.add(a);
        }
    }

    public void removeActor(Actor a) {
        actors.remove(a);
        if (fleet.contains(a.getAI())) {
            removeFromFleet((WingmanAI) a.getAI());
        }
    }

    public void addToFleet(ais.WingmanAI ai) {
        fleet.add(ai);
    }

    public void removeFromFleet(ais.WingmanAI ai) {
        fleet.remove(ai);
    }

    public void toggleFleetCommand() {
        if (fleet.size() > 0) {
            int order = fleet.get(0).getOrder();
            if (order == ais.WingmanAI.ORDER_FOLLOW) {
                order = ais.WingmanAI.ORDER_STAY;
                IO.println(Text.ORDER_STAY.toString(), IO.MessageType.IMPORTANT);
            } else {
                order = ais.WingmanAI.ORDER_FOLLOW;
                IO.println(Text.ORDER_FOLLOW.toString(), IO.MessageType.IMPORTANT);
            }
            for (ais.WingmanAI ai : fleet) {
                ai.setOrder(order);
            }
        } else {
            IO.println(Text.FLEET_EMPTY.toString(), IO.MessageType.IMPORTANT);
        }
    }

    public boolean switchMShip() {
        List<Ship> allys = getAllyships(mShip.team);
        allys.remove(mShip);
        if (allys.size() > 0) {
            while (mShipIndex >= allys.size()) {
                mShipIndex -= allys.size();
            }
            if (mShipIndex < 0) {
                mShipIndex = 0;
            }
            setMShip(allys.get(mShipIndex));
            mShipIndex++;
            return true;
        }
        return false;
    }

    public void setMShip(Ship ship) {
        if (ship == null) {
            throw new IllegalArgumentException("MShip must not be null!");
        }
        // removeActor(mShip);
        if (ais.WingmanAI.class.isInstance(ship.getAI())) {
            ship.setAI(mShip.getAI());
            mShip.setAI(new ais.WingmanAI());
        }
        mShip = ship;
        addActor(mShip);
        mShip.stop();
    }

    public Ship getMShip() {
        return mShip;
    }

    public GameData.Mode getGameMode() {
        return gameData.mode;
    }

    public static final synchronized void reset() {
        gameGrid = new GameGrid();
    }

    public static final GameGrid getInstance() {
        if (gameGrid == null) {
            synchronized (GameGrid.class) {
                if (gameGrid == null) {
                    gameGrid = new GameGrid();
                }
            }
        }
        return gameGrid;
    }

    public int getScore() {
        return gameData.score;
    }

    public boolean addScore(int score) {
        if (score < 0) {
            if (Math.abs(score) > gameData.score) {
                return false;
            }
            if (Math.abs(score) == 1) {
                IO.println(Text.LOST + " " + Math.abs(score) + " " + Text.CURRENCY_SINGULAR,
                        IO.MessageType.IMPORTANT);
            } else {
                IO.println(Text.LOST + " " + Math.abs(score) + " " + Text.CURRENCY_PLURAL,
                        IO.MessageType.IMPORTANT);
            }
        } else if (score == 1) {
            IO.println(Text.EARNED + " " + score + " " + Text.CURRENCY_SINGULAR,
                    IO.MessageType.IMPORTANT);
        } else {
            IO.println(Text.EARNED + " " + score + " " + Text.CURRENCY_PLURAL,
                    IO.MessageType.IMPORTANT);
        }
        gameData.score += score;
        return true;
    }

    public boolean isInGrid(int x, int y) {
        return x >= 0 && x < gameData.currentMap.WIDTH && y >= 0 && y < gameData.currentMap.HEIGHT;
    }

    public boolean isInGrid(Rectangle rect) {
        return bounds.contains(rect);
    }

    public void doStep() {
        for (Actor a : actors) {
            a.act();
            if (Shooting.class.isInstance(a)) {
                if (((Shooting) a).isShooting()) {
                    for (Projectile p : ((Shooting) a).shoot()) {
                        addActor(p);
                    }
                }
            }
            for (Actor other : actors) {
                if (a.getClass() == Projectile.class && Ship.class.isInstance(other)
                        && a.team.isEnemy(other.team)) {
                    if (!a.isInvalid() && a.isColiding(other)) {
                        a.invalidate();
                        ((Ship) other).hit((Projectile) a);
                    }
                }
            }
        }
        for (Actor a : actors) {
            if (a.isInvalid()) {
                removeActor(a);
                if (a.equals(mShip) && !switchMShip()) {
                    gameover = true;
                }
                if (Ship.class.isInstance(a)) {
                    addActor(new Explosion(a.getXOnScreen(), a.getYOnScreen()));
                    if (a.team.isEnemy(mShip.team)) {
                        IO.println(Text.ENEMY_KILLED.toString(), IO.MessageType.IMPORTANT);
                        addScore(((Ship) a).getMaxHp() * 2);
                    } else {
                        IO.println(Text.ALLY_KILLED.toString(), IO.MessageType.IMPORTANT);
                    }
                }
            }
        }
        if (gameData.mode == GameData.Mode.ARCADE) {
            checkLevelState();
        }
        createHint();
        ShipMonitorPanel.getInstance().refresh();
    }

    private void checkLevelState() {
        if (nextLvlDelay > 0) {
            nextLvlDelay--;
        } else if (nextLvlDelay == 0) {
            IO.println(Text.LEVEL_START + " " + gameData.level, IO.MessageType.IMPORTANT);
            if (gameData.level % 10 == 0) {
                IO.println(Text.MOTHERSHIPAPPROACH.toString(), IO.MessageType.IMPORTANT);
                Carrier mothership = Factory.createMothership(100, 100, Team.ALIEN, gameData.level * 50,
                        gameData.level / 10);
                mothership.setProductionRate(gameData.level / 10);
                addActor(mothership);
            } else if (gameData.level % 5 == 0) {
                IO.println(Text.HUNTERAPPROACH.toString(), IO.MessageType.IMPORTANT);
                for (int i = 0; i < gameData.level / 5; i++) {
                    Ship leader;
                    do {
                        leader = Factory.createAlienHunter((int) (Math.random() * gameData.currentMap.WIDTH),
                                (int) (Math.random() * gameData.currentMap.HEIGHT), gameData.level);
                    } while (this.isInLandingZone(leader));
                    addActor(leader);
                    for (Ship wingman : Factory.createFleet(leader, Constants.DEFAULT_FLEET_SIZE)) {
                        addActor(wingman);
                    }
                }
            } else {
                Actor neugegner;
                for (int i = 0; i < gameData.level; i++) {
                    do {
                        neugegner = Factory.createAlienFighter((int) (Math.random() * gameData.currentMap.WIDTH),
                                (int) (Math.random() * gameData.currentMap.HEIGHT), gameData.level);
                    } while (this.isInLandingZone(neugegner));
                    addActor(neugegner);
                }
            }
            nextLvlDelay = -1;
        } else if (allClear()) {
            IO.println(Text.LEVEL_FINISHED.toString(), IO.MessageType.IMPORTANT);
            gameData.level++;
            nextLvlDelay = Constants.TIMEOUT_LEVEL;
        }
    }

    private void createHint() {
        if (nextLvlDelay > 0) {
            setHint(Text.LEVEL_COUNTDOWN
                    + utils.Utilities.round(1.0 * nextLvlDelay / Settings.getSettings().fps, 1) + "s");
        } else if (mShip != null && gameData.currentMap != null && isInLandingZone(mShip)) {
            setHint(Text.PRESS + " " + InputConfig.Key.LAND.toString() + " " + Text.TOLAND);
        } else if (gameData.mode == GameData.Mode.ARCADE) {
            int n = getEnemyCount();
            if (n != 1) {
                setHint(Text.LEVEL + " " + gameData.level + " - " + n + " " + Text.ENEMYSLEFT);
            } else {
                setHint(Text.LEVEL + " " + gameData.level + " - " + n + " " + Text.ENEMYLEFT);
            }
        } else {
            setHint("Map: " + gameData.currentMap);
        }
    }

    public boolean isInLandingZone(Actor a) {
        Rectangle mbounds = a.getBounds();
        for (Rectangle bounds : getLandingZones(a.team)) {
            if (bounds.contains(mbounds) && !bounds.getSize().equals(mbounds.getSize())) {
                return true;
            }
        }
        return false;
    }

    public List<Rectangle> getLandingZones(Team team) {
        LinkedList<Rectangle> lzones = new LinkedList<>();
        if (gameData.currentMap != null && gameData.currentMap.LANDING_ZONE != null) {
            lzones.add(gameData.currentMap.LANDING_ZONE);
        }
        for (Ship s : getAllyships(team)) {
            if (Carrier.class.isInstance(s)) {
                lzones.add(s.getBounds());
            }
        }
        return lzones;
    }

    public void clear() {
        for (Actor a : actors) {
            if (!a.equals(mShip)) {
                removeActor(a);
            }
        }
    }

    public void setLevel(int level) {
        if (level > 0) {
            this.gameData.level = level;
        }
    }

    public boolean allClear() {
        return getEnemyCount() == 0;
    }

    public int getEnemyCount() {
        return getEnemyships(mShip.team).size();
    }

    public List<Ship> getEnemyships(Team mTeam) {
        LinkedList<Ship> enemys = new LinkedList<>();
        for (Actor a : actors) {
            if (Ship.class.isInstance(a) && a.team.isEnemy(mTeam)) {
                enemys.add((Ship) a);
            }
        }
        return enemys;
    }

    public List<Ship> getAllyships(Team mTeam) {
        LinkedList<Ship> allys = new LinkedList<>();
        for (Actor a : actors) {
            if (Ship.class.isInstance(a) && a.team.isAlly(mTeam)) {
                allys.add((Ship) a);
            }
        }
        return allys;
    }

    public Map getMap() {
        return gameData.currentMap;
    }

    public void setMap(Map map) {
        gameData.currentMap = map;
        this.bounds = new Rectangle(0, 0, map.WIDTH, map.HEIGHT);
        this.miniMapBG = map.getImage().getScaledInstance(Constants.MINIMAP_BOUNDS.width,
                Constants.MINIMAP_BOUNDS.height, 0);
        miniMapScaleFactorX = 1.0 * Constants.MINIMAP_BOUNDS.width / map.WIDTH;
        miniMapScaleFactorY = 1.0 * Constants.MINIMAP_BOUNDS.height / map.HEIGHT;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void land() {
        mShip.stop();
        MainFrame.FRAME.setMainPanel(new HangarPanel(mShip));
        mShip.repair();
        if (this.save()) {
            IO.println(Text.SAVED.toString(), IO.MessageType.IMPORTANT);
        }
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
    public void paintComponent(Graphics g) {
        BufferedImage screen = createGUI();
        g.drawImage(screen.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0),
                0, 0, this);
    }

    public BufferedImage createGUI() {
        BufferedImage gui = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gui.createGraphics();
        if (mShip != null) {
            viewX = mShip.getXOnScreen() - WIDTH / 2;
            if (viewX < 0) {
                viewX = 0;
            } else if (gameData.currentMap != null && viewX + WIDTH > gameData.currentMap.WIDTH) {
                viewX = gameData.currentMap.WIDTH - WIDTH;
            }
            viewY = mShip.getYOnScreen() - HEIGHT / 2;
            if (viewY < 0) {
                viewY = 0;
            } else if (gameData.currentMap != null && viewY + HEIGHT > gameData.currentMap.HEIGHT) {
                viewY = gameData.currentMap.HEIGHT - HEIGHT;
            }
        }
        g.setColor(Settings.getSettings().backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.translate(-viewX, -viewY);
        gameData.currentMap.paint(g);
        for (Actor a : actors) {
            a.paint(g);
        }
        if (Settings.getSettings().drawBounds) {
            drawBounds(g);
        }
        g.translate(viewX, viewY);
        drawInterface(g);
        return gui;
    }

    private void drawInterface(Graphics2D g) {
        if (gameover) {
            g.setColor(Color.red);
            g.setFont(new Font("Consolas", 0, 30));
            g.drawString(Text.GAMEOVER.toString(), WIDTH / 2 - 50, HEIGHT / 2);
            drawCredits(g);
        } else if (isPaused()) {
            g.setColor(Color.red);
            g.setFont(new Font("Consolas", 0, 30));
            g.drawString(Text.PAUSED.toString(), WIDTH / 2 - 50, HEIGHT / 2);
        }
        if (hint != null) {
            g.setColor(Settings.getSettings().fontColor);
            g.setFont(new Font("Consolas", 0, 24));
            g.drawString(hint, 10, 30);
        }
        if (!this.printQueueIsEmpty()) {
            g.setColor(Settings.getSettings().fontColor);
            g.setFont(Settings.getSettings().font);
            for (int i = 0; i < printQueue.size() && i < Constants.MAX_MESSAGE_COUNT; i++) {
                String msg = printQueue.get(printQueue.size() - i - 1);
                // TODO limit linewidth
                g.drawString(msg, 10, HEIGHT - i * 30);
            }
            if (messageTimer > 0) {
                messageTimer--;
            } else if (messageTimer == 0) {
                printNext();
                messageTimer = Constants.TIMEOUT_MESSAGE;
            }
        }
        g.setColor(Settings.getSettings().fontColor);
        g.setFont(new Font("Consolas", 0, 20));
        if (Math.abs(gameData.score) == 1) {
            g.drawString(Text.SCORE + ": " + gameData.score + " " + Text.CURRENCY_SINGULAR, WIDTH - 300, 30);
        } else {
            g.drawString(Text.SCORE + ": " + gameData.score + " " + Text.CURRENCY_PLURAL, WIDTH - 300, 30);
        }
        drawMinimap(g);
    }

    private void drawBounds(Graphics2D g) {
        g.setColor(Color.green);
        for (Actor a : actors) {
            Rectangle bounds = a.getBounds();
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
        g.setColor(Color.blue);
        for (Rectangle bounds : getLandingZones(mShip.team)) {
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private void drawMinimap(Graphics2D g) {
        g.drawImage(miniMapBG, Constants.MINIMAP_BOUNDS.x, Constants.MINIMAP_BOUNDS.y, null);
        int size = Constants.MINIMAP_POINT_SIZE;
        g.setColor(Color.red);
        for (Ship enemy : this.getEnemyships(mShip.team)) {
            g.fillRect((int) (enemy.getX() * miniMapScaleFactorX + Constants.MINIMAP_BOUNDS.x),
                    (int) (enemy.getY() * miniMapScaleFactorY + Constants.MINIMAP_BOUNDS.y), size, size);
        }
        g.setColor(Color.cyan);
        for (Ship ally : this.getAllyships(mShip.team)) {
            g.fillRect((int) (ally.getX() * miniMapScaleFactorX + Constants.MINIMAP_BOUNDS.x),
                    (int) (ally.getY() * miniMapScaleFactorY + Constants.MINIMAP_BOUNDS.y), size, size);
        }
        g.setColor(Color.green);
        g
                .fillRect((int) (mShip.getX() * miniMapScaleFactorX + Constants.MINIMAP_BOUNDS.x),
                        (int) (mShip.getY() * miniMapScaleFactorY + Constants.MINIMAP_BOUNDS.y), size + 2, size + 2);
        g.setColor(Color.white);
        g.drawRect(Constants.MINIMAP_BOUNDS.x, Constants.MINIMAP_BOUNDS.y,
                Constants.MINIMAP_BOUNDS.width, Constants.MINIMAP_BOUNDS.height);
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

    @Override
    public void keyPressed(KeyEvent evt) {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null && !gameover) {
            switch (key) {
                case ACCELERATE:
                    if (!isPaused()) {
                        mShip.setAccelerating(true);
                    }
                    break;
                case BRAKE:
                    if (!isPaused()) {
                        mShip.setBraking(true);
                    }
                    break;
                case TURN_LEFT:
                    if (!isPaused()) {
                        mShip.rotateLeft();
                    }
                    break;
                case TURN_RIGHT:
                    if (!isPaused()) {
                        mShip.rotateRight();
                    }
                    break;
                case PAUSE:
                    setPaused(!isPaused());
                    break;
                case SHOOT:
                    if (!isPaused()) {
                        mShip.setShooting(true);
                    }
                    break;
                case LAND:
                    if (!isPaused()) {
                        if (isInLandingZone(mShip)) {
                            land();
                        } else {
                            IO.println(Text.NOT_IN_LZONE.toString(), IO.MessageType.IMPORTANT);
                        }
                    }
                    break;
                case SPECIAL:
                    toggleFleetCommand();
                    break;
                default:
                    break;
            }
        } else {
            IO.println("unsupported key", IO.MessageType.DEBUG);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null) {
            switch (key) {
                case ACCELERATE:
                    mShip.setAccelerating(false);
                    break;
                case BRAKE:
                    mShip.setBraking(false);
                    break;
                case TURN_LEFT:
                case TURN_RIGHT:
                    mShip.stopRotate();
                    break;
                case SHOOT:
                    mShip.setShooting(false);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        if (Settings.getSettings().mouseEnabled && Cruiser.class.isInstance(mShip)
                && mShip.getAI() == null) {
            setTarget((Cruiser) mShip, evt.getX(), evt.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent evt) {
        if (Settings.getSettings().mouseEnabled && Cruiser.class.isInstance(mShip)
                && mShip.getAI() == null) {
            setTarget((Cruiser) mShip, evt.getX(), evt.getY());
        }
    }

    private void setTarget(Cruiser me, int x, int y) {
        Vector2D target = new Vector2D(x * WIDTH / getWidth() + viewX, y * HEIGHT / getHeight()
                + viewY);
        me.setTarget(target);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if (Settings.getSettings().mouseEnabled && Cruiser.class.isInstance(mShip)) {
            mShip.setShooting(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if (Settings.getSettings().mouseEnabled && Cruiser.class.isInstance(mShip)) {
            mShip.setShooting(false);
        }
    }

    public File getSaveFile() {
        if (gameData.mode == GameData.Mode.ADVENTURE) {
            return new File(Constants.SAVES_DIRECTORY + Constants.SAVEFILE_BASENAME_ADVENTURE + saveslot + Constants.SAVEFILE_EXTENSION);
        } else {
            return new File(Constants.SAVES_DIRECTORY + Constants.SAVEFILE_BASENAME_ARCADE + saveslot + Constants.SAVEFILE_EXTENSION);
        }
    }

    public boolean save() {
        if (gameover) {
            IO.println("The game is over", IO.MessageType.IMPORTANT);
            return false;
        } else {
            gameData.actors = this.actors;
            gameData.fleet = this.fleet;
            gameData.mShip = this.mShip;
            return gameData.save(getSaveFile());
        }
    }

    public boolean load(GameData.Mode mode, int slot) {
        saveslot = slot;
        gameData.mode = mode;
        GameData loaded = GameData.load(getSaveFile());
        if (loaded != null) {
            this.gameData = loaded;
            this.actors.clear();
            this.actors.addAll(gameData.actors);
            this.fleet.clear();
            this.fleet.addAll(gameData.fleet);
            setMShip(gameData.mShip);
            setMap(gameData.currentMap);
            return true;
        }
        return false;
    }

    public void switchMap() {
        if (gameData.mShip.getX() <= 0) {
            switchMap(gameData.getMapAt(gameData.currentMap.position.add(new Vector2D(-1, 0))));
            gameData.mShip.setX(gameData.currentMap.WIDTH - gameData.mShip.getBounds().width - 10);
        } else if (gameData.mShip.getX() + gameData.mShip.getBounds().width > gameData.currentMap.WIDTH) {
            switchMap(gameData.getMapAt(gameData.currentMap.position.add(new Vector2D(1, 0))));
            gameData.mShip.setX(10);
        } else if (gameData.mShip.getY() < 0) {
            switchMap(gameData.getMapAt(gameData.currentMap.position.add(new Vector2D(0, -1))));
            gameData.mShip.setY(gameData.currentMap.HEIGHT - gameData.mShip.getBounds().height - 10);
        } else {
            switchMap(gameData.getMapAt(gameData.currentMap.position.add(new Vector2D(0, 1))));
            gameData.mShip.setY(10);
        }
    }

    public void switchMap(Map map) {
        setMap(map);
        gameData.mShip.stop();
        for (Actor a : gameData.actors) {
            if (!Ship.class.isInstance(a) || !WingmanAI.class.isInstance(((Ship) a).getAI()) || !gameData.fleet.contains((WingmanAI) (((Ship) a).getAI()))) {
                gameData.actors.remove(a);
            } else if (!isInGrid(a.getBounds())) {
                a.setX(gameData.mShip.getX());
                a.setY(gameData.mShip.getY());
            }
        }
        gameData.level = (int) map.position.magnitude2();
        nextLvlDelay = 0;
        checkLevelState();
    }
}
