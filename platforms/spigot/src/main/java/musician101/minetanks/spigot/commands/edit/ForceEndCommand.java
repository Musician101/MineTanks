package musician101.minetanks.spigot.commands.edit;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.commands.AbstractSpigotCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class ForceEndCommand extends AbstractSpigotCommand
{
    public ForceEndCommand(MineTanks plugin)
    {
        super(plugin, "forceend", "Forcibly end the match at the specified field.", Arrays.asList("/mt", "forceend", "<" + ChatColor.ITALIC + "field" + ChatColor.RESET + ">"), 1, "minetanks.edit", false);
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
        field.endMatch(true);
        field.setInProgress(false);
        sender.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Match terminated.");
        return true;
    }
}
