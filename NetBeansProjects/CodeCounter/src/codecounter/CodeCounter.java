/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import javax.swing.JFileChooser;

/**
 *
 * @author Dominik Messerschmidt
 */
public class CodeCounter
{

    public static final char[] IGNORED_CHARS = new char[]
    {
        '\r', '\n', '{', '}', ' ', '\t'
    };

    public static final String[] SUPPORTED_FORMATS = new String[]
    {
        "java", "c", "cpp"
    };
    public static final FilenameFilter FILEFILTER = new FilenameFilter()
    {
        @Override
        public boolean accept(File file, String string)
        {
            if (file.isDirectory())
            {
                return true;
            }
            String filename = file.getName();
            for (String format : SUPPORTED_FORMATS)
            {
                if (filename.endsWith(format))
                {
                    return true;
                }
            }
            return false;
        }
    };

    public static boolean lineValid(String line)
    {
        for (char c1 : line.toCharArray())
        {
            boolean ignored = false;
            for (char c2 : IGNORED_CHARS)
            {
                if (c1 == c2)
                {
                    ignored = true;
                }
            }
            if (!ignored)
            {
                return true;
            }
        }
        return false;
    }

    public static int countSLOC(File root)
    {
        int lines = 0;
        try
        {
            if (root.isFile() && FILEFILTER.accept(root, root.getName()))
            {
                BufferedReader reader = new BufferedReader(new FileReader(root));
                String line;
                boolean comment = false;
                while ((line = reader.readLine()) != null)
                {
                    if (!comment)
                    {
                        comment = line.contains("/*");
                    }
                    if (comment)
                    {
                        comment = line.contains("*/");
                    }
                    if (!comment && lineValid(line))
                    {
                        lines++;
                    }
                }
            }
            else if (root.isDirectory())
            {
                for (File child : root.listFiles(FILEFILTER))
                {
                    lines += countSLOC(child);
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }
        return lines;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        File root = null;
        if (args.length > 0)
        {
            root = new File(args[0]);
            if (!root.exists())
            {
                root = null;
            }
        }
        if (root == null)
        {
            JFileChooser jfc = new JFileChooser(new File("."));
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showOpenDialog(null);
            root = jfc.getSelectedFile();
        }
        int lines = countSLOC(root);
        System.out.println("Total sloc: " + lines);
    }

}
