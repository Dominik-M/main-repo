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

import actors.Carrier;
import actors.Cruiser;
import actors.Fighter;
import actors.Ship;
import ais.AI;
import java.util.Arrays;
import java.util.LinkedList;
import utils.Constants;
import utils.Constants.Team;

/**
 * Collection of methods which create Instances of Ships, Weapons and so on.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 28.03.2016
 */
public class Factory {

    private static final Factory FACTORY = new Factory();
    private int producedWeapons = 0;
    private int producedShips = 0;

    private Factory() {
    }

    public static int getProducedWeapons() {
        return FACTORY.producedWeapons;
    }

    public static int getProducedShips() {
        return FACTORY.producedShips;
    }

    public static Fighter createAlienFighter(int x, int y, int level) {
        Gun gun = getGun();
        gun.setDamage(1);
        gun.setFirerate(level < 5 ? Constants.DEFAULT_FIRERATE_SLOW : (level < 15 ? Constants.DEFAULT_FIRERATE_MEDIUM : Constants.DEFAULT_FIRERATE_FAST));
        Fighter f = new Fighter(x, y, Team.ALIEN, level / 2 + 3, 200, 100, 180, gun,
                Constants.IMAGENAME_ALIEN);
        f.setAI(AI.AI_DUMMYFIGHTER);
        FACTORY.producedShips++;
        return f;
    }

    public static Fighter createAlienHunter(int x, int y, int level) {
        SalvoGun gun = getSalvoGun();
        gun.setDamage(level / 10 + 1);
        gun.setSalvos(level / 5 + 2);
        Fighter f = new Fighter(x, y, Team.ALIEN, level * 2 + 5, 200, 100, 180, gun,
                Constants.IMAGENAME_ALIEN_RED);
        f.setAI(AI.AI_HUNTER);
        FACTORY.producedShips++;
        return f;
    }

    public static Carrier createMothership(int x, int y, Team team, int hp, int fleetcount) {
        Gun[] guns = new Gun[fleetcount > 6 ? 6 : fleetcount];
        for (int i = 0; i < guns.length; i++) {
            guns[i] = getGun();
        }
        Carrier mothership = Factory.createCarrier(x, y, team, hp, 5 * fleetcount, 1, guns);
        LinkedList<Ship> fleet = new LinkedList<>();
        for (int i = 0; i < fleetcount; i++) {
            Ship leader = createAlienHunter(x, y, 5);
            fleet.add(leader);
            fleet.addAll(Arrays.asList(createFleet(leader, 4)));
        }
        mothership.garrison(fleet);
        mothership.setAI(new ais.CarrierAI());
        return mothership;
    }

    public static Fighter createWingman(int x, int y, Ship leader, Weapon wingmanweapon) {
        String img = Constants.IMAGENAME_SPACESHIP;
        if (leader.team == Team.ALIEN) {
            img = Constants.IMAGENAME_ALIEN;
        }
        Fighter s = new Fighter(x, y, leader.team, 10, 400, 2000, 180, wingmanweapon, img);
        s.setAI(new ais.WingmanAI(leader));
        FACTORY.producedShips++;
        return s;
    }

    public static Fighter createSmallWingman(int x, int y, Ship leader, Weapon wingmanweapon) {
        String img = Constants.IMAGENAME_FIGHTER;
        if (leader.team == Team.ALIEN) {
            img = Constants.IMAGENAME_ALIENFIGHTER;
        }
        Fighter s = new Fighter(x, y, leader.team, 6, 400, 2000, 180, wingmanweapon, img);
        s.setAI(new ais.WingmanAI(leader));
        FACTORY.producedShips++;
        return s;
    }

    public static Fighter createHunter(int x, int y, Team team, Weapon weapon) {
        Fighter hunter = new Fighter(x, y, Team.EARTH, 10, 250, 400, 180, weapon,
                Constants.IMAGENAME_SPACESHIP,
                Constants.IMAGENAME_SPACESHIPACC);
        hunter.setAI(new ais.HunterAI());
        FACTORY.producedShips++;
        return hunter;
    }

    public static Ship[] createFleet(Ship leader, int count) {
        Ship[] wingmen = new Ship[count];
        for (int i = 0; i < count; i++) {
            int seed = (int) (Math.random() * 30) + 1 + i;
            wingmen[i] = createWingman(leader.getXOnScreen() + seed, leader.getYOnScreen() + seed,
                    leader, getGun());
        }
        return wingmen;
    }

    public static Ship[] createFighterFleet(Ship leader, int count) {
        Ship[] wingmen = new Ship[count];
        for (int i = 0; i < count; i++) {
            int seed = (int) (Math.random() * 30) + 1 + i;
            wingmen[i] = createSmallWingman(leader.getXOnScreen() + seed, leader.getYOnScreen()
                    + seed, leader, getGun());
        }
        return wingmen;
    }

