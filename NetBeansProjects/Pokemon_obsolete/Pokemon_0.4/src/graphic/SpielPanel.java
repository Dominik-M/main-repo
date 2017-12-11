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

import image.ImageIO.ImageFile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import spiel.IO;
import spiel.InputListener;
import spiel.Spielwelt;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class SpielPanel extends MainPanel {

    public static final int FPS = 50;
    private Spielwelt welt;
    public static final int HOCH = 13, BREIT = 13;
    private final Timer paintTimer;
    private final MenuPanel menu;
    private BufferedImage gui;
    private TextPanel output;

    /**
     * Creates new form SpielPanel.
     *
     * @param spielwelt current Spielwelt
     */
    public SpielPanel(Spielwelt spielwelt) {
        initComponents();
        welt = spielwelt;
        menu = new MenuPanel("Pokemon", welt.getSpieler().toString(), "Save", "Cancel") {
            @Override
            public void onSelect() {
                switch (getSelectedIndex()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        Spielwelt.save(Spielwelt.FILENAME);
                        break;
                    case 3:
                        setMenuOpen(false);
                        break;
                }
            }
        };
        menu.setVisible(false);
        paintTimer = new Timer(1000 / FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                repaint();
            }
        });
        setMinimumSize(new Dimension(BREIT * 16, HOCH * 16));
    }

    public void setPaused(boolean paused) {
        if (paused && isRunning()) {
            paintTimer.stop();
        } else if (!paused && !isRunning()) {
            paintTimer.start();
        }
    }

    public void setMenuOpen(boolean open) {
        if (open != menu.isVisible()) {
            if (open) {
                if (IO.IOMANAGER.isPrinting()) {
                    return;
                }
                IO.IOMANAGER.removeInputListener(welt);
            } else {
                IO.IOMANAGER.addInputListener(welt);
            }
            menu.setVisible(open);
        }
    }

    public boolean isRunning() {
        return paintTimer.isRunning();
    }

    @Override
    public void paintComponent(Graphics g) {
        createGUI();
        g.drawImage(gui.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0), 0, 0, this);
    }

    public void createGUI() {
        gui = new BufferedImage(BREIT * 16, HOCH * 16, BufferedImage.TYPE_INT_ARGB);
        Graphics g = gui.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        int x0 = welt.getSpieler().getX() - BREIT / 2, y0 = welt.getSpieler().getY() - HOCH / 2;
        if (x0 + BREIT >= welt.getOrt().WIDTH) {
            x0 = welt.getOrt().WIDTH - BREIT;
        }
        if (x0 < 0) {
            x0 = 0;
        }
        if (y0 + HOCH >= welt.getOrt().HEIGHT) {
            y0 = welt.getOrt().HEIGHT - HOCH;
        }
        if (y0 < 0) {
            y0 = 0;
        }
        int xOnScreen = 0, yOnScreen;
        for (int x = x0; x <= BREIT + x0; x++) {
            yOnScreen = 0;
            ImageFile img = null;
            for (int y = y0; y <= HOCH + y0; y++) {
                // draw background
                img = welt.getOrt().getBackground().getImageFile();
                welt.getOrt().getBackground().draw(g.create(xOnScreen, yOnScreen, img.getImgWidth(), img.getImgHeight()));
                // draw object
                img = welt.getOrt().get(x, y).getImageFile();
                welt.getOrt().get(x, y).draw(g.create(xOnScreen, yOnScreen, img.getImgWidth(), img.getImgHeight()));
                yOnScreen += img.getImgHeight();
            }
            if (img != null) {
                xOnScreen += img.getImgWidth();
            }
        }
        ImageFile img = ImageFile.TRAINER_DOWN;
        if (welt.getSpieler().getDirection() == InputListener.UP) {
            img = ImageFile.TRAINER_UP;
        } else if (welt.getSpieler().getDirection() == InputListener.RIGHT) {
            img = ImageFile.TRAINER_RIGHT;
        } else if (welt.getSpieler().getDirection() == InputListener.LEFT) {
            img = ImageFile.TRAINER_LEFT;
        }
        xOnScreen = (welt.getSpieler().getX() - x0) * img.getImgWidth();
        yOnScreen = (welt.getSpieler().getY() - y0) * img.getImgHeight();
        img.draw(g.create(xOnScreen, yOnScreen, img.getImgWidth(), img.getImgHeight()));
        if (menu.isVisible()) {
            menu.draw(g.create(2 * getWidth() / 3, 0, getWidth() / 3, getHeight()));
        }
        if (output != null && output.isPrinting()) {
            output.paintComponent(g.create(0, 2 * getHeight() / 3, getWidth(), getHeight() / 3));
        }
    }

    public void setSpielwelt(Spielwelt s) {
        welt = s;
    }

    public void scale(int width, int height) {
//        int hSpot = width / BREIT;
//        int vSpot = height / HOCH;
//      scale Objekts
//        Objekt[][] objekte = welt.getOrt().getObjekte();
//        for (int x = 0; x < objekte.length; x++) {
//            for (int y = 0; y < objekte[x].length; y++) {
//                objekte[x][y].scale(hSpot, vSpot);
//            }
//        }
        // scale images
//        ImageFile[] images = ImageFile.values();
//        for (ImageFile img : images) {
//            img.scale(hSpot, vSpot);
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        scale(getWidth(), getHeight());
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    protected void onSelect() {
        setPaused(false);
        welt.setPaused(false);
        IO.IOMANAGER.addInputListener(welt);
    }

    @Override
    protected void onDisselect() {
        setPaused(true);
        welt.setPaused(true);
        IO.IOMANAGER.removeInputListener(welt);
    }

    @Override
    public void ButtonPressed(int button) {
        if (button == START) {
            setMenuOpen(!menu.isVisible());
            repaint();
        } else if (button == B) {
            if (menu.isVisible()) {
                setMenuOpen(false);
            }
        } else if (button == A) {
            if (menu.isVisible()) {
                menu.onSelect();
            }
        } else if (button == UP) {
            if (menu.isVisible()) {
                menu.setSelectedIndex(menu.getSelectedIndex() - 1);
            }
        } else if (button == DOWN) {
            if (menu.isVisible()) {
                menu.setSelectedIndex(menu.getSelectedIndex() + 1);
            }
        }
    }

    @Override
    public void ButtonReleased(int button) {
    }

    public TextPanel getOutput() {
        return output;
    }

    public void setOutput(TextPanel output) {
        this.output = output;
    }

}
