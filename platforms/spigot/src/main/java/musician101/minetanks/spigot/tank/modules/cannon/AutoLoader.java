package musician101.minetanks.spigot.tank.modules.cannon;

import musician101.minetanks.spigot.util.HasIcon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AutoLoader extends Cannon implements HasIcon
{
    double cycleTime;
    int clipSize;

    public AutoLoader(String name, int ammoCount, double reloadTime, double cycleTime, int clipSize)
    {
        super(name, ammoCount, reloadTime);
        this.clipSize = clipSize;
        this.cycleTime = cycleTime;
        parseCannon();
    }

    @Override
    public ItemStack getIcon()
    {
        return (ItemStack) cannon;
    }

    private void parseCannon()
    {
        cannon = new ItemStack(Material.BOW);
        ItemMeta meta = getIcon().getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.setLore(Arrays.asList("Your Cannon", "Clip Size: " + clipSize + "/" + clipSize, "Cycle Time: " + cycleTime, "Clip Reload time: " + getReloadTime()));
        getIcon().setItemMeta(meta);
    }
}
