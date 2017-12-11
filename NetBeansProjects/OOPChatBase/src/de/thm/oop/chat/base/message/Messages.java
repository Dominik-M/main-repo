package de.thm.oop.chat.base.message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A list of Message objects. In addition, stores the timestamp of the most
 * recent message. This can be used to fetch only new messages from the server.
 */
@SuppressWarnings("serial")
public class Messages extends ArrayList<Message>
{

    private long lastTimestamp = 0;

    public long getLastTimestamp()
    {
        return lastTimestamp;
    }

    @Override
    public boolean addAll(Collection<? extends Message> c)
    {
        for (Message message : c)
        {
            lastTimestamp = Math.max(lastTimestamp, message.getTimestamp());
        }
        return super.addAll(c);
    }

    public boolean contains(Message msg)
    {
        for (Message message : this)
        {
            if (message.toString().equals(msg.toString()))
            {
                return true;
            }
        }
        return false;
    }
}
