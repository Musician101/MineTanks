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
	int ammoCount, clipSize;
	double cycleTime, reloadTime;
	ItemStack cannon;
	CannonTypes type;
	
	public Cannons(String name, int ammoCount, double reloadTime)
	{
		this(name, CannonTypes.SINGLE_SHOT, ammoCount, reloadTime, reloadTime, 1);
	}
	
	public Cannons(String name, CannonTypes type, int ammoCount, double reloadTime, double cycleTime, int clipSize)
	{
		this.name = name;
		this.type = type;
		this.reloadTime = reloadTime;
		this.ammoCount = ammoCount;
		this.cycleTime = cycleTime;
		this.clipSize = clipSize;
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
	
	public double cycleTime()
	{
		return cycleTime;
	}
	
	public ItemStack getCannon()
	{
		return cannon;
	}
	
	public int getClipSize()
	{
		return clipSize;
	}
	
	public CannonTypes getType()
	{
		return type;
	}
	
	private void parseCannon()
	{
		cannon = new ItemStack(Material.BOW);
		ItemMeta meta = cannon.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		if (type == CannonTypes.SINGLE_SHOT)
			meta.setLore(Arrays.asList("Your Cannon", "Reload Time: " + reloadTime));
		else
			meta.setLore(Arrays.asList("Your Cannon", "Clip Size: " + clipSize + "/" + clipSize,"Cycle Time: " + cycleTime, "Clip Reload Time: " + reloadTime));
		
		cannon.setItemMeta(meta);
	}
	
	public enum CannonTypes
	{
		AUTO_LOADER,
		SINGLE_SHOT;
	}
}
