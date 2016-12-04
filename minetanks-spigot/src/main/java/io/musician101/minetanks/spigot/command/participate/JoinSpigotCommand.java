package io.musician101.minetanks.spigot.command.participate;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.menu.MainSelectionMenu;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandUsage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class JoinSpigotCommand extends AbstractSpigotCommand
{
    public JoinSpigotCommand()
    {
        super(CommonCommands.JOIN_NAME, CommonCommands.JOIN_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.JOIN_NAME), new SpigotCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new SpigotCommandPermissions(CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        SpigotBattleField field = SpigotMineTanks.instance().getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        if (!field.isEnabled())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.FIELD_DISABLED);
            return false;
        }

        if (!field.isReady())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.FIELD_NOT_READY);
            return false;
        }

        if (field.inProgress())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.MATCH_IN_PROGRESS);
            return false;
        }

        field.addPlayer(player, MTTeam.UNASSIGNED);
        new MainSelectionMenu(field, player);
        return true;
    }
}
