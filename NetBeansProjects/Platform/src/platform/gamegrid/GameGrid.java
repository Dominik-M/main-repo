package platform.gamegrid;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import platform.graphic.MainPanel;
import platform.utils.Settings;
import platform.utils.SettingsListener;

public abstract class GameGrid extends MainPanel implements SettingsListener {

    private static final long serialVersionUID = 5765898768018725406L;
    private final GameData gameData;
    private int viewX, viewY;
    private Timer repaintClock, actClock; // separate HMI and Application for more performance with multicore processors

    public GameGrid(GameData gameData) {
        this.gameData = gameData;
        init();
    }

    // private methods
    private void init() {
        int fps = 50; // frames per second
        if (Settings.isDefined("fps", Integer.class)) {
            fps = (Integer) Settings.get("fps");
        } else {
            Settings.set("fps", fps);
        }

        int sps = fps; // steps per second
        if (Settings.isDefined("sps", Integer.class)) {
            sps = (Integer) Settings.get("sps");
        } else {
            Settings.set("sps", sps);
        }

        repaintClock = new Timer(1000 / fps, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                repaint();
            }
        });

        actClock = new Timer(1000 / sps, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                act();
            }
        });
    }

    // public methods
    public boolean isInGrid(int x, int y) {
        return getGameData().isInGrid(x, y);
    }

    public boolean isInGrid(Rectangle rect) {
        return getGameData().isInGrid(rect);
    }

    public boolean isRunning() {
        return repaintClock.isRunning() || actClock.isRunning();
    }

    public void setRunning(boolean run) {
        if (isRunning() != run) {
            if (run) {
                repaintClock.start();
                actClock.start();
            } else {
                repaintClock.stop();
                actClock.stop();
            }
        }
    }

    public int getViewX() {
        return viewX;
    }

    public void setViewX(int viewX) {
        this.viewX = viewX;
    }

    public int getViewY() {
        return viewY;
    }

    public void setViewY(int viewY) {
        this.viewY = viewY;
    }

    public final void act() {
        preAct();
        getGameData().clock();
    }

    public GameData getGameData() {
        return gameData;
    }

    // abstract methods
    protected abstract void preAct();

    protected abstract void drawBackground(Graphics2D g);

    protected abstract void drawInterface(Graphics2D g);

    // superclass methods
    @Override
    public void onSelect() {
        requestFocus();
    }

    @Override
    public void onDisselect() {

    }

    @Override
    public void drawGUI(Graphics2D g) {
        g.translate(-viewX, -viewY);
        drawBackground(g);
        for (Actor a : getGameData().getActors()) {
            a.paint(g);
            if ((Boolean) Settings.get("drawbounds")) {
                g.drawRect(a.getBounds().x, a.getBounds().y, a.getBounds().width, a.getBounds().height);
            }
        }
        g.translate(viewX, viewY);

        drawInterface(g);
    }

    @Override
    public void preferenceChanged(String key, Object value) {
        switch (key) {
            case "fps":
                int fps = (int) value;
                repaintClock.setDelay(1000 / fps);
                actClock.setDelay(1000 / fps);
                break;
            default:
                break;
        }
    }
}
