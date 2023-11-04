/*
 * Copyright (C) 2017 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dmsr.anno.gui;

import dmsr.anno.main.GameController;
import dmsr.anno.world.Terrain;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 31.10.2017
 */
public class MainPanel extends JPanel
{

    public static final Color BACKGROUND_COLOR = Color.white;
    public static final int SPOT = 16;

    private int screenWidth, screenHeight;
    private int viewX, viewY;
    private int mouseX, mouseY;
    private int zoom = 3;
    private final double[] zoomFactors = new double[]
    {
        0.125, 0.25, 0.5, 1, 2.0, 4.0
    };

    private final MouseAdapter mouseListener = new MouseAdapter()
    {
        @Override
        public void mousePressed(MouseEvent me)
        {
            Point mouseGridPos = getGridPos(me.getPoint());
            System.out.println("MousePressed at " + mouseGridPos);
        }

        @Override
        public void mouseReleased(MouseEvent me)
        {
        }

        @Override
        public void mouseMoved(MouseEvent me)
        {
            mouseX = me.getX();
            mouseY = me.getY();
        }

        @Override
        public void mouseDragged(MouseEvent me)
        {
            viewX += (mouseX - me.getX()) * getZoom();
            viewY += (mouseY - me.getY()) * getZoom();
            mouseX = me.getX();
            mouseY = me.getY();
            repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mwe)
        {
            zoom(mwe.getWheelRotation());
        }
    };

    public MainPanel()
    {
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addMouseWheelListener(mouseListener);
    }

    public void zoom(int steps)
    {
        zoom += steps;
        if (zoom < 0)
        {
            zoom = 0;
        }
        if (zoom >= zoomFactors.length)
        {
            zoom = zoomFactors.length - 1;
        }

    }

    public double getZoom()
    {
        return zoomFactors[zoom];
    }

    public Point getScreenPos(Point gridPos)
    {
        int x = (int) (gridPos.x * SPOT / getZoom() - viewX);
        int y = (int) (gridPos.y * SPOT / getZoom() - viewY);
        return new Point(x, y);
    }

    public Point getGridPos(Point screenPos)
    {
        int x = (int) ((screenPos.x + viewX) * getZoom() / SPOT);
        int y = (int) ((screenPos.y + viewY) * getZoom() / SPOT);
        return new Point(x, y);
    }

    public final BufferedImage createGUI()
    {
        screenWidth = (int) (getWidth() * getZoom());
        screenHeight = (int) (getHeight() * getZoom());
        BufferedImage gui = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gui.createGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, gui.getWidth(), gui.getHeight());
        draw(g);
        return gui;
    }

    public void draw(Graphics2D g)
    {
        int x0 = viewX / SPOT;
        int y0 = viewY / SPOT;
        int gridWidth = screenWidth / SPOT + 1;
        int gridHeight = screenHeight / SPOT + 1;
        for (int xi = -1; xi <= gridWidth; xi++)
        {
            int gridX = x0 + xi;
            int screenX = xi * SPOT - (viewX % SPOT);
            for (int yi = -1; yi <= gridHeight; yi++)
            {
                int gridY = y0 + yi;
                int screenY = yi * SPOT - (viewY % SPOT);
                // draw ground
                Terrain ground = GameController.DATA.getMap().getGround(gridX, gridY);
                g.setColor(ground.COLOR);
                g.fillRect(screenX, screenY, SPOT, SPOT);
                if (ground != Terrain.NONE)
                {
                    g.setColor(Color.BLACK);
                    g.drawRect(screenX, screenY, SPOT, SPOT);
                }
                // draw buildings
            }
        }
        // draw mouse
        int mouseXScreen = (int) ((mouseX - viewX) * getZoom());
        mouseXScreen = mouseXScreen - (mouseXScreen % SPOT);
        int mouseYScreen = (int) ((mouseY - viewY) * getZoom());
        mouseYScreen = mouseYScreen - (mouseYScreen % SPOT);
        g.setColor(Color.yellow);
        g.fillRect(mouseXScreen, mouseYScreen, SPOT, SPOT);
        // draw test point
        g.setColor(Color.red);
        Point testPos = getScreenPos(new Point(2, 2));
        g.fillRect(testPos.x, testPos.y, SPOT, SPOT);
    }

    // Superclass Methods
    @Override
    public final void paintComponent(Graphics g)
    {
        BufferedImage screen = createGUI();

        g.drawImage(screen.getScaledInstance(g.getClipBounds().width, g.getClipBounds().height, 0),
                0, 0, this);
    }
}
