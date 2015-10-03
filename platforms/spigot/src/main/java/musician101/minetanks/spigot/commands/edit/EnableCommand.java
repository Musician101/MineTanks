package musician101.minetanks.spigot.commands.edit;

import musician101.common.java.minecraft.spigot.AbstractSpigotCommand;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class EnableCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public EnableCommand(MineTanks plugin)
    {
        super("enable", "Set the battlefield that you wish to edit.", Arrays.asList("/mt", "enable", "<" + ChatColor.ITALIC + "field" + ChatColor.RESET + ">"), 2, "minetanks.edit", false, ChatColor.RED + "No Permission", ChatColor.RED + "Player Only");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + plugin.getPrefix() + " Error: Field not specified."))
            return false;

        BattleField field = plugin.getFieldStorage().getField(args[1]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
            return false;
        }

        if (field.isEnabled())
        {
            field.setEnabled(false);
            sender.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " " + field.getName() + " has been disabled.");
        }
        else
        {
            field.setEnabled(true);
            sender.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " " + field.getName() + " has been enabled.");
        }

        return true;
    }
}
