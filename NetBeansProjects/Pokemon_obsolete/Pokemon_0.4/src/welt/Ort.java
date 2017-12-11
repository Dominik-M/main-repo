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
import java.util.LinkedList;
import language.Text;
import spiel.InputListener;
import spiel.Trainer;
import static welt.Objekt.BLUMEN;
import static welt.Objekt.GRAS;
import static welt.Objekt.STEIN2;
import static welt.Objekt.WEG;
import static welt.Objekt.WIESE;
import static welt.Objekt.ZAUN;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Ort implements java.io.Serializable {

    public final int WIDTH, HEIGHT, STARTX, STARTY;
    private Objekt defaultObjekt, background;
    private final Objekt[][] objekte;
    private final LinkedList<Trainer> trainer;
    public final Text NAME;     // Used as an ID
    private String soundfilename;

    public Ort(Text n, int width, int height, int startX, int startY) {
        NAME = n;
        WIDTH = width;
        HEIGHT = height;
        objekte = new Objekt[WIDTH][HEIGHT];
        trainer = new LinkedList<>();
        defaultObjekt = Objekt.BODEN;
        background = Objekt.BODEN;
        STARTX = startX;
        STARTY = startY;
        soundfilename = NAME.name().toLowerCase() + ".wav";
    }

    public void addTrainer(Trainer t) {
        trainer.add(t);
    }

    public String getSoundFilename() {
        return soundfilename;
    }

    public Trainer[] getTrainer() {
        return (Trainer[]) trainer.toArray();
    }

    public boolean removeTrainer(Trainer t) {
        return trainer.remove(t);
    }

    public Objekt get(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return defaultObjekt;
        }
        Objekt o = objekte[x][y];
        if (o == null) {
            return defaultObjekt;
        } else {
            return o;
        }
    }

    public boolean isPassable(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && get(x, y).isPassable();
    }

    public void set(int x, int y, Objekt o) {
        objekte[x][y] = o;
    }

    public Objekt[][] getObjekte() {
        Objekt[][] o = new Objekt[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                o[x][y] = get(x, y);
            }
        }
        return o;
    }

    private void setObjekte(Objekt[] o) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int index = x + y * WIDTH;
                if (index >= o.length) {
                    break;
                }
                set(x, y, o[index]);
            }
        }
    }

    public void reload() {
        for (int x = 0; x < objekte.length; x++) {
            for (int y = 0; y < objekte[x].length; y++) {
                if (objekte[x][y] != null) {
                    objekte[x][y].init();
                }
            }
        }
    }

    public static void placeHaus(Ort o, int x, int y, int width, int height, boolean bewohnt) {
        o.set(x, y, new Objekt(ImageFile.DACH_OBEN_LINKS, false));
        for (int i = 0; i < width; i++) {
            o.set(x + i + 1, y, new Objekt(ImageFile.DACH_OBEN_MITTE, false));
        }
        o.set(x + width, y, new Objekt(ImageFile.DACH_OBEN_RECHTS, false));
        o.set(x, y + 1, new Objekt(ImageFile.DACH_UNTEN_LINKS, false));
        for (int i = 0; i < width; i++) {
            o.set(x + i + 1, y + 1, new Objekt(ImageFile.DACH_UNTEN_MITTE, false));
        }
        o.set(x + width, y + 1, new Objekt(ImageFile.DACH_UNTEN_RECHTS, false));
        for (int yi = y + 2; yi < y + height + 2; yi++) {
            o.set(x, yi, new Objekt(ImageFile.FENSTER_LINKS, false));
            for (int xi = x + 1; xi < x + width; xi++) {
                o.set(xi, yi, new Objekt(ImageFile.FENSTER, false));
            }
            o.set(x + width, yi, new Objekt(ImageFile.FENSTER_RECHTS, false));
        }
        o.set(x, y + height + 2, new Objekt(ImageFile.HAUSECKE_UNTEN_LINKS, false));
        for (int xi = x + 1; xi < x + width; xi++) {
            o.set(xi, y + height + 2, new Objekt(ImageFile.HAUSWAND_UNTEN, false));
        }
        if (bewohnt) {
            o.set(x + width / 2, y + height + 2, new Objekt(ImageFile.TUER, true));
        }
        o.set(x + width, y + height + 2, new Objekt(ImageFile.HAUSECKE_UNTEN_RECHTS, false));
    }

    public static void placeHuette(Ort o, int x, int y) {
        o.objekte[x][y] = new Objekt(ImageFile.HUETTE_OBEN_LINKS, false);
        o.objekte[x + 1][y] = new Objekt(ImageFile.HUETTE_OBEN_MITTE, false);
        o.objekte[x + 2][y] = new Objekt(ImageFile.HUETTE_OBEN_MITTE, false);
        o.objekte[x + 3][y] = new Objekt(ImageFile.HUETTE_OBEN_RECHTS, false);
        o.objekte[x][y + 1] = new Objekt(ImageFile.HUETTE_MITTE_LINKS, false);
        o.objekte[x + 1][y + 1] = new Objekt(ImageFile.HUETTE_MITTE, false);
        o.objekte[x + 2][y + 1] = new Objekt(ImageFile.HUETTENFENSTER, false);
        o.objekte[x + 3][y + 1] = new Objekt(ImageFile.HUETTE_MITTE_RECHTS, false);
        o.objekte[x][y + 2] = new Objekt(ImageFile.HUETTE_UNTEN_LINKS, false);
        o.objekte[x + 1][y + 2] = new Objekt(ImageFile.TUER, true);
        o.objekte[x + 2][y + 2] = new Objekt(ImageFile.FENSTER_UNTEN, false);
        o.objekte[x + 3][y + 2] = new Objekt(ImageFile.HUETTE_UNTEN_RECHTS, false);
    }

    public static void addGateway(int x1, int y1, Ort pos1, int x2, int y2, Ort pos2, ImageFile img) {
        pos1.set(x1, y1, new GateObjekt(pos2, x2, y2, img));
        pos2.set(x2, y2, new GateObjekt(pos1, x1, y1, img));
    }

    public static Ort initAlabastia() {
        Ort alabastia = new Ort(Text.ALABASTIA, 20, 18, 5, 6);
        Objekt[] o = new Objekt[]{
            WIESE, WIESE, WIESE, STEIN2, WIESE, WIESE, WIESE, STEIN2, GRAS, GRAS, STEIN2, WIESE, WIESE, WIESE, WIESE, WIESE, WIESE, WIESE, STEIN2, WIESE,
            STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, GRAS, GRAS, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2};
        for (int y = 2; y < 18; y++) {
            alabastia.set(0, y, STEIN2);
            alabastia.set(19, y, STEIN2);
        }
        for (int x = 1; x < 19; x++) {
            alabastia.set(x, 17, STEIN2);
        }
        for (int y = 2; y < 18; y++) {
            alabastia.set(1, y, WIESE);
        }
        for (int y = 14; y < 18; y++) {
            alabastia.set(2, y, WIESE);
            alabastia.set(3, y, WIESE);
        }
        for (int x = 2; x < 18; x++) {
            alabastia.set(x, 6, WEG);
            alabastia.set(x, 7, WEG);
        }
        for (int y = 8; y < 14; y++) {
            alabastia.set(2, y, WEG);
            alabastia.set(3, y, WEG);
        }
        alabastia.setObjekte(o);
        alabastia.set(4, 9, ZAUN);
        alabastia.set(5, 9, ZAUN);
        alabastia.set(6, 9, ZAUN);
        alabastia.set(7, 9, new Sign(Text.ALABASTIA));
        for (int x = 4; x < 8; x++) {
            alabastia.set(x, 10, BLUMEN);
            alabastia.set(x, 11, BLUMEN);
            alabastia.set(x, 12, WEG);
            alabastia.set(x, 13, WEG);
        }
        for (int y = 2; y < 14; y++) {
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
        Person p = new Person("Peter", InputListener.DOWN, null, spiel.Konstanten.HAUTFARBEN[0], Color.BLUE,
                Text.DIALOG_PETER_1, Text.DIALOG_PETER_2, Text.DIALOG_PETER_3, Text.DIALOG_PETER_4);
        alabastia.set(5, 8, p);
        placeHuette(alabastia, 4, 3);
        alabastia.set(3, 5, new Sign(Text.SIGN_PLAYERSHOUSE));
        placeHuette(alabastia, 12, 3);
        alabastia.set(11, 5, new Sign(Text.SIGN_RIVALSHOUSE));
        placeHaus(alabastia, 10, 8, 4, 1, true);
        return alabastia;
    }

    public static Ort initRoute1() {
        Ort route1 = new Ort(Text.ROUTE1, 20, 18, 4, 7);
        // TODO init route 1
        return route1;
    }

    public static Ort initVertania() {
        Ort vertania = new Ort(Text.VERTANIA_CITY, 20, 18, 4, 7);
        //TODO init vertania city
        return vertania;
    }

    public Objekt getBackground() {
        return background;
    }
}
