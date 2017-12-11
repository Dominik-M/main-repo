/**
 * Copyright (C) 2016 Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Constants;
import utils.Dictionary;
import utils.SortedStringTree;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 14.09.2016
 */
public class Database {

    public static final char SEPARATOR = ',', END_KEY = ':';
    private static Database DB;

    private Dictionary<String, SortedStringTree> values;

    private Database() {
        values = new Dictionary<>();
        if (load(Constants.DB_FILE) >= 0) {
            System.out.println("Loaded Database file " + Constants.DB_FILE);
        } else {
            System.out.println("created a new Database");
        }
    }

    /**
     *
     * @return
     */
    public static Database getInstance() {
        if (DB == null) {
            synchronized (Database.class) {
                if (DB == null) {
                    DB = new Database();
                }
            }
        }
        return DB;
    }

    public void print(PrintStream out) {
        for (String key : values.getKeys()) {
            out.print(key + END_KEY);
            String[] elements = values.get(key).toString().split(SEPARATOR + "");
            for (String s : elements) {
                out.print(s + SEPARATOR);
            }
            out.println();
        }
    }

    public boolean isValid(String key, String value) {
        if (values.containsKey(key)) {
            return values.get(key).contains(value);
        }
        return false;
    }

    public boolean save(File f) {
        try {
            PrintStream out = new PrintStream(f);
            print(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public final int load(File f) {
        int elcount = 0;
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(f));
            int c;
            int state = 0;
            String key = "";
            SortedStringTree elements = new SortedStringTree();
            String curElement = "";
            System.out.println("loading " + f);
            while ((c = reader.read()) >= 0) {
                switch (state) {
                    case 0: // read key
                        if (c == ':') {
                            state++;
                        } else {
                            key += (char) c;
                        }
                        break;
                    case 1: // read element
                        if (c == SEPARATOR) {
                            state++;
                        } else {
                            curElement += (char) c;
                        }
                        break;
                    case 2: // read next element
                        if (values.containsKey(key)) {
                            if (values.get(key).add(curElement)) {
                                System.out.println(curElement + " added to " + key);
                                elcount++;
                            } else {
                                //System.out.println(curElement + " already contained in " + key);
                            }
                        } else {
                            elements.add(curElement);
                            elcount++;
                        }
                        if (c == '\n' || c == '\r') {
                            state++;
                        } else {
                            state = 1;
                            curElement = "" + (char) c;
                        }
                        break;
                    case 3: // read next key
                        if (values.add(key, elements)) {
                            System.out.println("Added new Key: " + key);
                        }
                        key = "";
                        elements = new SortedStringTree();
                        curElement = "";
                        state = 0;
                        break;
                    default:
                        System.err.println("Invalid State " + state);
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return elcount;
    }
}
