/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import platform.Interface;
import platform.gamegrid.GameData;
import platform.graphic.MainFrame;
import platform.graphic.MainPanel;
import platform.image.ImageIO;
import platform.image.Sprite;
import platform.utils.Command;
import platform.utils.IO;
import platform.utils.Interpreter;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
 */
public class Chess extends GameData {

	public enum Team {

		SCHWARZ,
		WEIS;

		public Team getOpposite() {
			if (this == SCHWARZ) {
				return WEIS;
			} else {
				return SCHWARZ;
			}
		}
	}

	public enum FigureType {

		BAUER,
		LAEUFER,
		TURM,
		SPRINGER,
		DAME,
		KOENIG;
	}

	public class Figure implements Cloneable {

		private Team team;
		private final Position pos;
		private FigureType type;

		public Figure(FigureType type, Team team, int x, int y) {
			this.type = type;
			pos = new Position(x, y);
			this.team = team;
		}

		@Override
		public Figure clone() {
			Figure clone = new Figure(type, getTeam(), pos.x, pos.y);
			return clone;
		}

		public int getX() {
			return getPos().x;
		}

		public int getY() {
			return getPos().y;
		}

		public boolean isUpper() {
			return (blackIsUpper && getTeam() == Team.SCHWARZ)
			|| (!blackIsUpper && getTeam() == Team.WEIS);
		}

		public boolean isOnStartPosition() {
			return (type == FigureType.BAUER)
			&& ((isUpper() && pos.y == 1) || (!isUpper() && pos.y == getHeight() - 2));
		}

		@Override
		public String toString() {
			if (getTeam() == Team.SCHWARZ) {
				return type.name() + " " + getTeam().name() + " at " + getPos().x + "|"
				+ getPos().y;
			} else {
				return type.name() + " " + getTeam().name() + " at " + getPos().x + "|"
				+ getPos().y;
			}
		}

