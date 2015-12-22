package musician101.minetanks.common.tank.modules;

import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.common.tank.Info;

public abstract class AbstractTurret<ItemStack> extends Info<ItemStack>
{
    private final Armor armor;

    protected AbstractTurret(String name, Armor armor)
    {
        super(name);
        this.armor = armor;
    }

    public Armor getArmor()
    {
        return armor;
    }
}
