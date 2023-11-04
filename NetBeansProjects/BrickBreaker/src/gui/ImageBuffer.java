/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Dominik Messerschmidt
 */
public class ImageBuffer
{

    public class BufferEntry
    {

        public final int WIDTH, HEIGHT;
        public final Image IMAGE;

        public BufferEntry(Image scaledImage, int w, int h)
        {
            IMAGE = scaledImage;
            WIDTH = w;
            HEIGHT = h;
        }
    }

    private final LinkedList<BufferEntry> buffer;
    private final BufferedImage BASE;

    public ImageBuffer(BufferedImage baseImage)
    {
        buffer = new LinkedList<>();
        BASE = baseImage;
        buffer.add(new BufferEntry(baseImage, baseImage.getWidth(), baseImage.getHeight()));
    }

    public BufferEntry get(int w, int h)
    {
        for (BufferEntry entry : buffer)
        {
            if (entry.WIDTH == w && entry.HEIGHT == h)
            {
                return entry;
            }
        }
        // not contained, make a new entry
        BufferEntry entry = new BufferEntry(BASE.getScaledInstance(w, h, 0), w, h);
        buffer.add(entry);
        System.out.println("ImageBuffer.get(" + w + "," + h + "): Image added.");
        return entry;
    }

    public boolean contains(BufferEntry entry)
    {
        return contains(entry.WIDTH, entry.HEIGHT);
    }

    public boolean contains(int w, int h)
    {
        for (BufferEntry entry : buffer)
        {
            if (entry.WIDTH == w && entry.HEIGHT == h)
            {
                return true;
            }
        }
        return false;
    }

    public static ImageBuffer tryLoad(File f)
    {
        ImageBuffer buff = null;
        try
        {
            BufferedImage img = ImageIO.read(f);
            buff = new ImageBuffer(img);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ImageBuffer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buff;
    }
}
