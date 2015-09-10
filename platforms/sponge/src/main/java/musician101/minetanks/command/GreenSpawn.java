package musician101.minetanks.command;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.spigot.battlefield.Battlefield;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

public class GreenSpawn extends SubCommand
{
	public GreenSpawn(String name, String description, List<String> aliases)
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
		if (field == null)
			throw new FieldDoesNotExistException(Messages.NEGATIVE_PREFIX + "Error: No fields have been created.");
		
		if (field.getRegion() == null || !player.getWorld().getName().equals(field.getRegion().getWorld().getName()))
		{
			player.sendMessage(Messages.MISSING_CUBOID);
			return;
		}
		
		if (!field.getRegion().isInRegion(player.getLocation()))
		{
			player.sendMessage(Messages.LOCATION_NOT_IN_CUBOID);
			return;
		}
		
		field.setGreenSpawn(player.getLocation());
		player.sendMessage(Messages.POSITIVE_PREFIX + "Green Spawn point set.");
	}
}
