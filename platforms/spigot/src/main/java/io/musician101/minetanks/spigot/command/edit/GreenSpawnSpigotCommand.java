package io.musician101.minetanks.spigot.command.edit;

import io.musician101.common.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandUsage;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GreenSpawnSpigotCommand extends AbstractSpigotCommand<SpigotMineTanks>
{
    public GreenSpawnSpigotCommand(SpigotMineTanks plugin)
    {
        super(plugin, CommonCommands.GREEN_SPAWN_NAME, CommonCommands.GREEN_SPAWN_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.GREEN_SPAWN_NAME), new SpigotCommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)), 1), new SpigotCommandPermissions(CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        Player player = (Player) sender;
        SpigotBattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        Location loc = player.getLocation();
        if (field.getRegion() == null || !field.getRegion().isInRegion(loc))
        {
            player.sendMessage(ChatColor.RED + CommonMessages.LOCATION_NOT_IN_REGION);
            return false;
        }

        field.setGreenSpawn(loc);
        player.sendMessage(ChatColor.GREEN + CommonMessages.GREEN_SPAWN_SET);
        return true;
    }
}
