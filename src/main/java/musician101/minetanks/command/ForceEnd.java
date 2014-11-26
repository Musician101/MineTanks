package musician101.minetanks.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;

import org.spongepowered.api.entity.Player;
import org.spongepowered.api.util.command.CommandSource;

public class ForceEnd extends SubCommand
{
	public ForceEnd(String name, String description, String usage, List<String> aliases)
	{
		super(name, description, usage, aliases);
	}

	@Override
	public void execute(CommandSource source, List<String> args) throws Exception
	{
		Player player = (Player) source;
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
			Player p = MineTanks.getGame().getPlayer(uuid).get();
			p.sendMessage(Messages.NEGATIVE_PREFIX + "An admin has forcibly terminated the match.");
			field.removePlayer(p);
		}
		
		field.setInProgress(false);
		player.sendMessage(Messages.POSITIVE_PREFIX + "Match terminated.");
	}
	
}
