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

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Board implements Serializable
{

    public static final int WIDTH = 26, HEIGHT = 19;
    public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
    public static final String CONFIG_COMMAND_SEPARATOR = ":",
            CONFIG_PARAM_SEPARATOR = ",";

    public enum BoardObject
    {

        START("Start"),
        HWALL("Horizontal Wall"),
        VWALL("Vertical Wall"),
        HDOOR("Horizontal Door"),
        VDOOR("Vertical Door"),
        SHDOOR("Secret Horizontal Door"),
        SVDOOR("Secret Vertical Door"),
        BARBAR("Barbar"),
        MAGE("Mage"),
        ELF("Elf"),
        DWARF("Dwarf"),
        GOBLIN("Goblin"),
        ORC("Orc"),
        SCELETON("Sceleton"),
        KNIGHT("Knight"),
        MUMMY("Mummy"),
        TROLL("Troll"),
        DEMON("Demon"),
        ITEM("Item"),
        TRAP("Trap");

        private final String text;

        private BoardObject(String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }

    public enum WallState
    {
        NONE,
        BORDER,
        WALL,
        DOOR_OPEN,
        DOOR_CLOSED,
        SECRET_DOOR;

        public boolean isPassable()
        {
            return this == NONE || (this == DOOR_OPEN);
        }
    }

    public class Field
    {

        public final int x, y;
        int roomID = 0;
        private boolean visible, passable, trap, trapvisible;
        private Item item;

        public Field(int x, int y, boolean v, boolean p)
        {
            this.x = x;
            this.y = y;
            this.visible = v;
            this.passable = p;
            this.trapvisible = false;
            this.item = null;
        }

        @Override
        public String toString()
        {
            return "Field(" + x + "|" + y + ") " + (visible ? "v," : "!v,") + (passable ? "p" : "!p");
        }

        public boolean isFree()
        {
            boolean free = getPassable();
            if (free)
            {
                for (Actor a : Game.getInstance().getActors())
                {
                    if (a.getX() == x && a.getY() == y)
                    {
                        free = false;
                        break;
                    }
                }
            }
            return free;
        }

        public boolean getVisible()
        {
            return visible;
        }

        public void setVisible(boolean visible)
        {
            this.visible = visible;
        }

        public boolean getPassable()
        {
            return passable;
        }

        public void setPassable(boolean passable)
        {
            this.passable = passable;
        }

        public boolean getTrap()
        {
            return trap;
        }

        public void setTrap(boolean trap)
        {
            this.trap = trap;
        }

        public boolean isTrapVisible()
        {
            return trapvisible;
        }

        public void setTrapVisible(boolean visible)
        {
            trapvisible = visible;
        }

        public Item getItem()
        {
            return item;
        }

        public void setItem(Item i)
        {
            item = i;
        }

    }

    private final Field[][] FIELDS = new Field[WIDTH][HEIGHT];
    private final WallState[][] HWALLS = new WallState[WIDTH][HEIGHT];
    private final WallState[][] VWALLS = new WallState[WIDTH][HEIGHT];
    private final Actor.ActorType[][] SPAWNS = new Actor.ActorType[WIDTH][HEIGHT];
    private int roomCount = 0, startX, startY;
    private String config;

    public Board()
    {
        System.out.println("Create Board");
        init();
    }

    private void init()
    {
        initBaseMap();
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartY()
    {
        return startY;
    }

    public Actor.ActorType getSpawn(int x, int y)
    {
        if (isInBounds(x, y))
        {
            return SPAWNS[x][y];
        }
        return null;
    }

    public Field getField(int x, int y)
    {
        if (isInBounds(x, y))
        {
            return FIELDS[x][y];
        }
        else
        {
            return null;
        }
    }

    public WallState getHWall(int x, int y)
    {
        if (isInBounds(x, y))
        {
            return HWALLS[x][y];
        }
        return WallState.BORDER;
    }

    public WallState getVWall(int x, int y)
    {
        if (isInBounds(x, y))
        {
            return VWALLS[x][y];
        }
        return WallState.BORDER;
    }

    public boolean isInBounds(int x, int y)
    {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    public WallState[] getWallsaround(Field f)
    {
        int x = f.x;
        int y = f.y;
        return new WallState[]
        {
            getVWall(x, y), // LEFT
            getVWall(x + 1, y), // RIGHT
            getHWall(x, y), // UP
            getHWall(x, y + 1) // DOWN
        };
    }

    public void setWallAt(int x, int y, int direction, WallState wall)
    {
        if (isInBounds(x, y))
        {
            switch (direction)
            {
                case LEFT:
                    VWALLS[x][y] = wall;
                    break;
                case RIGHT:
                    VWALLS[x + 1][y] = wall;
                    break;
                case UP:
                    HWALLS[x][y] = wall;
                    break;
                case DOWN:
                    HWALLS[x][y + 1] = wall;
                    break;
                default:
                    System.out.println("setWallAt(" + x + ", " + y + ", " + direction + ", " + wall + "): Invalid direction");
                    break;
            }
        }
        else
        {
            System.out.println("setWallAt(" + x + ", " + y + ", " + direction + ", " + wall + "): Out of bounds");
        }
    }

    public Field[] getAdjacentFields(int x, int y, boolean checkWalls, boolean diagonal)
    {
        Field f = getField(x, y);
        LinkedList<Field> fieldslist = new LinkedList<>();
        if (f != null)
        {
            WallState[] walls = getWallsaround(f);
            if (isInBounds(x - 1, y)) // LEFT
            {
                if (!checkWalls || walls[LEFT].isPassable())
                {
                    fieldslist.add(FIELDS[x - 1][y]);
                }
            }
            if (isInBounds(x + 1, y)) // RIGHT
            {
                if (!checkWalls || walls[RIGHT].isPassable())
                {
                    fieldslist.add(FIELDS[x + 1][y]);
                }
            }
            if (isInBounds(x, y - 1)) // UP
            {
                if (!checkWalls || walls[UP].isPassable())
                {
                    fieldslist.add(FIELDS[x][y - 1]);
                }
            }
            if (isInBounds(x, y + 1)) // DOWN
            {
                if (!checkWalls || walls[DOWN].isPassable())
                {
                    fieldslist.add(FIELDS[x][y + 1]);
                }
            }
            if (diagonal)
            {
                if (isInBounds(x - 1, y - 1)) // LEFT-UP
                {
                    if (!checkWalls || walls[LEFT].isPassable() || walls[UP].isPassable())
                    {
                        fieldslist.add(FIELDS[x - 1][y - 1]);
                    }
                }
                if (isInBounds(x + 1, y - 1)) // RIGHT-UP
                {
                    if (!checkWalls || walls[RIGHT].isPassable() || walls[UP].isPassable())
                    {
                        fieldslist.add(FIELDS[x + 1][y - 1]);
                    }
                }
                if (isInBounds(x - 1, y + 1)) // LEFT-DOWN
                {
                    if (!checkWalls || walls[LEFT].isPassable() || walls[DOWN].isPassable())
                    {
                        fieldslist.add(FIELDS[x - 1][y + 1]);
                    }
                }
                if (isInBounds(x + 1, y + 1)) // RIGHT-DOWN
                {
                    if (!checkWalls || walls[DOWN].isPassable() || walls[RIGHT].isPassable())
                    {
                        fieldslist.add(FIELDS[x + 1][y + 1]);
                    }
                }
            }
        }

        Field[] fieldsarray = new Field[fieldslist.size()];
        for (int i = 0; i < fieldsarray.length; i++)
        {
            fieldsarray[i] = fieldslist.get(i);
        }
        return fieldsarray;
    }

    public LinkedList<Field> getFieldsInRange(int x, int y, int range, boolean hero)
    {
        LinkedList<Field> fields = new LinkedList<>();
        if (isInBounds(x, y) && FIELDS[x][y].isFree())
        {
            fields.add(FIELDS[x][y]);
        }
        if (range > 0)
        {
            for (Field f : getAdjacentFields(x, y, true, false))
            {
                Actor a = Game.getInstance().getActorAt(f.x, f.y);
                if (a == null || a.isHero() == hero)
                {
                    LinkedList<Field> sub = getFieldsInRange(f.x, f.y, range - 1, hero);
                    for (Field fsub : sub)
                    {
                        if (!fields.contains(fsub))
                        {
                            fields.add(fsub);
                        }
                    }
                }
            }
        }
        return fields;
    }

    public LinkedList<Field> getFieldsInSight(int x, int y)
    {
        LinkedList<Field> fieldslist = new LinkedList<>();
        if (isInBounds(x, y))
        {
            Field f = getField(x, y);
            fieldslist.add(f);
            // look up
            for (int dy = 1; getHWall(x, y - (dy - 1)).isPassable(); dy++)
            {
                f = getField(x, y - dy);
                if (!fieldslist.contains(f))
                {
                    fieldslist.add(f);
                }
                //diagonal left
                for (int dx = 1; dx <= dy; dx++)
                {
                    if (!getVWall(x - (dx - 1), y - dy).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getHWall(x - dx, y - dy + 1).isPassable())
                    {
                        f = getField(x - dx, y - dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
                //diagonal right
                for (int dx = 1; dx <= dy; dx++)
                {
                    if (!getVWall(x + dx, y - dy).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getHWall(x + dx, y - dy + 1).isPassable())
                    {
                        f = getField(x + dx, y - dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
            }
            // look down
            for (int dy = 1; getHWall(x, y + dy).isPassable(); dy++)
            {
                f = getField(x, y + dy);
                if (!fieldslist.contains(f))
                {
                    fieldslist.add(f);
                }
                //diagonal left
                for (int dx = 1; dx <= dy; dx++)
                {
                    if (!getVWall(x - (dx - 1), y + dy).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getHWall(x - dx, y + dy).isPassable())
                    {
                        f = getField(x - dx, y + dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
                //diagonal right
                for (int dx = 1; dx <= dy; dx++)
                {
                    if (!getVWall(x + dx, y + dy).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getHWall(x + dx, y + dy).isPassable())
                    {
                        f = getField(x + dx, y + dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
            }
            // look left
            for (int dx = 1; getVWall(x - dx + 1, y).isPassable(); dx++)
            {
                f = getField(x - dx, y);
                if (!fieldslist.contains(f))
                {
                    fieldslist.add(f);
                }
                //diagonal up
                for (int dy = 1; dy <= dx; dy++)
                {
                    if (!getHWall(x - (dx - 1), y - dy + 1).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getVWall(x - (dx - 1), y - dy).isPassable())
                    {
                        f = getField(x - dx, y - dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
                //diagonal down
                for (int dy = 1; dy <= dx; dy++)
                {
                    if (!getHWall(x - (dx - 1), y + dy).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getVWall(x - (dx - 1), y + dy).isPassable())
                    {
                        f = getField(x - dx, y + dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
            }
            // look right
            for (int dx = 1; getVWall(x + dx, y).isPassable(); dx++)
            {
                f = getField(x + dx, y);
                if (!fieldslist.contains(f))
                {
                    fieldslist.add(f);
                }
                //diagonal up
                for (int dy = 1; dy <= dx; dy++)
                {
                    if (!getHWall(x + dx, y - dy + 1).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getVWall(x + dx, y - dy).isPassable())
                    {
                        f = getField(x + dx, y - dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
                //diagonal down
                for (int dy = 1; dy <= dx; dy++)
                {
                    if (!getHWall(x + dx, y + dy).isPassable())
                    {
                        break;
                    }
                    if (dy <= 1 || getVWall(x + dx, y + dy).isPassable())
                    {
                        f = getField(x + dx, y + dy);
                        if (!fieldslist.contains(f))
                        {
                            fieldslist.add(f);
                        }
                    }
                }
            }
        }

        return fieldslist;
    }

    private void makeRectRoom(int x, int y, int width, int height)
    {
        for (int xi = 0; xi < width; xi++)
        {
            HWALLS[x + xi][y] = WallState.WALL;
            HWALLS[x + xi][y + height] = WallState.WALL;
        }
        for (int yi = 0; yi < height; yi++)
        {
            VWALLS[x][y + yi] = WallState.WALL;
            VWALLS[x + width][y + yi] = WallState.WALL;
        }
        for (int xi = 0; xi < width; xi++)
        {
            for (int yi = 0; yi < height; yi++)
            {
                FIELDS[x + xi][y + yi].roomID = roomCount;
            }
        }
        roomCount++;
    }

    public void initBaseMap()
    {
        config = "";
        System.out.println("initBaseMap()");
        // Fields
        for (int x = 0; x < FIELDS.length; x++)
        {
            for (int y = 0; y < FIELDS[x].length; y++)
            {
                FIELDS[x][y] = new Field(x, y, false, true);
            }
        }

        // horizontal walls
        for (int x = 0; x < HWALLS.length; x++)
        {
            for (int y = 0; y < HWALLS[x].length; y++)
            {
                HWALLS[x][y] = y == 0 ? WallState.BORDER : WallState.NONE;
            }
        }
        // vertical walls
        for (int x = 0; x < VWALLS.length; x++)
        {
            for (int y = 0; y < VWALLS[x].length; y++)
            {
                VWALLS[x][y] = x == 0 ? WallState.BORDER : WallState.NONE;
            }
        }
        // Spawns
        for (int x = 0; x < SPAWNS.length; x++)
        {
            for (int y = 0; y < SPAWNS[x].length; y++)
            {
                SPAWNS[x][y] = null;
            }
        }

        // rooms
        roomCount = 1;
        makeRectRoom(1, 1, 4, 3);
        makeRectRoom(5, 1, 4, 3);
        makeRectRoom(9, 1, 3, 5);
        makeRectRoom(1, 4, 4, 5);
        makeRectRoom(5, 4, 4, 5);
        makeRectRoom(14, 1, 3, 5);
        makeRectRoom(17, 1, 4, 4);
        makeRectRoom(21, 1, 4, 4);
        makeRectRoom(17, 5, 4, 4);
        makeRectRoom(21, 5, 4, 4);
        makeRectRoom(10, 7, 6, 5);
        makeRectRoom(1, 10, 4, 4);
        makeRectRoom(5, 10, 2, 3);
        makeRectRoom(7, 10, 2, 3);
        makeRectRoom(1, 14, 4, 4);
        makeRectRoom(5, 13, 4, 5);
        makeRectRoom(9, 13, 3, 5);
        makeRectRoom(17, 10, 4, 4);
        makeRectRoom(21, 10, 4, 4);
        makeRectRoom(21, 14, 4, 4);
        makeRectRoom(18, 14, 3, 4);
        makeRectRoom(14, 13, 4, 5);

        // corrections
        VWALLS[17][13] = WallState.NONE;
        HWALLS[17][14] = WallState.NONE;

        startX = 14;
        startY = 10;
    }

    public boolean placeObject(BoardObject type, int x, int y)
    {
        boolean ok = false;
        if (type != null)
        {
            if (isInBounds(x, y))
            {
                switch (type)
                {
                    case BARBAR:
                        SPAWNS[x][y] = Actor.ActorType.BARBAR;
                        ok = true;
                        break;
                    case DEMON:
                        SPAWNS[x][y] = Actor.ActorType.DEMON;
                        ok = true;
                        break;
                    case DWARF:
                        SPAWNS[x][y] = Actor.ActorType.DWARF;
                        ok = true;
                        break;
                    case ELF:
                        SPAWNS[x][y] = Actor.ActorType.ELF;
                        ok = true;
                        break;
                    case GOBLIN:
                        SPAWNS[x][y] = Actor.ActorType.GOBLIN;
                        ok = true;
                        break;
                    case HDOOR:
                        HWALLS[x][y] = WallState.DOOR_CLOSED;
                        ok = true;
                        break;
                    case SHDOOR:
                        HWALLS[x][y] = WallState.SECRET_DOOR;
                        ok = true;
                        break;
                    case HWALL:
                        HWALLS[x][y] = WallState.WALL;
                        ok = true;
                        break;
                    case ITEM:
                        //TODO
                        System.out.println("Items not available yet");
                        ok = true;
                        break;
                    case KNIGHT:
                        SPAWNS[x][y] = Actor.ActorType.KNIGHT;
                        ok = true;
                        break;
                    case MAGE:
                        SPAWNS[x][y] = Actor.ActorType.MAGE;
                        ok = true;
                        break;
                    case MUMMY:
                        SPAWNS[x][y] = Actor.ActorType.MUMMY;
                        ok = true;
                        break;
                    case ORC:
                        SPAWNS[x][y] = Actor.ActorType.ORC;
                        ok = true;
                        break;
                    case SCELETON:
                        SPAWNS[x][y] = Actor.ActorType.SCELETON;
                        ok = true;
                        break;
                    case START:
                        startX = x;
                        startY = y;
                        ok = true;
                        break;
                    case TRAP:
                        FIELDS[x][y].trap = true;
                        ok = true;
                        break;
                    case TROLL:
                        SPAWNS[x][y] = Actor.ActorType.TROLL;
                        ok = true;
                        break;
                    case VDOOR:
                        VWALLS[x][y] = WallState.DOOR_CLOSED;
                        ok = true;
                        break;
                    case SVDOOR:
                        VWALLS[x][y] = WallState.SECRET_DOOR;
                        ok = true;
                        break;
                    case VWALL:
                        VWALLS[x][y] = WallState.WALL;
                        ok = true;
                        break;
                    default:
                        System.out.println(type + " is not a valid object");
                        break;
                }
            }
            else
            {
                System.out.println("Out of bounds " + x + "|" + y);
            }
        }
        else
        {
            System.out.println("No item selected");
        }
        if (ok)
        {
            String text = type.name() + CONFIG_COMMAND_SEPARATOR + x + CONFIG_PARAM_SEPARATOR + y + "\n";
            if (!config.contains(text))
            {
                System.out.print("Added: " + text);
                config += text;
            }
        }
        return ok;
    }

    public boolean removeObject(BoardObject type, int x, int y)
    {
        boolean ok = false;
        if (type != null)
        {
            if (isInBounds(x, y))
            {
                switch (type)
                {
                    case BARBAR:
                    case DEMON:
                    case DWARF:
                    case ELF:
                    case GOBLIN:
                    case MAGE:
                    case KNIGHT:
                    case MUMMY:
                    case ORC:
                    case SCELETON:
                    case TROLL:
                        SPAWNS[x][y] = null;
                        ok = true;
                        break;
                    case HDOOR:
                    case SHDOOR:
                    case HWALL:
                        HWALLS[x][y] = WallState.NONE;
                        ok = true;
                        break;
                    case ITEM:
                        //TODO
                        System.out.println("Items not available yet");
                        ok = true;
                        break;
                    case START:
                        startX = x;
                        startY = y;
                        ok = true;
                        break;
                    case TRAP:
                        FIELDS[x][y].trap = false;
                        ok = true;
                        break;
                    case VDOOR:
                    case SVDOOR:
                    case VWALL:
                        VWALLS[x][y] = WallState.NONE;
                        ok = true;
                        break;
                    default:
                        System.out.println(type + " is not a valid object");
                        break;
                }
            }
            else
            {
                System.out.println("Out of bounds " + x + "|" + y);
            }
        }
        else
        {
            System.out.println("No item selected");
        }
        if (ok)
        {
            String text = type.name() + CONFIG_COMMAND_SEPARATOR + x + CONFIG_PARAM_SEPARATOR + y + "\n";
            String replaceAll = config.replaceAll(text, "");
            System.out.print("Removed: " + text);
        }
        return ok;
    }

    public void parseConfig(String config)
    {
        System.out.println("parseConfig()");
        for (String line : config.split("\n"))
        {
            if (line.contains(CONFIG_COMMAND_SEPARATOR))
            {
                int i1 = 0, i2 = 0;
                i2 = line.indexOf(CONFIG_COMMAND_SEPARATOR);
                String command = line.substring(0, i2);
                i1 = i2 + CONFIG_COMMAND_SEPARATOR.length();
                String[] parameters = line.substring(i1).split(CONFIG_PARAM_SEPARATOR);
                boolean ok = false;
                try
                {
                    BoardObject type = Board.BoardObject.valueOf(command);
                    if (parameters.length < 2)
                    {
                        System.err.println("Error parsing line:\"" + line + "\"");
                        System.err.println(type + " command takes two parameters");
                    }
                    else
                    {
                        try
                        {
                            int x = Integer.parseInt(parameters[0]);
                            int y = Integer.parseInt(parameters[1]);
                            ok = placeObject(type, x, y);
                        }
                        catch (NumberFormatException ex)
                        {
                            System.err.println("Error parsing line:\"" + line + "\"");
                            System.err.println(ex);
                        }
                    }
                }
                catch (IllegalArgumentException ex)
                {
                    System.err.println(command + " is not a valid Type");
                }
                if (!ok)
                {
                    System.err.println("Error parsing line:\"" + line + "\"");
                }
            }
        }
    }

    public String getConfig()
    {
        return config;
    }
}
