package musician101.minetanks.spigot.tank.modules;

import java.util.Arrays;

import musician101.minetanks.common.tank.modules.AbstractRadio;
import musician101.minetanks.spigot.tank.TankType;
import musician101.minetanks.spigot.tank.TankTypes;

import musician101.minetanks.spigot.util.HasIcon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Radio extends AbstractRadio implements HasIcon
{
    public Radio(String name, TankType type)
    {
        super(name);
        parseRadio(type);
    }

    @Override
    public ItemStack getIcon()
    {
        return (ItemStack) radio;
    }

    private void parseRadio(TankType type)
    {
        Material material = Material.AIR;
        if (type == TankTypes.LIGHT) material = Material.LEATHER_CHESTPLATE;
        else if (type == TankTypes.MEDIUM) material = Material.IRON_CHESTPLATE;
        else if (type == TankTypes.HEAVY) material = Material.DIAMOND_CHESTPLATE;
        else if (type == TankTypes.TD) material = Material.CHAINMAIL_CHESTPLATE;
        else if (type == TankTypes.ARTY) material = Material.GOLD_CHESTPLATE;

        radio = new ItemStack(material);
        ItemMeta meta = getIcon().getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Arrays.asList("Your Radio"));
        getIcon().setItemMeta(meta);
    }
}
