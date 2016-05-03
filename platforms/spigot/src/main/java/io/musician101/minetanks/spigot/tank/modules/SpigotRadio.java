package io.musician101.minetanks.spigot.tank.modules;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.tank.modules.AbstractRadio;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotRadio extends AbstractRadio<ItemStack, SpigotTankType>
{
    public SpigotRadio(String name, SpigotTankType type)
    {
        super(name);
        parseRadio(type);
    }

    @Override
    protected void parseRadio(SpigotTankType type)
    {
        Material tankMaterial = Material.AIR;
        for (SpigotTankType stt : SpigotTankTypes.getValues())
            if (stt.getName().equals(type.getName()))
                tankMaterial = stt.getItem().getType();

        Material material = Material.AIR;
        if (tankMaterial == Material.WOOD_SWORD)
            material = Material.LEATHER_CHESTPLATE;
        else if (tankMaterial == Material.STONE_SWORD)
            material = Material.IRON_CHESTPLATE;
        else if (tankMaterial == Material.IRON_SWORD)
            material = Material.DIAMOND_CHESTPLATE;
        else if (tankMaterial == Material.GOLD_SWORD)
            material = Material.CHAINMAIL_CHESTPLATE;
        else if (tankMaterial == Material.DIAMOND_SWORD)
            material = Material.GOLD_CHESTPLATE;
        
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.RADIO));
        item.setItemMeta(meta);
        setItem(item);
    }
}
