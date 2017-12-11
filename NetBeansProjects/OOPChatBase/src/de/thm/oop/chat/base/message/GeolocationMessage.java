package de.thm.oop.chat.base.message;

/**
 * A message that contains a geolocation.
 */
public class GeolocationMessage extends StringMessage {
	
	private double latitude;
	private double longitude;

	public GeolocationMessage(String partner, double latitude, double longitude) {
		super(partner);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public GeolocationMessage(long timestamp, boolean incoming, String partner,
			String message) {
		super(timestamp, incoming, partner);
		String[] parts = message.split("/");
		latitude = Double.parseDouble(parts[0]);
		longitude = Double.parseDouble(parts[1]);
	}
		

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public String getMessageString() {
		return latitude + "/" + longitude;
	}

	@Override
	public String getType() {
		return TYPE_GEO;
	}

}
