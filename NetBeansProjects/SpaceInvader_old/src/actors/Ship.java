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

import graphic.GameGrid;
import java.awt.Color;
import java.awt.Graphics;
import main.GameData;
import utils.Constants;
import utils.Constants.Team;
import utils.IO;
import utils.Settings;
import utils.Text;
import utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public abstract class Ship extends Actor implements Shooting {

    private boolean accelerate = false;
    private boolean brake = false;
    private boolean shooting = false;
    private double maxAcceleration;
    private double maxSpeed;
    private double rotation;
    private double maxRotation;
    private int maxHp;
    private int hp;

    public Ship(int x, int y, Team team, int maxHp, double maxSpeed, double maxAccel,
            double maxRot, String... spritenames) {
        super(team, spritenames);
        this.maxHp = maxHp;
        hp = maxHp;
        setX(x);
        setY(y);
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAccel;
        this.maxRotation = maxRot;
    }

    public Ship() {
        this(0, 0, Team.EARTH, 10, 200, 100, 180, Constants.IMAGENAME_SPACESHIP,
                Constants.IMAGENAME_SPACESHIPACC);
    }

    @Override
    public void act() {
        if (getAI() != null) {
            getAI().preAct(this);
        }
        if (accelerate) {
            accelerate(getAcceleration());
        }
        if (brake) {
            if (this.getSpeedVector().magnitude() > 0) {
                Vector2D velocity = this.getSpeedVector().clone();
                velocity.normalize();
                velocity = velocity.mult(maxAcceleration * Constants.DELTA_T / 1000.0);
                if (velocity.magnitude() >= this.getSpeedVector().magnitude()) {
                    this.setSpeed(Vector2D.NULLVECTOR);
                } else {
                    this.setSpeed(this.getSpeedVector().sub(velocity));
                }
            }
        }
        if (rotation != 0) {
            this.setDirection(getDirection() + (rotation * Constants.DELTA_T / 1000.0));
        }
        move();
        if (!GameGrid.getInstance().isInGrid(getBounds())) {
            this.setSpeed(this.getSpeedVector().invert());
            if (this.equals(GameGrid.getInstance().getMShip())) {
                if (GameGrid.getInstance().getGameMode() == GameData.Mode.ARCADE) {
                    IO.println(Text.LOST_IN_SPACE_WARNING.toString(), IO.MessageType.IMPORTANT);
                } else {
                    GameGrid.getInstance().switchMap();
                }
            }
        }
    }

    public Vector2D getAcceleration() {
        return new Vector2D(Math.cos(this.getDirectionRad()), Math.sin(getDirectionRad()))
                .mult(maxAcceleration * Constants.DELTA_T / 1000.0);
    }

    public boolean isAccelerating() {
        return accelerate;
    }

    private void accelerate(Vector2D acceleration) {
        Vector2D velocity = getSpeedVector().add(acceleration);
        if (velocity.magnitude() > maxSpeed) {
            velocity.normalize();
            velocity = velocity.mult(maxSpeed);
        }
        setSpeed(velocity);
    }

    @Override
    public void setSpeed(Vector2D speed) {
        if (speed.magnitude() > maxSpeed) {
            speed.normalize();
            super.setSpeed(speed.mult(maxSpeed));
        } else {
            super.setSpeed(speed);
        }
    }

    public void setAccelerating(boolean accelerate) {
        this.accelerate = accelerate;
        if (accelerate) {
            setBraking(false);
            this.spriteID = 1;
        } else {
            this.spriteID = 0;
        }
    }

    public void setBraking(boolean braking) {
        this.brake = braking;
        if (braking) {
            accelerate = false;
        }
    }

    public void rotateLeft() {
        rotation = -maxRotation;
    }

    public void rotateRight() {
        rotation = maxRotation;
    }

    public void stopRotate() {
        rotation = 0;
    }

    public void stop() {
        setSpeed(Vector2D.NULLVECTOR);
        setAccelerating(false);
        setBraking(false);
        setShooting(false);
        stopRotate();
    }

    // {
    // Rectangle bounds = this.getBounds();
    // return new Projectile(this, bounds.x + bounds.width / 2, bounds.y + bounds.height / 2,
    // new Vector2D(Math.cos(this.getDirectionRad()), Math.sin(getDirectionRad())).mult(500));
    // }
    /**
     * Notify that this ship was hit by a projectile. Invalidates if destroyed
     * by that.
     *
     * @param p the projectile that hit the ship.
     */
    public void hit(Projectile p) {
        hp -= p.source.getDamage();
        if (hp <= 0) {
            invalidate();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (Settings.getSettings().drawLifes) {
            g.setColor(Color.red);
            g.fillRect(getXOnScreen() - 5, getYOnScreen(), 30, 5);
            g.setColor(Color.green);
            g.fillRect(getXOnScreen() - 5, getYOnScreen(), hp * 30 / maxHp, 5);
        }
    }

    public int getHP() {
        return hp;
    }

    /**
     * Retrieve the maximal rotation speed in degree per second.
     *
     * @return maximal rotation speed in degree per second
     */
    public double getMaxRotation() {
        return maxRotation;
    }

    /**
     * Set the maximum rotation speed in degree per second. Be aware that a
     * negative rotation speed will invert the steering controls.
     *
     * @param maxRotation max rotation speed in degree per second.
     */
    public void setMaxRotation(double maxRotation) {
        this.maxRotation = maxRotation;
    }

    /**
     * Retrieve the maximum hitpoints.
     *
     * @return the maximum hitpoints.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Set the maximum hitpoints of the ship.
     *
     * @param maxHp maximum hitpoints of the ship
     */
    public void setMaxHp(int maxHp) {
        if (maxHp > 0) {
            this.maxHp = maxHp;
        }
    }

    /**
     * Retrieve the maximum acceleration of the ship.
     *
     * @return maximum acceleration of the ship in pixels per squaresecond.
     */
    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    /**
     * Set the maximum acceleration of the ship.
     *
     * @param maxAcceleration maximum acceleration of the ship in pixels per
     * squaresecond.
     */
    public void setMaxAcceleration(double maxAcceleration) {
        if (maxAcceleration >= 0) {
            this.maxAcceleration = maxAcceleration;
        }
    }

    /**
     * Retrieve the maximum speed of the ship.
     *
     * @return the maximum speed of the ship in pixels per second.
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Set the maximum speed of the ship.
     *
     * @param maxSpeed the maximum speed of the ship in pixels per second.
     */
    public void setMaxSpeed(double maxSpeed) {
        if (maxSpeed >= 0) {
            this.maxSpeed = maxSpeed;
        }
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    @Override
    public boolean isShooting() {
        return shooting;
    }

    public void repair() {
        hp = maxHp;
    }

    public String getDataString() {
        return Text.HP + ": " + getMaxHp() + "\n" + Text.SPEED + ": " + getMaxSpeed() + "\n" + Text.ACCEL + ": "
                + getMaxAcceleration() + "\n" + Text.ROTSPEED + ": " + getMaxRotation();
    }
}
