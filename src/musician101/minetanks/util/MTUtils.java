package musician101.minetanks.util;

import java.util.Arrays;

import musician101.minetanks.tankinfo.modules.Cannons;
import musician101.minetanks.tankinfo.modules.Engines;
import musician101.minetanks.tankinfo.modules.Radios;
import musician101.minetanks.tankinfo.modules.Tracks;
import musician101.minetanks.tankinfo.modules.Turrets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MTUtils
{
	public static double calculateArmorValue(double front, double side, double rear)
	{
		return (front + side + rear) / 3;
	}
	
	public static Inventory parseWeapons(Cannons cannon)
	{
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		inv.setItem(0, cannon.getCannon());
		inv.addItem(new ItemStack(Material.ARROW, cannon.getAmmoCount()));
		return inv;
	}
	
	public static ItemStack[] parseArmor(Engines engine, Radios radio, Tracks tracks, Turrets turret, double armor, int speed)
	{
		return new ItemStack[]{parseArmorValue(parseSpeedValue(engine.getEngine(), speed), armor),
				parseArmorValue(tracks.getTracks(), armor),
				parseArmorValue(radio.getRadio(), armor),
				parseArmorValue(turret.getHelmet(), (turret.getArmor() == 0 ? turret.getArmor() : armor))};
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
	
	public static ItemStack createCustomItem(Material material, String displayName)
	{
		return createCustomItem(material, displayName, "");
	}
	
	public static ItemStack createCustomItem(Material material, String displayName, String description)
	{
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§a" + displayName);
		if (!description.equals(""))
			meta.setLore(Arrays.asList(description));
		
		item.setItemMeta(meta);
		return item;
	}
}
