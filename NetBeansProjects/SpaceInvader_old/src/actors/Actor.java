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
package actors;

import ais.AI;
import image.ImageIO;
import image.Sprite;
import java.awt.Graphics;
import java.awt.Rectangle;
import utils.Constants.Team;
import utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public abstract class Actor implements java.io.Serializable {

    private double x = 0;
    private double y = 0;
    private Vector2D speedvector = Vector2D.NULLVECTOR;
    private double direction = 0;
    private boolean visible = true, invalid = false;
    private final String[] spritenames;
    protected int spriteID = 0;
    public final Team team;
    private AI ai;

    public Actor(Team team, String... spritenames) {
        this.team = team;
        this.spritenames = spritenames;
    }

    public int getXOnScreen() {
        return (int) x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getYOnScreen() {
        return (int) y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2D getPosition() {
        Rectangle bounds = this.getBounds();
        return new Vector2D(bounds.getCenterX(), bounds.getCenterY());
    }

    public double getSpeed() {
        return speedvector.magnitude();
    }

    void setSpeed(Vector2D speed) {
        speedvector = speed;
    }

    public Vector2D getSpeedVector() {
        return speedvector;
    }

    public double getDirection() {
        return direction;
    }

    public double getDirectionRad() {
        return utils.Utilities.toRad(direction - 90);
    }

    public void setDirection(double direction) {
        while (direction < 0) {
            direction += 360;
        }
        while (direction >= 360) {
            direction -= 360;
        }
        this.direction = direction;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public abstract void act();

    public void move() {
        Vector2D speedvector = this.getSpeedVector();
        speedvector = speedvector.mult(utils.Constants.DELTA_T / 1000.0);
        x += speedvector.x;
        y += speedvector.y;
    }

    public boolean isColiding(Actor other) {
        if (isNear(other)) {
            return true;
        }
        return false;
    }

    public boolean isNear(Actor other) {
        return this.getBounds().intersects(other.getBounds());
    }

    public AI getAI() {
        return ai;
    }

    public void setAI(AI ki) {
        this.ai = ki;
    }

    public void invalidate() {
        invalid = true;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public Sprite getSprite() {
        if (spritenames != null && spritenames.length > 0) {
            if (spriteID < spritenames.length) {
                return ImageIO.getSprite(spritenames[spriteID]);
            } else {
                return ImageIO.getSprite(spritenames[0]);
            }
        } else {
            return null;
        }
    }

    public Rectangle getBounds() {
        Sprite sprite = getSprite();
        if (sprite != null) {
            return new Rectangle(this.getXOnScreen(), this.getYOnScreen(), sprite.getWidth(),
                    sprite.getHeight());
        } else {
            return new Rectangle(this.getXOnScreen(), this.getYOnScreen(), 1, 1);
        }
    }

    public void paint(Graphics g) {
        Sprite sprite = getSprite();
        if (visible && sprite != null) {
            sprite.draw(g, this.getXOnScreen(), this.getYOnScreen(), direction);
        }
    }
}
