package musician101.minetanks.command;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank.MTTeam;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;

import org.spongepowered.api.entity.Player;
import org.spongepowered.api.util.command.CommandSource;

public class Join extends SubCommand
{
	public Join(String name, String description, String usage, List<String> aliases)
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
		
		if (!field.isReady())
		{
			player.sendMessage(Messages.FIELD_NOT_READY);
			return;
		}
		
		if (field.inProgress())
		{
			player.sendMessage(Messages.MATCH_IN_PROGRESS);
			return;
		}
		
		field.addPlayer(player, MTTeam.UNASSIGNED);
		MineTanks.openTankMenu(player);
	}
}
