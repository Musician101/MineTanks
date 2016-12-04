package io.musician101.minetanks.spigot.tank.modules;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractEngine;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotEngine extends AbstractEngine<ItemStack, SpigotTankType>
{
    public SpigotEngine(String name, SpigotTankType type)
    {
        super(name);
        parseEngine(type);
    }

    @Override
    protected void parseEngine(SpigotTankType type)
    {
        ItemStack item = new ItemStack(type.getItem().getType());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.ENGINE));
        item.setItemMeta(meta);
        setItem(item);
    }
}
