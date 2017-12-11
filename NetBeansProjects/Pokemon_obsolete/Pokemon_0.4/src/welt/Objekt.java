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
package welt;

import image.ImageIO.ImageFile;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Objekt implements java.io.Serializable {

    public static final Objekt BLUMEN = new Objekt(ImageFile.BLUMEN, true);
    public static final Objekt BODEN = new Objekt(ImageFile.BODEN, true);
    public static final Gras GRAS = new Gras();
    public static final Stone STEIN = new Stone(ImageFile.STEIN);
    public static final Stone STEIN2 = new Stone(ImageFile.STEIN2);
    public static final Objekt WEG = new Objekt(ImageFile.WEG, true);
    public static final Objekt WIESE = new Objekt(ImageFile.WIESE, true);
    public static final Objekt ZAUN = new Objekt(ImageFile.ZAUN, false);

    private ImageFile imgFile;
    private boolean passable;
    private Color color;

    public Objekt(ImageFile image, boolean passable) {
        setImageFile(image);
        this.passable = passable;
    }

    public Objekt(Color c, boolean passable) {
        this.passable = passable;
        color = c;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    boolean isPassable() {
        return passable;
    }

    public ImageFile getImageFile() {
        return imgFile;
    }

    public final void setImageFile(ImageFile image) {
        imgFile = image;
    }

    public void init() {

    }

    public void draw(Graphics g) {
        if (imgFile != null) {
            getImageFile().draw(g);
        } else {
            g.setColor(color);
            g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        }
    }

    public void betreten() {

    }

    public void benutzt() {

    }
}
