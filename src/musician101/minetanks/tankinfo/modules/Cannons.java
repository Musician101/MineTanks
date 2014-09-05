package musician101.minetanks.tankinfo.modules;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Cannons
{
	String name;
	int ammoCount;
	double reloadTime;
	ItemStack cannon;
	
	public Cannons(String name, int ammoCount, double rateOfFire)
	{
		this.name = name;
		this.ammoCount = ammoCount;
		this.reloadTime = rateOfFire / 60;
		parseCannon();
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getAmmoCount()
	{
		return ammoCount;
	}
	
	public double reloadTime()
	{
		return reloadTime;
	}
	
	public ItemStack getCannon()
	{
		return cannon;
	}
	
	private void parseCannon()
	{
		cannon = new ItemStack(Material.BOW);
		ItemMeta meta = cannon.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		meta.setLore(Arrays.asList("Your Cannon", "Reload time: " + this.reloadTime));
		cannon.setItemMeta(meta);
	}
}
