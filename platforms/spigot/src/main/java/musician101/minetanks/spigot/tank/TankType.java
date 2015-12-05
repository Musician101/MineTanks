package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.tank.AbstractTankType;
import musician101.minetanks.spigot.util.ItemRepresentation;
import org.bukkit.inventory.ItemStack;

public class TankType extends AbstractTankType implements ItemRepresentation
{
    ItemStack item;

    public TankType(String name, ItemStack item)
    {
        super(name);
        this.item = item;
    }

    @Override
    public ItemStack getItem()
    {
        return item;
    }
}
