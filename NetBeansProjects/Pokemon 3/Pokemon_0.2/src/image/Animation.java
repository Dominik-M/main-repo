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

import java.awt.Graphics;
import utils.Dictionary;

/**
 *
 * @author Dundun
 */
public class Animation extends utils.SerializableReflectObject {

    private final Sprite[] sprites;
    private int index;

    public Animation(Sprite... sprites) {
        this.sprites = sprites;
        index = 0;
    }

    public boolean isFinished() {
        return index >= sprites.length;
    }

    public void start() {
        index = 0;
    }

    public boolean draw(Graphics g, int x, int y, int rotDegree) {
        if (!isFinished()) {
            sprites[index].draw(g, x, y, rotDegree);
            index++;
            return true;
        }
        return false;
    }

    @Override
    public Dictionary<String, Object> getAttributes() {
        Dictionary<String, Object> atts = new Dictionary();
        atts.add("index", index);
        for (int i = 0; i < sprites.length; i++) {
            atts.add("sprites[" + i + "]", sprites[i]);
        }
        return atts;
    }
}
