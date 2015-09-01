package musician101.minetanks.tankinfo.modules;

import java.util.Arrays;

import musician101.minetanks.tankinfo.TankTypes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Radios
{
	String name;
	ItemStack radio;
	
	public Radios(String name, TankTypes type)
	{
		this.name = name;
		parseRadio(type);
	}
	
	public String getName()
	{
		return name;
	}
	
	public ItemStack getRadio()
	{
		return radio;
	}
	
	private void parseRadio(TankTypes type)
	{
		Material material = Material.AIR;
		if (type == TankTypes.LIGHT)
			material = Material.LEATHER_CHESTPLATE;
		else if (type == TankTypes.MEDIUM)
			material = Material.IRON_CHESTPLATE;
		else if (type == TankTypes.HEAVY)
			material = Material.DIAMOND_CHESTPLATE;
		else if (type == TankTypes.TD)
			material = Material.CHAINMAIL_CHESTPLATE;
		else if (type == TankTypes.ARTY)
			material = Material.GOLD_CHESTPLATE;
		
		radio = new ItemStack(material);
		ItemMeta meta = radio.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setLore(Arrays.asList("Your Radio"));
		radio.setItemMeta(meta);
	}
}
