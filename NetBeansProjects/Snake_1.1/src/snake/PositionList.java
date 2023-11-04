package snake;

/**
 *
 * @author Dominik
 */
@SuppressWarnings("serial")
public class PositionList extends java.util.LinkedList<Position> {

    @Override
    public boolean contains(Object o) {
        if (o.getClass() == Position.class) {
            Position p = (Position) o;
            for (Position p2 : this) {
                if (p.equals(p2)) {
                    return true;
                }
            }
            return false;
        } else {
            return super.contains(o);
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o.getClass() == Position.class) {
            Position p = (Position) o;
            for (Position p2 : this) {
                if (p.equals(p2)) {
                    return super.remove(p2);
                }
            }
        }
        return super.remove(o);
    }
}
