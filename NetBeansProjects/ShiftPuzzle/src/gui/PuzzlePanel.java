/*
 * Copyright (C) 2019 Dominik Messerschmidt
 *
 * This program is free software: you c+,an redistribute it and/or modify
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
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.ShiftPuzzle;
import main.IntTuple;

/**
 *
 * @author Dominik Messerschmidt
 */
public final class PuzzlePanel extends JPanel
{

    private boolean debug;
    public static final Font FONT = new Font("Consolas", 0, 32);
    public static final int UPDATE_RATE = 25;

    private final ShiftPuzzle puzzle;
    private int mouseX, mouseY, selX, selY, playbackIndex;
    private LinkedList<IntTuple> playback;
    private BufferedImage origImage;
    private BufferedImage[][] tileImages;
    private Animation animation;
    private int animatedTileX, animatedTileY;
    private final AnimationListener AL = new AnimationListener()
    {
        @Override
        public void animationComplete()
        {
            if (puzzle.shiftTile(animatedTileX, animatedTileY))
            {
                System.out.println("animationComplete(): shifted tile");
            }
            else
            {
                System.err.println("animationComplete(): Something went terribly wrong");
            }
            animation = null;
        }
    };
    private final MouseAdapter mausHaendler = new MouseAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent me)
        {
            mouseX = me.getX();
            mouseY = me.getY();
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
            if (me.getButton() == MouseEvent.BUTTON3)
            {
                int x = puzzle.getSize() * me.getX() / getWidth();
                int y = puzzle.getSize() * me.getY() / getHeight();
                System.out.println("Right clicked at " + x + "," + y);
                if (selX < 0 || selY < 0)
                {
                    selX = x;
                    selY = y;
                }
                else
                {
                    puzzle.switchTilesForcefully(x, y, selX, selY);
                    selX = -1;
                    selY = -1;
                }
            }
            else
            {
                clickAt(me.getX(), me.getY());
            }
        }
    };

    private final Timer updateTimer = new Timer(UPDATE_RATE, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            //System.out.println("update()");
            int tilewidth = getWidth() / puzzle.getSize();
            int tileheight = getHeight() / puzzle.getSize();
            if (tileImages[0][0] == null || (tileImages[0][0].getWidth(null) != tilewidth) || (tileImages[0][0].getHeight(null) != tileheight))
            {
                createTileImages();
            }
            repaint();
            if (animation != null)
            {
                animation.doStep(UPDATE_RATE);
            }
            else if (playback != null && playbackIndex < playback.size())
            {
                int x = playback.get(playbackIndex).X * tilewidth + tilewidth / 2;
                int y = playback.get(playbackIndex).Y * tileheight + tileheight / 2;
                if (clickAt(x, y))
                {
                    playbackIndex++;
                }
            }
        }
    });

    public PuzzlePanel(ShiftPuzzle puzzle)
    {
        this(puzzle, null);
    }

    public PuzzlePanel(ShiftPuzzle puzzle, BufferedImage img)
    {
        this.puzzle = puzzle;
        this.addMouseListener(mausHaendler);
        //this.addMouseMotionListener(mausHaendler);
        debug = !setImage(img);
        selX = -1;
        selY = -1;
        updateTimer.start();
    }

    public boolean clickAt(int clickx, int clicky)
    {
        if (animation != null)
        {
            System.out.println("Animation still running, wait till finished");
            return false;
        }
        int x = puzzle.getSize() * clickx / getWidth();
        int y = puzzle.getSize() * clicky / getHeight();
        System.out.println("Clicked at " + x + "," + y);
        if (puzzle.canMove(x, y))
        {
            animatedTileX = x;
            animatedTileY = y;
            IntTuple freeTile = puzzle.getFreeTile();
            int tilewidth = getWidth() / puzzle.getSize();
            int tileheight = getHeight() / puzzle.getSize();
            animation = new Animation(tilewidth * animatedTileX, tileheight * animatedTileY, tilewidth * freeTile.X, tileheight * freeTile.Y, 250);
            animation.addAnimationListener(AL);
            System.out.println("Started animation playback");
        }
        else
        {
            System.out.println("Not possible to shift that tile");
            return false;
        }
        repaint();
        if (puzzle.isComplete())
        {
            System.out.println("Du hast gewonnen!");
            System.out.println("Du musstest " + puzzle.getShifted() + " mal shieben.");
        }
        return true;
    }

    public void startPlayback(LinkedList<IntTuple> sequence)
    {
        System.out.println("Start playback of move sequence");
        playback = sequence;
        playbackIndex = 0;
    }

    public boolean setImage(BufferedImage image)
    {
        if (image != null)
        {
            this.origImage = image;
            int size = puzzle.getSize();
            tileImages = new BufferedImage[size][size];
            // create a puzzle of that image
            createTileImages();
            System.out.println("Loaded Image successfully");
            return true;
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        int tilewidth = getWidth() / puzzle.getSize();
        int tileheight = getHeight() / puzzle.getSize();
        g.setFont(FONT);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        for (int x = 0; x < puzzle.getSize(); x++)
        {
            for (int y = 0; y < puzzle.getSize(); y++)
            {
                int screenX, screenY;
                if (animation != null && animatedTileX == x && animatedTileY == y)
                {
                    IntTuple values = animation.getValues();
                    screenX = values.X;
                    screenY = values.Y;
                }
                else
                {
                    screenX = x * tilewidth;
                    screenY = y * tileheight;
                }
                if (debug)
                {
                    if (mouseX / tilewidth == x && mouseY / tileheight == y)
                    {
                        g.setColor(Color.BLUE);
                        g.fillRect(screenX, screenY, tilewidth, tileheight);
                    }
                    if (selX >= 0 && selY >= 0 && selX / tilewidth == x && selY / tileheight == y)
                    {
                        g.setColor(Color.GREEN);
                        g.fillRect(x * tilewidth, y * tileheight, tilewidth, tileheight);
                    }
                    g.setColor(Color.BLACK);
                    if (puzzle.isFree(x, y))
                    {
                        g.drawString("FREE", x * tilewidth + tilewidth / 4, y * tileheight + tileheight / 2);
                    }
                    else
                    {
                        g.drawString(puzzle.getTile(x, y) + "", x * tilewidth + tilewidth / 4, y * tileheight + tileheight / 2);
                    }
                }
                else
                {
                    if (puzzle.isFree(x, y))
                    {
                        //g.drawString("FREE", x * tilewidth + tilewidth / 4, y * tileheight + tileheight / 2);
                    }
                    else
                    {
                        g.drawImage(getTileImage(puzzle.getTile(x, y) - 1), screenX, screenY, this);
                    }
                }
            }
        }
    }

    private Image getTileImage(int tile)
    {
        int size = puzzle.getSize();
        int x = tile % size;
        int y = tile / size;
        return tileImages[x][y];
    }

    private void createTileImages()
    {
        int size = puzzle.getSize();
        int imgWidth = origImage.getWidth();
        int imgHeight = origImage.getHeight();
        // create a puzzle of that image
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                BufferedImage tileImage = origImage.getSubimage(x * imgWidth / size, y * imgHeight / size, imgWidth / size, imgHeight / size);
                if (getWidth() > 0 && getHeight() > 0)
                {
                    tileImages[x][y] = toBufferedImage(tileImage.getScaledInstance(getWidth() / size, getHeight() / size, 0));
                }
            }
        }
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
