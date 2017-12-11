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
package image;

import graphic.ProgressPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import pokemon.PokemonBasis;
import spiel.IO;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class ImageIO {

    public static final Color COLOR_TRANSPARENT = new Color(250, 250, 250);
    private static Icon[] icons;
    private static final Image[] images = new Image[100];

    public enum ImageFile {

        BODEN("boden.png"), BLUMEN("blumen.png"), DACH_OBEN_LINKS("dach_ol.png"),
        DACH_OBEN_MITTE("dach_om.png"), DACH_OBEN_RECHTS("dach_or.png"),
        DACH_UNTEN_LINKS("dach_ul.png"), DACH_UNTEN_MITTE("dach_um.png"),
        DACH_UNTEN_RECHTS("dach_ur.png"), FENSTER("fenster.png"),
        FENSTER_LINKS("fenster_l.png"), FENSTER_RECHTS("fenster_r.png"),
        FENSTER_UNTEN("fenster_u.png"), GRAS("gras.png"), HAUSFENSTER_UNTEN("hausfenster.png"),
        HAUSWAND_UNTEN("hauswand_um.png"), HAUSECKE_UNTEN_LINKS("hausecke_ul.png"),
        HAUSECKE_UNTEN_RECHTS("hausecke_ur.png"), HUETTE_MITTE("huette_m.png"),
        HUETTE_MITTE_LINKS("huette_ml.png"), HUETTE_MITTE_RECHTS("huette_mr.png"),
        HUETTE_OBEN_LINKS("huette_ol.png"), HUETTE_OBEN_RECHTS("huette_or.png"),
        HUETTE_OBEN_MITTE("huette_om.png"), HUETTE_UNTEN_LINKS("huette_ul.png"),
        HUETTE_UNTEN_RECHTS("huette_ur.png"),
        HUETTENFENSTER("huettenfenster.png"), SCHILD("schild.png"),
        STEIN("stein.png"), STEIN2("stein2.png"), TUER("tuer.png"), TRAINER_DOWN("trainer_down.png"),
        TRAINER_RIGHT("trainer_right.png"), TRAINER_LEFT("trainer_left.png"),
        TRAINER_UP("trainer_up.png"), UFER_LINKS_MITTE("ufer_lm.png"), UFER_OBEN_LINKS("ufer_ol.png"),
        UFER_OBEN_MITTE("ufer_om.png"), UFER_OBEN_RECHTS("ufer_or.png"), UFER_RECHTS_MITTE("ufer_rm.png"),
        UFER_UNTEN_LINKS("ufer_ul.png"), UFER_UNTEN_MITTE("ufer_um.png"), UFER_UNTEN_RECHTS("ufer_ur.png"),
        WASSER("wasser.png"), WEG("weg.png"), WIESE("wiese.png"), ZAUN("zaun.png");

        ImageFile(String filename) {
            this.filename = filename;
        }
        private Image img;
        private Image nativeImage;
        private final String filename;

        public void scale(int width, int height) {
            //IO.IOMANAGER.println(filename + " scaled", IO.MessageType.DEBUG);
            img = nativeImage.getScaledInstance(width, height, 0);
        }

        public int getImgWidth() {
            return img.getWidth(null);
        }

        public int getImgHeight() {
            return img.getHeight(null);
        }

        public void draw(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }

    public static void initIcons() {
        ProgressPanel.setProgress(0);
        ProgressPanel.setText("Initialising Images...");
        PokemonBasis[] poks = PokemonBasis.values();
        icons = new Icon[poks.length];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = initPokemonIcon(poks[i]);
            ProgressPanel.setProgress((int) ((i + 1) * 100 / icons.length));
        }
        ProgressPanel.setProgress(0);
        ImageFile[] images = ImageFile.values();
        for (int i = 0; i < images.length; i++) {
            if (!initImage(images[i])) {
                IO.IOMANAGER.println(images[i].filename + " cannot be loaded!", IO.MessageType.ERROR);
            }
            ProgressPanel.setProgress((int) ((i + 1) * 100 / images.length));
        }
    }

    public static boolean initImage(ImageFile file) {
        Image img = null;
        try {
            img = javax.imageio.ImageIO.read(ImageIO.class.getResource("/image/" + file.filename));
        } catch (IOException ex) {
            Logger.getLogger(ImageIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            img = ImageIO.makeColorTransparent(img, COLOR_TRANSPARENT);
            file.nativeImage = img;
            file.img = img;
        }
        return true;
    }

    public static ImageIcon initPokemonIcon(PokemonBasis pok) {
        ImageIcon bild;
        try {
            bild = new javax.swing.ImageIcon(ImageIO.class.getResource("/pokemon/img/" + pok.getBaseName().toLowerCase() + "Icon.png"));
        } catch (Exception e) {
            bild = new javax.swing.ImageIcon(ImageIO.class.getResource("/pokemon/img/abstract.png"));
        }
        bild.setImage(ImageIO.makeColorTransparent(bild.getImage(), COLOR_TRANSPARENT));
        return bild;
    }

    public static Icon getPokemonIcon(int id) {
        return icons[id];
    }

    public static int imgAlloc(Image img) {
        for (int i = 0; i < images.length; i++) {
            if (images[i] == null) {
                images[i] = makeColorTransparent(img, COLOR_TRANSPARENT);
                return i;
            }
        }
        return -1;
    }

    public static boolean imgFree(int index) {
        if (index >= images.length || index < 0 || images[index] == null) {
            return false;
        } else {
            images[index] = null;
            return true;
        }
    }

    public static Image getImage(int index) {
        return images[index];
    }

    public static Image makeColorTransparent(Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}
