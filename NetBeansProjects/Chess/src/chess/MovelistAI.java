package chess;

import java.util.LinkedList;

import chess.Chess.Team;

public class MovelistAI extends BaseAI {
	public final LinkedList<Move> planned;

	public MovelistAI(Team mTeam, LinkedList<Move> moves) {
		super(mTeam);
		if (moves == null) {
			planned = new LinkedList<>();
		} else {
			planned = moves;
		}
	}

	@Override
	public String toString() {
		return "MovelistAI " + team;
	}

	@Override
	public boolean doMove() {
		boolean didmove = false;
		if (planned.size() > 0) {
			Move next = planned.pollFirst();
			didmove = Chess.getInstance().moveFigure(next);
		}
		return didmove;
	}
}
