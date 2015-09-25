package musician101.minetanks.common;

public abstract class AbstractCommand
{
    boolean isPlayerOnly;
    int minArgs;
    String description;
    String name;
    String usage;

    public AbstractCommand(String name, String description, String usage, int minArgs, boolean isPlayerOnly)
    {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.minArgs = minArgs;
        this.isPlayerOnly = isPlayerOnly;
    }

    public int getMinArgs()
    {
        return minArgs;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getUsage()
    {
        return usage;
    }

    public boolean isPlayerOnly()
    {
        return isPlayerOnly;
    }

    public abstract String getCommandHelpInfo();
}
