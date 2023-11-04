/**
 * Copyright (C) 2017 Dominik Messerschmidt
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

import ais.BaseAI;
import armory.Gun;
import armory.GunFactory;
import armory.SalvoGun;
import armory.Weapon;
import java.util.Arrays;
import java.util.LinkedList;
import main.SpaceInvader;
import main.SpaceInvader.Team;
import platform.utils.IO;

/**
 * Collection of methods which create Instances of Ships and balancing settings
 * for Ships.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 27.01.2017
 */
public class ShipFactory
{

    /**
     * Balancing settings for Ships
     */
    public static final int DEFAULT_HP_VERYLOW = 5;
    public static final int DEFAULT_HP_LOW = 10;
    public static final int DEFAULT_HP_MED = 50;
    public static final int DEFAULT_HP_HIGH = 500;
    public static final int DEFAULT_HP_VERYHIGH = 2500;
    public static final int DEFAULT_HP_EXTREME = 20000;
    public static final int SHIELD_VERYLOW = 5;
    public static final int SHIELD_LOW = 10;
    public static final int SHIELD_MED = 50;
    public static final int SHIELD_HIGH = 500;
    public static final int SHIELD_VERYHIGH = 2500;
    public static final int SHIELD_EXTREME = 20000;
    public static final int SHIELD_REGEN_VERYLOW = 1;
    public static final int SHIELD_REGEN_LOW = 2;
    public static final int SHIELD_REGEN_MED = 5;
    public static final int SHIELD_REGEN_HIGH = 20;
    public static final int SHIELD_REGEN_VERYHIGH = 100;
    public static final int SHIELD_REGEN_EXTREME = 500;
    public static final int DEFAULT_FLEET_SIZE = 4;
    public static final int DEFAULT_MASS_LOW = 50;
    public static final int DEFAULT_MASS_MED = 100;
    public static final int DEFAULT_MASS_HIGH = 500;
    public static final int DEFAULT_MASS_VERYHIGH = 2500;
    public static final int DEFAULT_POWER_LOW = 5000;
    public static final int DEFAULT_POWER_MED = 20000;
    public static final int DEFAULT_POWER_HIGH = 100000;
    public static final int DEFAULT_SPEED_VERYLOW = 20;
    public static final int DEFAULT_SPEED_LOW = 50;
    public static final int DEFAULT_SPEED_MED = 300;
    public static final int DEFAULT_SPEED_HIGH = 600;
    public static final int DEFAULT_ROT_SPEED = 180;

    /**
     * Ship images
     */
    public static final String IMAGENAME_SPACESHIP = "spaceship.gif";
    public static final String IMAGENAME_HUNTER_M = "hunter_M.png";
    public static final String IMAGENAME_CRUISER = "cruiser.gif";
    public static final String IMAGENAME_CARRIER = "carrier.gif";
    public static final String IMAGENAME_SPACESHIPACC = "spaceshipAcc.gif";
    public static final String IMAGENAME_FIGHTER = "fighter.gif";
    public static final String IMAGENAME_CHROM_S = "chromium_S.png";
    public static final String IMAGENAME_TURRET = "turret.gif";
    public static final String IMAGENAME_ALIEN = "alien.gif";
    public static final String IMAGENAME_ALIENFIGHTER = "alienfighter.gif";
    public static final String IMAGENAME_ALIEN_RED = "alienRed.gif";
    public static final String IMAGENAME_ALIEN_MOTHERSHIP = "alienMothership.gif";

    public static final String IMAGENAME_EXPLOSION = "explosion1.gif";

    /**
     * Prototypes
     */
    public static final Fighter fighterPrototyp = new Fighter(0, 0, SpaceInvader.Team.PASSIV, DEFAULT_HP_VERYLOW,
            DEFAULT_SPEED_MED, DEFAULT_MASS_LOW, DEFAULT_POWER_MED, DEFAULT_ROT_SPEED, GunFactory.getGun(), SHIELD_VERYLOW, SHIELD_REGEN_VERYLOW, IMAGENAME_FIGHTER);

    public static final Fighter wingmanPrototyp = new Fighter(0, 0, SpaceInvader.Team.PASSIV, DEFAULT_HP_LOW,
            DEFAULT_SPEED_MED, DEFAULT_MASS_LOW, DEFAULT_POWER_MED, DEFAULT_ROT_SPEED, GunFactory.getGun(), SHIELD_LOW, SHIELD_REGEN_VERYLOW, IMAGENAME_SPACESHIP);

