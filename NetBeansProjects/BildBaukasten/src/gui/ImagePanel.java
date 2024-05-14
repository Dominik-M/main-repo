package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;
import main.Element;

public class ImagePanel extends JPanel
{

    enum PixelState
    {
        SELECTED, NOT_SELECTED, NOT_CHECKED;
    }

    public static final Color GRAY_MARKER = new Color(0xFFFFFFFF);
    private static final long serialVersionUID = 3006769532505931833L;

    private BufferedImage img;
    private PixelState[][] selectedPixels;
    private Image scaledImg;
    private double scale;
    private int cursorX, cursorY, dragX, dragY;
    private LinkedList<Element> elements;

    public ImagePanel()
    {
        init();
    }

    private void init()
    {
        setPreferredSize(new Dimension(500, 300));
        img = null;
        scaledImg = null;
        scale = 1;
        cursorX = 0;
        cursorY = 0;
        dragX = -1;
        dragY = -1;
        selectedPixels = new PixelState[0][0];
    }

    public void buildImage(LinkedList<Element> elements)
    {
        this.elements = elements;
        Dimension maxSize = new Dimension(-1, -1);

        for (Element e : elements)
        {
            if (e.getRightBorder() > maxSize.width)
            {
                maxSize.width = e.getRightBorder();
            }
            if (e.getBottomBorder() > maxSize.height)
            {
                maxSize.height = e.getBottomBorder();
            }
        }

        if (maxSize.width > 0 && maxSize.height > 0)
        {
            img = new BufferedImage(maxSize.width, maxSize.height, BufferedImage.TYPE_INT_ARGB);
            selectedPixels = new PixelState[maxSize.width][maxSize.height];
        }
        else
        {
            img = null;
        }
        if (img != null)
        {
            Graphics2D g = img.createGraphics();
            for (Element e : elements)
            {
                g.drawImage(e.image.getScaledInstance(e.getScaledWidth(), e.getScaledHeight(), 0), e.getX(), e.getY(), this);
            }
            setImage(img);
        }
    }

    public BufferedImage getImage()
    {
        return img;
    }

    public void setImage(BufferedImage img)
    {
        this.img = img;
        this.scaledImg = getScaledImage();
        setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    }

    public Image getScaledImage()
    {
        if (img != null)
        {
            int width = (int) (img.getWidth() * scale);
            width = (width <= 0) ? 1 : width;
            int height = (int) (img.getHeight() * scale);
            height = (height <= 0) ? 1 : height;
            return img.getScaledInstance(width, height, 0);
        }
        return null;
    }

    public boolean isPixelSelected(int x, int y)
    {
        if (x >= 0 && x < selectedPixels.length
                && y >= 0 && y < selectedPixels[x].length)
        {
            return selectedPixels[x][y] == PixelState.SELECTED;
        }
        return false;
    }

    public void clearSelectedPixels()
    {
        for (int x = 0; x < selectedPixels.length; x++)
        {
            for (int y = 0; y < selectedPixels[x].length; y++)
            {
                selectedPixels[x][y] = PixelState.SELECTED;
            }
        }
    }

    public boolean isBorderPixelOfSelection(int x, int y)
    {
        return isPixelSelected(x, y)
                && (!isPixelSelected(x - 1, y) // left
                || !isPixelSelected(x + 1, y) // right
                || !isPixelSelected(x, y - 1) // up
                || !isPixelSelected(x, y + 1)); // down
    }

    private void selectPixelsAroundRecursive(int x, int y, int markerColor, int threshold)
    {
        if (x >= 0 && x < selectedPixels.length
                && y >= 0 && y < selectedPixels[x].length
                && selectedPixels[x][y] == PixelState.NOT_CHECKED)
        {
            int mrgb = img.getRGB(x, y);
            int rgbsum1 = ((mrgb >> 24) & 0x000000FF)
                    + ((mrgb >> 16) & 0x000000FF)
                    + ((mrgb >> 8) & 0x000000FF)
                    + (mrgb & 0x000000FF);
            int rgbsum2 = ((markerColor >> 24) & 0x000000FF)
                    + ((markerColor >> 16) & 0x000000FF)
                    + ((markerColor >> 8) & 0x000000FF)
                    + (markerColor & 0x000000FF);
            if (Math.abs(rgbsum1 - rgbsum2) <= threshold)
            {
                // Match
                selectedPixels[x][y] = PixelState.SELECTED;
                selectPixelsAroundRecursive(x - 1, y, markerColor, threshold); // left
                selectPixelsAroundRecursive(x + 1, y, markerColor, threshold); // right
                selectPixelsAroundRecursive(x, y - 1, markerColor, threshold); // up
                selectPixelsAroundRecursive(x, y + 1, markerColor, threshold); // down
            }
            else
            {
                selectedPixels[x][y] = PixelState.NOT_SELECTED;
            }
        }
    }

    public void selectPixelsAround(int x, int y, int threshold)
    {
        if (img != null
                && x >= 0 && x < selectedPixels.length
                && y >= 0 && y < selectedPixels[x].length)
        {
            for (int xi = 0; xi < selectedPixels.length; xi++)
            {
                for (int yi = 0; yi < selectedPixels[xi].length; yi++)
                {
                    selectedPixels[xi][yi] = PixelState.NOT_CHECKED;
                }
            }
            selectedPixels[x][y] = PixelState.SELECTED;
            int markerColor = img.getRGB(x, y);
            selectPixelsAroundRecursive(x - 1, y, markerColor, threshold); // left
            selectPixelsAroundRecursive(x + 1, y, markerColor, threshold); // right
            selectPixelsAroundRecursive(x, y - 1, markerColor, threshold); // up
            selectPixelsAroundRecursive(x, y + 1, markerColor, threshold); // down
        }
    }

    public void setScale(double scale)
    {
        this.scale = scale;
        scaledImg = getScaledImage();
    }

    public double getScale()
    {
        return scale;
    }

    public void setCursor(int x, int y)
    {
        cursorX = x;
        cursorY = y;
    }

    public int getCursorX()
    {
        return cursorX;
    }

    public int getCursorY()
    {
        return cursorY;
    }

    public void setDrag(int x, int y)
    {
        dragX = x;
        dragY = y;
    }

    public int getDragX()
    {
        return dragX;
    }

    public int getDragY()
    {
        return dragY;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        // background
        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());

        // main image
        g.drawImage(scaledImg, 0, 0, null);

        // selected elements highlight
        if (elements != null)
        {
            g.setColor(Color.blue);
            for (Element e : elements)
            {
                if (e.selected)
                {
                    g.drawRect(e.getX(), e.getY(), e.getScaledWidth(), e.getScaledHeight());
                }
            }
        }

        g.setColor(Color.cyan);
        for (int x = 0; x < selectedPixels.length; x++)
        {
            for (int y = 0; y < selectedPixels[x].length; y++)
            {
                if (isBorderPixelOfSelection(x, y))
                {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }

        // cursor
        g.setColor(Color.black);
        g.drawLine(cursorX, 0, cursorX, getHeight());
        g.drawLine(0, cursorY, getWidth(), cursorY);

        // selection frame
        if (dragX >= 0 && dragY >= 0)
        {
            g.setColor(GRAY_MARKER);
            int x = dragX < cursorX ? dragX : cursorX;
            int y = dragY < cursorY ? dragY : cursorY;
            int width = Math.abs(dragX - cursorX);
            int height = Math.abs(dragY - cursorY);
            g.drawRect(x, y, width, height);
        }
    }
}
