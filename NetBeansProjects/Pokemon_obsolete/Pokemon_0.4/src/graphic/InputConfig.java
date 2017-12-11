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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import spiel.IO;
import spiel.InputListener;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class InputConfig extends javax.swing.JPanel {

    private static int KEY_UP = KeyEvent.VK_UP, KEY_RIGHT = KeyEvent.VK_RIGHT, KEY_DOWN = KeyEvent.VK_DOWN, KEY_LEFT = KeyEvent.VK_LEFT,
            KEY_A = KeyEvent.VK_SPACE, KEY_B = KeyEvent.VK_SHIFT, KEY_SELECT = KeyEvent.VK_CONTROL, KEY_ENTER = KeyEvent.VK_ENTER;
    public static final File CONFIG_FILE = new File("inputConfig.cfg");

    /**
     * Creates new form InputConfig
     */
    public InputConfig() {
        initComponents();
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                hint.setText("Press a Key");
            }
        };
        upbtn.addActionListener(listener);
        rightbtn.addActionListener(listener);
        downbtn.addActionListener(listener);
        leftbtn.addActionListener(listener);
        abtn.addActionListener(listener);
        bbtn.addActionListener(listener);
        selbtn.addActionListener(listener);
        enterbtn.addActionListener(listener);
        paintLabels();
    }

    public static int translateKeyCode(int keycode) {
        if (keycode == KEY_UP) {
            return InputListener.UP;
        } else if (keycode == KEY_RIGHT) {
            return InputListener.RIGHT;
        } else if (keycode == KEY_DOWN) {
            return InputListener.DOWN;
        } else if (keycode == KEY_LEFT) {
            return InputListener.LEFT;
        } else if (keycode == KEY_A) {
            return InputListener.A;
        } else if (keycode == KEY_B) {
            return InputListener.B;
        } else if (keycode == KEY_ENTER) {
            return InputListener.START;
        } else if (keycode == KEY_SELECT) {
            return InputListener.SELECT;
        }
        return -1;
    }

    public static void showInputConfigDialog(Frame parent) {
        javax.swing.JDialog dialog = new javax.swing.JDialog(parent, "Configure Input", true);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().add(new InputConfig(), BorderLayout.CENTER);
        dialog.pack();
        dialog.setVisible(true);
        dialog.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                saveConfig(CONFIG_FILE);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    public static void saveConfig(File f) {
        try {
            FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            int[] keys = new int[]{KEY_UP, KEY_RIGHT, KEY_DOWN, KEY_LEFT, KEY_A, KEY_B, KEY_SELECT, KEY_ENTER};
            oos.writeObject(keys);
            fos.close();
            oos.close();
        } catch (Exception ex) {
            IO.IOMANAGER.println("failed to save Config File", IO.MessageType.ERROR);
        }
    }

    public static void loadConfig(File f) {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            int[] keys = (int[]) ois.readObject();
            fis.close();
            ois.close();
            KEY_UP = keys[0];
            KEY_RIGHT = keys[1];
            KEY_DOWN = keys[2];
            KEY_LEFT = keys[3];
            KEY_A = keys[4];
            KEY_B = keys[5];
            KEY_SELECT = keys[6];
            KEY_ENTER = keys[7];
        } catch (Exception ex) {
            IO.IOMANAGER.println("Cannot load config file", IO.MessageType.ERROR);
        }
    }

    public void paintLabels() {
        upbtn.setText(KeyEvent.getKeyText(KEY_UP));
        rightbtn.setText(KeyEvent.getKeyText(KEY_RIGHT));
        downbtn.setText(KeyEvent.getKeyText(KEY_DOWN));
        leftbtn.setText(KeyEvent.getKeyText(KEY_LEFT));
        abtn.setText(KeyEvent.getKeyText(KEY_A));
        bbtn.setText(KeyEvent.getKeyText(KEY_B));
        selbtn.setText(KeyEvent.getKeyText(KEY_SELECT));
        enterbtn.setText(KeyEvent.getKeyText(KEY_ENTER));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        upbtn = new javax.swing.JButton();
        rightbtn = new javax.swing.JButton();
        downbtn = new javax.swing.JButton();
        leftbtn = new javax.swing.JButton();
        enterbtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        abtn = new javax.swing.JButton();
        bbtn = new javax.swing.JButton();
        selbtn = new javax.swing.JButton();
        hint = new javax.swing.JLabel();

        jLabel1.setText("UP");

        jLabel2.setText("RIGHT");

        jLabel3.setText("DOWN");

        jLabel4.setText("LEFT");

        upbtn.setText("UP");
        upbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                upbtnKeyPressed(evt);
            }
        });

        rightbtn.setText("RIGHT");
        rightbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rightbtnKeyPressed(evt);
            }
        });

        downbtn.setText("DOWN");
        downbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                downbtnKeyPressed(evt);
            }
        });

        leftbtn.setText("LEFT");
        leftbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                leftbtnKeyPressed(evt);
            }
        });

        enterbtn.setText("ENTER");
        enterbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                enterbtnKeyPressed(evt);
            }
        });

        jLabel5.setText("A");

        jLabel6.setText("B");

        jLabel7.setText("SELECT");

        jLabel8.setText("ENTER");

        abtn.setText("SPACE");
        abtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                abtnKeyPressed(evt);
            }
        });

        bbtn.setText("SHIFT LEFT");
        bbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bbtnKeyPressed(evt);
            }
        });

        selbtn.setText("CTRL");
        selbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                selbtnKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(upbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(downbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(leftbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rightbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(abtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(enterbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(hint, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(abtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(bbtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selbtn)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(enterbtn)
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(upbtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(rightbtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(downbtn)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(leftbtn)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hint, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void upbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_upbtnKeyPressed
        KEY_UP = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_upbtnKeyPressed

    private void rightbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rightbtnKeyPressed
        KEY_RIGHT = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_rightbtnKeyPressed

    private void downbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_downbtnKeyPressed
        KEY_DOWN = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_downbtnKeyPressed

    private void leftbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_leftbtnKeyPressed
        KEY_LEFT = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_leftbtnKeyPressed

    private void abtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_abtnKeyPressed
        KEY_A = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_abtnKeyPressed

    private void bbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bbtnKeyPressed
        KEY_B = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_bbtnKeyPressed

    private void selbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selbtnKeyPressed
        KEY_SELECT = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_selbtnKeyPressed

    private void enterbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_enterbtnKeyPressed
        KEY_ENTER = evt.getKeyCode();
        hint.setText("");
        paintLabels();
        this.requestFocus();
    }//GEN-LAST:event_enterbtnKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abtn;
    private javax.swing.JButton bbtn;
    private javax.swing.JButton downbtn;
    private javax.swing.JButton enterbtn;
    private javax.swing.JLabel hint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JButton leftbtn;
    private javax.swing.JButton rightbtn;
    private javax.swing.JButton selbtn;
    private javax.swing.JButton upbtn;
    // End of variables declaration//GEN-END:variables
}
