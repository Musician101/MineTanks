package musician101.minetanks.spigot.commands;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.util.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CuboidCommand extends AbstractSpigotCommand
{
    public CuboidCommand(MineTanks plugin)
    {
        super(plugin, "cuboid", "Set the cuboid region of the currently selected battlefield.", Arrays.asList("/mt", "cuboid", "<" + ChatColor.ITALIC + "field" + ChatColor.RESET + ">", "<" + ChatColor.ITALIC + "radius | xradius yradius zradius" + ChatColor.RESET + ">"), 2, "minetanks.edit", true);
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        BattleField field = plugin.getFieldStorage().getField(args[0]);
        if (args.length >= 2 && args.length < 4)
        {
            int radius;
            try
            {
                radius = Integer.valueOf(args[1]);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: " + args[1] + " is not a number.");
                return false;
            }

            field.setCuboid(Cuboid.createFromLocationRadius(player.getLocation(), radius));
            player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Cuboid set.");
            return true;
        }
        else if (args.length >= 4)
        {
            int xRadius;
            int yRadius;
            int zRadius;
            try
            {
                xRadius = Integer.valueOf(args[1]);
                yRadius = Integer.valueOf(args[2]);
                zRadius = Integer.valueOf(args[3]);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: One or more of the inputted radii is not a number.");
                return false;
            }

            field.setCuboid(Cuboid.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
            player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Cuboid set.");
            return true;
        }

        player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: Not enough args.");
        return false;
    }
}
