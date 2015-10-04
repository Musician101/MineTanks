package musician101.minetanks.spigot.commands.edit;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class RemoveCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public RemoveCommand(MineTanks plugin)
    {
        super("remove", "Remove the specified field.", Arrays.asList(new CommandArgument("/mt"), new CommandArgument("remove"), new CommandArgument("field", Syntax.REQUIRED, Syntax.REPLACE)), 1, "minetanks.edit", false, ChatColor.RED + "No Permission", ChatColor.RED + "Player Only");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (minArgsMet(sender, args.length, ChatColor.RED + plugin.getPrefix() + " Error: Field not specified."))
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
