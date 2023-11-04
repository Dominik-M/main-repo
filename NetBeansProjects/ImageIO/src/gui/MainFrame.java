package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Utils;

public class MainFrame extends JFrame
{

    public static final int DEFAULT_WIDTH = 600, DEFAULT_HEIGHT = 400;
    private static final long serialVersionUID = -8026416994513756565L;

    public static final FileNameExtensionFilter FILE_FILTER = new FileNameExtensionFilter(
            "Image files", Utils.SUPPORTED_FORMATS);

    private JMenuBar menubar;

    private JMenu fileMenu;
    private JMenuItem newfileItem;
    private JMenuItem openfileItem;
    private JMenuItem pastefileItem;
    private JMenuItem saveItem;
    private JMenu exportMenu;

    private JMenu editMenu;
    private JMenuItem removeBlackItem;
    private JMenuItem removeColorItem;
    private JMenuItem removeAllItem;
    private JMenuItem replaceColorItem;
    private JMenuItem blendItem;

    private JMenu toolsMenu;
    private JMenuItem mergeItem;
    private JMenuItem expandItem;
    private JMenuItem shrinkItem;

    private ImagePanel image;

    private File currentFile;
    private String format;
    private boolean changed;

    private JFileChooser fileChooser;
    private JFileChooser directoryChooser;
    private final Dimension preferedDimension = new Dimension(8, 16);

    private final MouseAdapter mouseCallbacks = new MouseAdapter()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (image.getImage() != null)
            {
                int x = e.getX();
                int y = e.getY();
                int rgb = image.getImage().getRGB(x, y);
                System.out.println("Colorcode at " + x + "," + y + " = "
                        + ((rgb >> 24) & 0x000000FF) + ","
                        + ((rgb >> 16) & 0x000000FF) + ","
                        + ((rgb >> 8) & 0x000000FF) + ","
                        + (rgb & 0x000000FF));
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            if (image.getImage() != null)
            {
                int x = e.getX();
                int y = e.getY();
                image.setCursor(x, y);
                image.setDrag(x, y);
                image.repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if (image.getImage() != null)
            {
                int x = e.getX();
                int y = e.getY();
                image.setCursor(x, y);
                image.repaint();
            }
        }
    };

    public MainFrame()
    {
        super("ImageIO");
        init();
    }

