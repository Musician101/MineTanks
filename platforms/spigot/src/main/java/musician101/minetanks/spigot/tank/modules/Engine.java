package musician101.minetanks.spigot.tank.modules;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.modules.AbstractEngine;
import musician101.minetanks.spigot.tank.TankType;
import musician101.minetanks.spigot.tank.TankTypes;
import musician101.minetanks.spigot.util.HasIcon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Engine extends AbstractEngine implements HasIcon
{
    public Engine(String name, TankType type)
    {
        super(name);
        parseEngine(type);
    }

    @Override
    public ItemStack getIcon()
    {
        return (ItemStack) engine;
    }

    private void parseEngine(TankType type)
    {
        Material material = Material.AIR;
        if (type == TankTypes.LIGHT)
            material = Material.LEATHER_LEGGINGS;
        else if (type == TankTypes.MEDIUM)
            material = Material.IRON_LEGGINGS;
        else if (type == TankTypes.HEAVY)
            material = Material.DIAMOND_LEGGINGS;
        else if (type == TankTypes.TD)
            material = Material.CHAINMAIL_LEGGINGS;
        else if (type == TankTypes.ARTY)
            material = Material.GOLD_LEGGINGS;

        engine = new ItemStack(material);
        ItemMeta meta = getIcon().getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.ENGINE));
        getIcon().setItemMeta(meta);
    }
}
