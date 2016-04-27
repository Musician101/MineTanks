package io.musician101.minetanks.spigot.command.edit;

import io.musician101.common.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandUsage;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleFieldStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CreateSpigotCommand extends AbstractSpigotCommand<SpigotMineTanks>
{
    public CreateSpigotCommand(SpigotMineTanks plugin)
    {
        super(plugin, CommonCommands.CREATE_NAME, CommonCommands.CREATE_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.CREATE_NAME), new SpigotCommandArgument(CommonCommands.NAME, Syntax.REPLACE, Syntax.REQUIRED)), 1), new SpigotCommandPermissions(CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        SpigotBattleFieldStorage fieldStorage = plugin.getFieldStorage();
        if (!fieldStorage.createField(args[0]))
        {
            player.sendMessage(ChatColor.RED + CommonMessages.FIELD_EXISTS);
            return false;
        }

        player.sendMessage(new String[]{ChatColor.GREEN + CommonMessages.fieldCreated(fieldStorage.getField(args[0])), ChatColor.GREEN + CommonMessages.FIELD_CREATED_STATUS});
        return true;
    }
}
