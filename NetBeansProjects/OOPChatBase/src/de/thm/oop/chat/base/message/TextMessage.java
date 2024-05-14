package de.thm.oop.chat.base.message;

/**
 * A simple text message.
 */
public class TextMessage extends StringMessage {
	
	private String text;
	
	public TextMessage(String partner, String text) {
		super(partner);
		this.text = text;
	}

	public TextMessage(long timestamp, boolean incoming, String partner, String text) {
		super(timestamp, incoming, partner);
		this.text = text;
	}
	
	@Override
	public String getType() {
		return TYPE_TXT;
	}

	@Override
	public String getMessageString() {
		return text;
	}

}
