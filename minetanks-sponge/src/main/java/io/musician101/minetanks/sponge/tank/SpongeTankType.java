package io.musician101.minetanks.sponge.tank;

import io.musician101.minetanks.common.tank.AbstractTankType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;


public class SpongeTankType extends AbstractTankType<ItemStack> {

    public SpongeTankType(String name, ItemType type, int ramModifier) {
        super(name, ItemStack.of(type, 1), ramModifier);
    }
}
