/*
 * Copyright (C) 2021 Dundun <dominikmesserschmidt@googlemail.com>
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

/**
 *
 * @author Dundun <dominikmesserschmidt@googlemail.com>
 */
public class NotesPanel extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    /**
     *
     * @author Dundun <dominikmesserschmidt@googlemail.com>
     * Created 15.09.2021
     */
    public class Note implements java.io.Serializable {

        final int len, tick, ton;

        Note(int length, int pos, int tonhöhe) {
            len = length;
            tick = pos;
            ton = tonhöhe;
        }
    }

    public static final int LINIEN_ANZ = 60;

    private int lineDistance = 16;
    private int tactWidth = 80;
    private int numTacts = 64;
    private int cursor = 0, ton = 0, mouseX = 0, mouseY = 0, caret = 0;
    private boolean active = false, flag = false;
    private java.util.LinkedList<Note> noten;
    private Note lastNote = null;

    /**
     * Creates new form NewJPanel
     */
    public NotesPanel() {
        noten = new LinkedList<>();
        initComponents();
    }

    void addNote(Note n) {
        noten.add(n);
        flag = true;
        lastNote = n;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new java.awt.Dimension(numTacts * tactWidth, lineDistance * LINIEN_ANZ);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        int max = 60 + LINIEN_ANZ;
        lineDistance = this.getHeight() / LINIEN_ANZ;
        tactWidth = lineDistance * 4;
        if (tactWidth < 80) {
            tactWidth = 80;
        }
        g.setColor(Color.BLUE);
        g.fillRect(caret * tactWidth / 4, 0, 2, getHeight());
        if (active) {
            int x = mouseX * (tactWidth / 4);
            int y = (max - mouseY) * lineDistance / 2 - lineDistance / 2;
            g.fillOval(x, y, lineDistance, lineDistance);
            for (int i = 1; i < cursor - x; i++) {
                g.drawOval(x + i * (tactWidth / 4), y, lineDistance, lineDistance);
            }
            g.drawString("Tick: " + (cursor + 1) + " Tonhöhe: " + ton, x, y);
        }
        g.setColor(Color.RED);
        for (Note n : noten) {
            int x = (n.tick) * (tactWidth / 4);
            int y = (max - n.ton) * lineDistance / 2 - lineDistance / 2;
            g.fillOval(x, y, lineDistance, lineDistance);
            for (int i = 1; i < n.len; i++) {
                g.drawOval(x + i * (tactWidth / 4), y, lineDistance, lineDistance);
            }
        }
        g.setColor(Color.BLACK);
        for (int i = 1; i < LINIEN_ANZ; i++) {
            int y = i * lineDistance;
            g.drawLine(0, y, getWidth(), y);
            char[] text = ((max - i * 2) + "").toCharArray();
            g.drawChars(text, 0, text.length, 0, y);
        }
        for (int i = 1; i < numTacts; i++) {
            int x = i * tactWidth;
            g.drawLine(x, 0, x, getHeight());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (cursor / 4 >= numTacts - 1) {
            numTacts++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        boolean neu = true;
        for (Note n : noten) {
            if (n.tick == cursor && n.ton == ton) {
                noten.remove(n);
                neu = false;
                break;
            }
        }
        if (neu) {
            int tick = 1 + e.getX() / (tactWidth / 4) - cursor;
            addNote(new Note(tick, cursor, ton));
            caret = cursor;
        }
        flag = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        active = true;
        requestFocus();
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        active = false;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cursor = e.getX() / (tactWidth / 4);
        ton = (60 + LINIEN_ANZ) - e.getY() / (lineDistance / 2);
        int neuX = e.getX() / (tactWidth / 4);
        int neuY = (60 + LINIEN_ANZ) - e.getY() / (lineDistance / 2);
        if (neuX != mouseX || neuY != mouseY) {
            mouseX = neuX;
            mouseY = neuY;
            repaint();
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 696, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
