package io.musician101.minetanks.forge.tank;

import io.musician101.minetanks.common.tank.AbstractCountry;
import net.minecraft.item.ItemStack;

public class ForgeCountry extends AbstractCountry<ItemStack>
{
    ForgeCountry(String name, ItemStack itemStack)
    {
        super(name, itemStack);
    }
}