		public Position[] getPossibleLocations(ChessBoard board) {
			Position[] positions = new Position[0];
			switch (this.type) {
				case BAUER: {
					int x, y;
					LinkedList<Position> posList = new LinkedList<>();
					if (isUpper()) {
						x = getPos().x;
						y = getPos().y + 1;
						if (isInGrid(x, y) && board.spots[x][y] == null) {
							posList.add(new Position(x, y));
						}
						if (isOnStartPosition()) {
							x = getPos().x;
							y = getPos().y + 2;
							if (isInGrid(x, y) && board.spots[x][y] == null) {
								posList.add(new Position(x, y));
							}
						}
						// kill diagonal
						x = getPos().x - 1; // left
						y = getPos().y + 1; // down
						if (isInGrid(x, y) && board.spots[x][y] != null
						&& board.spots[x][y].team != getTeam()) {
							posList.add(new Position(x, y));
						}
						x = getPos().x + 1; // right
						y = getPos().y + 1; // down
						if (isInGrid(x, y) && board.spots[x][y] != null
						&& board.spots[x][y].team != getTeam()) {
							posList.add(new Position(x, y));
						}
					} else // lower
					{
						x = getPos().x;
						y = getPos().y - 1;
						if (isInGrid(x, y) && board.spots[x][y] == null) {
							posList.add(new Position(x, y));
						}
						if (isOnStartPosition()) {
							x = getPos().x;
							y = getPos().y - 2;
							if (isInGrid(x, y) && board.spots[x][y] == null) {
								posList.add(new Position(x, y));
							}
						}
						// kill diagonal
						x = getPos().x - 1; // left
						y = getPos().y - 1; // up
						if (isInGrid(x, y) && board.spots[x][y] != null
						&& board.spots[x][y].team != getTeam()) {
							posList.add(new Position(x, y));
						}
						x = getPos().x + 1; // right
						y = getPos().y - 1; // up
						if (isInGrid(x, y) && board.spots[x][y] != null
						&& board.spots[x][y].team != getTeam()) {
							posList.add(new Position(x, y));
						}
					}
					positions = new Position[posList.size()];
					for (int i = 0; i < positions.length; i++) {
						positions[i] = posList.get(i);
					}
					break;
				}
				case LAEUFER: {
					int x, y;
					LinkedList<Position> posList = new LinkedList<>();
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x + i;// right
						y = getPos().y + i;// down
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x + i;// right
						y = getPos().y - i;// up
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x - i;// left
						y = getPos().y + i;// down
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x - i;// left
						y = getPos().y - i;// up
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					positions = new Position[posList.size()];
					for (int i = 0; i < positions.length; i++) {
						positions[i] = posList.get(i);
					}
					break;
				}
				case TURM: {
					int x, y;
					LinkedList<Position> posList = new LinkedList<>();
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x;
						y = getPos().y - i;// up
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x + i;// right
						y = getPos().y;
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x;
						y = getPos().y + i;// down
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x - i;// left
						y = getPos().y;
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					positions = new Position[posList.size()];
					for (int i = 0; i < positions.length; i++) {
						positions[i] = posList.get(i);
					}
					break;
				}
				case SPRINGER: {
					int x, y;
					LinkedList<Position> posList = new LinkedList<>();
					x = getPos().x + 2; // right
					y = getPos().y + 1; // down
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}
					x = getPos().x + 2; // right
					y = getPos().y - 1; // up
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}
					x = getPos().x - 2; // left
					y = getPos().y + 1; // down
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}
					x = getPos().x - 2; // left
					y = getPos().y - 1; // up
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}
					y = getPos().y + 2; // down
					x = getPos().x + 1; // right
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}
					y = getPos().y + 2; // down
					x = getPos().x - 1; // left
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}
					y = getPos().y - 2; // up
					x = getPos().x + 1; // right
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}
					y = getPos().y - 2; // up
					x = getPos().x - 1; // left
					if (isInGrid(x, y)) {
						posList.add(new Position(x, y));
					}

					positions = new Position[posList.size()];
					for (int i = 0; i < positions.length; i++) {
						positions[i] = posList.get(i);
					}
					break;
				}
				case DAME: {
					int x, y;
					LinkedList<Position> posList = new LinkedList<>();
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x;
						y = getPos().y - i;// up
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x + i;// right
						y = getPos().y;
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x;
						y = getPos().y + i;// down
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth() || i < getHeight(); i++) {
						x = getPos().x - i;// left
						y = getPos().y;
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x + i;// right
						y = getPos().y + i;// down
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x + i;// right
						y = getPos().y - i;// up
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x - i;// left
						y = getPos().y + i;// down
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					for (int i = 1; i < getWidth(); i++) {
						x = getPos().x - i;// left
						y = getPos().y - i;// up
						if (isInGrid(x, y)) {
							if (board.spots[x][y] != null) {
								if (board.spots[x][y].team != getTeam()) {
									posList.add(new Position(x, y));
								}
								break;
							}
							posList.add(new Position(x, y));
						}
					}
					positions = new Position[posList.size()];
					for (int i = 0; i < positions.length; i++) {
						positions[i] = posList.get(i);
					}
					break;
				}
				case KOENIG: {
					LinkedList<Position> posList = new LinkedList<>();
					Position next = new Position(getPos().x + 1, getPos().y - 1); // right upper
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}
					next = new Position(getPos().x + 1, getPos().y); // right center
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}
					next = new Position(getPos().x + 1, getPos().y + 1); // right down
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}
					next = new Position(getPos().x, getPos().y + 1); // center down
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}
					next = new Position(getPos().x - 1, getPos().y + 1); // left down
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}
					next = new Position(getPos().x - 1, getPos().y); // left center
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}
					next = new Position(getPos().x - 1, getPos().y - 1); // left upper
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}
					next = new Position(getPos().x, getPos().y - 1); // center upper
					if (isInGrid(next.x, next.y)) {
						if ((board.spots[next.x][next.y] == null)
						|| (board.spots[next.x][next.y].team != this.getTeam())) {
							if (!isSchach(board, next)) {
								posList.add(next);
							}
						}
					}

					positions = new Position[posList.size()];
					for (int i = 0; i < positions.length; i++) {
						positions[i] = posList.get(i);
					}
					break;
				}
				default:
					break;
			}
			return positions;
		}

		public boolean isSchach(ChessBoard board) {
			return isSchach(board, this.getPos());
		}

		public boolean isSchach(ChessBoard board, Position pos) {
			for (Figure other : board.getFiguresInTeam(getTeam().getOpposite())) {
				if (other.type == FigureType.KOENIG) {
					if (other.getPos().distanceTo(pos) < 2) {
						return true;
					}
				} else if (other.type == FigureType.BAUER) {
					if ((Math.abs(other.getX() - pos.x) == 1)
					&& ((other.isUpper() && (pos.y - other.getY() == 1)) || (!other.isUpper() && (pos.y
					- other.getY() == -1)))) {
						return true;
					}
				} else {
					for (Position movepos : other.getPossibleLocations(board)) {
						if (movepos.equals(pos)) {
							return true;
						}
					}
				}
			}
			return false;
		}

		public Sprite getSprite() {
			return ImageIO.getSprite(type.name().toLowerCase() + "_"
			+ getTeam().name().toLowerCase() + ".png");
		}

		public Position getPos() {
			return pos;
		}

		public Team getTeam() {
			return team;
		}

		public FigureType getType() {
			return type;
		}
	}

	private final ChessBoard board = new ChessBoard();
	private static Chess instance;
	private boolean blackIsUpper, gameover;
	private Team onTurn;
	private final LinkedList<BaseAI> ais = new LinkedList<>();

	private Chess() {
		init();
	}

	private void init() {
		IO.println("Chess.init()", IO.MessageType.DEBUG);
		board.spots = new Figure[8][8];
		blackIsUpper = true;
		gameover = false;
		onTurn = Team.WEIS;
		Team team;

		stopAIs();
		ais.clear();

		team = blackIsUpper ? Team.SCHWARZ : Team.WEIS;

		// upper team
		for (int x = 0; x < board.spots.length; x++) {
			setFigure(FigureType.BAUER, team, x, 1);
		}
		setFigure(FigureType.TURM, team, 0, 0);
		setFigure(FigureType.SPRINGER, team, 1, 0);
		setFigure(FigureType.LAEUFER, team, 2, 0);
		setFigure(FigureType.DAME, team, 3, 0);
		setFigure(FigureType.KOENIG, team, 4, 0);
		setFigure(FigureType.LAEUFER, team, 5, 0);
		setFigure(FigureType.SPRINGER, team, 6, 0);
		setFigure(FigureType.TURM, team, 7, 0);

		team = blackIsUpper ? Team.WEIS : Team.SCHWARZ;

		// lower team
		for (int x = 0; x < board.spots.length; x++) {
			setFigure(FigureType.BAUER, team, x, 6);
		}
		setFigure(FigureType.TURM, team, 0, 7);
		setFigure(FigureType.SPRINGER, team, 1, 7);
		setFigure(FigureType.LAEUFER, team, 2, 7);
		setFigure(FigureType.DAME, team, 3, 7);
		setFigure(FigureType.KOENIG, team, 4, 7);
		setFigure(FigureType.LAEUFER, team, 5, 7);
		setFigure(FigureType.SPRINGER, team, 6, 7);
		setFigure(FigureType.TURM, team, 7, 7);

		BaseAI ai = new BacktrackerAI(Team.SCHWARZ);
		ais.add(ai);
		ai.start();
		ai = new BaseAI(Team.WEIS);
		ais.add(ai);
		// ai.start();
	}

	private void stopAIs() {
		for (BaseAI ai : ais) {
			ai.stop();
		}
	}

	private void setFigure(FigureType type, Team team, int x, int y) {
		board.spots[x][y] = new Figure(type, team, x, y);
		IO.println("Chess.setFigure(): " + board.spots[x][y], IO.MessageType.DEBUG);
	}

	private void removeFigure(int x, int y) {
		if (board.spots[x][y] != null) {
			IO.println("Chess.removeFigure(): " + board.spots[x][y], IO.MessageType.DEBUG);
			board.spots[x][y] = null;
		}
	}

	public synchronized boolean moveFigure(Move move) {
		if (move == null || gameover)
			return false;
		return moveFigure(move.startPos.x, move.startPos.y, move.destPos.x, move.destPos.y);
	}

	public synchronized boolean canMove(Move move) {
		if (move == null || gameover)
			return false;
		return canMove(move.startPos.x, move.startPos.y, move.destPos.x, move.destPos.y);
	}

	public synchronized boolean canMove(int startX, int startY, int destX, int destY) {
		if (gameover)
			return false;
		boolean retVal = false;
		Figure mFigure = null;
		// IO.println("Chess.canMove()", IO.MessageType.DEBUG);

		if (startX >= 0 && startX < getWidth() && startY >= 0 && startY < getHeight() && destX >= 0
		&& destX < getWidth() && destY >= 0 && destY < getHeight()) {
			mFigure = board.spots[startX][startY];
			retVal = mFigure != null;
		} else {
			IO.println("Chess.canMove(): No Figure available at " + startX + "|" + startY,
			IO.MessageType.ERROR);
		}

		if (retVal) {
			if (onTurn != mFigure.getTeam()) {
				retVal = false;
				IO.println(mFigure.getTeam() + " is not on turn", IO.MessageType.ERROR);
			}
		}
		if (retVal && board.spots[destX][destY] != null) {
			if (board.spots[destX][destY].team == mFigure.getTeam()) {
				IO.println("Chess.canMove(): Cannot move " + mFigure + " onto "
				+ board.spots[destX][destY], IO.MessageType.ERROR);
				retVal = false;
			}
		}

		if (retVal && board.getKing(onTurn) != null) {
			// check for safe move
			Figure rem = board.spots[destX][destY];
			board.spots[destX][destY] = mFigure;
			mFigure.getPos().x = destX;
			mFigure.getPos().y = destY;
			board.spots[startX][startY] = null;
			retVal = !board.getKing(onTurn).isSchach(board);
			if (!retVal) {
				IO.println(mFigure + " cannot move to " + destX + "|" + destY
				+ " without risk the Kings life.", IO.MessageType.ERROR);
			}
			// rollback
			board.spots[destX][destY] = rem;
			mFigure.getPos().x = startX;
			mFigure.getPos().y = startY;
			board.spots[startX][startY] = mFigure;
		}
		return retVal;
	}

	public synchronized boolean moveFigure(int startX, int startY, int destX, int destY) {
		if (gameover)
			return false;
		boolean retVal = canMove(startX, startY, destX, destY);

		if (retVal) {
			Figure mFigure = board.spots[startX][startY];
			if (board.spots[destX][destY] != null) {
				removeFigure(destX, destY);
			}
			board.spots[destX][destY] = mFigure;
			IO.println(mFigure + " moved to " + destX + "|" + destY, IO.MessageType.DEBUG);
			mFigure.getPos().x = destX;
			mFigure.getPos().y = destY;
			board.spots[startX][startY] = null;
			if (mFigure.type == FigureType.BAUER) {
				if ((mFigure.isUpper() && mFigure.getPos().y == getHeight() - 1)
				|| (!mFigure.isUpper() && mFigure.getPos().y == 0)) {
					mFigure.type = FigureType.DAME;
				}
			}
			if (nextTurn()) {
				gameover = true;
				stopAIs();
			}
		}

		return retVal;
	}

	private boolean nextTurn() {
		onTurn = onTurn.getOpposite();
		IO.println(onTurn + " is now on turn", IO.MessageType.DEBUG);
		if (board.getKing(onTurn) != null && board.getKing(onTurn).isSchach(board)) {
			IO.println(onTurn + " is Schach", IO.MessageType.DEBUG);
			IO.println("Schach", IO.MessageType.IMPORTANT);
		}
		// check if lost
		for (BaseAI ai : ais) {
			if (ai.team == onTurn) {
				if (ai.getRandomMove(board) == null) {
					IO.makeToast("Schach Matt! " + onTurn + " verliert.", IO.MessageType.IMPORTANT);
					return true;
				}
				break;
			}
		}
		// check for stalemate
		if (board.getFiguresInTeam(Team.WEIS).length <= 2
		&& board.getFiguresInTeam(Team.SCHWARZ).length <= 2) {
			IO.makeToast("Unentschieden!", IO.MessageType.IMPORTANT);
			return true;
		}
		return false;
	}

	public ChessBoard getBoard() {
		return board.clone();
	}

	public Team getOnTurn() {
		return onTurn;
	}

	@Override
	protected void act() {
		IO.println("Chess.act() not supported", IO.MessageType.ERROR);
	}

	@Override
	public void reset() {
		init();
	}

	@Override
	public int getWidth() {
		return board.getWidth();
	}

	@Override
	public int getHeight() {
		return board.getHeight();
	}

	public static final Chess getInstance() {
		if (instance == null) {
			synchronized (Chess.class) {
				if (instance == null) {
					instance = new Chess();
				}
			}
		}
		return instance;
	}

	public static void postRun() {
		getInstance().stopAIs();
	}

	/**
	 * @param args
	 *        the command line arguments
	 */
	public static void main(String[] args) {
		final PrintStream stdout = System.out;
		Interface.initAll();
		System.setOut(new PrintStream(new OutputStream() {

			@Override
			public void write(int c) throws IOException {
				MainFrame.FRAME.writeToConsole("" + (char) c);
				stdout.append((char) c);
			}
		}));
		Interface.onClose = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				postRun();
			}
		};
		Interface.onReset = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getInstance().reset();
			}
		};

		Interpreter.add(new Command("SETFIGURE", "Sets a figure at the given position", "Type",
		"Team", "X", "Y") {

			@Override
			public boolean execute(String... params) {
				FigureType type = null;
				try {
					type = FigureType.valueOf(params[0].toUpperCase());
				} catch (IllegalArgumentException ex) {
					IO.println(params[0] + " is not a valid FigureType", IO.MessageType.ERROR);
					IO.println("Valid FigureType values:", IO.MessageType.DEBUG);
					for (FigureType ft : FigureType.values()) {
						IO.println(ft.name(), IO.MessageType.DEBUG);
					}
					return false;
				}

				Team team = null;
				try {
					team = Team.valueOf(params[1].toUpperCase());
				} catch (IllegalArgumentException ex) {
					IO.println(params[1] + " is not a valid Team", IO.MessageType.ERROR);
					IO.println("Valid Team values:", IO.MessageType.DEBUG);
					for (Team t : Team.values()) {
						IO.println(t.name(), IO.MessageType.DEBUG);
					}
					return false;
				}

				int x = Integer.parseInt(params[2]);
				int y = Integer.parseInt(params[3]);
				if (!instance.isInGrid(x, y)) {
					IO.println(x + "|" + y + " is not in the grid", IO.MessageType.ERROR);
					IO.println(
					"Grid dimension: " + instance.getWidth() + "|" + instance.getHeight(),
					IO.MessageType.DEBUG);
					return false;
				}
				instance.setFigure(type, team, x, y);
				IO.println("Set figure " + instance.board.spots[x][y], IO.MessageType.NORMAL);
				return false;
			}
		});

		Interpreter.add(new Command("STARTAIS", "Starts all AIs") {

			@Override
			public boolean execute(String... params) {
				for (BaseAI ai : instance.ais)
					ai.start();
				return false;
			}
		});

		Interpreter.add(new Command("STOPAIS", "Stops all AIs") {

			@Override
			public boolean execute(String... params) {
				instance.stopAIs();
				return false;
			}
		});
		Interpreter.add(new Command("TOGGLETEAMS",
		"Switches the team of all figures to its opposite") {

			@Override
			public boolean execute(String... params) {
				for (int x = 0; x < instance.board.spots.length; x++) {
					for (int y = 0; y < instance.board.spots[x].length; y++) {
						if (instance.board.spots[x][y] != null) {
							instance.board.spots[x][y].team = instance.board.spots[x][y].team
							.getOpposite();
						}
					}
				}
				instance.blackIsUpper = !instance.blackIsUpper;
				return false;
			}
		});
		Interpreter.add(new Command("REPEAT", "Loads a recorded game and repeats the moves",
		"Filename") {

			@Override
			public boolean execute(String... params) {
				try {
					String filename = params[0];
					FileInputStream fis = new FileInputStream(new File(filename));
					ObjectInputStream ois = new ObjectInputStream(fis);
					RecordedGame recorded = (RecordedGame) ois.readObject();
					instance.stopAIs();
					instance.ais.clear();
					BaseAI ai = new MovelistAI(Team.SCHWARZ, recorded.blacksMoves);
					instance.ais.add(ai);
					ai.start();
					ai = new MovelistAI(Team.WEIS, recorded.whitesMoves);
					instance.ais.add(ai);
					ai.start();
				} catch (Exception ex) {
					IO.printException(ex);
				}
				return false;
			}
		});

		IO.setTranslation("APP_NAME", "Schach");

		MainPanel mainPanel = new graphic.ChessPanel();
		MainFrame.FRAME.setMainPanel(mainPanel);

		IO.println(IO.translate("HELLO"), IO.MessageType.IMPORTANT);
	}
}
