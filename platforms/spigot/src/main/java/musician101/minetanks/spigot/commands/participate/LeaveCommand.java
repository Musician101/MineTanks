package musician101.minetanks.spigot.commands.participate;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.commands.AbstractSpigotCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaveCommand extends AbstractSpigotCommand
{
    public LeaveCommand(MineTanks plugin)
    {
        super(plugin, "leave", "Leave the battlefield you are currently in.", Arrays.asList("/mt", "leave"), 0, "minetanks.participate", true);
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (plugin.getFieldStorage().canPlayerExit(player.getUniqueId()))
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, but you do not have permission for that.");
            return false;
        }

        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            BattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayer(player.getUniqueId()) != null)
            {
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " You have left the battlefield.");
                field.removePlayer(player);
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + plugin.getPrefix() + " You are not in a battlefield");
        return false;
    }
}
