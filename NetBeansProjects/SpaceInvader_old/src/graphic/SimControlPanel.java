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
package graphic;

import actors.Carrier;
import actors.Fighter;
import actors.Ship;
import ais.AI;
import ais.WingmanAI;
import armory.Factory;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import utils.Constants;
import utils.Constants.Team;
import utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 28.03.2016
 */
public class SimControlPanel extends JPanel implements ActionListener, ChangeListener {

    private static final long serialVersionUID = -6885400479662214128L;
    private JButton startstopBtn;
    private JButton stepBtn;
    private JButton addWingmanBtn;
    private JButton addFighterFleetBtn;
    private JButton addFleetBtn;
    private JButton fleetCommandBtn;
    private JButton switchMShipBtn;
    private JComboBox<String> kiCBox;
    private JSlider fpsSlider;

    public SimControlPanel() {
        init();
    }

    private void init() {
        startstopBtn = new JButton("Start");
        startstopBtn.addActionListener(this);
        startstopBtn.setFocusable(false);
        stepBtn = new JButton("Step");
        stepBtn.addActionListener(this);
        stepBtn.setFocusable(false);
        fleetCommandBtn = new JButton("Toggle Fleet Command");
        fleetCommandBtn.addActionListener(this);
        fleetCommandBtn.setFocusable(false);
        switchMShipBtn = new JButton("Switch ship");
        switchMShipBtn.addActionListener(this);
        switchMShipBtn.setFocusable(false);
        addFighterFleetBtn = new JButton("Add Fighter fleet");
        addFighterFleetBtn.addActionListener(this);
        addFighterFleetBtn.setFocusable(false);
        addFleetBtn = new JButton("Add Hunter fleet");
        addFleetBtn.addActionListener(this);
        addFleetBtn.setFocusable(false);
        fpsSlider = new JSlider();
        fpsSlider.setPaintLabels(true);
        fpsSlider.setPaintTicks(true);
        fpsSlider.setMinimum(10);
        fpsSlider.setMaximum(100);
        fpsSlider.setMajorTickSpacing(20);
        fpsSlider.setValue(Settings.getSettings().fps);
        fpsSlider.setMinorTickSpacing(10);
        fpsSlider.addChangeListener(this);
        fpsSlider.setFocusable(false);
        kiCBox = new JComboBox<String>();
        kiCBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Player",
            AI.AI_DUMMYFIGHTER.toString(), AI.AI_HUNTER.toString()}));
        kiCBox.addActionListener(this);
        kiCBox.setFocusable(false);
        addWingmanBtn = new JButton("Add Wingman");
        addWingmanBtn.setFocusable(false);
        addWingmanBtn.addActionListener(this);
        this.setLayout(new FlowLayout());
        this.add(fleetCommandBtn);
        this.add(switchMShipBtn);
        this.add(kiCBox);
        this.add(stepBtn);
        this.add(startstopBtn);
        this.add(fpsSlider);
        if (Constants.DEBUG_ENABLE) {
            this.add(addWingmanBtn);
            this.add(addFighterFleetBtn);
            this.add(addFleetBtn);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(startstopBtn)) {
            GameGrid.getInstance().setPaused(!GameGrid.getInstance().isPaused());
            if (GameGrid.getInstance().isPaused()) {
                startstopBtn.setText("Start");
            } else {
                startstopBtn.setText("Stop");
            }
        } else if (e.getSource().equals(stepBtn)) {
            GameGrid.getInstance().doStep();
        } else if (e.getSource().equals(fleetCommandBtn)) {
            GameGrid.getInstance().toggleFleetCommand();
        } else if (e.getSource().equals(switchMShipBtn)) {
            GameGrid.getInstance().switchMShip();
        } else if (e.getSource().equals(addFleetBtn)) {
            addHunterFleet();
        } else if (e.getSource().equals(addFighterFleetBtn)) {
            addFighterFleet();
        } else if (e.getSource().equals(addWingmanBtn)) {
            addWingman();
        } else if (e.getSource().equals(kiCBox)) {
            switch (kiCBox.getSelectedIndex()) {
                case 1:
                    GameGrid.getInstance().getMShip().setAI(AI.AI_DUMMYFIGHTER);
                    break;
                case 2:
                    GameGrid.getInstance().getMShip().setAI(AI.AI_HUNTER);
                    break;
                default:
                    GameGrid.getInstance().getMShip().setAI(null);
                    break;
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        GameGrid.getInstance().setFps(fpsSlider.getValue());
    }

    public static void addFighter() {
        int x = randomX();
        int y = randomY();
        Fighter fighter = Factory.createSmallWingman(x, y, GameGrid.getInstance().getMShip(),
                Factory.getGun());
        GameGrid.getInstance().addActor(fighter);
        GameGrid.getInstance().addToFleet((WingmanAI) fighter.getAI());
    }

    public static void addFighterFleet() {
        int x = randomX();
        int y = randomY();
        Ship leader = new Fighter(x, y, Team.EARTH, 10, 250, 400, 180, Factory.getSalvoGun(),
                Constants.IMAGENAME_FIGHTER);
        leader.setAI(new ais.HunterAI());
        Ship[] wingmen = Factory.createFighterFleet(leader, Constants.DEFAULT_FLEET_SIZE);
        GameGrid.getInstance().addActor(leader);
        for (Ship wingman : wingmen) {
            GameGrid.getInstance().addActor(wingman);
        }
    }

    public static void addWingman() {
        int x = randomX();
        int y = randomY();
        Ship wingman = Factory.createWingman(x, y, GameGrid.getInstance().getMShip(),
                Factory.getSalvoGun());
        GameGrid.getInstance().addActor(wingman);
        GameGrid.getInstance().addToFleet((WingmanAI) wingman.getAI());
    }

    public static void addHunterFleet() {
        int x = randomX();
        int y = randomY();
        Ship leader = Factory.createHunter(x, y, GameGrid.getInstance().getMShip().team,
                Factory.getRapidGun());
        Ship[] wingmen = Factory.createFleet(leader, Constants.DEFAULT_FLEET_SIZE);
        GameGrid.getInstance().addActor(leader);
        for (Ship wingman : wingmen) {
            GameGrid.getInstance().addActor(wingman);
        }
    }

    public static void addCruiser() {
        int x = randomX();
        int y = randomY();
        GameGrid.getInstance().addActor(
                Factory.createCruiser(x, y, GameGrid.getInstance().getMShip().team, Factory.getSalvoGun(),
                        Factory.getSalvoGun(), Factory.getRapidGun()));
    }

    public static void addCarrier() {
        int x = randomX();
        int y = randomY();
        Carrier carrier = Factory.createCarrier(x, y, GameGrid.getInstance().getMShip().team, 2000,
                20, 10, Factory.getRapidGun(), Factory.get3BurstGun(), Factory.get5BurstGun(),
                Factory.get8BurstGun());
        carrier.setProduceFleets(true);
        GameGrid.getInstance().addActor(carrier);
    }

    public static int randomX() {
        if (GameGrid.getInstance().getMap() != null) {
            if (GameGrid.getInstance().getMap().LANDING_ZONE != null) {
                return (int) (GameGrid.getInstance().getMap().LANDING_ZONE.getCenterX() + (Math.random() - 0.5)
                        * GameGrid.getInstance().getMap().LANDING_ZONE.width / 2);
            } else {
                return (int) (Math.random() * GameGrid.getInstance().getMap().WIDTH);
            }
        } else {
            return 0;
        }
    }

    public static int randomY() {
        if (GameGrid.getInstance().getMap() != null) {
            if (GameGrid.getInstance().getMap().LANDING_ZONE != null) {
                return (int) (GameGrid.getInstance().getMap().LANDING_ZONE.getCenterY() + (Math.random() - 0.5)
                        * GameGrid.getInstance().getMap().LANDING_ZONE.height / 2);
            } else {
                return (int) (Math.random() * GameGrid.getInstance().getMap().HEIGHT);
            }
        } else {
            return 0;
        }
    }
}
