package main;

import java.awt.Rectangle;
import template.utils.Constants;
import template.utils.IO;
import template.utils.Settings;
import template.utils.Text;

public class Pong {

    public enum Direction {

        UP, DOWN, NONE;
    }

    public enum Mode {

        CPU_ONLY, ONE_PLAYER, TWO_PLAYER;
    }

    public static final int PONG_WIDTH = 10, PONG_HEIGHT = 50, PONG_SPEED = 6,
            DEFAULT_BALL_SPEED = 8;
    public static final int BALL_SIZE = 10;
    public static final double X_INCREMENT = 0.1;

    private Vector2D ballSpeed, expectedBallPos;
    private double ballX, ballY;
    private double maxBallSpeed;
    private int pingX, pingY, pongX, pongY, pingScore, pongScore;
    private Direction pingDir, pongDir;
    private Mode mode;

    public Pong() {
        init();
    }

    public void init() {
        createBall(false);
        pingX = 10;
        pingY = (int) ballY - PONG_HEIGHT / 2;
        pongX = Constants.WIDTH - PONG_WIDTH - 10;
        pongY = (int) ballY - PONG_HEIGHT / 2;
        pingDir = Direction.NONE;
        pongDir = Direction.NONE;
        pingScore = 0;
        pongScore = 0;
        mode = Settings.getSettings().preferredMode;
    }

    public void act() {
        // AI
        if (mode != Mode.TWO_PLAYER) {
            if (ballSpeed.x < 0) {
                if (mode != Mode.ONE_PLAYER) {
                    if (pongY > Constants.HEIGHT / 2 + PONG_HEIGHT) {
                        pongDir = Direction.UP;
                    } else if (pongY < Constants.HEIGHT / 2 - PONG_HEIGHT) {
                        pongDir = Direction.DOWN;
                    } else {
                        pongDir = Direction.NONE;
                    }
                }
                if (expectedBallPos.y - BALL_SIZE <= pingY) {
                    pingDir = Direction.UP;
                } else if (expectedBallPos.y + BALL_SIZE >= pingY + PONG_HEIGHT) {
                    pingDir = Direction.DOWN;
                } else {
                    pingDir = Direction.NONE;
                }

            } else {
                if (pingY > Constants.HEIGHT / 2 + PONG_HEIGHT) {
                    pingDir = Direction.UP;
                } else if (pingY < Constants.HEIGHT / 2 - PONG_HEIGHT) {
                    pingDir = Direction.DOWN;
                } else {
                    pingDir = Direction.NONE;
                }
                if (mode == Mode.CPU_ONLY) {
                    if (expectedBallPos.y - BALL_SIZE <= pongY) {
                        pongDir = Direction.UP;
                    } else if (expectedBallPos.y + BALL_SIZE >= pongY + PONG_HEIGHT) {
                        pongDir = Direction.DOWN;
                    } else {
                        pongDir = Direction.NONE;
                    }
                }
            }
        }

        // move
        ballX += ballSpeed.x;
        ballY += ballSpeed.y;

        if (pingDir == Direction.UP && (pingY - PONG_SPEED >= 0)) {
            pingY = pingY - PONG_SPEED;
        } else if (pingDir == Direction.DOWN
                && (pingY + PONG_SPEED + PONG_HEIGHT <= Constants.HEIGHT)) {
            pingY = pingY + PONG_SPEED;
        }

        if (pongDir == Direction.UP && (pongY - PONG_SPEED >= 0)) {
            pongY = pongY - PONG_SPEED;
        } else if (pongDir == Direction.DOWN
                && (pongY + PONG_SPEED + PONG_HEIGHT <= Constants.HEIGHT)) {
            pongY = pongY + PONG_SPEED;
        }

        // check borders
        if (ballX < 0 || ballX > Constants.WIDTH) {
            if (ballX < 0) {
                IO.println(Text.PONG_SCORED.toString(), IO.MessageType.IMPORTANT);
                pongScore++;
                createBall(false);
            } else if (ballX > Constants.WIDTH) {
                IO.println(Text.PING_SCORED.toString(), IO.MessageType.IMPORTANT);
                pingScore++;
                createBall(true);
            }
        }
        if (ballY < 0 || ballY + BALL_SIZE > Constants.HEIGHT) {
            ballSpeed = new Vector2D(ballSpeed.x, -1 * ballSpeed.y);
        }

        // reflect on ping
        if (getBallBounds().intersects(getPingBounds()) && ballSpeed.x < 0) {
            maxBallSpeed += X_INCREMENT;
            setBallSpeed(new Vector2D(maxBallSpeed / 2, ballSpeed.y
                    - (maxBallSpeed * (pingY + PONG_HEIGHT / 2 - ballY) / PONG_HEIGHT)));
            calcExpectedBallPos(false);
        }

        // reflect on pong
        if (getBallBounds().intersects(getPongBounds()) && ballSpeed.x > 0) {
            maxBallSpeed += X_INCREMENT;
            setBallSpeed(new Vector2D(maxBallSpeed / -2, ballSpeed.y
                    - (maxBallSpeed * (pongY + PONG_HEIGHT / 2 - ballY) / PONG_HEIGHT)));
            calcExpectedBallPos(true);
        }
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        Settings s = Settings.getSettings();
        s.preferredMode = mode;
        Settings.setSettings(s);
    }

