package graphic;

import actors.Carrier;
import actors.Cruiser;
import actors.Ship;
import armory.Gun;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import main.Map;
import main.SpaceInvader;
import platform.gamegrid.GameData;
import platform.gamegrid.GameGrid;
import platform.graphic.InputConfig;
import platform.graphic.InputConfig.Key;
import platform.graphic.MainFrame;
import platform.utils.IO;
import platform.utils.Settings;
import platform.utils.Utilities;
import platform.utils.Vector2D;

public class GamePanel extends GameGrid implements MouseListener, MouseMotionListener
{

    public static final GamePanel INSTANCE = new GamePanel();
    private String hint;
    private int messageTimer = SpaceInvader.TIMEOUT_MESSAGE;
    private Image miniMapBG;
    private double miniMapScaleFactorX, miniMapScaleFactorY;
    private int creditsProgress = 0;
    private final int WIDTH, HEIGHT;
    private int mouseX, mouseY;

    private GamePanel()
    {
        super(null);
        setBlockOnPrint(false);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        WIDTH = getScreenWidth();
        HEIGHT = getScreenHeight();
    }

    @Override
    public void onDisselect()
    {
        setRunning(false);
    }

    @Override
    public GameData getGameData()
    {
        return SpaceInvader.getInstance();
    }

    private static final long serialVersionUID = 736492432226384265L;

    public void land()
    {
        Ship mShip = SpaceInvader.getInstance().getMShip();
        mShip.stop();
        MainFrame.FRAME.setMainPanel(new HangarPanel(mShip));
        //mShip.repair();
        if (SpaceInvader.save())
        {
            IO.println(IO.translate("SAVED"), IO.MessageType.IMPORTANT);
        }
    }

    private void createHint()
    {
        Ship mShip = SpaceInvader.getInstance().getMShip();
        if (SpaceInvader.getInstance().getLevelState() > 0)
        {
            hint = (IO.translate("LEVEL_COUNTDOWN")
                    + Utilities.round(1.0 * SpaceInvader.getInstance().getLevelState()
                            / ((Integer) Settings.get("fps")), 1) + "s");
        }
        else if (mShip != null && SpaceInvader.getInstance().getMap() != null
                && SpaceInvader.getInstance().isInLandingZone(mShip))
        {
            hint = (IO.translate("PRESS") + " " + InputConfig.Key.KEY_SELECT.toString() + " " + IO
                    .translate("TOLAND"));
        }
        else if (SpaceInvader.getInstance().getMode() == SpaceInvader.Mode.ARCADE)
        {
            int n = SpaceInvader.getInstance().getEnemyCount();
            if (n != 1)
            {
                hint = (IO.translate("LEVEL") + " " + SpaceInvader.getInstance().getLevel() + " - "
                        + n + " " + IO.translate("ENEMYSLEFT"));
            }
            else
            {
                hint = (IO.translate("LEVEL") + " " + SpaceInvader.getInstance().getLevel() + " - "
                        + n + " " + IO.translate("ENEMYLEFT"));
            }
        }
        else
        {
            hint = ("Map: " + SpaceInvader.getInstance().getMap());
        }
    }

    public void setMap(Map map)
    {
        this.miniMapBG = map.getImage().getScaledInstance(SpaceInvader.MINIMAP_BOUNDS.width,
                SpaceInvader.MINIMAP_BOUNDS.height, 0);
        miniMapScaleFactorX = 1.0 * SpaceInvader.MINIMAP_BOUNDS.width / map.WIDTH;
        miniMapScaleFactorY = 1.0 * SpaceInvader.MINIMAP_BOUNDS.height / map.HEIGHT;
        repaint();
    }

    @Override
    protected void drawBackground(Graphics2D g)
    {
        if (SpaceInvader.getInstance().getMap() != null)
        {
            SpaceInvader.getInstance().getMap().paint(g);
        }
    }

