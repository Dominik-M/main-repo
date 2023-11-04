/*
 * Copyright (C) 2022 Dominik Messerschmidt
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
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import main.Actor;
import main.Board;
import main.Board.Field;
import main.Game;
import main.GoldItem;
import main.Item;

/**
 *
 * @author Dominik Messerschmidt
 */
public class BoardPanel extends javax.swing.JPanel
{

    public static final int BOARD_WIDTH = 26,
            BOARD_HEIGHT = 19,
            SCALED_BOARD_WIDTH = 1929,
            SCALED_BOARD_HEIGHT = 1246,
            OFFSET_X = 2,
            OFFSET_Y = 5,
            TILE_WIDTH = 66,
            TILE_HEIGHT = 65,
            WALL_SIZE = 4,
            UPDATE_INTERVAL = 50,
            MOVE_STEP = 8;
    public static final boolean DRAW_WALLS = true;

    private Image bgImage, fieldImageGreen, fieldImageBlue, cursorImage, shade60Image, shade90Image, stairsImage, trapImage, goldImage;
    private final Image[] actorIcons = new Image[Actor.ActorType.values().length];
    private int viewX, viewY, mouseX, mouseY, dragX, dragY;
    private boolean moveLeft, moveRight, moveUp, moveDown, dragging, editMode;
    private String tooltip;

