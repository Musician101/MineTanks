package musician101.minetanks.tank.modules.cannon;

import musician101.minetanks.common.tank.modules.AbstractCannon;
import musician101.minetanks.util.HasIcon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Cannon extends AbstractCannon implements HasIcon
{
    public Cannon(String name, int ammoCount, double reloadTime)
    {
        super(name, ammoCount, reloadTime);
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
        meta.setLore(Arrays.asList("Your Cannon", "Reload time: " + getReloadTime()));
        getIcon().setItemMeta(meta);
    }
}
