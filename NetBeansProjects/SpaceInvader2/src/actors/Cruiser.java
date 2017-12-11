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

import armory.Weapon;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import main.SpaceInvader.Team;
import platform.utils.IO;
import platform.utils.Utilities;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class Cruiser extends Ship
{

    private final LinkedList<Turret> turrets = new LinkedList<>();
    /**
     * Vector2Ds defining turrets positions relative to the Cruiser's position
     */
    private final LinkedList<Vector2D> turretPositions = new LinkedList<>();
    private Vector2D target;

    Cruiser(int x, int y, Team team, int maxHp, double maxSpeed, int hullmass, double maxAccel, double maxRot,
            int maxShield, double shieldRegen, String mainSprite, String... spritenames)
    {
        super(x, y, team, maxHp, maxSpeed, hullmass, maxAccel, maxRot, maxShield, shieldRegen, mainSprite, spritenames);
    }

    public Vector2D getTarget()
    {
        return target;
    }

    /**
     * Set target's position relative to the cruisers position.
     *
     * @param target Vector defining target's position relative to the cruisers
     * position or null if not aiming.
     */
    public void setTarget(Vector2D target)
    {
        this.target = target;
    }

    public void addWeapon(Weapon w, int relX, int relY, String spritename)
    {
        Turret t = new Turret(this, w, spritename);
        turrets.add(t);
        turretPositions.add(new Vector2D(relX, relY));
        calcTurretPosition(t, turretPositions.getLast());
        // GameGrid.getInstance().addActor(t);
    }

    private void calcTurretPosition(Turret t, Vector2D relPos)
    {
        Vector2D position = new Vector2D(relPos.x, relPos.y);
        position.rotate(Utilities.toRad(getDirection()));
        t.setX(position.x + getPosition().x - t.getBounds().width / 2);
        t.setY(position.y + getPosition().y - t.getBounds().height / 2);
    }

    @Override
    public void act()
    {
        super.act();
        for (int i = 0; i < turrets.size(); i++)
        {
            calcTurretPosition(turrets.get(i), turretPositions.get(i));
            if (target != null && getAI() == null)
            {
                turrets.get(i).setDirection(Utilities.toDeg(target.sub(turrets.get(i).getPosition()).getDirection()) + 90);
            }
            else
            {
                turrets.get(i).setDirection(getDirection());
            }
            turrets.get(i).act();
        }
    }

    @Override
    public List<Projectile> shoot()
    {
        LinkedList<Projectile> shots = new LinkedList<>();
        for (Turret t : turrets)
        {
            shots.addAll(t.shoot());
        }
        return shots;
    }

    @Override
    public List<Weapon> getWeapons()
    {
        LinkedList<Weapon> weapons = new LinkedList<>();
        for (Turret t : turrets)
        {
            weapons.add(t.getMainWeapon());
        }
        return weapons;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        for (Turret t : turrets)
        {
            t.paint(g);
        }
    }

    @Override
    public String getDataString()
    {
        String s = IO.translate("CLASS") + ": " + IO.translate("CRUISER") + "\n"
                + super.getDataString() + "\n";
        for (int i = 0; i < turrets.size(); i++)
        {
            s += IO.translate("TURRET") + " " + i + ": " + turrets.get(i).getMainWeapon() + "\n";
        }
        return s;
    }
}
