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
package armory;

import actors.Projectile;
import java.util.List;
import main.SpaceInvader;
import platform.gamegrid.Actor;
import platform.image.ImageIO;
import platform.utils.IO;
import platform.utils.SerializableReflectObject;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 28.03.2016
 */
public abstract class Weapon extends SerializableReflectObject {

    public Actor owner;
    public final int projectileSpeed;
    protected double cooldown = 0;
    private int damage;
    private int range;
    private int spreadAngle;
    private final String name;
    private String projectileImage;

    Weapon(String name, int damage, int projectileSpeed, int range) {
        this(name, damage, projectileSpeed, range, 0, GunFactory.IMAGENAME_BOMB);
    }

    Weapon(String name, int damage, int projectileSpeed, int range, int spread) {
        this(name, damage, projectileSpeed, range, spread, GunFactory.IMAGENAME_BOMB);
    }

    Weapon(String name, int damage, int projectileSpeed, int range, int spread,
            String projectileImage) {
        this.projectileSpeed = projectileSpeed;
        this.damage = damage;
        this.range = range;
        this.name = name;
        this.spreadAngle = spread;
        this.projectileImage = projectileImage;
    }

    public abstract List<Projectile> shoot(int x, int y, double direction);

    public void refresh() {
        cooldown -= SpaceInvader.DELTA_T;
        if (cooldown < 0) {
            cooldown = 0;
        }
    }

    public int getCooldown() {
        return (int) cooldown;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
        IO.println(this.toString() + ".setDamage(): Damage set to " + damage, IO.MessageType.DEBUG);
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
        IO.println(this.toString() + ".setRange(): Range set to " + range, IO.MessageType.DEBUG);
    }

    public int getSpreadAngle() {
        return spreadAngle;
    }

    public void setSpreadAngle(int angle) {
        while (angle > 360) {
            angle -= 360;
        }
        while (angle < 0) {
            angle += 360;
        }
        spreadAngle = angle;
        IO.println(this.toString() + ".setSpreadAngle(): Angle set to " + angle,
                IO.MessageType.DEBUG);
    }

    public String getProjectileImage() {
        return projectileImage;
    }

    public void setProjectileImage(String projectileImage) {
        if (ImageIO.containsSprite(projectileImage)) {
            this.projectileImage = projectileImage;
            IO.println(this.toString() + ".setProjectileImage(): Image set to " + projectileImage,
                    IO.MessageType.DEBUG);
        } else {
            IO.println(this.toString() + ".setProjectileImage(): Image cannot set to " + projectileImage + " due to it is not initialized",
                    IO.MessageType.ERROR);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDataString() {
        return IO.translate("DAMAGE") + ": " + this.damage + "\n" + IO.translate("RANGE") + ": "
                + this.range + "\n" + IO.translate("PROJECTILESPEED") + ": " + this.projectileSpeed;
    }
}
