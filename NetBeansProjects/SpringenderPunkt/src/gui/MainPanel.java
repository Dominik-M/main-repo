/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import javax.swing.Timer;

/**
 *
 * @author Dominik Messerschmidt
 */
public class MainPanel extends javax.swing.JPanel
{

    public static final int MAX_TRAIL_SIZE = 1024;
    public static final double BOTTOM_BOUNCE_FACTOR = -0.975;

    private final Timer updateTimer = new Timer(10, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (left)
            {
                speedX -= accX;
            }
            if (right)
            {
                speedX += accX;
            }
            if (up)
            {
                speedY -= accX;
            }
            if (down)
            {
                speedY += accX;
            }
            if (brake)
            {
                speedX *= 0.9;
                speedY *= 0.9;
            }

            // move
            pointX += speedX;
            pointY += speedY;

            // check bounds
            if (pointX <= 0)
            {
                pointX = 0;
                speedX = speedX * -1;
            }
            if (pointX >= getWidth() - pointWidth)
            {
                pointX = getWidth() - pointWidth;
                speedX = speedX * -1;
            }
            if (pointY <= 0)
            {
                pointY = 0;
                speedY = speedY * -1;
            }
            else if (pointY >= getHeight() - pointHeight)
            {
                pointY = getHeight() - pointHeight;
                speedY = speedY * BOTTOM_BOUNCE_FACTOR;
            }
            else
            {
                // apply gravity
                speedY += gravity;
            }

            // trail
            if (trail.size() + 1 > MAX_TRAIL_SIZE)
            {
                trail.pop();
            }
            trail.add(new Point((int) pointX, (int) pointY));

            requestFocus();
            repaint();
        }
    });

    private KeyAdapter keyHandler = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent ke)
        {
            System.out.println("KeyPressed(): " + ke.getKeyChar());
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    left = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    break;
                case KeyEvent.VK_UP:
                    up = true;
                    break;
                case KeyEvent.VK_DOWN:
                    down = true;
                    break;
                case KeyEvent.VK_SPACE:
                    brake = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke)
        {
            //System.out.println("KeyReleased(): " + ke.getKeyChar());
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
                case KeyEvent.VK_SPACE:
                    brake = false;
                    break;
            }
        }

    };

    private double pointX = 200, pointY = 150, speedX = 5, speedY = 0, pointWidth = 25, pointHeight = 25, gravity = 0.5, accX = 1, accY = 1;
    private boolean left = false, right = false, up = false, down = false, brake = false;
    private final LinkedList<Point> trail = new LinkedList<>();

    /**
     * Creates new form MainPanel
     */
    public MainPanel()
    {
        initComponents();
        this.addKeyListener(keyHandler);
        updateTimer.start();
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // trail
        g.setColor(Color.BLUE);
        Point lastPoint;
        for (Point p : trail)
        {
            g.fillOval(p.x, p.y, 3, 3);
        }

        // point
        g.setColor(Color.RED);
        g.fillOval((int) pointX, (int) pointY, (int) pointWidth, (int) pointHeight);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
