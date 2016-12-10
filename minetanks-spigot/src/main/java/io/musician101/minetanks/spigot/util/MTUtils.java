package io.musician101.minetanks.spigot.util;

import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MTUtils {

    private MTUtils() {

    }

    public static ItemStack createCustomItem(Material material, String displayName, String... description) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + displayName);
        if (description != null)
            meta.setLore(Arrays.asList(description));

        item.setItemMeta(meta);
        return item;
    }
}
