package musician101.minetanks.spigot.commands.participate;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaveCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public LeaveCommand(MineTanks plugin)
    {
        super(CommonCommands.LEAVE_NAME, CommonCommands.LEAVE_DESC, Arrays.asList(new CommandArgument("/" + CommonCommands.MT), new CommandArgument(CommonCommands.LEAVE_NAME)), 0, CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (plugin.getFieldStorage().canPlayerExit(player.getUniqueId()))
        {
            player.sendMessage(ChatColor.RED + CommonMessages.NO_PERMISSION);
            return false;
        }

        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            BattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayer(player.getUniqueId()) != null)
            {
                player.sendMessage(ChatColor.GREEN + CommonMessages.LEFT_FIELD);
                field.removePlayer(player.getUniqueId());
                return true;
            }
        }

        //TODO need to check if the player is in a field or not first.
        player.sendMessage(ChatColor.RED + CommonMessages.NOT_IN_A_FIELD);
        return false;
    }
}
