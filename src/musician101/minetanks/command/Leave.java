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

public class Leave implements ICommand
{
	@Override
	public String getName()
	{
		return "leave";
	}

	@Override
	public String getDescription()
	{
		return "Leave the current battlefield you are in.";
	}

	@Override
	public String getUsage()
	{
		return "/mt leave";
	}

	@Override
	public String getPermission()
	{
		return Perms.PERMS_PREFIX + getName();
	}

	@Override
	public List<String> getAliases()
	{
		return Arrays.asList(getName(), "l");
	}

	@Override
	public void execute(Player player, List<String> args) throws Exception
	{
		if (!player.hasPermission(getPermission()) || !MineTanks.getFieldStorage().canPlayerExit(player.getUniqueId()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		Battlefield field = MineTanks.getFieldStorage().getPlayerField(player.getUniqueId());
		if (field != null)
		{
			field.removePlayer(player);
			player.sendMessage(Messages.POSITIVE_PREFIX + "You have left the battlefield.");
			return;
		}
		
		player.sendMessage(Messages.NEGATIVE_PREFIX + "You are not in a battlefield.");
	}
	
}
