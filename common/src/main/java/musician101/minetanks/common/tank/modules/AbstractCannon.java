package musician101.minetanks.common.tank.modules;

import musician101.minetanks.common.tank.Info;

public abstract class AbstractCannon<ItemStack> extends Info<ItemStack>
{
    private final double reloadTime;
    private final int ammoCount;

    protected AbstractCannon(String name, int ammoCount, double reloadTime)
    {
        super(name);
        this.ammoCount = ammoCount;
        this.reloadTime = reloadTime;
    }

    public double getReloadTime()
    {
        return reloadTime;
    }

    public int getAmmoCount()
    {
        return ammoCount;
    }
}
