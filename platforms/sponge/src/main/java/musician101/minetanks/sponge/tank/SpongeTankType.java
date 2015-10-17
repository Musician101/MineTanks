package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.tank.AbstractTankType;
import musician101.minetanks.sponge.util.ItemRepresentation;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Collections;

public class SpongeTankType extends AbstractTankType implements ItemRepresentation
{
    ItemType type;

    public SpongeTankType(String name, ItemType type)
    {
        super(name);
        this.type = type;
    }

    @Override
    public ItemStack getItem()
    {
        return MTUtils.createCustomItem(type);
    }
}
