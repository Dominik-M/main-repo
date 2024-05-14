/**
 * Copyright (C) 2018 Dominik Messerschmidt
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
package main;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 06.03.2018
 */
public class Main
{

    /**
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        // Working directory
        File root = new File("").getAbsoluteFile();
        // Process command line arguments
        if (args.length > 0)
        {
            File f = new File(args[0]);
            if (f.exists())
            {
                if (f.isDirectory())
                {
                    File outputFile = new File(root.getAbsolutePath() + "/" + f.getName()
                            + "_spritesheet_.png");
                    Utils.createSpritesheet(f.listFiles(), outputFile);
                    return;
                }
            }
        }

        // prompt filechooser
        JFileChooser jfc = new JFileChooser(root);
        jfc.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
        File[] selection;
        jfc.setMultiSelectionEnabled(true);
        while (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            selection = jfc.getSelectedFiles();

            if (selection == null)
            {
                System.out.println("Selection invalid");
                continue;
            }

            // Dimension d = requestDimension(preferedDimension,
            // "Enter spritesheet dimension [cols,rows]");
            // if (d == null)
            // return;
            // System.out.println("Entered dimension: " + d);
            boolean filesSelected = false;

            for (File f : selection)
            {
                if (f.isDirectory())
                {
                    System.out
                            .println("Creating spritesheet from directory " + f.getAbsolutePath());
                    File outputFile = new File(root.getAbsolutePath() + "/" + f.getName()
                            + "_spritesheet_.png");
                    // normal
                    Utils.createSpritesheet(f.listFiles(), outputFile);
                    // clipped
                    // Utils.createSpritesheet(f.listFiles(), outputFile, new Rectangle(480, 180, 960, 360));
                }
                else if (f.isFile())
                {
                    filesSelected = true;
                }
            }

            if (filesSelected)
            {
                System.out.println("Creating spritesheet from selected files in " + root);
                File outputFile = new File(root.getAbsolutePath() + "/" + selection[0].getName()
                        + "_spritesheet_.png");
                Utils.createSpritesheet(selection, outputFile);
            }
        }
        System.out.println("FileChooser closed. Application is shutting down");
    }
}
