package io.musician101.minetanks.spigot.tank.modules;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractRadio;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import java.util.Collections;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpigotRadio extends AbstractRadio<ItemStack, SpigotTankType> {

    public SpigotRadio(String name, SpigotTankType type) {
        super(name);
        parseRadio(type);
    }

    @Override
    protected void parseRadio(SpigotTankType type) {
        ItemStack item = new ItemStack(type.getItem().getType());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Collections.singletonList(CommonItemText.RADIO));
        item.setItemMeta(meta);
        setItem(item);
    }
}
