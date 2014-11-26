package musician101.minetanks.command;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;

import org.spongepowered.api.entity.Player;
import org.spongepowered.api.util.command.CommandSource;

public class Remove extends SubCommand
{
	
	public Remove(String name, String description, String usage, List<String> aliases)
	{
		super(name, description, usage, aliases);
	}

	@Override
	public void execute(CommandSource source, List<String> args) throws Exception
	{
		Player player = (Player) source;
		if (!player.hasPermission(getPermission()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		if (!MineTanks.getFieldStorage().removeField(args.get(0)))
			throw new FieldDoesNotExistException(Messages.FIELD_DNE);
		
		player.sendMessage(Messages.POSITIVE_PREFIX + args.get(0) + " has been deleted.");
	}
	
}
