package hexagol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Dominik Messerschmidt
 */
public class HexaGOL
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        HexaGOL game = frame.getGame();
        game.getAlive = 2;
        game.minStayAlive = 2;
        game.maxStayAlive = 2;
        int x = 1, y = 2;
        //game.setCellState(x, y, CellState.INVALID);
        game.setCellState(1, 0, CellState.ALIVE);
        game.setCellState(0, 1, CellState.ALIVE);
        game.setCellState(1, 1, CellState.ALIVE);
        game.setCellState(0, 3, CellState.ALIVE);
        game.setCellState(1, 3, CellState.ALIVE);
        game.setCellState(1, 4, CellState.ALIVE);

        game.setCellState(4, 0, CellState.ALIVE);
        game.setCellState(3, 1, CellState.ALIVE);
        game.setCellState(4, 1, CellState.ALIVE);
        game.setCellState(3, 3, CellState.ALIVE);
        game.setCellState(4, 3, CellState.ALIVE);
        game.setCellState(4, 4, CellState.ALIVE);

        game.setCellState(3, 6, CellState.ALIVE);
        game.setCellState(2, 7, CellState.ALIVE);
        game.setCellState(3, 7, CellState.ALIVE);
        game.setCellState(2, 9, CellState.ALIVE);
        game.setCellState(3, 10, CellState.ALIVE);
        game.setCellState(3, 11, CellState.ALIVE);
        for (CellState state : game.getAdjacentStates(x, y))
        {
            System.out.println(state);
        }
        game.setRunning(true);
    }

    private final CellState[][] CELLS;
    public final int WIDTH, HEIGHT;
    private int getAlive, minStayAlive, maxStayAlive;
    private int interval = 200;

    private final Timer TIMER = new Timer(interval, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            doStep();
        }
    });

    public HexaGOL(int width, int height)
    {
        CELLS = new CellState[width][height];
        WIDTH = width;
        HEIGHT = height;
        reset();
    }

    public final void reset()
    {
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                CELLS[x][y] = CellState.DEAD;
            }
        }
    }

    public void setRunning(boolean run)
    {
        if (run)
        {
            TIMER.start();
        }
        else
        {
            TIMER.stop();
        }
    }

    public void doStep()
    {
        System.out.println("doStep()");
        CellState[][] tempCells = new CellState[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                tempCells[x][y] = calculateNextState(x, y);
            }
        }
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                setCellState(x, y, tempCells[x][y]);
            }
        }
    }

    public CellState calculateNextState(int x, int y)
    {
        int numAdjacentLiving = 0;
        CellState current = getCellState(x, y);
        for (CellState state : getAdjacentStates(x, y))
        {
            if (state == CellState.ALIVE)
            {
                numAdjacentLiving++;
            }
        }
        if ((current == CellState.ALIVE) && ((numAdjacentLiving < minStayAlive) || (numAdjacentLiving > maxStayAlive)))
        {
            System.out.println("Cell[" + x + "," + y + "] died: " + numAdjacentLiving);
            return CellState.DEAD;
        }
        else if ((current == CellState.DEAD) && (numAdjacentLiving >= getAlive))
        {
            System.out.println("Cell[" + x + "," + y + "] got alive: " + numAdjacentLiving);
            return CellState.ALIVE;
        }
        return current;
    }

    public CellState[] getAdjacentStates(int x, int y)
    {
        CellState[] states = new CellState[6];
        states[0] = getCellState(x, y - 2);
        states[1] = getCellState(x - 1, y - 1);
        states[3] = getCellState(x - 1, y + 1);
        states[2] = getCellState(x, y - 1);
        states[4] = getCellState(x, y + 1);
        states[5] = getCellState(x, y + 2);
        return states;
    }

    public CellState getCellState(int x, int y)
    {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT)
        {
            return CELLS[x][y];
        }
        return CellState.INVALID;
    }

    public boolean setCellState(int x, int y, CellState cellState)
    {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT)
        {
            CELLS[x][y] = cellState;
            return true;
        }
        return false;
    }
}
