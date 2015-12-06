package musician101.minetanks.spigot.tank.modules.cannon;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.spigot.SpigotReference;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AutoLoader extends Cannon
{
    private final double cycleTime;
    private final int clipSize;

    public AutoLoader(String name, int ammoCount, double reloadTime, double cycleTime, int clipSize)
    {
        super(name, ammoCount, reloadTime);
        this.clipSize = clipSize;
        this.cycleTime = cycleTime;
        parseCannon();
    }

    public double getCycleTime()
    {
        return cycleTime;
    }

    public int getClipSize()
    {
        return clipSize;
    }

    private void parseCannon()
    {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.setLore(Arrays.asList(CommonItemText.CANNON, SpigotReference.number(CommonItemText.CLIP_SIZE, clipSize), SpigotReference.number(CommonItemText.CYCLE_TIME, cycleTime), SpigotReference.number(CommonItemText.CLIP_RELOAD_TIME, getReloadTime())));
        item.setItemMeta(meta);
        setItem(item);
    }
}
