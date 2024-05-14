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
package utils;

/**
 * Created 09.03.2016
 * 
 * @author Dominik Messerschmidt (dominik.messerschmidt@continental-corporation.com)
 * 
 */
public enum Text {
	HELLO("Hello World"), EXIT_QUESTION("Exit " + Constants.APP_NAME + "?"), CONFIRM_EXIT(
	"Confirm exit");

	private String text;

	Text(String txt) {
		text = txt;
	}

	@Override
	public String toString() {
		return text;
	}

	private static utils.Constants.Language currentLanguage = utils.Constants.DEFAULT_LANGUAGE;

	public static utils.Constants.Language getCurrentLanguage() {
		return currentLanguage;
	}

	public static boolean loadLanguageFile(utils.Constants.Language lang) {
		try {
			java.io.File file = new java.io.File(lang.getFileName());
			java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
			String line;
			reader.skip(1); // Skip SOF
			while ((line = reader.readLine()) != null) {
				String name = line.substring(0, line.indexOf(" "));
				String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
				for (Text t : Text.values()) {
					if (t.name().equals(name)) {
						t.text = value;
						break;
					}
				}
			}
			reader.close();
			currentLanguage = lang;
			IO.println("Language switched to " + currentLanguage.toString(), IO.MessageType.DEBUG);
			return true;
		} catch (Exception ex) {
			IO.println("failed to load Language: " + ex.toString(), IO.MessageType.ERROR);
			return false;
		}
	}

	private static boolean saveLanguageFile(String filename) {
		try {
			java.io.File file = new java.io.File(filename);
			java.io.PrintWriter writer = new java.io.PrintWriter(file);
			for (Text t : Text.values()) {
				writer.println(t.name() + " = " + "\"" + t.text + "\"");
			}
			writer.close();
			IO.println("saved language file " + filename, IO.MessageType.DEBUG);
			return true;
		} catch (Exception ex) {
			IO.println("failed to save Language: " + ex.toString(), IO.MessageType.ERROR);
			return false;
		}
	}

	public static boolean saveLanguageFile() {
		return saveLanguageFile(currentLanguage.getFileName());
	}

	public static void createAllLanguageFiles() {
		for (utils.Constants.Language lang : utils.Constants.Language.values()) {
			if (!new java.io.File(lang.getFileName()).exists())
				saveLanguageFile(lang.getFileName());
		}
	}
}
