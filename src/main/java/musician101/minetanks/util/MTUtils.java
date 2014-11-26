package musician101.minetanks.util;

import java.util.Arrays;

import musician101.minetanks.tank.Tanks;
import musician101.minetanks.tank.module.Cannon;
import musician101.minetanks.tank.module.Engine;
import musician101.minetanks.tank.module.Radio;
import musician101.minetanks.tank.module.Tracks;
import musician101.minetanks.tank.module.Turrets;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.math.Vector3d;
import org.spongepowered.api.world.Location;

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
		items[1] = new ItemStack()
		{
			@Override
			public int compareTo(ItemStack arg0)
			{
				return 0;
			}

			@Override
			public ItemType getItem()
			{
				return ItemTypes.ARROW;
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
				return cannon.getAmmoCount();
			}

			@Override
			public void setQuantity(int quantity) throws IllegalArgumentException
			{
				
			}

			@Override
			public int getMaxStackQuantity()
			{
				return cannon.getAmmoCount();
			}

			@Override
			public void setMaxStackQuantity(int quantity)
			{
				//NOOP
			}
		};
		return items;
	}
	
	public static ItemStack[] parseArmor(Engine engine, Radio radio, Tracks tracks, Turrets turret, double armor, int speed)
	{
		return new ItemStack[]{parseArmorValue(tracks.getTracks(), armor),
				parseArmorValue(parseSpeedValue(engine.getEngine(), speed), armor),
				parseArmorValue(radio.getRadio(), armor),
				parseArmorValue(turret.getHelmet(), (turret.getArmor() != 0 ? turret.getArmor() : armor))};
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
		return createCustomItem(type, displayName, "");
	}
	
	@SuppressWarnings("serial")
	public static ItemStack createCustomItem(final ItemType type, String displayName, String description)
	{
		//TODO item metadata has not been implemented
		ItemStack item = new ItemStack()
		{

			@Override
			public int compareTo(ItemStack o)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public ItemType getItem()
			{
				return type;
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
				return getItem().getMaxStackQuantity();
			}

			@Override
			public void setMaxStackQuantity(int quantity)
			{
				//NOOP
			}
		};
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00A7a" + displayName);
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
