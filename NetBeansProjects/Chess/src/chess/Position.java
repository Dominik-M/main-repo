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

import java.io.Serializable;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 13.03.2017
 */
public class Position implements Serializable {

	public int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int distanceTo(Position other) {
		int dX = this.x - other.x;
		int dY = this.y - other.y;
		return (int) (Math.sqrt(dX * dX + dY * dY) + 0.5);
	}

	public boolean equals(Position other) {
		return (other.x == this.x) && (other.y == this.y);
	}

	@Override
	public String toString() {
		return x + "|" + y;
	}
}
