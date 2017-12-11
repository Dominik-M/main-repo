/*
 * Copyright (C) 2016 Dundun
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
package platform.utils;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 14.06.2016
 */
public class Interpreter {

	private static final Dictionary<String, Command> commands = new Dictionary<>();

	public static void initDefaultCommands() {
		addAll(new Command("HELP", "Shows Help text") {

			@Override
			public boolean execute(String... params) {
				for (Command c : commands.getElements()) {
					IO.println(c.toString(), IO.MessageType.NORMAL);
					IO.println(c.getDescription(), IO.MessageType.NORMAL);
				}
				return true;
			}
		}, new Command("FONTSIZE", "Sets the Fontsize", "size") {

			@Override
			public boolean execute(String... params) {
				try {
					int fontsize = Integer.parseInt(params[0]);
					java.awt.Font font = (java.awt.Font) Settings.get("font");
					Settings.set("font", new java.awt.Font(font.getFontName(), font.getStyle(),
					fontsize));
					IO.println("Fontsize set to " + fontsize, IO.MessageType.NORMAL);
					return true;
				} catch (Exception ex) {
					IO.printException(ex);
					IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
					return false;
				}
			}
		}, new Command("SETFONT", "Sets the Font", "Name", "style", "size") {

			@Override
			public boolean execute(String... params) {
				try {
					Settings.set("font", new java.awt.Font(params[0], Integer.parseInt(params[1]),
					Integer.parseInt(params[2])));
					IO.println("Font set to " + Settings.get("font"), IO.MessageType.NORMAL);
					return true;
				} catch (Exception ex) {
					IO.printException(ex);
					IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
					return false;
				}
			}
		}, new Command("BGCOLOR", "Sets the background color", "Red", "Green", "Blue") {

			@Override
			public boolean execute(String... params) {
				try {
					Settings.set("backgroundColor", new java.awt.Color(Integer.parseInt(params[0]),
					Integer.parseInt(params[1]), Integer.parseInt(params[2])));
					IO.println("Background color set to " + Settings.get("backgroundColor"),
					IO.MessageType.NORMAL);
					return true;
				} catch (Exception ex) {
					IO.printException(ex);
					IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
					return false;
				}
			}
		}, new Command("FONTCOLOR", "Sets the font color", "Red", "Green", "Blue") {

			@Override
			public boolean execute(String... params) {
				try {
					Settings.set("fontColor", new java.awt.Color(Integer.parseInt(params[0]),
					Integer.parseInt(params[1]), Integer.parseInt(params[2])));
					IO.println("Font color set to " + Settings.get("fontColor"),
					IO.MessageType.NORMAL);
					return true;
				} catch (Exception ex) {
					IO.printException(ex);
					IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
					return false;
				}
			}
		});
	}

	public static boolean add(Command cmd) {
		return commands.add(cmd.name(), cmd);
	}

	public static Command get(String key) {
		return commands.get(key.toUpperCase());
	}

	public static int addAll(Command... cmds) {
		int n = 0;
		for (Command cmd : cmds)
			if (add(cmd))
				n++;
		return n;
	}

	public static boolean execute(Command cmd, String... params) {
		return cmd.execute(params);
	}

	public static boolean execute(String in) {
		try {
			String[] args = in.split(" ");
			if (args.length > 0) {
				Command cmd;
				cmd = commands.get(args[0].trim().toUpperCase());
				if (cmd == null) {
					IO.println("Unknown Command. Enter HELP to show commands",
					IO.MessageType.NORMAL);
					return false;
				}
				String[] params = new String[args.length - 1];
				for (int i = 0; i < params.length; i++) {
					params[i] = args[i + 1].trim();
				}
				return execute(cmd, params);
			} else {
				return false;
			}
		} catch (Exception ex) {
			IO.printException(ex);
			IO.println("Syntax Error", IO.MessageType.NORMAL);
			return false;
		}
	}
}
