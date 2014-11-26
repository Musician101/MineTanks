package musician101.minetanks.command;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.lib.Reference.Messages;

import org.spongepowered.api.entity.Player;
import org.spongepowered.api.util.command.CommandSource;

public class Leave extends SubCommand
{
	public Leave(String name, String description, List<String> aliases)
	{
		super(name, description, aliases);
	}

	@Override
	public void execute(CommandSource source, List<String> args) throws Exception
	{
		Player player = (Player) source;
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
