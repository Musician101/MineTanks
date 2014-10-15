package musician101.minetanks.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.exception.NoPermissionException;
import musician101.luc.bukkit.exception.NotEnoughArgumentsException;
import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ForceEnd implements ICommand
{
	@Override
	public String getName()
	{
		return "forceend";
	}

	@Override
	public String getDescription()
	{
		return "Forcibly end the match at the specified field.";
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
		return Arrays.asList(getName(), "fe");
	}

	@Override
	public void execute(Player player, List<String> args) throws Exception
	{
		if (!player.hasPermission(getPermission()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		if (args.size() < 1)
			throw new NotEnoughArgumentsException(Messages.NEGATIVE_PREFIX + "Error: Field not specified.");
		
		Battlefield field = MineTanks.getFieldStorage().getField(args.get(0));
		if (field == null)
			throw new FieldDoesNotExistException(Messages.FIELD_DNE);
		
		List<UUID> uuids = new ArrayList<UUID>();
		for (UUID uuid : field.getPlayers().keySet())
			uuids.add(uuid);
		
		for (UUID uuid : uuids)
		{
			Player p = Bukkit.getPlayer(uuid);
			p.sendMessage(Messages.NEGATIVE_PREFIX + "An admin has forcibly terminated the match.");
			field.removePlayer(p);
		}
		
		field.setInProgress(false);
		player.sendMessage(Messages.POSITIVE_PREFIX + "Match terminated.");
	}
	
}
