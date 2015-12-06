package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.tank.AbstractTankType;
import org.bukkit.inventory.ItemStack;

public class TankType extends AbstractTankType<ItemStack>
{
    public TankType(String name, ItemStack item)
    {
        super(name, item);
    }
}
