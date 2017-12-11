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

import armory.Factory;
import armory.Weapon;
import graphic.GameGrid;
import java.util.LinkedList;
import java.util.List;
import utils.Constants;
import utils.Constants.Team;
import utils.Text;
import utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class Carrier extends Cruiser {

    private final LinkedList<Ship> content = new LinkedList<>();
    private int capacity;
    private double productionRate;
    private int pCounter, fCounter;
    private boolean produceFleets;

    public Carrier(int x, int y, Team team, int maxHp, double maxSpeed, double maxAccel,
            double maxRot, int capacity, double productionRate, String... spritenames) {
        super(x, y, team, maxHp, maxSpeed, maxAccel, maxRot, spritenames);
        this.capacity = capacity;
        this.productionRate = productionRate;
    }

    @Override
    public void act() {
        super.act();
        pCounter -= Constants.DELTA_T;
        if (pCounter <= 0) {
            if (produceFleets) {
                fCounter++;
                if (fCounter > Constants.DEFAULT_FLEET_SIZE) {
                    Vector2D position = getPosition();
                    Ship leader = new Fighter((int) position.x, (int) position.y, team, 10, 250,
                            400, 180, Factory.getGun(), Constants.IMAGENAME_FIGHTER);
                    leader.setAI(new ais.HunterAI());
                    Ship[] wingmen = Factory.createFighterFleet(leader,
                            Constants.DEFAULT_FLEET_SIZE);
                    GameGrid.getInstance().addActor(leader);
                    for (Ship wingman : wingmen) {
                        GameGrid.getInstance().addActor(wingman);
                    }
                    fCounter = 0;
                }
            } else {
                Vector2D position = getPosition();
                GameGrid.getInstance().addActor(
                        Factory.createSmallWingman((int) position.x, (int) position.y, this,
                                Factory.getGun()));
            }
            pCounter = (int) (Constants.DELTA_T * 1000 / productionRate);
        }
        if (isInvalid()) {
            for (Ship s : deployAll()) {
                GameGrid.getInstance().addActor(s);
            }
        }
    }

    public boolean garrison(List<Ship> ships) {
        if (content.size() + ships.size() <= capacity) {
            content.addAll(ships);
            return true;
        }
        return false;
    }

    public boolean garrison(Ship... ships) {
        LinkedList<Ship> shiplist = new LinkedList<>();
        for (int i = 0; i < ships.length; i++) {
            shiplist.add(ships[i]);
        }
        return garrison(shiplist);
    }

    public int getContentSize() {
        return content.size();
    }

    public List<Ship> deployAll() {
        LinkedList<Ship> deployed = new LinkedList<>();
        Vector2D position = this.getPosition();
        for (int i = 0; i < content.size(); i++) {
            Ship s = content.get(i);
            s.setX(position.x + i + 1);
            s.setY(position.y + i + 1);
        }
        content.removeAll(deployed);
        return deployed;
    }

    public List<Ship> deployFirst(int count) {
        LinkedList<Ship> deployed = new LinkedList<>();
        Vector2D position = this.getPosition();
        for (int i = 0; i < count; i++) {
            if (content.isEmpty()) {
                break;
            }
            Ship s = content.poll();
            s.setX(position.x + i + 1);
            s.setY(position.y + i + 1);
            deployed.add(s);
        }
        return deployed;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getProductionRate() {
        return productionRate;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    public boolean isProducingFleets() {
        return produceFleets;
    }

    public void setProduceFleets(boolean produceFleets) {
        this.produceFleets = produceFleets;
    }

    @Override
    public String getDataString() {
        String s = Text.CLASS + ": " + Text.CARRIER + "\n";
        s += Text.HP + ": " + getMaxHp() + "\n" + Text.SPEED + ": " + getMaxSpeed() + "\n" + Text.ACCEL + ": "
                + getMaxAcceleration() + "\n" + Text.ROTSPEED + ": " + getMaxRotation() + "\n";
        s += Text.CAPACITY + ": " + capacity + "\n";
        s += Text.PRODRATE + ": " + productionRate + "\n";
        List<Weapon> weapons = getWeapons();
        for (int i = 0; i < weapons.size(); i++) {
            s += Text.TURRET + " " + i + ": " + weapons.get(i) + "\n";
        }
        return s;
    }
}
