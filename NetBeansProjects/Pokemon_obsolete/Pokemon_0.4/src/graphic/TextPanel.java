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
import java.awt.Graphics;
import java.awt.Rectangle;
import spiel.Queue;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class TextPanel extends javax.swing.JPanel {

    private int lines, charsPerLine;
    private final Queue<String> textlines;
    private String[] text;
    public static final String NEWLINE = "\n";

    /**
     * Creates new form TextPanel
     */
    public TextPanel() {
        initComponents();
        lines = 2;
        charsPerLine = 25;
        textlines = new Queue();
        text = new String[lines];
    }

    public boolean isPrinting() {
        return text.length > 0 && text[0] != null;
    }

    public void print(String txt) {
        String[] words = txt.split(" ");
        String line = "";
        int counter = 0;
        for (int i = 0; i < words.length; i++) {
            if (counter + words[i].length() >= charsPerLine) {
                textlines.add(line);
                line = "";
                counter = 0;
            } else if (words[i].equals(NEWLINE)) {
                counter = charsPerLine;
                continue;
            }
            line += words[i] + " ";
            counter += words[i].length();
        }
        if (counter != 0) {
            textlines.add(line);
        }
        if (!isPrinting()) {
            printNext();
        }
    }

    public boolean printNext() {
        for (int i = 0; i < lines; i++) {
            text[i] = textlines.pull();
        }
        return !isPrinting();
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int linesNeu) {
        lines = linesNeu;
        text = new String[lines];
    }

    public int getCharsPerLine() {
        return charsPerLine;
    }

    public void setCharsPerLine(int charsPerLineNeu) {
        charsPerLine = charsPerLineNeu;
    }

    @Override
    public void paintComponent(Graphics g) {
        Rectangle bounds = g.getClipBounds();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, bounds.width, bounds.height);
        g.setColor(Color.BLACK);
        int size = bounds.height / lines;
        double scale = 0.5;
        if (scale * bounds.width / charsPerLine < size) {
            size = (int) (scale * bounds.width / charsPerLine);
        }
        int offset = size - 2;
        g.setFont(new java.awt.Font(spiel.Konstanten.DEFAULT_FONT_NAME, 0, size));
        for (int i = 0; i < lines; i++) {
            if (text[i] != null) {
                g.drawString(text[i], 0, offset);
            }
            offset += size;
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
            .addGap(0, 299, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}