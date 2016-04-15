package musician101.minetanks.spigot.util;

import musician101.minetanks.spigot.tank.SpigotTanks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MTUtils
{
    public static ItemStack createCustomItem(Material material, String displayName, String description)
    {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00A7a" + displayName);
        if (!description.equals(""))
            meta.setLore(Collections.singletonList(description));

        item.setItemMeta(meta);
        return item;
    }

    public static int getMenuSize()
    {
        int rows = 0;
        while (rows * 9 < SpigotTanks.tanks.size())
            rows++;

        return rows * 9;
    }
}
