package musician101.minetanks.spigot.util;

import com.flowpowered.math.vector.Vector3d;
import musician101.minetanks.MineTanks;
import musician101.minetanks.spigot.tank.Tanks;
import musician101.minetanks.spigot.tank.module.Cannon;
import musician101.minetanks.spigot.tank.module.Engine;
import musician101.minetanks.spigot.tank.module.Radio;
import musician101.minetanks.spigot.tank.module.Tracks;
import musician101.minetanks.spigot.tank.module.Turrets;
import org.json.simple.JSONObject;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Arrays;
import java.util.List;

public class MTUtils
{
    public static double calculateArmorValue(double front, double side, double rear)
    {
        return (front + side + rear) / 3;
    }

    @SuppressWarnings("serial")
    public static ItemStack[] parseWeapons(final Cannon cannon)
    {
        ItemStack[] items = new ItemStack[2];
        items[0] = cannon.getCannon();
        items[1] = createCustomItem(ItemTypes.ARROW, 0, cannon.getAmmoCount());
        return items;
    }

    public static ItemStack[] parseArmor(Engine engine, Radio radio, Tracks tracks, Turrets turret, double armor, int speed)
    {
        return new ItemStack[]{parseArmorValue(tracks.getTracks(), armor), parseArmorValue(parseSpeedValue(engine.getEngine(), speed), armor), parseArmorValue(radio.getRadio(), armor), parseArmorValue(turret.getHelmet(), (turret.getArmor() != 0 ? turret.getArmor() : armor))};
    }

    private static ItemStack parseArmorValue(ItemStack item, double armor)
    {
        int a = 0;
        if (armor >= 1 && armor < 2)
            a = 1;
        else if (armor >= 2 && armor < 3)
            a = 2;
        else if (armor >= 3 && armor < 4)
            a = 3;
        else if (armor >= 4 && armor < 5)
            a = 4;
        else if (armor >= 5 && armor < 6)
            a = 5;
        else if (armor >= 6 && armor < 7)
            a = 6;
        else if (armor >= 7 && armor < 8)
            a = 7;
        else if (armor >= 8 && armor < 9)
            a = 8;
        else if (armor >= 9 && armor < 10)
            a = 9;
        else if (armor >= 10)
            a = 10;

        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, a, true);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack parseSpeedValue(ItemStack item, int speed)
    {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(meta.getLore().get(0), "Speed value: " + speed));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createCustomItem(ItemType type, String displayName)
    {
        return createCustomItem(type, displayName, null);
    }

    public static ItemStack createCustomItem(ItemType type, String displayName, List<String> description)
    {
        return createCustomItem(type, 0, 1, displayName, description);
    }

    public static ItemStack createCustomItem(ItemType type, int damage, int quantity)
    {
        return createCustomItem(type, damage, quantity, "", null);
    }

    @SuppressWarnings("serial")
    public static ItemStack createCustomItem(ItemType type, int damage, int quantity, String displayName, List<String> description)
    {
        ItemStackBuilder isb = MineTanks.getGame().getRegistry().getItemBuilder();
        isb.withItemType(type);
        isb.withDamage(damage);
        isb.withQuantity(quantity);

        //TODO item metadata has not been implemented
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00A7a" + displayName);
        if (!description.equals(""))
            meta.setLore(Arrays.asList(description));

        item.setItemMeta(meta);
        return isb.build();
    }

    public static int getMenuSize()
    {
        int rows = 0;
        while (rows * 9 < Tanks.values().length)
            rows++;

        return rows * 9;
    }

    public static Location deserializeLocation(JSONObject locJSON)
    {
        Vector3d position = new Vector3d(Integer.valueOf(locJSON.get("x").toString()), Integer.valueOf(locJSON.get("y").toString()), Integer.valueOf(locJSON.get("z").toString()));
        World world = MineTanks.getGame().getServer().get().getWorld(locJSON.get("world").toString()).get();
        return new Location(world, position);
    }

    public static Location addPosition(Location loc, double x, double y, double z)
    {
        Vector3d vec = loc.getPosition().add(x, y, z);
        loc.setPosition(vec);
        return loc;
    }

    public static Location subPosition(Location loc, double x, double y, double z)
    {
        Vector3d vec = loc.getPosition().sub(x, y, z);
        loc.setPosition(vec);
        return loc;
    }
}
