package musician101.minetanks.spigot.commands.edit;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.commands.AbstractSpigotCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class RemoveCommand extends AbstractSpigotCommand
{
    public RemoveCommand(MineTanks plugin)
    {
        super(plugin, "remove", "Remove the specified field.", Arrays.asList("/mt", "remove", "<" + ChatColor.ITALIC + "field" + ChatColor.RESET + ">"), 1, "minetanks.edit", false);
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (minArgsMet(sender, args.length, ChatColor.RED + plugin.getPrefix() + " Error: Field not specified."))
            return false;

        if (!doesFieldExist(sender, args[0]))
            return false;

        BattleField field = plugin.getFieldStorage().getField(args[0]);
        if (!plugin.getFieldStorage().removeField(field.getName()))
        {
            sender.sendMessage(ChatColor.RED + plugin.getPrefix() + " " + args[1] + " does not exist.");
            return false;
        }

        sender.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " " + args[1] + " has been deleted.");
        return true;
    }
}