    private void init()
    {
        // instantiate components
        menubar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        toolsMenu = new JMenu("Tools");
        newfileItem = new JMenuItem("New...");
        openfileItem = new JMenuItem("Open...");
        pastefileItem = new JMenuItem("Paste file...");
        saveItem = new JMenuItem("Save");
        exportMenu = new JMenu("Export as ...");
        removeBlackItem = new JMenuItem("Remove black pixels");
        removeAllItem = new JMenuItem("Remove all colors but...");
        replaceColorItem = new JMenuItem("Replace color...");
        removeColorItem = new JMenuItem("Make color transparent...");
        blendItem = new JMenuItem("Blend with color...");
        mergeItem = new JMenuItem("Merge to spritesheet...");
        expandItem = new JMenuItem("Expand spritesheet...");
        shrinkItem = new JMenuItem("Resize Images...");
        image = new ImagePanel();
        currentFile = null;
        changed = false;
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(FILE_FILTER);
        directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        // populate menu bar
        this.setJMenuBar(menubar);
        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(toolsMenu);

        fileMenu.add(newfileItem);
        fileMenu.add(openfileItem);
        fileMenu.add(pastefileItem);
        fileMenu.add(saveItem);
        fileMenu.add(exportMenu);

        // export menu
        JMenuItem exportItem = new JMenuItem("txt");
        exportItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (currentFile == null)
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                    return;
                }
                String path = currentFile.getAbsolutePath();
                String outputPath = path.substring(0, path.lastIndexOf('.') + 1) + "txt";
                Utils.writeImageData(image.getImage(), outputPath);
            }
        });
        exportMenu.add(exportItem);

        for (String format : Utils.SUPPORTED_FORMATS)
        {
            exportItem = new JMenuItem(format);
            exportItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    if (currentFile == null)
                    {
                        showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open",
                                "No imagefile open");
                        return;
                    }
                    saveAs(((JMenuItem) arg0.getSource()).getText());
                }
            });
            exportMenu.add(exportItem);
        }

        // edit menu
        editMenu.add(removeBlackItem);
        editMenu.add(removeColorItem);
        editMenu.add(removeAllItem);
        editMenu.add(replaceColorItem);
        editMenu.add(blendItem);

        // set Callbacks
        image.addMouseListener(mouseCallbacks);
        image.addMouseMotionListener(mouseCallbacks);

        newfileItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (changed)
                {
                    int result = JOptionPane.showConfirmDialog(null, "Save changes",
                            "Save changes?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.NO_OPTION)
                    {
                        changed = false;
                    }
                    else if (result == JOptionPane.YES_OPTION)
                    {
                        changed = !save();
                    }
                }
                if (!changed)
                {
                    Dimension d = requestDimension(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT), "Enter the new image size");
                    createNewImage(d.width, d.height);
                }
            }
        });

        openfileItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                open();
            }
        });

        pastefileItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (image.getImage() != null)
                {
                    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    {
                        File f = fileChooser.getSelectedFile();
                        System.out.println("Pasting image from file " + f);
                        try
                        {
                            BufferedImage img = ImageIO.read(f);
                            Utils.blend(image.getImage(), img);
                            repaint();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
                else
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                }
            }
        });

        removeBlackItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (image.getImage() != null)
                {
                    Image img = Utils.makeColorTransparent(image.getImage(), 0);
                    image.setImage(Utils.toBufferedImage(img));
                    repaint();
                }
                else
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                }
            }
        });

        removeColorItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (image.getImage() != null)
                {
                    Color in = requestColor();
                    if (in != null)
                    {
                        System.out.println("Removing colorcode " + in.getRGB());
                        Image img = Utils.makeColorTransparent(image.getImage(), in.getRGB());
                        image.setImage(Utils.toBufferedImage(img));
                    }
                    else
                    {
                        System.out.println("Removing all pixels aborted");
                    }
                    repaint();
                }
                else
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                }
            }
        });

        removeAllItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (image.getImage() != null)
                {
                    Color in = requestColor();
                    if (in != null)
                    {
                        System.out.println("Removing all pixels except with colorcode "
                                + in.getRGB());
                        Image img = Utils.makeColorTransparentInverse(image.getImage(), in.getRGB());
                        image.setImage(Utils.toBufferedImage(img));
                    }
                    else
                    {
                        System.out.println("Removing all pixels aborted");
                    }
                    repaint();
                }
                else
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                }
            }
        });

        replaceColorItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (image.getImage() != null)
                {
                    Color target = requestColor("Choose the color value to replace(target)");
                    Color source = requestColor("Choose the color to insert instead(source)");
                    if (target != null && source != null)
                    {
                        System.out.println("Replacing colorcode " + target.getRGB() + " with "
                                + source.getRGB());
                        Utils.replaceColor(image.getImage(), target.getRGB(), source.getRGB());
                        // image.setImage(Utils.toBufferedImage(img));
                    }
                    else
                    {
                        System.out.println("Removing all pixels aborted");
                    }
                    repaint();
                }
                else
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                }
            }
        });

        blendItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (image.getImage() != null)
                {
                    Color in = requestColor();
                    if (in != null)
                    {
                        System.out.println("Blending image with colorcode " + in.getRGB());
                        Image img = Utils.blend(image.getImage(), in.getRGB());
                        image.setImage(Utils.toBufferedImage(img));
                    }
                    else
                    {
                        System.out.println("Blending all pixels aborted");
                    }
                    repaint();
                }
                else
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                }
            }
        });

        saveItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (currentFile == null && fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    currentFile = fileChooser.getSelectedFile();
                }
                if (save())
                {
                    System.out.println("Image saved in " + currentFile);
                }
                else
                {
                    System.out.println("Failed to save image");
                }
            }
        });

        // tools menu
        toolsMenu.add(mergeItem);
        toolsMenu.add(expandItem);
        toolsMenu.add(shrinkItem);

        mergeItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                File[] dirs = chooseDirectories();
                if (dirs == null)
                {
                    return;
                }
                // Dimension d = requestDimension(preferedDimension,
                // "Enter spritesheet dimension [cols,rows]");
                // if (d == null)
                // return;
                // System.out.println("Entered dimension: " + d);
                for (File dir : dirs)
                {
                    System.out.println("Creating spritesheet from directory "
                            + dir.getAbsolutePath());
                    BufferedImage spritesheet = Utils.mergeToSpriteSheet(dir);
                    if (spritesheet != null)
                    {
                        File outputfile = new File(new File("").getAbsolutePath() + "/"
                                + dir.getName() + "_spritesheet_.png");
                        System.out.println("Writing spritesheet to file " + outputfile.getName());
                        try
                        {
                            ImageIO.write(spritesheet, "png", outputfile);
                            System.out.println("Succesfully created spritesheet");
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            System.err.println("Failed to write spritesheet");
                        }
                    }
                    else
                    {
                        System.err.println("Failed to create spritesheet");
                    }
                }
            }
        });

        expandItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (image.getImage() != null)
                {
                    Dimension imagesize = requestDimension(new Dimension(80, 140), "Enter single image Dimensions");
                    if (imagesize != null)
                    {
                        Dimension imagesizeScaled = requestDimension(imagesize, "Enter Scaled Dimensions (Optional)");
                        if (imagesizeScaled == null)
                        {
                            imagesizeScaled = imagesize;
                        }
                        System.out.println("Extracting Spritesheet with Dimension: " + imagesize);
                        BufferedImage[] images = Utils.extractSpritesheetImages(image.getImage(), imagesize);
                        int i = 0;
                        for (BufferedImage img : images)
                        {
                            Image scaledImg = img.getScaledInstance(imagesizeScaled.width, imagesizeScaled.height, 0);
                            File outputfile = new File(currentFile.getParentFile().getAbsolutePath() + "/" + currentFile.getName().substring(0, currentFile.getName().lastIndexOf(".") - 1) + "_" + i++ + ".png");
                            System.out.println("Writing spritesheet to file " + outputfile.getName());
                            try
                            {
                                ImageIO.write(Utils.toBufferedImage(scaledImg), "png", outputfile);
                                System.out.println("Succesfully extracted spritesheet");
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                                System.err.println("Failed to save extracted spritesheet");
                            }

                        }
                    }
                    else
                    {
                        System.out.println("Invalid input, Spritesheet extraction aborted");
                    }
                    repaint();
                }
                else
                {
                    showMessage(JOptionPane.ERROR_MESSAGE, "No imagefile open", "No imagefile open");
                }
            }
        });

        shrinkItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                File dir = chooseDirectory();
                if (dir != null && dir.exists() && dir.isDirectory())
                {
                    new File(dir.getAbsolutePath() + "/resized").mkdir();
                    Dimension size = requestDimension(new Dimension(100, 50), "Enter the image dimension");
                    if (Utils.resizeImageFiles(size, dir.listFiles()))
                    {
                        System.out.println("Successfully resized images");
                    }
                    else
                    {
                        System.err.println("Failed to resize images");
                    }
                }
                else
                {
                    System.err.println("Failed to open image directory");
                }
            }
        });

        // init Frame
        setLayout(new BorderLayout());
        getContentPane().add(image, BorderLayout.CENTER);
        setLocation(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public void showMessage(int messageType, String title, String message)
    {
        JOptionPane.showMessageDialog(this, title, message, messageType);
    }

    public int requestInt(String title)
    {
        int retVal = -1;

        return retVal;
    }

    public Color requestColor(String message)
    {
        Color retVal = null;

        retVal = JColorChooser.showDialog(this, message, Color.black);

        return retVal;
    }

    public Color requestColor()
    {
        Color retVal = null;

        retVal = requestColor("Choose a color");

        return retVal;
    }

    public Dimension requestDimension(Dimension preference, String message)
    {
        Dimension d = null;
        String input = JOptionPane.showInputDialog(this, message, "Enter Dimension",
                JOptionPane.PLAIN_MESSAGE, null, null, preference.width + "," + preference.height)
                .toString();
        if (input != null && input.contains(","))
        {
            String[] args = input.split(",");
            if (args.length > 1)
            {
                try
                {
                    d = new Dimension(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                }
                catch (Exception ex)
                {
                    System.err.println("requestDimension(): Failed to parse input - " + input);
                }
            }
        }
        else
        {
            System.out.println("requestDimension(): Canceled");
        }
        return d;
    }

    public void setImage(BufferedImage img)
    {
        image.setImage(img);
        image.setScale(1);
        pack();
    }

    public boolean saveAs(String format, File file)
    {
        if (file == null)
        {
            return false;
        }
        try
        {
            return ImageIO.write(image.getImage(), format, file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAs(String format)
    {
        if (currentFile == null)
        {
            return false;
        }
        try
        {
            String path = currentFile.getAbsolutePath();
            File file = new File(path.substring(0, path.lastIndexOf('.') + 1) + format);
            if (ImageIO.write(image.getImage(), format, file))
            {
                System.out.println("Image saved in " + file);
                return true;
            }
            else
            {
                System.out.println("Failed to save image in " + file);
                return false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save()
    {
        return saveAs(format, currentFile);
    }

    public void createNewImage(int width, int height)
    {
        setTitle("New image");
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                img.setRGB(x, y, 0xFFFFFFFF);
            }
        }
        setImage(img);
        image.setScale(1);
    }

    public boolean open()
    {
        if (changed)
        {
            int result = JOptionPane.showConfirmDialog(null, "Save changes", "Save changes?",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.NO_OPTION)
            {
                changed = false;
            }
            else if (result == JOptionPane.YES_OPTION)
            {
                changed = !save();
            }
        }
        if (!changed)
        {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                return open(fileChooser.getSelectedFile());
            }
        }
        return false;
    }

    public boolean open(File f)
    {
        try
        {
            BufferedImage img = ImageIO.read(f);
            setImage(img);
            currentFile = f;
            format = f.getName().substring(1 + f.getName().lastIndexOf('.'));
            setTitle(f.getAbsolutePath() + " - " + format);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public File chooseDirectory()
    {
        if (changed)
        {
            int result = JOptionPane.showConfirmDialog(null, "Save changes", "Save changes?",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.NO_OPTION)
            {
                changed = false;
            }
            else if (result == JOptionPane.YES_OPTION)
            {
                changed = !save();
            }
        }
        if (!changed)
        {
            directoryChooser.setMultiSelectionEnabled(false);
            if (directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File dir = directoryChooser.getSelectedFile();
                return dir;
            }
        }
        return null;
    }

    public File[] chooseDirectories()
    {
        if (changed)
        {
            int result = JOptionPane.showConfirmDialog(null, "Save changes", "Save changes?",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.NO_OPTION)
            {
                changed = false;
            }
            else if (result == JOptionPane.YES_OPTION)
            {
                changed = !save();
            }
        }
        if (!changed)
        {
            directoryChooser.setMultiSelectionEnabled(true);
            if (directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File[] dirs = directoryChooser.getSelectedFiles();
                return dirs;
            }
        }
        return null;
    }

    public static void setNimbusLookAndFeel()
    {
        /* Set the Nimbus look and feel */
 /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
