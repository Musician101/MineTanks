package musician101.minetanks.spigot.tank.modules.cannon;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.modules.AbstractCannon;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.util.ItemRepresentation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Cannon extends AbstractCannon implements ItemRepresentation
{
    public Cannon(String name, int ammoCount, double reloadTime)
    {
        super(name, ammoCount, reloadTime);
        parseCannon();
    }

    @Override
    public ItemStack getItem()
    {
        return (ItemStack) cannon;
    }

    private void parseCannon()
    {
        cannon = new ItemStack(Material.BOW);
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.setLore(Arrays.asList(CommonItemText.CANNON, SpigotReference.number(CommonItemText.RELOAD_TIME, getReloadTime())));
        getItem().setItemMeta(meta);
    }
}
