/**
 * Copyright (C) 2016 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
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
package main;

/**
 * Class representing a two-dimensional vector with double coordinates (x, y). The following situation is assumed:
 * <code><pre>
 * 
 *     (0, 0) o------------------>x-axis
 *            | .  / direction
 *            |  .
 *            |   .vector
 *            |    .
 *            |     .
 *            |      *
 *            v
 *          y-axis
 * 
 * </pre></code> The direction of the vector is defined as the angle (0..2*pi) of the vector direction clockwise with
 * reference to the x-coordinate direction.
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class Vector2D {
	public static final Vector2D NULLVECTOR = new Vector2D(0, 0);
	/**
	 * The public x-coordinate.
	 */
	public double x;
	/**
	 * The public y-coordinate.
	 */
	public double y;

	/**
	 * Constructs a zero vector (with coordinates (0, 0)).
	 */
	public Vector2D() {
		this(0, 0);
	}

	/**
	 * Constructs a vector from given integer x-y-coordinates.
	 * 
	 * @param x
	 *        the x-coordinate of the vector
	 * @param y
	 *        the y-coordinate of the vector
	 */
	public Vector2D(int x, int y) {
		this((double) x, (double) y);
	}

	/**
	 * Constructs a vector from given float x-y-coordinates.
	 * 
	 * @param x
	 *        the x-coordinate of the vector
	 * @param y
	 *        the y-coordinate of the vector
	 */
	public Vector2D(float x, float y) {
		this((double) x, (double) y);
	}

	/**
	 * Constructs a vector from given double x-y-coordinates.
	 * 
	 * @param x
	 *        the x-coordinate of the vector
	 * @param y
	 *        the y-coordinate of the vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the magnitude (length) of the vector.
	 * 
	 * @return the magnitude
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the square of the magnitude, without squareroot calculation. This is useful when performance is an issue.
	 * 
	 * @return the square of the magnitude
	 */
	public double magnitude2() {
		return x * x + y * y;
	}

	/**
	 * Modifies the vector to unit magnitude.
	 */
	public void normalize() {
		double magnitude = magnitude();
		x = x / magnitude;
		y = y / magnitude;
	}

	/**
	 * Returns a new vector with magnitude 1 in the direction of the given vector.
	 * 
	 * @return a unit vector with same direction
	 */
	public Vector2D getNormalized() {
		double magnitude = magnitude();
		return new Vector2D(x / magnitude, y / magnitude);
	}

	/**
	 * Returns the direction of the vector (range 0..2*pi)
	 * 
	 * @return the direction of the vector (in radian, zero to east, clockwise)
	 */
	public double getDirection() {
		double theta = Math.atan2(y, x);
		if (theta >= 0)
			return theta;
		else
			return 2 * Math.PI + theta;
	}

	/**
	 * Rotates the vector by the specified angle.
	 * 
	 * @param angle
	 *        the rotation angle (in radian, clockwise)
	 */
	public void rotate(double angle) {
		double xnew, ynew;
		xnew = Math.cos(angle) * x - Math.sin(angle) * y;
		ynew = Math.sin(angle) * x + Math.cos(angle) * y;
		x = xnew;
		y = ynew;
	}

	/**
	 * Rotates the vector by the specified angle at the position specified by the given Vector2D.
	 * 
	 * @param angle
	 *        the rotation angle (in radian, clockwise)
	 * @param v
	 *        a Vector2D defining the point to rotate around.
	 */
	public void rotateAt(Vector2D v, double angle) {
		double xnew, ynew;
		xnew = v.x + (this.x - v.x) * Math.cos(angle) - (this.y - v.y) * Math.sin(angle);
		ynew = v.y + (this.x - v.x) * Math.sin(angle) + (this.y - v.y) * Math.cos(angle);
		x = xnew;
		y = ynew;
	}

	public Vector2D getOrthogonal() {
		return new Vector2D(y, -x);
	}

	/**
	 * Returns the scalar product (dot product) of the current vector with the given vector.
	 * 
	 * @param v
	 *        the vector to take for the dot product
	 * @return the dot product
	 */
	public double dot(Vector2D v) {
		return x * v.x + y * v.y;
	}

	/**
	 * Returns the distance between the current vector and the given vector.
	 * 
	 * @param v
	 *        the vector to take for the distance measurement
	 * @return the distance (magnitude of the vector difference)
	 */
	public double distanceTo(Vector2D v) {
		return Math.sqrt(Math.pow(v.x - x, 2) + Math.pow(v.y - y, 2));
	}

	/**
	 * Returns a new vector that is the vector sum of the current vector and the given vector. Be aware that the current
	 * vector is not modified.
	 * 
	 * @param v
	 *        the vector to be added to the current vector
	 * @return the sum of the two vectors
	 */
	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}

	/**
	 * Returns a new vector with inverted coordinates. Be aware that the current vector is not modified.
	 * 
	 * @return the inverted vector
	 */
	public Vector2D invert() {
		return new Vector2D(-x, -y);
	}

	/**
	 * Returns a new vector that is the vector difference of the current vector and the given vector. Be aware that the
	 * current vector is not modified.
	 * 
	 * @param v
	 *        the vector to be substracted from the current vector
	 * @return the difference of the two vectors
	 */
	public Vector2D sub(Vector2D v) {
		return new Vector2D(x - v.x, y - v.y);
	}

	/**
	 * Returns a new vector that is the product by a scalar of the current vector and the given integer. Be aware that
	 * the current vector is not modified.
	 * 
	 * @param b
	 *        the integer scale factor
	 * @return the vector that is scaled with the given integer
	 */
	public Vector2D mult(int b) {
		return new Vector2D(x * b, y * b);
	}

	/**
	 * Returns a new vector that is the product by a scalar of the current vector and the given float. Be aware that the
	 * current vector is not modified.
	 * 
	 * @param b
	 *        the float scale factor
	 * @return the vector that is scaled with the given float
	 */
	public Vector2D mult(float b) {
		return new Vector2D(x * b, y * b);
	}

	/**
	 * Returns a new vector that is the product by a scalar of the current vector and the given double. Be aware that
	 * the current vector is not modified.
	 * 
	 * @param b
	 *        the double scale factor
	 * @return the vector that is scaled with the given double
	 */
	public Vector2D mult(double b) {
		return new Vector2D(x * b, y * b);
	}

	/**
	 * Returns a new vector with the same coordinates as the current vector.
	 * 
	 * @return a clone of the current vector
	 */
	@Override
	public Vector2D clone() {
		return new Vector2D(x, y);
	}

	/**
	 * Returns a string with the x-y-coordinates in the format (x, y).
	 * 
	 * @return a string representation of the vector
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