    @Override
    protected void drawInterface(Graphics2D g)
    {
        createHint();
        if (SpaceInvader.getInstance().isGameOver())
        {
            g.setColor(Color.red);
            g.setFont(new Font("Consolas", 0, 30));
            g.drawString(IO.translate("GAMEOVER"), WIDTH / 2 - 50, HEIGHT / 2);
            drawCredits(g);
        }
        else if (!isRunning())
        {
            g.setColor(Color.red);
            g.setFont(new Font("Consolas", 0, 30));
            g.drawString(IO.translate("PAUSED"), WIDTH / 2 - 50, HEIGHT / 2);
        }
        if (hint != null)
        {
            g.setColor((Color) Settings.get("fontcolor"));
            g.setFont(new Font("Consolas", 0, 24));
            g.drawString(hint, 10, 30);
        }
        if (!this.printQueueIsEmpty())
        {
            g.setColor((Color) Settings.get("fontcolor"));
            g.setFont((Font) Settings.get("font"));
            for (int i = 0; i < printQueue.size() && i < SpaceInvader.MAX_MESSAGE_COUNT; i++)
            {
                String msg = printQueue.get(printQueue.size() - i - 1);
                // TODO limit linewidth
                g.drawString(msg, 10, HEIGHT - i * 30);
            }
            if (messageTimer > 0)
            {
                messageTimer--;
            }
            else if (messageTimer == 0)
            {
                printNext();
                messageTimer = SpaceInvader.TIMEOUT_MESSAGE;
            }
        }
        g.setColor((Color) Settings.get("fontcolor"));
        g.setFont(new Font("Consolas", 0, 20));
        if (Math.abs(SpaceInvader.getInstance().getScore()) == 1)
        {
            g.drawString(IO.translate("SCORE") + ": " + SpaceInvader.getInstance().getScore() + " "
                    + IO.translate("CURRENCY_SINGULAR"), WIDTH - 300, 30);
        }
        else
        {
            g.drawString(IO.translate("SCORE") + ": " + SpaceInvader.getInstance().getScore() + " "
                    + IO.translate("CURRENCY_PLURAL"), WIDTH - 300, 30);
        }
        drawMinimap(g);
        drawShipControlPanel(g);
    }

    private void drawShipControlPanel(Graphics2D g)
    {
        Ship mShip = SpaceInvader.getInstance().getMShip();
        int hp = 0;
        int fuel = 0;
        int shield = 0;
        int speed = 0;
        int[] guns = new int[]
        {
        };
        String[] gunnames = new String[]
        {
        };
        if (mShip != null)
        {
            hp = 100 * mShip.getHP() / mShip.getMaxHp();
            fuel = 100 * SpaceInvader.getInstance().getFuel() / SpaceInvader.getInstance().getMaxFuel();
            shield = 100 * mShip.getShield() / mShip.getMaxShield();
            speed = (int) (100 * mShip.getSpeed() / mShip.getMaxSpeed());
            guns = new int[mShip.getWeapons().size()];
            gunnames = new String[guns.length];
            for (int i = 0; i < guns.length; i++)
            {
                Gun gun = (Gun) mShip.getWeapons().get(i);
                guns[i] = 100 * gun.getCooldown() / gun.getMaxCooldown();
                gunnames[i] = gun.toString();
            }
        }
        int x = SpaceInvader.SHIP_MONITOR_BOUNDS.x;
        int y = SpaceInvader.SHIP_MONITOR_BOUNDS.y;
        int width = SpaceInvader.SHIP_MONITOR_BOUNDS.width / 2;
        int height = SpaceInvader.SHIP_MONITOR_BOUNDS.height / 10;

        g.setColor(Color.red);
        g.fillRect(x + width, y, width, height);
        g.setColor(Color.green);
        g.fillRect(x + width, y, width * hp / 100, height);
        g.setColor(Color.black);
        g.drawRect(x + width, y, width, height);
        g.setColor((Color) Settings.get("fontcolor"));
        g.drawString(IO.translate("HP"), x, y + height);
        y += height * 2;

        g.setColor(Color.lightGray);
        g.fillRect(x + width, y, width, height);
        g.setColor(Color.cyan);
        g.fillRect(x + width, y, width * shield / 100, height);
        g.setColor(Color.black);
        g.drawRect(x + width, y, width, height);
        g.setColor((Color) Settings.get("fontcolor"));
        g.drawString(IO.translate("SHIELD"), x, y + height);
        y += height * 2;

        g.setColor(Color.red);
        g.fillRect(x + width, y, width, height);
        g.setColor(Color.green);
        g.fillRect(x + width, y, width * fuel / 100, height);
        g.setColor(Color.black);
        g.drawRect(x + width, y, width, height);
        g.setColor((Color) Settings.get("fontcolor"));
        g.drawString(IO.translate("FUEL"), x, y + height);
        y += height * 2;

        g.setColor(Color.gray);
        g.fillRect(x + width, y, width, height);
        g.setColor(Color.green);
        g.fillRect(x + width, y, width * speed / 100, height);
        g.setColor(Color.black);
        g.drawRect(x + width, y, width, height);
        g.setColor((Color) Settings.get("fontcolor"));
        g.drawString(IO.translate("SPEED"), x, y + height);
        y += height * 2;

        for (int i = 0; i < guns.length; i++)
        {
            g.setColor(Color.gray);
            g.fillRect(x + width, y, width, height);
            g.setColor(Color.red);
            g.fillRect(x + width, y, width * guns[i] / 100, height);
            g.setColor(Color.black);
            g.drawRect(x + width, y, width, height);
            g.setColor((Color) Settings.get("fontcolor"));
            g.drawString(gunnames[i], x, y + height);
            y += height * 2;
        }

        // g.setColor(Color.white);
        // g.drawRect(SpaceInvader.SHIP_MONITOR_BOUNDS.x, SpaceInvader.SHIP_MONITOR_BOUNDS.y,
        // SpaceInvader.SHIP_MONITOR_BOUNDS.width, SpaceInvader.SHIP_MONITOR_BOUNDS.height);
    }

