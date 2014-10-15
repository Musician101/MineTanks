package musician101.minetanks.command;

import java.util.ArrayList;
import java.util.List;

import musician101.luc.bukkit.command.ICommand;
import musician101.luc.bukkit.command.ICommandManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CommandManager implements ICommandManager
{
	List<ICommand> commandList = new ArrayList<ICommand>();
	
	public CommandManager()
	{
		addCommand(new Create());
		addCommand(new Cuboid());
		addCommand(new Edit());
		addCommand(new Enable());
		addCommand(new ForceEnd());
		addCommand(new GreenSpawn());
		addCommand(new Join());
		addCommand(new Leave());
		addCommand(new RedSpawn());
		addCommand(new Remove());
		addCommand(new Spectate());
		addCommand(new Spectators());
		addCommand(new Status());
	}
	
	@Override
	public List<ICommand> getCommandList()
	{
		return commandList;
	}
	
	@Override
	public void addCommand(ICommand command)
	{
		commandList.add(command);
	}
	
	@Override
	public void removeCommand(ICommand command)
	{
		commandList.remove(command);
	}
	
	@Override
	public List<String> getHelp(int page)
	{
		//Page argument is ignored until multiple help pages are needed
		List<String> help = new ArrayList<String>();
		help.add(ChatColor.GREEN + "===== MineTanks =====");
		help.add(ChatColor.GREEN + "Version: " + Bukkit.getPluginManager().getPlugin("MineTanks").getDescription().getVersion());
		help.add(ChatColor.GREEN + "Recommended BukkitAPI Version: 1.7.10-R0.1");
		help.add(ChatColor.GREEN + "World of Tanks version: 0.9.2");
		help.add(ChatColor.GREEN + "[] = optional, <> = mandatory");
		for (ICommand command : commandList)
			help.add(ChatColor.GREEN + command.getUsage() + ": " + ChatColor.AQUA + command.getDescription());
		
		return help;
	}
}
