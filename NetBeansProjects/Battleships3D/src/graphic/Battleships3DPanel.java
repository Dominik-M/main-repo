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
package graphic;

import game.Battleships3D;
import game.ShipType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import utils.Point2D;
import utils.Point3D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 05.05.2017
 */
public class Battleships3DPanel extends JPanel
{

    private Battleships3D game;
    private Point2D fluchtpunkt = new Point2D(0, 0);
    private double zoom = 1.0;
    private final MouseAdapter mouseCallback = new MouseAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent e)
        {
            fluchtpunkt = new Point2D(e.getX(), e.getY());
            repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            zoom += zoom;
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (e.getButton() == MouseEvent.BUTTON1)
            {
                zoom *= 2;
            }
            else
            {
                zoom /= 2;
            }
        }

    };

    public Battleships3DPanel(Battleships3D game)
    {
        this.game = game;
        setPreferredSize(new Dimension(600, 400));
        this.addMouseMotionListener(mouseCallback);
        this.addMouseListener(mouseCallback);
        this.addMouseWheelListener(mouseCallback);
    }

    public Point2D translateToScreen(Point3D p3d)
    {
        int x0 = fluchtpunkt.x;
        int y0 = fluchtpunkt.y; // Fluchtpunkt d. Perspektive
        double z_max = Battleships3D.DEFAULT_DEPTH + 1;
        int screen_x = (int) (x0 + p3d.x * zoom * z_max / (z_max - p3d.z));
        int screen_y = (int) (y0 - p3d.y * zoom * z_max / (z_max - p3d.z));
        Point2D p2d = new Point2D(screen_x, screen_y);
        return p2d;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int spotWidth = getWidth() / (Battleships3D.DEFAULT_WIDTH + 2);
        int spotHeight = getHeight() / (Battleships3D.DEFAULT_HEIGHT + 2);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        Point3D p = new Point3D(0, 0, 0);
        Point2D p1, p2;
        p1 = translateToScreen(p);
        p2 = translateToScreen(new Point3D(p.x, p.y, p.z + 1));
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        for (int x = 0; x < Battleships3D.DEFAULT_WIDTH; x++)
        {
            for (int y = 0; y < Battleships3D.DEFAULT_HEIGHT; y++)
            {
                for (int z = 0; z < Battleships3D.DEFAULT_DEPTH; z++)
                {

                    Point3D p3d = new Point3D(x * spotWidth, y * spotHeight, z);
                    Point3D pRight = new Point3D((x + 1) * spotWidth, y * spotHeight, z);
                    Point3D pUp = new Point3D(x * spotWidth, (y + 1) * spotHeight, z);
                    Point3D pBack = new Point3D(x * spotWidth, y * spotHeight, z + 1);
                    Point2D p2d = translateToScreen(p3d);
                    Point2D pRight2d = translateToScreen(pRight);
                    Point2D pUp2d = translateToScreen(pUp);
                    Point2D pBack2d = translateToScreen(pBack);
                    g.drawLine(p2d.x, p2d.y, pRight2d.x, pRight2d.y);
                    g.drawLine(p2d.x, p2d.y, pUp2d.x, pUp2d.y);
                    g.drawLine(p2d.x, p2d.y, pBack2d.x, pBack2d.y);
                    ShipType spot = game.getShipAt(x, y, z);
                }
            }
        }
    }
}
