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

import java.awt.Color;
import java.awt.Graphics;
import main.SpaceInvader;
import main.SpaceInvader.Team;
import platform.gamegrid.Actor;
import platform.utils.IO;
import platform.utils.Settings;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public abstract class Ship extends Actor implements Shooting {

    private boolean accelerate = false;
    private boolean brake = false;
    private boolean shooting = false;
    private double maxSpeed;
    private double rotation;
    private double maxRotation;
    private int maxHp;
    private int hp;
    private int maxShield, shieldCD; // max shield energy in hitpoints and energy regeneration cooldown in ms
    private double shield, shieldPower; // shield regeneration speed in hitpoints per second
    private boolean shieldOn, superShield;
    private final int hullmass;
    private int totalMass;
    private double maxPower;

    Ship(int x, int y, Team team, int maxHp, double maxSpeed, int hullmass, double maxPower, double maxRot,
            int maxShield, double shieldRegen, String mainSprite, String... spritenames) {
        super(mainSprite, spritenames);
        this.maxHp = maxHp;
        hp = maxHp;
        setX(x);
        setY(y);
        this.hullmass = hullmass;
        this.totalMass = hullmass;
        this.maxSpeed = maxSpeed;
        this.maxPower = maxPower;
        this.maxRotation = maxRot;
        this.maxShield = maxShield;
        this.shield = this.maxShield;
        this.shieldOn = true;
        this.superShield = false;
        this.shieldCD = 0;
        this.shieldPower = shieldRegen;
        setTeam(team.ordinal());
    }

    @Override
    public void act() {
        if (getAI() != null) {
            getAI().preAct(this);
        }
        if (accelerate) {
            Vector2D velocity = this.getSpeedVector().clone();
            velocity = velocity.add(getAcceleration());
            if (velocity.magnitude() > maxSpeed) {
                velocity.normalize();
                velocity = velocity.mult(maxSpeed);
            }
            this.setSpeed(velocity);
        }
        if (brake) {
            if (this.getSpeedVector().magnitude() > 0) {
                Vector2D velocity = this.getSpeedVector().clone();
                velocity.normalize();
                velocity = velocity.mult(maxPower * SpaceInvader.DELTA_T / 1000.0 / totalMass);
                if (velocity.magnitude() >= this.getSpeedVector().magnitude()) {
                    this.setSpeed(Vector2D.NULLVECTOR);
                } else {
                    this.setSpeed(this.getSpeedVector().sub(velocity));
                }
            }
        }
        if (rotation != 0) {
            this.setDirection(getDirection() + (rotation * SpaceInvader.DELTA_T / 1000.0));
        }
        move();
        if (!SpaceInvader.getInstance().isInGrid(getBounds())) { // TODO remove this wrong direction dependency
            this.setSpeed(this.getSpeedVector().invert());
            if (this.equals(SpaceInvader.getInstance().getMShip())) {
                if (SpaceInvader.getInstance().getMode() == SpaceInvader.Mode.ARCADE) {
                    IO.println(IO.translate("LOST_IN_SPACE_WARNING"), IO.MessageType.IMPORTANT);
                } else {
                    this.setSpeed(this.getSpeedVector().invert());
                    SpaceInvader.getInstance().switchMap();
                }
            }
        }
        if (shieldOn) {
            if (shieldCD > 0) {
                shieldCD -= SpaceInvader.DELTA_T;
            } else if (shield < maxShield) {
                shield += SpaceInvader.DELTA_T * getShieldPower() / 1000.0;
                if (shield > maxShield) {
                    shield = maxShield;
                }
            }
        }
    }

    private Vector2D getAcceleration() {
        return new Vector2D(Math.cos(this.getDirectionRad()), Math.sin(getDirectionRad()))
                .mult(maxPower * SpaceInvader.DELTA_T / 1000.0 / totalMass);
    }

    public boolean isAccelerating() {
        return accelerate;
    }

    public void accelerate(Vector2D acceleration) {
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
            this.spriteID = 1;
        } else {
            this.spriteID = 0;
        }
    }

    public void setBraking(boolean braking) {
        this.brake = braking;
    }

    public boolean isBraking() {
        return brake;
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
        int dmg = p.source.getDamage();
        if (shield > 0) {
            if (shield < dmg) {
                shield = 0;
                dmg -= (int) shield;
            } else {
                shield -= dmg;
                dmg = 0;
            }
            if (!superShield) {
                shieldCD = 1000;
            }
        }
        hp -= dmg;
        if (hp <= 0) {
            invalidate();
        }
    }

    @Override
    public void paint(Graphics g) {
        if (superShield) {
            g.setColor(Color.cyan);
            g.fillOval(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
        }
        super.paint(g);
        if ((Boolean) Settings.get("drawLifes")) {
            g.setColor(Color.red);
            g.fillRect(getXOnScreen() - 5, getYOnScreen(), 30, 5);
            g.setColor(Color.green);
            g.fillRect(getXOnScreen() - 5, getYOnScreen(), hp * 30 / maxHp, 5);

            g.setColor(Color.lightGray);
            g.fillRect(getXOnScreen() - 5, getYOnScreen() + 10, 30, 5);
            g.setColor(Color.cyan);
            g.fillRect(getXOnScreen() - 5, getYOnScreen() + 10, (int) (shield * 30 / maxShield), 5);
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
        IO.println(this + ".setMaxRotation(): maxRotation speed set to " + maxRotation, IO.MessageType.DEBUG);
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
            IO.println(this + ".setMaxHP(): maxHP set to " + maxHp, IO.MessageType.DEBUG);
        }
    }

    /**
     * Retrieve the maximum acceleration of the ship.
     *
     * @return maximum acceleration of the ship in pixels per squaresecond.
     */
    public double getMaxAcceleration() {
        return maxPower / totalMass;
    }

    /**
     * Retrieve the hull mass of the ship. The unit is artificial.
     *
     * @return total mass of the ship in pixels per squaresecond.
     */
    public int getHullMass() {
        return hullmass;
    }

    /**
     * Retrieve the total mass of the ship. The unit is artificial.
     *
     * @return total mass of the ship in pixels per squaresecond.
     */
    public int getTotalMass() {
        return totalMass;
    }

    /**
     * Set the total mass of the ship.
     *
     */
    public void setTotalMass(int totalMass) {
        this.totalMass = totalMass;
        IO.println(this + ".setTotalMass(): totalMass set to " + totalMass, IO.MessageType.DEBUG);
    }

    /**
     * Retrieve the maximum power of the ship.
     *
     * @return maximum acceleration of the ship in pixels per squaresecond.
     */
    public double getMaxPower() {
        return maxPower;
    }

    /**
     * Sets the maximum Power of the ship.
     *
     * @param maxPower maximum power of the Ship.
     */
    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
        IO.println(this + ".setMaxPower(): maxPower set to " + maxPower, IO.MessageType.DEBUG);
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
            IO.println(this + ".setMaxSpeed(): maxSpeed set to " + maxSpeed, IO.MessageType.DEBUG);
        }
    }

    public int getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(int maxShield) {
        this.maxShield = maxShield;
    }

    public int getShield() {
        return (int) shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public double getShieldPower() {
        if (superShield) {
            return shieldPower * 2;
        } else {
            return shieldPower;
        }
    }

    public void setShieldPower(double shieldPower) {
        this.shieldPower = shieldPower;
    }

    public boolean getShieldOn() {
        return shieldOn;
    }

    public void setShieldOn(boolean shieldOn) {
        this.shieldOn = shieldOn;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public void setSuperShieldOn(boolean superShieldOn) {
        if (superShieldOn) {
            this.shieldCD = 0;
        } else {
            this.shieldCD = 1000;
        }
        this.superShield = superShieldOn;
    }

    public boolean getSuperShieldOn() {
        return this.superShield;
    }

    @Override
    public boolean isShooting() {
        return shooting;
    }

    public void repair() {
        hp = maxHp;
        IO.println(this + ".repair(): Ship repaired.", IO.MessageType.DEBUG);
    }

    @Override
    public void move() {
        Vector2D speedvector = this.getSpeedVector();
        speedvector = speedvector.mult(SpaceInvader.DELTA_T / 1000.0);
        x += speedvector.x;
        y += speedvector.y;
    }

    public String getDataString() {
        return IO.translate("HP") + ": " + getMaxHp() + "\n"
                + IO.translate("HULLMASS") + ": " + getHullMass() + "\n"
                + IO.translate("TOTALMASS") + ": " + getTotalMass() + "\n"
                + IO.translate("SHIELD") + ": " + getMaxShield() + "\n"
                + IO.translate("SHIELDPOWER") + ": " + getShieldPower() + "\n"
                + IO.translate("SPEED") + ": " + getMaxSpeed() + "\n"
                + IO.translate("POWER") + ": " + getMaxAcceleration() + "\n"
                + IO.translate("ROTSPEED") + ": " + getMaxRotation();
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
