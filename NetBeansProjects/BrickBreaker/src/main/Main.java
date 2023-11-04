/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.MainFrame;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Game.getInstance().reset();
        Game.getInstance().initLevel(1);
        MainFrame.start();
        //printResidualReiskorn(14, 0.7);
    }

    public static void printResidualReiskorn(int days, double freshRatio)
    {
        double[] parts = new double[days];

        for (int i = 0; i < days; i++)
        {
            System.out.println("Day " + (i + 1));
            parts[i] = i == 0 ? 1.0 : freshRatio;
            for (int j = i; j >= 0; j--)
            {
                int percentValue = (int) (100 * parts[j]);
                int percentValueAfterComma = (int) (10000 * parts[j] + 0.5) % 100;
                System.out.println((i - j) + "d " + percentValue + "." + percentValueAfterComma + "%");
                parts[j] *= (1.0 - freshRatio);
            }
            System.out.println();
        }
    }

}
