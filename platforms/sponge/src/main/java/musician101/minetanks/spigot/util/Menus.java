package musician101.minetanks.spigot.util;

import musician101.minetanks.MineTanks;
import musician101.minetanks.handler.TankSelectionHandler;
import musician101.minetanks.spigot.tank.Tanks;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.ItemTypes;

public class Menus
{
	MineTanks plugin;
	IconMenu tankSelection;
	
	public Menus(MineTanks plugin)
	{
		this.plugin = plugin;
		this.tankSelection = new IconMenu("Tank Selection", MTUtils.getMenuSize(), new TankSelectionHandler());
		int nextId = 0;
		for (Tanks tank : Tanks.values())
		{
			nextId++;
			//TODO ItemStack is an interface
			tankSelection.setOption(nextId, ItemTypes.MINECART, "\u00A7a" + tank.getName(), tank.getDescription());
		}
	}
	
	public void openTankMenu(Player player)
	{
		tankSelection.open(player);
	}
}
