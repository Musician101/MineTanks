package musician101.minetanks.tank.modules.tracks;

import musician101.minetanks.common.tank.modules.AbstractTracks;
import musician101.minetanks.tank.TankType;
import musician101.minetanks.tank.TankTypes;
import musician101.minetanks.util.HasIcon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Trackz extends AbstractTracks implements HasIcon
{
    public Trackz(String name, TankType type)
    {
        super(name);
        parseTracks(type);
    }

    @Override
    public ItemStack getIcon()
    {
        return (ItemStack) tracks;
    }

    private void parseTracks(TankType type)
    {
        Material material = Material.AIR;
        if (type == TankTypes.LIGHT)
            material = Material.LEATHER_BOOTS;
        else if (type == TankTypes.MEDIUM)
            material = Material.IRON_BOOTS;
        else if (type == TankTypes.HEAVY)
            material = Material.DIAMOND_BOOTS;
        else if (type == TankTypes.TD)
            material = Material.CHAINMAIL_BOOTS;
        else if (type == TankTypes.ARTY)
            material = Material.GOLD_BOOTS;

        tracks = new ItemStack(material);
        ItemMeta meta = getIcon().getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Arrays.asList("Your Tracks"));
        getIcon().setItemMeta(meta);
    }
}
