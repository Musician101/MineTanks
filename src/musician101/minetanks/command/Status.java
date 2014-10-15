package musician101.minetanks.command;

import java.util.Arrays;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.exception.NoPermissionException;
import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Status implements ICommand
{

	@Override
	public String getName()
	{
		return "status";
	}

	@Override
	public String getDescription()
	{
		return "Check the status of the currently selected battlefield.";
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
		return Arrays.asList(getName(), "st");
	}

	@Override
	public void execute(Player player, List<String> args) throws Exception
	{
		if (!player.hasPermission(getPermission()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		Battlefield field = MineTanks.getFieldStorage().getEdit();
		if (field == null)
			throw new FieldDoesNotExistException(Messages.NEGATIVE_PREFIX + "Error: No fields have been created.");
		
		if (field.isReady())
		{
			player.sendMessage(Messages.POSITIVE_PREFIX + field.getName() + " is ready for battle.");
			return;
		}
		
		player.sendMessage(new String[]{Messages.POSITIVE_PREFIX + "Status of " + field.getName(),
				Messages.POSITIVE_PREFIX + "Enabled: " + (field.isEnabled() ? ChatColor.RED + "Yes" : ChatColor.GREEN + "No"),
				Messages.POSITIVE_PREFIX + "Cuboid: " + (field.getCuboid() == null ? ChatColor.RED + "Not Set" : ChatColor.GREEN + "Set"),
				Messages.POSITIVE_PREFIX + "Green Spawn: " + (field.getGreenSpawn() == null ? ChatColor.RED + "Not Set" : ChatColor.GREEN + "Set"),
				Messages.POSITIVE_PREFIX + "Red Spawn: " + (field.getRedSpawn() == null ? ChatColor.RED + "Not Set" : ChatColor.GREEN + "Set"),
				Messages.POSITIVE_PREFIX + "Spectators Spawn: " + (field.getSpectators() == null ? ChatColor.RED + "Not Set" : ChatColor.GREEN + "Set"),
				Messages.POSITIVE_PREFIX + "Match in progress: " + (field.inProgress() ? ChatColor.RED + "Yes" : ChatColor.GREEN + "No")});
	}
	
}
