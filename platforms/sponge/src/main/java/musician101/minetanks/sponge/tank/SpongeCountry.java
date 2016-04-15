package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.tank.AbstractCountry;
import org.spongepowered.api.item.inventory.ItemStack;

public class SpongeCountry extends AbstractCountry<ItemStack>
{
    public SpongeCountry(String name, ItemStack itemStack)
    {
        super(name, itemStack);
    }
}
