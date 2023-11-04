/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.Timer;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Game
{

    // Tower size in units
    public static final int TOWER_WIDTH = 120, TOWER_HEIGHT = 120;

    // Trupp movement speed in units per tick
    public static final double TRUPP_SPEED = 7.5;

    public static final Map MAP1 = new Map(512, 700,
            new Tower(175, 125, Team.RED, 10, 10, 100, 20),
            new Tower(50, 300, Team.NONE, 5, 10, 0, 25),
            new Tower(320, 300, Team.NONE, 5, 10, 0, 25),
            new Tower(175, 480, Team.BLUE, 1, 10, 30, 20)
    );

    private static Game instance;

    private Map map;
    private Team playerTeam;
    private CopyOnWriteArrayList<Trupp> trupps;
    private int tickCount = 0, coins = 100;

    private final Timer CLOCK = new Timer(50, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            tick();
        }
    });

    private final TruppListener TL = new TruppListener()
    {
        @Override
        public void arrived(Trupp t)
        {
            truppArrived(t);
        }
    };

    private Game()
    {
        init();
    }

    public static final Game getInstance()
    {
        if (instance == null)
        {
            synchronized (Game.class)
            {
                if (instance == null)
                {
                    instance = new Game();
                }
            }
        }
        return instance;
    }

    public Map getMap()
    {
        return map;
    }

    public Trupp[] getTrupps()
    {
        Trupp[] t = new Trupp[trupps.size()];
        for (int i = 0; i < t.length; i++)
        {
            t[i] = trupps.get(i);
        }
        return t;
    }

    public Team getPlayerTeam()
    {
        return playerTeam;
    }

    public int getCoins()
    {
        return coins;
    }

    private void init()
    {
        System.out.println("Game.init()");
        trupps = new CopyOnWriteArrayList<>();
        map = MAP1;
        playerTeam = Team.BLUE;
    }

    private void tick()
    {
        tickCount++;

        for (Trupp t : trupps)
        {
            t.doStep();
        }

        for (Tower t : map.getTowers())
        {
            t.tick();
            Tower tgt = t.getTarget();
            if (tgt != null && t.spawn())
            {
                Trupp trupp = new Trupp(t, tgt, TRUPP_SPEED);
                trupp.addListener(TL);
                trupps.add(trupp);
                System.out.println("Spawn " + trupp);
            }
        }
    }

    public void setPaused(boolean paused)
    {
        if (paused)
        {
            CLOCK.stop();
        }
        else
        {
            CLOCK.start();
        }
    }

    public boolean isPaused()
    {
        return !CLOCK.isRunning();
    }

    public Tower getTowerAt(int absX, int absY)
    {
        for (Tower t : map.getTowers())
        {
            if (new Rectangle(t.getX(), t.getY(), TOWER_WIDTH, TOWER_HEIGHT).contains(absX, absY))
            {
                return t;
            }
        }
        return null;
    }

    private void truppArrived(Trupp t)
    {
        Team myTeam = t.team;
        Team otherTeam = t.target.getColor();
        if (myTeam == otherTeam)
        {
            t.target.setValue(t.target.getValue() + 1);
        }
        else
        {
            t.target.setValue(t.target.getValue() - 1);
            if (t.target.getValue() == 0)
            {
                t.target.setColor(Team.NONE);
            }
            else if (t.target.getValue() < 0)
            {
                t.target.setValue(1);
                t.target.setColor(myTeam);
            }
        }
        trupps.remove(t);
    }

}
