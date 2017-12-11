/**
 * Copyright (C) 2016 Dominik Messerschmidt
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
package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import main.Item;
import platform.utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 08.02.2017
 */
public class InventoryPanel extends JPanel
{

    public static final String ICON_DIRECTORY = "/graphic/icons/";
    public static final String DEFAULT_ITEM_IMAGE = "abstract.png";
    public static final String EMPTY_ITEM_IMAGE = "box.png";

    class ItemBox extends JComponent
    {

        private Item item;
        private boolean focused, selected, active;
        private final MouseListener mouseListener = new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (active && item != null)
                {
                    selected = !selected;
                    if (selected)
                    {
                        if (!multiSel)
                        {
                            clearSelection();
                            selected = true;
                        }
                        selectedItems.add(item);
                    }
                    else
                    {
                        selectedItems.remove(item);
                    }
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                focused = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                focused = false;
                repaint();
            }
        };

        ItemBox(Item item)
        {
            this.item = item;
            focused = false;
            selected = false;
            active = true;
            init();
        }

        private void init()
        {
            this.addMouseListener(mouseListener);
            if (item != null)
            {
                setToolTipText(item.toString());
            }
            setPreferredSize(new Dimension(64, 64));
        }

        public Item getItem()
        {
            return item;
        }

        public void setItem(Item item)
        {
            this.item = item;
            if (item != null)
            {
                setToolTipText(item.toString());
            }
            else
            {
                setToolTipText(null);
            }
        }

        public void setActive(boolean active)
        {
            this.active = active;
        }

        public boolean isActive()
        {
            return active;
        }

        public ImageIcon getIcon()
        {
            if (item != null)
            {
                try
                {
                    return new ImageIcon(getClass().getResource(ICON_DIRECTORY + item.type.getImagename()));
                } catch (Exception ex)
                {
                    IO.println("Icon " + item.type.getImagename() + " not found", IO.MessageType.ERROR);
                }
                return new ImageIcon(getClass().getResource(ICON_DIRECTORY + DEFAULT_ITEM_IMAGE));
            }
            else
            {
                return new ImageIcon(getClass().getResource(ICON_DIRECTORY + EMPTY_ITEM_IMAGE));
            }
        }

        @Override
        public void paintComponent(Graphics g)
        {
            if (active && (focused || selected))
            {
                if (selected)
                {
                    g.setColor(Color.green);
                }
                else
                {
                    g.setColor(Color.red);
                }
                g.fillRect(0, 0, 64, 64);
            }
            g.drawImage(getIcon().getImage(), 8, 8, this);
            if (item != null && item.getNumber() > 1)
            {
                g.setFont(new Font("Consolas", 1, 16));
                g.setColor(Color.black);
                g.drawString(item.getNumber() + "x", 0, 20);
            }
        }
    }

    private final int GRID_WIDTH, GRID_HEIGHT;
    private final LinkedList<ItemBox> itemBoxs = new LinkedList<>();
    private final LinkedList<Item> selectedItems = new LinkedList<>();
    private boolean multiSel;

    public InventoryPanel(int gridWidth, int gridHeight, Item... items)
    {
        GRID_WIDTH = gridWidth;
        GRID_HEIGHT = gridHeight;
        init();
        setItems(items);
    }

    private void init()
    {
        this.setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH, 10, 10));
        for (int i = 0; i < GRID_WIDTH * GRID_HEIGHT; i++)
        {
            ItemBox box = new ItemBox(null);
            this.add(box);
            itemBoxs.add(box);
        }
        multiSel = true;
    }

    public final void setItems(Item... items)
    {
        for (int i = 0; i < GRID_WIDTH * GRID_HEIGHT; i++)
        {
            if (i < items.length)
            {
                itemBoxs.get(i).setItem(items[i]);
            }
            else
            {
                itemBoxs.get(i).setItem(null);
            }
        }
    }

    @Override
    public void addMouseListener(MouseListener l)
    {
        super.addMouseListener(l);
        for (ItemBox box : itemBoxs)
        {
            box.addMouseListener(l);
        }
    }

    @Override
    public void removeMouseListener(MouseListener l)
    {
        super.removeMouseListener(l);
        for (ItemBox box : itemBoxs)
        {
            box.removeMouseListener(l);
        }
    }

    public LinkedList<Item> getSelectedItems()
    {
        return selectedItems;
    }

    public void clearSelection()
    {
        selectedItems.clear();
        for (ItemBox box : itemBoxs)
        {
            box.selected = false;
        }
    }

    public void setMultiSelectable(boolean multi)
    {
        multiSel = multi;
    }
}
