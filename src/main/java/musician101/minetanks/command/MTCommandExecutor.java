package musician101.minetanks.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.minetanks.lib.Reference.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MTCommandExecutor implements CommandExecutor
{
	CommandManager cm;
	
	public MTCommandExecutor()
	{
		cm = new CommandManager();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] mainArgs)
	{
		if (!(sender instanceof Player))
		{
			for (String msg : cm.getHelp(0))
				sender.sendMessage(msg);
			
			return true;
		}
		
		List<String> args = new ArrayList<String>();
		Collections.addAll(args, mainArgs);
		if (mainArgs.length > 0)
			args.remove(mainArgs[0]);
		
		for (ICommand cmd : cm.getCommandList())
		{
			for (String alias : cmd.getAliases())
			{
				if (alias.equalsIgnoreCase(mainArgs[0]))
				{
					try
					{
						cmd.execute((Player) sender, args);
					}
					catch (Exception e)
					{
						sender.sendMessage(e.getMessage());
						return true;
					}
					return true;
				}
			}
		}
		
		sender.sendMessage(Messages.NEGATIVE_PREFIX + "Unkown command: " + mainArgs[0]);
		return true;
	}

}
