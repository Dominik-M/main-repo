/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.LinkedList;
import static main.Game.WIDTH;

/**
 *
 * @author Dominik Messerschmidt
 */
public class LevelFactory
{

    public static void spawnBricks(int level, LinkedList<Brick> bricks)
    {
        if (bricks == null)
        {
            System.err.println("LevelFactory.spawnBricks(): Nullpointer exception");
        }
        else
        {
            switch (level)
            {
                case 1:
                    //spawnBricksLevelOne(bricks);
                    break;
                case 2:
                    //spawnBricksLevelTwo(bricks);
                    break;
                default:
                    System.err.println("LevelFactory.spawnBricks(): Level " + level + " not defined.");
                    break;
            }
            addARandomSimpleBrick(bricks);
        }
    }

    public static void addARandomSimpleBrick(LinkedList<Brick> bricks)
    {
        int seed = (int) (Math.random() * 0xFFFF);
        int x = seed & 0xFF + 64;
        int y = (seed & 0xFF00) >> 9;
        Brick brick = new Brick();
        brick.setColor(seed);
        brick.setX(x);
        brick.setY(y);
        brick.setWidth(100);
        brick.setHeight(40);
        bricks.add(brick);
    }

    public static void spawnBricksLevelOne(LinkedList<Brick> bricks)
    {
        int rows = 3, cols = 5;
        int width = WIDTH / cols, height = 32;
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                Brick brick = new Brick();
                brick.setColor(0xFF << (row * 8));
                brick.setX(col * width);
                brick.setY(row * height);
                brick.setWidth(width);
                brick.setHeight(height);
                bricks.add(brick);
            }
        }
    }

    public static void spawnBricksLevelTwo(LinkedList<Brick> bricks)
    {
        int rows = 5, cols = 20;
        int width = WIDTH / cols, height = 16;
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                Brick brick = new Brick();
                brick.setColor(0xFF << ((row % 3) * 8));
                brick.setX(col * width);
                brick.setY(row * height);
                brick.setWidth(width);
                brick.setHeight(height);
                bricks.add(brick);
            }
        }
    }
}
