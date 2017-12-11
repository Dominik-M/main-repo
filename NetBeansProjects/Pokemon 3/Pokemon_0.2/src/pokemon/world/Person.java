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
package pokemon.world;

import image.ImageIO.ImageFile;
import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.GameData;
import pokemon.Spieler;
import utils.Constants;
import utils.Dictionary;
import utils.IO;
import utils.Text;

/**
 *
 * @author Dominik
 */
public class Person extends Objekt {

    private LinkedList<Text> dialog;
    private String name;
    private Text text;

    /**
     *
     */
    protected int richtung;
    private Item item;
    private Color farbe;
    private Color hautfarbe;

    /**
     *
     * @param n
     */
    public Person(String n) {
        this(n, Constants.DIRECTION_DOWN, (Item) null);
    }

    /**
     *
     * @param n
     * @param blickrichtung
     * @param texte
     */
    public Person(String n, int blickrichtung, Text... texte) {
        this(n, blickrichtung, (Item) null, texte);
    }

    /**
     *
     * @param n
     * @param blickrichtung
     * @param anItem
     * @param texte
     */
    public Person(String n, int blickrichtung, Item anItem, Text... texte) {
        this(n, blickrichtung, anItem, Constants.HAUTFARBEN[(int) (Math.random() * Constants.HAUTFARBEN.length)], new java.awt.Color((int) (Math.random() * Integer.MAX_VALUE)), texte);
    }

    /**
     *
     * @param n
     * @param blickrichtung
     * @param anItem
     * @param skincolor
     * @param clothcolor
     * @param texte
     */
    public Person(String n, int blickrichtung, Item anItem, Color skincolor, Color clothcolor, Text... texte) {
        super(ImageFile.TRAINER_DOWN, false);
        dialog = new LinkedList<>();
        for (Text txt : texte) {
            dialog.add(txt);
        }
        name = n;
        item = anItem;
        farbe = clothcolor;
        hautfarbe = skincolor;
        setRichtung(blickrichtung);
    }

    /**
     *
     */
    @Override
    public void benutzt() {
        Spieler s = GameData.getCurrentGame().getPlayer();
        setRichtung(utils.Utilities.invertDirection(s.getDirection()));
        if (!dialog.isEmpty()) {
            text = dialog.poll();
        }
        IO.println(this + ": " + text, IO.MessageType.IMPORTANT);
        if (item != null) {
            s.addItem(item);
            IO.println(s.toString() + " erhält " + item.toString(), IO.MessageType.IMPORTANT);
            item = null;
        }
    }

    /**
     *
     */
    @Override
    public void betreten() {
    }

    /**
     *
     * @return
     */
    public Item getItem() {
        return item;
    }

    /**
     *
     * @param anItem
     */
    public void setItem(Item anItem) {
        item = anItem;
    }

    /**
     *
     * @return
     */
    public Color getColor() {
        return farbe;
    }

    /**
     *
     * @return
     */
    public Color getSkinColor() {
        return hautfarbe;
    }

    /**
     *
     * @param kleidungsfarbe
     */
    public void setColor(Color kleidungsfarbe) {
        if (kleidungsfarbe != null) {
            farbe = kleidungsfarbe;
        }
    }

    /**
     *
     * @param hautfarb
     */
    public void setSkinColor(Color hautfarb) {
        if (hautfarb != null) {
            hautfarbe = hautfarb;
        }
    }

    /**
     *
     * @param blickrichtung
     */
    public final void setRichtung(int blickrichtung) {
        richtung = blickrichtung;
        if (richtung == Constants.DIRECTION_UP) {
            setImageFile(ImageFile.TRAINER_UP);
        } else if (richtung == Constants.DIRECTION_LEFT) {
            setImageFile(ImageFile.TRAINER_LEFT);
        } else if (richtung == Constants.DIRECTION_RIGHT) {
            setImageFile(ImageFile.TRAINER_RIGHT);
        } else {
            setImageFile(ImageFile.TRAINER_DOWN);
        }
    }

    /**
     *
     * @param texte
     */
    public void setDialog(Text... texte) {
        dialog = new LinkedList();
        for (Text txt : texte) {
            dialog.add(txt);
        }
    }

    /**
     *
     * @param texte
     */
    public void addDialog(Text... texte) {
        for (Text txt : texte) {
            dialog.add(txt);
        }
    }

    /**
     *
     * @return
     */
    public Object[] getDialog() {
        return dialog.toArray();
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public Dictionary<String, Object> getAttributes() {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : Person.class.getDeclaredFields()) {
            try {
                if (!Modifier.isStatic(f.getModifiers())) {
                    values.add(f.getName(), f.get(this));
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (dialog != null && dialog.size() > 0) {
            for (int i = 0; i < dialog.size(); i++) {
                values.add("dialog[" + i + "]", dialog.get(i));
            }
        }
        return values;
    }
}
