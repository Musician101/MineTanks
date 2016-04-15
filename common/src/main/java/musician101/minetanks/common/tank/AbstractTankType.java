package musician101.minetanks.common.tank;

public abstract class AbstractTankType<ItemStack> extends Info<ItemStack>
{
    protected final int ramModifier;

    protected AbstractTankType(String name, ItemStack item, int ramModifier)
    {
        super(name);
        setItem(item);
        this.ramModifier = ramModifier;
    }

    public int getRamModifier()
    {
        return ramModifier;
    }
}