    public static Cruiser createCruiser(int x, int y, Team team, Weapon... weapons) {
        String img = Constants.IMAGENAME_CRUISER;
        if (team == Team.ALIEN) {
            img = Constants.IMAGENAME_ALIEN;
        }
        Cruiser ship = new Cruiser(x, y, team, 500, Constants.DEFAULT_SPEED_VERYLOW, 5, 90, img);
        for (int i = 0; i < weapons.length; i++) {
            ship.addWeapon(weapons[i], 0, i * 20 - 30,
                    Constants.IMAGENAME_TURRET);
        }
        ship.setAI(new ais.HunterAI());
        FACTORY.producedShips++;
        return ship;
    }

    public static Carrier createCarrier(int x, int y, Team team, int hp, int capacity, double rate,
            Weapon... weapons) {
        String img = Constants.IMAGENAME_CARRIER;
        if (team == Team.ALIEN) {
            img = Constants.IMAGENAME_ALIEN_MOTHERSHIP;
        }
        Carrier ship = new Carrier(x, y, team, hp, Constants.DEFAULT_SPEED_VERYLOW, 50, 50, capacity, rate, img);
        for (int i = 0; i < weapons.length; i++) {
            ship.addWeapon(weapons[i], -10 + (i % 2) * 20, (i / 2) * 25 - 40,
                    Constants.IMAGENAME_TURRET);
        }
        ship.setAI(new ais.HunterAI());
        FACTORY.producedShips++;
        return ship;
    }

    public static Gun getGun() {
        FACTORY.producedWeapons++;
        return new Gun(Constants.WEAPONNAMES[0], Constants.DEFAULT_DAMAGE_LOW,
                Constants.DEFAULT_PROJECTILESPEED_MEDIUM, Constants.DEFAULT_FIRERATE_MEDIUM,
                Constants.DEFAULT_RANGE_MEDIUM);
    }

    public static BurstGun get3BurstGun() {
        FACTORY.producedWeapons++;
        return new BurstGun(Constants.WEAPONNAMES[1], Constants.DEFAULT_DAMAGE_LOW,
                Constants.DEFAULT_PROJECTILESPEED_MEDIUM, Constants.DEFAULT_FIRERATE_MEDIUM,
                Constants.DEFAULT_RANGE_MEDIUM, 3, 30);
    }

    public static Gun getRifleGun() {
        FACTORY.producedWeapons++;
        return new Gun(Constants.WEAPONNAMES[2], Constants.DEFAULT_DAMAGE_EXTREME,
                Constants.DEFAULT_PROJECTILESPEED_FAST, Constants.DEFAULT_FIRERATE_SLOW,
                Constants.DEFAULT_RANGE_WIDE);
    }

    public static BurstGun get5BurstGun() {
        FACTORY.producedWeapons++;
        return new BurstGun(Constants.WEAPONNAMES[3], Constants.DEFAULT_DAMAGE_MEDIUM,
                Constants.DEFAULT_PROJECTILESPEED_MEDIUM, Constants.DEFAULT_FIRERATE_MEDIUM,
                Constants.DEFAULT_RANGE_MEDIUM, 5, 30);
    }

    public static SalvoGun getSalvoGun() {
        FACTORY.producedWeapons++;
        return new SalvoGun(Constants.WEAPONNAMES[4], Constants.DEFAULT_DAMAGE_LOW,
                Constants.DEFAULT_PROJECTILESPEED_MEDIUM, Constants.DEFAULT_FIRERATE_FAST * 4,
                Constants.DEFAULT_SALVO_COUNT, Constants.DEFAULT_HEATRATE);
    }

    public static Gun getRapidGun() {
        FACTORY.producedWeapons++;
        return new Gun(Constants.WEAPONNAMES[5], Constants.DEFAULT_DAMAGE_LOW,
                Constants.DEFAULT_PROJECTILESPEED_MEDIUM, Constants.DEFAULT_FIRERATE_FAST,
                Constants.DEFAULT_RANGE_MEDIUM);
    }

    public static BurstGun get8BurstGun() {
        FACTORY.producedWeapons++;
        return new BurstGun(Constants.WEAPONNAMES[6], Constants.DEFAULT_DAMAGE_HIGH,
                Constants.DEFAULT_PROJECTILESPEED_MEDIUM, Constants.DEFAULT_FIRERATE_MEDIUM,
                Constants.DEFAULT_RANGE_MEDIUM, 8, 30);
    }

    public static BurstGun getSchockwave() {
        FACTORY.producedWeapons++;
        return new BurstGun(Constants.WEAPONNAMES[7], Constants.DEFAULT_DAMAGE_LOW,
                Constants.DEFAULT_PROJECTILESPEED_FAST, Constants.DEFAULT_FIRERATE_SLOW,
                Constants.DEFAULT_RANGE_SHORT, 360, 360);
    }
}
