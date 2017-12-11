/*
 * Copyright (C) 2017 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;

import platform.utils.IO;
import chess.Chess.Figure;
import chess.Chess.Team;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 10.03.2017
 */
public class BaseAI {

	public final Team team;
	private Timer mTimer;

	public BaseAI(Team mTeam) {
		team = mTeam;
	}

	private void init() {
		mTimer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Chess.getInstance().getOnTurn() == team) {
					doBasicMove();
				}
			}
		});
	}

	public void start() {
		IO.println("BaseAI " + team + " joined.", IO.MessageType.DEBUG);
		if (mTimer == null || !mTimer.isRunning()) {
			init();
			mTimer.start();
		}
	}

	public void stop() {
		if (mTimer != null) {
			mTimer.stop();
		}
		IO.println("BaseAI " + team + " left the game.", IO.MessageType.DEBUG);
	}

	public final LinkedList<Position> getKillMoves(Figure f) {
		LinkedList<Position> killpos = new LinkedList<>();
		for (Figure other : Chess.getInstance().getFiguresInTeam(team.getOpposite())) {
			for (Position pos : f.getPossibleLocations()) {
				if (pos.x == other.getX() && pos.y == other.getY()) {
					killpos.add(pos);
				}
			}
		}
		return killpos;
	}

	public final LinkedList<Move> getPossibleMoves() {
		LinkedList<Move> moves = new LinkedList<>();
		for (Figure f : Chess.getInstance().getFiguresInTeam(team)) {
			for (Position pos : f.getPossibleLocations()) {
				moves.add(new Move(f.getX(), f.getY(), pos.x, pos.y));
			}
		}
		return moves;
	}

	public boolean doMove() {
		boolean didmove;
		didmove = tryKillMove();
		return didmove;
	}

	public boolean tryKillMove() {
		boolean didmove = false;
		for (Figure f : Chess.getInstance().getFiguresInTeam(team)) {
			LinkedList<Position> movepos = getKillMoves(f);
			if (movepos.size() > 0) {
				didmove = Chess.getInstance().moveFigure(f.getX(), f.getY(), movepos.getFirst().x,
				movepos.getFirst().y);
			}
			if (didmove) {
				IO.println("BaseAI " + team + " performed a kill move.", IO.MessageType.DEBUG);
				break;
			}
		}
		return didmove;
	}

	public final Move getRandomMove() {
		LinkedList<Move> moves = getPossibleMoves();

		if (moves.size() > 1) {
			// mix
			for (int i = 0; i < 100; i++) {
				int index1 = (int) (Math.random() * moves.size());
				int index2 = (int) (Math.random() * moves.size());
				Move mem = moves.get(index1);
				moves.set(index1, moves.get(index2));
				moves.set(index2, mem);
			}
		}
		for (Move move : moves) {
			if (Chess.getInstance().canMove(move)) {
				return move;
			}
		}

		return null;
	}

	private void doBasicMove() {
		boolean didmove = false;
		IO.println("BaseAI " + team + " does move..", IO.MessageType.DEBUG);
		didmove = doMove();
		if (!didmove) {
			Move move = getRandomMove();
			didmove = Chess.getInstance().moveFigure(move);
			IO.println("BaseAI " + team + " performed a random move.", IO.MessageType.DEBUG);
		}
		if (!didmove) {
			IO.makeToast(team + " kapituliert.", IO.MessageType.IMPORTANT);
			stop();
		}
	}
}
