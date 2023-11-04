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
package armory;

import platform.utils.IO;

/**
 * Collection of methods which create Instances of Weapons and balancing
 * settings for weapons.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 27.01.2017
 */
public class GunFactory
{

    /**
     * Balancing settings for Guns
     */
    public static final int DEFAULT_PROJECTILESPEED_SLOW = 400;
    public static final int DEFAULT_PROJECTILESPEED_MEDIUM = 800;
    public static final int DEFAULT_PROJECTILESPEED_FAST = 2000;
    public static final int DEFAULT_FIRERATE_SLOW = 10;
    public static final int DEFAULT_FIRERATE_MEDIUM = 40;
    public static final int DEFAULT_FIRERATE_FAST = 250;
    public static final int DEFAULT_FIRERATE_VERYFAST = 500;
    public static final int DEFAULT_DAMAGE_LOW = 1;
    public static final int DEFAULT_DAMAGE_MEDIUM = 2;
    public static final int DEFAULT_DAMAGE_HIGH = 4;
    public static final int DEFAULT_DAMAGE_EXTREME = 10;
    public static final int DEFAULT_RANGE_SHORT = 200000;
    public static final int DEFAULT_RANGE_MEDIUM = 600000;
    public static final int DEFAULT_RANGE_FAR = 1200000;
    public static final int DEFAULT_SALVO_COUNT = 6;
    public static final int SPREAD_NONE = 0;
    public static final int SPREAD_SMALL = 10;
    public static final int SPREAD_MEDIUM = 20;
    public static final int SPREAD_WIDE = 40;
    public static final double DEFAULT_HEATRATE = 7;

    public enum GunOffer
    {

        GUN("Gun", 0),
        BURSTGUN3("3Burst Gun", 50),
        SNIPERGUN("Sniper Gun", 90),
        BURSTGUN5("5Burst Gun", 300),
        SALVOGUN("Salvo Gun", 500),
        RAPIDGUN("Rapid Gun", 1200),
        BURSTGUN8("8Burst Gun", 3000),
        MASSDRIVER("Massentreiber", 4500),
        SHOCKWAVE("Shockwave", 8000);

        public final String name;
        public final int price;

        GunOffer(String name, int price)
        {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString()
        {
            return name;
        }

    }

    public static final GunOffer[] GUN_STORE = GunOffer.values();

    /**
     * Weapon and projectile images
     */
    public static final String IMAGENAME_BOMB = "bomb.gif";
    public static final String IMAGENAME_LASER_R = "laser_red.png";
    public static final String IMAGENAME_LASER_G = "laser_green.png";
    public static final String IMAGENAME_LASER_B = "laser_blue.png";

    public static int getMaxCatalogID()
    {
        return GUN_STORE.length;
    }

    public static Gun getGunFromCatalog(GunOffer offer)
    {
        Gun gun = null;
        switch (offer)
        {
            case GUN:
                gun = getGun();
                break;
            case BURSTGUN3:
                gun = get3BurstGun();
                break;
            case SNIPERGUN:
                gun = getSniperGun();
                break;
            case BURSTGUN5:
                gun = get5BurstGun();
                break;
            case SALVOGUN:
                gun = getSalvoGun();
                break;
            case RAPIDGUN:
                gun = getRapidGun();
                break;
            case BURSTGUN8:
                gun = get8BurstGun();
                break;
            case MASSDRIVER:
                gun = getMassDriver();
                break;
            case SHOCKWAVE:
                gun = getShockwave();
                break;
            default:
                IO.println("GunFactory.getGunFromCatalog(): Item " + offer + " not available",
                        IO.MessageType.ERROR);
                break;
        }
        return gun;
    }

    public static Gun getGun()
    {
        Gun gun = new Gun(GUN_STORE[0].toString(), DEFAULT_DAMAGE_LOW,
                DEFAULT_PROJECTILESPEED_MEDIUM, DEFAULT_FIRERATE_MEDIUM, DEFAULT_RANGE_MEDIUM, SPREAD_SMALL);
        IO.println("GunFactory.getGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static BurstGun get3BurstGun()
    {
        BurstGun gun = new BurstGun(GUN_STORE[1].toString(), DEFAULT_DAMAGE_LOW,
                DEFAULT_PROJECTILESPEED_MEDIUM, DEFAULT_FIRERATE_MEDIUM, DEFAULT_RANGE_MEDIUM, 3, 30);
        IO.println("GunFactory.get3BurstGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static Gun getSniperGun()
    {
        Gun gun = new Gun(GUN_STORE[2].toString(), DEFAULT_DAMAGE_EXTREME,
                DEFAULT_PROJECTILESPEED_FAST, DEFAULT_FIRERATE_SLOW, DEFAULT_RANGE_FAR, SPREAD_NONE);
        IO.println("GunFactory.getSniperGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static BurstGun get5BurstGun()
    {
        BurstGun gun = new BurstGun(GUN_STORE[3].toString(), DEFAULT_DAMAGE_MEDIUM,
                DEFAULT_PROJECTILESPEED_MEDIUM, DEFAULT_FIRERATE_MEDIUM, DEFAULT_RANGE_MEDIUM, 5, 30);
        IO.println("GunFactory.get5BurstGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static SalvoGun getSalvoGun()
    {
        SalvoGun gun = new SalvoGun(GUN_STORE[4].toString(), DEFAULT_DAMAGE_LOW,
                DEFAULT_PROJECTILESPEED_MEDIUM, DEFAULT_FIRERATE_FAST * 4, DEFAULT_SALVO_COUNT,
                DEFAULT_HEATRATE, SPREAD_MEDIUM);
        IO.println("GunFactory.getSalvoGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static Gun getRapidGun()
    {
        Gun gun = new Gun(GUN_STORE[5].toString(), DEFAULT_DAMAGE_LOW,
                DEFAULT_PROJECTILESPEED_MEDIUM, DEFAULT_FIRERATE_VERYFAST, DEFAULT_RANGE_MEDIUM,
                SPREAD_SMALL);
        IO.println("GunFactory.getRapidGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static BurstGun get8BurstGun()
    {
        BurstGun gun = new BurstGun(GUN_STORE[6].toString(), DEFAULT_DAMAGE_HIGH,
                DEFAULT_PROJECTILESPEED_MEDIUM, DEFAULT_FIRERATE_MEDIUM, DEFAULT_RANGE_MEDIUM, 8, 30);
        IO.println("GunFactory.get8BurstGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static SalvoGun getMassDriver()
    {
        SalvoGun gun = new SalvoGun(GUN_STORE[4].toString(), DEFAULT_DAMAGE_HIGH,
                DEFAULT_PROJECTILESPEED_FAST, DEFAULT_FIRERATE_FAST * 8, DEFAULT_SALVO_COUNT * 2,
                DEFAULT_HEATRATE * 2, SPREAD_SMALL);
        IO.println("GunFactory.get8BurstGun(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }

    public static BurstGun getShockwave()
    {
        BurstGun gun = new BurstGun(GUN_STORE[8].toString(), DEFAULT_DAMAGE_LOW,
                DEFAULT_PROJECTILESPEED_FAST, DEFAULT_FIRERATE_SLOW, DEFAULT_RANGE_SHORT, 360, 360);
        IO.println("GunFactory.getShockwave(): Produced " + gun, IO.MessageType.DEBUG);
        return gun;
    }
}
