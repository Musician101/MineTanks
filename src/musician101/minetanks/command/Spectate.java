package musician101.minetanks.command;

import java.util.Arrays;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.exception.NoPermissionException;
import musician101.luc.bukkit.exception.NotEnoughArgumentsException;
import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank.MTTeam;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.lib.Reference.Perms;

import org.bukkit.entity.Player;

public class Spectate implements ICommand
{
	@Override
	public String getName()
	{
		return "spectate";
	}
	
	@Override
	public String getDescription()
	{
		return "Spectate the specified battlefield.";
	}
	
	@Override
	public String getUsage()
	{
		return "/mt spectate <field>";
	}
	
	@Override
	public String getPermission()
	{
		return Perms.PERMS_PREFIX + getName();
	}
	
	@Override
	public List<String> getAliases()
	{
		return Arrays.asList(getName(), "s");
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
		
		if (!field.isReady())
		{
			player.sendMessage(Messages.FIELD_NOT_READY);
			return;
		}
		
		field.addPlayer(player, MTTeam.SPECTATOR);
	}
}
