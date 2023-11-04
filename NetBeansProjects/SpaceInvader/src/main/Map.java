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
package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import platform.image.ImageIO;
import platform.image.Sprite;
import platform.utils.IO;
import platform.utils.SerializableReflectObject;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 27.03.2016
 */
public class Map extends SerializableReflectObject {

    public final Vector2D position;
    public final int WIDTH, HEIGHT;
    public final Rectangle LANDING_ZONE;
    private final String background;

    public Map(String spritename, Rectangle landingZone, Vector2D position) {
        background = spritename;
        WIDTH = getSprite().getWidth();
        HEIGHT = getSprite().getHeight();
        LANDING_ZONE = landingZone;
        this.position = position;
    }

    public final Sprite getSprite() {
        if (!ImageIO.containsSprite(background)) {
            ImageIO.initSprite("Map_Space." + position, ImageIO.getRandomBackground(WIDTH, HEIGHT),
                    false);
        }
        return ImageIO.getSprite(background);
    }

    public void paint(Graphics g) {
        getSprite().draw(g, 0, 0, 0);
    }

    public Image getImage() {
        return getSprite().getImages()[0];
    }

    @Override
    public String toString() {
        //return background.substring(4, background.indexOf(".")).toUpperCase() + " " + position;
        return background;
    }

    public static Map getRandomMap(int width, int height, Vector2D position) {
        String name = "Map_Space." + position;
        if (ImageIO.containsSprite(name) || ImageIO.initSprite(name, ImageIO.getRandomBackground(width, height), false)) {
            Map map = new Map(name, null, position);
            IO.println("Created Map " + map, IO.MessageType.DEBUG);
            return map;
        } else {
            IO.println("failed to create Map " + name, IO.MessageType.ERROR);
            return null;
        }
    }
}
