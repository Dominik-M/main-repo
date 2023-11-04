/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import main.Game;
import main.Team;
import main.Tower;
import main.Trupp;
import main.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 */
public class GamePanel extends javax.swing.JPanel
{

    public static final int BG_WIDTH = 512, BG_HEIGHT = 700, TRUPP_IMG_WIDTH = 32, TRUPP_IMG_HEIGHT = 32;

    private Image bgImage, towerImageNone, towerImageBlue, towerImageRed, truppImage, markerImage, coinImage;
    private int viewX, viewY, mouseX, mouseY, dragX, dragY;
    private boolean dragging, editMode, paused;
    private Tower selected;

    private final Timer CLOCK = new Timer(50, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            repaint();
        }
    });

    private final MouseAdapter mouseHandler = new MouseAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent me)
        {
            //System.out.println("mouseMoved");
            mouseX = me.getX();
            mouseY = me.getY();
        }

        @Override
        public void mouseDragged(MouseEvent me)
        {
            //System.out.println("mouseDragged");
            mouseX = me.getX();
            mouseY = me.getY();
            if (dragging)
            {
                viewX -= (dragX - me.getX());
                viewY -= (dragY - me.getY());
                dragX = me.getX();
                dragY = me.getY();
            }
        }

        @Override
        public void mousePressed(MouseEvent me)
        {
            if (me.getButton() == MouseEvent.BUTTON3) // Right click
            {
                dragging = true;
                dragX = me.getX();
                dragY = me.getY();
            }
            else if (me.getButton() == MouseEvent.BUTTON1) // Left click
            {
                int absX = me.getX() - viewX;
                int absY = me.getY() - viewY;
                Tower t = Game.getInstance().getTowerAt(absX, absY);
                if (t != null)
                {
                    System.out.println("Selected " + t);
                    if (selected != null)
                    {
                        System.out.println("Set target of " + selected + " to " + t);
                        selected.setTarget(t);
                        selected = null;
                    }
                    else
                    {
                        selected = t;
                    }
                }
                else
                {
                    System.out.println("Unselect");
                    selected = null;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me)
        {
            if (me.getButton() == MouseEvent.BUTTON3)
            {
                dragging = false;
            }
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
            // TODO
        }

    };
    private final KeyAdapter keyHandler = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent ke)
        {
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    paused = !paused;
                    Game.getInstance().setPaused(paused);
                    break;
            }
        }
    };

    /**
     * Creates new form GamePanel
     */
    public GamePanel()
    {
        init();
    }

    private void init()
    {
        System.out.println("GamePanel.init()");
        initComponents();

        viewX = 0;
        viewY = 0;
        mouseX = 0;
        mouseY = 0;
        dragX = 0;
        dragY = 0;
        selected = null;
        dragging = false;
        editMode = false;
        paused = true;
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.addKeyListener(keyHandler);
        try
        {
            bgImage = ImageIO.read(new File("src/images/background.png")).getScaledInstance(BG_WIDTH, BG_HEIGHT, 0);
            towerImageBlue = ImageIO.read(new File("src/images/towerBlue.png")).getScaledInstance(Game.TOWER_WIDTH, Game.TOWER_HEIGHT, 0);
            towerImageNone = ImageIO.read(new File("src/images/towerGray.png")).getScaledInstance(Game.TOWER_WIDTH, Game.TOWER_HEIGHT, 0);
            towerImageRed = ImageIO.read(new File("src/images/towerRed.png")).getScaledInstance(Game.TOWER_WIDTH, Game.TOWER_HEIGHT, 0);
            truppImage = ImageIO.read(new File("src/images/truppRed.png")).getScaledInstance(TRUPP_IMG_WIDTH, TRUPP_IMG_HEIGHT, 0);
            markerImage = ImageIO.read(new File("src/images/marker.png")).getScaledInstance(Game.TOWER_WIDTH, Game.TOWER_HEIGHT, 0);
            coinImage = ImageIO.read(new File("src/images/coin.png")).getScaledInstance(32, 32, 0);
        }
        catch (IOException ex)
        {
            System.err.println(ex.toString());
        }
        CLOCK.start();
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.translate(viewX, viewY);

        g.drawImage(bgImage, 0, 0, this);

        // Draw lanes
        for (Tower t : Game.getInstance().getMap().getTowers())
        {
            if (t.getTarget() != null)
            {
                int srcCenterX = t.getX() + Game.TOWER_WIDTH / 2;
                int srcCenterY = t.getY() + Game.TOWER_HEIGHT / 2;
                int tgtCenterX = t.getTarget().getX() + Game.TOWER_WIDTH / 2;
                int tgtCenterY = t.getTarget().getY() + Game.TOWER_HEIGHT / 2;
                Polygon plgn = getLane(srcCenterX, srcCenterY, tgtCenterX, tgtCenterY);
                g.setColor(getTowerColor(t));
                g.fillPolygon(plgn);
            }
        }

        // Draw Towers
        g.setColor(java.awt.Color.WHITE);
        g.setFont(this.getFont());
        for (Tower t : Game.getInstance().getMap().getTowers())
        {
            Image towerImage = towerImageNone;
            if (t.getColor() == Team.BLUE)
            {
                towerImage = towerImageBlue;
            }
            else if (t.getColor() == Team.RED)
            {
                towerImage = towerImageRed;

            }
            g.drawImage(towerImage, t.getX(), t.getY(), this);
            int value = t.getValue();
            g.drawString("" + value, t.getX() + 50, t.getY() + 10);
        }

        // Draw Trupps
        for (Trupp t : Game.getInstance().getTrupps())
        {
            int x = (int) t.getX() - TRUPP_IMG_WIDTH / 2;
            int y = (int) t.getY() - TRUPP_IMG_HEIGHT / 2;
            g.drawImage(truppImage, x, y, this);
        }

        // Draw selection
        if (selected != null)
        {
            g.drawImage(markerImage, selected.getX(), selected.getY(), this);
        }

        g.translate(-viewX, -viewY);

        if (paused)
        {
            g.drawString("PAUSED", getWidth() / 3, getHeight() / 2);
        }

        g.drawImage(coinImage, 16, 16, this);
        g.drawString("" + Game.getInstance().getCoins(), 52, 40);

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

        setFont(new java.awt.Font("Impact", 3, 28)); // NOI18N

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

    public static Polygon getLane(int srcCenterX, int srcCenterY, int tgtCenterX, int tgtCenterY)
    {
        int dX = srcCenterX - tgtCenterX;
        int dY = srcCenterY - tgtCenterY;
        Vector2D diagonal = new Vector2D(dY, dX).getNormalized().mult(10);
        int[] xs = new int[]
        {
            srcCenterX - (int) diagonal.x, srcCenterX + (int) diagonal.x, tgtCenterX + (int) diagonal.x, tgtCenterX - (int) diagonal.x
        };
        int[] ys = new int[]
        {
            srcCenterY + (int) diagonal.y, srcCenterY - (int) diagonal.y, tgtCenterY - (int) diagonal.y, tgtCenterY + (int) diagonal.y
        };
        return new Polygon(xs, ys, 4);
    }

    private java.awt.Color getTowerColor(Tower t)
    {
        switch (t.getColor())
        {
            case BLUE:
                return java.awt.Color.BLUE;
            case RED:
                return java.awt.Color.RED;
            case GREEN:
                return java.awt.Color.GREEN;
            case YELLOW:
                return java.awt.Color.YELLOW;
            default:
                return java.awt.Color.GRAY;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
