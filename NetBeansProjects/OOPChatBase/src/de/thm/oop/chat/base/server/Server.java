package de.thm.oop.chat.base.server;

import de.thm.oop.chat.base.message.Message;

/**
 * The base Server interface. 
 */
public interface Server {
	
	/**
	 * Has to be called first before calling any other operation.
	 * @param username the username used by all subsequent operations.
	 * @param password the password used by all subsequent operations.
	 */
	void login(String username, String password);
	
	/**
	 * After calling logout, login has to be called again
	 * before any other operation can be used.
	 */
	void logout();
	
	/**
	 * Sends a message.
	 * @param message the message to be sent.
	 */
	void sendMessage(Message message);
}
