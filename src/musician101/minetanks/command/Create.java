package musician101.minetanks.command;

import java.util.Arrays;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.exception.NoPermissionException;
import musician101.luc.bukkit.exception.NotEnoughArgumentsException;
import musician101.minetanks.MineTanks;
import musician101.minetanks.exception.FieldAlreadyExistsException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;

import org.bukkit.entity.Player;

public class Create implements ICommand
{
	@Override
	public String getName()
	{
		return "create";
	}

	@Override
	public String getDescription()
	{
		return "Create a battlefield with the given name.";
	}

	@Override
	public String getUsage()
	{
		return "/mt " + getName() + " <name>";
	}

	@Override
	public String getPermission()
	{
		return Perms.PERMS_PREFIX + getName();
	}

	@Override
	public List<String> getAliases()
	{
		return Arrays.asList(getName(), "c");
	}

	@Override
	public void execute(Player player, List<String> args) throws Exception
	{
		if (!player.hasPermission(getPermission()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		if (args.size() < 1)
			throw new NotEnoughArgumentsException(Messages.NEGATIVE_PREFIX + "Error: Field not specified.");
		
		if (!MineTanks.getFieldStorage().createField(args.get(0)))
			throw new FieldAlreadyExistsException(String.format(Messages.FIELD_ALREADY_EXISTS, args.get(0)));
		
		MineTanks.getFieldStorage().createField(args.get(0));
		player.sendMessage(new String[]{Messages.POSITIVE_PREFIX + args.get(0) + " successfully created",
				Messages.POSITIVE_PREFIX + "Check the status of the battlefield by using /mt status."});
	}
}
