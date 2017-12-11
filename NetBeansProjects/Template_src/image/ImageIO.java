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
package image;

import graphic.ProgressPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import utils.Constants;
import utils.IO;
import utils.IO.MessageType;
import utils.Text;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 14.03.2016
 */
public class ImageIO {

    private static final utils.Dictionary<String, Sprite> sprites = new utils.Dictionary<>();

    private ImageIO() {
    }

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
        private final String filename;

        public File getFile() {
            return new File(Constants.IMAGE_DIRECTORY + filename);
        }

        public String toString() {
            return filename;
        }
    }

    public static Sprite getSprite(String name) {
        Sprite sprite = sprites.get(name);
        if (sprite == null) {
            IO.makeToast("Image " + name + " is missing", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return sprite;
    }

    public static java.util.LinkedList<String> getSpriteNames() {
        return sprites.getKeys();
    }

    public static void initAllSprites() {
        try {
            ProgressPanel.setProgress(0);
            ProgressPanel.setText(Text.LOADING_IMAGES.toString());
            File[] files = new File(utils.Constants.IMAGE_DIRECTORY).listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    if (ImageIO.initSprite(file, false)) {
                        IO.println("initialized sprite: " + file.getName(), IO.MessageType.DEBUG);
                    }
                }
                ProgressPanel.setProgress(100 * i / files.length);
            }
        } catch (Exception ex) {
            IO.println("ImageIO Error: " + ex, IO.MessageType.DEBUG);
            IO.printException(ex);
        } finally {
            ProgressPanel.setProgress(100);
            ProgressPanel.setText(Text.READY.toString());
        }
    }

    public static boolean initSprite(File file, boolean rotatable) {
        try {
            if (!sprites.containsKey(file.getName())) {
                BufferedImage bufferedSourceImage = javax.imageio.ImageIO.read(file);
                Image sourceImage = ImageIO.makeColorTransparent(bufferedSourceImage,
                        utils.Constants.COLOR_TRANSPARENT);
                int w = bufferedSourceImage.getWidth();
                int h = bufferedSourceImage.getHeight();
                BufferedImage[] images = new BufferedImage[64];
                // create an accelerated image of the right size to store our sprite in
                GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice().getDefaultConfiguration();
                if (rotatable) {
                    int s = (int) Math.ceil(Math.sqrt(w * w + h * h));
                    BufferedImage biTmp = gc.createCompatibleImage(s, s, Transparency.BITMASK);
                    Graphics2D gTmp = biTmp.createGraphics();
                    gTmp.drawImage(sourceImage, (s - w) / 2, (s - h) / 2, null);

                    for (int i = 0; i < 64; i++) {
                        images[i] = gc.createCompatibleImage(s, s, Transparency.BITMASK);
                        Graphics2D g2D = images[i].createGraphics();

                        g2D.translate(s / 2, s / 2); // Translate the coordinate system (zero a image's center)
                        g2D.rotate(Math.toRadians(360.0 / 64 * i)); // Rotate the image
                        g2D.translate(-s / 2, -s / 2); // Translate the coordinate system (zero a image's center)
                        g2D.drawImage(biTmp, 0, 0, null);
                    }
                    gTmp.dispose();
                } else {
                    images[0] = gc.createCompatibleImage(w, h, Transparency.BITMASK);

                    Graphics2D g2D = images[0].createGraphics();
                    g2D.drawImage(sourceImage, 0, 0, null);
                }
                Sprite sprite = new Sprite(images);
                // try {
                // Thread.sleep(100); // TODO remove this sloc
                // } catch (Exception ex) {
                // }
                return sprites.add(file.getName(), sprite);
            }
        } catch (IOException ex) {
            IO.println("ImageIO error: " + ex, MessageType.ERROR);
            IO.printException(ex);
        }
        return false;
    }

    public static Icon initIcon(File file) {
        Icon icon = null;
        try {
            icon = new javax.swing.ImageIcon(file.getAbsolutePath());
        } catch (Exception ex) {
            IO.println("ImageIO error: " + ex, MessageType.ERROR);
            IO.printException(ex);
        }
        return icon;
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
