package musician101.minetanks.spigot.commands.edit;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


import java.util.Arrays;

public class StatusCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public StatusCommand(MineTanks plugin)
    {
        super("status", "View the status of the specified field.", Arrays.asList(new CommandArgument("/mt"), new CommandArgument("status"), new CommandArgument("field", Syntax.REPLACE, Syntax.REQUIRED)), 1, "minetanks.edit", false, ChatColor.RED + "No Permission", ChatColor.RED + "Player Only");
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
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
            return false;
        }

        sender.sendMessage(new String[]{ChatColor.GREEN + plugin.getPrefix() + " Status of " + field.getName(), ChatColor.GREEN + plugin.getPrefix() + " Enabled: " + field.isEnabled(), ChatColor.GREEN + plugin.getPrefix() + " Region: " + (field.getSpigotRegion() == null ? "Not Set" : "Set"), ChatColor.GREEN + plugin.getPrefix() + " Green Spawn: " + (field.getGreenSpawn() == null ? "Not Set" : "Set"), ChatColor.GREEN + plugin.getPrefix() + " Red Spawn: " + (field.getRedSpawn() == null ? "Not Set" : "Set"), ChatColor.GREEN + plugin.getPrefix() + " Spectators Spawn: " + (field.getSpectators() == null ? "Not Set" : "Set")});
        return true;
    }
}
