package io.musician101.minetanks.spigot.command.participate;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleFieldStorage;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandUsage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaveSpigotCommand extends AbstractSpigotCommand
{
    public LeaveSpigotCommand()
    {
        super(CommonCommands.LEAVE_NAME, CommonCommands.LEAVE_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.LEAVE_NAME))), new SpigotCommandPermissions(CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        SpigotBattleFieldStorage storage = SpigotMineTanks.instance().getFieldStorage();
        for (String name : storage.getFields().keySet())
        {
            SpigotBattleField field = storage.getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                if (!SpigotMineTanks.instance().getFieldStorage().canPlayerExit(player.getUniqueId()))
                {
                    player.sendMessage(ChatColor.RED + CommonMessages.NO_PERMISSION);
                    return false;
                }

                player.sendMessage(ChatColor.GREEN + CommonMessages.LEFT_FIELD);
                field.removePlayer(player);
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + CommonMessages.NOT_IN_A_FIELD);
        return false;
    }
}
