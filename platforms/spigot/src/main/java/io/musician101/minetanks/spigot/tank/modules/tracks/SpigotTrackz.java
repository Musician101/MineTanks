package io.musician101.minetanks.spigot.tank.modules.tracks;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.tank.AbstractTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractTrackz;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotTrackz extends AbstractTrackz<ItemStack>
{
    SpigotTrackz(String name, SpigotTankType type)
    {
        super(name);
        parseTracks(type);
    }

    @Override
    protected <T extends AbstractTankType> void parseTracks(T type)
    {
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
        meta.setLore(Collections.singletonList(CommonItemText.TRACKS));
        item.setItemMeta(meta);
        setItem(item);
    }
}
