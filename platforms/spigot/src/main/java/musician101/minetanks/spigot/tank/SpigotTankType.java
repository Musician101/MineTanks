package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.tank.AbstractTankType;
import org.bukkit.inventory.ItemStack;

public class SpigotTankType extends AbstractTankType<ItemStack>
{
    public SpigotTankType(String name, ItemStack item)
    {
        super(name, item);
    }
}
