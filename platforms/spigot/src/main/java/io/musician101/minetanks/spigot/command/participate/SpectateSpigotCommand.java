package io.musician101.minetanks.spigot.command.participate;

import io.musician101.common.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandUsage;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SpectateSpigotCommand extends AbstractSpigotCommand<SpigotMineTanks>
{
    public SpectateSpigotCommand(SpigotMineTanks plugin)
    {
        super(plugin, CommonCommands.SPECTATE_NAME, CommonCommands.SPECTATE_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.SPECTATE_NAME), new SpigotCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new SpigotCommandPermissions(CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        SpigotBattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            player.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        if (!field.isReady())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.FIELD_NOT_READY);
            return false;
        }

        field.addPlayer(player.getUniqueId(), MTTeam.SPECTATOR);
        return true;
    }
}
