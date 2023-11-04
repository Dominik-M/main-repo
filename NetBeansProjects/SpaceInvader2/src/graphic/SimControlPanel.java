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
import actors.ShipFactory;
import ais.BaseAI;
import ais.WingmanAI;
import armory.GunFactory;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.SpaceInvader;
import main.SpaceInvader.Team;
import platform.utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 28.03.2016
 */
public class SimControlPanel extends JPanel implements ActionListener, ChangeListener
{

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

    public SimControlPanel()
    {
        init();
    }

    private void init()
    {
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
        fpsSlider.setValue((int) Settings.get("fps"));
        fpsSlider.setMinorTickSpacing(10);
        fpsSlider.addChangeListener(this);
        fpsSlider.setFocusable(false);
        kiCBox = new JComboBox<String>();
        kiCBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]
        {
            "Player",
            BaseAI.AI_DUMMYFIGHTER.toString(), BaseAI.AI_HUNTER.toString()
        }));
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
        this.add(addWingmanBtn);
        this.add(addFighterFleetBtn);
        this.add(addFleetBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(startstopBtn))
        {
            GamePanel.INSTANCE.setRunning(!GamePanel.INSTANCE.isRunning());
            if (!GamePanel.INSTANCE.isRunning())
            {
                startstopBtn.setText("Start");
            }
            else
            {
                startstopBtn.setText("Stop");
            }
        }
        else if (e.getSource().equals(stepBtn))
        {
            GamePanel.INSTANCE.act();
            GamePanel.INSTANCE.repaint();
        }
        else if (e.getSource().equals(fleetCommandBtn))
        {
            SpaceInvader.getInstance().toggleFleetCommand();
        }
        else if (e.getSource().equals(switchMShipBtn))
        {
            SpaceInvader.getInstance().switchMShip();
        }
        else if (e.getSource().equals(addFleetBtn))
        {
            addHunterFleet();
        }
        else if (e.getSource().equals(addFighterFleetBtn))
        {
            addFighterFleet();
        }
        else if (e.getSource().equals(addWingmanBtn))
        {
            addWingman();
        }
        else if (e.getSource().equals(kiCBox))
        {
            switch (kiCBox.getSelectedIndex())
            {
                case 1:
                    SpaceInvader.getInstance().getMShip().setAI(BaseAI.AI_DUMMYFIGHTER);
                    break;
                case 2:
                    SpaceInvader.getInstance().getMShip().setAI(BaseAI.AI_HUNTER);
                    break;
                default:
                    SpaceInvader.getInstance().getMShip().setAI(null);
                    break;
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent arg0)
    {
        Settings.set("fps", (fpsSlider.getValue()));
    }

    public static void addFighter()
    {
        int x = randomX();
        int y = randomY();
        Fighter fighter = ShipFactory.createWingman(x, y, ShipFactory.DEFAULT_HP_VERYLOW, SpaceInvader.getInstance()
                .getMShip(), GunFactory.getGun(), ShipFactory.IMAGENAME_FIGHTER);
        SpaceInvader.getInstance().addActor(fighter);
        SpaceInvader.getInstance().addToFleet((WingmanAI) fighter.getAI());
    }

    public static void addFighterFleet()
    {
        int x = randomX();
        int y = randomY();
        Ship leader = ShipFactory.createFighter(x, y, Team.EARTH, ShipFactory.DEFAULT_HP_LOW, GunFactory.getSalvoGun());
        leader.setAI(new ais.HunterAI());
        Ship[] wingmen = ShipFactory.createFleet(leader, ShipFactory.DEFAULT_FLEET_SIZE);
        SpaceInvader.getInstance().addActor(leader);
        for (Ship wingman : wingmen)
        {
            SpaceInvader.getInstance().addActor(wingman);
        }
    }

    public static void addWingman()
    {
        int x = randomX();
        int y = randomY();
        Ship wingman = ShipFactory.createWingman(x, y, ShipFactory.DEFAULT_HP_LOW, SpaceInvader.getInstance().getMShip(),
                GunFactory.getSalvoGun(), ShipFactory.IMAGENAME_SPACESHIP);
        SpaceInvader.getInstance().addActor(wingman);
        SpaceInvader.getInstance().addToFleet((WingmanAI) wingman.getAI());
    }

    public static void addHunterFleet()
    {
        int x = randomX();
        int y = randomY();
        Ship leader = ShipFactory.createFighter(x, y, Team.EARTH, ShipFactory.DEFAULT_HP_MED, ShipFactory.DEFAULT_MASS_LOW, ShipFactory.DEFAULT_SPEED_MED, ShipFactory.DEFAULT_POWER_MED, ShipFactory.DEFAULT_ROT_SPEED, GunFactory.getRapidGun(), ShipFactory.IMAGENAME_HUNTER_M);
        Ship[] wingmen = ShipFactory.createFleet(leader, ShipFactory.DEFAULT_FLEET_SIZE);
        SpaceInvader.getInstance().addActor(leader);
        for (Ship wingman : wingmen)
        {
            SpaceInvader.getInstance().addActor(wingman);
        }
    }

    public static void addCruiser()
    {
        int x = randomX();
        int y = randomY();
        SpaceInvader.getInstance().addActor(
                ShipFactory.createCruiser(x, y, SpaceInvader.Team.values()[SpaceInvader.getInstance()
                        .getMShip().getTeam()], GunFactory.getSalvoGun(), GunFactory.getSalvoGun(),
                        GunFactory.getRapidGun()));
    }

    public static void addCarrier()
    {
        int x = randomX();
        int y = randomY();
        Carrier carrier = ShipFactory.createCarrier(x, y,
                SpaceInvader.Team.get(SpaceInvader.getInstance().getMShip().getTeam()), ShipFactory.DEFAULT_HP_VERYHIGH, ShipFactory.SHIELD_VERYHIGH, 20, 10,
                GunFactory.getRapidGun(), GunFactory.get3BurstGun(), GunFactory.get5BurstGun(),
                GunFactory.get8BurstGun());
        carrier.setProduceFleets(true);
        SpaceInvader.getInstance().addActor(carrier);
    }

    public static int randomX()
    {
        if (SpaceInvader.getInstance().getMap() != null)
        {
            if (SpaceInvader.getInstance().getMap().LANDING_ZONE != null)
            {
                return (int) (SpaceInvader.getInstance().getMap().LANDING_ZONE.getCenterX() + (Math
                        .random() - 0.5) * SpaceInvader.getInstance().getMap().LANDING_ZONE.width / 2);
            }
            else
            {
                return (int) (Math.random() * SpaceInvader.getInstance().getMap().WIDTH);
            }
        }
        else
        {
            return 0;
        }
    }

    public static int randomY()
    {
        if (SpaceInvader.getInstance().getMap() != null)
        {
            if (SpaceInvader.getInstance().getMap().LANDING_ZONE != null)
            {
                return (int) (SpaceInvader.getInstance().getMap().LANDING_ZONE.getCenterY() + (Math
                        .random() - 0.5) * SpaceInvader.getInstance().getMap().LANDING_ZONE.height / 2);
            }
            else
            {
                return (int) (Math.random() * SpaceInvader.getInstance().getMap().HEIGHT);
            }
        }
        else
        {
            return 0;
        }
    }
}
