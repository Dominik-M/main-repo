package main;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Utils
{

    public static final String[] SUPPORTED_FORMATS = new String[]
    {
        "png", "gif", "bmp", "jpg"
    };
    public static final FilenameFilter IMAGEFILE_FILTER = new FilenameFilter()
    {

        @Override
        public boolean accept(File file, String name)
        {
            if (file.isFile())
            {
                for (String format : SUPPORTED_FORMATS)
                {
                    if (name.endsWith("." + format))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    };

    public static File[] getImageFiles(String dirPath)
    {
        File[] imgFiles;
        File inputDirectory = new File(dirPath);

        if (inputDirectory.exists() && inputDirectory.isDirectory())
        {
            imgFiles = inputDirectory.listFiles(IMAGEFILE_FILTER);
        }
        else
        {
            imgFiles = new File[0];
        }
        return imgFiles;
    }

    public static boolean writeImageData(BufferedImage img, String outputPath)
    {
        final String TAG = "Utils.writeImageData(): ";
        File outputFile;
        PrintWriter out;
        boolean retVal = true;

        try
        {
            outputFile = new File(outputPath);
            out = new PrintWriter(outputFile);
        }
        catch (FileNotFoundException e1)
        {
            System.err.println(TAG + "Output file not found");
            e1.printStackTrace();
            return false;
        }
        if (img != null)
        {
            int width = img.getWidth(), height = img.getHeight();
            System.out.println(TAG + "Image Dimensions: " + width + " x " + height);
            out.print(outputFile.getName().toUpperCase() + "[" + width + "x" + height + "] = ");
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    out.print(img.getRGB(x, y) + ",");
                }
                out.println();
            }
            out.println();
        }
        out.close();
        return retVal;
    }

    public static Image blend(BufferedImage img, final int value)
    {
        ImageFilter filter = new RGBImageFilter()
        {

            @Override
            public final int filterRGB(int x, int y, int rgb)
            {
                if ((rgb & 0xFF000000) != 0)
                {
                    // blend
                    return value | rgb;
                }
                else
                {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static void replaceColor(BufferedImage img, int target, int value)
    {
        // the color we are looking for... Alpha bits are set to opaque
        int markerRGB = target | 0xFF000000;

        if (img != null)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                for (int y = 0; y < img.getHeight(); y++)
                {
                    if ((img.getRGB(x, y) | 0xFF000000) == markerRGB)
                    {
                        img.setRGB(x, y, value);
                    }
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Image must not be null!");
        }
    }

    public static Image makeColorTransparent(Image im, final int color)
    {
        ImageFilter filter = new RGBImageFilter()
        {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb)
            {
                if ((rgb | 0xFF000000) == markerRGB)
                {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else
                {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static Image makeColorTransparentInverse(Image im, final int color)
    {
        ImageFilter filter = new RGBImageFilter()
        {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb)
            {
                if ((rgb | 0xFF000000) != markerRGB)
                {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else
                {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
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

    public static boolean resizeImageFiles(File... files)
    {
        for (File f : files)
        {
            try
            {
                BufferedImage img = ImageIO.read(f);
                Image rawimg = img.getScaledInstance(img.getWidth() / 2, img.getHeight() / 2, 0);

                File output = new File(f.getParentFile().getAbsolutePath() + "/resized/"
                        + f.getName().substring(0, f.getName().lastIndexOf(".")) + ".png");

                ImageIO.write(toBufferedImage(rawimg), "png", output);
            }
            catch (IOException e)
            {
                System.err.println("Failed to process " + f);
            }
        }
        return true;
    }

    public static boolean createSpritesheet(File[] files, File outputFile)
    {
        return createSpritesheet(files, outputFile, null, null);
    }

    public static boolean createSpritesheet(File[] files, File outputFile, File outputXML)
    {
        return createSpritesheet(files, outputFile, outputXML, null);
    }

    public static boolean createSpritesheet(File[] files, File outputFile, Rectangle clip)
    {
        return createSpritesheet(files, outputFile, null, clip);
    }

    public static boolean createSpritesheet(File[] files, File outputFile, File outputXML, Rectangle clip)
    {
        LinkedList<File> imagefiles = new LinkedList<>();
        if (files != null)
        {
            for (File f : files)
            {
                // try read:
                // BufferedImage img = ImageIO.read(f);
                // filter by filename:
                if (IMAGEFILE_FILTER.accept(f, f.getName()))
                {
                    imagefiles.add(f);
                }
            }
            int n = imagefiles.size();
            if (n > 1)
            {
                try
                {
                    int width, height;
                    if (clip == null)
                    {
                        BufferedImage img = ImageIO.read(imagefiles.get(0));
                        width = img.getWidth();
                        height = img.getHeight();
                    }
                    else
                    {
                        width = clip.width;
                        height = clip.height;
                    }
                    System.out.println("Merging " + n + " images. Resolution: " + width + " x "
                            + height);

                    // quadratic dimensions:
                    int c = (int) (Math.sqrt(1.0 * n * height / width) + 0.5);
                    int r = (int) (1.0 * n / c + 0.5);
                    // minimum dimensions:
                    /*
                    int c = (int) (Math.sqrt(n));
                    int r = c;
                    while (c * r < n)
                    {
                        if (width * c < height * r)
                        {
                            c++;
                        }
                        else
                        {
                            r++;
                        }
                    }
                     */
                    // fixed:
                    //c = 5;
                    //r = 12;

                    System.out.println("Calculated dimension: c = " + c + " , r = " + r);

                    int cols = c;
                    int rows = r;
                    int totalWidth = width * cols;
                    int totalHeight = height * rows;
                    System.out.println("Total width = " + totalWidth + ", total height = "
                            + totalHeight);
                    if (totalWidth * totalHeight > 44609040)
                    {
                        System.err.println("Total resolution is too high.");
                        return false;
                    }
                    BufferedImage spritesheet = new BufferedImage(totalWidth, totalHeight,
                            BufferedImage.TYPE_INT_ARGB);
                    Graphics g = spritesheet.createGraphics();
                    for (int i = 0; i < n; i++)
                    {
                        int x = i % cols * width;
                        int y = i / cols * height;
                        BufferedImage img = ImageIO.read(imagefiles.get(i));
                        if (clip != null)
                        {
                            img = img.getSubimage(clip.x, clip.y, clip.width, clip.height);
                        }
                        if (img.getWidth() != width || img.getHeight() != height)
                        {
                            System.err.println("Warning: " + imagefiles.get(i).getName()
                                    + " has unequal dimensions");
                        }
                        g.drawImage(img, x, y, width, height, null);
                    }
                    System.out.println("Writing spritesheet to file " + outputFile.getName());
                    try
                    {
                        ImageIO.write(spritesheet, "png", outputFile);
                        System.out.println("Succesfully created spritesheet");
                        return true;
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        System.err.println("Failed to write spritesheet");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
