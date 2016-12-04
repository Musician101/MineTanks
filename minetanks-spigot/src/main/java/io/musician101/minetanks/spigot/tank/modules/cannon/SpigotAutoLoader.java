package io.musician101.minetanks.spigot.tank.modules.cannon;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SpigotAutoLoader extends SpigotCannon
{
    private final int clipSize;
    private final double cycleTime;

    public SpigotAutoLoader(String name, int ammoCount, double reloadTime, double cycleTime, int clipSize)
    {
        super(name, ammoCount, reloadTime);
        this.clipSize = clipSize;
        this.cycleTime = cycleTime;
        parseCannon();
    }

    public int getClipSize()
    {
        return clipSize;
    }

    public double getCycleTime()
    {
        return cycleTime;
    }

    @Override
    protected void parseCannon()
    {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
        meta.setLore(Arrays.asList(CommonItemText.CANNON,
                CommonItemText.clipSize(clipSize, clipSize),
                CommonItemText.cycleTime(cycleTime),
                CommonItemText.clipReloadTime(getReloadTime())));

        item.setItemMeta(meta);
        setItem(item);
    }
}
