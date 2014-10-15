package musician101.minetanks.command;

import java.util.Arrays;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.exception.NoPermissionException;
import musician101.luc.bukkit.exception.NotEnoughArgumentsException;
import musician101.minetanks.MineTanks;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;

import org.bukkit.entity.Player;

public class Edit implements ICommand
{
	
	@Override
	public String getName()
	{
		return "edit";
	}
	
	@Override
	public String getDescription()
	{
		return "Set the battlefield to be edited.";
	}
	
	@Override
	public String getUsage()
	{
		return "/mt " + getName() + " <field>";
	}
	
	@Override
	public String getPermission()
	{
		return Perms.PERMS_PREFIX + getName();
	}
	
	@Override
	public List<String> getAliases()
	{
		return Arrays.asList(getName(), "e");
	}
	
	@Override
	public void execute(Player player, List<String> args) throws Exception
	{
		if (!player.hasPermission(getPermission()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		if (args.size() < 1)
			throw new NotEnoughArgumentsException(Messages.NEGATIVE_PREFIX + "Error: Field not specified.");
		
		if (!MineTanks.getFieldStorage().setEdit(args.get(0)))
			throw new FieldDoesNotExistException(Messages.FIELD_DNE);
		
		player.sendMessage(Messages.POSITIVE_PREFIX + args.get(0) + " is now being edited.");
	}
	
}