    private final Timer updateTimer = new Timer(UPDATE_INTERVAL, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (moveLeft)
            {
                viewX += MOVE_STEP;
            }
            if (moveRight)
            {
                viewX -= MOVE_STEP;
            }
            if (moveUp)
            {
                viewY += MOVE_STEP;
            }
            if (moveDown)
            {
                viewY -= MOVE_STEP;
            }
            repaint();
        }
    });

    private final KeyAdapter keyHandler = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent ke)
        {
            //System.out.println("KeyPressed(): " + ke.getKeyChar());
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    moveLeft = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRight = true;
                    break;
                case KeyEvent.VK_UP:
                    moveUp = true;
                    break;
                case KeyEvent.VK_DOWN:
                    moveDown = true;
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    if (!editMode)
                    {
                        if (!MainFrame.getInstance().popMessage())
                        {
                            Game.getInstance().turn();
                            MainFrame.getInstance().popMessage();
                        }
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke)
        {
            //System.out.println("KeyReleased(): " + ke.getKeyChar());
            switch (ke.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    moveLeft = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRight = false;
                    break;
                case KeyEvent.VK_UP:
                    moveUp = false;
                    break;
                case KeyEvent.VK_DOWN:
                    moveDown = false;
                    break;
            }
        }
    };

    private final MouseAdapter mouseHandler = new MouseAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent me)
        {
            //System.out.println("mouseMoved");
            mouseX = me.getX();
            mouseY = me.getY();
            int gridX = getGridX(mouseX);
            int gridY = getGridY(mouseY);
            Field f = Game.getInstance().getBoard().getField(gridX, gridY);
            Actor a = Game.getInstance().getActorAt(gridX, gridY);
            tooltip = "";
            if (f != null)
            {
                tooltip += f.x + "|" + f.y + "\n";
                if (f.getTrap() && f.isTrapVisible())
                {
                    tooltip += "Trap\n";
                }
                if (f.getItem() != null)
                {
                    tooltip += f.getItem() + "\n";
                }
            }
            if (a != null)
            {
                tooltip += a.toString() + "\n";
            }
        }

        @Override
        public void mouseDragged(MouseEvent me)
        {
            //System.out.println("mouseDragged");
            mouseX = me.getX();
            mouseY = me.getY();
            if (dragging)
            {
                viewX -= (dragX - me.getX());
                viewY -= (dragY - me.getY());
                dragX = me.getX();
                dragY = me.getY();
            }
        }

        @Override
        public void mousePressed(MouseEvent me)
        {
            if (me.getButton() == MouseEvent.BUTTON3)
            {
                dragging = true;
                dragX = me.getX();
                dragY = me.getY();
            }
        }

        @Override
        public void mouseReleased(MouseEvent me)
        {
            if (me.getButton() == MouseEvent.BUTTON3)
            {
                dragging = false;
            }
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
            if (me.getButton() == MouseEvent.BUTTON1)
            {
                int gridX = getGridX(me.getX());
                int gridY = getGridY(me.getY());
                if (editMode)
                {
                    MainFrame.getInstance().placeItemFromPallette(gridX, gridY);
                }
                else
                {
                    Game.getInstance().select(gridX, gridY);
                }
            }
            else if (me.getButton() == MouseEvent.BUTTON3)
            {
                int gridX = getGridX(me.getX());
                int gridY = getGridY(me.getY());
                if (editMode)
                {
                    MainFrame.getInstance().removeItemFromPallette(gridX, gridY);
                }
                else
                {
                    Game.getInstance().select(-1, -1);
                }
            }
        }

    };

    /**
     * Creates new form BoardPanel
     */
    public BoardPanel()
    {
        System.out.println("Create BoardPanel");
        init();
        initComponents();
    }

    private void init()
    {
        tooltip = "";
        editMode = false;
        viewX = 0;
        viewY = 0;
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        try
        {
            bgImage = ImageIO.read(new File("src/images/background.png")).getScaledInstance(SCALED_BOARD_WIDTH, SCALED_BOARD_HEIGHT, 0);
            fieldImageGreen = ImageIO.read(new File("src/images/fieldmarker_green.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            fieldImageBlue = ImageIO.read(new File("src/images/fieldmarker_blue.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            cursorImage = ImageIO.read(new File("src/images/cursor.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            shade60Image = ImageIO.read(new File("src/images/shade60.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            shade90Image = ImageIO.read(new File("src/images/shade90.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            stairsImage = ImageIO.read(new File("src/images/stairs.png")).getScaledInstance(TILE_WIDTH * 2, TILE_HEIGHT * 2, 0);
            trapImage = ImageIO.read(new File("src/images/trap.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            goldImage = ImageIO.read(new File("src/images/gold.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            for (int i = 0; i < actorIcons.length; i++)
            {
                actorIcons[i] = ImageIO.read(new File("src/images/" + Actor.ActorType.values()[i].name().toLowerCase() + "Icon.png")).getScaledInstance(TILE_WIDTH, TILE_HEIGHT, 0);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateTimer.start();
    }

    public void setEditMode(boolean editMode)
    {
        this.editMode = editMode;
    }

    public int getGridX(int screenX)
    {
        return (screenX - OFFSET_X - viewX) / TILE_WIDTH;
    }

    public int getGridY(int screenY)
    {
        return (screenY - OFFSET_Y - viewY) / TILE_HEIGHT;
    }

    public int getScreenX(int gridX)
    {
        return OFFSET_X + gridX * TILE_WIDTH;
    }

    public int getScreenY(int gridY)
    {
        return OFFSET_Y + gridY * TILE_HEIGHT;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int gridX, gridY;

        g.translate(viewX, viewY);

        g.drawImage(bgImage, 0, 0, this);

        g.setColor(Color.BLACK);
        for (gridX = 0; gridX < BOARD_WIDTH; gridX++)
        {
            for (gridY = 0; gridY < BOARD_HEIGHT; gridY++)
            {
                Field f = Game.getInstance().getBoard().getField(gridX, gridY);
                Board.WallState hwall = Game.getInstance().getBoard().getHWall(gridX, gridY);
                Board.WallState vwall = Game.getInstance().getBoard().getVWall(gridX, gridY);
                if (f.getTrap() && (f.isTrapVisible() || editMode))
                {
                    g.drawImage(trapImage, getScreenX(gridX), getScreenY(gridY), null);
                }
                Item item = f.getItem();
                if (item != null)
                {
                    if (GoldItem.class.isInstance(item))
                    {
                        g.drawImage(goldImage, getScreenX(gridX), getScreenY(gridY), null);
                    }
                }
                if (!hwall.isPassable())
                {
                    if (hwall == Board.WallState.DOOR_CLOSED)
                    {
                        g.setColor(Color.ORANGE.darker());
                        g.fillRect(getScreenX(gridX), getScreenY(gridY) - (WALL_SIZE + 4) / 2, TILE_WIDTH, WALL_SIZE + 4);
                    }
                    else
                    {
                        g.setColor(Color.BLACK);
                        g.fillRect(getScreenX(gridX), getScreenY(gridY) - WALL_SIZE / 2, TILE_WIDTH, WALL_SIZE);
                    }
                }
                if (!vwall.isPassable())
                {
                    if (vwall == Board.WallState.DOOR_CLOSED)
                    {
                        g.setColor(Color.ORANGE.darker());
                        g.fillRect(getScreenX(gridX) - (WALL_SIZE + 4) / 2, getScreenY(gridY), WALL_SIZE + 4, TILE_HEIGHT);
                    }
                    else
                    {
                        g.setColor(Color.BLACK);
                        g.fillRect(getScreenX(gridX) - WALL_SIZE / 2, getScreenY(gridY), WALL_SIZE, TILE_HEIGHT);
                    }
                }
                if (editMode)
                {
                    Actor.ActorType a = Game.getInstance().getBoard().getSpawn(gridX, gridY);
                    if (a != null)
                    {
                        g.drawImage(getActorIcon(a), getScreenX(gridX), getScreenY(gridY), null);
                    }
                }

            }
        }

        gridX = Game.getInstance().getBoard().getStartX();
        gridY = Game.getInstance().getBoard().getStartY();
        g.drawImage(stairsImage, getScreenX(gridX), getScreenY(gridY), null);

        for (Actor a : Game.getInstance().getActors())
        {
            gridX = a.getX();
            gridY = a.getY();
            g.drawImage(getActorIcon(a.getType()), getScreenX(gridX), getScreenY(gridY), null);
        }

        if (!editMode)
        {
            Field[] fieldsInSight = Game.getInstance().getCurrentFieldsInSight();
            for (gridX = 0; gridX < BOARD_WIDTH; gridX++)
            {
                for (gridY = 0; gridY < BOARD_HEIGHT; gridY++)
                {
                    boolean inSight = false;
                    for (Field f : fieldsInSight)
                    {
                        if (f.x == gridX && f.y == gridY)
                        {
                            inSight = true;
                            break;
                        }
                    }
                    if (!Game.getInstance().getBoard().getField(gridX, gridY).getVisible())
                    {
                        g.drawImage(shade90Image, getScreenX(gridX), getScreenY(gridY), null);
                    }
                    else if (!inSight)
                    {
                        g.drawImage(shade60Image, getScreenX(gridX), getScreenY(gridY), null);
                    }
                }
            }

            for (Field f : Game.getInstance().getCurrentFieldsInRange())
            {
                gridX = f.x;
                gridY = f.y;
                if (gridX >= 0 && gridY >= 0)
                {
                    g.drawImage(fieldImageBlue, getScreenX(gridX), getScreenY(gridY), null);
                }
            }

            gridX = Game.getInstance().getSelectedX();
            gridY = Game.getInstance().getSelectedY();
            if (gridX >= 0 && gridY >= 0)
            {
                g.drawImage(fieldImageGreen, getScreenX(gridX), getScreenY(gridY), null);
            }
        }

        gridX = getGridX(mouseX);
        gridY = getGridY(mouseY);
        g.drawImage(cursorImage, getScreenX(gridX), getScreenY(gridY), null);

        g.translate(-viewX, -viewY);

        // draw tooltip
        g.setFont(new java.awt.Font("Tahoma", 1, 16));
        g.setColor(Color.WHITE);
        int y = mouseY + 12;
        for (String line : tooltip.split("\n"))
        {
            g.drawString(line, mouseX + 16, y);
            y += 18;
        }

        // draw hint
//        g.setFont(new java.awt.Font("Tahoma", 1, 20));
//        String text = "Press Enter to continue";
//        if (messages.size() > 0)
//        {
//            text = messages.peek();
//        }
//        else
//        {
//            switch (Game.getInstance().getState())
//            {
//                case HEROES_TURN_ROLL:
//                    text = "Press Enter to roll the dice";
//                    break;
//                case HEROES_TURN_MOVE:
//                    if (!Game.getInstance().isInMoveRange(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY()))
//                    {
//                        text = "Select a Field in move range";
//                    }
//                    break;
//                case HEROES_TURN_ACTION:
//                case HEROES_TURN_MOVE_DOORAHEAD:
//                    if (Game.getInstance().getSelectedAction() == Game.Action.INVALID)
//                    {
//                        text = "Select an action for " + Game.getInstance().getActorOnTurn();
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//        g.drawString(text, getWidth() / 2 - text.length() * 5, getHeight() - 100);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private Image getActorIcon(Actor.ActorType a)
    {
        return actorIcons[a.ordinal()];
    }

    public void setViewToField(int gridX, int gridY)
    {
        viewX = getWidth() / 2 - getScreenX(gridX);
        viewY = getHeight() / 2 - getScreenY(gridY);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
