package io.musician101.minetanks.spigot.tank;

import io.musician101.minetanks.common.tank.AbstractCountry;
import org.bukkit.inventory.ItemStack;

public class SpigotCountry extends AbstractCountry<ItemStack> {

    SpigotCountry(String name, ItemStack itemStack) {
        super(name, itemStack);
    }
}
