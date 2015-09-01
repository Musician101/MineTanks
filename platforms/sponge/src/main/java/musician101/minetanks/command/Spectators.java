package musician101.minetanks.command;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.lib.Reference.Messages;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

public class Spectators extends SubCommand
{
	public Spectators(String name, String description, List<String> aliases)
	{
		super(name, description, aliases);
	}
	
	@Override
	public void execute(CommandSource source, List<String> args) throws Exception
	{
		Player player = (Player) source;
		if (!player.hasPermission(getPermission()))
			throw new NoPermissionException(Messages.NO_PERMISSION);
		
		Battlefield field = MineTanks.getFieldStorage().getEdit();
		if (field.getRegion() == null || !player.getWorld().getName().equals(field.getRegion().getWorld().getName()))
		{
			player.sendMessage(Messages.MISSING_CUBOID);
			return;
		}
		
		if (field.getRegion().isInRegion(player.getLocation()))
		{
			player.sendMessage(Messages.LOCATION_NOT_IN_CUBOID);
			return;
		}
		
		field.setSpectators(player.getLocation());
		player.sendMessage(Messages.POSITIVE_PREFIX + "Spectators point set.");
	}
	
}
