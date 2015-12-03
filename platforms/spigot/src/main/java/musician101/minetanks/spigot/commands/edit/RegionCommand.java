package musician101.minetanks.spigot.commands.edit;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.util.SpigotRegion;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RegionCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public RegionCommand(MineTanks plugin)
    {
        super(CommonCommands.REGION_NAME, CommonCommands.REGION_DESC, Arrays.asList(new CommandArgument("/" + CommonCommands.MT), new CommandArgument(CommonCommands.REGION_NAME), new CommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED), new CommandArgument(CommonCommands.RADIUS, Syntax.REPLACE, Syntax.REQUIRED)), 2, CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        BattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        if (args.length >= 2 && args.length < 4)
        {
            int radius;
            try
            {
                radius = Integer.valueOf(args[1]);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(ChatColor.RED + SpigotReference.string(CommonMessages.NOT_A_NUMBER, args[1]));
                return false;
            }

            field.setSpigotRegion(SpigotRegion.createFromLocationRadius(player.getLocation(), radius));
            player.sendMessage(ChatColor.GREEN + CommonMessages.REGION_SET);
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
                player.sendMessage(ChatColor.RED + CommonMessages.NOT_A_NUMBER_2);
                return false;
            }

            field.setSpigotRegion(SpigotRegion.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
            player.sendMessage(ChatColor.GREEN + CommonMessages.REGION_SET);
            return true;
        }

        player.sendMessage(ChatColor.RED + CommonMessages.NOT_ENOUGH_ARGS);
        return false;
    }
}
