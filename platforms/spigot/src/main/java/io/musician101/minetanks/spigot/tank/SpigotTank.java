package io.musician101.minetanks.spigot.tank;

import io.musician101.minetanks.spigot.tank.modules.SpigotModules;
import io.musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import io.musician101.minetanks.spigot.util.MTUtils;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.AbstractTank;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.spigot.tank.modules.SpigotEngine;
import io.musician101.minetanks.spigot.tank.modules.SpigotRadio;
import io.musician101.minetanks.spigot.tank.modules.tracks.SpigotTrackz;
import io.musician101.minetanks.spigot.tank.modules.turret.SpigotTurret;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SpigotTank extends AbstractTank<SpigotCountry, SpigotTankType, SpigotModules, SpigotCannon, SpigotEngine, SpigotRadio, SpigotTrackz, SpigotTurret, ItemStack>
{
    SpigotTank(String name, SpigotCountry country, SpigotTankType type, int health, Armor armor, int speed, SpigotModules modules)
    {
        super(name, country, type, health, armor, speed, modules);
        setItem(MTUtils.createCustomItem(Material.MINECART, name, CommonItemText.tankType(type)));
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item)
    {
        return parseArmorValue(item, armor);
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item, Armor armor)
    {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, (int) Math.round(armor.getArmorValue()), true);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    protected ItemStack parseSpeedValue(ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(meta.getLore().get(0), CommonItemText.speedValue(getSpeed())));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack getHelmet()
    {
        return parseArmorValue(turret.getItem(), armor);
    }

    @Override
    public ItemStack getChestplate()
    {
        return parseArmorValue(radio.getItem());
    }

    @Override
    public ItemStack getLeggings()
    {
        return parseArmorValue(parseSpeedValue(engine.getItem()));
    }

    @Override
    public ItemStack getBoots()
    {
        return parseArmorValue(tracks.getItem());
    }

    @Override
    public List<ItemStack> getWeapons()
    {
        List<ItemStack> items = new ArrayList<>();
        items.add(cannon.getItem());
        ItemStack ammo = MTUtils.createCustomItem(Material.ARROW, CommonItemText.AMMO, "");
        ammo.setAmount(cannon.getAmmoCount());
        items.add(ammo);
        return items;
    }

    @Override
    public void applySpeedEffect(UUID uuid)
    {
        int amplifier;
        PotionEffectType effect;
        if (getSpeed() < 6)
        {
            effect = PotionEffectType.SLOW;
            amplifier = -getSpeed() + 6;
        }
        else
        {
            effect = PotionEffectType.SPEED;
            amplifier = getSpeed() - 5;
        }

        Bukkit.getPlayer(uuid).addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, amplifier, false));
    }
}
