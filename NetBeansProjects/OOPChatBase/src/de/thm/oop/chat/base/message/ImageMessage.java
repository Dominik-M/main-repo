package de.thm.oop.chat.base.message;

import java.io.InputStream;

/**
 * Abstract base class of all messages that contain an image.
 */
public abstract class ImageMessage extends Message {
	
	private String mimeType;
	
	protected ImageMessage(String partner, String mimeType) {
		super(partner);
		this.mimeType = mimeType;
	}

	protected ImageMessage(long timestamp, boolean incoming, String partner, String mimeType) {
		super(timestamp, incoming, partner);
		this.mimeType = mimeType;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	/**
	 * @return an InputStream that contains the image data
	 */
	public abstract InputStream getInputStream();

	@Override
	public String getType() {
		return TYPE_IMG;
	}

	@Override
	public String toString() {
		return super.toString() + " " + mimeType;
	}
}
