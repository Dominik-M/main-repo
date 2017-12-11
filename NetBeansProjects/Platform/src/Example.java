import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javafx.stage.Stage;
import platform.Interface;
import platform.graphic.MainFrame;
import platform.graphic.TextPanel;
import platform.utils.Command;
import platform.utils.IO;
import platform.utils.Interpreter;

public class Example extends javafx.application.Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Interface.initAll();
		System.setOut(new PrintStream(new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {
				MainFrame.FRAME.writeToConsole("" + (char) arg0);
			}
		}));
		TextPanel mainPanel = new TextPanel();
		MainFrame.FRAME.setMainPanel(mainPanel);
		Interpreter.add(new Command("EXIT", "Exit the application") {

			@Override
			public boolean execute(String... params) {
				Interface.shutdown();
				return false;
			}
		});
		IO.println(IO.translate("HELLO"), IO.MessageType.IMPORTANT);
		// while (mainPanel.isPrinting())
		// ;
		// Interface.shutdown();
	}
}
