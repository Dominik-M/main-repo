package chess;

import java.util.LinkedList;

import chess.Chess.Figure;
import chess.Chess.FigureType;
import chess.Chess.Team;

public class ChessBoard implements Cloneable {
	public Figure[][] spots;

	@Override
	public ChessBoard clone() {
		ChessBoard clone = new ChessBoard();
		clone.spots = new Figure[spots.length][spots[0].length];
		for (int x = 0; x < clone.spots.length; x++) {
			for (int y = 0; y < clone.spots[x].length; y++) {
				if (spots[x][y] != null)
					clone.spots[x][y] = spots[x][y].clone();
				else
					clone.spots[x][y] = null;
			}
		}
		return clone;
	}

	public synchronized Figure[] getFiguresInTeam(Team team) {
		LinkedList<Figure> figList = new LinkedList<>();
		for (int x = 0; x < spots.length; x++) {
			for (int y = 0; y < spots[0].length; y++) {
				if (spots[x][y] != null && spots[x][y].getTeam() == team) {
					figList.add(spots[x][y]);
				}
			}
		}
		Figure[] figs = new Figure[figList.size()];
		for (int i = 0; i < figs.length; i++) {
			figs[i] = figList.get(i);
		}
		return figs;
	}

	public Figure getFigureAt(int x, int y) {
		if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
			return spots[x][y];
		else
			return null;
	}

	public Figure getKing(Team team) {
		for (Figure f : getFiguresInTeam(team)) {
			if (f.getType() == FigureType.KOENIG) {
				return f;
			}
		}
		return null;
	}

	public int getWidth() {
		return spots.length;
	}

	public int getHeight() {
		return spots[0].length;
	}

	public synchronized void moveFigure(Move move) {
		Figure mFigure = spots[move.startPos.x][move.startPos.y];
		spots[move.destPos.x][move.destPos.y] = mFigure;
		mFigure.getPos().x = move.destPos.x;
		mFigure.getPos().y = move.destPos.y;
		spots[move.startPos.x][move.startPos.y] = null;
	}

	public synchronized boolean canMove(Team mTeam, Move move) {
		if (move == null)
			return false;
		boolean retVal = false;
		Figure mFigure = null;

		if (move.startPos.x >= 0 && move.startPos.x < getWidth() && move.startPos.y >= 0
		&& move.startPos.y < getHeight() && move.destPos.x >= 0 && move.destPos.x < getWidth()
		&& move.destPos.y >= 0 && move.destPos.y < getHeight()) {
			mFigure = spots[move.startPos.x][move.startPos.y];
			retVal = mFigure != null;
		}

		if (retVal && spots[move.destPos.x][move.destPos.y] != null) {
			if (spots[move.destPos.x][move.destPos.y].getTeam() == mFigure.getTeam()) {
				retVal = false;
			}
		}

		if (retVal && getKing(mTeam) != null) {
			// check for safe move
			Figure rem = spots[move.destPos.x][move.destPos.y];
			spots[move.destPos.x][move.destPos.y] = mFigure;
			mFigure.getPos().x = move.destPos.x;
			mFigure.getPos().y = move.destPos.y;
			spots[move.startPos.x][move.startPos.y] = null;
			retVal = !getKing(mTeam).isSchach(this);
			// rollback
			spots[move.destPos.x][move.destPos.y] = rem;
			mFigure.getPos().x = move.startPos.x;
			mFigure.getPos().y = move.startPos.y;
			spots[move.startPos.x][move.startPos.y] = mFigure;
		}
		return retVal;
	}
}
