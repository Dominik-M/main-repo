package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import platform.Interface;
import platform.graphic.MainFrame;
import platform.graphic.MainPanel;
import platform.utils.Command;
import platform.utils.IO;
import platform.utils.Interpreter;
import dame.DameAI;
import dame.DameBoard;

public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final PrintStream stdout = System.out;
        Interface.initAll();
        System.setOut(new PrintStream(new OutputStream()
        {

            @Override
            public void write(int c) throws IOException
            {
                MainFrame.FRAME.writeToConsole("" + (char) c);
                stdout.append((char) c);
            }
        }));

        Interface.onReset = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DameBoard.getInstance().init();
            }
        };

        Interpreter.add(new Command("SOLVE", "Starts solving the Dameproblem with given N", "N")
        {

            @Override
            public boolean execute(String... params)
            {
                int n = Integer.parseInt(params[0]);
                IO.println("Starting DameAI with N = " + n, IO.MessageType.NORMAL);
                new Thread(new DameAI(n, 1)).start();
                return false;
            }
        });

        IO.setTranslation("APP_NAME", "Dame");

        MainPanel mainPanel = new graphic.ChessPanel();
        MainFrame.FRAME.setMainPanel(mainPanel);

        IO.println(IO.translate("HELLO"), IO.MessageType.IMPORTANT);
    }
}
