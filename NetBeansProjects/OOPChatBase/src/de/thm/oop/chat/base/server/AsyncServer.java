package de.thm.oop.chat.base.server;

import de.thm.oop.chat.base.contact.Contacts;
import de.thm.oop.chat.base.message.Messages;

/**
 * Interface for an asynchronous server connection, i.e.,
 * all operations return immediately. After the server has
 * responded, the provided callback is invoked.
 */
public interface AsyncServer extends Server {
	/** All known users are added the given contacts-list. */
	void getUsers(Contacts contacts, Runnable callback);
	
	/**
	 * All messages for the current user (both sent and received ones)
	 * are added the given messages-list.
	 */	
	void getMessages(Messages messages, Runnable callback);
	
	/**
	 * All messages for the current user (both sent and received ones)
	 * whose timestamp is >= "since" are added the given messages-list.
	 */
	void getMessages(Messages messages, Runnable callback, long since);
}
