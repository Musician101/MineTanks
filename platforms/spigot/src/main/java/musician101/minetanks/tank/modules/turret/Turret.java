package musician101.minetanks.tank.modules.turret;

import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.common.tank.modules.AbstractTurret;
import musician101.minetanks.tank.TankType;
import musician101.minetanks.tank.TankTypes;
import musician101.minetanks.util.HasIcon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Turret extends AbstractTurret implements HasIcon
{
    public Turret(String name, TankType type, Armor armor)
    {
        super(name, armor);
        parseHelmet(type);
    }

    @Override
    public ItemStack getIcon()
    {
        return (ItemStack) turret;
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

        turret = new ItemStack(material);
        ItemMeta meta = getIcon().getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Arrays.asList("Your Turret"));
        getIcon().setItemMeta(meta);
    }
}
