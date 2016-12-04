package io.musician101.minetanks.forge.tank;

import io.musician101.minetanks.common.tank.AbstractTankType;
import net.minecraft.item.ItemStack;

public class ForgeTankType extends AbstractTankType<ItemStack>
{
    ForgeTankType(String name, ItemStack item, int ramModifier)
    {
        super(name, item, ramModifier);
    }
}
