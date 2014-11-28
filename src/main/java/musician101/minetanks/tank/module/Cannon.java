package musician101.minetanks.tank.module;

import java.util.Arrays;

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
	
	@SuppressWarnings("serial")
	private void parseCannon()
	{
		cannon = new ItemStack()
		{
			@Override
			public int compareTo(ItemStack o)
			{
				return 0;
			}

			@Override
			public ItemType getItem()
			{
				return ItemTypes.BOW;
			}

			@Override
			public short getDamage()
			{
				return 0;
			}

			@Override
			public void setDamage(short damage)
			{
				//NOOP
			}

			@Override
			public int getQuantity()
			{
				return 1;
			}

			@Override
			public void setQuantity(int quantity) throws IllegalArgumentException
			{
				//NOOP
			}

			@Override
			public int getMaxStackQuantity()
			{
				return 0;
			}

			@Override
			public void setMaxStackQuantity(int quantity)
			{
				//NOOP
			}
		};
		
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
