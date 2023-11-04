package main;

import gui.MainFrame;
import java.io.File;

public class Main
{

    public static final String OUTPUT_FILENAME = "ImageOutput.txt";
    public static final String INPUT_FILENAME = "images/";

    public static void main(String[] args)
    {
        MainFrame.setNimbusLookAndFeel();
        MainFrame frame = new MainFrame();
        if (args.length > 0)
        {
            File f = new File(args[0]);
            if (f.exists())
            {
//                if (f.isDirectory())
//                {
//                    BufferedImage spritesheet = Utils.mergeToSpriteSheet(f);
//                    if (spritesheet != null)
//                    {
//                        File outputfile = new File(new File("").getAbsolutePath() + "/"
//                                + f.getName() + "_spritesheet_.png");
//                        System.out.println("Writing spritesheet to file " + outputfile.getName());
//                        try
//                        {
//                            ImageIO.write(spritesheet, "png", outputfile);
//                            System.out.println("Succesfully created spritesheet");
//                        }
//                        catch (IOException e)
//                        {
//                            e.printStackTrace();
//                            System.err.println("Failed to write spritesheet");
//                        }
//                    }
//                    else
//                    {
//                        System.err.println("Failed to create spritesheet");
//                    }
//                    return;
//                }
//                else
                {
                    frame.open(f);
                }
            }
        }
        frame.setVisible(true);
        /*
		 * // first limit: 6679x6679=44609041 // second limit: 44612842x1
		 *
		 * int width = 22306421, height = 2;
		 *
		 * BufferedImage img; while (width > 0) { System.out.println("Creating BufferedImage " + width + " x " +
		 * height); img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); width += 1; }
         */
    }
}
