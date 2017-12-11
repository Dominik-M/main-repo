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
import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Pokemon;
import pokemon.PokemonBasis;
import pokemon.Trainer;
import static pokemon.world.Objekt.BLUMEN;
import static pokemon.world.Objekt.GRAS;
import static pokemon.world.Objekt.STEIN2;
import static pokemon.world.Objekt.WEG;
import static pokemon.world.Objekt.WIESE;
import static pokemon.world.Objekt.ZAUN;
import utils.Constants;
import utils.Dictionary;
import utils.IO;
import utils.Text;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Ort extends utils.SerializableReflectObject
{

    private final int width, height, startX, startY;
    private Objekt defaultObjekt, background;
    private final Objekt[][] objekte;
    private final LinkedList<Trainer> trainer;
    private final LinkedList<PokemonBasis> availPoks;
    private final int minLvl, maxLvl;

    /**
     *
     */
    public final Text NAME;     // Used as an ID
    private String soundfilename;

    /**
     *
     * @param n
     * @param width
     * @param height
     * @param startX
     * @param startY
     * @param minLvl
     * @param maxLvl
     */
    public Ort(Text n, int width, int height, int startX, int startY, int minLvl, int maxLvl)
    {
        NAME = n;
        this.width = width;
        this.height = height;
        objekte = new Objekt[width][height];
        trainer = new LinkedList<>();
        availPoks = new LinkedList<>();
        defaultObjekt = Objekt.BODEN;
        background = Objekt.BODEN;
        this.startX = startX;
        this.startY = startY;
        this.minLvl = minLvl;
        this.maxLvl = maxLvl;
        soundfilename = Constants.SOUND_DIRECTORY + NAME.name().toLowerCase() + ".wav";
    }

    @Override
    public String toString()
    {
        return NAME.toString();
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
        if (x < 0 || x >= width || y < 0 || y >= height)
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
        return x >= 0 && x < width && y >= 0 && y < height && get(x, y).isPassable();
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
        Objekt[][] o = new Objekt[width][height];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                o[x][y] = get(x, y);
            }
        }
        return o;
    }

    private void setObjekte(Objekt[] o)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int index = x + y * width;
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
        return width;
    }

    public int getHeight()
    {
        return height;
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
    public static Ort createDefaultHouse(Text name)
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
    public static Ort placeHaus(Ort o, int x, int y, int width, int height, Text name, Ort inside)
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
            addGateway(x + width / 2, y + height + 2, o, 3, 7, inside, ImageFile.TUER);
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
    public static Ort placeHuette(Ort o, Text name, int x, int y, Ort inside)
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
            addGateway(x + 1, y + 2, o, 3, 7, inside, ImageFile.TUER);
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
     * @param x1
     * @param y1
     * @param pos1
     * @param x2
     * @param y2
     * @param pos2
     * @param img
     */
    public static void addGateway(int x1, int y1, Ort pos1, int x2, int y2, Ort pos2, ImageFile img)
    {
        pos1.set(x1, y1, new GateObjekt(pos2, x2, y2, img));
        pos2.set(x2, y2, new GateObjekt(pos1, x1, y1, img));
    }

    /**
     *
     * @return
     */
    public static Ort initAlabastia()
    {
        Ort alabastia = new Ort(Text.ALABASTIA, 20, 18, 5, 6, 2, 5);
        alabastia.availPoks.add(PokemonBasis.BISASAM);
        Objekt[] o = new Objekt[]
        {
            WIESE, WIESE, WIESE, STEIN2, WIESE, WIESE, WIESE, STEIN2, GRAS, GRAS, STEIN2, WIESE, WIESE, WIESE, WIESE, WIESE, WIESE, WIESE, STEIN2, WIESE,
            STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, GRAS, GRAS, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2
        };
        alabastia.setObjekte(o);
        for (int y = 2; y < 18; y++)
        {
            alabastia.set(0, y, STEIN2);
            alabastia.set(19, y, STEIN2);
        }
        for (int x = 1; x < 19; x++)
        {
            alabastia.set(x, 17, STEIN2);
        }
        for (int y = 2; y < 18; y++)
        {
            alabastia.set(1, y, WIESE);
        }
        for (int y = 14; y < 18; y++)
        {
            alabastia.set(2, y, WIESE);
            alabastia.set(3, y, WIESE);
        }
        for (int x = 2; x < 18; x++)
        {
            alabastia.set(x, 6, WEG);
            alabastia.set(x, 7, WEG);
        }
        for (int y = 8; y < 14; y++)
        {
            alabastia.set(2, y, WEG);
            alabastia.set(3, y, WEG);
        }
        alabastia.set(4, 9, ZAUN);
        alabastia.set(5, 9, ZAUN);
        alabastia.set(6, 9, ZAUN);
        alabastia.set(7, 9, new Sign(Text.ALABASTIA));
        for (int x = 4; x < 8; x++)
        {
            alabastia.set(x, 10, BLUMEN);
            alabastia.set(x, 11, BLUMEN);
            alabastia.set(x, 12, WEG);
            alabastia.set(x, 13, WEG);
        }
        for (int y = 2; y < 14; y++)
        {
            alabastia.set(8, y, WEG);
            alabastia.set(9, y, WEG);
        }
        alabastia.set(1, 17, STEIN2);
        alabastia.set(4, 17, new Water(ImageFile.UFER_LINKS_MITTE));
        alabastia.set(4, 16, new Water(ImageFile.UFER_LINKS_MITTE));
        alabastia.set(4, 15, new Water(ImageFile.UFER_LINKS_MITTE));
        alabastia.set(4, 14, new Water(ImageFile.UFER_OBEN_LINKS));
        alabastia.set(5, 14, new Water(ImageFile.UFER_OBEN_MITTE));
        alabastia.set(6, 14, new Water(ImageFile.UFER_OBEN_MITTE));
        alabastia.set(7, 14, new Water(ImageFile.UFER_OBEN_RECHTS));
        alabastia.set(7, 15, new Water(ImageFile.UFER_RECHTS_MITTE));
        alabastia.set(7, 16, new Water(ImageFile.UFER_RECHTS_MITTE));
        alabastia.set(7, 17, new Water(ImageFile.UFER_RECHTS_MITTE));
        alabastia.set(6, 17, new Water(ImageFile.WASSER));
        alabastia.set(6, 16, new Water(ImageFile.WASSER));
        alabastia.set(6, 15, new Water(ImageFile.WASSER));
        alabastia.set(5, 17, new Water(ImageFile.WASSER));
        alabastia.set(5, 16, new Water(ImageFile.WASSER));
        alabastia.set(5, 15, new Water(ImageFile.WASSER));
        Person p = new Person("Peter", Constants.DIRECTION_DOWN, null, Constants.HAUTFARBEN[0], Color.BLUE,
                Text.DIALOG_PETER_1, Text.DIALOG_PETER_2, Text.DIALOG_PETER_3, Text.DIALOG_PETER_4);
        alabastia.set(5, 8, p);
        Ort house = placeHuette(alabastia, Text.SIGN_PLAYERSHOUSE, 4, 3, createDefaultHouse(Text.SIGN_PLAYERSHOUSE));
        alabastia.set(3, 5, new Sign(Text.SIGN_PLAYERSHOUSE));
        house = placeHuette(alabastia, Text.SIGN_RIVALSHOUSE, 12, 3, createDefaultHouse(Text.SIGN_RIVALSHOUSE));
        alabastia.set(11, 5, new Sign(Text.SIGN_RIVALSHOUSE));
        house = placeHaus(alabastia, 10, 8, 6, 1, Text.ALABASTIA, createDefaultHouse(Text.ALABASTIA));
        return alabastia;
    }

    /**
     *
     * @return
     */
    public static Ort initRoute1()
    {
        Ort route1 = new Ort(Text.ROUTE1, 20, 20, 4, 7, 2, 7);
        // TODO init route 1
        return route1;
    }

    /**
     *
     * @return
     */
    public static Ort initVertania()
    {
        Ort vertania = new Ort(Text.VERTANIA_CITY, 20, 20, 4, 7, 3, 10);
        //TODO init vertania city
        return vertania;
    }

    /**
     *
     * @return
     */
    public static utils.Dictionary<Text, Ort> createWorld()
    {
        utils.Dictionary<Text, Ort> world = new utils.Dictionary<>();
        Ort alabastia = initAlabastia();
        world.add(alabastia.NAME, alabastia);
        Ort route1 = initRoute1();
        world.add(route1.NAME, route1);
        Ort vertania = initVertania();
        world.add(vertania.NAME, vertania);
        // gateways alabastia <-> route1
        alabastia.set(8, 0, new GateObjekt(route1, 8, 18, ImageFile.GRAS));
        alabastia.set(9, 0, new GateObjekt(route1, 9, 18, ImageFile.GRAS));
        route1.set(8, 19, new GateObjekt(alabastia, 8, 1, ImageFile.GRAS));
        route1.set(9, 19, new GateObjekt(alabastia, 9, 1, ImageFile.GRAS));
        //addGateway(8, 0, alabastia, 8, 19, route1, ImageFile.GRAS);
        //addGateway(9, 0, alabastia, 9, 19, route1, ImageFile.GRAS);
        return world;
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
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                values.add("objekte[" + x + "][" + y + "]", get(x, y));
            }
        }
        return values;
    }
}
