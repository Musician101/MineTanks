package musician101.minetanks.handlers;

import musician101.minetanks.MineTanks;
import musician101.minetanks.stats.PlayerStats;
import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.TankTypes;
import musician101.minetanks.tankinfo.tanks.ChinaHeavy;
import musician101.minetanks.tankinfo.tanks.ChinaLight;
import musician101.minetanks.tankinfo.tanks.ChinaMedium;
import musician101.minetanks.tankinfo.tanks.ITank;
import musician101.minetanks.util.IconMenu.OptionClickEvent;
import musician101.minetanks.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.util.MTUtils;
import musician101.minetanks.util.Menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MenuHandlers
{	
	public static class CountrySelectionHandler implements OptionClickEventHandler
	{
		MineTanks plugin;
		
		public CountrySelectionHandler(MineTanks plugin)
		{
			this.plugin = plugin;
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			int slot = event.getPosition();
			Player player = event.getPlayer();
			event.setWillClose(false);
			for (Countries country : Countries.values())
			{
				//null check is temporary for countries not implemented
				if (slot == country.getId() && country.getMenu() != null)
				{
					country.getMenu().open(player);
					if (plugin.getNavigation().containsKey(player.getUniqueId()))
						plugin.getNavigation().remove(player.getUniqueId());
					
					plugin.getNavigation().put(player.getUniqueId(), new NavigationHandler(country));
					return;
				}
			}
			//Temp message until all countries have been implemented
			event.getPlayer().sendMessage("I'm sorry but this country hasn't been implemented yet.");
		}
	}
	
	public static class TankTypeSelectionHandler implements OptionClickEventHandler
	{
		MineTanks plugin;
		
		public TankTypeSelectionHandler(MineTanks plugin)
		{
			this.plugin = plugin;
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			event.setWillClose(false);
			int slot = event.getPosition();
			Player player = event.getPlayer();
			NavigationHandler nh = plugin.getNavigation().get(player.getUniqueId());
			for (TankTypes type : nh.getCountry().getTypes())
			{
				if (slot == type.getId())
				{
					type.getMenu(player, nh.getCountry(), plugin.getStatStorage().getStats().get(player.getUniqueId()).getTanks()).open(player);
					plugin.getNavigation().get(player.getUniqueId()).setType(type);
					return;
				}
			}
		}
	}
	
	public static class TankSelectionHandler implements OptionClickEventHandler
	{
		MineTanks plugin;
		
		public TankSelectionHandler(MineTanks plugin)
		{
			this.plugin = plugin;
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			Player player = event.getPlayer();
			int slot = event.getPosition();
			NavigationHandler nh = plugin.getNavigation().get(player.getUniqueId());
			Countries country = nh.getCountry();
			TankTypes type = nh.getType();
			ITank tank = null;
			if (country == Countries.CHINA)
			{
				if (type == TankTypes.LIGHT)
				{
					for (ITank iTank : ChinaLight.values())
						if (iTank.getId() == slot)
							tank = iTank;
				}
				else if (type == TankTypes.MEDIUM)
				{
					for (ITank iTank : ChinaMedium.values())
						if (iTank.getId() == slot)
							tank = iTank;
				}
				else if (type == TankTypes.HEAVY)
				{
					for (ITank iTank : ChinaHeavy.values())
						if (iTank.getId() == slot)
							tank = iTank;
				}
			}
			
			for (String name : plugin.getFieldStorage().getFields().keySet())
			{
				plugin.getFieldStorage().getField(name).getPlayer(player.getUniqueId()).setTank(tank);
				player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getIcon().getType(), "Open Hangar.", "Tank: " + tank.getName()));
				player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " You have chosen the " + tank.getName() + ".");
				player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " If you wish to choose another tank, right click with the 'Open Hangar' item.");
				player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " When you are ready, simply right click the 'Ready' item.");
				return;
			}
			
			player.sendMessage(ChatColor.RED + plugin.getPrefix() + "Please click a valid tank.");
			event.setWillClose(false);
		}
	}

	public static class TechTreeSelectionHandler implements OptionClickEventHandler
	{
		MineTanks plugin;
		
		public TechTreeSelectionHandler(MineTanks plugin)
		{
			this.plugin = plugin;
		}
		
		@Override
		public void onOptionClick(OptionClickEvent event)
		{
			event.setWillClose(false);
			Player player = event.getPlayer();
			int slot = event.getPosition();
			NavigationHandler nh = plugin.getNavigation().get(player.getUniqueId());
			Countries country = nh.getCountry();
			TankTypes type = nh.getType();
			ITank tank = null;
			if (country == Countries.CHINA)
			{
				if (type == TankTypes.LIGHT)
				{
					for (ITank iTank : ChinaLight.values())
						if (iTank.getId() == slot)
							tank = iTank;
				}
				else if (type == TankTypes.MEDIUM)
				{
					for (ITank iTank : ChinaMedium.values())
						if (iTank.getId() == slot)
							tank = iTank;
				}
				else if (type == TankTypes.HEAVY)
				{
					for (ITank iTank : ChinaHeavy.values())
						if (iTank.getId() == slot)
							tank = iTank;
				}
			}
			
			PlayerStats stats = plugin.getStatStorage().getStats().get(player.getUniqueId());
			if (!stats.getTanks().contains(tank))
			{
				if (stats.getMoney() >= tank.getMoney() && stats.getXp() >= tank.getXp())
				{
					plugin.getStatStorage().getStats().get(player.getUniqueId()).addTank(tank);
					plugin.getStatStorage().getStats().get(player.getUniqueId()).setMoney(plugin.getStatStorage().getStats().get(player.getUniqueId()).getMoney() - tank.getMoney());
					plugin.getStatStorage().getStats().get(player.getUniqueId()).setXp(plugin.getStatStorage().getStats().get(player.getUniqueId()).getXp() - tank.getXp());
					player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " You have successfully unlocked the" + tank.getName() + ".");
					Menus.openTechTree(player, nh.getCountry(), nh.getType());
					return;
				}
			}
			
			player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: You do not have enough money and/or experience to unlock this tank.");
		}
	}
}
