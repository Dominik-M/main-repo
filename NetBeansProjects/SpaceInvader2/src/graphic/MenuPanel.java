/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this platform file, choose Tools | Templates
 * and open the platform in the editor.
 */
package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import main.SpaceInvader;
import platform.graphic.InputConfig.Key;
import platform.graphic.MainFrame;
import platform.graphic.MainPanel;
import platform.utils.IO;
import platform.utils.Settings;

/**
 *
 * @author domin
 */
public class MenuPanel extends MainPanel implements ActionListener
{

    enum Menu
    {

        MAIN,
        ARCADE,
        ADVENTURE,
        OPTIONS;
    }

    private JButton[] saveslotBtns;
    private JButton arcadeBtn, adventureBtn, optionBtn, backBtn;
    private Menu curMenu, prevMenu;
    private int gap = 20;
    private JComponent placeholder, placeholder2, placeholder3, placeholder4;

    public MenuPanel()
    {
        init();
        setAutoScaled(false);
    }

    void setMenu(Menu m)
    {
        prevMenu = curMenu;
        curMenu = m;
        buildUI();
        MainFrame.FRAME.pack();
    }

    private void init()
    {
        placeholder = new JPanel();
        placeholder.setPreferredSize(new Dimension(gap, gap));
        placeholder.setBackground(Color.lightGray);
        placeholder2 = new JPanel();
        placeholder2.setPreferredSize(new Dimension(gap, gap));
        placeholder2.setBackground(Color.lightGray);
        placeholder3 = new JPanel();
        placeholder3.setPreferredSize(new Dimension(gap, gap));
        placeholder3.setBackground(Color.lightGray);
        placeholder4 = new JPanel();
        placeholder4.setPreferredSize(new Dimension(gap, gap));
        placeholder4.setBackground(Color.lightGray);

        saveslotBtns = new JButton[SpaceInvader.MAX_SAVE_SLOTS];
        for (int i = 0; i < saveslotBtns.length; i++)
        {
            saveslotBtns[i] = new JButton();
        }
        arcadeBtn = new JButton("Arcade Mode");
        arcadeBtn.setFont((Font) Settings.get("font"));
        arcadeBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setMenu(Menu.ARCADE);
            }
        });
        adventureBtn = new JButton("Adventure Mode");
        adventureBtn.setFont((Font) Settings.get("font"));
        adventureBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setMenu(Menu.ADVENTURE);
            }
        });
        optionBtn = new JButton("Options");
        optionBtn.setFont((Font) Settings.get("font"));
        optionBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setMenu(Menu.OPTIONS);
            }
        });
        backBtn = new JButton("Back");
        backBtn.setFont((Font) Settings.get("font"));
        backBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setMenu(prevMenu);
            }
        });
        curMenu = Menu.MAIN;
        buildUI();
    }

    private void buildUI()
    {
        this.removeAll();
        this.setLayout(new BorderLayout(50, 50));
        JPanel mainPanel = new JPanel();
        switch (curMenu)
        {
            case ARCADE:
                mainPanel.setLayout(new GridLayout(SpaceInvader.MAX_SAVE_SLOTS + 1, 1, gap, gap));
                mainPanel.add(backBtn);
                for (int i = 0; i < saveslotBtns.length; i++)
                {
                    if (!new java.io.File(SpaceInvader.SAVES_DIRECTORY
                            + SpaceInvader.SAVEFILE_BASENAME_ARCADE + i + SpaceInvader.SAVEFILE_EXTENSION)
                            .exists())
                    {
                        saveslotBtns[i].setText(IO.translate("NEWGAME"));
                    }
                    else
                    {
                        saveslotBtns[i].setText(IO.translate("CONTINUE"));
                    }
                    saveslotBtns[i].addActionListener(this);
                    saveslotBtns[i].setFont((Font) Settings.get("font"));
                    mainPanel.add(saveslotBtns[i]);
                }
                break;
            case ADVENTURE:
                mainPanel.setLayout(new GridLayout(SpaceInvader.MAX_SAVE_SLOTS + 1, 1, gap, gap));
                mainPanel.add(backBtn);
                for (int i = 0; i < saveslotBtns.length; i++)
                {
                    if (!new java.io.File(SpaceInvader.SAVES_DIRECTORY
                            + SpaceInvader.SAVEFILE_BASENAME_ADVENTURE + i
                            + SpaceInvader.SAVEFILE_EXTENSION).exists())
                    {
                        saveslotBtns[i].setText(IO.translate("NEWGAME"));
                    }
                    else
                    {
                        saveslotBtns[i].setText(IO.translate("CONTINUE"));
                    }
                    saveslotBtns[i].addActionListener(this);
                    saveslotBtns[i].setFont((Font) Settings.get("font"));
                    mainPanel.add(saveslotBtns[i]);
                }
                break;
            case MAIN:
                mainPanel.setLayout(new GridLayout(3, 1, gap, gap));
                mainPanel.add(arcadeBtn);
                mainPanel.add(adventureBtn);
                mainPanel.add(optionBtn);
                break;
            case OPTIONS:
                mainPanel.setLayout(new GridLayout(1, 1));
                mainPanel.add(backBtn);
                break;
        }
        mainPanel.setMaximumSize(new Dimension(300, 600));
        this.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        this.add(placeholder, BorderLayout.EAST);
        this.add(placeholder2, BorderLayout.NORTH);
        this.add(placeholder3, BorderLayout.SOUTH);
        this.add(placeholder4, BorderLayout.WEST);
    }

    @Override
    public void onSelect()
    {
        this.requestFocus();
    }

    @Override
    public void onDisselect()
    {

    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        SpaceInvader.Mode mode;
        if (curMenu == Menu.ADVENTURE)
        {
            mode = SpaceInvader.Mode.ADVENTURE;
        }
        else
        {
            mode = SpaceInvader.Mode.ARCADE;
        }
        for (int i = 0; i < saveslotBtns.length; i++)
        {
            if (ae.getSource().equals(saveslotBtns[i]))
            {
                if (!SpaceInvader.load(mode, i))
                {
                    SpaceInvader.getInstance().reset();
                }
                SpaceInvader.getInstance().start(mode, i);
                break;
            }
        }
    }

    @Override
    public void drawGUI(Graphics2D arg0)
    {
    }

    @Override
    public void keyPressed(Key arg0)
    {

    }

    @Override
    public void keyReleased(Key arg0)
    {

    }
}
