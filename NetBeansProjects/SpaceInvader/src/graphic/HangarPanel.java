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
import actors.Cruiser;
import actors.Fighter;
import actors.Ship;
import armory.Factory;
import static armory.Factory.get3BurstGun;
import static armory.Factory.get5BurstGun;
import static armory.Factory.get8BurstGun;
import static armory.Factory.getGun;
import static armory.Factory.getRapidGun;
import static armory.Factory.getSalvoGun;
import armory.Weapon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import utils.Constants;
import utils.IO;
import utils.Settings;
import utils.Text;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class HangarPanel extends MainPanel implements ActionListener, ItemListener {

    public static final Fighter fighterPrototyp = new Fighter(0, 0, Constants.Team.PASSIV, 6, 400, 2000, 180, getGun(), Constants.IMAGENAME_FIGHTER);
    public static final Fighter wingmanPrototyp = new Fighter(0, 0, Constants.Team.PASSIV, 10, 400, 2000, 180, getSalvoGun(), Constants.IMAGENAME_SPACESHIP);
    public static final Fighter fleetleaderPrototyp = new Fighter(0, 0, Constants.Team.PASSIV, 10, 250, 400, 180, Factory.getSalvoGun(), Constants.IMAGENAME_FIGHTER);
    public static final Fighter hunterPrototyp = new Fighter(0, 0, Constants.Team.PASSIV, 10, 250, 400, 180, getRapidGun(), Constants.IMAGENAME_SPACESHIP);
    public static final Cruiser cruiserPrototyp = Factory.createCruiser(0, 0, Constants.Team.PASSIV, getSalvoGun(), getSalvoGun(), getRapidGun());
    public static final Carrier carrierPrototyp = Factory.createCarrier(0, 0, Constants.Team.PASSIV, 2000, 20, 10, getRapidGun(), get3BurstGun(), get5BurstGun(), get8BurstGun());

    private static final long serialVersionUID = -3607005615642233606L;
    private static final boolean[] bought = new boolean[]{true, false, false, false, false,
        false, false, false};
    private static int assignedWeapon = 0;
    private JTabbedPane contentPane;
    private JLabel scoreLabel;
    private JButton liftoffBtn;
    private JPanel shipdataGrid;
    private JPanel fleetdataGrid;
    private JLabel hpLabel;
    private JButton hpBtn;
    private JLabel classLabel;
    private JButton shipBtn;
    private JLabel speedLabel;
    private JButton speedBtn;
    private JLabel rotLabel;
    private JButton rotBtn;
    private JLabel accelLabel;
    private JButton accelBtn;
    private JComboBox<String> weaponCBox;
    private JButton weaponBtn;
    private JLabel[] weaponLabels;
    private JButton[] weaponBtns;
    private JLabel capacityLabel;
    private JButton capacityBtn;
    private JLabel prodRateLabel;
    private JButton prodRateBtn;
    private JLabel prodFleetsLabel;
    private JButton prodFleetsBtn;
    private JButton addTurretBtn;
    private JButton addSmallWingmanBtn;
    private JSpinner smallWingmanCount;
    private JButton addWingmanBtn;
    private JSpinner wingmanCount;
    private JButton addFighterFleetBtn;
    private JSpinner fighterFleetCount;
    private JButton addHunterFleetBtn;
    private JSpinner hunterCount;
    private JButton addCruiserBtn;
    private JSpinner cruiserCount;
    private JButton addCarrierBtn;
    private JSpinner carrierCount;

    private final Ship ship;
    private int propertyCount;

    public HangarPanel(Ship mShip) {
        ship = mShip;
        init();
    }

    private void init() {
        if (Fighter.class.isInstance(ship)) {
            propertyCount = 6;
        } else if (Cruiser.class.isInstance(ship)) {
            if (Carrier.class.isInstance(ship)) {
                propertyCount = 9 + ((Carrier) ship).getWeapons().size();
            } else {
                propertyCount = 6 + ((Cruiser) ship).getWeapons().size();
            }
        } else {
            propertyCount = 5;
        }
        liftoffBtn = new JButton(Text.LIFTOFF.toString());
        shipdataGrid = new JPanel();
        scoreLabel = new JLabel(Text.SCORE + ": " + GameGrid.getInstance().getScore() + " "
                + Text.CURRENCY_PLURAL);
        scoreLabel.setFont(Settings.getSettings().font);
        hpLabel = new JLabel();
        hpBtn = new JButton("+10 " + Text.HP);
        hpBtn.addActionListener(this);
        classLabel = new JLabel();
        shipBtn = new JButton(Text.BUYSHIP.toString());
        shipBtn.setEnabled(false);
        shipBtn.addActionListener(this);
        speedLabel = new JLabel();
        speedBtn = new JButton("+" + Text.SPEED);
        speedBtn.setEnabled(false);
        speedBtn.addActionListener(this);
        rotLabel = new JLabel();
        rotBtn = new JButton("+" + Text.ROTSPEED);
        rotBtn.setEnabled(false);
        rotBtn.addActionListener(this);
        accelLabel = new JLabel();
        accelBtn = new JButton("+" + Text.ACCEL);
        accelBtn.setEnabled(false);
        accelBtn.addActionListener(this);
        weaponBtn = new JButton();
        weaponBtn.addActionListener(this);
        addSmallWingmanBtn = new JButton(Text.BUY.toString());
        addSmallWingmanBtn.setToolTipText(" " + Constants.PRIZE_FIGHTER + " "
                + Text.CURRENCY_PLURAL);
        // addSmallWingmanBtn.setEnabled(false);
        addSmallWingmanBtn.addActionListener(this);
        smallWingmanCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addWingmanBtn = new JButton(Text.BUY.toString());
        addWingmanBtn.setToolTipText(" " + Constants.PRIZE_WINGMAN + " " + Text.CURRENCY_PLURAL);
        // addWingmanBtn.setEnabled(false);
        addWingmanBtn.addActionListener(this);
        wingmanCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addFighterFleetBtn = new JButton(Text.BUY.toString());
        addFighterFleetBtn.setToolTipText(" " + Constants.PRIZE_FIGHTER_SQUAD + " "
                + Text.CURRENCY_PLURAL);
        // addFighterFleetBtn.setEnabled(false);
        addFighterFleetBtn.addActionListener(this);
        fighterFleetCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addHunterFleetBtn = new JButton(Text.BUY.toString());
        addHunterFleetBtn.setToolTipText(" " + Constants.PRIZE_HUNTER_SQUAD + " "
                + Text.CURRENCY_PLURAL);
        // addHunterFleetBtn.setEnabled(false);
        addHunterFleetBtn.addActionListener(this);
        hunterCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addCruiserBtn = new JButton(Text.BUY.toString());
        addCruiserBtn.setToolTipText(" " + Constants.PRIZE_CRUISER + " " + Text.CURRENCY_PLURAL);
        // addCruiserBtn.setEnabled(false);
        addCruiserBtn.addActionListener(this);
        cruiserCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addCarrierBtn = new JButton(Text.BUY.toString());
        addCarrierBtn.setToolTipText(" " + Constants.PRIZE_CARRIER + " " + Text.CURRENCY_PLURAL);
        // addCarrierBtn.setEnabled(false);
        addCarrierBtn.addActionListener(this);
        carrierCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        liftoffBtn.setFont(new java.awt.Font("Consolas", 1, 24));
        liftoffBtn.setBackground(Color.GREEN);
        liftoffBtn.addActionListener(this);
        contentPane = new JTabbedPane(2);
        contentPane.add(createShipTab());
        contentPane.setTitleAt(0, Text.SHIP.toString());
        contentPane.add(getFleetTab());
        contentPane.setTitleAt(1, Text.FLEET.toString());
        this.setLayout(new BorderLayout());
        this.add(contentPane, BorderLayout.CENTER);
        this.add(liftoffBtn, BorderLayout.SOUTH);
        this.add(scoreLabel, BorderLayout.NORTH);
        paintLabels();
    }

    private JPanel createShipTab() {
        JPanel shipTab = new JPanel();
        shipdataGrid.setLayout(new GridLayout(propertyCount, 3));
        shipdataGrid.add(new JLabel(Text.CLASS + ":"));
        shipdataGrid.add(classLabel);
        shipdataGrid.add(shipBtn);
        shipdataGrid.add(new JLabel(Text.HITPOINTS + ":"));
        shipdataGrid.add(hpLabel);
        shipdataGrid.add(hpBtn);
        shipdataGrid.add(new JLabel(Text.SPEED + ":"));
        shipdataGrid.add(speedLabel);
        shipdataGrid.add(speedBtn);
        shipdataGrid.add(new JLabel(Text.ACCEL + ":"));
        shipdataGrid.add(accelLabel);
        shipdataGrid.add(accelBtn);
        shipdataGrid.add(new JLabel(Text.ROTSPEED + ":"));
        shipdataGrid.add(rotLabel);
        shipdataGrid.add(rotBtn);
        if (Fighter.class.isInstance(ship)) {
            weaponCBox = new JComboBox<String>(new DefaultComboBoxModel<>(
                    Constants.WEAPONNAMES));
            weaponCBox.setSelectedIndex(assignedWeapon);
            weaponCBox.addItemListener(this);
            shipdataGrid.add(new JLabel(Text.MAINWEAPON + ":"));
            shipdataGrid.add(weaponCBox);
            shipdataGrid.add(weaponBtn);
            checkWeaponBtnStatus();
        } else if (Cruiser.class.isInstance(ship)) {
            List<Weapon> weapons = ((Cruiser) ship).getWeapons();
            weaponLabels = new JLabel[weapons.size()];
            weaponBtns = new JButton[weapons.size()];
            for (int i = 0; i < weapons.size(); i++) {
                weaponLabels[i] = new JLabel(weapons.get(i).toString());
                weaponBtns[i] = new JButton(Text.SELL.toString());
                weaponBtns[i].setEnabled(false);
                shipdataGrid.add(new JLabel(Text.TURRET + " " + i));
                shipdataGrid.add(weaponLabels[i]);
                shipdataGrid.add(weaponBtns[i]);
            }
            if (Carrier.class.isInstance(ship)) {
                Carrier c = (Carrier) ship;
                capacityLabel = new JLabel(c.getCapacity() + "");
                capacityBtn = new JButton("+" + 1);
                capacityBtn.setEnabled(false);
                prodRateLabel = new JLabel(c.getProductionRate() + "");
                prodRateBtn = new JButton("+" + 1);
                prodRateBtn.setEnabled(false);
                prodFleetsLabel = new JLabel();
                prodFleetsBtn = new JButton("Toggle");
                prodFleetsBtn.setEnabled(false);
                shipdataGrid.add(new JLabel(Text.CAPACITY.toString()));
                shipdataGrid.add(capacityLabel);
                shipdataGrid.add(capacityBtn);
                shipdataGrid.add(new JLabel(Text.PRODRATE.toString()));
                shipdataGrid.add(prodRateLabel);
                shipdataGrid.add(prodRateBtn);
                shipdataGrid.add(new JLabel(Text.PRODFLEETS.toString()));
                shipdataGrid.add(prodFleetsLabel);
                shipdataGrid.add(prodFleetsBtn);
            }
            weaponCBox = new JComboBox<String>(new DefaultComboBoxModel<>(
                    Constants.WEAPONNAMES));
            weaponCBox.setSelectedIndex(assignedWeapon);
            weaponCBox.addItemListener(this);
            shipdataGrid.add(weaponCBox);
            addTurretBtn = new JButton(Text.BUY + " " + Text.TURRET);
            shipdataGrid.add(addTurretBtn);
        }
        for (Component c : shipdataGrid.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        shipTab.setLayout(new BorderLayout());
        shipTab.add(new JScrollPane(shipdataGrid), BorderLayout.CENTER);
        return shipTab;
    }

    private JPanel getFleetTab() {
        JPanel fleetTab = new JPanel();
        JPanel buyPanel;
        fleetTab.setLayout(new BorderLayout());
        fleetdataGrid = new JPanel();
        fleetdataGrid.setLayout(new GridLayout(6, 4));
        fleetdataGrid.add(new JLabel(new ImageIcon(Constants.ICON_DIRECTORY + "fighter.gif")));
        fleetdataGrid.add(new JLabel(Text.FIGHTER.toString()));
        fleetdataGrid.add(createShipDataPanel(fighterPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(smallWingmanCount);
        buyPanel.add(addSmallWingmanBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(Constants.ICON_DIRECTORY + "spaceship.gif")));
        fleetdataGrid.add(new JLabel(Text.WINGMAN.toString()));
        fleetdataGrid.add(createShipDataPanel(wingmanPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(wingmanCount);
        buyPanel.add(addWingmanBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(Constants.ICON_DIRECTORY + "fighterfleet.gif")));
        fleetdataGrid.add(new JLabel(Text.FIGHTER + " " + Text.SQUADRON));
        fleetdataGrid.add(createShipDataPanel(fleetleaderPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(fighterFleetCount);
        buyPanel.add(addFighterFleetBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(Constants.ICON_DIRECTORY + "fighterfleet.gif")));
        fleetdataGrid.add(new JLabel(Text.HUNTER + " " + Text.SQUADRON));
        fleetdataGrid.add(createShipDataPanel(hunterPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(hunterCount);
        buyPanel.add(addHunterFleetBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(Constants.ICON_DIRECTORY + "cruiser.gif")));
        fleetdataGrid.add(new JLabel(Text.CRUISER.toString()));
        fleetdataGrid.add(createShipDataPanel(cruiserPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(cruiserCount);
        buyPanel.add(addCruiserBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(Constants.ICON_DIRECTORY + "carrier.gif")));
        fleetdataGrid.add(new JLabel(Text.CARRIER.toString()));
        fleetdataGrid.add(createShipDataPanel(carrierPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(carrierCount);
        buyPanel.add(addCarrierBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        for (Component c : fleetdataGrid.getComponents()) {
            c.setFont(Settings.getSettings().font);
            c.setFocusable(false);
        }
        fleetTab.add(new JScrollPane(fleetdataGrid), BorderLayout.CENTER);
        return fleetTab;
    }

    public static JPanel createShipDataPanel(Ship s) {
        String data = s.getDataString();
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        JTextArea dataText = new JTextArea();
        dataText.setEditable(false);
        dataText.setText(data);
        dataText.setRows(6);
        dataPanel.add(new JScrollPane(dataText), BorderLayout.CENTER);
        return dataPanel;
    }

    public void paintLabels() {
        hpLabel.setText(ship.getMaxHp() + "");
        classLabel.setText(ship.getClass().getSimpleName());
        speedLabel.setText(ship.getMaxSpeed() + "");
        rotLabel.setText(ship.getMaxRotation() + "");
        accelLabel.setText(ship.getMaxAcceleration() + "");
        if (Cruiser.class.isInstance(ship)) {
            if (Carrier.class.isInstance(ship)) {
                Carrier c = (Carrier) ship;
                capacityLabel.setText(c.getCapacity() + "");
                prodRateLabel.setText(c.getProductionRate() + "");
                if (c.isProducingFleets()) {
                    prodFleetsLabel.setText(Text.TRUE.toString());
                } else {
                    prodFleetsLabel.setText(Text.FALSE.toString());
                }
            }
        }
    }

    @Override
    public void onSelect() {
        requestFocus();
    }

    @Override
    public void onDisselect() {
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null) {
            switch (key) {
                case LAND:
                    liftoff();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(liftoffBtn)) {
            liftoff();
        } else if (evt.getSource().equals(weaponBtn)) {
            int index = weaponCBox.getSelectedIndex();
            if (bought[index]) {
                if (index != assignedWeapon && Fighter.class.isInstance(ship)) {
                    ((Fighter) ship).setMainWeapon(Constants.WEAPONS[index]);
                    assignedWeapon = index;
                    checkWeaponBtnStatus();
                }
            } else if (doTransaction(-1 * Constants.WEAPONPRICE[index])) {
                bought[index] = true;
                ((Fighter) ship).setMainWeapon(Constants.WEAPONS[index]);
                assignedWeapon = index;
                checkWeaponBtnStatus();
            }
        } else if (evt.getSource().equals(addSmallWingmanBtn)) {
            int n = (int) (smallWingmanCount.getValue());
            if (doTransaction(-1 * Constants.PRIZE_FIGHTER * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addFighter();
                }
                IO.println(Text.FIGHTER + " " + Text.ADDTOFLEET, IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addWingmanBtn)) {
            int n = (int) (wingmanCount.getValue());
            if (doTransaction(-1 * Constants.PRIZE_WINGMAN * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addWingman();
                }
                IO.println(Text.WINGMAN + " " + Text.ADDTOFLEET, IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addFighterFleetBtn)) {
            int n = (int) (fighterFleetCount.getValue());
            if (doTransaction(-1 * Constants.PRIZE_FIGHTER_SQUAD * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addFighterFleet();
                }
                IO.println(Text.FIGHTER + " " + Text.SQUADRON + " " + Text.ADDTOFLEET,
                        IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addHunterFleetBtn)) {
            int n = (int) (hunterCount.getValue());
            if (doTransaction(-1 * Constants.PRIZE_HUNTER_SQUAD * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addHunterFleet();
                }
                IO.println(Text.HUNTER + " " + Text.SQUADRON + " " + Text.ADDTOFLEET,
                        IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addCruiserBtn)) {
            int n = (int) (cruiserCount.getValue());
            if (doTransaction(-1 * Constants.PRIZE_CRUISER * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addCruiser();
                }
                IO.println(Text.CRUISER + " " + Text.ADDTOFLEET, IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addCarrierBtn)) {
            int n = (int) (carrierCount.getValue());
            if (doTransaction(-1 * Constants.PRIZE_CARRIER * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addCarrier();
                }
                IO.println(Text.CARRIER + " " + Text.ADDTOFLEET, IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(hpBtn)) {
            if (doTransaction(-1 * Constants.HP_UPGRADE_PRICE)) {
                GameGrid.getInstance().getMShip()
                        .setMaxHp(GameGrid.getInstance().getMShip().getMaxHp() + 10);
            }
        }
        paintLabels();
    }

    private void liftoff() {
        GameGrid.getInstance().getMShip().repair();
        MainFrame.FRAME.setMainPanel(GameGrid.getInstance());
        GameGrid.getInstance().setPaused(false);
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        checkWeaponBtnStatus();
    }

    private void checkWeaponBtnStatus() {
        int index = weaponCBox.getSelectedIndex();
        if (bought[weaponCBox.getSelectedIndex()]) {
            if (index != assignedWeapon) {
                weaponBtn.setText(Text.ASSIGN.toString());
                weaponBtn.setEnabled(true);
            } else {
                weaponBtn.setText(Text.ASSIGNED.toString());
                weaponBtn.setEnabled(false);
            }
        } else {
            weaponBtn.setText(Text.BUY.toString());
            weaponBtn.setToolTipText(Text.BUY + " " + Constants.WEAPONNAMES[index] + " \n"
                    + Constants.WEAPONPRICE[index] + Text.CURRENCY_PLURAL);
            weaponBtn.setEnabled(true);
        }
    }

    public boolean doTransaction(int n) {
        if (GameGrid.getInstance().addScore(n)) {
            scoreLabel.setText(GameGrid.getInstance().getScore() + " " + Text.CURRENCY_PLURAL);
            return true;
        } else {
            IO.makeToast(Text.NOTENOUGH + " " + Text.CURRENCY_PLURAL + "\n",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
