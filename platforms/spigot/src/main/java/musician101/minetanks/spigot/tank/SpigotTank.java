package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.AbstractTank;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.spigot.tank.modules.SpigotEngine;
import musician101.minetanks.spigot.tank.modules.SpigotRadio;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import musician101.minetanks.spigot.tank.modules.tracks.SpigotTrackz;
import musician101.minetanks.spigot.tank.modules.turret.SpigotTurret;
import musician101.minetanks.spigot.util.MTUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpigotTank extends AbstractTank<SpigotTankType, SpigotCannon, SpigotEngine, SpigotRadio, SpigotTrackz, SpigotTurret, ItemStack, PotionEffect>
{
    public SpigotTank(String name, SpigotTankType type, int health, Armor armor, int speed, SpigotCannon cannon, SpigotEngine engine, SpigotRadio radio, SpigotTrackz tracks, SpigotTurret turret)
    {
        super(name, type, health, armor, speed, cannon, engine, radio, tracks, turret);
    }

    private ItemStack parseArmorValue(ItemStack item)
    {
        return parseArmorValue(item, armor);
    }

    private ItemStack parseArmorValue(ItemStack item, Armor armor)
    {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, (int) Math.round(armor.getArmorValue()), true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack parseSpeedValue(ItemStack item)
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

    public PotionEffect getSpeedEffect()
    {
        int amplifier = 0;
        PotionEffectType effect;
        if (getSpeed() < 6)
        {
            effect = PotionEffectType.SLOW;
            if (getSpeed() == 1)
                amplifier = 5;
            else if (getSpeed() == 2)
                amplifier = 4;
            else if (getSpeed() == 3)
                amplifier = 3;
            else if (getSpeed() == 4)
                amplifier = 2;
            else if (getSpeed() == 5)
                amplifier = 1;
        }
        else
        {
            effect = PotionEffectType.SPEED;
            if (getSpeed() == 6)
                amplifier = 1;
            else if (getSpeed() == 7)
                amplifier = 2;
            else if (getSpeed() == 8)
                amplifier = 3;
            else if (getSpeed() == 9)
                amplifier = 4;
            else if (getSpeed() == 10)
                amplifier = 5;
        }

        return new PotionEffect(effect, amplifier, Integer.MAX_VALUE, false);
    }
}
