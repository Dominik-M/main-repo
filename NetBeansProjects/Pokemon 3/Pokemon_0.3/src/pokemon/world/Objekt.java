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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Dictionary;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Objekt extends utils.SerializableReflectObject
{

    /**
     * Default flowers deco object
     */
    public static final Objekt BLUMEN = new Objekt(ImageFile.BLUMEN, true);

    /**
     * Default floor
     */
    public static final Objekt BODEN = new Objekt(ImageFile.BODEN, true);

    /**
     * Default gras
     */
    public static final Gras GRAS = new Gras();

    /**
     * Default stone (without item)
     */
    public static final Stone STEIN = new Stone(ImageFile.STEIN);

    /**
     * Default stone (without item) in different color
     */
    public static final Stone STEIN2 = new Stone(ImageFile.STEIN2);

    /**
     * Default street ground
     */
    public static final Objekt WEG = new Objekt(ImageFile.WEG, true);

    /**
     * Default greenish ground
     */
    public static final Objekt WIESE = new Objekt(ImageFile.WIESE, true);

    /**
     * Default fence
     */
    public static final Objekt ZAUN = new Objekt(ImageFile.ZAUN, false);

    /**
     * Simply black ground
     */
    public static final Objekt SCHWARZ = new Objekt(null, false);

    private ImageFile imgFile;
    private boolean passable;

    /**
     * Constructor for Objekts.
     *
     * @param image ImageFile reference for drawing.
     * @param passable determines whether player can walk on or not
     */
    public Objekt(ImageFile image, boolean passable)
    {
        setImageFile(image);
        this.passable = passable;
    }

    /**
     * Sets passable field
     *
     * @param passable determines whether player shall be able to walk on or not
     */
    public void setPassable(boolean passable)
    {
        this.passable = passable;
    }

    public boolean isPassable()
    {
        return passable;
    }

    /**
     *
     * @return
     */
    public ImageFile getImageFile()
    {
        return imgFile;
    }

    /**
     *
     * @param image
     */
    public final void setImageFile(ImageFile image)
    {
        imgFile = image;
    }

    /**
     *
     */
    public void init()
    {

    }

    /**
     *
     */
    public void betreten()
    {

    }

    /**
     *
     */
    public void benutzt()
    {

    }

    @Override
    public String toString()
    {
        if (imgFile != null)
        {
            return imgFile.name();
        }
        else
        {
            return super.toString();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : Objekt.class.getDeclaredFields())
        {
            try
            {
                if (!Modifier.isStatic(f.getModifiers()))
                {
                    values.add(f.getName(), f.get(this));
                }
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(Objekt.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(Objekt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }
}
