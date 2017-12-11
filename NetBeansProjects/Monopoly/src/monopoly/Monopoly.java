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
package monopoly;

import java.awt.Color;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 14.02.2017
 */
public class Monopoly
{

    public static final int COLOR_BLUE = Color.BLUE.getRGB();
    public static final int COLOR_CYAN = Color.CYAN.getRGB();
    public static final int COLOR_PURPLE = 0xFFFF00FF;
    public static final int COLOR_ORANGE = Color.ORANGE.getRGB();
    public static final int COLOR_RED = Color.RED.getRGB();
    public static final int COLOR_YELLOW = Color.YELLOW.getRGB();
    public static final int COLOR_GREEN = Color.GREEN.getRGB();

    private static Monopoly instance;

    private Field[] felder;
    private String currency = ",- DM";

    private Monopoly()
    {
        init();
    }

    public final void init()
    {
        System.out.println("Monopoly.init()");
        felder = new Field[]
        {
            new Field(Field.Type.GO, "LOS", 4000, false),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
            new Field(Field.Type.COMMUNITY, "Gemein- schaftsfeld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Field(Field.Type.TAXES, "Einkommen- Steuer", 4000, false),
            new Field(Field.Type.STATION, "Südbahnhof", 4000, true),
            new Street("Chaussee- Straße", 2000, COLOR_CYAN, 200, 500),
            new Field(Field.Type.COMMUNITY, "Gemein- schaftsfeld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
            new Field(Field.Type.PRISON, "Gefängnis", 0, false),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
            new Field(Field.Type.COMMUNITY, "Gemein- schaftsfeld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Field(Field.Type.TAXES, "Einkommen- Steuer", 4000, false),
            new Field(Field.Type.STATION, "Südbahnhof", 4000, true),
            new Street("Chaussee-Straße", 2000, COLOR_CYAN, 200, 500),
            new Field(Field.Type.COMMUNITY, "Gemeinschafts-Feld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
            new Field(Field.Type.FREEPARKING, "Frei Parken", 0, false),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
            new Field(Field.Type.COMMUNITY, "Gemeinschafts-Feld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Field(Field.Type.TAXES, "Einkommen-Steuer", 4000, false),
            new Field(Field.Type.STATION, "Südbahnhof", 4000, true),
            new Street("Chaussee-Straße", 2000, COLOR_CYAN, 200, 500),
            new Field(Field.Type.COMMUNITY, "Gemeinschafts-Feld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
            new Field(Field.Type.GOTOPRISON, "Gehen Sie-In das-Gefängnis", 0, false),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
            new Field(Field.Type.COMMUNITY, "Gemeinschafts-Feld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Field(Field.Type.TAXES, "Einkommen-Steuer", 4000, false),
            new Field(Field.Type.STATION, "Südbahnhof", 4000, true),
            new Street("Chaussee-Straße", 2000, COLOR_CYAN, 200, 500),
            new Field(Field.Type.COMMUNITY, "Gemeinschafts-Feld", 0, false),
            new Street("Turmstraße", 1200, COLOR_BLUE, 150, 500),
            new Street("Badstraße", 1200, COLOR_BLUE, 120, 500),
        };
    }

    public static final Monopoly getInstance()
    {
        if (instance == null)
        {
            synchronized (Monopoly.class)
            {
                if (instance == null)
                {
                    instance = new Monopoly();
                }
            }
        }
        return instance;
    }

    public int getWidth()
    {
        return felder.length / 4;
    }

    public int getHeight()
    {
        return felder.length / 4;
    }

    public Field getField(int index)
    {
        return felder[index];
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }
}
