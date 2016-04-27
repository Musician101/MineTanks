package io.musician101.minetanks.spigot.command.edit;

import io.musician101.common.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandUsage;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.util.SpigotRegion;
import io.musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RegionSpigotCommand extends AbstractSpigotCommand<SpigotMineTanks>
{
    public RegionSpigotCommand(SpigotMineTanks plugin)
    {
        super(plugin, CommonCommands.REGION_NAME, CommonCommands.REGION_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.REGION_NAME), new SpigotCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED), new SpigotCommandArgument(CommonCommands.RADIUS, Syntax.REPLACE, Syntax.REQUIRED)), 2), new SpigotCommandPermissions(CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.NOT_ENOUGH_ARGS))
            return false;

        Player player = (Player) sender;
        SpigotBattleField field = plugin.getFieldStorage().getField(args[0]);
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
                player.sendMessage(ChatColor.RED + CommonMessages.notANumber(args[1]));
                return false;
            }

            field.setRegion(SpigotRegion.createFromLocationRadius(player.getLocation(), radius));
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
                player.sendMessage(ChatColor.RED + CommonMessages.NOT_A_NUMBER_MULTI);
                return false;
            }

            field.setRegion(SpigotRegion.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
            player.sendMessage(ChatColor.GREEN + CommonMessages.REGION_SET);
            return true;
        }

        player.sendMessage(ChatColor.RED + CommonMessages.NOT_ENOUGH_ARGS);
        return false;
    }
}
