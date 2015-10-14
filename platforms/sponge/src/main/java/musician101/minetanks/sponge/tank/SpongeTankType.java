package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.tank.AbstractTankType;
import musician101.minetanks.sponge.util.ItemRepresentation;
import org.spongepowered.api.item.inventory.ItemStack;

public class SpongeTankType extends AbstractTankType implements ItemRepresentation
{
    public SpongeTankType(String name, ItemStack icon)
    {
        super(name, icon);
    }

    @Override
    public ItemStack getItem()
    {
        return (ItemStack) icon;
    }
}
