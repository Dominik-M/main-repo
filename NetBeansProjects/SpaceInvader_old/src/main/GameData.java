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
package main;

import actors.Actor;
import actors.Fighter;
import actors.Ship;
import ais.WingmanAI;
import armory.Factory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;
import utils.Constants;
import utils.Constants.Team;
import utils.IO;
import utils.Vector2D;

/**
 * Created 12.04.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class GameData implements java.io.Serializable {

    public enum Mode {

        ARCADE, ADVENTURE;
    }

    public CopyOnWriteArrayList<Map> maps;
    public Map currentMap;
    public CopyOnWriteArrayList<Actor> actors;
    public CopyOnWriteArrayList<WingmanAI> fleet;
    public Ship mShip;
    public int level, score;
    public Mode mode;

    public GameData() {
        maps = new CopyOnWriteArrayList<>();
        maps.add(new Map(Constants.MAPNAME_EARTH, new java.awt.Rectangle(1030, 540, 500, 480), new Vector2D(0, 0)));
        currentMap = maps.get(0);
        actors = new CopyOnWriteArrayList<>();
        fleet = new CopyOnWriteArrayList<>();
        Ship s = new Fighter(1200, 700, Team.EARTH, 20, 300, 400, 180, Factory.getGun(),
                Constants.IMAGENAME_SPACESHIP);
//        Carrier s = Factory.createCarrier(1200, 700, Constants.Team.EARTH, 20000, 20, 5,
//                Factory.getRifleGun(), Factory.getRifleGun(), Factory.getRapidGun(), Factory.getRapidGun(),
//                Factory.getSalvoGun(), Factory.getSalvoGun(), Factory.get5BurstGun(), Factory.get8BurstGun());
//        s.setProduceFleets(true);
//        s.setAI(null);
        // Carrier s = Factory.createCarrier(1200, 700, Team.EARTH, 10);
        // s.setProductionRate(200);
        mShip = s;
        level = 0;
        score = 0;
        if (Constants.DEBUG_ENABLE) {
            score += 20000;
        }
        mode = Mode.ARCADE;
    }

    public Map getMapAt(Vector2D position) {
        for (Map m : maps) {
            if (m.position.equals(position)) {
                return m;
            }
        }
        Map m = Map.getRandomMap(Constants.DEFAULT_MAP_WIDTH, Constants.DEFAULT_MAP_HEIGHT, position);
        maps.add(m);
        return m;
    }

    public boolean save(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            fos.close();
            oos.close();
            IO.println("saved " + file, IO.MessageType.DEBUG);
            return true;
        } catch (Exception ex) {
            IO.println("failed to save " + file, IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    public static GameData load(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameData g = (GameData) ois.readObject();
            fis.close();
            ois.close();
            IO.println("loaded " + file, IO.MessageType.DEBUG);
            return g;
        } catch (Exception ex) {
            IO.println("failed to load " + file, IO.MessageType.ERROR);
            IO.printException(ex);
            return null;
        }
    }
}
