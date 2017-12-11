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
package platform.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import platform.Interface;
import platform.sound.Sound;
import platform.utils.IO;
import platform.utils.Settings;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class MainFrame extends JFrame implements java.awt.event.WindowListener,
        java.awt.event.ComponentListener, platform.utils.SettingsListener {

    public static final MainFrame FRAME = new MainFrame();
    private static final long serialVersionUID = -3096857025952504353L;

    private MainPanel mainPanel;
    private ConsolePanel console;
    private JMenuBar menuBar;
    private JMenu menuSystem;
    private JMenuItem resetMenuItem;
    private JMenuItem clearLogsMenuItem;
    private JMenuItem showConsoleMenuItem;
    private JMenu menuOptions;
    private JCheckBoxMenuItem loggingEnCheckBox;
    private JCheckBoxMenuItem soundOnCheckBox;
    private JCheckBoxMenuItem confirmExitCheckBox;
    private JMenuItem settingsBtn;
    private JMenuItem inputconfigMenuItem;
    private JMenu menuLanguage;
    private JRadioButtonMenuItem[] languageMenuItems;
    private ButtonGroup langBtnGrp;
    private final java.awt.event.ActionListener langBtnListener = new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            for (int i = 0; i < languageMenuItems.length; i++) {
                if (languageMenuItems[i].equals(arg0.getSource())) {
                    IO.loadLanguageFile(IO.Language.values()[i]);
                    setSettingIgnoreCallback("preferredLanguage", IO.getCurrentLanguage());
                    break;
                }
            }
        }
    };

    private MainFrame() {
        super(IO.translate("APP_NAME"));
        try {
            init();
        } catch (Exception ex) {
            IO.printException(ex);
        }
    }

    private void init() {
        console = new ConsolePanel();
        this.setLayout(new java.awt.BorderLayout());
        this.getContentPane().add(console, BorderLayout.WEST);
        setConsoleEnabled((Boolean) Settings.get("showConsole"));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        this.addComponentListener(this);

        this.menuBar = new JMenuBar();
        this.menuSystem = new JMenu("System");
        this.resetMenuItem = new JMenuItem("Reset");
        this.resetMenuItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Interface.reset();
            }
        });
        this.menuSystem.add(this.resetMenuItem);
        this.clearLogsMenuItem = new JMenuItem("Clear Logs");
        this.clearLogsMenuItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                IO.clearLogs();
            }
        });
        this.menuSystem.add(this.clearLogsMenuItem);
        showConsoleMenuItem = new JMenuItem("Show Console");
        this.showConsoleMenuItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                setConsoleEnabled(true);
            }
        });
        this.menuSystem.add(showConsoleMenuItem);
        this.menuBar.add(menuSystem);

        this.menuOptions = new JMenu("Options");
        this.menuLanguage = new JMenu("Language");
        IO.Language[] langs = IO.Language.values();
        this.languageMenuItems = new JRadioButtonMenuItem[langs.length];
        this.langBtnGrp = new ButtonGroup();
        for (int i = 0; i < langs.length; i++) {
            this.languageMenuItems[i] = new JRadioButtonMenuItem(langs[i].toString());
            if (langs[i] == IO.getCurrentLanguage()) {
                this.languageMenuItems[i].setSelected(true);
            }
            this.languageMenuItems[i].addActionListener(langBtnListener);
            this.langBtnGrp.add(this.languageMenuItems[i]);
            this.menuLanguage.add(this.languageMenuItems[i]);
        }
        this.menuOptions.add(this.menuLanguage);

        this.loggingEnCheckBox = new JCheckBoxMenuItem("Logging",
                (Boolean) Settings.get("loggingEnabled"));
        this.loggingEnCheckBox.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                setSettingIgnoreCallback("loggingEnabled", loggingEnCheckBox.getState());
                if (!(Boolean) Settings.get("loggingEnabled")) {
                    IO.closeLogs();
                } else {
                    IO.initLogs();
                }
            }
        });
        this.menuOptions.add(this.loggingEnCheckBox);
        this.soundOnCheckBox = new JCheckBoxMenuItem("Sound",
                (Boolean) Settings.get("soundOn"));
        this.soundOnCheckBox.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Sound.setSoundOn(soundOnCheckBox.getState());
            }
        });
        this.menuOptions.add(this.soundOnCheckBox);
        this.confirmExitCheckBox = new JCheckBoxMenuItem("Confirm Exit",
                (Boolean) Settings.get("showCloseWarning"));
        this.confirmExitCheckBox.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                setSettingIgnoreCallback("showCloseWarning", confirmExitCheckBox.getState());
            }
        });
        this.menuOptions.add(this.confirmExitCheckBox);
        inputconfigMenuItem = new JMenuItem("Input Config");
        inputconfigMenuItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                InputConfig.showInputConfigDialog(FRAME);
            }
        });
        menuOptions.add(inputconfigMenuItem);
        this.settingsBtn = new JMenuItem("Settings...");
        this.settingsBtn.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new SettingsDialog().setVisible(true);
            }
        });
        this.menuOptions.add(this.settingsBtn);
        this.menuBar.add(menuOptions);
        this.setJMenuBar(menuBar);

        this.setLocation((Point) Settings.get("preferredWindowPosition"));
        Settings.addListener(this);
        paintLabels();
    }

    public MainPanel setMainPanel(MainPanel p) {
        MainPanel old = mainPanel;
        if (mainPanel != null) {
            mainPanel.onDisselect();
            this.getContentPane().remove(mainPanel);
        }
        mainPanel = p;
        if (mainPanel != null) {
            this.getContentPane().add(mainPanel, BorderLayout.CENTER);
            mainPanel.onSelect();
        }
        pack();
        repaint();
        return old;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
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
            for (UIManager.LookAndFeelInfo info : UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
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
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void writeToConsole(String text) {
        console.append(text);
    }

    public void setConsoleEnabled(boolean enabled) {
        console.setVisible(enabled);
        setSettingIgnoreCallback("showConsole", enabled);
    }

    public void setSettingIgnoreCallback(String key, Object value) {
        Settings.removeListener(this);
        Settings.set(key, value);
        Settings.addListener(this);
    }

    public void paintLabels() {
        menuSystem.setText(IO.translate("SYSTEM"));
        resetMenuItem.setText(IO.translate("RESET"));
        clearLogsMenuItem.setText(IO.translate("CLEAR_LOGS"));
        showConsoleMenuItem.setText(IO.translate("SHOW_CONSOLE"));
        menuOptions.setText(IO.translate("OPTIONS"));
        loggingEnCheckBox.setText(IO.translate("LOGGING"));
        soundOnCheckBox.setText(IO.translate("SOUND"));
        confirmExitCheckBox.setText(IO.translate("CONFIRM_EXIT"));
        settingsBtn.setText(IO.translate("SETTINGS"));
        inputconfigMenuItem.setText(IO.translate("INPUT_CONFIG"));
        menuLanguage.setText(IO.translate("LANGUAGE"));
    }

    public JMenu getSystemMenu() {
        return menuSystem;
    }

    public JMenu getOptionsMenu() {
        return menuOptions;
    }

    @Override
    public Dimension getPreferredSize() {
        return (Dimension) Settings.get("preferredSize");
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        Interface.shutdown();
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        int result = JOptionPane.YES_OPTION;
        if ((Boolean) Settings.get("showCloseWarning")) {
            result = JOptionPane.showConfirmDialog(this, IO.translate("EXIT_QUESTION"),
                    IO.translate("CONFIRM_EXIT"), JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
        }
        if (result == JOptionPane.YES_OPTION) {
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
        setSettingIgnoreCallback("preferredWindowPosition", this.getLocation());
        // IO.println("Frame moved to Position: " + this.getLocation(), IO.MessageType.DEBUG);
    }

    @Override
    public void componentResized(ComponentEvent arg0) {
        setSettingIgnoreCallback("preferredSize", this.getSize());
        // IO.println("Frame resized: " + this.getSize(), IO.MessageType.DEBUG);
    }

    @Override
    public void componentShown(ComponentEvent arg0) {
    }

    @Override
    public void preferenceChanged(String key, Object value) {
        switch (key) {
            case "preferredLanguage":
                paintLabels();
                break;
            case "showConsole":
                console.setVisible((Boolean) value);
                break;
            case "loggingEnabled":
                loggingEnCheckBox.setState((Boolean) Settings.get("loggingEnabled"));
                break;
            case "showCloseWarning":
                confirmExitCheckBox.setState((Boolean) Settings.get("showCloseWarning"));
                break;
            case "soundOn":
                soundOnCheckBox.setState((Boolean) Settings.get("soundOn"));
                break;
            case "preferredWindowPosition":
                this.setLocation((Point) Settings.get("preferredWindowPosition"));
                break;
            case "preferredSize":
                pack();
                break;
        }
    }
}
