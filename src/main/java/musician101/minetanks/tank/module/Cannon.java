package musician101.minetanks.tank.module;

import java.util.Arrays;

import musician101.minetanks.util.MTUtils;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColors;

public class Cannon
{
	String name;
	int ammoCount, clipSize;
	double cycleTime, reloadTime;
	ItemStack cannon;
	CannonTypes type;
	
	public Cannon(String name, int ammoCount, double reloadTime)
	{
		this(name, CannonTypes.SINGLE_SHOT, ammoCount, reloadTime, reloadTime, 1);
	}
	
	public Cannon(String name, CannonTypes type, int ammoCount, double reloadTime, double cycleTime, int clipSize)
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
	
	public Double reloadTime()
	{
		return reloadTime;
	}
	
	public Double cycleTime()
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
		//TODO modules will not be fully updated until MetaData API is released
		cannon = MTUtils.createCustomItem(ItemTypes.BOW, TextColors.GREEN + name);
		ItemMeta meta = cannon.getItemMeta();
		meta.setDisplayName(TextColors.GREEN + name);
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
