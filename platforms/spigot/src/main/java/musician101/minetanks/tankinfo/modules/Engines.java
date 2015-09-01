package musician101.minetanks.tankinfo.modules;

import java.util.Arrays;

import musician101.minetanks.tankinfo.TankTypes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Engines
{	
	String name;
	ItemStack engine;
	
	public Engines(String name, TankTypes type)
	{
		this.name = name;
		parseEngine(type);
	}
	
	public String getName()
	{
		return name;
	}
	
	public ItemStack getEngine()
	{
		return engine;
	}
	
	private void parseEngine(TankTypes type)
	{
		Material material = Material.AIR;
		if (type == TankTypes.LIGHT)
			material = Material.LEATHER_LEGGINGS;
		else if (type == TankTypes.MEDIUM)
			material = Material.IRON_LEGGINGS;
		else if (type == TankTypes.HEAVY)
			material = Material.DIAMOND_LEGGINGS;
		else if (type == TankTypes.TD)
			material = Material.CHAINMAIL_LEGGINGS;
		else if (type == TankTypes.ARTY)
			material = Material.GOLD_LEGGINGS;
		
		engine = new ItemStack(material);
		ItemMeta meta = engine.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setLore(Arrays.asList("Your Engine"));
		engine.setItemMeta(meta);
	}
}
