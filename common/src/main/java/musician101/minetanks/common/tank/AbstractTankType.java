package musician101.minetanks.common.tank;

public abstract class AbstractTankType<I> extends Info<I>
{
    public AbstractTankType(String name, I item)
    {
        super(name);
        setItem(item);
    }
}
