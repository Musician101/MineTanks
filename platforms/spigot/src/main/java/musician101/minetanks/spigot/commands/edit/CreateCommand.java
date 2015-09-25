package musician101.minetanks.spigot.commands.edit;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.commands.AbstractSpigotCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CreateCommand extends AbstractSpigotCommand
{
    public CreateCommand(MineTanks plugin)
    {
        super(plugin, "create", "Create a new battlefield.", Arrays.asList("/mt", "create", "<" + ChatColor.ITALIC + "name" + ChatColor.RESET + ">"), 1, "minetanks.edit", true);
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + plugin.getPrefix() + " Error: Field not specified."))
            return false;

        if (!plugin.getFieldStorage().createField(args[0]))
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field already exists.");
            return false;
        }

        player.sendMessage(new String[]{ChatColor.GREEN + plugin.getPrefix() + " " + args[1] + " successfully created.", ChatColor.GREEN + plugin.getPrefix() + " Check the status of the battlefield by using /mt status."});
        return true;
    }
}