    private void drawMinimap(Graphics2D g)
    {
        Ship mShip = SpaceInvader.getInstance().getMShip();
        g.drawImage(miniMapBG, SpaceInvader.MINIMAP_BOUNDS.x, SpaceInvader.MINIMAP_BOUNDS.y, null);
        int size = SpaceInvader.MINIMAP_POINT_SIZE;
        g.setColor(Color.red);
        for (Ship enemy : SpaceInvader.getInstance().getEnemyships(mShip.getTeam()))
        {
            g.fillRect((int) (enemy.getX() * miniMapScaleFactorX + SpaceInvader.MINIMAP_BOUNDS.x),
                    (int) (enemy.getY() * miniMapScaleFactorY + SpaceInvader.MINIMAP_BOUNDS.y), size, size);
        }
        g.setColor(Color.cyan);
        for (Ship ally : SpaceInvader.getInstance().getAllyships(mShip.getTeam()))
        {
            g.fillRect((int) (ally.getX() * miniMapScaleFactorX + SpaceInvader.MINIMAP_BOUNDS.x),
                    (int) (ally.getY() * miniMapScaleFactorY + SpaceInvader.MINIMAP_BOUNDS.y), size, size);
        }
        g.setColor(Color.green);
        g.fillRect((int) (mShip.getX() * miniMapScaleFactorX + SpaceInvader.MINIMAP_BOUNDS.x),
                (int) (mShip.getY() * miniMapScaleFactorY + SpaceInvader.MINIMAP_BOUNDS.y), size + 2,
                size + 2);
        g.setColor(Color.white);
        g.drawRect(SpaceInvader.MINIMAP_BOUNDS.x, SpaceInvader.MINIMAP_BOUNDS.y,
                SpaceInvader.MINIMAP_BOUNDS.width, SpaceInvader.MINIMAP_BOUNDS.height);
    }

    private void drawCredits(Graphics g)
    {
        int x = WIDTH / 5;
        int y = HEIGHT - creditsProgress;
        g.setFont((Font) Settings.get("font"));
        g.setColor((Color) Settings.get("fontcolor"));
        g.drawString("CREDITS", x, y);
        y += g.getFont().getSize() * 3;
        g.drawString("Author", x, y);
        y += g.getFont().getSize() * 3;
        g.drawString(SpaceInvader.AUTHOR, x, y);
        y += g.getFont().getSize() * 3;
        g.drawString("Co producer", x, y);
        y += g.getFont().getSize() * 3;
        for (int i = 0; i < SpaceInvader.COAUTHORS.length; i++)
        {
            g.drawString(SpaceInvader.COAUTHORS[i], x, y);
            y += g.getFont().getSize() * 3;
        }
        creditsProgress++;
    }

