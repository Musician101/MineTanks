package musician101.minetanks.spigot.command.participate;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaveSpigotCommand extends AbstractSpigotCommand<SpigotMineTanks>
{
    public LeaveSpigotCommand(SpigotMineTanks plugin)
    {
        super(plugin, CommonCommands.LEAVE_NAME, CommonCommands.LEAVE_DESC, Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.LEAVE_NAME)), 0, CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                if (!plugin.getFieldStorage().canPlayerExit(player.getUniqueId()))
                {
                    player.sendMessage(ChatColor.RED + CommonMessages.NO_PERMISSION);
                    return false;
                }

                player.sendMessage(ChatColor.GREEN + CommonMessages.LEFT_FIELD);
                field.removePlayer(player.getUniqueId());
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + CommonMessages.NOT_IN_A_FIELD);
        return false;
    }
}
