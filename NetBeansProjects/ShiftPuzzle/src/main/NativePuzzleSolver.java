/*
 * Copyright (C) 2020 Dominik Messerschmidt
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
 *
 * @author Dominik Messerschmidt
 */
public class NativePuzzleSolver
{

    static
    {
        System.loadLibrary("lib/libShiftPuzzleJNI"); // Load native library
    }

    private native int[] solvePuzzle(int[] puzzletiles, int size, int maxDepth);

    public static int[] solvePuzzle(int[][] puzzletiles)
    {
        int[] puzzletiles_flat = new int[puzzletiles.length * puzzletiles.length];
        for (int x = 0; x < puzzletiles.length; x++)
        {
            for (int y = 0; y < puzzletiles.length; y++)
            {
                puzzletiles_flat[x + y * puzzletiles.length] = puzzletiles[x][y];
            }
        }
        for (int maxDepth = 16; maxDepth < 128; maxDepth += 4)
        {
            long startTime = System.nanoTime();
            System.out.println("NativePuzzleSolver: Trying to solve puzzle with max Stackdepth of " + maxDepth);
            int[] solution = new NativePuzzleSolver().solvePuzzle(puzzletiles_flat, puzzletiles.length, maxDepth);
            long timeSpent = System.nanoTime() - startTime;
            System.out.println("NativePuzzleSolver: Took " + timeSpent / 1000000 + "ms with max Stackdepth of " + maxDepth);
            if (solution != null)
            {
                return solution;
            }
            else
            {
                System.err.println("We failed!");
            }
        }
        System.err.println("Exceeded max stack depth");
        return null;
    }

}
