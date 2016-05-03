package io.musician101.minetanks.spigot.tank.modules.turret;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.common.tank.modules.AbstractTurret;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpigotTurret extends AbstractTurret<ItemStack, SpigotTankType>
{
    SpigotTurret(String name, SpigotTankType type, Armor armor)
    {
        super(name, armor);
        parseTurret(type);
    }

    @Override
    protected void parseTurret(SpigotTankType type)
    {
        Material tankMaterial = Material.AIR;
        for (SpigotTankType stt : SpigotTankTypes.getValues())
            if (stt.getName().equals(type.getName()))
                tankMaterial = stt.getItem().getType();

        Material material = Material.AIR;
        if (tankMaterial == Material.WOOD_SWORD)
            material = Material.LEATHER_HELMET;
        else if (tankMaterial == Material.STONE_SWORD)
            material = Material.IRON_HELMET;
        else if (tankMaterial == Material.IRON_SWORD)
            material = Material.DIAMOND_HELMET;
        else if (tankMaterial == Material.GOLD_SWORD)
            material = Material.CHAINMAIL_HELMET;
        else if (tankMaterial == Material.DIAMOND_SWORD)
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
