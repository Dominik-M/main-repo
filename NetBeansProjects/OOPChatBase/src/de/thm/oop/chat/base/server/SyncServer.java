package de.thm.oop.chat.base.server;

import java.util.List;

import de.thm.oop.chat.base.contact.User;
import de.thm.oop.chat.base.message.Message;

/**
 * Interface for a synchronous server connection, i.e.,
 * all operations return not before the server has responded.
 */
public interface SyncServer extends Server {
	/**
	 * @return a list of all known users. Messages can be sent to
	 * 		   any known user.
	 */
	List<User> getUsers();
	
	/**
	 * @return all messages for the current user (both sent and received ones).
	 */
	List<Message> getMessages();
	
	/**
	 * @param since used to ignore older messages.
	 * @return all messages for the current user (both sent and received ones)
	 * 		   whose timestamp is >= "since".
	 */
	List<Message> getMessages(long since);
}
