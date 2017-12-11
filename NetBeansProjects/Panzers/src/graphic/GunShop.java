/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package graphic;

import panzers.Gun;
import panzers.Tank;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class GunShop extends javax.swing.JDialog {
    public static final int[] REQUIRED_LEVEL={3,5,10,12,18,25,30};

    private Tank mTank;
    private Gun selected;
    private int gunPrice;
    /**
     * Creates new form GunShop
     */
    private GunShop() {
        this.gunPrice = 5;
        initComponents();
        setModal(true);
    }
    
    public GunShop(java.awt.Point location,Tank t){
        this();
        this.gunPrice = 5;
        mTank=t;
        updateLabels();
        setLocation(location);
        setVisible(true);
    }

    public final void updateLabels(){
        int level=mTank.getLevel();
        buyMG.setEnabled(level>=GunShop.REQUIRED_LEVEL[0]);
        if(buyMG.isEnabled())
          buyMG.setToolTipText("Fast shooting light machine gun.");
        else{
          buyMG.setToolTipText("Unlock at level "+GunShop.REQUIRED_LEVEL[0]);
        }
        buyGrenade.setEnabled(level>=GunShop.REQUIRED_LEVEL[1]);
        if(buyGrenade.isEnabled())
          buyGrenade.setToolTipText("High area on effect damage at close range.");
        else{
          buyGrenade.setToolTipText("Unlock at level "+GunShop.REQUIRED_LEVEL[1]);
        }
        buyRicochet.setEnabled(level>=GunShop.REQUIRED_LEVEL[2]);
        if(buyRicochet.isEnabled())
          buyRicochet.setToolTipText("Bouncing projectiles with high range.");
        else{
          buyRicochet.setToolTipText("Unlock at level "+GunShop.REQUIRED_LEVEL[2]);
        }
        buyFlamethrower.setEnabled(level>=GunShop.REQUIRED_LEVEL[3]);
        if(buyFlamethrower.isEnabled())
          buyFlamethrower.setToolTipText("Armor piercing damage over time.");
        else{
          buyFlamethrower.setToolTipText("Unlock at level "+GunShop.REQUIRED_LEVEL[3]);
        }
        buyLaser.setEnabled(level>=GunShop.REQUIRED_LEVEL[4]);
        if(buyLaser.isEnabled())
          buyLaser.setToolTipText("Fires an instant laserbeam.");
        else{
          buyLaser.setToolTipText("Unlock at level "+GunShop.REQUIRED_LEVEL[4]);
        }
        buyRocket.setEnabled(level>=GunShop.REQUIRED_LEVEL[5]);
        if(buyRocket.isEnabled())
          buyRocket.setToolTipText("Extremely strong and fast rockets.");
        else{
          buyRocket.setToolTipText("Unlock at level "+GunShop.REQUIRED_LEVEL[5]);
        }
        buyGatlin.setEnabled(level>=GunShop.REQUIRED_LEVEL[6]);
        if(buyGatlin.isEnabled())
          buyGatlin.setToolTipText("Bursts strong projectiles with extremely high firerate.");
        else{
          buyGatlin.setToolTipText("Unlock at level "+GunShop.REQUIRED_LEVEL[6]);
        }
        if(selected!=null){
            name.setText(selected.toString());
            dmg.setText("Damage: "+selected.getDamage());
            rate.setText("Firerate: "+selected.getFirerate());
            range.setText("Range: "+selected.getRange());
            accu.setText("Accuracy: "+selected.getAccuracy());
            ammo.setText("Ammo: "+selected.getAmmo());
            price.setText("("+gunPrice+" Upgrade Points)");
        }else{
            name.setText("NONE");
            dmg.setText("Damage: ----");
            rate.setText("Firerate: ----");
            range.setText("Range: ----");
            accu.setText("Accuracy: ----");
            ammo.setText("Ammo: ----");
            price.setText(("(nothing selected)"));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buyMG = new javax.swing.JButton();
        dmg = new javax.swing.JLabel();
        buyGrenade = new javax.swing.JButton();
        buyLaser = new javax.swing.JButton();
        buyGatlin = new javax.swing.JButton();
        buyRicochet = new javax.swing.JButton();
        buyFlamethrower = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        rate = new javax.swing.JLabel();
        range = new javax.swing.JLabel();
        accu = new javax.swing.JLabel();
        ammo = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        buy = new javax.swing.JButton();
        price = new javax.swing.JLabel();
        buyRocket = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buyMG.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        buyMG.setText("Machine Gun");
        buyMG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyMGActionPerformed(evt);
            }
        });

        dmg.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        dmg.setText("Damage: 1000");

        buyGrenade.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        buyGrenade.setText("Grenade Launcher");
        buyGrenade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyGrenadeActionPerformed(evt);
            }
        });

        buyLaser.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        buyLaser.setText("Laser Gun");
        buyLaser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyLaserActionPerformed(evt);
            }
        });

        buyGatlin.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        buyGatlin.setText("Gatlin Gun");
        buyGatlin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyGatlinActionPerformed(evt);
            }
        });

        buyRicochet.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        buyRicochet.setText("Ricochet Rifle");
        buyRicochet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyRicochetActionPerformed(evt);
            }
        });

        buyFlamethrower.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        buyFlamethrower.setText("Flamethrower");
        buyFlamethrower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyFlamethrowerActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel2.setText("Weapon Stats");

        rate.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        rate.setText("Firerate: 1000");

        range.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        range.setText("Range: 10000");

        accu.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        accu.setText("Accuracy: 100");

        ammo.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        ammo.setText("Ammo: 10000");

        name.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        name.setText("Grenade Launcher");

        buy.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        buy.setText("BUY");
        buy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyActionPerformed(evt);
            }
        });

        price.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        price.setText("(5 Upgrade Points)");

        buyRocket.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        buyRocket.setText("Rocket Launcher");
        buyRocket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyRocketActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(buyFlamethrower, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyMG, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyGrenade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyLaser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyGatlin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyRicochet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyRocket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(name)
                            .addComponent(dmg)
                            .addComponent(rate)
                            .addComponent(range)
                            .addComponent(accu)
                            .addComponent(ammo)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(buy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(price)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buyMG)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buyGrenade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buyRicochet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buyFlamethrower))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dmg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(range)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buyLaser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buyRocket))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(accu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ammo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buyGatlin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buy)
                    .addComponent(price))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buyMGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyMGActionPerformed
        selected=Gun.getMachineGun();
        updateLabels();
    }//GEN-LAST:event_buyMGActionPerformed

    private void buyGrenadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyGrenadeActionPerformed
        selected=Gun.getGrenadeLauncher();
        updateLabels();
    }//GEN-LAST:event_buyGrenadeActionPerformed

    private void buyLaserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyLaserActionPerformed
        selected=Gun.getLaser();
        updateLabels();
    }//GEN-LAST:event_buyLaserActionPerformed

    private void buyGatlinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyGatlinActionPerformed
        selected=Gun.getGatlinGun();
        updateLabels();
    }//GEN-LAST:event_buyGatlinActionPerformed

    private void buyRicochetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyRicochetActionPerformed
        selected=Gun.getRicochetRifle();
        updateLabels();
    }//GEN-LAST:event_buyRicochetActionPerformed

    private void buyFlamethrowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyFlamethrowerActionPerformed
        selected=Gun.getFlamethrower();
        updateLabels();
    }//GEN-LAST:event_buyFlamethrowerActionPerformed

    private void buyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyActionPerformed
        if(selected!=null){
            if(mTank.spendPoints(gunPrice)){
              mTank.addGuns(selected);
            }
            else{
                javax.swing.JOptionPane.showMessageDialog(this, "Not enough Upgrade Points.");
                return;
            }
        }
        javax.swing.JOptionPane.showMessageDialog(this, selected+" has been added to your inventory.");
    }//GEN-LAST:event_buyActionPerformed

    private void buyRocketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyRocketActionPerformed
        selected=Gun.getRocketLauncher();
        updateLabels();
    }//GEN-LAST:event_buyRocketActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accu;
    private javax.swing.JLabel ammo;
    private javax.swing.JButton buy;
    private javax.swing.JButton buyFlamethrower;
    private javax.swing.JButton buyGatlin;
    private javax.swing.JButton buyGrenade;
    private javax.swing.JButton buyLaser;
    private javax.swing.JButton buyMG;
    private javax.swing.JButton buyRicochet;
    private javax.swing.JButton buyRocket;
    private javax.swing.JLabel dmg;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel name;
    private javax.swing.JLabel price;
    private javax.swing.JLabel range;
    private javax.swing.JLabel rate;
    // End of variables declaration//GEN-END:variables
}