package musician101.minetanks.spigot.commands.participate;

import musician101.common.java.minecraft.spigot.AbstractSpigotCommand;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaveCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public LeaveCommand(MineTanks plugin)
    {
        super("leave", "Leave the battlefield you are currently in.", Arrays.asList("/mt", "leave"), 0, "minetanks.participate", true, ChatColor.RED + "No Permission", ChatColor.RED + "Player Only");
        this.plugin = plugin;
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
                field.removePlayer(player.getUniqueId());
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + plugin.getPrefix() + " You are not in a battlefield");
        return false;
    }
}
