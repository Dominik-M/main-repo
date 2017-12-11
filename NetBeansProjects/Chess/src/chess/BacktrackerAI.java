package chess;

import java.io.File;
import java.util.LinkedList;

import platform.utils.IO;
import chess.Chess.Figure;
import chess.Chess.Team;

public class BacktrackerAI extends BaseAI
{

    public static final int MAX_STACK_SIZE = 16;
    private final BaseAI opponent;

    public BacktrackerAI(Team mTeam)
    {
        super(mTeam);
        opponent = new BaseAI(mTeam.getOpposite());
    }

    public final boolean getVictoryMoves(ChessBoard board, LinkedList<Move> moves,
            LinkedList<Move> othersMoves, int depth, int maxDepth)
    {
        // IO.println("BacktrackerAI.getVictoryMoves(): " + moves.size() + " Moves at Depth " + depth,
        // IO.MessageType.DEBUG);
        if (opponent.getRandomMove(board) == null)
        {
            return true; // we already won
        }
        if (depth < maxDepth)
        {
            int maxP = 0;
            LinkedList<Move> possible = getPossibleMoves(board);
            for (int i = 0; i < possible.size(); i++)
            {
                Move move = possible.get(i);
                if (board.canMove(team, move))
                {
                    Figure mFigure = board.spots[move.startPos.x][move.startPos.y];
                    Figure rem = board.spots[move.destPos.x][move.destPos.y];
                    board.spots[move.destPos.x][move.destPos.y] = mFigure;
                    mFigure.getPos().x = move.destPos.x;
                    mFigure.getPos().y = move.destPos.y;
                    board.spots[move.startPos.x][move.startPos.y] = null;

                    moves.add(move);
                    boolean result = false;
                    boolean didMove = false;
                    int p = 0;
                    // let opponent try all possible moves
                    // for (Move otherMove : opponent.getPossibleMoves(board))
                    //{
                    Move otherMove = opponent.getRandomMove(board);
                    if (board.canMove(opponent.team, otherMove))
                    {
                        Figure otherFigure = board.spots[otherMove.startPos.x][otherMove.startPos.y];
                        Figure otherRem = board.spots[otherMove.destPos.x][otherMove.destPos.y];
                        board.spots[otherMove.destPos.x][otherMove.destPos.y] = otherFigure;
                        otherFigure.getPos().x = otherMove.destPos.x;
                        otherFigure.getPos().y = otherMove.destPos.y;
                        board.spots[otherMove.startPos.x][otherMove.startPos.y] = null;
                        othersMoves.add(otherMove);
                        // recursive call
                        result = getVictoryMoves(board, moves, othersMoves, depth + 1, maxDepth);
                        // rollback opponents turn
                        board.spots[otherMove.destPos.x][otherMove.destPos.y] = otherRem;
                        otherFigure.getPos().x = otherMove.startPos.x;
                        otherFigure.getPos().y = otherMove.startPos.y;
                        board.spots[otherMove.startPos.x][otherMove.startPos.y] = otherFigure;
                        didMove = true;
                    }
                    if (result)
                    {
                        p++;
                    }
                    //}
                    // rollback my turn
                    board.spots[move.destPos.x][move.destPos.y] = rem;
                    mFigure.getPos().x = move.startPos.x;
                    mFigure.getPos().y = move.startPos.y;
                    board.spots[move.startPos.x][move.startPos.y] = mFigure;

                    if (!didMove)// || p > maxP)
                    {
                        maxP = p;
                        return true;
                    }
                    else
                    {
                        moves.remove(move);
                        // othersMoves.remove(otherMove);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean doMove()
    {
        boolean didmove = false;
        LinkedList<Move> victoryMoves = new LinkedList<>();
        LinkedList<Move> othersMoves = new LinkedList<>();
        int maxDepth = 1;
        while (!didmove)
        {
            victoryMoves.clear();
            othersMoves.clear();
            IO.println(this + " calculating victory with maxDepth " + maxDepth,
                    IO.MessageType.DEBUG);
            didmove = getVictoryMoves(Chess.getInstance().getBoard(), victoryMoves, othersMoves, 0,
                    maxDepth++);
            didmove = didmove && victoryMoves.size() > 0;
        }
        if (didmove)
        {
            didmove = Chess.getInstance().moveFigure(victoryMoves.getFirst());
            IO.println(this + ": I will win after " + victoryMoves.size() + " moves!",
                    IO.MessageType.DEBUG);
            for (int i = 0; i < victoryMoves.size() || i < othersMoves.size(); i++)
            {
                if (i < victoryMoves.size())
                {
                    IO.println(this + ": My " + i + ". move: " + victoryMoves.get(i),
                            IO.MessageType.DEBUG);
                }
                if (i < othersMoves.size())
                {
                    IO.println(this + ": Your " + i + ". move: " + othersMoves.get(i),
                            IO.MessageType.DEBUG);
                }
            }
            RecordedGame record = new RecordedGame();
            if (team == Team.SCHWARZ)
            {
                record.blacksMoves = victoryMoves;
                record.whitesMoves = othersMoves;
            }
            else
            {
                record.whitesMoves = victoryMoves;
                record.blacksMoves = othersMoves;
            }
            record.save(new File("recordedGame.dat"));
        }
        return didmove;
    }

    @Override
    public String toString()
    {
        return "BacktrackerAI " + team;
    }
}
