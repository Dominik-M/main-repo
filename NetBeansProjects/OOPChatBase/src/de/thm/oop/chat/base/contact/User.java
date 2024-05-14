package de.thm.oop.chat.base.contact;

import java.util.ArrayList;
import java.util.List;

/**
 * A single user.
 */
public class User extends Contact {

	public User(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public List<String> getUsernames() {
		List<String> result = new ArrayList<String>();
		result.add(getName());
		return result;
	}	
	
}
