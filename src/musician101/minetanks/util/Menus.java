package musician101.minetanks.util;

import musician101.minetanks.MineTanks;
import musician101.minetanks.handlers.MenuHandlers.TankSelectionHandler;
import musician101.minetanks.tankinfo.Tanks;

import org.bukkit.entity.Player;

public class Menus
{
	static MineTanks plugin;
	IconMenu tankSelection;
	
	public Menus(MineTanks plugin)
	{
		Menus.plugin = plugin;
		this.tankSelection = new IconMenu("Country Selection", 9, new TankSelectionHandler(plugin), plugin);
		for (Tanks tank : Tanks.values())
			tankSelection.setOption(tank.getId(), tank.getType().getIcon(), "§a" + tank.getName(), tank.getDescription());
	}
	
	public void openCountryMenu(Player player)
	{
		tankSelection.open(player);
	}
}
