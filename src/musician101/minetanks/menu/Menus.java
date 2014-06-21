package musician101.minetanks.menu;

import musician101.minetanks.MineTanks;
import musician101.minetanks.menu.MenuHandlers.ChinaLightSelectionHandler;
import musician101.minetanks.menu.MenuHandlers.ChinaMediumSelectionHandler;
import musician101.minetanks.menu.MenuHandlers.ChinaTypeSelectionHandler;
import musician101.minetanks.menu.MenuHandlers.CountrySelectionHandler;
import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.TankTypes;
import musician101.minetanks.tankinfo.tanks.ChinaLight;
import musician101.minetanks.tankinfo.tanks.ChinaMedium;
import musician101.minetanks.util.IconMenu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Menus
{
	public static IconMenu countrySelection;
	public static IconMenu chineseTypeSelection;
	public static IconMenu chineseLightSelection;
	public static IconMenu chineseMediumSelection;
	public static IconMenu chineseHeavySelection;
	
	public static void init(final MineTanks plugin)
	{
		countrySelection = new IconMenu("Country Selection", 9, new CountrySelectionHandler(), plugin);
		
		for (Countries country : Countries.values())
			countrySelection.setOption(country.getId(), country.getIcon(), "§a" + country.getName(), country.getDescription());
		
		chineseTypeSelection = new IconMenu("Chinese Type Selection", 9, new ChinaTypeSelectionHandler(), plugin);
		
		for (TankTypes type : Countries.CHINA.getTypes())
			chineseTypeSelection.setOption(type.getId(), type.getIcon(), "§a" + type.getMenuName(Countries.CHINA), type.getDescription(Countries.CHINA));
		
		chineseLightSelection = new IconMenu("Chinese Light Tank Selection", 9, new ChinaLightSelectionHandler(plugin), plugin);
		
		for (ChinaLight tank : ChinaLight.values())
			chineseLightSelection.setOption(tank.getId(), new ItemStack(Material.WOOD_SWORD, 1), "§a" + tank.getName());
		
		chineseMediumSelection = new IconMenu("Chinese Medium Tank Selection", 9, new ChinaMediumSelectionHandler(plugin), plugin);
		
		for (ChinaMedium tank : ChinaMedium.values())
			chineseMediumSelection.setOption(tank.getId(), new ItemStack(Material.STONE_SWORD, 1), "§a" + tank.getName());
	}
}
