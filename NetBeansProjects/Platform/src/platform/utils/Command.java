package platform.utils;

public abstract class Command
{

    private final String name, desc;
    private final String[] paramNames;

    public Command(String name, String description, String... paramNames)
    {
        this.name = name.toUpperCase();
        this.desc = description;
        this.paramNames = paramNames;
    }

    @Override
    public String toString()
    {
        String s = this.name();
        for (String p : paramNames)
        {
            s += " <" + p + ">";
        }
        return s;
    }

    public String name()
    {
        return name;
    }

    public String getDescription()
    {
        return desc;
    }

    public abstract boolean execute(String... params);
}
