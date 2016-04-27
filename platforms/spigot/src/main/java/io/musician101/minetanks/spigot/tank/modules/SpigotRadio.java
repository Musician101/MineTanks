package io.musician101.minetanks.spigot.tank.modules;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractRadio;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotRadio extends AbstractRadio<ItemStack>
{
    public SpigotRadio(String name, SpigotTankType type)
    {
        super(name);
        parseRadio(type);
    }

    private void parseRadio(SpigotTankType type)
    {
        type.getItem();
        Material material = Material.AIR;
        if (type == SpigotTankTypes.getTankType(CommonTankTypes.LIGHT))
            material = Material.LEATHER_LEGGINGS;
        else if (type == SpigotTankTypes.getTankType(CommonTankTypes.MEDIUM))
            material = Material.IRON_LEGGINGS;
        else if (type == SpigotTankTypes.getTankType(CommonTankTypes.HEAVY))
            material = Material.DIAMOND_LEGGINGS;
        else if (type == SpigotTankTypes.getTankType(CommonTankTypes.TD))
            material = Material.CHAINMAIL_LEGGINGS;
        else if (type == SpigotTankTypes.getTankType(CommonTankTypes.SPG))
            material = Material.GOLD_LEGGINGS;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.RADIO));
        item.setItemMeta(meta);
        setItem(item);
    }
}
