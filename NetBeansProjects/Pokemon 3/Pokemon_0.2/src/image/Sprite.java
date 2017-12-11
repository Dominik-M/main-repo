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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Dictionary;
import utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class Sprite extends utils.SerializableReflectObject {
    // The images to be drawn for this sprite

    private final String name;
    private final BufferedImage image;

    // The standard collision areas (when direction is 0)
    /**
     *
     * @param name
     * @param image
     */
    protected Sprite(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    /**
     * Returns the width of the sprite.
     *
     * @return The width in pixels of this sprite
     */
    public int getWidth() {
        return image.getWidth(null);
    }

    /**
     * Returns the height of the sprite with given id.
     *
     * @return The height in pixels of this sprite
     */
    public int getHeight() {
        return image.getHeight(null);
    }

    /**
     *
     * @return
     */
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Draws the sprite onto the graphics context provided.
     *
     * @param g The graphics context on which to draw the sprite
     * @param x The x location in pixel coordinates at which to draw the sprite
     * @param y The y location in pixel coordinates at which to draw the sprite
     * @param rotDegree
     */
    public void draw(Graphics g, int x, int y, double rotDegree) {
        g.drawImage(image, x, y, null);
        if (Settings.getSettings().drawBounds) {
            g.setColor(Color.black);
            g.drawRect(x, y, image.getWidth(), image.getHeight());
        }
    }

    @Override
    public Dictionary<String, Object> getAttributes() {
        Dictionary<String, Object> atts = new Dictionary();
        atts.add("width", getWidth());
        atts.add("height", getHeight());
        return atts;
    }
}
