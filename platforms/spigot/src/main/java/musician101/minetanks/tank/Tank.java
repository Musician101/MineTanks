package musician101.minetanks.tank;

import musician101.minetanks.common.tank.AbstractTank;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.tank.modules.Engine;
import musician101.minetanks.tank.modules.Radio;
import musician101.minetanks.tank.modules.cannon.AutoLoader;
import musician101.minetanks.tank.modules.tracks.Trackz;
import musician101.minetanks.tank.modules.turret.Turret;
import musician101.minetanks.tank.modules.cannon.Cannon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Tank extends AbstractTank
{
    ItemStack[] wornArmor;
    Inventory weapons;

    public Tank(String name, TankType type, int health, Armor armor, int speed, Cannon cannon, Engine engine, Radio radio, Trackz tracks, Turret turret, String... description)
    {
        super(name, type, health, speed, cannon, engine, radio, tracks, turret, description);
        this.wornArmor = parseArmor(armor.getArmorValue(), speed, engine, radio, tracks, turret);
        this.weapons = parseWeapons(cannon);
    }

    private ItemStack[] parseArmor(double armor, int speed, Engine engine, Radio radio, Trackz tracks, Turret turret)
    {
        return new ItemStack[]{parseArmorValue(tracks.getIcon(), armor),
            parseArmorValue(parseSpeedValue(engine.getIcon(), speed), armor),
            parseArmorValue(radio.getIcon(), armor),
            parseArmorValue(turret.getIcon(), (turret.getArmor().getArmorValue() != 0 ? turret.getArmor().getArmorValue() : armor))};
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
        meta.setLore(Arrays.asList(meta.getLore().get(0), "Speed value: " + speed));
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
}