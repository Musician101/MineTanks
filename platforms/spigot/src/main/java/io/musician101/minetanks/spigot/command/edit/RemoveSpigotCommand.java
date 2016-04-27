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
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class RemoveSpigotCommand extends AbstractSpigotCommand<SpigotMineTanks>
{
    public RemoveSpigotCommand(SpigotMineTanks plugin)
    {
        super(plugin, CommonCommands.REMOVE_NAME, CommonCommands.REMOVE_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.REMOVE_NAME), new SpigotCommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)), 1), new SpigotCommandPermissions(CommonPermissions.EDIT_PERM, false, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        SpigotBattleField field = plugin.getFieldStorage().getField(args[0]);
        if (!plugin.getFieldStorage().removeField(field.getName()))
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        sender.sendMessage(ChatColor.GREEN + CommonMessages.fieldDeleted(field));
        return true;
    }
}
