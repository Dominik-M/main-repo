package dame;

import java.util.LinkedList;

import platform.image.ImageIO;
import platform.image.Sprite;

public class Figure implements Cloneable {

	private final Team team;
	private final Position pos;
	private final FigureType type;

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

	@Override
	public String toString() {
		if (getTeam() == Team.SCHWARZ) {
			return type.name() + " " + getTeam().name() + " at " + getPos().x + "|" + getPos().y;
		} else {
			return type.name() + " " + getTeam().name() + " at " + getPos().x + "|" + getPos().y;
		}
	}

	public Sprite getSprite() {
		return ImageIO.getSprite(type.name().toLowerCase() + "_" + getTeam().name().toLowerCase()
		+ ".png");
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

	public Position[] getPossibleLocations(DameBoard board) {
		Position[] positions = new Position[0];
		switch (this.type) {
			case DAME: {
				int x, y;
				LinkedList<Position> posList = new LinkedList<>();
				for (int i = 1; i < board.getWidth() || i < board.getHeight(); i++) {
					x = getPos().x;
					y = getPos().y - i;// up
					if (board.isInGrid(x, y)) {
						if (board.spots[x][y] != null) {
							if (board.spots[x][y].team != getTeam()) {
								posList.add(new Position(x, y));
							}
							break;
						}
						posList.add(new Position(x, y));
					}
				}
				for (int i = 1; i < board.getWidth() || i < board.getHeight(); i++) {
					x = getPos().x + i;// right
					y = getPos().y;
					if (board.isInGrid(x, y)) {
						if (board.spots[x][y] != null) {
							if (board.spots[x][y].team != getTeam()) {
								posList.add(new Position(x, y));
							}
							break;
						}
						posList.add(new Position(x, y));
					}
				}
				for (int i = 1; i < board.getWidth() || i < board.getHeight(); i++) {
					x = getPos().x;
					y = getPos().y + i;// down
					if (board.isInGrid(x, y)) {
						if (board.spots[x][y] != null) {
							if (board.spots[x][y].team != getTeam()) {
								posList.add(new Position(x, y));
							}
							break;
						}
						posList.add(new Position(x, y));
					}
				}
				for (int i = 1; i < board.getWidth() || i < board.getHeight(); i++) {
					x = getPos().x - i;// left
					y = getPos().y;
					if (board.isInGrid(x, y)) {
						if (board.spots[x][y] != null) {
							if (board.spots[x][y].team != getTeam()) {
								posList.add(new Position(x, y));
							}
							break;
						}
						posList.add(new Position(x, y));
					}
				}
				for (int i = 1; i < board.getWidth(); i++) {
					x = getPos().x + i;// right
					y = getPos().y + i;// down
					if (board.isInGrid(x, y)) {
						if (board.spots[x][y] != null) {
							if (board.spots[x][y].team != getTeam()) {
								posList.add(new Position(x, y));
							}
							break;
						}
						posList.add(new Position(x, y));
					}
				}
				for (int i = 1; i < board.getWidth(); i++) {
					x = getPos().x + i;// right
					y = getPos().y - i;// up
					if (board.isInGrid(x, y)) {
						if (board.spots[x][y] != null) {
							if (board.spots[x][y].team != getTeam()) {
								posList.add(new Position(x, y));
							}
							break;
						}
						posList.add(new Position(x, y));
					}
				}
				for (int i = 1; i < board.getWidth(); i++) {
					x = getPos().x - i;// left
					y = getPos().y + i;// down
					if (board.isInGrid(x, y)) {
						if (board.spots[x][y] != null) {
							if (board.spots[x][y].team != getTeam()) {
								posList.add(new Position(x, y));
							}
							break;
						}
						posList.add(new Position(x, y));
					}
				}
				for (int i = 1; i < board.getWidth(); i++) {
					x = getPos().x - i;// left
					y = getPos().y - i;// up
					if (board.isInGrid(x, y)) {
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
			default:
				break;
		}
		return positions;
	}
}