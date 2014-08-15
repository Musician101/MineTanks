package musician101.minetanks.util;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.handlers.MenuHandlers.CountrySelectionHandler;
import musician101.minetanks.handlers.MenuHandlers.TankSelectionHandler;
import musician101.minetanks.handlers.MenuHandlers.TankTypeSelectionHandler;
import musician101.minetanks.handlers.MenuHandlers.TechTreeSelectionHandler;
import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.TankTypes;
import musician101.minetanks.tankinfo.tanks.ChinaHeavy;
import musician101.minetanks.tankinfo.tanks.ChinaLight;
import musician101.minetanks.tankinfo.tanks.ChinaMedium;
import musician101.minetanks.tankinfo.tanks.ITank;

import org.bukkit.entity.Player;

public class Menus
{
	static MineTanks plugin;
	IconMenu countrySelection;
	public static IconMenu chineseTypeSelection;
	static IconMenu chineseLightSelection;
	static IconMenu chineseMediumSelection;
	static IconMenu chineseHeavySelection;
	
	public Menus(MineTanks plugin)
	{
		Menus.plugin = plugin;
		this.countrySelection = new IconMenu("Country Selection", 9, new CountrySelectionHandler(plugin), plugin);
		for (Countries country : Countries.values())
			countrySelection.setOption(country.getId(), country.getIcon(), "§a" + country.getName(), country.getDescription());
		
		chineseTypeSelection = new IconMenu("Chinese Type Selection", 9, new TankTypeSelectionHandler(plugin), plugin);
		for (TankTypes type : Countries.CHINA.getTypes())
			chineseTypeSelection.setOption(type.getId(), type.getIcon(), "§a" + type.getMenuName(Countries.CHINA), type.getDescription(Countries.CHINA));
		
		chineseLightSelection = new IconMenu("Chinese Light Selection", 9, new TankSelectionHandler(plugin), plugin);
		chineseMediumSelection = new IconMenu("Chinese Medium Selection", 9, new TankSelectionHandler(plugin), plugin);
		chineseHeavySelection = new IconMenu("Chinese Medium Selection", 9, new TankSelectionHandler(plugin), plugin);
	}
	
	public void openCountryMenu(Player player)
	{
		countrySelection.open(player);
	}
	
	public void openTypeMenu(Player player, Countries country)
	{
		if (country == Countries.CHINA)
			chineseTypeSelection.open(player);
	}
	
	public static IconMenu openTankMenu(Player player, Countries country, TankTypes type, List<ITank> tanks)
	{
		if (country == Countries.CHINA)
		{
			if (type == TankTypes.LIGHT)
			{
				for (ITank tank : ChinaLight.values())
				{
					for (ITank t : tanks)
					{
						String color = "a";
						if (!tank.getName().equals(t.getName()))
							color = "c";
						
						chineseLightSelection.setOption(tank.getId(), tank.getType().getIcon(), "§" + color + tank.getName());
					}
				}
			}
			else if (type == TankTypes.MEDIUM)
			{
				for (ITank tank : ChinaMedium.values())
				{
					for (ITank t : tanks)
					{
						String color = "a";
						if (!tank.getName().equals(t.getName()))
							color = "c";
						
						chineseMediumSelection.setOption(tank.getId(), tank.getType().getIcon(), "§" + color + tank.getName());
					}
				}
			}
			else if (type == TankTypes.HEAVY)
			{
				for (ITank tank : ChinaHeavy.values())
				{
					for (ITank t : tanks)
					{
						String color = "a";
						if (!tank.getName().equals(t.getName()))
							color = "c";
						
						chineseHeavySelection.setOption(tank.getId(), tank.getType().getIcon(), "§" + color + tank.getName());
					}
				}
			}
		}
		
		return null;
	}
	
	public static IconMenu openTechTree(Player player, Countries country, TankTypes type)
	{
		
		ITank[] tanks = null;
		if (country == Countries.CHINA)
		{
			if (type == TankTypes.LIGHT)
				tanks = ChinaLight.values();
			else if (type == TankTypes.MEDIUM)
				tanks = ChinaMedium.values();
			else if (type == TankTypes.HEAVY)
				tanks = ChinaHeavy.values();
		}
		
		int size = 9;
		if (tanks.length > 9)
			size = 18;
		else if (tanks.length > 18)
			size = 27;
		else if (tanks.length > 27)
			size = 36;
		else if (tanks.length > 36)
			size = 45;
		else if (tanks.length > 45)
			size = 54;
		
		IconMenu menu = new IconMenu("Tech Tree", size, new TechTreeSelectionHandler(plugin), plugin);
		for (ITank tank : tanks)
		{
			if (tank.getId() <= 54)
			{
				String color = "a";
				if (!plugin.getStatStorage().getPlayer(player.getUniqueId()).getTanks().contains(tank))
					color = "r";
				
				menu.setOption(tank.getId(), tank.getType().getIcon(), "§" + color + tank.getName());
			}
		}
		
		return menu;
	}
}
