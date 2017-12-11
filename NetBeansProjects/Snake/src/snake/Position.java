package snake;

/**
 *
 * @author Dominik
 */
public class Position {

    public final int X, Y;

    public Position(int x, int y) {
        X = x;
        Y = y;
    }

    public boolean equals(Position otherpos) {
        if (otherpos != null) {
            return otherpos.X == this.X && otherpos.Y == this.Y;
        } else {
            return false;
        }
    }
}
