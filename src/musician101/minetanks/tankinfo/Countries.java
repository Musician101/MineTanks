package musician101.minetanks.tankinfo;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Countries implements IInfo
{
	CHINA(0, "China", Arrays.asList(TankTypes.LIGHT, TankTypes.MEDIUM, TankTypes.HEAVY), new String[]{"Unparalleled mobility.", "Poor armor."}, new ItemStack(Material.WOOL, 1, (short) 14)),
	FRANCE(1, "France", new String[]{"Lower tiers are slow, have poor cannons, and great armor.", "Higher tiers are fast, have great cannons and poor armor."}, new ItemStack(Material.LAPIS_BLOCK, 1)),
	GERMANY(2, "Germany", new String[]{"High armor.", "Slow.", "Low damage."}, new ItemStack(Material.WOOL, 1, (short) 15)),
	//Japan is missing information on all of the wikis I'm able to find
	JAPAN(3, "Japan", Arrays.asList(TankTypes.LIGHT, TankTypes.MEDIUM), new String[]{}, new ItemStack(Material.STAINED_GLASS, 1, (short) 15)),
	UK(4, "U.K.", new String[]{"Lower tiers are situational tanks.", "Higher tiers are all purpose tanks."}, new ItemStack(Material.WOOL, 1)),
	USA(5, "U.S.A.", new String[]{"Great firepower.", "Best rate of fire.", "Often have the highest damage per minute."}, new ItemStack(Material.STAINED_GLASS, 1, (short) 11)),
	USSR(6, "U.S.S.R.", new String[]{"Low armor.", "Good speed.", "Cannons pack a punch."}, new ItemStack(Material.STAINED_CLAY, 1, (short) 14));
	
	int id;
	ItemStack icon;
	String name;
	List<TankTypes> types;
	String[] desc;
	
	private Countries(int id, String name, String[] desc, ItemStack icon)
	{
		this(id, name, Arrays.asList(TankTypes.LIGHT, TankTypes.MEDIUM, TankTypes.HEAVY, TankTypes.TD, TankTypes.ARTY), desc, icon);
	}
	
	private Countries(int id, String name, List<TankTypes> types, String[] desc, ItemStack icon)
	{
		this.id = id;
		this.name = name;
		this.types = types;
		this.desc = desc;
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
	
	public List<TankTypes> getTypes()
	{
		return types;
	}
	
	public String[] getDescription()
	{
		return desc;
	}
	
	public ItemStack getIcon()
	{
		return icon;
	}
}
