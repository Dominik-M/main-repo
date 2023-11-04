/*
 * Copyright (C) 2022 Dominik Messerschmidt
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
package gui;

import main.Actor;
import main.Game;

/**
 *
 * @author Dominik Messerschmidt
 */
public class CharacterPanel extends javax.swing.JPanel
{

    private Actor actor;

    /**
     * Creates new form CharacterPanel
     */
    public CharacterPanel()
    {
        initComponents();
        setSize(getPreferredSize());
    }

    public Actor getActor()
    {
        return actor;
    }

    public void setActor(Actor actor)
    {
        if (actor != null)
        {
            switch (actor.getType())
            {
                case BARBAR:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/barbarIcon.png")));
                    break;
                case DWARF:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dwarfIcon.png")));
                    break;
                case ELF:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/elfIcon.png")));
                    break;
                case GOBLIN:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/goblinIcon.png")));
                    break;
                case KNIGHT:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/knightIcon.png")));
                    break;
                case MAGE:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mageIcon.png")));
                    break;
                case ORC:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/orcIcon.png")));
                    break;
                default:
                    image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/abstract.png")));
                    break;
            }
        }
        this.actor = actor;
    }

    public void update()
    {
        if (actor != null)
        {
            if (actor.equals(Game.getInstance().getActorOnTurn()))
            {
                setBackground(new java.awt.Color(134, 255, 117));
            }
            else
            {
                setBackground(new java.awt.Color(234, 205, 117));
            }
            name.setText(actor.getName());
            hp.setText("HP: " + actor.getHp() + "/" + actor.getMaxHp());
            atk.setText("ATK: " + actor.getAtk());
            def.setText("DEF: " + actor.getDef());
            spd.setText("SPD: " + actor.getSpd());
            res.setText("INT: " + actor.getRes());
            gold.setText("Gold: " + actor.getGold());
        }
        else
        {
            name.setText("");
            hp.setText("");
            atk.setText("");
            def.setText("");
            spd.setText("");
            res.setText("");
            gold.setText("");
        }
    }

    @Override
    public boolean isVisible()
    {
        return super.isVisible() && this.actor != null;
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

        image = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        atk = new javax.swing.JLabel();
        def = new javax.swing.JLabel();
        spd = new javax.swing.JLabel();
        hp = new javax.swing.JLabel();
        res = new javax.swing.JLabel();
        gold = new javax.swing.JLabel();

        setBackground(new java.awt.Color(234, 205, 117));
        setPreferredSize(new java.awt.Dimension(380, 111));

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/abstract.png"))); // NOI18N

        name.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name.setText("Name");

        atk.setText("jLabel1");

        def.setText("jLabel1");

        spd.setText("jLabel1");

        hp.setText("jLabel1");

        res.setText("jLabel1");

        gold.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(hp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(atk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(def)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(res))
                    .addComponent(gold))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(name)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(atk)
                            .addComponent(def)
                            .addComponent(spd)
                            .addComponent(hp)
                            .addComponent(res))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gold))
                    .addComponent(image)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atk;
    private javax.swing.JLabel def;
    private javax.swing.JLabel gold;
    private javax.swing.JLabel hp;
    private javax.swing.JLabel image;
    private javax.swing.JLabel name;
    private javax.swing.JLabel res;
    private javax.swing.JLabel spd;
    // End of variables declaration//GEN-END:variables
}
