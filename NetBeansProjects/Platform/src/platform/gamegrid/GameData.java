package platform.gamegrid;

import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;
import platform.utils.SerializableReflectObject;

public abstract class GameData extends SerializableReflectObject implements ClockTimed {

    private static final long serialVersionUID = 2863309594850038780L;

    private final CopyOnWriteArrayList<Actor> actors = new CopyOnWriteArrayList<>();

    public void addActor(Actor a) {
        actors.add(a);
    }

    public boolean removeActor(Actor a) {
        return actors.remove(a);
    }

    public void clear() {
        actors.clear();
    }

    public synchronized Iterable<Actor> getActors() {
        return actors;
    }

    public boolean isInGrid(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public boolean isInGrid(Rectangle rect) {
        return new Rectangle(0, 0, getWidth(), getHeight()).contains(rect);
    }

    @Override
    public final void clock() {
        act();
    }

    protected abstract void act();

    public abstract void reset();

    public abstract int getWidth();

    public abstract int getHeight();
}
