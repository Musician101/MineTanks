package musician101.minetanks.util;

import musician101.minetanks.MineTanks;
import musician101.minetanks.handler.TankSelectionHandler;
import musician101.minetanks.tank.Tanks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menus
{
	MineTanks plugin;
	IconMenu tankSelection;
	
	public Menus(MineTanks plugin)
	{
		this.plugin = plugin;
		this.tankSelection = new IconMenu("Tank Selection", MTUtils.getMenuSize(), new TankSelectionHandler(plugin), plugin);
		int nextId = 0;
		for (Tanks tank : Tanks.values())
		{
			nextId++;
			tankSelection.setOption(nextId, new ItemStack(Material.MINECART, 1), "§a" + tank.getName(), tank.getDescription());
		}
	}
	
	public void openTankMenu(Player player)
	{
		tankSelection.open(player);
	}
}
