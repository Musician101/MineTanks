package musician101.minetanks.util;

import musician101.minetanks.MineTanks;
import musician101.minetanks.handler.TankSelectionHandler;
import musician101.minetanks.tank.Tanks;

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
			tankSelection.setOption(nextId, new ItemStack(ItemTypesw.MINECART, 1), "§a" + tank.getName(), tank.getDescription());
		}
	}
	
	public void openTankMenu(Player player)
	{
		tankSelection.open(player);
	}
}
