/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Battleships3D;
import static game.Battleships3D.DEFAULT_DEPTH;
import static game.Battleships3D.DEFAULT_HEIGHT;
import static game.Battleships3D.DEFAULT_WIDTH;
import graphic.Battleships3DPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Battleships3D game = new Battleships3D();
        for (int x = 0; x < DEFAULT_WIDTH; x++)
        {
            for (int y = 0; y < DEFAULT_HEIGHT; y++)
            {
                for (int z = 0; z < DEFAULT_DEPTH; z++)
                {
                    System.out.println(game.getCoordinateString(x, y, z));
                }
            }
        }
        JFrame frame = new JFrame("Battleships3D");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(new Battleships3DPanel(game), BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
