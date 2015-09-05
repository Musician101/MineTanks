package musician101.minetanks.util;

import java.util.Arrays;

import musician101.minetanks.tank.Tanks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MTUtils
{
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
	
	public static int getMenuSize()
	{
		int rows = 0;
		while (rows * 9 < Tanks.values().length)
			rows++;
		
		return rows * 9;
	}
}
