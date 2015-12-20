package musician101.minetanks.spigot.tank.modules;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.modules.AbstractRadio;
import musician101.minetanks.spigot.tank.SpigotTankType;
import musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotRadio extends AbstractRadio<ItemStack>
{
    public SpigotRadio(String name, SpigotTankType type)
    {
        super(name);
        parseRadio(type);
    }

    private void parseRadio(SpigotTankType type)
    {
        Material material = Material.AIR;
        if (type == SpigotTankTypes.LIGHT)
            material = Material.LEATHER_CHESTPLATE;
        else if (type == SpigotTankTypes.MEDIUM)
            material = Material.IRON_CHESTPLATE;
        else if (type == SpigotTankTypes.HEAVY)
            material = Material.DIAMOND_CHESTPLATE;
        else if (type == SpigotTankTypes.TD)
            material = Material.CHAINMAIL_CHESTPLATE;
        else if (type == SpigotTankTypes.ARTY)
            material = Material.GOLD_CHESTPLATE;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.RADIO));
        item.setItemMeta(meta);
        setItem(item);
    }
}
