package de.thm.oop.chat.base.message;

import java.io.InputStream;

/**
 * A message that contains an image that is stored locally.
 * Used to construct a message in order to send it.
 */
public class LocalImageMessage extends ImageMessage {

	private InputStream imageData;
	
	public LocalImageMessage(String partner, String mimeType, InputStream imageData) {
		super(partner, mimeType);
		this.imageData = imageData;
	}

	public LocalImageMessage(long timestamp, boolean incoming,
			String partner, String mimeType, InputStream imageData) {
		super(timestamp, incoming, partner, mimeType);
		this.imageData = imageData;
	}

	@Override
	public InputStream getInputStream() {
		return imageData;
	}
	
}
