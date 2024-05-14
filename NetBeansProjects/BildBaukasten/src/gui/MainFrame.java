/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Element;
import main.Settings;
import main.Utils;

/**
 *
 * @author Dominik Messerschmidt
 */
public class MainFrame extends javax.swing.JFrame
{

    public static final java.awt.Cursor MOVE_CURSOR = new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR),
            DEFAULT_CURSOR = new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR);

    public static final FileNameExtensionFilter FILE_FILTER = new FileNameExtensionFilter(
            "Image files", Utils.SUPPORTED_FORMATS);

    private boolean changed, moveMode;
    private File currentFile;
    private String format;
    private LinkedList<Element> elements;

    private JFileChooser fileChooser;
    private JFileChooser directoryChooser;
    private final MouseAdapter mouseCallbacks = new MouseAdapter()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            image.requestFocus();
            int x = e.getX();
            int y = e.getY();
            if (e.getButton() == MouseEvent.BUTTON1) // Leftclick
            {
                leftClickAt(x, y);
            }
            else if (e.getButton() == MouseEvent.BUTTON3) // Rightclick
            {
                rightClickAt(x, y);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            LinkedList<Element> sel = getElementsAt(x, y);
            boolean selected = false;
            for (Element element : sel)
            {
                if (element.selected)
                {
                    selected = true;
                    break;
                }
            }
            if (selected)
            {
                //TODO if(nearBorder) resizeMode;

                setCursor(MOVE_CURSOR);
                moveMode = true;
            }
            else
            {
                setCursor(DEFAULT_CURSOR);
                moveMode = false;
            }
            if (image != null)
            {
                image.setCursor(x, y);
                image.setDrag(x, y);
                image.repaint();
            }
            updateInfo();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();

            if (moveMode)
            {
                for (Element element : elements)
                {
                    if (element != null && element.selected)
                    {
                        int dX = x - image.getDragX();
                        int dY = y - image.getDragY();
                        element.setX(element.getX() + dX);
                        element.setY(element.getY() + dY);
                    }
                }
                if (image != null)
                {
                    image.setDrag(x, y);
                    image.buildImage(elements);
                }
            }

            if (image != null)
            {
                image.setCursor(x, y);
                image.repaint();
            }
            updateInfo();
        }

        @Override
        public void mouseReleased(MouseEvent me)
        {
            //TODO try select elements within drag frame

            image.setDrag(me.getX(), me.getY());
            image.repaint();
        }

    };

    private final KeyAdapter keyHandler = new KeyAdapter()
    {
        @Override
        public void keyReleased(KeyEvent ke)
        {
            System.out.println("KeyReleased: " + ke.getKeyChar());
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_DELETE:
                    removeSelectedPixels();
                    image.clearSelectedPixels();
                    image.buildImage(elements);
                    image.repaint();
                    break;
            }
        }

    };

    /**
     * Creates new form MainFrame
     */
    public MainFrame()
    {
        initComponents();
        changed = false;
        fileChooser = new JFileChooser(new File(Settings.getInstance().getDefaultDirectory()));
        fileChooser.setFileFilter(FILE_FILTER);
        fileChooser.setMultiSelectionEnabled(true);
        directoryChooser = new JFileChooser(fileChooser.getCurrentDirectory());
        directoryChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        currentFile = null;
        format = "png";
        elements = new LinkedList<>();
        scene.setModel(new javax.swing.AbstractListModel<String>()
        {
            public int getSize()
            {
                return elements.size();
            }

            public String getElementAt(int i)
            {
                return elements.get(i).name;
            }
        });
        image.addMouseListener(mouseCallbacks);
        image.addMouseMotionListener(mouseCallbacks);
        image.addKeyListener(keyHandler);
        updateInfo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        scene = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        image = new gui.ImagePanel();
        jSeparator1 = new javax.swing.JSeparator();
        infoLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openItem = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        formatMenu = new javax.swing.JMenu();
        pngFormatButton = new javax.swing.JRadioButtonMenuItem();
        gifFormatButton = new javax.swing.JRadioButtonMenuItem();
        jpgFormatButton = new javax.swing.JRadioButtonMenuItem();
        bmpFormatButton = new javax.swing.JRadioButtonMenuItem();
        jMenu2 = new javax.swing.JMenu();
        makeTransparentItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        settingsItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));

        jLabel1.setText("Scene");

        scene.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        scene.setDragEnabled(true);
        scene.setDropMode(javax.swing.DropMode.INSERT);
        scene.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                sceneKeyReleased(evt);
            }
        });
        scene.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                sceneValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(scene);

        javax.swing.GroupLayout imageLayout = new javax.swing.GroupLayout(image);
        image.setLayout(imageLayout);
        imageLayout.setHorizontalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 842, Short.MAX_VALUE)
        );
        imageLayout.setVerticalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(image);

        infoLabel.setText("jLabel2");

        jMenu1.setText("File");

        openItem.setText("Open");
        openItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                openItemActionPerformed(evt);
            }
        });
        jMenu1.add(openItem);

        saveItem.setText("Save");
        saveItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveItem);

        formatMenu.setText("Export as");

        buttonGroup1.add(pngFormatButton);
        pngFormatButton.setSelected(true);
        pngFormatButton.setText("png");
        pngFormatButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                pngFormatButtonActionPerformed(evt);
            }
        });
        formatMenu.add(pngFormatButton);

        buttonGroup1.add(gifFormatButton);
        gifFormatButton.setText("gif");
        gifFormatButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                gifFormatButtonActionPerformed(evt);
            }
        });
        formatMenu.add(gifFormatButton);

        buttonGroup1.add(jpgFormatButton);
        jpgFormatButton.setText("jpg");
        jpgFormatButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jpgFormatButtonActionPerformed(evt);
            }
        });
        formatMenu.add(jpgFormatButton);

        buttonGroup1.add(bmpFormatButton);
        bmpFormatButton.setText("bmp");
        bmpFormatButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bmpFormatButtonActionPerformed(evt);
            }
        });
        formatMenu.add(bmpFormatButton);

        jMenu1.add(formatMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        makeTransparentItem.setText("Make color transparent");
        makeTransparentItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                makeTransparentItemActionPerformed(evt);
            }
        });
        jMenu2.add(makeTransparentItem);
        jMenu2.add(jSeparator2);

        settingsItem.setText("Settings");
        settingsItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                settingsItemActionPerformed(evt);
            }
        });
        jMenu2.add(settingsItem);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tools");

        jMenuItem1.setText("Paste Logo in all files in directory");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sceneValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_sceneValueChanged
    {//GEN-HEADEREND:event_sceneValueChanged
        System.out.println("sceneValueChanged()");
        updateSelection();
        image.buildImage(elements);
    }//GEN-LAST:event_sceneValueChanged

    private void openItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_openItemActionPerformed
    {//GEN-HEADEREND:event_openItemActionPerformed
        open();
    }//GEN-LAST:event_openItemActionPerformed

    private void saveItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveItemActionPerformed
    {//GEN-HEADEREND:event_saveItemActionPerformed
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
    }//GEN-LAST:event_saveItemActionPerformed

    private void pngFormatButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pngFormatButtonActionPerformed
    {//GEN-HEADEREND:event_pngFormatButtonActionPerformed
        setFormat("png");
    }//GEN-LAST:event_pngFormatButtonActionPerformed

    private void gifFormatButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_gifFormatButtonActionPerformed
    {//GEN-HEADEREND:event_gifFormatButtonActionPerformed
        setFormat("gif");
    }//GEN-LAST:event_gifFormatButtonActionPerformed

    private void jpgFormatButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jpgFormatButtonActionPerformed
    {//GEN-HEADEREND:event_jpgFormatButtonActionPerformed
        setFormat("jpg");
    }//GEN-LAST:event_jpgFormatButtonActionPerformed

    private void bmpFormatButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bmpFormatButtonActionPerformed
    {//GEN-HEADEREND:event_bmpFormatButtonActionPerformed
        setFormat("bmp");
    }//GEN-LAST:event_bmpFormatButtonActionPerformed

    private void makeTransparentItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_makeTransparentItemActionPerformed
    {//GEN-HEADEREND:event_makeTransparentItemActionPerformed
        System.out.println("Make color transparent...");

        LinkedList<Element> sel = getSelectedElements();
        if (sel.isEmpty())
        {
            showMessage(JOptionPane.ERROR_MESSAGE, "Warning", "No elements selected. Action aborted.");
            return;
        }
        Color in = requestColor();
        if (in != null)
        {
            if (image != null)
            {
                for (Element e : sel)
                {
                    System.out.println("Removing colorcode " + in.getRGB());
                    Image img = Utils.makeColorTransparent(e.image, in.getRGB());
                    e.image = Utils.toBufferedImage(img);
                }
                image.buildImage(elements);
                repaint();
            }
        }
        else
        {
            System.out.println("Removing all pixels aborted");
        }
    }//GEN-LAST:event_makeTransparentItemActionPerformed

    private void sceneKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_sceneKeyReleased
    {//GEN-HEADEREND:event_sceneKeyReleased
        switch (evt.getKeyCode())
        {
            case KeyEvent.VK_DELETE:
                removeElements(getSelectedElements());
                break;
        }
    }//GEN-LAST:event_sceneKeyReleased

    private void settingsItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_settingsItemActionPerformed
    {//GEN-HEADEREND:event_settingsItemActionPerformed
        new OptionsFrame(this).setVisible(true);
    }//GEN-LAST:event_settingsItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed

        try
        {
            BufferedImage logo = ImageIO.read(new File(Settings.getInstance().getLogoPath()));
            if (directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File dir = directoryChooser.getSelectedFile();
                System.out.println("pasteLogoOnAllImagesInDirectory(): " + dir);
                Utils.pasteLogoOnAllImagesInDirectory(logo, dir, format);
            }
            else
            {
                System.out.println("File chooser dialog aborted");
            }
        }
        catch (IOException ex)
        {
            System.err.println("Failed to open logo file: " + Settings.getInstance().getLogoPath());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public static void setNimbusLookAndFeel()
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
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButtonMenuItem bmpFormatButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenu formatMenu;
    private javax.swing.JRadioButtonMenuItem gifFormatButton;
    private gui.ImagePanel image;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JRadioButtonMenuItem jpgFormatButton;
    private javax.swing.JMenuItem makeTransparentItem;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JRadioButtonMenuItem pngFormatButton;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JList<String> scene;
    private javax.swing.JMenuItem settingsItem;
    // End of variables declaration//GEN-END:variables

    public void setFormat(String format)
    {
        this.format = format;
        if (Utils.isSupportedFormat(format))
        {
            System.out.println("Changed file format to " + format);
        }
        else
        {
            System.err.println("File format not supported: " + format);
        }
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

    public void updateInfo()
    {
        String text = "";

        if (currentFile != null)
        {
            text = "File: " + currentFile.getName();
            if (changed)
            {
                text = text + "*";
            }
            text = text + " ";
        }
        else
        {
            text = "No file selected. ";
        }
        if (image != null)
        {
            int x = image.getCursorX();
            int y = image.getCursorY();
            text = text + " Cursor at X=" + x + " Y=" + y + " ";
            if (image.getImage() != null)
            {
                if (x >= 0 && x < image.getImage().getWidth() && y >= 0 && y < image.getImage().getHeight())
                {
                    int rgb = image.getImage().getRGB(x, y);
                    text = text + "Colorcode = "
                            + ((rgb >> 24) & 0x000000FF) + ","
                            + ((rgb >> 16) & 0x000000FF) + ","
                            + ((rgb >> 8) & 0x000000FF) + ","
                            + (rgb & 0x000000FF);
                }
            }
        }
        if (moveMode)
        {
            text = text + "\t\t Click and drag to move elements.";
        }

        infoLabel.setText(text);
    }

    public void setSelected(Element e)
    {
        if (e == null)
        {
            scene.clearSelection();
        }
        else
        {
            scene.setSelectedValue(e.name, true);
        }
    }

    public JList getSceneList()
    {
        return scene;
    }

    public LinkedList<Element> getSelectedElements()
    {
        LinkedList<Element> sel = new LinkedList<>();
        for (Element e : elements)
        {
            if (e.selected)
            {
                sel.add(e);
            }
        }
        return sel;
    }

    public void updateSelection()
    {
        boolean isAnySelected = false;
        for (int i = 0; i < elements.size(); i++)
        {
            elements.get(i).selected = scene.isSelectedIndex(i);
            isAnySelected = isAnySelected || elements.get(i).selected;
        }
        makeTransparentItem.setEnabled(isAnySelected);
    }

    public void updateList()
    {
        scene.updateUI();
        updateSelection();
        image.buildImage(elements);
    }

    public void addImage(String name, BufferedImage img)
    {
        elements.add(new Element(img, name));
        updateList();
    }

    public boolean save()
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

    public void open()
    {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            open(fileChooser.getSelectedFiles());
        }
    }

    public void open(File... files)
    {
        for (File f : files)
        {
            try
            {
                BufferedImage img = ImageIO.read(f);
                addImage(f.getName(), img);
                System.out.println("Opened file: " + f.getName());

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public LinkedList<Element> getElementsAt(int x, int y)
    {
        LinkedList<Element> sel = new LinkedList<>();
        Point p = new Point(x, y);
        for (Element i : elements)
        {
            Rectangle bounds = new Rectangle(i.getX(), i.getY(), i.getScaledWidth(), i.getScaledHeight());
            if (bounds.contains(p))
            {
                sel.add(i);
            }
        }
        return sel;
    }

    private void removeElements(LinkedList<Element> selectedElements)
    {
        elements.removeAll(selectedElements);
        updateList();
    }

    private void leftClickAt(int x, int y)
    {
        if (image.getImage() != null)
        {
            if (x >= 0 && x < image.getImage().getWidth()
                    && y >= 0 && y < image.getImage().getHeight())
            {
                int rgb = image.getImage().getRGB(x, y);
                System.out.println("Colorcode at " + x + "," + y + " = "
                        + ((rgb >> 24) & 0x000000FF) + ","
                        + ((rgb >> 16) & 0x000000FF) + ","
                        + ((rgb >> 8) & 0x000000FF) + ","
                        + (rgb & 0x000000FF));
            }
        }
        LinkedList<Element> sel = getElementsAt(x, y);
        Element element = null;
        for (int i = 0; i < sel.size(); i++)
        {
            // select the next in the list to make a "click through"
            if (sel.get(i).selected)
            {
                if (i + 1 < sel.size())
                {
                    element = sel.get(i + 1);
                }
                else
                {
                    element = sel.get(0);
                }
                break;
            }
            else if (element == null)
            {
                element = sel.get(i);
            }
        }
        setSelected(element);
        updateList();
    }

    private void rightClickAt(int x, int y)
    {
        if (image != null)
        {
            image.selectPixelsAround(x, y, Settings.getInstance().getColorSelectThreshold());
        }
    }

    private void removeSelectedPixels()
    {
        System.out.println("removeSelectedPixels()");
        if (image != null && image.getImage() != null)
        {
            int width = image.getImage().getWidth();
            int height = image.getImage().getHeight();

            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    if (image.isPixelSelected(x, y))
                    {
                        for (Element e : getElementsAt(x, y))
                        {
                            e.image.setRGB(x, y, 0);
                        }
                    }
                }
            }
        }
    }
}
