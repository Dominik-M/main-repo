package dame;

import java.util.LinkedList;

public class DameBoard implements Cloneable
{

    public static final int WIDTH = 8, HEIGHT = 8;
    private static DameBoard instance;
    public Figure[][] spots;

    private DameBoard()
    {
        init();
    }

    public final void init()
    {
        spots = new Figure[WIDTH][HEIGHT];
    }

    public static final DameBoard getInstance()
    {
        if (instance == null)
        {
            synchronized (DameBoard.class)
            {
                if (instance == null)
                {
                    instance = new DameBoard();
                }
            }
        }
        return instance;
    }

    @Override
    public DameBoard clone()
    {
        DameBoard clone = new DameBoard();
        clone.spots = new Figure[spots.length][spots[0].length];
        for (int x = 0; x < clone.spots.length; x++)
        {
            for (int y = 0; y < clone.spots[x].length; y++)
            {
                if (spots[x][y] != null)
                {
                    clone.spots[x][y] = spots[x][y].clone();
                }
                else
                {
                    clone.spots[x][y] = null;
                }
            }
        }
        return clone;
    }

    public synchronized Figure[] getFiguresInTeam(Team team)
    {
        LinkedList<Figure> figList = new LinkedList<>();
        for (int x = 0; x < spots.length; x++)
        {
            for (int y = 0; y < spots[0].length; y++)
            {
                if (spots[x][y] != null && spots[x][y].getTeam() == team)
                {
                    figList.add(spots[x][y]);
                }
            }
        }
        Figure[] figs = new Figure[figList.size()];
        for (int i = 0; i < figs.length; i++)
        {
            figs[i] = figList.get(i);
        }
        return figs;
    }

    public Figure getFigureAt(int x, int y)
    {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
        {
            return spots[x][y];
        }
        else
        {
            return null;
        }
    }

    public Figure getKing(Team team)
    {
        for (Figure f : getFiguresInTeam(team))
        {
            if (f.getType() == FigureType.KOENIG)
            {
                return f;
            }
        }
        return null;
    }

    public int getWidth()
    {
        return spots.length;
    }

    public int getHeight()
    {
        return spots[0].length;
    }

    public synchronized void moveFigure(Move move)
    {
        Figure mFigure = spots[move.startPos.x][move.startPos.y];
        spots[move.destPos.x][move.destPos.y] = mFigure;
        mFigure.getPos().x = move.destPos.x;
        mFigure.getPos().y = move.destPos.y;
        spots[move.startPos.x][move.startPos.y] = null;
    }

    public synchronized boolean canMove(Team mTeam, Move move)
    {
        if (move == null)
        {
            return false;
        }
        boolean retVal = false;
        Figure mFigure = null;

        if (move.startPos.x >= 0 && move.startPos.x < getWidth() && move.startPos.y >= 0
                && move.startPos.y < getHeight() && move.destPos.x >= 0 && move.destPos.x < getWidth()
                && move.destPos.y >= 0 && move.destPos.y < getHeight())
        {
            mFigure = spots[move.startPos.x][move.startPos.y];
            retVal = mFigure != null;
        }

        if (retVal && spots[move.destPos.x][move.destPos.y] != null)
        {
            if (spots[move.destPos.x][move.destPos.y].getTeam() == mFigure.getTeam())
            {
                retVal = false;
            }
        }

        if (retVal && getKing(mTeam) != null)
        {
            // check for safe move
            Figure rem = spots[move.destPos.x][move.destPos.y];
            spots[move.destPos.x][move.destPos.y] = mFigure;
            mFigure.getPos().x = move.destPos.x;
            mFigure.getPos().y = move.destPos.y;
            spots[move.startPos.x][move.startPos.y] = null;
            // retVal = !getKing(mTeam).isSchach(this);
            // rollback
            spots[move.destPos.x][move.destPos.y] = rem;
            mFigure.getPos().x = move.startPos.x;
            mFigure.getPos().y = move.startPos.y;
            spots[move.startPos.x][move.startPos.y] = mFigure;
        }
        return retVal;
    }

    public boolean isInGrid(int x, int y)
    {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public boolean setDameAt(int x, int y)
    {
        boolean retVal = isInGrid(x, y);
        if (retVal)
        {
            retVal = spots[x][y] == null;
        }
        if (retVal)
        {
            for (int xi = 0; xi < getWidth(); xi++)
            {
                for (int yi = 0; yi < getHeight(); yi++)
                {
                    Figure other = spots[xi][yi];
                    if (other != null)
                    {
                        int dX = Math.abs(other.getX() - x);
                        int dY = Math.abs(other.getY() - y);
                        if ((other.getX() == x) || (other.getY() == y) || (dX == dY))
                        {
                            retVal = false;
                            break;
                        }
                    }
                }
            }
        }
        if (retVal)
        {
            spots[x][y] = new Figure(FigureType.DAME, Team.WEIS, x, y);
        }
        return retVal;
    }

    public boolean removeFigureAt(int x, int y)
    {
        if (isInGrid(x, y))
        {
            spots[x][y] = null;
            return true;
        }
        return false;
    }
}
