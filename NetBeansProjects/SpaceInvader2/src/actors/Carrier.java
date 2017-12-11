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

import armory.GunFactory;
import armory.Weapon;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import main.SpaceInvader;
import main.SpaceInvader.Team;
import platform.utils.IO;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class Carrier extends Cruiser
{

    private final LinkedList<Ship> content = new LinkedList<>();
    private final CopyOnWriteArrayList<Ship> fleet = new CopyOnWriteArrayList<>();
    private int capacity;
    private double productionRate;
    private int pCounter, fCounter;
    private boolean produceFleets;

    Carrier(int x, int y, Team team, int maxHp, double maxSpeed, int hullmass, double maxAccel, double maxRot,
            int capacity, double productionRate, int maxShield, double shieldRegen, String mainSprite, String... spritenames)
    {
        super(x, y, team, maxHp, maxSpeed, hullmass, maxAccel, maxRot, maxShield, shieldRegen, mainSprite, spritenames);
        this.capacity = capacity;
        this.productionRate = productionRate;
    }

    @Override
    public void act()
    {
        super.act();
        int fleetsize = countLivingWingmen();
        if (fleetsize < capacity)
        {
            pCounter -= SpaceInvader.DELTA_T;
            if (pCounter <= 0)
            {
                if (produceFleets)
                {
                    if (fleetsize + fCounter < capacity)
                    {
                        fCounter++;
                        if (fCounter > ShipFactory.DEFAULT_FLEET_SIZE)
                        {
                            Vector2D position = getPosition();
                            Ship leader = ShipFactory.createFighter((int) position.x, (int) position.y, Team.get(getTeam()), ShipFactory.DEFAULT_HP_VERYLOW, GunFactory.getGun());
                            leader.setAI(new ais.HunterAI(this));
                            Ship[] wingmen = ShipFactory.createFleet(leader,
                                    ShipFactory.DEFAULT_FLEET_SIZE);
                            SpaceInvader.getInstance().addActor(leader);
                            for (Ship wingman : wingmen)
                            {
                                fleet.add(wingman);
                                SpaceInvader.getInstance().addActor(wingman);
                            }
                            fCounter = 0;
                        }
                    }
                }
                else
                {
                    Vector2D position = getPosition();
                    Ship wingman = ShipFactory.createWingman((int) position.x, (int) position.y, ShipFactory.DEFAULT_HP_VERYLOW, this,
                            GunFactory.getGun(), ShipFactory.IMAGENAME_FIGHTER);
                    fleet.add(wingman);
                    SpaceInvader.getInstance().addActor(wingman);
                }
                pCounter = (int) (SpaceInvader.DELTA_T * 1000 / productionRate);
            }
        }
        if (isInvalid())
        {
            for (Ship s : deployAll())
            {
                SpaceInvader.getInstance().addActor(s);
            }
        }
    }

    public boolean garrison(List<Ship> ships)
    {
        if (content.size() + ships.size() <= capacity)
        {
            content.addAll(ships);
            return true;
        }
        return false;
    }

    public int countLivingWingmen()
    {
        for (Ship s : fleet)
        {
            if (s.isInvalid())
            {
                fleet.remove(s);
            }
        }
        return fleet.size();
    }

    public boolean garrison(Ship... ships)
    {
        LinkedList<Ship> shiplist = new LinkedList<>();
        for (int i = 0; i < ships.length; i++)
        {
            shiplist.add(ships[i]);
        }
        return garrison(shiplist);
    }

    public int getContentSize()
    {
        return content.size();
    }

    public List<Ship> deployAll()
    {
        LinkedList<Ship> deployed = new LinkedList<>();
        Vector2D position = this.getPosition();
        for (int i = 0; i < content.size(); i++)
        {
            Ship s = content.get(i);
            s.setX(position.x + i + 1);
            s.setY(position.y + i + 1);
        }
        content.removeAll(deployed);
        return deployed;
    }

    public List<Ship> deployFirst(int count)
    {
        LinkedList<Ship> deployed = new LinkedList<>();
        Vector2D position = this.getPosition();
        for (int i = 0; i < count; i++)
        {
            if (content.isEmpty())
            {
                break;
            }
            Ship s = content.poll();
            s.setX(position.x + i + 1);
            s.setY(position.y + i + 1);
            deployed.add(s);
        }
        return deployed;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public double getProductionRate()
    {
        return productionRate;
    }

    public void setProductionRate(int productionRate)
    {
        this.productionRate = productionRate;
    }

    public boolean isProducingFleets()
    {
        return produceFleets;
    }

    public void setProduceFleets(boolean produceFleets)
    {
        this.produceFleets = produceFleets;
    }

    @Override
    public String getDataString()
    {
        String s = IO.translate("CLASS") + ": " + IO.translate("CARRIER") + "\n";
        s += IO.translate("HP") + ": " + getMaxHp() + "\n" + IO.translate("SPEED") + ": "
                + getMaxSpeed() + "\n" + IO.translate("ACCEL") + ": " + getMaxAcceleration() + "\n"
                + IO.translate("ROTSPEED") + ": " + getMaxRotation() + "\n";
        s += IO.translate("CAPACITY") + ": " + capacity + "\n";
        s += IO.translate("PRODRATE") + ": " + productionRate + "\n";
        List<Weapon> weapons = getWeapons();
        for (int i = 0; i < weapons.size(); i++)
        {
            s += IO.translate("TURRET") + " " + i + ": " + weapons.get(i) + "\n";
        }
        return s;
    }
}
