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

import actors.Ship;
import graphic.InventoryPanel.ItemBox;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import main.Item;
import main.SpaceInvader;
import platform.graphic.InputConfig;
import platform.graphic.MainFrame;
import platform.graphic.MainPanel;
import platform.utils.IO;
import platform.utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 08.02.2017
 */
public class HangarPanel extends MainPanel
{

    private final Ship mShip;
    private int repairCost;
    private final InventoryPanel loadedItems;
    private final InventoryPanel storedItems;
    private final InventoryPanel marketInventoryItems;
    private final InventoryPanel marketItems;
    private final JButton sellBtn;
    private final ItemBox marketItem;
    private final MouseListener updateOnClick = new MouseListener()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            updateLabels();
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
        }
    };

    /**
     * Creates new form HangarPanel
     *
     * @param mShip selected ship to show in the hangar.
     */
    public HangarPanel(Ship mShip)
    {
        this.mShip = mShip;
        setAutoScaled(false);
        initComponents();

        loadedItems = new InventoryPanel(10, 20, SpaceInvader.getInstance().getLoadedItems());
        loadedItems.addMouseListener(updateOnClick);
        JScrollPane scrollPane = new JScrollPane(loadedItems);
        inventoryPanelLeft.setLayout(new BorderLayout());
        inventoryPanelLeft.add(scrollPane, BorderLayout.CENTER);
        JButton storeBtn = new JButton(IO.translate("StoreAll"));
        storeBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for (Item item : SpaceInvader.getInstance().getLoadedItems())
                {
                    SpaceInvader.getInstance().addStoredItem(item);
                    SpaceInvader.getInstance().removeLoadedItem(item);
                    updateLabels();
                }
            }
        });
        inventoryPanelLeft.add(storeBtn, BorderLayout.SOUTH);
        inventoryPanelLeft.add(new JLabel("Loaded Items"), BorderLayout.NORTH);

        storedItems = new InventoryPanel(10, 20, SpaceInvader.getInstance().getStoredItems());
        storedItems.addMouseListener(updateOnClick);
        JScrollPane scrollPane2 = new JScrollPane(storedItems);
        inventoryPanelRight.setLayout(new BorderLayout());
        inventoryPanelRight.add(scrollPane2, BorderLayout.CENTER);
        inventoryPanelRight.add(new JLabel("Stored Items"), BorderLayout.NORTH);

        marketInventoryItems = new InventoryPanel(10, 20, SpaceInvader.getInstance().getStoredItems());
        marketInventoryItems.addMouseListener(updateOnClick);
        scrollPane2 = new JScrollPane(marketInventoryItems);
        marketInventoryPanel.setLayout(new BorderLayout());
        marketInventoryPanel.add(scrollPane2, BorderLayout.CENTER);
        marketInventoryPanel.add(new JLabel("Stored Items"), BorderLayout.NORTH);
        sellBtn = new JButton(IO.translate("SELL"));
        sellBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                LinkedList<Item> selectedItems = marketInventoryItems.getSelectedItems();
                int value = 0;
                for (Item item : selectedItems)
                {
                    SpaceInvader.getInstance().removeStoredItem(item);
                    value += item.getValue();
                }
                SpaceInvader.getInstance().addScore(value);
                marketInventoryItems.clearSelection();
                updateLabels();
            }
        });
        marketInventoryPanel.add(sellBtn, BorderLayout.SOUTH);

        marketItems = new InventoryPanel(10, 20, SpaceInvader.getInstance().getMarketItems());
        marketItems.addMouseListener(updateOnClick);
        scrollPane2 = new JScrollPane(marketItems);
        marketPanel.setLayout(new BorderLayout());
        marketPanel.add(scrollPane2, BorderLayout.CENTER);
        marketItems.setMultiSelectable(false);
        marketItem = marketItems.new ItemBox(null);
        marketItem.setActive(false);
        marketItemPanel.setLayout(new BorderLayout());
        marketItemPanel.add(marketItem, BorderLayout.CENTER);

        Font font = (Font) Settings.get("font");
        if (font != null)
        {
            sellBtn.setFont(font);
            storeBtn.setFont(font);
            dataText.setFont(font);
            buttonLiftOff.setFont(font);
            jTabbedPane1.setFont(font);
            jLabel1.setFont(font);
            jLabel2.setFont(font);
            jLabel3.setFont(font);
            jLabel4.setFont(font);
            jLabel5.setFont(font);
            marketItemNumberLabel.setFont(font);
            marketPriceLabel.setFont(font);
            marketItemLabel.setFont(font);
            creditLabel.setFont(font);
        }
        updateLabels();
    }

    public final void updateLabels()
    {
        creditLabel.setText(IO.translate("SCORE") + ": " + SpaceInvader.getInstance().getScore() + IO.translate("CREDITS"));
        jLabel1.setIcon(new javax.swing.ImageIcon(mShip.getSprite().getImages()[0]));
        dataText.setText(mShip.getDataString());
        repairCost = mShip.getMaxHp() - mShip.getHP();
        repairCost += (int) ((SpaceInvader.getInstance().getMaxFuel() - SpaceInvader.getInstance().getFuel()) * SpaceInvader.getInstance().getFuelPrice());
        buttonRepair.setText("Repair&Refuel" + " " + repairCost + " " + IO.translate("CREDITS"));
        buttonRepair.setEnabled(repairCost > 0);
        loadedItems.setItems(SpaceInvader.getInstance().getLoadedItems());
        storedItems.setItems(SpaceInvader.getInstance().getStoredItems());
        marketInventoryItems.setItems(SpaceInvader.getInstance().getStoredItems());
        marketItems.setItems(SpaceInvader.getInstance().getMarketItems());
        if (marketItems.getSelectedItems().size() > 0)
        {
            marketItem.setItem(marketItems.getSelectedItems().getFirst());
            marketItemLabel.setText(marketItem.getItem().toString());
            int n = (int) marketItemSpinner.getValue();
            marketPriceLabel.setText(IO.translate("PRICE") + ": " + marketItem.getItem().getValue() * n + IO.translate("CREDITS"));
            buyButton.setEnabled(true);
        }
        else
        {
            marketItemLabel.setText("");
            marketItem.setItem(null);
            marketPriceLabel.setText("");
            buyButton.setEnabled(false);
        }
        LinkedList<Item> selectedItems = marketInventoryItems.getSelectedItems();
        sellBtn.setEnabled(selectedItems.size() > 0);
        int value = 0;
        for (Item item : selectedItems)
        {
            value += item.getValue();
        }
        sellBtn.setText(IO.translate("SELL") + " " + value + IO.translate("CREDITS"));
        repaint();
    }

    private void liftoff()
    {
        SpaceInvader.getInstance().getMShip().repair();
        MainFrame.FRAME.setMainPanel(GamePanel.INSTANCE);
        GamePanel.INSTANCE.setRunning(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        hangarPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataText = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        inventoryPanelLeft = new javax.swing.JPanel();
        inventoryPanelRight = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSplitPane4 = new javax.swing.JSplitPane();
        marketInventoryPanel = new javax.swing.JPanel();
        jSplitPane5 = new javax.swing.JSplitPane();
        marketPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        marketItemPanel = new javax.swing.JPanel();
        marketItemLabel = new javax.swing.JLabel();
        marketItemSpinner = new javax.swing.JSpinner();
        marketItemNumberLabel = new javax.swing.JLabel();
        marketPriceLabel = new javax.swing.JLabel();
        buyButton = new javax.swing.JButton();
        buttonLiftOff = new javax.swing.JButton();
        buttonRepair = new javax.swing.JButton();
        creditLabel = new javax.swing.JLabel();

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jLabel2.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Spaceship");

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane2.setDividerLocation(250);

        dataText.setEditable(false);
        dataText.setColumns(20);
        dataText.setRows(5);
        dataText.setText("Statistics\nHP: 93123 / 120000\nFuel: 923 / 10000\nShield: 89123 / 115000\n");
        jScrollPane1.setViewportView(dataText);

        jSplitPane2.setRightComponent(jScrollPane1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphic/icons/spaceship.gif"))); // NOI18N
        jSplitPane2.setLeftComponent(jLabel1);

        jSplitPane1.setLeftComponent(jSplitPane2);

        javax.swing.GroupLayout inventoryPanelLeftLayout = new javax.swing.GroupLayout(inventoryPanelLeft);
        inventoryPanelLeft.setLayout(inventoryPanelLeftLayout);
        inventoryPanelLeftLayout.setHorizontalGroup(
            inventoryPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        inventoryPanelLeftLayout.setVerticalGroup(
            inventoryPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        jSplitPane3.setLeftComponent(inventoryPanelLeft);

        javax.swing.GroupLayout inventoryPanelRightLayout = new javax.swing.GroupLayout(inventoryPanelRight);
        inventoryPanelRight.setLayout(inventoryPanelRightLayout);
        inventoryPanelRightLayout.setHorizontalGroup(
            inventoryPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
        );
        inventoryPanelRightLayout.setVerticalGroup(
            inventoryPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        jSplitPane3.setRightComponent(inventoryPanelRight);

        jSplitPane1.setRightComponent(jSplitPane3);

        javax.swing.GroupLayout hangarPanelLayout = new javax.swing.GroupLayout(hangarPanel);
        hangarPanel.setLayout(hangarPanelLayout);
        hangarPanelLayout.setHorizontalGroup(
            hangarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hangarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hangarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        hangarPanelLayout.setVerticalGroup(
            hangarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hangarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hangar", hangarPanel);

        jLabel3.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Fleet");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(436, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Fleet", jPanel3);

        jLabel4.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Factory");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(436, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Factory", jPanel1);

        jLabel5.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Market");

        jSplitPane4.setDividerLocation(300);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout marketInventoryPanelLayout = new javax.swing.GroupLayout(marketInventoryPanel);
        marketInventoryPanel.setLayout(marketInventoryPanelLayout);
        marketInventoryPanelLayout.setHorizontalGroup(
            marketInventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        marketInventoryPanelLayout.setVerticalGroup(
            marketInventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );

        jSplitPane4.setRightComponent(marketInventoryPanel);

        jSplitPane5.setDividerLocation(500);

        javax.swing.GroupLayout marketPanelLayout = new javax.swing.GroupLayout(marketPanel);
        marketPanel.setLayout(marketPanelLayout);
        marketPanelLayout.setHorizontalGroup(
            marketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        marketPanelLayout.setVerticalGroup(
            marketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 297, Short.MAX_VALUE)
        );

        jSplitPane5.setLeftComponent(marketPanel);

        javax.swing.GroupLayout marketItemPanelLayout = new javax.swing.GroupLayout(marketItemPanel);
        marketItemPanel.setLayout(marketItemPanelLayout);
        marketItemPanelLayout.setHorizontalGroup(
            marketItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        marketItemPanelLayout.setVerticalGroup(
            marketItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        marketItemLabel.setText("Item");

        marketItemSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        marketItemSpinner.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                marketItemSpinnerStateChanged(evt);
            }
        });

        marketItemNumberLabel.setText("Number:");

        marketPriceLabel.setText("Price: 124231231231232 CREDITS");

        buyButton.setText("Buy");
        buyButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(buyButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(marketItemLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(marketItemNumberLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(marketItemSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(136, Short.MAX_VALUE))
                    .addComponent(marketItemPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(marketPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(marketItemLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marketItemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(marketItemSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(marketItemNumberLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marketPriceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buyButton)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jSplitPane5.setRightComponent(jPanel2);

        jSplitPane4.setLeftComponent(jSplitPane5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSplitPane4)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane4))
        );

        jTabbedPane1.addTab("Market", jPanel4);

        buttonLiftOff.setBackground(new java.awt.Color(51, 255, 51));
        buttonLiftOff.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        buttonLiftOff.setText("Lift Off");
        buttonLiftOff.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonLiftOffActionPerformed(evt);
            }
        });

        buttonRepair.setBackground(new java.awt.Color(204, 255, 0));
        buttonRepair.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        buttonRepair.setText("Repair &  Refuel 123 CREDITS");
        buttonRepair.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonRepairActionPerformed(evt);
            }
        });

        creditLabel.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        creditLabel.setText("CREDITS: 123124");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonRepair)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLiftOff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(creditLabel)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLiftOff)
                    .addComponent(buttonRepair)
                    .addComponent(creditLabel))
                .addContainerGap())
        );

        buttonRepair.getAccessibleContext().setAccessibleName("Repair &  Refuel 123CREDITS");
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLiftOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLiftOffActionPerformed
        liftoff();
    }//GEN-LAST:event_buttonLiftOffActionPerformed

    private void buttonRepairActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonRepairActionPerformed
    {//GEN-HEADEREND:event_buttonRepairActionPerformed
        mShip.repair();
        SpaceInvader.getInstance().refuel();
        SpaceInvader.getInstance().addScore(repairCost * -1);
        updateLabels();
    }//GEN-LAST:event_buttonRepairActionPerformed

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buyButtonActionPerformed
    {//GEN-HEADEREND:event_buyButtonActionPerformed
        if (marketItem.getItem() != null)
        {
            int price = marketItem.getItem().getValue();
            int n = (int) marketItemSpinner.getValue();
            price = price * n;
            SpaceInvader.getInstance().addScore(price * -1);
            SpaceInvader.getInstance().addStoredItem(new Item(marketItem.getItem().type, n));
            updateLabels();
        }
    }//GEN-LAST:event_buyButtonActionPerformed

    private void marketItemSpinnerStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_marketItemSpinnerStateChanged
    {//GEN-HEADEREND:event_marketItemSpinnerStateChanged
        updateLabels();
    }//GEN-LAST:event_marketItemSpinnerStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLiftOff;
    private javax.swing.JButton buttonRepair;
    private javax.swing.JButton buyButton;
    private javax.swing.JLabel creditLabel;
    private javax.swing.JTextArea dataText;
    private javax.swing.JPanel hangarPanel;
    private javax.swing.JPanel inventoryPanelLeft;
    private javax.swing.JPanel inventoryPanelRight;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel marketInventoryPanel;
    private javax.swing.JLabel marketItemLabel;
    private javax.swing.JLabel marketItemNumberLabel;
    private javax.swing.JPanel marketItemPanel;
    private javax.swing.JSpinner marketItemSpinner;
    private javax.swing.JPanel marketPanel;
    private javax.swing.JLabel marketPriceLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onSelect()
    {

    }

    @Override
    public void onDisselect()
    {

    }

    @Override
    public void drawGUI(Graphics2D gd)
    {

    }

    @Override
    public void keyPressed(InputConfig.Key key)
    {
        if (key == InputConfig.Key.KEY_SELECT)
        {
            liftoff();
        }
    }

    @Override
    public void keyReleased(InputConfig.Key key)
    {

    }
}
