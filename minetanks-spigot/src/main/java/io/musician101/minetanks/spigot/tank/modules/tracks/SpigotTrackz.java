package io.musician101.minetanks.spigot.tank.modules.tracks;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractTrackz;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import java.util.Collections;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpigotTrackz extends AbstractTrackz<ItemStack, SpigotTankType> {

    SpigotTrackz(String name, SpigotTankType type) {
        super(name);
        parseTracks(type);
    }

    @Override
    protected void parseTracks(SpigotTankType type) {
        ItemStack item = new ItemStack(type.getItem().getType());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.TRACKS));
        item.setItemMeta(meta);
        setItem(item);
    }
}
