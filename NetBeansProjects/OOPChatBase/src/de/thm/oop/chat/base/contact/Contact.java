package de.thm.oop.chat.base.contact;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract base class of any contact (may be a single user or a group of
 * users).
 */
public abstract class Contact implements Serializable
{

    private String name;

    protected Contact(String name)
    {
        this.name = name;
    }

    protected String getName()
    {
        return name;
    }

    public abstract List<String> getUsernames();

}
