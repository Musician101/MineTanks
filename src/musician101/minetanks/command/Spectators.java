package musician101.minetanks.command;

import java.util.Arrays;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.exception.NoPermissionException;
import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;

import org.bukkit.entity.Player;

public class Spectators implements ICommand
{
	@Override
	public String getName()
	{
		return "spectators";
	}
	
	@Override
	public String getDescription()
	{
		return "Set the spectators spawn point of the currently selected battlefield.";
	}
	
	@Override
	public String getUsage()
	{
		return "/mt " + getName();
	}
	
	@Override
	public String getPermission()
	{
		return Perms.PERMS_PREFIX + getName();
	}
	
	@Override
	public List<String> getAliases()
	{
		return Arrays.asList(getName(), "ss");
	}
	
	@Override
	public void execute(Player player, List<String> args) throws Exception
	{
		if (!player.hasPermission(getPermission()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		Battlefield field = MineTanks.getFieldStorage().getEdit();
		if (field.getCuboid() == null || !player.getWorld().getName().equals(field.getCuboid().getWorld().getName()))
		{
			player.sendMessage(Messages.MISSING_CUBOID);
			return;
		}
		
		if (field.getCuboid().isInCuboid(player.getLocation()))
		{
			player.sendMessage(Messages.LOCATION_NOT_IN_CUBOID);
			return;
		}
		
		field.setSpectators(player.getLocation());
		player.sendMessage(Messages.POSITIVE_PREFIX + "Spectators point set.");
	}
	
}