    public static final Fighter fleetleaderPrototyp = new Fighter(0, 0, SpaceInvader.Team.PASSIV, DEFAULT_HP_MED,
            DEFAULT_SPEED_MED, DEFAULT_MASS_LOW, DEFAULT_POWER_MED, DEFAULT_ROT_SPEED, GunFactory.getGun(), SHIELD_LOW, SHIELD_REGEN_LOW, IMAGENAME_CHROM_S);

    public static final Fighter hunterPrototyp = new Fighter(0, 0, SpaceInvader.Team.PASSIV, DEFAULT_HP_MED,
            DEFAULT_SPEED_MED, DEFAULT_MASS_LOW, DEFAULT_POWER_MED, DEFAULT_ROT_SPEED, GunFactory.getGun(), SHIELD_MED, SHIELD_REGEN_LOW, IMAGENAME_HUNTER_M);

    public static final Cruiser cruiserPrototyp = createCruiser(0, 0, SpaceInvader.Team.PASSIV,
            GunFactory.getSalvoGun(), GunFactory.getSalvoGun(), GunFactory.getRapidGun());

    public static final Carrier carrierPrototyp = createCarrier(0, 0, SpaceInvader.Team.PASSIV,
            2000, 20, 10, GunFactory.getRapidGun(), GunFactory.get3BurstGun(), GunFactory.get5BurstGun(),
            GunFactory.get8BurstGun());

    /**
     * Prices
     */
    public static final int HP_UPGRADE_PRICE = 20;
    public static final int PRIZE_FIGHTER = 50;
    public static final int PRIZE_WINGMAN = 200;
    public static final int PRIZE_FIGHTER_SQUAD = (DEFAULT_FLEET_SIZE + 1) * PRIZE_FIGHTER;
    public static final int PRIZE_HUNTER_SQUAD = (DEFAULT_FLEET_SIZE + 1) * PRIZE_WINGMAN;
    public static final int PRIZE_CRUISER = 3000;
    public static final int PRIZE_CARRIER = 10000;

    public static Ship createStartShip()
    {
        Fighter f = new Fighter(1200, 700, Team.EARTH,
                DEFAULT_HP_MED,
                DEFAULT_SPEED_MED,
                DEFAULT_MASS_MED,
                DEFAULT_POWER_MED,
                DEFAULT_ROT_SPEED,
                GunFactory.getRapidGun(),
                SHIELD_MED,
                SHIELD_REGEN_MED,
                IMAGENAME_HUNTER_M);
        return f;
        /*
        Carrier c = createCarrier(1200, 700, Team.EARTH, DEFAULT_HP_EXTREME, 10, 100, GunFactory.getSalvoGun(), GunFactory.getSalvoGun(), GunFactory.getSalvoGun(), GunFactory.getSalvoGun());
        c.setProduceFleets(true);
        c.setAI(null);
        return c;
         */
    }

    public static Fighter createAlienFighter(int x, int y, int level)
    {
        Gun gun = GunFactory.getGun();
        gun.setDamage(1);
        gun.setFirerate(level < 5 ? GunFactory.DEFAULT_FIRERATE_SLOW
                : (level < 15 ? GunFactory.DEFAULT_FIRERATE_MEDIUM : GunFactory.DEFAULT_FIRERATE_FAST));
        Fighter f = new Fighter(x, y, Team.ALIEN, level / 2 + 3, 200, 50, 2000, 180, gun,
                SHIELD_LOW + level, SHIELD_REGEN_LOW, IMAGENAME_ALIEN);
        f.setAI(BaseAI.AI_DUMMYFIGHTER);
        return f;
    }

    public static Fighter createAlienHunter(int x, int y, int level)
    {
        SalvoGun gun = GunFactory.getSalvoGun();
        gun.setDamage(level / 10 + 1);
        gun.setSalvos(level / 5 + 2);
        Fighter f = new Fighter(x, y, Team.ALIEN, level * 2 + 5, 200, 50, 2000, 180, gun,
                SHIELD_MED, SHIELD_REGEN_MED, IMAGENAME_ALIEN_RED);
        f.setAI(BaseAI.AI_HUNTER);
        return f;
    }

    public static Carrier createMothership(int x, int y, Team team, int hp, int fleetcount)
    {
        Gun[] guns = new Gun[fleetcount > 6 ? 6 : fleetcount];
        for (int i = 0; i < guns.length; i++)
        {
            guns[i] = GunFactory.getGun();
        }
        Carrier mothership = createCarrier(x, y, team, hp, 5 * fleetcount, 1, guns);
        LinkedList<Ship> fleet = new LinkedList<>();
        for (int i = 0; i < fleetcount; i++)
        {
            Ship leader = createAlienHunter(x, y, 5);
            fleet.add(leader);
            fleet.addAll(Arrays.asList(createFleet(leader, 4)));
        }
        mothership.garrison(fleet);
        mothership.setAI(new ais.CarrierAI());
        return mothership;
    }

