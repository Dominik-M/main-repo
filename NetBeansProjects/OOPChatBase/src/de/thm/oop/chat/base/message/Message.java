package de.thm.oop.chat.base.message;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Abstract base class of all message types.
 */
public abstract class Message implements Serializable
{

    public static final String TYPE_TXT = "txt";
    public static final String TYPE_GEO = "geo";
    public static final String TYPE_IMG = "img";

    private long timestamp;
    private boolean incoming;
    private String partner;
    private DateFormat dateFormat;

    protected Message(String partner)
    {
        this(0, false, partner);
    }

    protected Message(long timestamp, boolean incoming, String partner)
    {
        this.timestamp = timestamp;
        this.incoming = incoming;
        this.partner = partner;

        dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMAN);
        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
    }

    /**
     * @return type of this message. One of the "TYPE_"-constants defined in the
     * Message-class.
     */
    public abstract String getType();

    protected long getTimestamp()
    {
        return timestamp;
    }

    /**
     * @return the timestamp of this message in a human-readable format.
     */
    public String getDate()
    {
        return dateFormat.format(new Date(timestamp));
    }

    /**
     * @return true iff this is an incoming message.
     */
    public boolean isIncoming()
    {
        return incoming;
    }

    /**
     * @return the receiver (of an outgoing message) or the sender (of an
     * incoming message).
     */
    public String getPartner()
    {
        return partner;
    }

    /**
     * @return the receiver / sender incl. a sign indicating whether it is an
     * outgoing or incoming message.
     */
    public String getDirectedPartner()
    {
        if (incoming)
        {
            return "< " + partner;
        }
        else
        {
            return "> " + partner;
        }
    }

    @Override
    public String toString()
    {
        return getDate() + " [" + getDirectedPartner() + "]";
    }

}
