/**
 * Copyright (C) 2017 Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import monopoly.Field;
import monopoly.Monopoly;
import monopoly.Street;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 09.02.2017
 */
public class GamePanel extends JPanel
{

    public enum Side
    {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        CORNER;
    }

    public static final Font CORNER_FONT = new Font("Consolas", 1, 28);
    public static final Font NORMAL_FONT = new Font("Consolas", 0, 20);
    public static final int FIELD_WIDTH = 125;
    public static final int FIELD_HEIGHT = 175;
    public static final Color BACKGROUND = new Color(30, 160, 60);

    private BufferedImage board;
    private int viewX, viewY;
    private int mouseX, mouseY;
    private double zoom;
    private Rectangle centerBounds;

    private final MouseAdapter mouseCallback = new MouseAdapter()
    {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            zoom -= e.getWheelRotation() / 10.0;
            if (zoom < 0.25)
            {
                zoom = 0.25;
            }
            if (zoom > 2)
            {
                zoom = 2;
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            requestFocus();
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            viewX += (mouseX - e.getX());
            viewY += (mouseY - e.getY());
            if (viewX < 0)
            {
                viewX = 0;
            }
            if (viewX > board.getWidth() - getWidth())
            {
                viewX = board.getWidth() - getWidth();
            }
            if (viewY < 0)
            {
                viewY = 0;
            }
            if (viewY > board.getHeight() - getHeight())
            {
                viewY = board.getHeight() - getHeight();
            }
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }

    };

    public GamePanel()
    {
        init();
        addListener();
    }

    private void addListener()
    {
        addMouseListener(mouseCallback);
        addMouseMotionListener(mouseCallback);
        addMouseWheelListener(mouseCallback);
    }

    public final void init()
    {
        System.out.println("GamePanel.init()");
        viewX = 0;
        viewY = 0;
        zoom = 1;
        Monopoly game = Monopoly.getInstance();
        int boardwidth = (game.getWidth() - 1) * FIELD_WIDTH + 2 * FIELD_HEIGHT;
        int boardheight = (game.getHeight() - 1) * FIELD_WIDTH + 2 * FIELD_HEIGHT;
        centerBounds = new Rectangle(FIELD_HEIGHT, FIELD_HEIGHT, boardwidth - 2 * FIELD_HEIGHT, boardwidth - 2 * FIELD_HEIGHT);
        board = new BufferedImage(boardwidth, boardheight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = board.createGraphics();
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, boardwidth, boardheight);
        int index = 0;
        for (int i = 0; i < game.getWidth(); i++)
        {
            BufferedImage img = getFieldImage(game.getField(index), i == 0 ? Side.CORNER : Side.BOTTOM);
            g.drawImage(img, boardwidth - FIELD_HEIGHT - i * FIELD_WIDTH, boardheight - FIELD_HEIGHT, null);
            index++;
        }
        for (int i = 0; i < game.getHeight(); i++)
        {
            BufferedImage img = getFieldImage(game.getField(index), i == 0 ? Side.CORNER : Side.LEFT);
            g.drawImage(img, 0, boardheight - FIELD_HEIGHT - i * FIELD_WIDTH, null);
            index++;
        }
        for (int i = 0; i < game.getWidth(); i++)
        {
            BufferedImage img = getFieldImage(game.getField(index), i == 0 ? Side.CORNER : Side.TOP);
            g.drawImage(img, (i == 0 ? 0 : FIELD_HEIGHT + (i - 1) * FIELD_WIDTH), 0, null);
            index++;
        }
        for (int i = 0; i < game.getHeight(); i++)
        {
            BufferedImage img = getFieldImage(game.getField(index), i == 0 ? Side.CORNER : Side.RIGHT);
            g.drawImage(img, boardwidth - FIELD_HEIGHT, (i == 0 ? 0 : FIELD_HEIGHT + (i - 1) * FIELD_WIDTH), null);
            index++;
        }
    }

    public BufferedImage getFieldImage(Field f, Side side)
    {
        BufferedImage rawImg;
        if (side == Side.CORNER)
        {
            rawImg = new BufferedImage(FIELD_HEIGHT, FIELD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }
        else
        {
            rawImg = new BufferedImage(FIELD_WIDTH, FIELD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D g = rawImg.createGraphics();
        if (side == Side.CORNER)
        {
            g.setFont(CORNER_FONT);
        }
        else
        {
            g.setFont(NORMAL_FONT);
        }
        String[] lines = f.getTitle().split(" ");
        if (Street.class.isInstance(f))
        {
            Street s = (Street) f;
            g.setColor(new Color(s.getColor()));
            g.fillRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT / 5);
            String[] lines2 = new String[lines.length + 1];
            for (int i = 0; i < lines.length; i++)
            {
                lines2[i] = lines[i];
            }
            lines2[lines2.length - 1] = s.getValue() + Monopoly.getInstance().getCurrency();
            lines = lines2;
        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < lines.length; i++)
        {
            g.drawString(lines[i], 10, FIELD_HEIGHT / 4 + (g.getFont().getSize() + 5) * i);
        }
        g.drawRect(0, 0, rawImg.getWidth() - 1, rawImg.getHeight() - 1);

        // rotate
        BufferedImage img;
        switch (side)
        {
            case LEFT:
                img = new BufferedImage(FIELD_HEIGHT, FIELD_WIDTH, BufferedImage.TYPE_INT_ARGB);
                for (int x = 0; x < FIELD_WIDTH; x++)
                {
                    for (int y = 0; y < FIELD_HEIGHT; y++)
                    {
                        img.setRGB(FIELD_HEIGHT - y - 1, x, rawImg.getRGB(x, y));
                    }
                }
                break;
            case RIGHT:
                img = new BufferedImage(FIELD_HEIGHT, FIELD_WIDTH, BufferedImage.TYPE_INT_ARGB);
                for (int x = 0; x < FIELD_WIDTH; x++)
                {
                    for (int y = 0; y < FIELD_HEIGHT; y++)
                    {
                        img.setRGB(y, FIELD_WIDTH - x - 1, rawImg.getRGB(x, y));
                    }
                }
                break;
            case TOP:
                img = new BufferedImage(FIELD_WIDTH, FIELD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
                for (int x = 0; x < FIELD_WIDTH; x++)
                {
                    for (int y = 0; y < FIELD_HEIGHT; y++)
                    {
                        img.setRGB(FIELD_WIDTH - x - 1, FIELD_HEIGHT - y - 1, rawImg.getRGB(x, y));
                    }
                }
                break;
            default:
                img = rawImg;
                break;
        }
        return img;
    }

    public void Update()
    {
        // animations

        // update UI
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // draw board
        g.translate(-viewX, -viewY);
        g.drawImage(board.getScaledInstance((int) (board.getWidth() * zoom), (int) (board.getHeight() * zoom), 0), 0, 0, null);
        // TODO draw player, dices and money

        g.translate(viewX, viewY);
        // TODO draw UI
    }

    public boolean printBoard(File f)
    {
        System.out.println("GamePanel.printBoard()");
        try
        {
            ImageIO.write(board, "png", f);
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
