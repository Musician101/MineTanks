package musician101.minetanks.command;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.util.Region;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

public class Cuboid extends SubCommand
{
	public Cuboid(String name, String description, String usage, List<String> aliases)
	{
		super(name, description, usage, aliases);
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
		
		if (args.size() >= 1 && args.size() < 3)
		{
			int radius = 0;
			try
			{
				radius = Integer.valueOf(args.get(0));
			}
			catch (NumberFormatException e)
			{
				player.sendMessage(Messages.NEGATIVE_PREFIX + "Error: " + args.get(0) + " is not a number.");
				return;
			}
			
			field.setRegion(Region.createFromLocationRadius(player.getLocation(), radius));
			player.sendMessage(Messages.POSITIVE_PREFIX + "Cuboid set.");
			return;
		}
		else if (args.size() == 3)
		{
			int xRadius = 0;
			int yRadius = 0;
			int zRadius = 0;
			try
			{
				xRadius = Integer.valueOf(args.get(0));
				yRadius = Integer.valueOf(args.get(1));
				zRadius = Integer.valueOf(args.get(2));
			}
			catch (NumberFormatException e )
			{
				player.sendMessage(Messages.NEGATIVE_PREFIX + "Error: One or more of the inputted radii is not a number.");
				return;
			}
			
			field.setRegion(Region.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
			player.sendMessage(Messages.POSITIVE_PREFIX + "Cuboid set.");
			return;
		}
		
		throw new NotEnoughArgumentsException(Messages.NEGATIVE_PREFIX + "Error: Too many or too little arguments.");
	}
}
