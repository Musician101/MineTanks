package musician101.minetanks.menu;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.BattleField;
import musician101.minetanks.battlefield.PlayerTank;
import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.TankTypes;
import musician101.minetanks.tankinfo.tanks.ChinaLight;
import musician101.minetanks.tankinfo.tanks.ChinaMedium;
import musician101.minetanks.util.IconMenu.OptionClickEvent;
import musician101.minetanks.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.util.MTUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MenuHandlers
{
	public static class CountrySelectionHandler implements OptionClickEventHandler
	{
		public CountrySelectionHandler()
		{
			super();
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			int slot = event.getPosition();
			event.setWillClose(false);
			for (Countries country : Countries.values())
			{
				//null check is temporary for countries not implemented
				if (slot == country.getId() && country.getMenu() != null)
				{
					country.getMenu().open(event.getPlayer());
					return;
				}
			}
			
			event.getPlayer().sendMessage("I'm sorry but this country hasn't been implemented yet.");
		}
	}
	
	public static class ChinaTypeSelectionHandler implements OptionClickEventHandler
	{
		public ChinaTypeSelectionHandler()
		{
			super();
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			event.setWillClose(false);
			int slot = event.getPosition();
			for (TankTypes type : TankTypes.values())
			{
				//null check is not necessarily needed bet will be kept for now.
				if (slot == type.getId() && type.getMenu(Countries.CHINA) != null)
				{
					type.getMenu(Countries.CHINA).open(event.getPlayer());
					return;
				}
			}
		}
	}
	
	public static class ChinaLightSelectionHandler implements OptionClickEventHandler
	{
		MineTanks plugin;
		
		public ChinaLightSelectionHandler(MineTanks plugin)
		{
			this.plugin = plugin;
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			Player player = event.getPlayer();
			int slot = event.getPosition();
			for (ChinaLight tank : ChinaLight.values())
			{
				if (slot == tank.getId())
				{
					for (BattleField field : plugin.fieldStorage.getFields())
					{
						PlayerTank pt = field.getPlayer(event.getPlayer().getUniqueId());
						if (pt != null)
						{
							pt.setTank(tank);
							player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getIcon().getType(), "Open Hangar.", "Tank: " + tank.getName()));
							player.sendMessage(ChatColor.GREEN + plugin.prefix + " You have chosen the " + tank.getName() + ".");
							player.sendMessage(ChatColor.GREEN + plugin.prefix + " If you wish to choose another tank, right click with the 'Open Hangar' item.");
							player.sendMessage(ChatColor.GREEN + plugin.prefix + " When you are ready, simply right click the 'Ready' item.");
							return;
						}
					}
				}
			}
			
			player.sendMessage("I'm sorry, but this tank has not been implemented yet.");
			event.setWillClose(false);
		}
	}

	public static class ChinaMediumSelectionHandler implements OptionClickEventHandler
	{
		MineTanks plugin;
		
		public ChinaMediumSelectionHandler(MineTanks plugin)
		{
			this.plugin = plugin;
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			Player player = event.getPlayer();
			int slot = event.getPosition();
			for (ChinaMedium tank : ChinaMedium.values())
			{
				if (slot == tank.getId())
				{
					for (BattleField field : plugin.fieldStorage.getFields())
					{
						PlayerTank pt = field.getPlayer(event.getPlayer().getUniqueId());
						if (pt != null)
						{
							pt.setTank(tank);
							player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getIcon().getType(), "Open Hangar.", "Tank: " + tank.getName()));
							player.sendMessage(ChatColor.GREEN + plugin.prefix + " You have chosen the " + tank.getName() + ".");
							player.sendMessage(ChatColor.GREEN + plugin.prefix + " If you wish to choose another tank, right click with the 'Open Hangar' item.");
							player.sendMessage(ChatColor.GREEN + plugin.prefix + " When you are ready, simply right click the 'Ready' item.");
							return;
						}
					}
				}
			}
			
			player.sendMessage("I'm sorry, but this tank has not been implemented yet.");
			event.setWillClose(false);
		}
	}
}
