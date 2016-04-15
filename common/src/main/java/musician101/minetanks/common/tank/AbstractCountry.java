package musician101.minetanks.common.tank;

public abstract class AbstractCountry<ItemStack> extends Info<ItemStack>
{
    protected AbstractCountry(String name, ItemStack itemStack)
    {
        super(name);
        setItem(itemStack);
    }
}
