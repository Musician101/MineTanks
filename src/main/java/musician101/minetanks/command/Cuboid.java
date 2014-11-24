package musician101.minetanks.command;

import java.util.Arrays;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.exception.NoPermissionException;
import musician101.luc.bukkit.exception.NotEnoughArgumentsException;
import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;
import musician101.minetanks.util.CuboidUtil;

import org.bukkit.entity.Player;

public class Cuboid implements ICommand
{
	
	@Override
	public String getName()
	{
		return "cuboid";
	}
	
	@Override
	public String getDescription()
	{
		return "Set the cuboid of the currently selected battlefield.";
	}
	
	@Override
	public String getUsage()
	{
		return "/mt " + getName() + " <radius | xradius yradius zradius>";
	}
	
	@Override
	public String getPermission()
	{
		return Perms.PERMS_PREFIX + getName();
	}
	
	@Override
	public List<String> getAliases()
	{
		return Arrays.asList(getName(), "cu");
	}
	
	@Override
	public void execute(Player player, List<String> args) throws Exception
	{
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
			
			field.setCuboid(CuboidUtil.createFromLocationRadius(player.getLocation(), radius));
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
			
			field.setCuboid(CuboidUtil.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
			player.sendMessage(Messages.POSITIVE_PREFIX + "Cuboid set.");
			return;
		}
		
		throw new NotEnoughArgumentsException(Messages.NEGATIVE_PREFIX + "Error: Too many or too little arguments.");
	}
	
}
