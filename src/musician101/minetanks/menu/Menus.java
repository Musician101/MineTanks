package musician101.minetanks.menu;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.BattleField;
import musician101.minetanks.battlefield.PlayerTank;
import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.TankTypes;
import musician101.minetanks.tankinfo.tanks.ChinaLight;
import musician101.minetanks.tankinfo.tanks.ChinaMedium;
import musician101.minetanks.util.IconMenu;
import musician101.minetanks.util.IconMenu.OptionClickEvent;
import musician101.minetanks.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.util.MTUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
		countrySelection = new IconMenu("Country Selection", 9, new OptionClickEventHandler()
		{
	        @Override
	        public void onOptionClick(OptionClickEvent event)
	        {
	            int slot = event.getPosition();
	            if (slot == 0)
	            	chineseTypeSelection.open(event.getPlayer());
	            else
	            	event.getPlayer().sendMessage("I'm sorry but this country hasn't been implemented yet.");
	            
	            event.setWillClose(false);
	        }
	    }, plugin);
		
		for (Countries country : Countries.values())
			countrySelection.setOption(country.getId(), country.getIcon(), "§a" + country.getName(), country.getDescription());
		
		chineseTypeSelection = new IconMenu("Chinese Type Selection", 9, new OptionClickEventHandler()
		{
			@Override
			public void onOptionClick(OptionClickEvent event)
			{
				Player player = event.getPlayer();
				int slot = event.getPosition();
	            if (slot == 0)
	            	chineseLightSelection.open(player);
	            else if (slot == 1)
	            	chineseMediumSelection.open(player);
	            else if (slot == 2)
	            	chineseHeavySelection.open(player);
	            
	            event.setWillClose(false);
			}
		}, plugin);
		
		for (TankTypes type : Countries.CHINA.getTypes())
			chineseTypeSelection.setOption(type.getId(), type.getIcon(), "§a" + type.getMenuName(Countries.CHINA), type.getDescription(Countries.CHINA));
		
		chineseLightSelection = new IconMenu("Chinese Light Tank Selection", 9, new OptionClickEventHandler()
		{
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
			}
		}, plugin);
		
		for (ChinaLight tank : ChinaLight.values())
			chineseLightSelection.setOption(tank.getId(), new ItemStack(Material.WOOD_SWORD, 1), "§a" + tank.getName());
		
		chineseMediumSelection = new IconMenu("Chinese Medium Tank Selection", 9, new OptionClickEventHandler()
		{
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
			}
		}, plugin);
		
		for (ChinaMedium tank : ChinaMedium.values())
			chineseMediumSelection.setOption(tank.getId(), new ItemStack(Material.STONE_SWORD, 1), "§a" + tank.getName());
	}
}
