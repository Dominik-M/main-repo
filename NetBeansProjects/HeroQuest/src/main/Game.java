/*
 * Copyright (C) 2022 Dominik Messerschmidt
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
package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import main.Board.Field;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Game implements Serializable
{

    public static final int MOVE_DICE = 6,
            HIT_RATE_HERO = 55,
            HIT_RATE_ENEMY = 40,
            BLOCK_RATE_HERO = 33,
            BLOCK_RATE_ENEMY = 20,
            CRIT_RATE = 5,
            TREASURE_RATE = 60,
            TRAP_RATE = 20,
            TRAP_DIFFUSE_CHANCE = 80,
            TRAP_EVADE_CHANCE = 33,
            TRAP_DAMAGE = 3,
            MAX_TREASURE_GOLD = 100;

    public enum TurnState
    {
        ENEMYS_TURN_MOVE,
        ENEMYS_TURN_ACTION,
        HEROES_TURN_ROLL,
        HEROES_TURN_MOVE,
        HEROES_TURN_MOVE_DOORAHEAD,
        HEROES_TURN_ACTION,
        INVALID
    }

    public enum Action
    {
        INVALID,
        NOTHING,
        ATTACK,
        LOOT,
        OPEN,
        SEARCH
    }

    private static Game instance;

    private final Actor[] HEROES = new Actor[]
    {
        ActorFactory.getDefaultActorOfType(Actor.ActorType.BARBAR),
        ActorFactory.getDefaultActorOfType(Actor.ActorType.DWARF),
        ActorFactory.getDefaultActorOfType(Actor.ActorType.ELF),
        ActorFactory.getDefaultActorOfType(Actor.ActorType.MAGE)
    };

    private final Board board;
    private final LinkedList<GameListener> listeners;
    private final LinkedList<Actor> actors;
    private int turn, dice, selX, selY;
    private TurnState state;
    private Action selectedAction;
    private LinkedList<Field> currentFieldsInRange, currentFieldsInSight;
    private Quest currentQuest;
    private boolean questDone;

    private Game()
    {
        System.out.println("Creating a new Game instance");
        actors = new LinkedList<>();
        listeners = new LinkedList<>();
        board = new Board();
        currentFieldsInRange = new LinkedList<>();
        currentFieldsInSight = new LinkedList<>();
        init();
    }

    private void init()
    {
        actors.clear();
        turn = 0;
        dice = -1;
        selX = -1;
        selY = -1;
        questDone = false;
        currentQuest = null;
        selectedAction = Action.INVALID;
        state = TurnState.HEROES_TURN_ROLL;
    }

    public void addGameListener(GameListener l)
    {
        listeners.add(l);
    }

    public static final Game getInstance()
    {
        if (instance == null)
        {
            synchronized (Game.class)
            {
                if (instance == null)
                {
                    instance = new Game();
                }
            }
        }
        return instance;
    }

    public Board getBoard()
    {
        return board;
    }

    public TurnState getState()
    {
        return state;
    }

    public static int roll(int max)
    {
        int n = (int) (Math.random() * max + 1);
        System.out.println("roll(" + max + "): " + n);
        return n;
    }

    public void select(int x, int y)
    {
        System.out.println("Selected: " + x + "|" + y);
        Field f = board.getField(x, y);
        if (f != null)
        {
            System.out.println("Field = " + f);
            Board.WallState[] walls = board.getWallsaround(f);
            System.out.println("Wallsaround:");
            for (Board.WallState wall : walls)
            {
                System.out.println(wall);
            }
        }
        else
        {
            System.out.println("Out of bounds");
        }
        selX = x;
        selY = y;
    }

    public void select(Action a)
    {
        selectedAction = a;
    }

    public Action getSelectedAction()
    {
        return selectedAction;
    }

    public int getSelectedX()
    {
        return selX;
    }

    public int getSelectedY()
    {
        return selY;
    }

    public boolean isInMoveRange(int x, int y)
    {
        for (Field f : currentFieldsInRange)
        {
            if (f.x == x && f.y == y)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isInSight(int x, int y)
    {
        for (Field f : currentFieldsInSight)
        {
            if (f.x == x && f.y == y)
            {
                return true;
            }
        }
        return false;
    }

    public void moveActor(Actor a, int x, int y)
    {
        message(a + " moves to " + x + "|" + y);
        a.setX(x);
        a.setY(y);
    }

    public void turn()
    {
        Actor a = getActorOnTurn();
        currentFieldsInSight = board.getFieldsInSight(a.getX(), a.getY());
        System.out.println("turn(): turn = " + turn + " (" + a + ") state = " + state);
        switch (state)
        {
            case HEROES_TURN_ROLL:
            {
                int d1 = roll(MOVE_DICE);
                int d2 = roll(MOVE_DICE);
                message("Rolled " + d1 + " and " + d2);
                dice = d1 + d2;
                currentFieldsInRange = board.getFieldsInRange(a.getX(), a.getY(), dice, a.isHero());
                state = TurnState.HEROES_TURN_MOVE;
                message("Select a field to move");
                break;
            }
            case HEROES_TURN_MOVE:
            {
                if (selectedAction == Action.NOTHING)
                {
                    message(a + " does not move");
                }
                else if (isInMoveRange(selX, selY))
                {
                    moveActor(a, selX, selY);
                    currentFieldsInSight = board.getFieldsInSight(a.getX(), a.getY());
                    // TODO check the path walked
                    Field f = board.getField(a.getX(), a.getY());
                    Item item = f.getItem();
                    if (item != null)
                    {
                        if (GoldItem.class.isInstance(item))
                        {
                            a.setGold(a.getGold() + item.getValue());
                        }
                        else
                        {
                            a.addItem(item);
                        }
                        message(a + " got " + item);
                        f.setItem(null);
                    }
                    if (f.getTrap())
                    {
                        hitTrap(f.x, f.y);
                    }
                    dice = 0; // TODO calculate actual distance moved
                }
                else
                {
                    message("Selection invalid");
                    break;
                }
                boolean doorInRange = false;
                Field f = board.getField(a.getX(), a.getY());
                for (Board.WallState wall : board.getWallsaround(f))
                {
                    if (wall == Board.WallState.DOOR_CLOSED)
                    {
                        doorInRange = true;
                        break;
                    }
                }
                if (doorInRange)
                {
                    message("There is a door ahead. Will you open it?");
                    state = TurnState.HEROES_TURN_MOVE_DOORAHEAD;
                }
                else
                {
                    selectedAction = Action.INVALID;
                    message("Select an action for " + a);
                    state = TurnState.HEROES_TURN_ACTION;
                }
                selX = -1;
                selY = -1;
                currentFieldsInRange.clear();
                break;
            }
            case HEROES_TURN_MOVE_DOORAHEAD:
            {
                switch (selectedAction)
                {
                    case OPEN:
                        boolean dooOpened = false;
                        Field f = board.getField(a.getX(), a.getY());
                        int i = 0;
                        for (Board.WallState wall : board.getWallsaround(f))
                        {
                            if (wall == Board.WallState.DOOR_CLOSED)
                            {
                                board.setWallAt(f.x, f.y, i, Board.WallState.DOOR_OPEN);
                                dooOpened = true;
                                break;
                            }
                            i++;
                        }
                        if (dooOpened)
                        {
                            message("Opened door");
                            currentFieldsInSight = board.getFieldsInSight(a.getX(), a.getY());
                            switch (i)
                            {
                                case Board.UP:
                                    f = board.getField(a.getX(), a.getY() - 1);
                                    break;
                                case Board.RIGHT:
                                    f = board.getField(a.getX() + 1, a.getY());
                                    break;
                                case Board.DOWN:
                                    f = board.getField(a.getX(), a.getY() + 1);
                                    break;
                                case Board.LEFT:
                                    f = board.getField(a.getX() - 1, a.getY());
                                    break;
                            }
                            revealRoom(f.roomID);

                        }
                        if (dice > 0)
                        {
                            currentFieldsInRange = board.getFieldsInRange(a.getX(), a.getY(), dice, a.isHero());
                            state = TurnState.HEROES_TURN_MOVE;
                            message("Select a field to move");
                            break;
                        }
                    // intended fall through
                    case NOTHING:
                        selectedAction = Action.INVALID;
                        message("Select an action for " + a);
                        state = TurnState.HEROES_TURN_ACTION;
                        break;
                    default:
                        break;
                }
                break;
            }
            case HEROES_TURN_ACTION:
            {
                boolean actionPerformed = false;
                switch (selectedAction)
                {
                    case ATTACK:
                        Actor other = getActorAt(selX, selY);
                        if (other == null)
                        {
                            message("No valid target selected");
                        }
                        else
                        {
                            if (isInAttackRange(a, other))
                            {
                                fight(a, other);
                                actionPerformed = true;
                            }
                            else
                            {
                                message(other + " is not in range");
                            }
                        }
                        break;
                    case LOOT:
                        message(a + " loots the room");
                        loot();
                        actionPerformed = true;
                        break;
                    case SEARCH:
                        message(a + " searches the room.");
                        message("No secret doors.");
                        revealTrapsInRoom(board.getField(a.getX(), a.getY()).roomID);
                        actionPerformed = true;
                        break;
                    case NOTHING:
                        message(a + " enjoys the moment");
                        actionPerformed = true;
                        break;
                    default:
                        message("Invalid Action: " + selectedAction);
                        break;
                }
                if (actionPerformed)
                {
                    selectedAction = Action.INVALID;
                    selX = -1;
                    selY = -1;
                    nextTurn();
                }
                break;
            }
            case ENEMYS_TURN_MOVE:
            {
                currentFieldsInRange = board.getFieldsInRange(a.getX(), a.getY(), a.getSpd(), a.isHero());
                Actor tgt;
                Field dest = board.getField(a.getX(), a.getY());
                for (Field f : currentFieldsInRange)
                {
                    for (Field f2 : board.getAdjacentFields(f.x, f.y, true, a.getWeapon().canDiagonal))
                    {
                        tgt = getActorAt(f2.x, f2.y);
                        if (tgt != null && tgt.isHero())
                        {
                            dest = f;
                            break;
                        }
                    }
                }
                moveActor(a, dest.x, dest.y);
                state = TurnState.ENEMYS_TURN_ACTION;
                break;
            }
            case ENEMYS_TURN_ACTION:
            {
                // Attack Hero in range
                Actor tgt;
                for (Field f : board.getAdjacentFields(a.getX(), a.getY(), true, a.getWeapon().canDiagonal))
                {
                    tgt = getActorAt(f.x, f.y);
                    if (tgt != null && tgt.isHero())
                    {
                        fight(a, tgt);
                        break;
                    }
                }
                nextTurn();
                break;
            }
            default:
                System.err.println("Invalid TurnState: " + state);
                break;
        }
        checkQuestDone();
        for (GameListener l : listeners)
        {
            l.stateChanged(state);
        }
    }

    public boolean checkQuestDone()
    {

        if (!questDone && currentQuest != null)
        {
            questDone = currentQuest.isDone();
            if (questDone)
            {
                message("Mission successful!");
            }
        }
        return questDone;
    }

    public void fight(Actor a, int atk, Actor b)
    {
        int dmg = 0;
        for (int i = 0; i < atk; i++)
        {
            int n = roll(100);
            if (n < CRIT_RATE) // critical hit
            {
                message("Critical Hit!");
                dmg++;
            }
            if (n < (a.isHero() ? HIT_RATE_HERO : HIT_RATE_ENEMY)) // hit
            {
                dmg++;
            }
            if (n >= 99) // critical fail
            {
                message("Critical Fail!");
                dmg = 0;
                break;
            }
        }
        if (dmg > 0)
        {
            if (dmg > 1)
            {
                message(dmg + " hits!");
            }
            else
            {
                message("One hit");
            }
            message(b + " can defend with " + b.getDef() + " dices");
            int def = 0;
            for (int i = 0; i < b.getDef(); i++)
            {
                int n = roll(100);
                if (n < (b.isHero() ? BLOCK_RATE_HERO : BLOCK_RATE_ENEMY)) // blocked
                {
                    def++;
                }
            }
            if (def > 0)
            {
                message(b + " blocked " + def);
            }
            dmg -= def;
            if (dmg > 0)
            {
                message(b + " got " + dmg + " damage");
                b.setHp(b.getHp() - dmg);
                if (b.getHp() <= 0)
                {
                    message(b + " fainted.");
                    removeActor(b);
                }
            }
            else
            {
                message(b + " got no damage");
            }
        }
        else
        {
            message(a + " missed");
        }
    }

    public void fight(Actor a, Actor b)
    {
        int atk = a.getAtk();
        message(a + " attacks " + b + " with " + atk + " attack dices");
        fight(a, atk, b);
    }

    public Item getRandomItem()
    {
        int n = roll(100);
        if (n == 0)
        {
            return new WeaponItem(Weapon.DRAGONSWORD5);
        }
        if (n < 60)
        {
            return Item.POTION;
        }
        else
        {
            return Item.THROWING_KNIFE;
        }
    }

    public boolean isNoEnemyOnBoard()
    {
        for (Actor a : actors)
        {
            if (a.isEnemy())
            {
                return false;
            }
        }
        return true;
    }

    private void loot()
    {
        int n = roll(100);
        if (n < TREASURE_RATE)
        {
            if (n < TREASURE_RATE / 3)
            {
                Item item = getRandomItem();
                message("Found a " + item);
                Actor a = getActorOnTurn();
                a.addItem(item);
            }
            else
            {
                message("Found a treasure!");
                n = roll(MAX_TREASURE_GOLD) + 20;
                message("It's worth " + n + " Gold!");
                Actor a = getActorOnTurn();
                a.setGold(a.getGold() + n);
            }
        }
        else if (n < TREASURE_RATE + TRAP_RATE)
        {
            message("A Trap!");
            n = roll(4) - 1;
            if (n > 0)
            {
                Actor a = getActorOnTurn();
                a.setHp(a.getHp() - n);
                message(a + " received " + n + " damage");
                if (a.getHp() <= 0)
                {
                    message(a + " fainted.");
                    removeActor(a);
                }
            }
            else
            {
                message(getActorOnTurn() + " dodged it");
            }
        }
        else
        {
            message("A stray monster appeared!");
            int x = getActorOnTurn().getX() + 1;
            int y = getActorOnTurn().getY();
            Actor a = ActorFactory.getDefaultActorOfType(Actor.ActorType.GOBLIN);
            a.setX(x);
            a.setY(y);
            addActor(a);
        }
    }

    private void revealTrapsInRoom(int roomID)
    {
        int traps = 0;
        for (int x = 0; x < Board.WIDTH; x++)
        {
            for (int y = 0; y < Board.HEIGHT; y++)
            {
                Field f = board.getField(x, y);
                if (f.roomID == roomID)
                {
                    if (f.getTrap())
                    {
                        f.setTrapVisible(true);
                        traps++;
                    }
                }
            }
        }
        if (traps == 0)
        {
            message("No traps.");
        }
        else if (traps == 1)
        {
            message("Found one trap");
        }
        else
        {
            message("Found " + traps + " traps");
        }
    }

    private void revealRoom(int roomID)
    {
        for (int x = 0; x < Board.WIDTH; x++)
        {
            for (int y = 0; y < Board.HEIGHT; y++)
            {
                Field f = board.getField(x, y);
                if (f.roomID == roomID)
                {
                    if (!f.getVisible())
                    {
                        Actor.ActorType spawn = board.getSpawn(x, y);
                        if (spawn != null)
                        {
                            Actor a = ActorFactory.getDefaultActorOfType(spawn);
                            a.setX(x);
                            a.setY(y);
                            addActor(a);
                        }
                        f.setVisible(true);
                    }
                }
            }
        }
    }

    public void useItem(Item sel)
    {
        boolean used = false;
        Actor a = getActorOnTurn();
        if (a.hasItem(sel) && sel.canUse())
        {
            message(a + " used " + sel);
            used = sel.use() && a.removeItem(sel);
            for (GameListener l : listeners)
            {
                l.stateChanged(state);
            }
        }
        if (used)
        {
            if (sel.consumesAction)
            {
                nextTurn();
            }
        }
        else
        {
            message(a + " cannot use item " + sel);
        }
    }

    void tryDiffuseTrap(int x, int y)
    {
        int n = Game.roll(100);
        if (n < Game.TRAP_DIFFUSE_CHANCE)
        {
            Game.getInstance().message("Trap removed");
        }
        else
        {
            Game.getInstance().message("Failed to diffuse the trap");
            hitTrap(x, y);
        }
        Game.getInstance().getBoard().getField(x, y).setTrap(false);
    }

    void hitTrap(int x, int y)
    {
        Actor a = getActorOnTurn();
        int n = Game.roll(100);
        int dmg = Game.roll(TRAP_DAMAGE);
        message(a + " stepped into a spike trap!");
        if (n < Game.TRAP_EVADE_CHANCE || dmg < 1)
        {
            Game.getInstance().message("But luckily " + a + " was missed");
        }
        else
        {
            message(a + " got " + dmg + " damage");
            a.setHp(a.getHp() - dmg);
            if (a.getHp() <= 0)
            {
                message(a + " fainted.");
                removeActor(a);
            }
        }
        Game.getInstance().getBoard().getField(x, y).setTrap(false);
    }

    public void message(String msg)
    {
        System.out.println(msg);
        for (GameListener l : listeners)
        {
            l.message(msg);
        }
    }

    public void nextTurn()
    {
        turn++;
        if (turn >= actors.size())
        {
            turn = 0;
        }
        if (getActorOnTurn().isHero())
        {
            state = TurnState.HEROES_TURN_ROLL;
        }
        else
        {
            state = TurnState.ENEMYS_TURN_MOVE;
        }
        message(getActorOnTurn() + " is now on turn.");

        for (GameListener l : listeners)
        {
            l.nextTurn(turn);
        }
    }

    public int getDice()
    {
        return dice;
    }

    public void addActor(Actor a)
    {
        actors.add(a);
        System.out.println("Added Actor " + a);
    }

    public boolean removeActor(Actor a)
    {
        boolean removed = actors.remove(a);
        if (removed)
        {
            System.out.println("Removed Actor " + a);
            if (a.isEnemy())
            {
                int gold = a.getGold();
                Field f = board.getField(a.getX(), a.getY());
                if (f != null)
                {
                    GoldItem item;
                    if (f.getItem() != null)
                    {
                        gold += f.getItem().getValue();
                    }
                    item = new GoldItem(gold);
                    f.setItem(item);
                    System.out.println(a + " dropped " + item);
                }
                checkQuestDone();
            }
        }
        boolean end = true;
        for (Actor a1 : actors)
        {
            if (a1.isHero())
            {
                end = false;
            }
        }
        if (end)
        {
            message("All Heroes fainted!");
            message("Mission failed");
            message("This is the end");
            state = TurnState.INVALID;
            questDone = true;
        }
        return removed;
    }

    public Field[] getCurrentFieldsInRange()
    {
        Field[] ret = new Field[currentFieldsInRange.size()];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = currentFieldsInRange.get(i);
        }
        return ret;
    }

    public Field[] getCurrentFieldsInSight()
    {
        Field[] ret = new Field[currentFieldsInSight.size()];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = currentFieldsInSight.get(i);
        }
        return ret;
    }

    public Actor[] getActors()
    {
        Actor[] ret = new Actor[actors.size()];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = actors.get(i);
        }
        return ret;
    }

    public Actor getActorOnTurn()
    {
        if (turn >= 0 && turn < actors.size())
        {
            return actors.get(turn);
        }
        return null;
    }

    public Actor getActorAt(int x, int y)
    {
        for (Actor a : actors)
        {
            if (a.getX() == x && a.getY() == y)
            {
                return a;
            }
        }
        return null;
    }

    public boolean isInAttackRange(Actor source, Actor target)
    {
        Field[] fieldsaround = board.getAdjacentFields(source.getX(), source.getY(), true, source.getWeapon().canDiagonal);
        for (Field f : fieldsaround)
        {
            if (target.getX() == f.x && target.getY() == f.y)
            {
                return true;
            }
        }
        return false;
    }

    public void startQuest(Quest q)
    {
        init();
        board.initBaseMap();
        board.parseConfig(q.getConfig());

        currentQuest = q;

        Actor a = HEROES[0];
        a.setHp(a.getMaxHp());
        a.setX(board.getStartX());
        a.setY(board.getStartY());
        addActor(a);

        a = HEROES[1];
        a.setHp(a.getMaxHp());
        a.setX(board.getStartX() + 1);
        a.setY(board.getStartY());
        addActor(a);

        a = HEROES[2];
        a.setHp(a.getMaxHp());
        a.setX(board.getStartX());
        a.setY(board.getStartY() + 1);
        addActor(a);

        a = HEROES[3];
        a.setHp(a.getMaxHp());
        a.setX(board.getStartX() + 1);
        a.setY(board.getStartY() + 1);
        addActor(a);

        revealRoom(board.getField(board.getStartX(), board.getStartY()).roomID);

        message("Starting Quest: " + q);
        for (String line : q.DESCRIPTION.split("\n"))
        {
            message(line);
        }
        message(getActorOnTurn() + " starts.");
        for (GameListener l : listeners)
        {
            l.nextTurn(turn);
            l.stateChanged(state);
        }
    }

    public boolean save(File file)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            fos.close();
            oos.close();
            System.out.println("Saved " + file);
            return true;
        }
        catch (Exception ex)
        {
            System.err.println("failed to save " + file);
            return false;
        }
    }

    public static boolean load(File file)
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            instance = (Game) ois.readObject();
            fis.close();
            ois.close();
            System.out.println("loaded " + file);
            return true;
        }
        catch (Exception ex)
        {
            System.err.println("failed to load " + file);
            return false;
        }
    }
}
