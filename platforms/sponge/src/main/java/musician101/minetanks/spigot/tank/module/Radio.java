package musician101.minetanks.spigot.tank.module;

import java.util.Arrays;

import musician101.minetanks.spigot.tank.Tanks.TankTypes;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColors;

public class Radio
{
	String name;
	ItemStack radio;
	
	public Radio(String name, TankTypes type)
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
	
	@SuppressWarnings("serial")
	private void parseRadio(TankTypes type)
	{
		final ItemType iType;
		if (type == TankTypes.LIGHT)
			iType = ItemTypes.LEATHER_CHESTPLATE;
		else if (type == TankTypes.MEDIUM)
			iType = ItemTypes.IRON_CHESTPLATE;
		else if (type == TankTypes.HEAVY)
			iType = ItemTypes.DIAMOND_CHESTPLATE;
		else if (type == TankTypes.TD)
			iType = ItemTypes.CHAINMAIL_CHESTPLATE;
		else if (type == TankTypes.ARTY)
			iType = ItemTypes.GOLDEN_CHESTPLATE;
		
		radio = new ItemStack()
		{
			@Override
			public int compareTo(ItemStack o)
			{
				return 0;
			}

			@Override
			public ItemType getItem()
			{
				return iType;
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
		
		ItemMeta meta = radio.getItemMeta();
		meta.setDisplayName(TextColors.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setLore(Arrays.asList("Your Radio"));
		radio.setItemMeta(meta);
	}
}
