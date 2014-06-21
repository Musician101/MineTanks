package musician101.minetanks.tankinfo;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TankTypes implements IInfo
{
	LIGHT(0, "Light", new ItemStack(Material.WOOD_SWORD, 1)),
	MEDIUM(1, "Medium", new ItemStack(Material.STONE_SWORD, 1)),
	HEAVY(2, "Heavy", new ItemStack(Material.IRON_SWORD, 1)),
	TD(3, "TD", new ItemStack(Material.GOLD_SWORD, 1)),
	ARTY(4, "SPG", new ItemStack(Material.DIAMOND_SWORD, 1));
	
	int id;
	String name;
	ItemStack icon;
	
	private TankTypes(int id, String name, ItemStack icon)
	{
		this.id = id;
		this.name = name;
		this.icon = icon;
	}
	
	@Override
	public int getId()
	{
		return id;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public int getMenuSize()
	{
		int rows = 0;
		while (rows * 9 < values().length)
			rows++;
		
		return rows * 9;
	}
	
	public ItemStack getIcon()
	{
		return icon;
	}
	
	public String getMenuName(Countries country)
	{
		String c = "";
		if (country == Countries.CHINA)
			c = "Chinese ";
		
		return c + getName() + " Tanks";
	}
	
	public String[] getDescription(Countries country)
	{
		if (country == Countries.CHINA)
		{
			if (this == LIGHT)
				return new String[]{"Fast and agile.", "Lightly armored."};
			else if (this == MEDIUM)
				return new String[]{"Low tiers are mobile and have high damage per shot.", "Low tiers have a poor rate of fire.", "High tiers gain more firepower."};
			else if (this == HEAVY)
				return new String[]{"High armor and rate of fire in early tiers.", "Later tiers focus more on maneuverability."};
		}
		
		return new String[]{};
	}
}
