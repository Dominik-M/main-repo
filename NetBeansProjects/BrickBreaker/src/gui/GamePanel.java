/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.Brick;
import main.Game;
import main.Pickup;

/**
 *
 * @author Dominik Messerschmidt
 */
public class GamePanel extends JPanel
{

    private final main.Game game;

    private int fps = 30;
    private boolean leftPressed = false, rightPressed = false, drawBallBounds = false;
    private Image bgImage, pickupImageScore;
    private ImageBuffer brickImgs1, brickImgs2, brickImgs3, brickImgs4, ballImages, paddelImages;
    private final Timer REPAINT_TIMER = new Timer(1000 / fps,
            new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            repaint();
        }
    });

    private final KeyAdapter KEY_HANDLER = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent ke)
        {
            // System.out.println("KeyPressed(): " + ke.getKeyCode());
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_ENTER:
                    game.setRunning(!game.isRunning());
                    break;
                case KeyEvent.VK_LEFT:
                    leftPressed = true;
                    game.setPaddelDirection(Game.Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = true;
                    game.setPaddelDirection(Game.Direction.RIGHT);
                    break;
                case KeyEvent.VK_SPACE:
                    if (game.isBallAtPaddel())
                    {
                        game.launchBall();
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke)
        {
            //System.out.println("KeyReleased(): " + ke.getKeyCode());
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    leftPressed = false;
                    game.setPaddelDirection(rightPressed ? Game.Direction.RIGHT : Game.Direction.NONE);
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = false;
                    game.setPaddelDirection(leftPressed ? Game.Direction.LEFT : Game.Direction.NONE);
                    break;
            }
        }

    };

    public GamePanel()
    {
        System.out.println("create GamePanel");
        initSprites();
        game = main.Game.getInstance();
        setFont(new java.awt.Font("Source Code Pro", 1, 18)); // NOI18N
        setPreferredSize(new Dimension(game.WIDTH, game.HEIGHT));
        this.addKeyListener(KEY_HANDLER);
        REPAINT_TIMER.start();
        requestFocus();
    }

    private void initSprites()
    {
        System.out.println("initSprites");
        try
        {
            bgImage = ImageIO.read(new File("src/images/background.png")).getScaledInstance(Game.WIDTH, Game.HEIGHT, 0);
            pickupImageScore = ImageIO.read(new File("src/images/pickup_score.png")).getScaledInstance(Game.DEFAULT_PICKUP_WIDTH, Game.DEFAULT_PICKUP_HEIGHT, 0);
            ballImages = ImageBuffer.tryLoad(new File("src/images/ball.png"));
            paddelImages = ImageBuffer.tryLoad(new File("src/images/paddel.png"));
            brickImgs1 = ImageBuffer.tryLoad(new File("src/images/brick1.png"));
            brickImgs2 = ImageBuffer.tryLoad(new File("src/images/brick2.png"));
            brickImgs3 = ImageBuffer.tryLoad(new File("src/images/brick3.png"));
            brickImgs4 = ImageBuffer.tryLoad(new File("src/images/brick4.png"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (bgImage != null)
        {
            g.drawImage(bgImage, 0, 0, this);
        }
        else
        {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Brick[] bricks = game.getBricks();
        for (Brick brick : bricks)
        {
            Image brickImage = getBrickImage(brick);
            if (brickImage != null)
            {
                g.drawImage(brickImage, brick.getX(), brick.getY(), this);
            }
            else
            {
                g.setColor(new Color(brick.getColor()));
                g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                g.setColor(Color.BLACK);
                g.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }

        for (Pickup pickup : game.getPickups())
        {
            Rectangle pickupBounds = new Rectangle((int) pickup.getPosition().x, (int) pickup.getPosition().y, Game.DEFAULT_PICKUP_WIDTH, Game.DEFAULT_PICKUP_HEIGHT);
            Image image = getPickupImage(pickup);
            if (image != null)
            {
                g.drawImage(image, pickupBounds.x, pickupBounds.y, this);
            }
            else
            {
                g.setColor(Color.CYAN);
                g.fillOval(pickupBounds.x, pickupBounds.y, pickupBounds.width, pickupBounds.height);
            }
        }

        Rectangle paddel = game.getPaddel();
        if (paddel != null)
        {
            if (paddelImages != null)
            {
                g.drawImage(paddelImages.get(paddel.width, paddel.height).IMAGE, paddel.x, paddel.y, this);
            }
            else
            {
                g.setColor(Color.BLUE);
                g.fillRoundRect(paddel.x, paddel.y, paddel.width, paddel.height, paddel.width, paddel.height);
            }
        }

        if (game != null && game.getBall() != null)
        {
            Rectangle ballBounds = game.getBall().getBounds();
            if (ballImages != null)
            {
                g.drawImage(ballImages.get(ballBounds.width, ballBounds.height).IMAGE, ballBounds.x, ballBounds.y, this);
            }
            else
            {
                g.setColor(Color.yellow);
                g.fillOval(ballBounds.x, ballBounds.y, ballBounds.width, ballBounds.height);
            }
            if (drawBallBounds)
            {
                g.setColor(Color.red);
                g.drawRect(ballBounds.x, ballBounds.y, ballBounds.width, ballBounds.height);
            }
        }

        if (game.isBallAtPaddel())
        {
            String timetext = game.getTimeToLaunch() / 1000 + "." + (game.getTimeToLaunch() % 1000) / 100;
            g.setColor(Color.ORANGE);
            g.setFont(getFont().deriveFont(14.5f));
            g.drawString("Press SPACE to launch", 10, getHeight() - 50);
            g.drawString("Autolaunch in " + timetext, 10, getHeight() - 30);
        }

        g.setColor(Color.ORANGE);
        g.setFont(getFont().deriveFont(14.5f));
        g.drawString("Level: " + game.getLevel(), 5, 10);
        g.drawString("Score: " + game.getScore(), 5, 25);
        g.drawString("Lifes: " + game.getLifes(), 5, 40);

        if (game.isGameOver())
        {
            g.setColor(Color.ORANGE);
            g.setFont(getFont().deriveFont(27.5f));
            g.drawString("GAME OVER", getWidth() / 2 - 40, getHeight() / 2);
        }
        else if (!game.isRunning())
        {
            g.setColor(Color.ORANGE);
            g.setFont(getFont().deriveFont(27.5f));
            g.drawString("PAUSA", getWidth() / 2 - 40, getHeight() / 2);
        }
    }

    private Image getBrickImage(Brick brick)
    {
        int color = brick.getColor();
        if (color <= 0xFF && brickImgs1 != null)
        {
            return brickImgs1.get(brick.getWidth(), brick.getHeight()).IMAGE;
        }
        else if (color <= 0xFF00 && brickImgs2 != null)
        {
            return brickImgs2.get(brick.getWidth(), brick.getHeight()).IMAGE;
        }
        else if (color <= 0xFF0000 && brickImgs3 != null)
        {
            return brickImgs3.get(brick.getWidth(), brick.getHeight()).IMAGE;
        }
        else if (brickImgs4 != null)
        {
            return brickImgs4.get(brick.getWidth(), brick.getHeight()).IMAGE;
        }
        return null;
    }

    private Image getPickupImage(Pickup pickup)
    {
        switch (pickup.type)
        {
            case EXTRA_SCORE:
                return pickupImageScore;
            default:
                return null;
        }
    }

}
