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
package main;

import actors.Carrier;
import actors.Explosion;
import actors.ItemObject;
import actors.Projectile;
import actors.Ship;
import actors.ShipFactory;
import actors.Shooting;
import ais.WingmanAI;
import graphic.GamePanel;
import graphic.ShipMonitorPanel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import platform.Interface;
import platform.gamegrid.AI;
import platform.gamegrid.Actor;
import platform.gamegrid.ClockTimed;
import platform.gamegrid.GameData;
import platform.graphic.MainFrame;
import platform.graphic.MainPanel;
import platform.utils.Command;
import platform.utils.Constants;
import platform.utils.IO;
import platform.utils.Interpreter;
import platform.utils.Settings;
import platform.utils.Vector2D;

/**
 * Created 12.04.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class SpaceInvader extends GameData implements ClockTimed
{

    private static final long serialVersionUID = -7651896077404010480L;
    /**
     * **************************
     * Constants * **************************
     */

    public static final String APP_NAME = "Space Invader";
    public static final String AUTHOR = "Dominik Messerschmidt (dominik.messerschmidt@continental-corporation.com)";
    public static final String[] COAUTHORS = new String[]
    {
    };

    /**
     * Simulated time difference in ms between each step. If DELTA_T = 1000 /
     * fps the simulated time equals real time.
     */
    public static final int DELTA_T = 10;

    /**
     * Maps
     */
    public static final String MAPNAME_EARTH = "map_earth.gif";
    public static final int DEFAULT_MAP_WIDTH = 2600;
    public static final int DEFAULT_MAP_HEIGHT = 1800;

    /**
     * Minimap bounds on screen
     */
    public static final Rectangle MINIMAP_BOUNDS = new Rectangle(MainPanel.getScreenWidth() - 210, MainPanel.getScreenHeight() - 110, 200,
            100);
    public static final int MINIMAP_POINT_SIZE = 2;

    /**
     * Ship monitor panel bounds
     */
    public static final Rectangle SHIP_MONITOR_BOUNDS = new Rectangle(MainPanel.getScreenWidth() - 210, 40, 200, 260);

    /**
     * Event timer
     */
    public static final int TIMEOUT_MESSAGE = 180;
    public static final int TIMEOUT_LEVEL = 300;

    /**
     * Output settings
     */
    public static final int MAX_MESSAGE_COUNT = 5;
    public static final int LINEWIDTH = 60;
    /**
     * maximum number of available savefiles
     */
    public static final int MAX_SAVE_SLOTS = 5;

    /**
     * Filenames
     */
    public static final String SAVEFILE_BASENAME_ARCADE = "save_arcade_";
    public static final String SAVEFILE_BASENAME_ADVENTURE = "save_adventure_";
    public static final String SAVEFILE_EXTENSION = ".dat";
    public static final String SAVES_DIRECTORY = Constants.DATA_DIRECTORY;
    public static final String ICON_DIRECTORY = Constants.IMAGE_DIRECTORY + "icons/";

    /**
     * Directions
     */
    public static final int NORTH = 0;
    public static final int NORTHEAST = 45;
    public static final int EAST = 90;
    public static final int SOUTHEAST = 135;
    public static final int SOUTH = 180;
    public static final int SOUTHWEST = 225;
    public static final int WEST = 270;
    public static final int NORTHWEST = 315;

    /**
     * Teams.
     *
     * @author Dominik Messerschmidt
     * <dominik.messerschmidt@continental-corporation.com> Created 27.10.2016
     */
    public enum Team
    {

        EARTH,
        ALIEN,
        PASSIV;

        public static Team get(int id)
        {
            return values()[id];
        }
    }

    /**
     * Game Modes.
     *
     * @author Dominik Messerschmidt
     * <dominik.messerschmidt@continental-corporation.com> Created 27.10.2016
     */
    public enum Mode
    {

        ARCADE,
        ADVENTURE;
    }

    /**
     * Singleton instance.
     */
    private static SpaceInvader instance;

    protected final CopyOnWriteArrayList<Map> maps;
    protected Map currentMap;
    protected final CopyOnWriteArrayList<WingmanAI> fleet;
    protected Ship mShip;
    protected int mShipIndex, maxFuel;
    protected double fuel;
    protected int level, score;
    protected Mode mode;
    protected boolean gameover;
    protected int nextLvlDelay = -1, saveslot;
    protected final CopyOnWriteArrayList<Item> loadedItems;
    protected final CopyOnWriteArrayList<Item> storedItems;

    private SpaceInvader()
    {
        maps = new CopyOnWriteArrayList<>();
        fleet = new CopyOnWriteArrayList<>();
        storedItems = new CopyOnWriteArrayList<>();
        loadedItems = new CopyOnWriteArrayList<>();
    }

    // Superclass methods
    @Override
    public void reset()
    {
        this.clear();
        maps.clear();
        fleet.clear();
        storedItems.clear();
        loadedItems.clear();
        maps.add(new Map(MAPNAME_EARTH, new java.awt.Rectangle(1030, 540, 500, 480), new Vector2D(
                0, 0)));
        setMap(maps.get(0));

        Ship s = ShipFactory.createStartShip();
        s.setMaxSpeed(200);
        // s.setAI(new ais.HunterAI());
        // Carrier s = Factory.createCarrier(1200, 700, Team.EARTH, 20000, 20, 5,
        // Factory.getRifleGun(), Factory.getRifleGun(), Factory.getRapidGun(), Factory.getRapidGun(),
        // Factory.getSalvoGun(), Factory.getSalvoGun(), Factory.get5BurstGun(), Factory.get8BurstGun());
        // s.setProduceFleets(true);
        // s.setAI(null);
        // Carrier s = Factory.createCarrier(1200, 700, Team.EARTH, 10);
        // s.setProductionRate(200);
        setMShip(s);
        addActor(s);
        nextLvlDelay = -1;
        gameover = false;
        mShipIndex = 0;
        level = 0;
        score = 200000;
        maxFuel = 10000000;
        fuel = maxFuel;
    }

    @Override
    public int getHeight()
    {
        if (currentMap != null)
        {
            return currentMap.HEIGHT;
        }
        return MainPanel.getScreenHeight();
    }

    @Override
    public int getWidth()
    {
        if (currentMap != null)
        {
            return currentMap.WIDTH;
        }
        return MainPanel.getScreenWidth();
    }

    // public methods
    @Override
    public void act()
    {
        // hinder acceleration without fuel
        if (mShip.isAccelerating() && fuel <= 0)
        {
            mShip.setAccelerating(false);
        }
        // let actors act, shoot and collide
        for (Actor a : getActors())
        {
            a.clock();
            if (Shooting.class.isInstance(a))
            {
                if (((Shooting) a).isShooting())
                {
                    for (Projectile p : ((Shooting) a).shoot())
                    {
                        addActor(p);
                    }
                }
            }
            else if (Projectile.class.isInstance(a))
            {
                for (Actor other : getActors())
                {
                    if (Ship.class.isInstance(other)
                            && isEnemy(a.getTeam(), other.getTeam()))
                    {
                        if (!a.isInvalid() && a.isColiding(other))
                        {
                            a.invalidate();
                            ((Ship) other).hit((Projectile) a);
                        }
                    }
                }
            }
        }
        // Destroy invalid actors
        for (Actor a : getActors())
        {
            if (a.isInvalid())
            {
                removeActor(a);
                if (a.equals(mShip) && !switchMShip())
                {
                    gameover = true;
                }
                if (Ship.class.isInstance(a))
                {
                    if (isEnemy(a.getTeam(), mShip.getTeam()))
                    {
                        IO.println(IO.translate("ENEMY_KILLED"), IO.MessageType.IMPORTANT);
                        addScore(((Ship) a).getMaxHp() * 2);
                        ItemObject item = new ItemObject(getRandomItems(level));
                        item.setX(a.getX());
                        item.setY(a.getY());
                        addActor(item);
                    }
                    else
                    {
                        IO.println(IO.translate("ALLY_KILLED"), IO.MessageType.IMPORTANT);
                    }
                    addActor(new Explosion(a.getXOnScreen(), a.getYOnScreen()));
                }
            }
        }
        // calculate fuel consumption
        double fuelConsum = getFuelConsum();
        fuel -= fuelConsum;
        if (fuel <= 0)
        {
            fuel = 0;
        }

        if (mode == Mode.ARCADE)
        {
            checkLevelState();
        }
        ShipMonitorPanel.getInstance().refresh();
    }

    public double getFuelConsum()
    {
        double fuelConsum = 0;
        if (mShip != null)
        {
            if (mShip.isAccelerating())
            {
                fuelConsum += mShip.getMaxPower() * DELTA_T / 1000.0;
            }
            if (mShip.getSuperShieldOn())
            {
                fuelConsum += mShip.getShieldPower() * DELTA_T;
            }
        }
        return fuelConsum;
    }

    public void checkLevelState()
    {
        if (nextLvlDelay > 0)
        {
            nextLvlDelay--;
        }
        else if (nextLvlDelay == 0)
        {
            if (level > 0)
            {
                IO.println(IO.translate("LEVEL_START") + " " + level, IO.MessageType.IMPORTANT);
                if (level % 10 == 0)
                {
                    IO.println(IO.translate("MOTHERSHIPAPPROACH"), IO.MessageType.IMPORTANT);
                    Carrier mothership = ShipFactory.createMothership(100, 100, Team.ALIEN, level * 50,
                            level / 10);
                    mothership.setProductionRate(level / 10);
                    addActor(mothership);
                }
                else if (level % 5 == 0)
                {
                    IO.println(IO.translate("HUNTERAPPROACH"), IO.MessageType.IMPORTANT);
                    for (int i = 0; i < level / 5; i++)
                    {
                        Ship leader;
                        do
                        {
                            leader = ShipFactory.createAlienHunter(
                                    (int) (Math.random() * currentMap.WIDTH),
                                    (int) (Math.random() * currentMap.HEIGHT), level);
                        }
                        while (this.isInLandingZone(leader));
                        addActor(leader);
                        for (Ship wingman : ShipFactory.createFleet(leader,
                                ShipFactory.DEFAULT_FLEET_SIZE))
                        {
                            addActor(wingman);
                        }
                    }
                }
                else
                {
                    Actor neugegner;
                    for (int i = 0; i < level; i++)
                    {
                        do
                        {
                            neugegner = ShipFactory.createAlienFighter(
                                    (int) (Math.random() * currentMap.WIDTH),
                                    (int) (Math.random() * currentMap.HEIGHT), level);
                        }
                        while (this.isInLandingZone(neugegner));
                        addActor(neugegner);
                    }
                }
            }
            nextLvlDelay = -1;
        }
        else if (allClear())
        {
            IO.println(IO.translate("LEVEL_FINISHED"), IO.MessageType.IMPORTANT);
            level++;
            nextLvlDelay = TIMEOUT_LEVEL;
        }
    }

    public int getLevelState()
    {
        return nextLvlDelay;
    }

    public void addToFleet(ais.WingmanAI ai)
    {
        fleet.add(ai);
    }

    public void removeFromFleet(ais.WingmanAI ai)
    {
        fleet.remove(ai);
    }

    public void toggleFleetCommand()
    {
        if (fleet.size() > 0)
        {
            int order = fleet.get(0).getOrder();
            if (order == ais.WingmanAI.ORDER_FOLLOW)
            {
                order = ais.WingmanAI.ORDER_STAY;
                IO.println(IO.translate("ORDER_STAY"), IO.MessageType.IMPORTANT);
            }
            else
            {
                order = ais.WingmanAI.ORDER_FOLLOW;
                IO.println(IO.translate("ORDER_FOLLOW"), IO.MessageType.IMPORTANT);
            }
            for (ais.WingmanAI ai : fleet)
            {
                ai.setOrder(order);
            }
        }
        else
        {
            IO.println(IO.translate("FLEET_EMPTY"), IO.MessageType.IMPORTANT);
        }
    }

    public boolean isGameOver()
    {
        return gameover;
    }

    public int getMaxFuel()
    {
        return maxFuel;
    }

    public int getFuel()
    {
        return (int) fuel;
    }

    public void setMaxFuel(int maxFuel)
    {
        this.maxFuel = maxFuel;
    }

    public double getFuelPrice()
    {
        return 0.001;
    }

    public void refuel()
    {
        fuel = maxFuel;
        IO.println("SpaceInvader.refuel()", IO.MessageType.DEBUG);
    }

    public Ship getMShip()
    {
        return mShip;
    }

    public boolean switchMShip()
    {
        IO.println("SpaceInvader.switchMShip()", IO.MessageType.DEBUG);
        List<Ship> allys = getAllyships(mShip.getTeam());
        allys.remove(mShip);
        if (allys.size() > 0)
        {
            while (mShipIndex >= allys.size())
            {
                mShipIndex -= allys.size();
            }
            if (mShipIndex < 0)
            {
                mShipIndex = 0;
            }
            setMShip(allys.get(mShipIndex));
            mShipIndex++;
            return true;
        }
        return false;
    }

    public void setMShip(Ship ship)
    {
        IO.println("SpaceInvader.setMShip()", IO.MessageType.DEBUG);
        if (ship == null)
        {
            throw new IllegalArgumentException("MShip must not be null!");
        }
        // removeActor(mShip);
        // switch AIs
        if (mShip != null)
        {
            AI ai = mShip.getAI();
            mShip.setAI(ship.getAI());
            ship.setAI(ai);
        }

        mShip = ship;
        //addActor(mShip);
        //mShip.stop();
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        if (level > 0)
        {
            this.level = level;
        }
    }

    public int getScore()
    {
        return score;
    }

    public boolean addScore(int score)
    {
        if (score < 0)
        {
            if (Math.abs(score) > this.score)
            {
                return false;
            }
            if (Math.abs(score) == 1)
            {
                IO.println(
                        IO.translate("LOST") + " " + Math.abs(score) + " "
                        + IO.translate("CURRENCY_SINGULAR"), IO.MessageType.IMPORTANT);
            }
            else
            {
                IO.println(
                        IO.translate("LOST") + " " + Math.abs(score) + " "
                        + IO.translate("CURRENCY_PLURAL"), IO.MessageType.IMPORTANT);
            }
        }
        else if (score == 1)
        {
            IO.println(
                    IO.translate("EARNED") + " " + score + " " + IO.translate("CURRENCY_SINGULAR"),
                    IO.MessageType.IMPORTANT);
        }
        else
        {
            IO.println(
                    IO.translate("EARNED") + " " + score + " " + IO.translate("CURRENCY_PLURAL"),
                    IO.MessageType.IMPORTANT);
        }
        this.score += score;
        return true;
    }

    private Item[] getRandomItems(int seed)
    {
        int n1 = (int) (Math.random() * seed);
        Item[] items = new Item[n1];
        for (int i = 0; i < n1; i++)
        {
            int index;
            if (seed > Item.Type.values().length)
            {
                index = (int) (Item.Type.values().length * Math.random());
            }
            else
            {
                index = (int) (seed * Math.random());
            }
            int n2 = (int) (seed * Math.random()) + 1;
            items[i] = new Item(Item.Type.values()[index], n2);
        }
        return items;
    }

    public Item[] getLoadedItems()
    {
        Item[] items = new Item[loadedItems.size()];
        for (int i = 0; i < items.length; i++)
        {
            items[i] = loadedItems.get(i);
        }
        return items;
    }

    public Item getLoadedItem(int index)
    {
        if (index >= 0 && index < loadedItems.size())
        {
            return loadedItems.get(index);
        }
        else
        {
            return null;
        }
    }

    public void addLoadedItem(Item item)
    {
        IO.println("SpaceInvader.addLoadedItem(): " + item, IO.MessageType.DEBUG);
        loadedItems.add(item);
    }

    public boolean removeLoadedItem(Item item)
    {
        IO.println("SpaceInvader.removeLoadedItem(): " + item, IO.MessageType.DEBUG);
        return loadedItems.remove(item);
    }

    public Item[] getStoredItems()
    {
        Item[] items = new Item[storedItems.size()];
        for (int i = 0; i < items.length; i++)
        {
            items[i] = storedItems.get(i);
        }
        return items;
    }

    public Item getStoredItem(int index)
    {
        if (index >= 0 && index < storedItems.size())
        {
            return storedItems.get(index);
        }
        else
        {
            return null;
        }
    }

    public void addStoredItem(Item item)
    {
        IO.println("SpaceInvader.addStoredItem(): " + item, IO.MessageType.DEBUG);
        for (Item i : storedItems)
        {
            if (i.type == item.type)
            {
                i.setNumber(i.getNumber() + item.getNumber());
                return;
            }
        }
        storedItems.add(item);
    }

    public boolean removeStoredItem(Item item)
    {
        IO.println("SpaceInvader.removeStoredItem(): " + item, IO.MessageType.DEBUG);
        return storedItems.remove(item);
    }

    public Item[] getMarketItems()
    {
        Item[] marketItems = new Item[Item.Type.values().length];
        for (int i = 0; i < marketItems.length; i++)
        {
            marketItems[i] = new Item(Item.Type.values()[i], 1);
        }
        return marketItems;
    }

    public Mode getMode()
    {
        return mode;
    }

    public void setMode(Mode mode)
    {
        this.mode = mode;
    }

    public boolean isInLandingZone(Actor a)
    {
        Rectangle mbounds = a.getBounds();
        for (Rectangle bounds : getLandingZones(a.getTeam()))
        {
            if (bounds.contains(mbounds) && !bounds.getSize().equals(mbounds.getSize()))
            {
                return true;
            }
        }
        return false;
    }

    public List<Rectangle> getLandingZones(int team)
    {
        LinkedList<Rectangle> lzones = new LinkedList<>();
        if (currentMap != null && currentMap.LANDING_ZONE != null)
        {
            lzones.add(currentMap.LANDING_ZONE);
        }
        for (Ship s : getAllyships(team))
        {
            if (Carrier.class.isInstance(s))
            {
                lzones.add(s.getBounds());
            }
        }
        return lzones;
    }

    public boolean allClear()
    {
        return getEnemyCount() == 0;
    }

    public int getEnemyCount()
    {
        return getEnemyships(mShip.getTeam()).size();
    }

    public List<Ship> getEnemyships(int mTeam)
    {
        LinkedList<Ship> enemys = new LinkedList<>();
        for (Actor a : getActors())
        {
            if (Ship.class.isInstance(a) && isEnemy(a.getTeam(), mTeam))
            {
                enemys.add((Ship) a);
            }
        }
        return enemys;
    }

    public List<Ship> getAllyships(int mTeam)
    {
        LinkedList<Ship> allys = new LinkedList<>();
        for (Actor a : getActors())
        {
            if (Ship.class.isInstance(a) && isAlly(a.getTeam(), mTeam))
            {
                allys.add((Ship) a);
            }
        }
        return allys;
    }

    public Map getMap()
    {
        return currentMap;
    }

    public Map[] getMaps()
    {
        Map[] m = new Map[maps.size()];
        for (int i = 0; i < m.length; i++)
        {
            m[i] = maps.get(i);
        }
        return m;
    }

    public Map getMapAt(Vector2D position)
    {
        for (Map m : maps)
        {
            if (((int) m.position.x == (int) position.x) && ((int) m.position.y == (int) position.y))
            {
                return m;
            }
        }
        Map m = Map.getRandomMap(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, position);
        maps.add(m);
        return m;
    }

    public void setMap(Map map)
    {
        currentMap = map;
        GamePanel.INSTANCE.setMap(map);
    }

    public void switchMap()
    {
        Map next;
        if (mShip.getX() <= 0)
        {
            next = getMapAt(currentMap.position.add(new Vector2D(-1, 0)));
            mShip.setX(next.WIDTH - mShip.getBounds().width - 10);
            switchMap(next);
        }
        else if (mShip.getX() + mShip.getBounds().width > currentMap.WIDTH)
        {
            next = getMapAt(currentMap.position.add(new Vector2D(1, 0)));
            mShip.setX(10);
            switchMap(next);
        }
        else if (mShip.getY() < 0)
        {
            next = getMapAt(currentMap.position.add(new Vector2D(0, -1)));
            mShip.setY(currentMap.HEIGHT - mShip.getBounds().height - 10);
            switchMap(next);
        }
        else
        {
            next = getMapAt(currentMap.position.add(new Vector2D(0, 1)));
            mShip.setY(10);
            switchMap(next);
        }
    }

    public void switchMap(Map map)
    {
        setMap(map);
        //mShip.stop();
        for (Actor a : getActors())
        {
            if (!a.equals(mShip)
                    && (!Ship.class.isInstance(a) || !WingmanAI.class.isInstance(((Ship) a).getAI()) || !fleet
                    .contains((((Ship) a).getAI()))))
            {
                removeActor(a);
            }
            else if (!a.equals(mShip))
            {
                a.setX(mShip.getX());
                a.setY(mShip.getY());
            }
        }
        level = (int) map.position.magnitude2();
        nextLvlDelay = 0;
        checkLevelState();
    }

    public void start(Mode mode, int saveslot)
    {
        this.mode = mode;
        this.saveslot = saveslot;
        MainFrame.FRAME.setMainPanel(GamePanel.INSTANCE);
        GamePanel.INSTANCE.setMap(instance.currentMap);
        IO.println("Started Game: " + mode + " " + saveslot, IO.MessageType.IMPORTANT);
    }

    // static functions
    public static boolean isEnemy(int team1, int team2)
    {
        return (team1 == Team.EARTH.ordinal() && team2 == Team.ALIEN.ordinal())
                || (team1 == Team.ALIEN.ordinal() && team2 == Team.EARTH.ordinal());
    }

    public static boolean isAlly(int team1, int team2)
    {
        return team1 == team2;
    }

    public static File getSaveFile(Mode mode, int saveslot)
    {
        if (mode == Mode.ADVENTURE)
        {
            return new File(SAVES_DIRECTORY + SAVEFILE_BASENAME_ADVENTURE + saveslot
                    + SAVEFILE_EXTENSION);
        }
        else
        {
            return new File(SAVES_DIRECTORY + SAVEFILE_BASENAME_ARCADE + saveslot
                    + SAVEFILE_EXTENSION);
        }
    }

    /**
     * Loads a SpaceInvader instance from the file associated with the given
     * mode and saveslot and assigns it to the currently used instance.
     *
     * @param mode Game Mode of the instance.
     * @param slot used saveslot.
     * @return true if the instance was succesfully loaded from the file.
     */
    public static synchronized boolean load(Mode mode, int slot)
    {
        SpaceInvader loaded = (SpaceInvader) SpaceInvader.load(getSaveFile(mode, slot));
        if (loaded != null)
        {
            instance = loaded;
            return true;
        }
        return false;
    }

    public static synchronized boolean save()
    {
        if (instance != null && !instance.gameover)
        {
            return instance.save(getSaveFile(instance.mode, instance.saveslot));
        }
        return false;
    }

    public static final SpaceInvader getInstance()
    {
        if (instance == null)
        {
            synchronized (SpaceInvader.class)
            {
                if (instance == null)
                {
                    instance = new SpaceInvader();
                }
            }
        }
        return instance;
    }

    /**
     * Post run actions. Executed just before logs are closed, settings are
     * saved and applications shutdown.
     */
    public static void postRun()
    {
        // IO.println("Produced Ships in this match: " + Factory.getProducedShips(),IO.MessageType.NORMAL);
        // IO.println("Produced Weapons in this match: " + Factory.getProducedWeapons(),IO.MessageType.NORMAL);
    }

    public static void init()
    {
        Interface.initAll();

        // settings
        if (!Settings.isDefined("drawlifes"))
        {
            Settings.set("drawlifes", false);
        }
        if (!Settings.isDefined("drawbounds"))
        {
            Settings.set("drawbounds", false);
        }
        if (!Settings.isDefined("toggleaccel"))
        {
            Settings.set("toggleaccel", true);
        }

        // events
        Interface.onClose = new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                postRun();
            }

        };
        Interface.onReset = new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                getInstance().reset();
                //MainPanel mainPanel = new graphic.MenuPanel();
                //MainFrame.FRAME.setMainPanel(mainPanel);
            }

        };

        // Console commands
        Interpreter.add(new Command("EXIT", "Exit the application")
        {

            @Override
            public boolean execute(String... params)
            {
                Interface.shutdown();
                return false;
            }
        });

        Interpreter.add(new Command("CREDITS", "Sets credits", "Credits")
        {

            @Override
            public boolean execute(String... params)
            {
                int n = Integer.parseInt(params[0]);
                instance.score = n;
                IO.println("Credits set to " + n, IO.MessageType.NORMAL);
                return false;
            }
        });
    }

    public static void main(String[] args)
    {
        final PrintStream stdout = System.out;
        init();
        System.setOut(new PrintStream(new OutputStream()
        {

            @Override
            public void write(int c) throws IOException
            {
                MainFrame.FRAME.writeToConsole("" + (char) c);
                stdout.append((char) c);
            }
        }));

        MainPanel mainPanel = new graphic.MenuPanel();
        MainFrame.FRAME.setMainPanel(mainPanel);

        IO.println(IO.translate("HELLO"), IO.MessageType.IMPORTANT);
    }
}
