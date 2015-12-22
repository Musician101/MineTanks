package musician101.minetanks.common.tank;

public abstract class Info<ItemStack>
{
    private ItemStack item;
    private final String name;

    protected Info(String name)
    {
        this.name = name;
    }

    public ItemStack getItem()
    {
        return item;
    }

    protected void setItem(ItemStack item)
    {
        this.item = item;
    }

    public String getName()
    {
        return name;
    }
}
