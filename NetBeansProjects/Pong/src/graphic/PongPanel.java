package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import main.Pong;
import main.Vector2D;
import template.graphic.InputConfig.Key;
import template.graphic.MainPanel;
import template.utils.Constants;
import template.utils.Settings;
import template.utils.Text;

public class PongPanel extends MainPanel {

    private static final long serialVersionUID = -5187707560650131818L;
    public static final int PRINT_DELAY = 100;
    public final Pong PONG;
    private int printTimer = 0;
    private final Timer clock = new Timer(1000 / Settings.getSettings().fps, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            doStep();
        }
    });

    public PongPanel(Pong pong) {
        PONG = pong;
    }

    public void doStep() {
        PONG.act();
        repaint();
        if (isPrinting()) {
            printTimer++;
            if (printTimer >= PRINT_DELAY) {
                printTimer = 0;
                printNext();
            }
        }
    }

    public int getFps() {
        return 1000 / clock.getDelay();
    }

    public void setFps(int fps) {
        Settings s = Settings.getSettings();
        s.fps = fps;
        clock.setDelay(1000 / fps);
        Settings.setSettings(s);
    }

    @Override
    public void keyPressed(Key key) {
        switch (key) {
            case KEY_ENTER:
                setRunning(!clock.isRunning());
                break;
            case KEY_UP:
                PONG.setPongDir(Pong.Direction.UP);
                break;
            case KEY_DOWN:
                PONG.setPongDir(Pong.Direction.DOWN);
                break;
            case KEY_LEFT:
                PONG.setPingDir(Pong.Direction.UP);
                break;
            case KEY_RIGHT:
                PONG.setPingDir(Pong.Direction.DOWN);
                break;
        }
    }

    @Override
    public void keyReleased(Key key) {
        switch (key) {
            case KEY_UP:
                if (PONG.getPongDir() == Pong.Direction.UP) {
                    PONG.setPongDir(Pong.Direction.NONE);
                }
                break;
            case KEY_DOWN:
                if (PONG.getPongDir() == Pong.Direction.DOWN) {
                    PONG.setPongDir(Pong.Direction.NONE);
                }
                break;
            case KEY_LEFT:
                if (PONG.getPingDir() == Pong.Direction.UP) {
                    PONG.setPingDir(Pong.Direction.NONE);
                }
                break;
            case KEY_RIGHT:
                if (PONG.getPingDir() == Pong.Direction.DOWN) {
                    PONG.setPingDir(Pong.Direction.NONE);
                }
                break;
        }
    }

    @Override
    public void onDisselect() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSelect() {
        requestFocus();
    }

    public void setRunning(boolean running) {
        if (running) {
            clock.start();
        } else {
            clock.stop();
        }
    }

    public boolean isPaused() {
        return !clock.isRunning();
    }

    @Override
    public void drawGUI(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.yellow);
        Vector2D expectedBallPos = PONG.getExpectedBallPos();
        g
                .fillOval((int) expectedBallPos.x, (int) expectedBallPos.y, Pong.BALL_SIZE, Pong.BALL_SIZE);
        g.setColor(Color.DARK_GRAY);
        g.fill(PONG.getBallBounds());
        g.fill(PONG.getPingBounds());
        g.fill(PONG.getPongBounds());
        g.setColor(Settings.getSettings().fontColor);
        g.setFont(Settings.getSettings().font);
        g.drawString(PONG.getPingScore() + ":" + PONG.getPongScore(), Constants.WIDTH / 2 - 40, 20);
        if (!clock.isRunning()) {
            g.drawString(Text.PAUSED.toString(), Constants.WIDTH / 2 - 40,
                    Constants.HEIGHT / 2 - 20);
        }
        if (isPrinting()) {
            for (int i = 0; i < printQueue.size(); i++) {
                String text = printQueue.get(i);
                g.drawString(text, Constants.WIDTH / 2 - text.length() * 7, Constants.HEIGHT - 50
                        * (1 + i));
            }
        }
    }
}
