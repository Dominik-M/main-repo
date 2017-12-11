package de.thm.oop.chat.base.message;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A message that contains an image that is stored at a remote server.
 * Used to represent a message that has been received from the server.
 */
public class RemoteImageMessage extends ImageMessage {
	
	private String size;
	private String url;
	
	public RemoteImageMessage(long timestamp, boolean incoming,
			String partner, String mimeType, String size, String url) {
		super(timestamp, incoming, partner, mimeType);
		this.size = size;
		this.url = url;
	}
	
	/**
	 * @return the size of the image as a String (incl. the unit)
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @return the URL of the image at the remote server
	 */
	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return super.toString() + " " + size + " " + url;
	}

	@Override
	public InputStream getInputStream() {
		try {
			return (InputStream) new URL(url).getContent();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
