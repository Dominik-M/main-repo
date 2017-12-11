package de.thm.oop.chat.base.message;

/**
 * Abstract base class for all message types that can be transmitted by a simple string.
 */
public abstract class StringMessage extends Message {
	
	protected StringMessage(String partner) {
		super(partner);
	}

	protected StringMessage(long timestamp, boolean incoming, String partner) {
		super(timestamp, incoming, partner);
	}

	/**
	 * @return the message string that can be exchanged with the server
	 */
	public abstract String getMessageString();
	
	@Override
	public String toString() {
		return super.toString() + " " + getMessageString();
	}

}
