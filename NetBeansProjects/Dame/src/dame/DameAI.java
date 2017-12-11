package dame;

import java.util.LinkedList;

import platform.utils.IO;

public class DameAI implements Runnable {
	private final int N;
	private final int delay;

	public DameAI(int n) {
		this(n, 0);
	}

	public DameAI(int n, int delay) {
		N = n;
		this.delay = delay;
	}

	public boolean solve(int n, LinkedList<Position> placed, DameBoard board) {
		if (n <= 0)
			return true; // solved
		// try all positions
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				if (board.setDameAt(x, y)) {
					if (delay > 0) {
						try {
							Thread.sleep(delay);
						} catch (Exception ex) {

						}
					}
					Position solution = new Position(x, y);
					placed.add(solution);
					if (solve(n - 1, placed, board)) {
						return true;
					} else {
						// rollback
						board.removeFigureAt(x, y);
						placed.remove(solution);
					}
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		if (solve(N, new LinkedList<Position>(), DameBoard.getInstance())) {
			IO.println("Solved.", IO.MessageType.IMPORTANT);
		} else {
			IO.println("No solution found for N = " + N, IO.MessageType.ERROR);
		}
	}
}