    /**
     * Creates an instance of Fighter with a WingmanAI which aligns to the given
     * leader Ship. The created Ship will be in the same Team as the leader.
     *
     * @param x X coordinate of the created Ship.
     * @param y Y coordinate of the created Ship.
     * @param hp Hitpoints of the created Ship.
     * @param leader Leader of the WingmanAI.
     * @param wingmanweapon Weapon of the created
     * @param img
     * @return
     */
    public static Fighter createWingman(int x, int y, int hp, Ship leader, Weapon wingmanweapon,
            String img)
    {
        Fighter s = new Fighter(x, y, Team.get(leader.getTeam()), hp, DEFAULT_SPEED_MED, DEFAULT_MASS_LOW,
                DEFAULT_POWER_HIGH, DEFAULT_ROT_SPEED, wingmanweapon, SHIELD_MED, SHIELD_REGEN_LOW, img);
        s.setAI(new ais.WingmanAI(leader));
        IO.println("ShipFactory.createWingman()", IO.MessageType.DEBUG);
        return s;
    }

    public static Fighter createFighter(int x, int y, Team team, int hp, int hullmass, int speed, int accel,
            int rot, Weapon weapon, String imagename)
    {
        Fighter hunter = new Fighter(x, y, Team.EARTH, hp, speed, hullmass, accel, rot, weapon,
                SHIELD_LOW, SHIELD_REGEN_LOW, imagename);
        return hunter;
    }

    public static Fighter createFighter(int x, int y, Team team, int hp, Gun gun)
    {
        return createFighter(x, y, team, hp, DEFAULT_MASS_LOW, DEFAULT_SPEED_MED, DEFAULT_POWER_MED, DEFAULT_ROT_SPEED, gun, IMAGENAME_SPACESHIP);
    }

    public static Ship[] createFleet(Ship leader, int count, int hp,
            Weapon wingmanweapon, String imagename)
    {
        Ship[] wingmen = new Ship[count];
        for (int i = 0; i < count; i++)
        {
            int seed = (int) (Math.random() * 30) + 1 + i;
            wingmen[i] = createWingman(leader.getXOnScreen() + seed, leader.getYOnScreen() + seed, hp,
                    leader, wingmanweapon, imagename);
        }
        return wingmen;
    }

    public static Ship[] createFleet(Ship leader, int count)
    {
        Ship[] wingmen = new Ship[count];
        for (int i = 0; i < count; i++)
        {
            int seed = (int) (Math.random() * 30) + 1 + i;
            wingmen[i] = createWingman(leader.getXOnScreen() + seed, leader.getYOnScreen()
                    + seed, DEFAULT_HP_LOW, leader, GunFactory.getGun(), IMAGENAME_SPACESHIP);
        }
        return wingmen;
    }

    public static Cruiser createCruiser(int x, int y, Team team, Weapon... weapons)
    {
        String img = IMAGENAME_CRUISER;
        if (team == Team.ALIEN)
        {
            img = IMAGENAME_ALIEN;
        }
        Cruiser ship = new Cruiser(x, y, team, DEFAULT_HP_HIGH, DEFAULT_SPEED_LOW,
                DEFAULT_MASS_HIGH, DEFAULT_POWER_MED, DEFAULT_ROT_SPEED / 2, SHIELD_HIGH, SHIELD_REGEN_MED, img);
        for (int i = 0; i < weapons.length; i++)
        {
            ship.addWeapon(weapons[i], 0, i * 20 - 30, IMAGENAME_TURRET);
        }
        ship.setAI(new ais.HunterAI());
        return ship;
    }

    public static Carrier createCarrier(int x, int y, Team team, int hp, int capacity, double rate,
            Weapon... weapons)
    {
        String img = IMAGENAME_CARRIER;
        if (team == Team.ALIEN)
        {
            img = IMAGENAME_ALIEN_MOTHERSHIP;
        }
        Carrier ship = new Carrier(x, y, team, hp, DEFAULT_SPEED_VERYLOW, DEFAULT_MASS_VERYHIGH, DEFAULT_POWER_HIGH, DEFAULT_ROT_SPEED / 4, capacity, rate,
                SHIELD_EXTREME, SHIELD_REGEN_HIGH, img);
        for (int i = 0; i < weapons.length; i++)
        {
            ship.addWeapon(weapons[i], -10 + (i % 2) * 20, (i / 2) * 25 - 40, IMAGENAME_TURRET);
        }
        ship.setAI(new ais.HunterAI());
        return ship;
    }
}
