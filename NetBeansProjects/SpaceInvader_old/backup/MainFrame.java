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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import sound.Sound;
import utils.Constants;
import utils.IO;
import utils.Settings;
import utils.Text;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class MainFrame extends javax.swing.JFrame implements java.awt.event.WindowListener,
        java.awt.event.ComponentListener {

    public static final MainFrame FRAME = new MainFrame();
    private static final long serialVersionUID = -3096857025952504353L;

    private MainPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuSystem;
    private javax.swing.JMenuItem resetMenuItem;
    private javax.swing.JMenuItem clearLogsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenu menuOptions;
    private javax.swing.JCheckBoxMenuItem loggingEnCheckBox;
    private javax.swing.JCheckBoxMenuItem soundOnCheckBox;
    private javax.swing.JCheckBoxMenuItem confirmExitCheckBox;
    private javax.swing.JCheckBoxMenuItem drawBoundsCheckBox;
    private javax.swing.JCheckBoxMenuItem drawHPBarsCheckBox;
    private javax.swing.JMenuItem inputconfigMenuItem;
    private javax.swing.JMenu menuLanguage;
    private javax.swing.JRadioButtonMenuItem[] languageMenuItems;
    private javax.swing.ButtonGroup langBtnGrp;
    private final java.awt.event.ActionListener langBtnListener = new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            for (int i = 0; i < languageMenuItems.length; i++) {
                if (languageMenuItems[i].equals(arg0.getSource())) {
                    utils.Text.switchLanguage(utils.Constants.Language.values()[i]);
                    Settings.settings.preferredLanguage = utils.Text.getCurrentLanguage();
                    break;
                }
            }
        }
    };

    private MainFrame() {
        super(utils.Constants.APP_NAME);
        try {
            init();
        } catch (Exception ex) {
            IO.printException(ex);
            if (Constants.DEBUG_ENABLE) {
                ex.printStackTrace();
            }
        }
    }

    private void init() {
        this.setLayout(new java.awt.BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        this.addComponentListener(this);

        this.menuBar = new javax.swing.JMenuBar();
        this.menuSystem = new javax.swing.JMenu("System");
        this.resetMenuItem = new javax.swing.JMenuItem("Reset");
        this.resetMenuItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                main.Main.reset();
            }
        });
        this.menuSystem.add(this.resetMenuItem);
        this.clearLogsMenuItem = new javax.swing.JMenuItem("Clear Logs");
        this.clearLogsMenuItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                IO.clearLogs();
            }
        });
        this.menuSystem.add(this.clearLogsMenuItem);
        saveMenuItem = new javax.swing.JMenuItem("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GameGrid.getInstance().save();
            }
        });
        this.menuSystem.add(saveMenuItem);
        this.menuBar.add(menuSystem);

        this.menuOptions = new javax.swing.JMenu("Options");
        this.menuLanguage = new javax.swing.JMenu("Language");
        utils.Constants.Language[] langs = utils.Constants.Language.values();
        this.languageMenuItems = new javax.swing.JRadioButtonMenuItem[langs.length];
        this.langBtnGrp = new javax.swing.ButtonGroup();
        for (int i = 0; i < langs.length; i++) {
            this.languageMenuItems[i] = new javax.swing.JRadioButtonMenuItem(langs[i].toString());
            if (langs[i] == utils.Text.getCurrentLanguage()) {
                this.languageMenuItems[i].setSelected(true);
            }
            this.languageMenuItems[i].addActionListener(langBtnListener);
            this.langBtnGrp.add(this.languageMenuItems[i]);
            this.menuLanguage.add(this.languageMenuItems[i]);
        }
        this.menuOptions.add(this.menuLanguage);

        this.loggingEnCheckBox = new javax.swing.JCheckBoxMenuItem("Logging",
                Settings.settings.loggingEnabled);
        this.loggingEnCheckBox.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Settings.settings.loggingEnabled = loggingEnCheckBox.getState();
                if (!Settings.settings.loggingEnabled) {
                    IO.closeLogs();
                } else {
                    IO.initLogs();
                }
            }
        });
        this.menuOptions.add(this.loggingEnCheckBox);
        this.soundOnCheckBox = new javax.swing.JCheckBoxMenuItem("Sound", Settings.settings.soundOn);
        this.soundOnCheckBox.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Sound.setSoundOn(soundOnCheckBox.getState());
            }
        });
        this.menuOptions.add(this.soundOnCheckBox);
        this.confirmExitCheckBox = new javax.swing.JCheckBoxMenuItem("Confirm Exit",
                Settings.settings.showCloseWarning);
        this.confirmExitCheckBox.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Settings.settings.showCloseWarning = confirmExitCheckBox.getState();
            }
        });
        this.menuOptions.add(this.confirmExitCheckBox);
        drawBoundsCheckBox = new javax.swing.JCheckBoxMenuItem("Draw bounds");
        drawBoundsCheckBox.setState(Settings.settings.drawBounds);
        drawBoundsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Settings.settings.drawBounds = drawBoundsCheckBox.getState();
            }
        });
        menuOptions.add(drawBoundsCheckBox);
        drawHPBarsCheckBox = new javax.swing.JCheckBoxMenuItem("Draw lifebars");
        drawHPBarsCheckBox.setState(Settings.settings.drawLifes);
        drawHPBarsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Settings.settings.drawLifes = drawHPBarsCheckBox.getState();
            }
        });
        menuOptions.add(drawHPBarsCheckBox);
        inputconfigMenuItem = new javax.swing.JMenuItem("Input Config");
        inputconfigMenuItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                InputConfig.showInputConfigDialog(FRAME);
            }
        });
        menuOptions.add(inputconfigMenuItem);
        this.menuBar.add(menuOptions);
        this.setJMenuBar(menuBar);

        this.setLocation(Settings.settings.preferredWindowPosition);
    }

    public void repaintLabels() {
        this.menuOptions.setText(Text.OPTIONS.toString());
        this.confirmExitCheckBox.setText(Text.CONFIRM_EXIT.toString());
        this.menuLanguage.setText(Text.LANGUAGE.toString());
        this.inputconfigMenuItem.setText(Text.INPUTCONFIG.toString());
        this.resetMenuItem.setText(Text.RESET.toString());
        this.clearLogsMenuItem.setText(Text.CLEARLOGS.toString());
    }

    public MainPanel setMainPanel(MainPanel p) {
        MainPanel old = mainPanel;
        if (mainPanel != null) {
            mainPanel.onDisselect();
            this.getContentPane().remove(mainPanel);
        }
        mainPanel = p;
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.onSelect();
        pack();
        return old;
    }

    public void println(String text) {
        mainPanel.addToPrintQueue(text);
    }

    public static void setNimbusLookAndFeel() {
        /* Set the Nimbus look and feel */
 /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return Settings.settings.preferredSize;
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        main.Main.postRun();
        utils.IO.closeLogs();
        Settings.saveSettings();
        System.exit(0);
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        int result = javax.swing.JOptionPane.YES_OPTION;
        if (Settings.settings.showCloseWarning) {
            result = javax.swing.JOptionPane.showConfirmDialog(this, Text.EXIT_QUESTION.toString(),
                    Text.CONFIRM_EXIT.toString(), javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE);
        }
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
    }

    @Override
    public void componentHidden(ComponentEvent arg0) {
    }

    @Override
    public void componentMoved(ComponentEvent arg0) {
        Settings.settings.preferredWindowPosition = this.getLocation();
        // IO.println("Frame moved to Position: " + this.getLocation(), IO.MessageType.DEBUG);
    }

    @Override
    public void componentResized(ComponentEvent arg0) {
        Settings.settings.preferredSize = this.getSize();
        // IO.println("Frame resized: " + this.getSize(), IO.MessageType.DEBUG);
    }

    @Override
    public void componentShown(ComponentEvent arg0) {
    }
}
