package musician101.minetanks.tank;

import musician101.minetanks.common.tank.AbstractTankType;
import musician101.minetanks.util.HasIcon;
import org.bukkit.inventory.ItemStack;

public class TankType extends AbstractTankType implements HasIcon
{
    public TankType(String name, ItemStack icon)
    {
        super(name, icon);
    }

    @Override
    public ItemStack getIcon()
    {
        return (ItemStack) icon;
    }
}