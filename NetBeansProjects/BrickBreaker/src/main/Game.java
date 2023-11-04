/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Timer;
import util.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Game
{

    public enum Direction
    {
        NONE,
        LEFT,
        RIGHT
    }

    public static final int WIDTH = 600, HEIGHT = 400, DEFAULT_BALL_SIZE = 8,
            DEFAULT_PADDEL_WIDTH = 64,
            DEFAULT_PADDEL_HEIGHT = 12,
            DEFAULT_PICKUP_WIDTH = 32,
            DEFAULT_PICKUP_HEIGHT = 32,
            DEFAULT_PADDEL_SPEED = 200,
            DEFAULT_TIME_TO_LAUNCH = 3000,
            START_LIFES = 3,
            PADDEL_HIT_SCORE = 1,
            BRICK_HIT_SCORE = 3,
            BRICK_DESTROY_SCORE = 10,
            LEVEL_CLEAR_BASE_SCORE = 50,
            EXTRA_SCORE = 100,
            PICKUP_SPAWN_CHANCE = 75,
            DEFAULT_PICKUP_LIFETIME = 6000;

    public static final double BALL_X_SPEED_FACTOR = 4.5;

    public static final Vector2D DEFAULT_BALL_SPEED = new Vector2D(50, -200),
            DEFAULT_PICKUP_SPEED = new Vector2D(0, 80);

    private static Game instance;

    private final Ball ball;
    private final LinkedList<Brick> bricks;
    private final LinkedList<Pickup> pickups;

    private Rectangle paddel;
    private int paddelSpeed;
    private Direction paddelDirection;
    private int level, timeToLaunch, lifes, score;
    private boolean ballAtPaddel, autoLaunch;

    private final int dT = 30;
    private final Timer TIMER = new Timer(dT, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            step();
        }
    });

    private Game()
    {
        System.out.println("create Game");
        ball = new Ball();
        bricks = new LinkedList<>();
        pickups = new LinkedList<>();
        autoLaunch = true;
    }

    /**
     * Main loop of the Game
     */
    public void step()
    {
        if (!isGameOver())
        {
            movePaddel();
            moveBall();
            movePickups();
            cleanup();
            checkLevel();
        }
    }

    /**
     * *****************************************************************
     *
     * Game control functions
     *
     *******************************************************************
     */
    private void movePaddel()
    {
        if (paddelDirection != Direction.NONE)
        {
            boolean applyNewPos = true;
            double dX = paddelSpeed * dT / 1000.0;
            if (paddelDirection == Direction.LEFT)
            {
                dX *= -1;
            }
            Vector2D newPos = new Vector2D(paddel.x + dX, paddel.y);
            // check paddel bounds
            int left = (int) (newPos.x);
            int right = (int) (newPos.x + paddel.width);
            if (left <= 0 || right >= WIDTH)
            {
                // Hit a side
                applyNewPos = false;
            }
            if (applyNewPos)
            {
                paddel.x = (int) newPos.x;
                paddel.y = (int) newPos.y;
            }
        }
    }

    private void moveBall()
    {
        boolean applyNewPos = true;
        Vector2D newPos;

        if (ballAtPaddel)
        {
            newPos = new Vector2D(paddel.x + paddel.width / 2, paddel.y - ball.getRadius());
            if (autoLaunch)
            {
                if (timeToLaunch <= dT)
                {
                    launchBall();
                }
                else
                {
                    timeToLaunch -= dT;
                }
            }
        }
        else
        {
            Rectangle ballBounds = ball.getBounds();
            if (paddel.intersects(ballBounds))
            {
                System.out.println("Ball hit paddel");
                addScore(PADDEL_HIT_SCORE);
                double dX = BALL_X_SPEED_FACTOR * (ball.getPosition().x - (paddel.x + paddel.width / 2));
                ball.setSpeed(new Vector2D(dX, -1 * Math.abs(ball.getSpeed().y)));
            }

            newPos = ball.getPosition().add(ball.getSpeed().mult(dT / 1000.0));
            // check ball bounds
            int left = (int) (newPos.x - ball.getRadius());
            int right = (int) (newPos.x + ball.getRadius());
            int up = (int) (newPos.y - ball.getRadius());
            int down = (int) (newPos.y + ball.getRadius());
            if (left <= 0)
            {
                // Hit left side
                ball.setSpeed(new Vector2D(Math.abs(ball.getSpeed().x), ball.getSpeed().y));
            }
            else if (right >= WIDTH)
            {
                // Hit right side
                ball.setSpeed(new Vector2D(Math.abs(ball.getSpeed().x) * -1, ball.getSpeed().y));
            }
            else if (up <= 0)
            {
                // Hit top
                ball.setSpeed(new Vector2D(ball.getSpeed().x, Math.abs(ball.getSpeed().y)));
            }
            else if (down >= HEIGHT)
            {
                System.out.println("Ball hit bottom");
                lifes--;
                if (lifes <= 0)
                {
                    gameover();
                }
                else
                {
                    resetBall();
                }
                applyNewPos = false;
            }

            // check bricks
            Rectangle newBounds = new Rectangle((int) newPos.x - ball.getRadius(), (int) newPos.y - ball.getRadius(), ball.getRadius() * 2, ball.getRadius() * 2);
            for (Brick brick : bricks)
            {
                Rectangle brickBounds = brick.getBounds();
                if (newBounds.intersects(brickBounds))
                {
                    System.out.println("Ball hit Brick");
                    addScore(BRICK_HIT_SCORE);

                    //Bounce Ball
                    boolean hitFromRight = newBounds.x <= (brickBounds.x + brickBounds.width)
                            && ballBounds.x >= (brickBounds.x + brickBounds.width);
                    boolean hitFromLeft = (newBounds.x + newBounds.width) >= brickBounds.x
                            && (ballBounds.x + ballBounds.width) <= brickBounds.x;
                    boolean hitFromBelow = newBounds.y <= (brickBounds.y + brickBounds.height)
                            && ballBounds.y >= (brickBounds.y + brickBounds.height);
                    boolean hitFromAbove = (newBounds.y + newBounds.height) >= brickBounds.y
                            && (ballBounds.y + ballBounds.height) <= brickBounds.y;

                    if (hitFromLeft && ball.getSpeed().x > 0)
                    {
                        ball.setSpeed(new Vector2D(ball.getSpeed().x * -1, ball.getSpeed().y));
                    }
                    if (hitFromRight && ball.getSpeed().x < 0)
                    {
                        ball.setSpeed(new Vector2D(ball.getSpeed().x * -1, ball.getSpeed().y));
                    }
                    if (hitFromAbove && ball.getSpeed().y > 0)
                    {
                        ball.setSpeed(new Vector2D(ball.getSpeed().x, ball.getSpeed().y * -1));
                    }
                    if (hitFromBelow && ball.getSpeed().y < 0)
                    {
                        ball.setSpeed(new Vector2D(ball.getSpeed().x, ball.getSpeed().y * -1));
                    }

                    //Damage Brick
                    brick.setColor(brick.getColor() >> 8);
                    if (brick.getColor() <= 0x0F)
                    {
                        brick.setColor(0);
                    }

                    // recalculate new pos
                    newPos = ball.getPosition().add(ball.getSpeed().mult(dT / 1000.0));
                }
            }
        }

        if (applyNewPos)
        {
            ball.setPosition(newPos);
        }
    }

    private void movePickups()
    {
        for (Pickup pickup : pickups)
        {
            Vector2D newPos = pickup.getPosition().add(DEFAULT_PICKUP_SPEED.mult(dT / 1000.0));
            pickup.setPosition(newPos);
            Rectangle bounds = new Rectangle((int) newPos.x, (int) newPos.y, DEFAULT_PICKUP_WIDTH, DEFAULT_PICKUP_HEIGHT);
            if (bounds.intersects(paddel) || bounds.intersects(ball.getBounds()))
            {
                System.out.println("Collected pickup " + pickup);
                usePowerup(pickup.type);
                pickup.setLifetime(0);
            }
            int lifetime = pickup.getLifetime();
            if (lifetime <= dT)
            {
                lifetime = 0;
            }
            else
            {
                lifetime -= dT;
            }
            pickup.setLifetime(lifetime);
        }
    }

    private void usePowerup(Pickup.Type type)
    {
        switch (type)
        {
            case EXTRA_SCORE:
                addScore(EXTRA_SCORE);
                break;
            default:
                System.out.println("Game.usePowerup(): Unknown type: " + type);
                break;
        }
    }

    private void cleanup()
    {
        LinkedList<Brick> brokenbricks = new LinkedList<>();
        for (Brick brick : bricks)
        {
            if (brick.getColor() == 0)
            {
                System.out.println("Remove Brick #" + brick.ID);
                addScore(BRICK_DESTROY_SCORE);
                brokenbricks.add(brick);
                if ((Math.random() * 100) < PICKUP_SPAWN_CHANCE)
                {
                    // spawn pickup
                    Pickup pickup = new Pickup(Pickup.Type.EXTRA_SCORE);
                    pickup.setLifetime(DEFAULT_PICKUP_LIFETIME);
                    pickup.setPosition(new Vector2D(brick.getX(), brick.getY()));
                    pickups.add(pickup);
                    System.out.println("Spawned pickup " + pickup);
                }
            }
        }
        bricks.removeAll(brokenbricks);

        LinkedList<Pickup> brokenpickups = new LinkedList<>();
        for (Pickup pickup : pickups)
        {
            if (pickup.getLifetime() <= 0)
            {
                System.out.println("Remove Pickup #" + pickup.ID);
                brokenpickups.add(pickup);
            }
        }
        pickups.removeAll(brokenpickups);
    }

    private void checkLevel()
    {
        if (bricks.isEmpty())
        {
            System.out.println("Level " + level + " cleared");
            addScore(LEVEL_CLEAR_BASE_SCORE * level);
            initLevel(level + 1);
        }
    }

    public void resetBall()
    {
        System.out.println("Reset Ball");
        ballAtPaddel = true;
        ball.setSpeed(Vector2D.NULLVECTOR);
        ball.setRadius(DEFAULT_BALL_SIZE);
        ball.setPosition(new Vector2D(paddel.x + paddel.width / 2, paddel.y - ball.getRadius()));
        timeToLaunch = DEFAULT_TIME_TO_LAUNCH;
    }

    public void launchBall()
    {
        if (ballAtPaddel)
        {
            System.out.println("Launch Ball");
            ballAtPaddel = false;
            timeToLaunch = 0;
            ball.setSpeed(DEFAULT_BALL_SPEED);
        }
        else
        {
            System.out.println("Ball already launched");
        }
    }

    public void resetPaddel()
    {
        System.out.println("Reset Paddel");
        paddel = new Rectangle(WIDTH / 2 - DEFAULT_PADDEL_WIDTH / 2, HEIGHT - DEFAULT_PADDEL_HEIGHT * 2, DEFAULT_PADDEL_WIDTH, DEFAULT_PADDEL_HEIGHT);
        paddelDirection = Direction.NONE;
        paddelSpeed = DEFAULT_PADDEL_SPEED;
    }

    public void reset()
    {
        System.out.println("Game.reset()");
        resetPaddel();
        resetBall();
        bricks.clear();
        pickups.clear();
        level = 0;
        score = 0;
        lifes = START_LIFES;
    }

    public void initLevel(int level)
    {
        this.level = level;
        bricks.clear();
        LevelFactory.spawnBricks(level, bricks);
    }

    public void addScore(int scoreadd)
    {
        System.out.println("addScore(): " + scoreadd);
        score += scoreadd;
    }

    private void gameover()
    {
        System.out.println("Game Over!");
        // something else may happen here?
    }

    /**
     * *****************************************************************
     *
     * Getter & Setter
     *
     *******************************************************************
     */
    /**
     * Get the singleton instance of Game. Creates a new one if not done yet.
     *
     * @return The Game instance
     */
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

    public boolean isRunning()
    {
        return TIMER.isRunning();
    }

    public void setRunning(boolean running)
    {
        if (running)
        {
            System.out.println("Game.setRunning(): Start Timer");
            TIMER.start();
        }
        else
        {
            System.out.println("Game.setRunning(): Stop Timer");
            TIMER.stop();
        }
    }

    public final Ball getBall()
    {
        return ball;
    }

    public final Brick[] getBricks()
    {
        Brick[] brickarray = new Brick[bricks.size()];
        for (int i = 0; i < brickarray.length; i++)
        {
            brickarray[i] = bricks.get(i);
        }
        return brickarray;
    }

    public final Pickup[] getPickups()
    {
        Pickup[] pickupsarray = new Pickup[pickups.size()];
        for (int i = 0; i < pickupsarray.length; i++)
        {
            pickupsarray[i] = pickups.get(i);
        }
        return pickupsarray;
    }

    public Rectangle getPaddel()
    {
        return paddel;
    }

    public Direction getPaddelDirection()
    {
        return paddelDirection;
    }

    public void setPaddelDirection(Direction paddelDirection)
    {
        this.paddelDirection = paddelDirection;
    }

    public boolean isBallAtPaddel()
    {
        return ballAtPaddel;
    }

    public int getTimeToLaunch()
    {
        return timeToLaunch;
    }

    public int getLevel()
    {
        return level;
    }

    public int getScore()
    {
        return score;
    }

    public int getLifes()
    {
        return lifes;
    }

    public boolean isAutoLaunchEnabled()
    {
        return autoLaunch;
    }

    public void setAutoLaunchEnabled(boolean auto)
    {
        autoLaunch = auto;
    }

    public boolean isGameOver()
    {
        return lifes <= 0;
    }

}