    public void createBall(boolean left) {
        maxBallSpeed = DEFAULT_BALL_SPEED;
        int y = (int) (Math.random() * maxBallSpeed - maxBallSpeed / 2);
        if (left) {
            setBallSpeed(new Vector2D(maxBallSpeed / -2, y));
            ballX = 3 * Constants.WIDTH / 4;
            ballY = Constants.HEIGHT / 2;
        } else {
            setBallSpeed(new Vector2D(maxBallSpeed / 2, y));
            ballX = Constants.WIDTH / 4;
            ballY = Constants.HEIGHT / 2;
        }
        calcExpectedBallPos(left);
    }

    public void calcExpectedBallPos(boolean ballHeadsLeft) {
        int x;
        if (ballHeadsLeft) {
            x = pingX + PONG_WIDTH;
        } else {
            x = pongX - BALL_SIZE;
        }
        expectedBallPos = new Vector2D(x, getBallYAtDifX(Math.abs(x - (int) ballX)));
    }

    public Vector2D getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(Vector2D speed) {
        ballSpeed = speed;
        if (ballSpeed.magnitude() > maxBallSpeed) {
            ballSpeed = ballSpeed.getNormalized().mult(maxBallSpeed);
        }
    }

    public int getballX() {
        return (int) ballX;
    }

    public int getBallY() {
        return (int) ballY;
    }

    public int getBallYAtDifX(int dX) {
        double t = Math.abs(dX / ballSpeed.x);
        double y = ballY;
        double dY = ballSpeed.y;
        final int height = Constants.HEIGHT - BALL_SIZE;
        // while reflecting
        while (t > 0) {
            y = y + t * dY;
            if (y < 0) {
                t = Math.abs(y / dY);
                y = 0;
                dY *= -1;
            } else if (y > height) {
                t = Math.abs((y - height) / dY);
                y = height;
                dY *= -1;
            } else {
                break;
            }
        }
        return (int) y;
    }

    public java.awt.geom.Ellipse2D getBallBounds() {
        return new java.awt.geom.Ellipse2D.Double(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }

    public Rectangle getPingBounds() {
        return new Rectangle(pingX, pingY, PONG_WIDTH, PONG_HEIGHT);
    }

    public Rectangle getPongBounds() {
        return new Rectangle(pongX, pongY, PONG_WIDTH, PONG_HEIGHT);
    }

    public int getPingX() {
        return pingX;
    }

    public void setPingX(int pingX) {
        this.pingX = pingX;
    }

    public int getPingY() {
        return pingY;
    }

    public void setPingY(int pingY) {
        this.pingY = pingY;
    }

    public int getPongX() {
        return pongX;
    }

    public void setPongX(int pongX) {
        this.pongX = pongX;
    }

    public int getPongY() {
        return pongY;
    }

    public void setPongY(int pongY) {
        this.pongY = pongY;
    }

    public Direction getPingDir() {
        return pingDir;
    }

    public void setPingDir(Direction pingDir) {
        this.pingDir = pingDir;
    }

    public Direction getPongDir() {
        return pongDir;
    }

    public void setPongDir(Direction pongDir) {
        this.pongDir = pongDir;
    }

    public int getPongScore() {
        return pongScore;
    }

    public void setPongScore(int score) {
        this.pongScore = score;
    }

    public int getPingScore() {
        return pingScore;
    }

    public void setPingScore(int score) {
        this.pingScore = score;
    }

    public Vector2D getExpectedBallPos() {
        return expectedBallPos;
    }
}
