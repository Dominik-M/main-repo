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
package pokemon.world;

import image.ImageIO.ImageFile;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Pokemon;
import pokemon.PokemonBasis;
import pokemon.Trainer;
import utils.Constants;
import utils.Dictionary;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Ort extends utils.SerializableReflectObject
{

    private int id_count = 0;
    public final int WIDTH, HEIGHT;
    private int startX, startY;
    private Objekt defaultObjekt, background;
    private final Objekt[][] objekte;
    private final LinkedList<Trainer> trainer;
    private final LinkedList<PokemonBasis> availPoks;
    private final int minLvl, maxLvl;

    /**
     *
     */
    public final int ID;
    public final String NAME;
    private String soundfilename;

    /**
     *
     * @param name
     * @param width
     * @param height
     * @param startX
     * @param startY
     * @param minLvl
     * @param maxLvl
     */
    public Ort(String name, int width, int height, int startX, int startY, int minLvl, int maxLvl)
    {
        ID = id_count++;
        NAME = name;
        this.WIDTH = width;
        this.HEIGHT = height;
        objekte = new Objekt[width][height];
        trainer = new LinkedList<>();
        availPoks = new LinkedList<>();
        defaultObjekt = Objekt.BODEN;
        background = Objekt.BODEN;
        this.startX = startX;
        this.startY = startY;
        this.minLvl = minLvl;
        this.maxLvl = maxLvl;
        soundfilename = Constants.SOUND_DIRECTORY + NAME.toLowerCase() + ".wav";
    }

    @Override
    public String toString()
    {
        return IO.translate(NAME);
    }

    /**
     *
     * @param t
     */
    public void addTrainer(Trainer t)
    {
        trainer.add(t);
    }

    /**
     *
     * @return
     */
    public Trainer[] getTrainer()
    {
        return (Trainer[]) trainer.toArray();
    }

    /**
     *
     * @param t
     * @return
     */
    public boolean removeTrainer(Trainer t)
    {
        return trainer.remove(t);
    }

    public void removePoks()
    {
        availPoks.clear();
    }

    public boolean removePok(PokemonBasis pok)
    {
        return availPoks.remove(pok);
    }

    public boolean addPok(PokemonBasis pok)
    {
        if (!availPoks.contains(pok))
        {
            return availPoks.add(pok);
        }
        else
        {
            return false;
        }
    }

    public Pokemon getRandomPok()
    {
        if (availPoks.size() > 0)
        {
            int i = (int) (Math.random() * availPoks.size());
            int lvl = (int) (Math.random() * (maxLvl - minLvl)) + minLvl;
            return new Pokemon(availPoks.get(i), lvl);
        }
        else
        {
            return null;
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Objekt get(int x, int y)
    {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
        {
            return defaultObjekt;
        }
        Objekt o = objekte[x][y];
        if (o == null)
        {
            return defaultObjekt;
        }
        else
        {
            return o;
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isPassable(int x, int y)
    {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && get(x, y).isPassable();
    }

    /**
     *
     * @param x
     * @param y
     * @param o
     */
    public void set(int x, int y, Objekt o)
    {
        objekte[x][y] = o;
    }

    /**
     *
     * @return
     */
    public Objekt[][] getObjekte()
    {
        Objekt[][] o = new Objekt[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                o[x][y] = get(x, y);
            }
        }
        return o;
    }

    void setObjekte(Objekt[] o)
    {
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                int index = x + y * WIDTH;
                if (index >= o.length)
                {
                    break;
                }
                set(x, y, o[index]);
            }
        }
    }

    /**
     *
     * @param xOffset
     * @param yOffset
     * @param width
     * @param height
     * @param o
     */
    public void fillRect(int xOffset, int yOffset, int width, int height, Objekt o)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                try
                {
                    set(x + xOffset, y + yOffset, o);
                } catch (Exception ex)
                {
                    IO.printException(ex);
                    return;
                }
            }
        }
    }

    /**
     *
     */
    public void reload()
    {
        for (int x = 0; x < objekte.length; x++)
        {
            for (int y = 0; y < objekte[x].length; y++)
            {
                if (objekte[x][y] != null)
                {
                    objekte[x][y].init();
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public Objekt getDefaultObjekt()
    {
        return defaultObjekt;
    }

    /**
     *
     * @param defaultObjekt
     */
    public void setDefaultObjekt(Objekt defaultObjekt)
    {
        this.defaultObjekt = defaultObjekt;
    }

    /**
     *
     * @return
     */
    public Objekt getBackground()
    {
        return background;
    }

    /**
     *
     * @param background
     */
    public void setBackground(Objekt background)
    {
        this.background = background;
    }

    /**
     *
     * @return
     */
    public String getSoundfilename()
    {
        return soundfilename;
    }

    /**
     *
     * @param soundfilename
     */
    public void setSoundfilename(String soundfilename)
    {
        this.soundfilename = soundfilename;
    }

    public int getWidth()
    {
        return WIDTH;
    }

    public int getHeight()
    {
        return HEIGHT;
    }

    public int getStartX()
    {
        return startX;
    }

//    public void setStartX(int startX) {
//        if (startX >= 0 && startX < width) {
//            this.startX = startX;
//        }
//    }
    public int getStartY()
    {
        return startY;
    }

//    private void setStartY(int startY) {
//        this.startY = startY;
//    }
    public void setStartPos(int x, int y)
    {
        startX = x;
        startY = y;
    }

    public static Ort createDefaultHouse(String name)
    {
        Ort inside = new Ort(name, 8, 8, 3, 7, 0, 0);
        inside.setDefaultObjekt(Objekt.SCHWARZ);
        inside.fillRect(1, 1, 6, 6, Objekt.BODEN);
        return inside;
    }

    /**
     *
     * @param o
     * @param x
     * @param y
     * @param width
     * @param height
     * @param name
     * @param inside
     * @return
     */
    public static Ort placeHaus(Ort o, int x, int y, int width, int height, String name, Ort inside)
    {
        o.set(x, y, new Objekt(ImageFile.DACH_OBEN_LINKS, false));
        for (int i = 0; i < width; i++)
        {
            o.set(x + i + 1, y, new Objekt(ImageFile.DACH_OBEN_MITTE, false));
        }
        o.set(x + width, y, new Objekt(ImageFile.DACH_OBEN_RECHTS, false));
        o.set(x, y + 1, new Objekt(ImageFile.DACH_UNTEN_LINKS, false));
        for (int i = 0; i < width; i++)
        {
            o.set(x + i + 1, y + 1, new Objekt(ImageFile.DACH_UNTEN_MITTE, false));
        }
        o.set(x + width, y + 1, new Objekt(ImageFile.DACH_UNTEN_RECHTS, false));
        for (int yi = y + 2; yi < y + height + 2; yi++)
        {
            o.set(x, yi, new Objekt(ImageFile.FENSTER_LINKS, false));
            for (int xi = x + 1; xi < x + width; xi++)
            {
                o.set(xi, yi, new Objekt(ImageFile.FENSTER, false));
            }
            o.set(x + width, yi, new Objekt(ImageFile.FENSTER_RECHTS, false));
        }
        o.set(x, y + height + 2, new Objekt(ImageFile.HAUSECKE_UNTEN_LINKS, false));
        for (int xi = x + 1; xi < x + width; xi++)
        {
            o.set(xi, y + height + 2, new Objekt(ImageFile.HAUSWAND_UNTEN, false));
        }
        if (inside != null)
        {
            o.set(x + width / 2, y + height + 2, new Objekt(ImageFile.TUER, true));
            World.addGateway(x + width / 2, y + height + 2, o, 3, 7, inside, ImageFile.TUER);
        }
        o.set(x + width, y + height + 2, new Objekt(ImageFile.HAUSECKE_UNTEN_RECHTS, false));
        return inside;
    }

    /**
     *
     * @param o
     * @param name
     * @param x
     * @param y
     * @param inside
     * @return
     */
    public static Ort placeHuette(Ort o, String name, int x, int y, Ort inside)
    {
        o.objekte[x][y] = new Objekt(ImageFile.HUETTE_OBEN_LINKS, false);
        o.objekte[x + 1][y] = new Objekt(ImageFile.HUETTE_OBEN_MITTE, false);
        o.objekte[x + 2][y] = new Objekt(ImageFile.HUETTE_OBEN_MITTE, false);
        o.objekte[x + 3][y] = new Objekt(ImageFile.HUETTE_OBEN_RECHTS, false);
        o.objekte[x][y + 1] = new Objekt(ImageFile.HUETTE_MITTE_LINKS, false);
        o.objekte[x + 1][y + 1] = new Objekt(ImageFile.HUETTE_MITTE, false);
        o.objekte[x + 2][y + 1] = new Objekt(ImageFile.HUETTENFENSTER, false);
        o.objekte[x + 3][y + 1] = new Objekt(ImageFile.HUETTE_MITTE_RECHTS, false);
        o.objekte[x][y + 2] = new Objekt(ImageFile.HUETTE_UNTEN_LINKS, false);
        if (inside != null)
        {
            World.addGateway(x + 1, y + 2, o, 3, 7, inside, ImageFile.TUER);
        }
        else
        {
            o.objekte[x + 1][y + 2] = new Objekt(ImageFile.FENSTER_UNTEN, false);
        }
        o.objekte[x + 2][y + 2] = new Objekt(ImageFile.FENSTER_UNTEN, false);
        o.objekte[x + 3][y + 2] = new Objekt(ImageFile.HUETTE_UNTEN_RECHTS, false);
        return inside;
    }

    /**
     *
     * @return
     */
    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : Ort.class.getDeclaredFields())
        {
            try
            {
                if (!Modifier.isStatic(f.getModifiers()))
                {
                    values.add(f.getName(), f.get(this));
                }
            } catch (IllegalArgumentException ex)
            {
                Logger.getLogger(Ort.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex)
            {
                Logger.getLogger(Ort.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                values.add("objekte[" + x + "][" + y + "]", get(x, y));
            }
        }
        return values;
    }
}
