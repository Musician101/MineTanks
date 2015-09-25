package musician101.minetanks.spigot.commands;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RedSpawnCommand extends AbstractSpigotCommand
{
    public RedSpawnCommand(MineTanks plugin)
    {
        super(plugin, "redspawn", "Set the red team's spawn point of the currently selected battlefield.", Arrays.asList("/mt", "redspawn", "<" + ChatColor.ITALIC + "field" + ChatColor.RESET + ">"), 1, "minetanks.edit", true);
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

        Player player = (Player) sender;
        BattleField field = plugin.getFieldStorage().getField(args[0]);
        Location loc = player.getLocation();
        if (field.getCuboid() == null || !field.getCuboid().isInCuboid(loc))
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: The location is not inside the field's region.");
            return false;
        }

        field.setRedSpawn(loc);
        player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Red Spawn point set.");
        return true;
    }
}
