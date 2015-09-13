package musician101.minetanks.common.tank;

public abstract class Info
{
    String name;

    protected Info(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
