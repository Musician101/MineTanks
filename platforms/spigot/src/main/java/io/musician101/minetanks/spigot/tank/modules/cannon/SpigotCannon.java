package io.musician101.minetanks.spigot.tank.modules.cannon;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractCannon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SpigotCannon extends AbstractCannon<ItemStack>
{
    public SpigotCannon(String name, int ammoCount, double reloadTime)
    {
        super(name, ammoCount, reloadTime);
        parseCannon();
    }

    @Override
    protected void parseCannon()
    {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.setLore(Arrays.asList(CommonItemText.CANNON,
                CommonItemText.reloadTime(getReloadTime())));

        item.setItemMeta(meta);
        setItem(item);
    }
}
