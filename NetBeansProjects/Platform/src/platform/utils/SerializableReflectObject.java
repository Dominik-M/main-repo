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
package platform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 04.10.2016
 */
public abstract class SerializableReflectObject implements Serializable, Cloneable {

	/**
	 * nonsense made by Eclipse
	 */
	private static final long serialVersionUID = 6452098271245791661L;
	private static long idCounter = 0;

	public final long ID;
	private boolean printed;

	public SerializableReflectObject() {
		ID = idCounter++;
		printed = false;
	}

	public static SerializableReflectObject load(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			SerializableReflectObject s = (SerializableReflectObject) ois.readObject();
			fis.close();
			ois.close();
			IO.println("loaded " + file, IO.MessageType.DEBUG);
			return s;
		} catch (Exception ex) {
			IO.println("failed to load " + file, IO.MessageType.ERROR);
			IO.printException(ex);
			return null;
		}
	}

	public boolean save(File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			fos.close();
			oos.close();
			return true;
		} catch (Exception ex) {
			IO.println("failed to save " + file, IO.MessageType.ERROR);
			IO.printException(ex);
			return false;
		}
	}

	public Dictionary<String, Object> getAttributes() {
		Dictionary<String, Object> atts = new Dictionary<>();
		try {
			for (Field f : this.getClass().getDeclaredFields()) {
				if (!Modifier.isPrivate(f.getModifiers())
				&& !Modifier.isProtected(f.getModifiers())) {
					String name = f.getName();
					Object value = f.get(this);
					atts.add(name, value);
				}
			}
		} catch (Exception ex) {
			IO.printException(ex);
		}
		return atts;
	}

	public void printRecursive(PrintStream out) {
		printRecursive("", out);
	}

	@SuppressWarnings("unchecked")
	private void printRecursive(String prefix, String fieldname, Iterable<Object> field,
	PrintStream out) {
		int i = 0;
		for (Object value : field) {
			String key = fieldname + "[" + i + "]";
			out.println(prefix + key + " = " + value);
			if (SerializableReflectObject.class.isInstance(value)) {
				((SerializableReflectObject) value).printRecursive(prefix + "  ", out);
			} else if (Iterable.class.isInstance(value)) {
				printRecursive(prefix + "  ", key, ((Iterable<Object>) value), out);
			}
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	private void printRecursive(String prefix, PrintStream out) {
		if (!printed) {
			printed = true;
			Dictionary<String, Object> atts = this.getAttributes();
			for (int i = 0; i < atts.size(); i++) {
				out.println(prefix + atts.getKey(i) + " = " + atts.getElement(i));
				if (SerializableReflectObject.class.isInstance(atts.getElement(i))) {
					((SerializableReflectObject) atts.getElement(i)).printRecursive(prefix + "  ",
					out);
				} else if (Iterable.class.isInstance(atts.getElement(i))) {
					printRecursive(prefix + "  ", atts.getKey(i),
					((Iterable<Object>) atts.getElement(i)), out);
				}
			}
			printed = false;
		}
	}

	/**
	 * Enhanced compare for instances of {@link SerializableReflectObject}.
	 * 
	 * @param other
	 *        instance of {@link SerializableReflectObject} to compare to.
	 * @return true if the given instance equals this.
	 */
	public boolean equals(SerializableReflectObject other) {
		return other.ID == ID;
	}
}
