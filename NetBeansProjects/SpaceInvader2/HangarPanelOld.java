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
import actors.ShipFactory;
import armory.GunFactory;
import armory.Weapon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import main.SpaceInvader;
import platform.graphic.InputConfig.Key;
import platform.graphic.MainFrame;
import platform.graphic.MainPanel;
import platform.utils.IO;
import platform.utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class HangarPanelOld extends MainPanel implements ActionListener, ItemListener {

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
    private JComboBox<GunFactory.GunOffer> weaponCBox;
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

    public HangarPanelOld(Ship mShip) {
        ship = mShip;
        init();
        this.setAutoScaled(false);
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
        liftoffBtn = new JButton(IO.translate("LIFTOFF"));
        shipdataGrid = new JPanel();
        scoreLabel = new JLabel(IO.translate("SCORE") + ": "
                + SpaceInvader.getInstance().getScore() + " " + IO.translate("CURRENCY_PLURAL"));
        scoreLabel.setFont((Font) Settings.get("font"));
        hpLabel = new JLabel();
        hpBtn = new JButton("+10 " + IO.translate("HP"));
        hpBtn.addActionListener(this);
        classLabel = new JLabel();
        shipBtn = new JButton(IO.translate("BUYSHIP"));
        shipBtn.setEnabled(false);
        shipBtn.addActionListener(this);
        speedLabel = new JLabel();
        speedBtn = new JButton("+" + IO.translate("SPEED"));
        // speedBtn.setEnabled(false);
        speedBtn.addActionListener(this);
        rotLabel = new JLabel();
        rotBtn = new JButton("+" + IO.translate("ROTSPEED"));
        // rotBtn.setEnabled(false);
        rotBtn.addActionListener(this);
        accelLabel = new JLabel();
        accelBtn = new JButton("+" + IO.translate("ACCEL"));
        // accelBtn.setEnabled(false);
        accelBtn.addActionListener(this);
        weaponBtn = new JButton();
        weaponBtn.addActionListener(this);
        addSmallWingmanBtn = new JButton(IO.translate("BUY"));
        addSmallWingmanBtn.setToolTipText(" " + ShipFactory.PRIZE_FIGHTER + " "
                + IO.translate("CURRENCY_PLURAL"));
        // addSmallWingmanBtn.setEnabled(false);
        addSmallWingmanBtn.addActionListener(this);
        smallWingmanCount = new javax.swing.JSpinner(
                new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addWingmanBtn = new JButton(IO.translate("BUY"));
        addWingmanBtn.setToolTipText(" " + ShipFactory.PRIZE_WINGMAN + " "
                + IO.translate("CURRENCY_PLURAL"));
        // addWingmanBtn.setEnabled(false);
        addWingmanBtn.addActionListener(this);
        wingmanCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addFighterFleetBtn = new JButton(IO.translate("BUY"));
        addFighterFleetBtn.setToolTipText(" " + ShipFactory.PRIZE_FIGHTER_SQUAD + " "
                + IO.translate("CURRENCY_PLURAL"));
        // addFighterFleetBtn.setEnabled(false);
        addFighterFleetBtn.addActionListener(this);
        fighterFleetCount = new javax.swing.JSpinner(
                new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addHunterFleetBtn = new JButton(IO.translate("BUY"));
        addHunterFleetBtn.setToolTipText(" " + ShipFactory.PRIZE_HUNTER_SQUAD + " "
                + IO.translate("CURRENCY_PLURAL"));
        // addHunterFleetBtn.setEnabled(false);
        addHunterFleetBtn.addActionListener(this);
        hunterCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addCruiserBtn = new JButton(IO.translate("BUY"));
        addCruiserBtn.setToolTipText(" " + ShipFactory.PRIZE_CRUISER + " "
                + IO.translate("CURRENCY_PLURAL"));
        // addCruiserBtn.setEnabled(false);
        addCruiserBtn.addActionListener(this);
        cruiserCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        addCarrierBtn = new JButton(IO.translate("BUY"));
        addCarrierBtn.setToolTipText(" " + ShipFactory.PRIZE_CARRIER + " "
                + IO.translate("CURRENCY_PLURAL"));
        // addCarrierBtn.setEnabled(false);
        addCarrierBtn.addActionListener(this);
        carrierCount = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        liftoffBtn.setFont(new java.awt.Font("Consolas", 1, 24));
        liftoffBtn.setBackground(Color.GREEN);
        liftoffBtn.addActionListener(this);
        contentPane = new JTabbedPane(2);
        contentPane.add(createShipTab());
        contentPane.setTitleAt(0, IO.translate("SHIP"));
        contentPane.add(getFleetTab());
        contentPane.setTitleAt(1, IO.translate("FLEET"));
        this.setLayout(new BorderLayout());
        this.add(contentPane, BorderLayout.CENTER);
        this.add(liftoffBtn, BorderLayout.SOUTH);
        this.add(scoreLabel, BorderLayout.NORTH);
        paintLabels();
    }

    private JPanel createShipTab() {
        JPanel shipTab = new JPanel();
        shipdataGrid.setLayout(new GridLayout(propertyCount, 3));
        shipdataGrid.add(new JLabel(IO.translate("CLASS") + ":"));
        shipdataGrid.add(classLabel);
        shipdataGrid.add(shipBtn);
        shipdataGrid.add(new JLabel(IO.translate("HITPOINTS") + ":"));
        shipdataGrid.add(hpLabel);
        shipdataGrid.add(hpBtn);
        shipdataGrid.add(new JLabel(IO.translate("SPEED") + ":"));
        shipdataGrid.add(speedLabel);
        shipdataGrid.add(speedBtn);
        shipdataGrid.add(new JLabel(IO.translate("ACCEL") + ":"));
        shipdataGrid.add(accelLabel);
        shipdataGrid.add(accelBtn);
        shipdataGrid.add(new JLabel(IO.translate("ROTSPEED") + ":"));
        shipdataGrid.add(rotLabel);
        shipdataGrid.add(rotBtn);
        if (Fighter.class.isInstance(ship)) {
            weaponCBox = new JComboBox<GunFactory.GunOffer>(new DefaultComboBoxModel<>(
                    GunFactory.GUN_STORE));
            weaponCBox.setSelectedIndex(assignedWeapon);
            weaponCBox.addItemListener(this);
            shipdataGrid.add(new JLabel(IO.translate("MAINWEAPON") + ":"));
            shipdataGrid.add(weaponCBox);
            shipdataGrid.add(weaponBtn);
            checkWeaponBtnStatus();
        } else if (Cruiser.class.isInstance(ship)) {
            List<Weapon> weapons = ((Cruiser) ship).getWeapons();
            weaponLabels = new JLabel[weapons.size()];
            weaponBtns = new JButton[weapons.size()];
            for (int i = 0; i < weapons.size(); i++) {
                weaponLabels[i] = new JLabel(weapons.get(i).toString());
                weaponBtns[i] = new JButton(IO.translate("SELL"));
                weaponBtns[i].setEnabled(false);
                shipdataGrid.add(new JLabel(IO.translate("TURRET") + " " + i));
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
                shipdataGrid.add(new JLabel(IO.translate("CAPACITY")));
                shipdataGrid.add(capacityLabel);
                shipdataGrid.add(capacityBtn);
                shipdataGrid.add(new JLabel(IO.translate("PRODRATE")));
                shipdataGrid.add(prodRateLabel);
                shipdataGrid.add(prodRateBtn);
                shipdataGrid.add(new JLabel(IO.translate("PRODFLEETS")));
                shipdataGrid.add(prodFleetsLabel);
                shipdataGrid.add(prodFleetsBtn);
            }
            weaponCBox = new JComboBox<GunFactory.GunOffer>(new DefaultComboBoxModel<>(
                    GunFactory.GUN_STORE));
            weaponCBox.setSelectedIndex(assignedWeapon);
            weaponCBox.addItemListener(this);
            shipdataGrid.add(weaponCBox);
            addTurretBtn = new JButton(IO.translate("BUY") + " " + IO.translate("TURRET"));
            shipdataGrid.add(addTurretBtn);
        }
        for (Component c : shipdataGrid.getComponents()) {
            c.setFont((Font) Settings.get("font"));
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
        fleetdataGrid.add(new JLabel(new ImageIcon(SpaceInvader.ICON_DIRECTORY + "fighter.gif")));
        fleetdataGrid.add(new JLabel(IO.translate("FIGHTER")));
        fleetdataGrid.add(createShipDataPanel(ShipFactory.fighterPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(smallWingmanCount);
        buyPanel.add(addSmallWingmanBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont((Font) Settings.get("font"));
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(SpaceInvader.ICON_DIRECTORY + "spaceship.gif")));
        fleetdataGrid.add(new JLabel(IO.translate("WINGMAN")));
        fleetdataGrid.add(createShipDataPanel(ShipFactory.wingmanPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(wingmanCount);
        buyPanel.add(addWingmanBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont((Font) Settings.get("font"));
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(
                new ImageIcon(SpaceInvader.ICON_DIRECTORY + "fighterfleet.gif")));
        fleetdataGrid.add(new JLabel(IO.translate("FIGHTER") + " " + IO.translate("SQUADRON")));
        fleetdataGrid.add(createShipDataPanel(ShipFactory.fleetleaderPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(fighterFleetCount);
        buyPanel.add(addFighterFleetBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont((Font) Settings.get("font"));
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(
                new ImageIcon(SpaceInvader.ICON_DIRECTORY + "fighterfleet.gif")));
        fleetdataGrid.add(new JLabel(IO.translate("HUNTER") + " " + IO.translate("SQUADRON")));
        fleetdataGrid.add(createShipDataPanel(ShipFactory.hunterPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(hunterCount);
        buyPanel.add(addHunterFleetBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont((Font) Settings.get("font"));
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(SpaceInvader.ICON_DIRECTORY + "cruiser.gif")));
        fleetdataGrid.add(new JLabel(IO.translate("CRUISER")));
        fleetdataGrid.add(createShipDataPanel(ShipFactory.cruiserPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(cruiserCount);
        buyPanel.add(addCruiserBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont((Font) Settings.get("font"));
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        fleetdataGrid.add(new JLabel(new ImageIcon(SpaceInvader.ICON_DIRECTORY + "carrier.gif")));
        fleetdataGrid.add(new JLabel(IO.translate("CARRIER")));
        fleetdataGrid.add(createShipDataPanel(ShipFactory.carrierPrototyp));
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(1, 2));
        buyPanel.add(carrierCount);
        buyPanel.add(addCarrierBtn);
        for (Component c : buyPanel.getComponents()) {
            c.setFont((Font) Settings.get("font"));
            c.setFocusable(false);
        }
        fleetdataGrid.add(buyPanel);
        for (Component c : fleetdataGrid.getComponents()) {
            c.setFont((Font) Settings.get("font"));
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
                    prodFleetsLabel.setText(IO.translate("TRUE"));
                } else {
                    prodFleetsLabel.setText(IO.translate("FALSE"));
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
    public void keyPressed(Key key) {
        switch (key) {
            case KEY_SELECT:
                liftoff();
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(Key key) {
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(liftoffBtn)) {
            liftoff();
        } else if (evt.getSource().equals(weaponBtn)) {
            int index = weaponCBox.getSelectedIndex();
            if (bought[index]) {
                if (index != assignedWeapon && Fighter.class.isInstance(ship)) {
                    ((Fighter) ship).setMainWeapon(GunFactory
                            .getGunFromCatalog(GunFactory.GUN_STORE[index]));
                    assignedWeapon = index;
                    checkWeaponBtnStatus();
                }
            } else if (doTransaction(-1 * GunFactory.GUN_STORE[index].price)) {
                bought[index] = true;
                ((Fighter) ship).setMainWeapon(GunFactory
                        .getGunFromCatalog(GunFactory.GUN_STORE[index]));
                assignedWeapon = index;
                checkWeaponBtnStatus();
            }
        } else if (evt.getSource().equals(addSmallWingmanBtn)) {
            int n = (int) (smallWingmanCount.getValue());
            if (doTransaction(-1 * ShipFactory.PRIZE_FIGHTER * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addFighter();
                }
                IO.println(IO.translate("FIGHTER") + " " + IO.translate("ADDTOFLEET"),
                        IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addWingmanBtn)) {
            int n = (int) (wingmanCount.getValue());
            if (doTransaction(-1 * ShipFactory.PRIZE_WINGMAN * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addWingman();
                }
                IO.println(IO.translate("WINGMAN") + " " + IO.translate("ADDTOFLEET"),
                        IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addFighterFleetBtn)) {
            int n = (int) (fighterFleetCount.getValue());
            if (doTransaction(-1 * ShipFactory.PRIZE_FIGHTER_SQUAD * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addFighterFleet();
                }
                IO.println(
                        IO.translate("FIGHTER") + " " + IO.translate("SQUADRON") + " "
                        + IO.translate("ADDTOFLEET"), IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addHunterFleetBtn)) {
            int n = (int) (hunterCount.getValue());
            if (doTransaction(-1 * ShipFactory.PRIZE_HUNTER_SQUAD * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addHunterFleet();
                }
                IO.println(
                        IO.translate("HUNTER") + " " + IO.translate("SQUADRON") + " "
                        + IO.translate("ADDTOFLEET"), IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addCruiserBtn)) {
            int n = (int) (cruiserCount.getValue());
            if (doTransaction(-1 * ShipFactory.PRIZE_CRUISER * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addCruiser();
                }
                IO.println(IO.translate("CRUISER") + " " + IO.translate("ADDTOFLEET"),
                        IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(addCarrierBtn)) {
            int n = (int) (carrierCount.getValue());
            if (doTransaction(-1 * ShipFactory.PRIZE_CARRIER * n)) {
                for (int i = 0; i < n; i++) {
                    SimControlPanel.addCarrier();
                }
                IO.println(IO.translate("CARRIER") + " " + IO.translate("ADDTOFLEET"),
                        IO.MessageType.IMPORTANT);
            }
        } else if (evt.getSource().equals(hpBtn)) {
            if (doTransaction(-1 * ShipFactory.HP_UPGRADE_PRICE)) {
                SpaceInvader.getInstance().getMShip()
                        .setMaxHp(SpaceInvader.getInstance().getMShip().getMaxHp() + 10);
            }
        }
        paintLabels();
    }

    private void liftoff() {
        SpaceInvader.getInstance().getMShip().repair();
        MainFrame.FRAME.setMainPanel(GamePanel.INSTANCE);
        GamePanel.INSTANCE.setRunning(true);
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        checkWeaponBtnStatus();
    }

    private void checkWeaponBtnStatus() {
        int index = weaponCBox.getSelectedIndex();
        if (bought[weaponCBox.getSelectedIndex()]) {
            if (index != assignedWeapon) {
                weaponBtn.setText(IO.translate("ASSIGN"));
                weaponBtn.setEnabled(true);
            } else {
                weaponBtn.setText(IO.translate("ASSIGNED"));
                weaponBtn.setEnabled(false);
            }
        } else {
            weaponBtn.setText(IO.translate("BUY"));
            weaponBtn.setToolTipText(IO.translate("BUY") + " " + GunFactory.GUN_STORE[index]
                    + " \n" + GunFactory.GUN_STORE[index].price + IO.translate("CURRENCY_PLURAL"));
            weaponBtn.setEnabled(true);
        }
    }

    public boolean doTransaction(int n) {
        if (SpaceInvader.getInstance().addScore(n)) {
            scoreLabel.setText(SpaceInvader.getInstance().getScore() + " "
                    + IO.translate("CURRENCY_PLURAL"));
            return true;
        } else {
            IO.makeToast(IO.translate("NOTENOUGH") + " " + IO.translate("CURRENCY_PLURAL") + "\n",
                    IO.MessageType.ERROR);
            return false;
        }
    }

    @Override
    public void drawGUI(Graphics2D arg0) {
    }
}
