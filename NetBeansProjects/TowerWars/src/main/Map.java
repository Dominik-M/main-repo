/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Map
{

    public final int WIDTH, HEIGHT;
    private final LinkedList<Tower> TOWERS = new LinkedList<>();

    public Map(int w, int h, Tower... towers)
    {
        WIDTH = w;
        HEIGHT = h;
        for (Tower t : towers)
        {
            TOWERS.add(t);
        }
    }

    public Tower[] getTowers()
    {
        Tower[] towers = new Tower[TOWERS.size()];
        for (int i = 0; i < towers.length; i++)
        {
            towers[i] = TOWERS.get(i);
        }
        return towers;
    }

}
