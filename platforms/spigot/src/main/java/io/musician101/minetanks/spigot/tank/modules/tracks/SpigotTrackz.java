package io.musician101.minetanks.spigot.tank.modules.tracks;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractTrackz;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotTrackz extends AbstractTrackz<ItemStack, SpigotTankType>
{
    SpigotTrackz(String name, SpigotTankType type)
    {
        super(name);
        parseTracks(type);
    }

    @Override
    protected void parseTracks(SpigotTankType type)
    {
        Material tankMaterial = Material.AIR;
        for (SpigotTankType stt : SpigotTankTypes.getValues())
            if (stt.getName().equals(type.getName()))
                tankMaterial = stt.getItem().getType();

        Material material = Material.AIR;
        if (tankMaterial == Material.WOOD_SWORD)
            material = Material.LEATHER_BOOTS;
        else if (tankMaterial == Material.STONE_SWORD)
            material = Material.IRON_BOOTS;
        else if (tankMaterial == Material.IRON_SWORD)
            material = Material.DIAMOND_BOOTS;
        else if (tankMaterial == Material.GOLD_SWORD)
            material = Material.CHAINMAIL_BOOTS;
        else if (tankMaterial == Material.DIAMOND_SWORD)
            material = Material.GOLD_BOOTS;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.TRACKS));
        item.setItemMeta(meta);
        setItem(item);
    }
}
