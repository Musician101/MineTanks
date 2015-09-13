package musician101.minetanks.spigot.commands;

import musician101.minetanks.common.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.util.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                sender.sendMessage(plugin.getPrefix() + " Sorry but this is a player only command.");
                return false;
            }

            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("join") && args.length == 2)
            {
                if (!player.hasPermission("minetanks.participate"))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, but you do not have permission for that.");
                    return false;
                }

                BattleField field = null;
                for (String name : plugin.getFieldStorage().getFields().keySet())
                    if (name.equalsIgnoreCase(args[1]))
                        field = plugin.getFieldStorage().getField(name);

                if (field == null)
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
                    return false;
                }

                if (!field.isReady())
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, this field is not ready.");
                    return false;
                }

                if (field.inProgress())
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, but the match has already started.");
                    return false;
                }

                field.addPlayer(player, MTTeam.UNASSIGNED);
                plugin.getMenuHandler().openTankMenu(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("leave"))
            {
                if (!player.hasPermission("minetanks.participate") || !plugin.getFieldStorage().canPlayerExit(player.getUniqueId()))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, but you do not have permission for that.");
                    return false;
                }

                for (String name : plugin.getFieldStorage().getFields().keySet())
                {
                    BattleField field = plugin.getFieldStorage().getField(name);
                    if (field.getPlayer(player.getUniqueId()) != null)
                    {
                        if (field.removePlayer(player))
                        {
                            player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " You have left the battlefield.");
                            return true;
                        }
                    }
                }

                player.sendMessage(ChatColor.RED + plugin.getPrefix() + " You are not in a battlefield.");
                return false;
            }

            if (args[0].equalsIgnoreCase("spectate") && args.length == 2)
            {
                if (!player.hasPermission("minetanks.participate"))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, but you do not have permission for that.");
                    return false;
                }

                BattleField field = null;
                for (String name : plugin.getFieldStorage().getFields().keySet())
                    if (name.equalsIgnoreCase(args[1]))
                        field = plugin.getFieldStorage().getField(args[1]);

                if (field == null)
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
                    return false;
                }

                if (!field.isReady())
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, this field is not ready.");
                    return false;
                }

                field.addPlayer(player, MTTeam.SPECTATOR);
                return true;
            }

            if (!player.hasPermission("minetanks.edit"))
            {
                player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, but you do not have permission for that.");
                return false;
            }

            if (args[0].equalsIgnoreCase("create") && args.length >= 2)
            {
                if (!plugin.getFieldStorage().createField(args[1]))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " " + args[1] + " already exists.");
                    return false;
                }

                plugin.getFieldStorage().createField(args[1]);
                player.sendMessage(new String[]{ChatColor.GREEN + plugin.getPrefix() + " " + args[1] + " successfully created", ChatColor.GREEN + plugin.getPrefix() + " Check the status of the battlefield by using /mt status."});
                return true;
            }

            if (args[0].equalsIgnoreCase("cuboid"))
            {
                BattleField field = plugin.getFieldStorage().getEdit();
                if (args.length == 2)
                {
                    int radius = 0;
                    try
                    {
                        radius = Integer.valueOf(args[1]);
                    }
                    catch (NumberFormatException e)
                    {
                        player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: " + args[1] + " is not a number.");
                        return false;
                    }

                    field.setCuboid(Cuboid.createFromLocationRadius(player.getLocation(), radius));
                    player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Cuboid set.");
                    return true;
                } else if (args.length == 4)
                {
                    int xRadius;
                    int yRadius;
                    int zRadius;
                    try
                    {
                        xRadius = Integer.valueOf(args[1]);
                        yRadius = Integer.valueOf(args[2]);
                        zRadius = Integer.valueOf(args[3]);
                    }
                    catch (NumberFormatException e)
                    {
                        player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: One or more of the inputted radii is not a number.");
                        return false;
                    }

                    field.setCuboid(Cuboid.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
                    player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Cuboid set.");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("edit") && args.length == 2)
            {
                BattleField field = null;
                for (String name : plugin.getFieldStorage().getFields().keySet())
                    if (name.equalsIgnoreCase(args[1]))
                        field = plugin.getFieldStorage().getField(name);

                if (field == null)
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
                    return false;
                }

                if (!plugin.getFieldStorage().setEdit(field.getName()))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + args[1] + " does not exist.");
                    return false;
                }

                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + args[1] + " is now being edited.");
                return true;
            }

            if (args[0].equalsIgnoreCase("enable"))
            {
                BattleField field = plugin.getFieldStorage().getEdit();
                if (field.isEnabled())
                {
                    field.setEnabled(false);
                    player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " " + field.getName() + " has been disabled.");
                } else if (!field.isEnabled())
                {
                    field.setEnabled(true);
                    player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " " + field.getName() + " has been enabled.");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("forceend") && args.length == 2)
            {
                BattleField field = null;
                for (String name : plugin.getFieldStorage().getFields().keySet())
                    if (args[1].equalsIgnoreCase(name))
                        field = plugin.getFieldStorage().getField(name);

                if (field == null)
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
                    return false;
                }

                List<UUID> uuids = new ArrayList<UUID>();
                for (UUID uuid : field.getPlayers().keySet())
                    uuids.add(uuid);

                for (UUID uuid : uuids)
                {
                    Player p = Bukkit.getPlayer(uuid);
                    p.removePotionEffect(PotionEffectType.SLOW);
                    p.removePotionEffect(PotionEffectType.SPEED);
                    p.sendMessage(ChatColor.RED + plugin.getPrefix() + " An admin has forcibly terminated the match.");
                    field.removePlayer(p);
                }

                field.setInProgress(false);
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Match terminated.");
                return false;
            }

            if (args[0].equalsIgnoreCase("greenSpawn"))
            {
                BattleField field = plugin.getFieldStorage().getEdit();
                if (field.getCuboid() == null || !player.getWorld().getName().equals(field.getCuboid().getWorld().getName()))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: The battlefield contains no cuboid or the cuboid in another world.");
                    return false;
                }

                field.setGreenSpawn(player.getLocation());
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Green Spawn point set.");
                return true;
            }

            if (args[0].equalsIgnoreCase("redSpawn"))
            {
                BattleField field = plugin.getFieldStorage().getEdit();
                if (field.getCuboid() == null || !player.getWorld().getName().equals(field.getCuboid().getWorld().getName()))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: The battlefield contains no cuboid or the cuboid in another world.");
                    return false;
                }

                field.setRedSpawn(player.getLocation());
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Red Spawn point set.");
                return true;
            }

            if (args[0].equalsIgnoreCase("spectators"))
            {
                BattleField field = plugin.getFieldStorage().getEdit();
                if (field.getCuboid() == null || !player.getWorld().getName().equals(field.getCuboid().getWorld().getName()))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: The battlefield contains no cuboid or the cuboid in another world.");
                    return false;
                }

                field.setSpectators(player.getLocation());
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Spectators point set.");
                return true;
            }

            if (args[0].equalsIgnoreCase("status"))
            {
                BattleField field = plugin.getFieldStorage().getEdit();
                if (field.isReady())
                {
                    player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " " + field.getName() + " is ready for battle.");
                    return true;
                }

                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Status of " + field.getName());
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Enabled: " + field.isEnabled());
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Cuboid: " + (field.getCuboid() == null ? "Not Set" : "Set"));
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Green Spawn: " + (field.getGreenSpawn() == null ? "Not Set" : "Set"));
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Red Spawn: " + (field.getRedSpawn() == null ? "Not Set" : "Set"));
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Spectators Spawn: " + (field.getSpectators() == null ? "Not Set" : "Set"));
                return true;
            }

            if (args[0].equalsIgnoreCase("remove") && args.length == 2)
            {
                BattleField field = null;
                for (String name : plugin.getFieldStorage().getFields().keySet())
                    if (name.equalsIgnoreCase(args[1]))
                        field = plugin.getFieldStorage().getField(name);

                if (field == null)
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
                    return false;
                }

                if (!plugin.getFieldStorage().removeField(field.getName()))
                {
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " " + args[1] + " does not exist.");
                    return false;
                }

                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " " + args[1] + " has been deleted.");
                return true;
            }
        }

        sender.sendMessage(ChatColor.GREEN + "===== MineTanks =====");
        sender.sendMessage(ChatColor.GREEN + "Version: " + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GREEN + "Recommended BukkitAPI Version: 1.7.10-R0.1");
        sender.sendMessage(ChatColor.GREEN + "World of Tanks version: 0.9.2");
        if (sender.hasPermission("minetanks.participate") || sender.hasPermission("minetanks.edit"))
            sender.sendMessage(ChatColor.GREEN + "[] = optional, <> = mandatory");

        if (sender.hasPermission("minetanks.participate"))
        {
            sender.sendMessage(ChatColor.GREEN + "/mt join [field]:" + ChatColor.AQUA + " Enter the selected battlefield.");
            sender.sendMessage(ChatColor.GREEN + "/mt leave:" + ChatColor.AQUA + " Leave the battlefield you are currently in.");
            sender.sendMessage(ChatColor.GREEN + "/mt spectate [field]:" + ChatColor.AQUA + " Spectate the selected battlefield.");
        }

        if (sender.hasPermission("minetanks.edit"))
        {
            sender.sendMessage(ChatColor.GREEN + "/mt create <name>:" + ChatColor.AQUA + " Create a new battlefield.");
            sender.sendMessage(ChatColor.GREEN + "/mt cuboid <radius|xradius yradius zradius>:" + ChatColor.AQUA + " Set the cuboid of the currently selected battlefield.");
            sender.sendMessage(ChatColor.GREEN + "/mt edit <name>:" + ChatColor.AQUA + " Set the battlefield that you wish to edit.");
            sender.sendMessage(ChatColor.GREEN + "/mt enable:" + ChatColor.AQUA + " Toggle whether the currently selected battlefield is enabled or disabled.");
            sender.sendMessage(ChatColor.GREEN + "/mt greenspawn:" + ChatColor.AQUA + " Set the green spawn point of the currently selected battlefield.");
            sender.sendMessage(ChatColor.GREEN + "/mt redspawn:" + ChatColor.AQUA + " Set the red spawn point of the currently selected battlefield.");
            sender.sendMessage(ChatColor.GREEN + "/mt remove <name>:" + ChatColor.AQUA + " Remove the selected battlefield.");
            sender.sendMessage(ChatColor.GREEN + "/mt spectate <name>:" + ChatColor.AQUA + " Spectate the battlefield that you specified.");
            sender.sendMessage(ChatColor.GREEN + "/mt spectators:" + ChatColor.AQUA + " Set the spectators spawn point of the currently selected battlefield.");
            sender.sendMessage(ChatColor.GREEN + "/mt status:" + ChatColor.AQUA + " Check the status of the currently selected battlefield.");
        }

        return true;
    }

}
