/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import main.GameData;
import utils.Constants;
import utils.Settings;
import utils.Text;

/**
 *
 * @author domin
 */
public class MenuPanel extends MainPanel implements ActionListener {

    enum Menu {

        MAIN, ARCADE, ADVENTURE, OPTIONS;
    }

    private JButton[] saveslotBtns;
    private JButton arcadeBtn, adventureBtn, optionBtn, backBtn;
    private Menu curMenu, prevMenu;

    public MenuPanel() {
        init();
    }

    void setMenu(Menu m) {
        prevMenu = curMenu;
        curMenu = m;
        buildUI();
        MainFrame.FRAME.pack();
    }

    private void init() {
        saveslotBtns = new JButton[Constants.MAX_SAVE_SLOTS];
        for (int i = 0; i < saveslotBtns.length; i++) {
            saveslotBtns[i] = new JButton();
        }
        arcadeBtn = new JButton("Arcade Mode");
        arcadeBtn.setFont(Settings.getSettings().font);
        arcadeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setMenu(Menu.ARCADE);
            }
        });
        adventureBtn = new JButton("Adventure Mode");
        adventureBtn.setFont(Settings.getSettings().font);
        adventureBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setMenu(Menu.ADVENTURE);
            }
        });
        optionBtn = new JButton("Options");
        optionBtn.setFont(Settings.getSettings().font);
        optionBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setMenu(Menu.OPTIONS);
            }
        });
        backBtn = new JButton("Back");
        backBtn.setFont(Settings.getSettings().font);
        backBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setMenu(prevMenu);
            }
        });
        curMenu = Menu.MAIN;
        buildUI();
    }

    private void buildUI() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        switch (curMenu) {
            case ARCADE:
                mainPanel.setLayout(new GridLayout(Constants.MAX_SAVE_SLOTS + 1, 1));
                mainPanel.add(backBtn);
                for (int i = 0; i < saveslotBtns.length; i++) {
                    if (!new java.io.File(Constants.SAVES_DIRECTORY
                            + Constants.SAVEFILE_BASENAME_ARCADE + i + Constants.SAVEFILE_EXTENSION).exists()) {
                        saveslotBtns[i].setText(Text.NEWGAME.toString());
                    } else {
                        saveslotBtns[i].setText(Text.CONTINUE.toString());
                    }
                    saveslotBtns[i].addActionListener(this);
                    saveslotBtns[i].setFont(Settings.getSettings().font);
                    mainPanel.add(saveslotBtns[i]);
                }
                break;
            case ADVENTURE:
                mainPanel.setLayout(new GridLayout(Constants.MAX_SAVE_SLOTS + 1, 1));
                mainPanel.add(backBtn);
                for (int i = 0; i < saveslotBtns.length; i++) {
                    if (!new java.io.File(Constants.SAVES_DIRECTORY
                            + Constants.SAVEFILE_BASENAME_ADVENTURE + i + Constants.SAVEFILE_EXTENSION).exists()) {
                        saveslotBtns[i].setText(Text.NEWGAME.toString());
                    } else {
                        saveslotBtns[i].setText(Text.CONTINUE.toString());
                    }
                    saveslotBtns[i].addActionListener(this);
                    saveslotBtns[i].setFont(Settings.getSettings().font);
                    mainPanel.add(saveslotBtns[i]);
                }
                break;
            case MAIN:
                mainPanel.setLayout(new GridLayout(3, 1));
                mainPanel.add(arcadeBtn);
                mainPanel.add(adventureBtn);
                mainPanel.add(optionBtn);
                break;
            case OPTIONS:
                mainPanel.setLayout(new GridLayout(1, 1));
                mainPanel.add(backBtn);
                break;
        }
        this.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }

    @Override
    public void onSelect() {
        this.requestFocus();
    }

    @Override
    public void onDisselect() {

    }

    @Override
    public void keyPressed(KeyEvent evt) {
        InputConfig.Key key = InputConfig.translateKeyCode(evt.getKeyCode());
        if (key != null) {
            switch (key) {
                case ACCELERATE:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        GameData.Mode mode;
        if (curMenu == Menu.ADVENTURE) {
            mode = GameData.Mode.ADVENTURE;
        } else {
            mode = GameData.Mode.ARCADE;
        }
        for (int i = 0; i < saveslotBtns.length; i++) {
            if (ae.getSource().equals(saveslotBtns[i])) {
                if (!GameGrid.getInstance().load(mode, i)) {
                    GameGrid.reset();
                }
                GameGrid.getInstance().start(mode, i);
                break;
            }
        }
    }
}
