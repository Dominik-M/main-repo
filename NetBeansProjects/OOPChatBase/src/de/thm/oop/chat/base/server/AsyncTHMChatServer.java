package de.thm.oop.chat.base.server;

import java.util.List;

import de.thm.oop.chat.base.contact.Contacts;
import de.thm.oop.chat.base.contact.User;
import de.thm.oop.chat.base.message.Message;
import de.thm.oop.chat.base.message.Messages;

/**
 * A concrete implementation of an asynchronous server connection
 * using the "THM Chat Server".
 * Android Apps must use this asynchronous variant.
 */
public class AsyncTHMChatServer implements AsyncServer {	

	private SyncTHMChatServer syncServer = new SyncTHMChatServer();

	@Override
	public void login(String username, String password) {
		syncServer.login(username, password);
	}

	@Override
	public void logout() {
		syncServer.logout();
	}

	@Override
	public void sendMessage(final Message message) {
		(new Thread() {
			@Override
			public void run() {
				syncServer.sendMessage(message);
			}
		}).start();
	}

	@Override
	public void getUsers(final Contacts contacts, final Runnable callback) {
		(new Thread() {
			@Override
			public void run() {
				List<User> users = syncServer.getUsers();
				contacts.addAll(users);
				callback.run();
			}
		}).start();
	}

	@Override
	public void getMessages(Messages messages, Runnable callback) {
		getMessages(messages, callback, 0);
	}

	@Override
	public void getMessages(final Messages messages, final Runnable callback, final long since) {
		(new Thread() {
			@Override
			public void run() {
				List<Message> messagesFromServer = syncServer.getMessages(since);
				messages.addAll(messagesFromServer);
				callback.run();
			}
		}).start();
	}

}
