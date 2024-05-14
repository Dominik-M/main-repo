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

/**
 *
 * @author Dominik
 */
public class AnimatedObjekt extends Objekt {

    private int currentFrame;
    private int frameCount;
    private int maxFrameCount;
    private final java.util.LinkedList<ImageFile> images;

    /**
     *
     * @param standardImg
     * @param passable
     * @param frames
     */
    public AnimatedObjekt(ImageFile standardImg, boolean passable, ImageFile... frames) {
        super(standardImg, passable);
        images = new java.util.LinkedList<>();
        images.add(standardImg);
        for (ImageFile img : frames) {
            images.add(img);
        }
        currentFrame = 0;
        frameCount = 0;
        maxFrameCount = 20;
    }

    /**
     *
     * @return
     */
    public int getMaxFrameCount() {
        return maxFrameCount;
    }

    /**
     *
     * @param maxFrameCountNeu
     */
    public void setMaxFrameCount(int maxFrameCountNeu) {
        maxFrameCount = maxFrameCountNeu;
    }

    /**
     *
     */
    public void nextFrame() {
        frameCount++;
        if (frameCount >= maxFrameCount) {
            frameCount = 0;
            currentFrame++;
            if (currentFrame >= images.size()) {
                currentFrame = 0;
            }
            setImageFile(images.get(currentFrame));
        }
    }
}
