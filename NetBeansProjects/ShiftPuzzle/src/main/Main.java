/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.PuzzleFrame;
import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Main
{

    public static final int PUZZLE_SIZE = 3;

    private static PuzzleFrame frame;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(PuzzleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(PuzzleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(PuzzleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(PuzzleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                frame = new PuzzleFrame(PUZZLE_SIZE);
                frame.setVisible(true);
                System.out.println("MainFrame initialized");
            }
        });

    }

    public static LinkedList<IntTuple> solvePuzzle(ShiftPuzzle puzzle)
    {
        for (int maxDepth = 16; maxDepth < 128; maxDepth += 4)
        {
            long startTime = System.nanoTime();
            LinkedList<IntTuple> solution = new LinkedList<>();
            System.out.println("Main.solvePuzzle(): Trying to solve puzzle with max Stackdepth of " + maxDepth);
            solution = solvePuzzle(puzzle.clone(), solution, null, 0, maxDepth);
            long timeSpent = System.nanoTime() - startTime;
            System.out.println("Main.solvePuzzle(): Took " + timeSpent / 1000000 + "ms with max Stackdepth of " + maxDepth);
            if (solution != null)
            {
                return solution;
            }
            else
            {
                System.err.println("We failed!");
            }
        }
        return null;
    }

    private static LinkedList<IntTuple> solvePuzzle(ShiftPuzzle puzzle, LinkedList<IntTuple> path, IntTuple beforeMove, int depth, int maxDepth)
    {
        if (puzzle.isComplete())
        {
            return (LinkedList<IntTuple>) path.clone();
        }
        LinkedList<IntTuple> bestPath = null;
        if (depth < maxDepth)
        {
            LinkedList<IntTuple> possibleMoves = puzzle.getMoveableTiles();
            for (IntTuple nextMove : possibleMoves)
            {
                if (beforeMove != null && beforeMove.equals(nextMove))
                {
                    continue;
                }
                IntTuple freeTile = puzzle.getFreeTile();
                if (puzzle.shiftTile(nextMove.X, nextMove.Y))
                {
                    path.add(nextMove);
                    LinkedList<IntTuple> result = solvePuzzle(puzzle, path, freeTile, depth + 1, maxDepth);
                    if (result != null && (bestPath == null || result.size() < bestPath.size()))
                    {
                        bestPath = result;
                    }
                    // Rollback and find new path
                    if (!puzzle.shiftTile(freeTile.X, freeTile.Y))
                    {
                        printErr(puzzle, nextMove);
                        break;
                    }
                    path.removeLast();
                }
                else
                {
                    printErr(puzzle, nextMove);
                }
            }
        }
        return bestPath;
    }

    public static void printErr(ShiftPuzzle puzzle, IntTuple nextMove)
    {
        System.err.println("Something went terribly wrong. Tile: (" + nextMove.X + "," + nextMove.Y + "), " + puzzle);
    }
}
