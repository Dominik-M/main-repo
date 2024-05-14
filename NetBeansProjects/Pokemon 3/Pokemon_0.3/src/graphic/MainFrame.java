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
import utils.Decision;
import utils.DecisionCallback;
import utils.IO;
import utils.Settings;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class MainFrame extends javax.swing.JFrame implements java.awt.event.WindowListener,
        java.awt.event.ComponentListener, utils.SettingsListener
{

    /**
     *
     */
    public static final MainFrame FRAME = new MainFrame();
    private static final long serialVersionUID = -3096857025952504353L;

    private MainPanel mainPanel;
    private ConsolePanel console;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuSystem;
    private javax.swing.JMenuItem resetMenuItem;
    private javax.swing.JMenuItem clearLogsMenuItem;
    private javax.swing.JMenuItem showConsoleMenuItem;
    private javax.swing.JMenu menuOptions;
    private javax.swing.JCheckBoxMenuItem loggingEnCheckBox;
    private javax.swing.JCheckBoxMenuItem soundOnCheckBox;
    private javax.swing.JCheckBoxMenuItem confirmExitCheckBox;
    private javax.swing.JCheckBoxMenuItem drawBoundsCheckBox;
    private javax.swing.JMenuItem settingsBtn;
    private javax.swing.JMenuItem inputconfigMenuItem;
    private javax.swing.JMenu menuLanguage;
    private javax.swing.JRadioButtonMenuItem[] languageMenuItems;
    private javax.swing.ButtonGroup langBtnGrp;
    private final java.awt.event.ActionListener langBtnListener = new java.awt.event.ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            for (int i = 0; i < languageMenuItems.length; i++)
            {
                if (languageMenuItems[i].equals(arg0.getSource()))
                {
                    utils.IO.loadLanguageFile(utils.IO.Language.values()[i]);
                    break;
                }
            }
        }
    };

    private MainFrame()
    {
        super(utils.Constants.APP_NAME);
        try
        {
            init();
        } catch (Exception ex)
        {
            IO.println("failed to initialize Frame", IO.MessageType.ERROR);
            if (Constants.DEBUG_ENABLE)
            {
                IO.printException(ex);
            }
        }
    }

    private void init()
    {
        console = new ConsolePanel();
        console.setVisible(Settings.getSettings().consoleVisible);
        this.setLayout(new java.awt.BorderLayout());
        if (Constants.DEBUG_ENABLE)
        {
            this.getContentPane().add(console, BorderLayout.WEST);
        }
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        this.addComponentListener(this);

        this.menuBar = new javax.swing.JMenuBar();
        this.menuSystem = new javax.swing.JMenu("System");
        this.resetMenuItem = new javax.swing.JMenuItem("Reset");
        this.resetMenuItem.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                main.Main.reset();
            }
        });
        this.menuSystem.add(this.resetMenuItem);
        this.clearLogsMenuItem = new javax.swing.JMenuItem("Clear Logs");
        this.clearLogsMenuItem.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                IO.clearLogs();
            }
        });
        this.menuSystem.add(this.clearLogsMenuItem);
        showConsoleMenuItem = new javax.swing.JMenuItem("Show Console");
        this.showConsoleMenuItem.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                setConsoleEnabled(true);
                Settings s = Settings.getSettings();
                s.consoleVisible = true;
                Settings.setSettings(s);
            }
        });
        if (Constants.DEBUG_ENABLE)
        {
            this.menuSystem.add(showConsoleMenuItem);
        }
        this.menuBar.add(menuSystem);

        this.menuOptions = new javax.swing.JMenu("Options");
        this.menuLanguage = new javax.swing.JMenu("Language");
        utils.IO.Language[] langs = utils.IO.Language.values();
        this.languageMenuItems = new javax.swing.JRadioButtonMenuItem[langs.length];
        this.langBtnGrp = new javax.swing.ButtonGroup();
        for (int i = 0; i < langs.length; i++)
        {
            this.languageMenuItems[i] = new javax.swing.JRadioButtonMenuItem(langs[i].toString());
            if (langs[i] == utils.IO.getCurrentLanguage())
            {
                this.languageMenuItems[i].setSelected(true);
            }
            this.languageMenuItems[i].addActionListener(langBtnListener);
            this.langBtnGrp.add(this.languageMenuItems[i]);
            this.menuLanguage.add(this.languageMenuItems[i]);
        }
        this.menuOptions.add(this.menuLanguage);

        this.loggingEnCheckBox = new javax.swing.JCheckBoxMenuItem("Logging",
                Settings.getSettings().loggingEnabled);
        this.loggingEnCheckBox.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                Settings s = Settings.getSettings();
                s.loggingEnabled = loggingEnCheckBox.getState();
                Settings.setSettings(s);
                if (!Settings.getSettings().loggingEnabled)
                {
                    IO.closeLogs();
                }
                else
                {
                    IO.initLogs();
                }
            }
        });
        this.menuOptions.add(this.loggingEnCheckBox);
        this.soundOnCheckBox = new javax.swing.JCheckBoxMenuItem("Sound", Settings.getSettings().soundOn);
        this.soundOnCheckBox.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                Sound.setSoundOn(soundOnCheckBox.getState());
            }
        });
        this.menuOptions.add(this.soundOnCheckBox);
        this.confirmExitCheckBox = new javax.swing.JCheckBoxMenuItem("Confirm Exit",
                Settings.getSettings().showCloseWarning);
        this.confirmExitCheckBox.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                Settings s = Settings.getSettings();
                s.showCloseWarning = confirmExitCheckBox.getState();
                Settings.setSettings(s);
            }
        });
        this.menuOptions.add(this.confirmExitCheckBox);
        this.drawBoundsCheckBox = new javax.swing.JCheckBoxMenuItem("Draw Bounds",
                Settings.getSettings().drawBounds);
        this.drawBoundsCheckBox.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                Settings s = Settings.getSettings();
                s.drawBounds = drawBoundsCheckBox.getState();
                Settings.setSettings(s);
            }
        });
        this.menuOptions.add(this.drawBoundsCheckBox);
        inputconfigMenuItem = new javax.swing.JMenuItem("Input Config");
        inputconfigMenuItem.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                InputConfig.showInputConfigDialog(FRAME);
            }
        });
        menuOptions.add(inputconfigMenuItem);
        this.settingsBtn = new javax.swing.JMenuItem("Settings...");
        this.settingsBtn.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new SettingsDialog().setVisible(true);
            }
        });
        this.menuOptions.add(this.settingsBtn);
        this.menuBar.add(menuOptions);
        this.setJMenuBar(menuBar);

        this.setLocation(Settings.getSettings().preferredWindowPosition);
        Settings.addSettingsListener(this);
    }

    /**
     *
     */
    public void repaintLabels()
    {
        this.menuOptions.setText(IO.translate("OPTIONS"));
        this.confirmExitCheckBox.setText(IO.translate("CONFIRM_EXIT"));
        this.menuLanguage.setText(IO.translate("LANGUAGE"));
        this.inputconfigMenuItem.setText(IO.translate("INPUTCONFIG"));
        this.resetMenuItem.setText(IO.translate("RESET"));
        this.clearLogsMenuItem.setText(IO.translate("CLEARLOGS"));
    }

    /**
     *
     * @param p
     */
    public void setMainPanel(MainPanel p)
    {
        if (mainPanel != null)
        {
            mainPanel.onDisselect();
            this.getContentPane().remove(mainPanel);
        }
        mainPanel = p;
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.onSelect();
        pack();
    }

    /**
     *
     * @param text
     */
    public void println(String text)
    {
        mainPanel.addToPrintQueue(text);
    }

    public void promptDecision(Decision d)
    {
        mainPanel.promptDecision(d);
    }

    public void promptItemDecision(DecisionCallback callback)
    {
        mainPanel.promptItemDecision(callback);
    }

    /**
     *
     * @param text
     */
    public void writeToConsole(String text)
    {
        console.append(text);
    }

    /**
     *
     * @param enabled
     */
    public void setConsoleEnabled(boolean enabled)
    {
        console.setVisible(enabled);
    }

    /**
     *
     */
    public static void setNimbusLookAndFeel()
    {
        /* Set the Nimbus look and feel */
 /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public java.awt.Dimension getPreferredSize()
    {
        return Settings.getSettings().preferredSize;
    }

    @Override
    public void windowActivated(WindowEvent arg0)
    {
    }

    @Override
    public void windowClosed(WindowEvent arg0)
    {
        utils.IO.closeLogs();
        Settings.saveSettings();
        IO.saveLanguageFile();
        // TODO post-run actions

        System.exit(0);
    }

    @Override
    public void windowClosing(WindowEvent arg0)
    {
        int result = javax.swing.JOptionPane.YES_OPTION;
        if (Settings.getSettings().showCloseWarning)
        {
            result = javax.swing.JOptionPane.showConfirmDialog(this, IO.translate("EXIT_QUESTION"),
                    IO.translate("CONFIRM_EXIT"), javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE);
        }
        if (result == javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent arg0)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0)
    {
    }

    @Override
    public void windowIconified(WindowEvent arg0)
    {
    }

    @Override
    public void windowOpened(WindowEvent arg0)
    {
    }

    @Override
    public void componentHidden(ComponentEvent arg0)
    {
    }

    @Override
    public void componentMoved(ComponentEvent arg0)
    {
        Settings s = Settings.getSettings();
        s.preferredWindowPosition = this.getLocation();
        Settings.setSettings(s);
        // IO.println("Frame moved to Position: " + this.getLocation(), IO.MessageType.DEBUG);
    }

    @Override
    public void componentResized(ComponentEvent arg0)
    {
        Settings s = Settings.getSettings();
        s.preferredSize = this.getSize();
        Settings.setSettings(s);
        // IO.println("Frame resized: " + this.getSize(), IO.MessageType.DEBUG);
    }

    @Override
    public void componentShown(ComponentEvent arg0)
    {
    }

    /**
     *
     */
    @Override
    public void settingsChanged()
    {
        loggingEnCheckBox.setState(Settings.getSettings().loggingEnabled);
        soundOnCheckBox.setState(Settings.getSettings().soundOn);
        confirmExitCheckBox.setState(Settings.getSettings().showCloseWarning);
        drawBoundsCheckBox.setState(Settings.getSettings().drawBounds);
        this.setLocation(Settings.getSettings().preferredWindowPosition);
        pack();
    }
}
