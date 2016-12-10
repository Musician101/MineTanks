package io.musician101.minetanks.spigot.tank;

import io.musician101.minetanks.common.tank.AbstractTankType;
import org.bukkit.inventory.ItemStack;

public class SpigotTankType extends AbstractTankType<ItemStack> {

    SpigotTankType(String name, ItemStack item, int ramModifier) {
        super(name, item, ramModifier);
    }
}
