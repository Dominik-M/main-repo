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
package main;

import platform.utils.IO;
import platform.utils.SerializableReflectObject;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 09.02.2017
 */
public class Item extends SerializableReflectObject
{

    public enum Type
    {
        STONE("Stone", "stein.png", 2),
        IRON("Iron", "iron.png", 10),
        ENDURIUM("Endurium", "endurium.png", 20),
        TERBIUM("Terbium", "terbium.png", 30),
        CADMIUM("Cadmium", "cadmium.png", 40),
        DURANIUM("Duranium", "duranium.png", 60),
        PALLADIUM("Palladium", "palladium.png", 120),
        STRONZIUM("Stronzium", "stronzium.png", 200),
        URIDIUM("Uridium", "uridium.png", 500);

        private int value, mass;
        private String image;
        private String name;

        Type(String name, String image, int value)
        {
            this(name, image, value, 1);
        }

        Type(String name, String image, int value, int mass)
        {
            this.value = value;
            this.image = image;
            this.name = name;
            this.mass = mass;
        }

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        public int getMass()
        {
            return mass;
        }

        public String getImagename()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return name + " " + value + IO.translate("CREDITS");
        }
    }

    public final Type type;
    private int number;

    public Item(Type type, int n)
    {
        this.type = type;
        number = n;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int n)
    {
        number = n;
    }

    public int getValue()
    {
        return number * type.value;
    }

    public void getMass()
    {

    }

    public String getName()
    {
        return IO.translate(type.name);
    }

    @Override
    public String toString()
    {
        if (number > 1)
        {
            return number + " x " + getName() + " " + getValue() + IO.translate("CREDITS");
        }
        return getName() + " " + getValue() + IO.translate("CREDITS");
    }
}
