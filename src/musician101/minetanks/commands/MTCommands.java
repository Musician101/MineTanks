package musician101.minetanks.commands;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank.MTTeam;
import musician101.minetanks.menu.Menus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MTCommands implements CommandExecutor
{
	MineTanks plugin;
	
	public MTCommands(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length != 0)
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(plugin.prefix + " Sorry but this is a player only command.");
				return false;
			}
			
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("join"))
			{
				if (!player.hasPermission("minetanks.participate"))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Sorry, but you do not have permission for that.");
					return false;
				}
				
				Battlefield field = plugin.fieldStorage.getFields().get(0);
				if (args.length == 2)
					if (plugin.fieldStorage.getField(args[1]) != null)
						field = plugin.fieldStorage.getField(args[1]);
				
				if (!field.isReady())
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Sorry, this field is not ready.");
					return false;
				}
				
				if (field.inProgress())
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Sorry, but the match has already started.");
					return false;
				}
				
				field.addPlayer(player, MTTeam.UNASSIGNED);
				Menus.countrySelection.open(player);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("leave"))
			{
				if (!player.hasPermission("minetanks.participate") || !plugin.fieldStorage.canPlayerExit(player.getUniqueId()))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Sorry, but you do not have permission for that.");
					return false;
				}
				
				for (String name : plugin.fieldStorage.getFields().keySet())
				{
					Battlefield field = plugin.fieldStorage.getField(name); 
					if (field.getPlayer(player.getUniqueId()) != null)
					{
						if (field.removePlayer(player))
						{
							player.sendMessage(ChatColor.GREEN + plugin.prefix + " You have left the battle field.");
							return true;
						}
					}
				}
				
				player.sendMessage(ChatColor.RED + plugin.prefix + " You are not in a battle field.");
				return false;
			}
			
			if (args[0].equalsIgnoreCase("spectate"))
			{
				if (!player.hasPermission("minetanks.participate"))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Sorry, but you do not have permission for that.");
					return false;
				}
				
				Battlefield field = plugin.fieldStorage.getFields().get(0);
				if (args.length == 2)
					if (plugin.fieldStorage.getField(args[1]) != null)
						field = plugin.fieldStorage.getField(args[1]);
				
				if (!field.isReady())
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Sorry, this field is not ready.");
					return false;
				}
				
				field.addPlayer(player, MTTeam.SPECTATOR);
				return true;
			}
			
			if (!player.hasPermission("minetanks.edit"))
			{
				player.sendMessage(ChatColor.RED + plugin.prefix + " Sorry, but you do not have permission for that.");
				return false;
			}
			
			if (args[0].equalsIgnoreCase("create")  && args.length == 2)
			{
				if (!plugin.fieldStorage.createField(args[1]))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " " + args[1] + " already exists.");
					return false;
				}
				
				plugin.fieldStorage.createField(args[1]);
				player.sendMessage(new String[]{ChatColor.GREEN + plugin.prefix + " " + args[1] + " successfully created", ChatColor.GREEN + plugin.prefix + " Check the status of the battle field by using /mt status."});
				return true;
			}
			
			if (args[0].equalsIgnoreCase("edit")  && args.length == 2)
			{
				if (!plugin.fieldStorage.setEdit(args[1]))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + args[1] + " does not exist.");
					return false;
				}
				
				player.sendMessage(ChatColor.GREEN + plugin.prefix + args[1] + " is now being edited.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("p1"))
			{
				Battlefield field = plugin.fieldStorage.getEdit();
				if (!player.getWorld().getName().equals(field.getWorldName()))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Error: The battlefield contains points in another world.");
					return false;
				}
				
				field.setPoint1(player.getLocation());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Point 1 set.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("p2"))
			{
				Battlefield field = plugin.fieldStorage.getEdit();
				if (!player.getWorld().getName().equals(field.getWorldName()))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Error: The battlefield contains points in another world.");
					return false;
				}
				
				field.setPoint2(player.getLocation());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Point 2 set.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("greenSpawn"))
			{
				Battlefield field = plugin.fieldStorage.getEdit();
				if (!player.getWorld().getName().equals(field.getWorldName()))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Error: The battlefield contains points in another world.");
					return false;
				}
				
				field.setGreenSpawn(player.getLocation());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Green Spawn point set.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("redSpawn"))
			{
				Battlefield field = plugin.fieldStorage.getEdit();
				if (!player.getWorld().getName().equals(field.getWorldName()))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Error: The battlefield contains points in another world.");
					return false;
				}
				
				field.setRedSpawn(player.getLocation());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Red Spawn point set.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("spectators"))
			{
				Battlefield field = plugin.fieldStorage.getEdit();
				if (!player.getWorld().getName().equals(field.getWorldName()))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Error: The battlefield contains points in another world.");
					return false;
				}
				
				field.setSpectators(player.getLocation());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Spectators point set.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("status"))
			{
				Battlefield field = plugin.fieldStorage.getEdit();
				if (field.isReady())
				{
					player.sendMessage(ChatColor.GREEN + plugin.prefix + " " + field.getName() + " is ready for battle.");
					return true;
				}
				
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Status of " + field.getName());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Enabled: " + field.isEnabled());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Point 1: " + (field.getPoint1() == null ? "Not Set" : "Set"));
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Point 2: " + (field.getPoint2() == null ? "Not Set" : "Set"));
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Green Spawn: " + (field.getGreenSpawn() == null ? "Not Set" : "Set"));
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Red Spawn: " + (field.getRedSpawn() == null ? "Not Set" : "Set"));
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Spectators Spawn: " + (field.getSpectators() == null ? "Not Set" : "Set"));
				return true;
			}
			
			if (args[0].equalsIgnoreCase("remove") && args.length == 2)
			{
				if (!plugin.fieldStorage.removeField(args[1]))
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " " + args[1] + " does not exist.");
					return false;
				}
				
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " " + args[1] + " has been deleted.");
				return true;
			}
		}
		
		sender.sendMessage(ChatColor.GREEN + "===== MineTanks =====");
		sender.sendMessage(ChatColor.GREEN + "Version:" + plugin.getDescription().getVersion());
		sender.sendMessage(ChatColor.GREEN + "Recommended BukkitAPI Version: 1.7.9-R0.2");
		sender.sendMessage(ChatColor.GREEN + "World of Tanks version: 0.9.1");
		return true;
	}

}
