package io.musician101.minetanks.common.tank;

public abstract class AbstractCountry<I> extends Info<I>
{
    protected AbstractCountry(String name, I itemStack)
    {
        super(name);
        setItem(itemStack);
    }
}
