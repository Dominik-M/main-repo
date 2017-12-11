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

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class SoundPlayerPanel extends javax.swing.JPanel implements java.awt.event.MouseMotionListener, java.awt.event.MouseListener{
    private int selIndex;
    
    /**
     * Creates new form SoundPlayerPanel
     */
    public SoundPlayerPanel() {
        initComponents();
        seekBar.addMouseListener(this);
        seekBar.addMouseMotionListener(this);
        cliplist.setModel(new javax.swing.AbstractListModel() {
            public int getSize() { return sound.Sound.getSoundfilesLength(); }
            public Object getElementAt(int i) { return sound.Sound.getSoundFileName(i); }
        });
        sound.Sound.addLineListener(new LineListener() {

            @Override
            public void update(LineEvent event) {
                if(event.getType() == LineEvent.Type.STOP){
                    play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play.png")));
                    if(seekBar.getValue() == seekBar.getMaximum())
                    {
                        sound.Sound.setMicrosecondPosition(0);
                        seekBar.setValue(0);
                    }
                }else if(event.getType() == LineEvent.Type.START)
                    play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pause.png")));
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        seekBar = new graphic.SeekBar();
        play = new javax.swing.JButton();
        next = new javax.swing.JButton();
        back = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        cliplist = new javax.swing.JList();

        play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play.png"))); // NOI18N
        play.setToolTipText("Play");
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });

        next.setText(">>");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        back.setText("<<");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stop.png"))); // NOI18N
        stop.setToolTipText("Stop");

        cliplist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Theme01", "Theme02", "Theme03", "Theme04" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        cliplist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        cliplist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cliplistMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(cliplist);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(seekBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(stop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(play)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(next)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(play)
                    .addComponent(next)
                    .addComponent(back)
                    .addComponent(stop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(seekBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        if(sound.Sound.isPlaying())
        {
            sound.Sound.stopSound();
        }
        else
        {
            if(selIndex == cliplist.getSelectedIndex())
                sound.Sound.startSound();
            else playSelected();
        }
    }//GEN-LAST:event_playActionPerformed

    private void cliplistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cliplistMouseClicked
        if(evt.getClickCount() > 1)
            playSelected();
    }//GEN-LAST:event_cliplistMouseClicked

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        int index = cliplist.getSelectedIndex()+1;
        if(index >= sound.Sound.getSoundfilesLength())
            index = 0;
        cliplist.setSelectedIndex(index);
        skip();
    }//GEN-LAST:event_nextActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        int index = cliplist.getSelectedIndex()-1;
        if(index < 0)
            index = sound.Sound.getSoundfilesLength()-1;
        cliplist.setSelectedIndex(index);
        skip();
    }//GEN-LAST:event_backActionPerformed

    private void skip()
    {
        sound.Sound.stopSound();
        playSelected();
    }
    
    public void playSelected()
    {
        int index = cliplist.getSelectedIndex();
        if(index >=0 && index <= sound.Sound.getSoundfilesLength())
        {
            sound.Sound.playSoundClip(index);
            selIndex = index;
            seekBar.setMaximum((int) (sound.Sound.getMicrosecondLength()/1000000));
        }
    }
    
        @Override
    public void mouseDragged(MouseEvent e) {
        seekBar.setValue(e.getX()*seekBar.getMaximum()/seekBar.getWidth());
        sound.Sound.setMicrosecondPosition(sound.Sound.getMicrosecondLength() * seekBar.getValue() / seekBar.getMaximum());
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        seekBar.setValue(e.getX()*seekBar.getMaximum()/seekBar.getWidth());
        sound.Sound.setMicrosecondPosition(sound.Sound.getMicrosecondLength() * seekBar.getValue() / seekBar.getMaximum());
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        seekBar.setColor(Color.blue);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        seekBar.setColor(Color.cyan);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JList cliplist;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JButton next;
    private javax.swing.JButton play;
    private graphic.SeekBar seekBar;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables
}
