package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.AbstractTank;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.tank.modules.Engine;
import musician101.minetanks.spigot.tank.modules.Radio;
import musician101.minetanks.spigot.tank.modules.cannon.Cannon;
import musician101.minetanks.spigot.tank.modules.tracks.Trackz;
import musician101.minetanks.spigot.tank.modules.turret.Turret;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Tank extends AbstractTank
{
    private final ItemStack[] wornArmor;
    private final Inventory weapons;

    public Tank(String name, TankType type, int health, Armor armor, int speed, Cannon cannon, Engine engine, Radio radio, Trackz tracks, Turret turret, String... description)
    {
        super(name, type, health, speed, cannon, engine, radio, tracks, turret, description);
        this.wornArmor = parseArmor(armor.getArmorValue(), speed, engine, radio, tracks, turret);
        this.weapons = parseWeapons(cannon);
    }

    private ItemStack[] parseArmor(double armor, int speed, Engine engine, Radio radio, Trackz tracks, Turret turret)
    {
        return new ItemStack[]{parseArmorValue(tracks.getIcon(), armor), parseArmorValue(parseSpeedValue(engine.getIcon(), speed), armor), parseArmorValue(radio.getIcon(), armor), parseArmorValue(turret.getIcon(), (turret.getArmor().getArmorValue() != 0 ? turret.getArmor().getArmorValue() : armor))};
    }

    private ItemStack parseArmorValue(ItemStack item, double armor)
    {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, (int) Math.round(armor), true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack parseSpeedValue(ItemStack item, int speed)
    {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(meta.getLore().get(0), SpigotReference.number(CommonItemText.SPEED_VALUE, speed)));
        item.setItemMeta(meta);
        return item;
    }

    private Inventory parseWeapons(Cannon cannon)
    {
        Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
        inv.setItem(0, cannon.getIcon());
        inv.addItem(new ItemStack(Material.ARROW, cannon.getAmmoCount()));
        return inv;
    }

    public Cannon getCannon()
    {
        return (Cannon) cannon;
    }

    public ItemStack[] getArmor()
    {
        return wornArmor;
    }

    public Inventory getWeapons()
    {
        return weapons;
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

    public TankType getType()
    {
        return (TankType) type;
    }
}
