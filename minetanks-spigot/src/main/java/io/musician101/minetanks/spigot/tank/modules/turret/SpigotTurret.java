package io.musician101.minetanks.spigot.tank.modules.turret;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.common.tank.modules.AbstractTurret;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotTurret extends AbstractTurret<ItemStack, SpigotTankType>
{
    SpigotTurret(String name, SpigotTankType type, Armor armor)
    {
        super(name, armor);
        parseTurret(type);
    }

    @Override
    protected void parseTurret(SpigotTankType type)
    {
        ItemStack item = new ItemStack(type.getItem().getType());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.TURRET));
        item.setItemMeta(meta);
        setItem(item);
    }
}
