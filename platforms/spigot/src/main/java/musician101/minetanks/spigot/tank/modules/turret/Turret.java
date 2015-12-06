package musician101.minetanks.spigot.tank.modules.turret;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.common.tank.modules.AbstractTurret;
import musician101.minetanks.spigot.tank.TankType;
import musician101.minetanks.spigot.tank.TankTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Turret extends AbstractTurret<ItemStack>
{
    public Turret(String name, TankType type, Armor armor)
    {
        super(name, armor);
        parseHelmet(type);
    }

    private void parseHelmet(TankType type)
    {
        Material material = Material.AIR;
        if (type == TankTypes.LIGHT)
            material = Material.LEATHER_HELMET;
        else if (type == TankTypes.MEDIUM)
            material = Material.IRON_HELMET;
        else if (type == TankTypes.HEAVY)
            material = Material.DIAMOND_HELMET;
        else if (type == TankTypes.TD)
            material = Material.CHAINMAIL_HELMET;
        else if (type == TankTypes.ARTY)
            material = Material.GOLD_HELMET;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.TURRET));
        item.setItemMeta(meta);
        setItem(item);
    }
}
