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
package utils;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 07.08.2016
 */
public abstract class SerializableReflectObject implements java.io.Serializable, java.lang.Cloneable
{

    private boolean printed = false;

    /**
     * Get a dictionary conatining all attributes declared in the subclass of
     * this class and its assigned values.
     *
     * @return Dictionary instance where keys are attributes names and values
     * the attribute values. Implementation example: public
     * Dictionary<String, Object>
     * getAttributes() { Dictionary<String, Object> values = new Dictionary<>();
     * for (Field f : this.getClass().getDeclaredFields()) { try {
     * values.add(f.getName(), f.get(this)); } catch (IllegalArgumentException
     * ex) {
     * Logger.getLogger(SerializableReflectObject.class.getName()).log(Level.SEVERE,
     * null, ex); } catch (IllegalAccessException ex) {
     * Logger.getLogger(SerializableReflectObject.class.getName()).log(Level.SEVERE,
     * null, ex); } } return values; }
     */
    public abstract Dictionary<String, Object> getAttributes();

    /**
     *
     */
    public void printDataRecursive()
    {
        printDataRecursive(0);
    }

    /**
     *
     * @param depth
     */
    public final void printDataRecursive(int depth)
    {
        if (!printed)
        {
            Dictionary<String, Object> values = getAttributes();
            for (String key : values.getKeys())
            {
                Object value = values.get(key);
                String line = key + " = ";
                if (value != null)
                {
                    line = line + value.toString();
                }
                else
                {
                    line = line + "NULL";
                }
                for (int i = 0; i < depth; i++)
                {
                    line = "  " + line;
                }
                IO.println(line, IO.MessageType.NORMAL);
                printed = true;
                if (SerializableReflectObject.class.isInstance(value))
                {
                    ((SerializableReflectObject) value).printDataRecursive(depth + 1);
                }
            }
            printed = false;
        }
    }
}
