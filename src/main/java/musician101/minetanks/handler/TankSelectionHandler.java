package musician101.minetanks.handler;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.tank.Tanks;
import musician101.minetanks.util.IconMenu.OptionClickEvent;
import musician101.minetanks.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.util.MTUtils;

import org.bukkit.entity.Player;

public class TankSelectionHandler implements OptionClickEventHandler
{
	MineTanks plugin;
	
	public TankSelectionHandler(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void onOptionClick(OptionClickEvent event)
	{
		int slot = event.getPosition();
		Player player = event.getPlayer();
		Tanks tank = null;
		for (Tanks t : Tanks.values())
			if (slot == t.getId())
				tank = t;
		
		if (tank == null)
			return;
		
		Battlefield field = MineTanks.getFieldStorage().getPlayerField(player.getUniqueId());
		field.getPlayer(player.getUniqueId()).setTank(tank);
		player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getIcon().getType(), "Open Hangar", "Tank: " + tank.getName()));
		player.sendMessage(Messages.POSITIVE_PREFIX + "You have chosen the " + tank.getName() + ".");
		player.sendMessage(Messages.POSITIVE_PREFIX + "If you wish to choose another tank, right click with the 'Open Hangar' item.");
		player.sendMessage(Messages.POSITIVE_PREFIX + "When you are ready, simply right click the 'Ready' item.");
	}
}
