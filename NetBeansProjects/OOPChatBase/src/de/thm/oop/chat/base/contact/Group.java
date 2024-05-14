package de.thm.oop.chat.base.contact;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of (other) contacts.
 * Usually used to represent a group of users.
 */
public class Group extends Contact {
	
	private List<Contact> members;
	
	public Group(String name, List<Contact> members) {
		super(name);
		this.members = members;
	}

	@Override
	public String toString() {
		List<String> usernames = getUsernames();
		String usernamesString = "";
		for (int i = 0; i < usernames.size(); i++) {
			if (i > 0) usernamesString += ", ";
			usernamesString += usernames.get(i);
		}
		
		return "[G] " + getName() + " (" + usernamesString + ")";
	}

	@Override
	public List<String> getUsernames() {		
		List<String> result = new ArrayList<String>();
		if (members != null) {
			for (Contact member : members) {
				result.addAll(member.getUsernames());
			}
		}
		return result;
	}

}
