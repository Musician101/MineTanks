package io.musician101.minetanks.common.tank;

public abstract class Info<I>
{
    private I item;
    private final String name;

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
