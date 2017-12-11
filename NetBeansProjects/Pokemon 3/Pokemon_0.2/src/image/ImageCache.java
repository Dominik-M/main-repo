/*
 * Copyright (C) 2016 Dundun
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

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Dictionary;
import utils.SerializableReflectObject;

/**
 *
 * @author Dundun
 */
public class ImageCache extends SerializableReflectObject {

    private final utils.Dictionary<String, int[][]> imageData = new utils.Dictionary<>();

    ImageCache(Sprite... sprites) {
        for (int i = 0; i < sprites.length; i++) {
            int width = sprites[i].getWidth();
            int height = sprites[i].getHeight();
            int[][] rgb = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    rgb[x][y] = sprites[i].getImage().getRGB(x, y);
                }
            }
            imageData.add(sprites[i].toString(), rgb);
        }
    }

    public Sprite[] createSprites() {
        Sprite[] sprites = new Sprite[imageData.size()];
        for (int i = 0; i < sprites.length; i++) {
            String name = imageData.getKeys().get(i);
            int[][] rgb = imageData.get(name);
            BufferedImage img = new BufferedImage(rgb.length, rgb[0].length, BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < rgb.length; x++) {
                for (int y = 0; y < rgb[x].length; y++) {
                    img.setRGB(x, y, rgb[x][y]);
                }
            }
            sprites[i] = new Sprite(name, img);
        }
        return sprites;
    }

    @Override
    public Dictionary<String, Object> getAttributes() {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : ImageCache.class.getDeclaredFields()) {
            try {
                values.add(f.getName(), f.get(this));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ImageCache.class.getName()).log(Level.SEVERE,
                        null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ImageCache.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
        return values;
    }
}