    @Override
    protected void preAct()
    {
        Ship mShip = SpaceInvader.getInstance().getMShip();
        if (mShip != null)
        {
            setViewX(mShip.getXOnScreen() + mouseX - WIDTH);
            if (getViewX() < 0)
            {
                setViewX(0);
            }
            else if (SpaceInvader.getInstance().getMap() != null
                    && getViewX() + WIDTH > SpaceInvader.getInstance().getWidth())
            {
                setViewX(SpaceInvader.getInstance().getWidth() - WIDTH);
            }
            setViewY(mShip.getYOnScreen() + mouseY - HEIGHT);
            if (getViewY() < 0)
            {
                setViewY(0);
            }
            else if (SpaceInvader.getInstance().getMap() != null
                    && getViewY() + HEIGHT > SpaceInvader.getInstance().getHeight())
            {
                setViewY(SpaceInvader.getInstance().getHeight() - HEIGHT);
            }
        }
    }

    @Override
    public void keyPressed(Key key)
    {
        if (!SpaceInvader.getInstance().isGameOver())
        {
            switch (key)
            {
                case KEY_UP:
                    if (isRunning())
                    {
                        if ((Boolean) Settings.get("toggleaccel"))
                        {
                            SpaceInvader.getInstance().getMShip().setAccelerating(!SpaceInvader.getInstance().getMShip().isAccelerating());
                            SpaceInvader.getInstance().getMShip().setBraking(false);
                        }
                        else
                        {
                            SpaceInvader.getInstance().getMShip().setAccelerating(true);
                        }
                    }
                    break;
                case KEY_DOWN:
                    if (isRunning())
                    {
                        if ((Boolean) Settings.get("toggleaccel"))
                        {
                            SpaceInvader.getInstance().getMShip().setBraking(!SpaceInvader.getInstance().getMShip().isBraking());
                            SpaceInvader.getInstance().getMShip().setAccelerating(false);
                        }
                        else
                        {
                            SpaceInvader.getInstance().getMShip().setBraking(true);
                        }
                    }
                    break;
                case KEY_LEFT:
                    if (isRunning())
                    {
                        SpaceInvader.getInstance().getMShip().rotateLeft();
                    }
                    break;
                case KEY_RIGHT:
                    if (isRunning())
                    {
                        SpaceInvader.getInstance().getMShip().rotateRight();
                    }
                    break;
                case KEY_ENTER:
                    setRunning(!isRunning());
                    break;
                case KEY_A:
                    if (isRunning())
                    {
                        SpaceInvader.getInstance().getMShip().setShooting(true);
                    }
                    break;
                case KEY_SELECT:
                    if (isRunning())
                    {
                        if (SpaceInvader.getInstance().isInLandingZone(
                                SpaceInvader.getInstance().getMShip()))
                        {
                            land();
                        }
                        else
                        {
                            IO.println(IO.translate("NOT_IN_LZONE"), IO.MessageType.IMPORTANT);
                        }
                    }
                    break;
                case KEY_B:
                    if (isRunning())
                    {
                        SpaceInvader.getInstance().toggleFleetCommand();
                        SpaceInvader.getInstance().getMShip().setSuperShieldOn(!SpaceInvader.getInstance().getMShip().getSuperShieldOn());
                    }
                    break;
                default:
                    break;
            }
        }
        else
        {
            IO.println("unsupported key", IO.MessageType.DEBUG);
        }
    }

    @Override
    public void keyReleased(Key key)
    {
        switch (key)
        {
            case KEY_UP:
                if (!(Boolean) Settings.get("toggleaccel"))
                {
                    SpaceInvader.getInstance().getMShip().setAccelerating(false);
                }
                break;
            case KEY_DOWN:
                if (!(Boolean) Settings.get("toggleaccel"))
                {
                    SpaceInvader.getInstance().getMShip().setBraking(false);
                }
                break;
            case KEY_LEFT:
            case KEY_RIGHT:
                SpaceInvader.getInstance().getMShip().stopRotate();
                break;
            case KEY_A:
                SpaceInvader.getInstance().getMShip().setShooting(false);
                break;
            default:
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
        requestFocus();
    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        SpaceInvader.getInstance().getMShip().setShooting(true);
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        SpaceInvader.getInstance().getMShip().setShooting(false);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        setMousePosition(e.getX(), e.getY());
        System.out.println(mouseX + " " + mouseY);
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        setMousePosition(e.getX(), e.getY());
    }

    private void setMousePosition(int x, int y)
    {
        mouseX = x;
        mouseY = y;
        if (Cruiser.class.isInstance(SpaceInvader.getInstance().getMShip()))
        {
            Cruiser c = (Carrier) SpaceInvader.getInstance().getMShip();
            c.setTarget(new Vector2D(mouseX + getViewX(), mouseY + getViewY()));
        }
    }
}
