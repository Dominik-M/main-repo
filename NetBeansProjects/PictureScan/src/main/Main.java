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
package main;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 */
public class Main
{

    public static volatile boolean interruptFlag = false;
    public static volatile int filecounter = 0;
    public static final FileFilter FILTER_PICTURES = new FileFilter()
    {
        @Override
        public boolean accept(File pathname)
        {
            if (pathname.exists() && pathname.canRead() && pathname.isFile())
            {
                String name = pathname.getName();
                int index = name.lastIndexOf(".");
                if (index > 0)
                {
                    return name.substring(index).matches(".png|.gif|.jpg|.PNG|.GIF|.JPG");
                }
            }
            return false;
        }
    };
    public static final FileFilter FILTER_DIRECTORY = new FileFilter()
    {
        @Override
        public boolean accept(File pathname)
        {
            return pathname.exists() && pathname.canRead() && pathname.isDirectory();
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(graphic.MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(graphic.MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(graphic.MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(graphic.MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new graphic.MainFrame().setVisible(true);
            }
        });
    }

    public static LinkedList<File> listImagefilesRecursive(File root)
    {
        LinkedList<File> files = new LinkedList<>();
        if (root.isDirectory() && !interruptFlag)
        {
            System.out.println("Scanning directory \"" + root + "\"...");
            File[] list = root.listFiles(FILTER_PICTURES);
            if (list != null)
            {
                for (File f : list)
                {
                    files.add(f);
                    System.out.println("Found " + f);
                    filecounter++;
                }
            }
            else
            {
                System.out.println("No imagefiles found.");
            }
            list = root.listFiles(FILTER_DIRECTORY);
            if (list != null)
            {
                for (File dir : list)
                {
                    files.addAll(listImagefilesRecursive(dir));
                    if (interruptFlag)
                    {
                        break;
                    }
                }
            }
            else
            {
                System.out.println("No subdirectorys found.");
            }
        }
        else if (FILTER_PICTURES.accept(root))
        {
            files.add(root);
            filecounter++;
            System.out.println("Found " + root);
        }
        return files;
    }

}
