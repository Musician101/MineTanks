package musician101.minetanks.common.tank;

public abstract class Info<I>
{
    I item;
    String name;

    protected Info(String name)
    {
        this.name = name;
    }

    public I getItem()
    {
        return item;
    }

    protected void setItem(I item)
    {
        this.item = item;
    }

    public String getName()
    {
        return name;
    }
}
