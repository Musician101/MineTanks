package musician101.minetanks.spigot.commands.edit;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.commands.AbstractSpigotCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class StatusCommand extends AbstractSpigotCommand
{
    public StatusCommand(MineTanks plugin)
    {
        super(plugin, "status", "View the status of the specified field.", Arrays.asList("/mt", "status", "<" + ChatColor.ITALIC + "field" + ChatColor.RESET + ">"), 1, "minetanks.edit", false);
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
        sender.sendMessage(new String[]{ChatColor.GREEN + plugin.getPrefix() + " Status of " + field.getName(), ChatColor.GREEN + plugin.getPrefix() + " Enabled: " + field.isEnabled(), ChatColor.GREEN + plugin.getPrefix() + " Cuboid: " + (field.getCuboid() == null ? "Not Set" : "Set"), ChatColor.GREEN + plugin.getPrefix() + " Green Spawn: " + (field.getGreenSpawn() == null ? "Not Set" : "Set"), ChatColor.GREEN + plugin.getPrefix() + " Red Spawn: " + (field.getRedSpawn() == null ? "Not Set" : "Set"), ChatColor.GREEN + plugin.getPrefix() + " Spectators Spawn: " + (field.getSpectators() == null ? "Not Set" : "Set")});

        return true;
    }
}
