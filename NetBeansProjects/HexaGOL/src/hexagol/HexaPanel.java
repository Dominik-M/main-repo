/*
 * Copyright (C) 2019 Dominik Messerschmidt
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
package hexagol;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Dominik Messerschmidt
 */
public class HexaPanel extends JPanel
{

    private HexaGOL game;
    private int spot = 32, stencil = 2;
    private int mouseX, mouseY;

    private final MouseAdapter mouseEventHandler = new MouseAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent me)
        {
            mouseX = me.getX();
            mouseY = me.getY();
        }

    };

    public HexaPanel()
    {
        this(new HexaGOL(9, 23));
    }

    public HexaPanel(HexaGOL hgol)
    {
        game = hgol;
        new Timer(20, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                repaint();
            }
        }).start();
        this.addMouseWheelListener(mouseEventHandler);
        this.addMouseMotionListener(mouseEventHandler);
        this.addMouseListener(mouseEventHandler);
    }

    public HexaGOL getGame()
    {
        return game;
    }

    public void setGame(HexaGOL game)
    {
        this.game = game;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(3 * game.WIDTH * spot / 2 + spot / 4, game.HEIGHT * spot / 2 + spot / 2);
    }

    public void drawHexagon(Graphics g, int x, int y, int width, int height)
    {
        int[] ints = new int[]
        {
            x, x + width / 4, x + 3 * width / 4, x + width, x + 3 * width / 4, x + width / 4
        };
        int[] ints1 = new int[]
        {
            y + height / 2, y, y, y + height / 2, y + height, y + height
        };

        g.fillPolygon(ints, ints1, 6);
    }

    public Color getStateColor(CellState state)
    {
        if (null == state)
        {
            return Color.RED;
        }
        else
        {
            switch (state)
            {
                case ALIVE:
                    return Color.GREEN;

                case DEAD:
                    return Color.GRAY;
                default:
                    return Color.RED;
            }
        }
    }

    public int getIndexX(int screenX, int screenY)
    {
        int y = getIndexY(screenY);
        return (2 * screenX - (y % 2 == 0 ? 0 : 3 * spot / 4)) / 3 * spot;
    }

    public int getIndexY(int screenY)
    {
        return 2 * screenY / spot;
    }

    public int getScreenX(int indexX, int indexY)
    {
        return 3 * indexX * spot / 2 + (indexY % 2 == 0 ? 0 : 3 * spot / 4);
    }

    public int getScreenY(int indexY)
    {
        return indexY * spot / 2;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Dimension d = getPreferredSize();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, d.width, d.height);
        for (int x = 0; x < game.WIDTH; x++)
        {
            for (int y = 0; y < game.HEIGHT; y++)
            {
                CellState state = game.getCellState(x, y);
                int screenX = getScreenX(x, y);
                int screenY = getScreenY(y);
                g.setColor(Color.BLACK);
                drawHexagon(g, screenX, screenY, spot, spot);
                g.setColor(getStateColor(state));
                drawHexagon(g, screenX + stencil, screenY + stencil, spot - 2 * stencil, spot - 2 * stencil);
            }
        }

        g.setColor(Color.YELLOW);
        int screenX = getScreenX(getIndexX(mouseX, mouseY), getIndexY(mouseY));
        int screenY = getScreenY(getIndexY(mouseY));
        drawHexagon(g, screenX + stencil, screenY + stencil, spot - 2 * stencil, spot - 2 * stencil);
    }
}
