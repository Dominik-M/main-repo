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
package utils;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 14.06.2016
 */
public class Interpreter {

    public enum Command {
        HELP("Shows Help text"),
        FONTSIZE("Sets the Fontsize", "size"),
        SETFONT("Sets the Font", "Name", "style", "size"),
        BGCOLOR("Sets the background color", "Red", "Green", "Blue"),
        FONTCOLOR("Sets the font color", "Red", "Green", "Blue");
        private final String desc;
        private final String[] paramNames;

        Command(String description, String... paramNames) {
            this.desc = description;
            this.paramNames = paramNames;
        }

        @Override
        public String toString() {
            String s = this.name();
            for (String p : paramNames) {
                s += " <" + p + ">";
            }
            return s;
        }

        public String getDescription() {
            return desc;
        }
    }

    public static boolean execute(Command cmd, String... params) {
        switch (cmd) {
            case HELP:
                for (Command c : Command.values()) {
                    IO.println(c.toString(), IO.MessageType.NORMAL);
                    IO.println(c.getDescription(), IO.MessageType.NORMAL);
                }
                return true;
            case FONTSIZE:
                try {
                    int fontsize = Integer.parseInt(params[0]);
                    Settings s = Settings.getSettings();
                    s.font = new java.awt.Font(s.font.getFontName(), s.font.getStyle(), fontsize);
                    Settings.setSettings(s);
                    IO.println("Fontsize set to " + fontsize, IO.MessageType.NORMAL);
                    return true;
                } catch (Exception ex) {
                    IO.printException(ex);
                    IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
                    return false;
                }
            case SETFONT:
                try {
                    Settings s = Settings.getSettings();
                    s.font = new java.awt.Font(params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]));
                    Settings.setSettings(s);
                    IO.println("Font set to " + s.font, IO.MessageType.NORMAL);
                    return true;
                } catch (Exception ex) {
                    IO.printException(ex);
                    IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
                    return false;
                }
            case BGCOLOR:
                try {
                    Settings s = Settings.getSettings();
                    s.backgroundColor = new java.awt.Color(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
                    Settings.setSettings(s);
                    IO.println("Background color set to " + s.backgroundColor, IO.MessageType.NORMAL);
                    return true;
                } catch (Exception ex) {
                    IO.printException(ex);
                    IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
                    return false;
                }
            case FONTCOLOR:
                try {
                    Settings s = Settings.getSettings();
                    s.fontColor = new java.awt.Color(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
                    Settings.setSettings(s);
                    IO.println("Font color set to " + s.fontColor, IO.MessageType.NORMAL);
                    return true;
                } catch (Exception ex) {
                    IO.printException(ex);
                    IO.println("Invalid parameter. Check your syntax", IO.MessageType.NORMAL);
                    return false;
                }
            default:
                IO.println("Unknown Command. Enter HELP to show commands", IO.MessageType.NORMAL);
                return false;
        }
    }

    public static boolean execute(String in) {
        try {
            String[] args = in.split(" ");
            if (args.length > 0) {
                Command cmd;
                try {
                    cmd = Command.valueOf(args[0].trim().toUpperCase());
                } catch (java.lang.IllegalArgumentException ex) {
                    IO.printException(ex);
                    IO.println("Unknown Command. Enter HELP to show commands", IO.MessageType.NORMAL);
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
