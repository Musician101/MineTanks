package musician101.minetanks.spigot.tank.modules;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.modules.AbstractEngine;
import musician101.minetanks.spigot.tank.SpigotTankType;
import musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotEngine extends AbstractEngine<ItemStack>
{
    public SpigotEngine(String name, SpigotTankType type)
    {
        super(name);
        parseEngine(type);
    }

    private void parseEngine(SpigotTankType type)
    {
        Material material = Material.AIR;
        if (type == SpigotTankTypes.LIGHT)
            material = Material.LEATHER_LEGGINGS;
        else if (type == SpigotTankTypes.MEDIUM)
            material = Material.IRON_LEGGINGS;
        else if (type == SpigotTankTypes.HEAVY)
            material = Material.DIAMOND_LEGGINGS;
        else if (type == SpigotTankTypes.TD)
            material = Material.CHAINMAIL_LEGGINGS;
        else if (type == SpigotTankTypes.ARTY)
            material = Material.GOLD_LEGGINGS;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.ENGINE));
        item.setItemMeta(meta);
        setItem(item);
    }
}
