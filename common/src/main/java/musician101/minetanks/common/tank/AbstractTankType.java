package musician101.minetanks.common.tank;

public abstract class AbstractTankType<ItemStack> extends Info<ItemStack>
{
    protected AbstractTankType(String name, ItemStack item)
    {
        super(name);
        setItem(item);
    }
}
