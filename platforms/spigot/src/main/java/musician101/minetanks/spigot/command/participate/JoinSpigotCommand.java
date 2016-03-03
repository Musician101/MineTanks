package musician101.minetanks.spigot.command.participate;

import musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class JoinSpigotCommand extends AbstractSpigotCommand<SpigotMineTanks>
{
    public JoinSpigotCommand(SpigotMineTanks plugin)
    {
        super(plugin, CommonCommands.JOIN_NAME, CommonCommands.JOIN_DESC, Arrays.asList(new SpigotCommandArgument(CommonCommands.MT_CMD), new SpigotCommandArgument(CommonCommands.JOIN_NAME), new SpigotCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1, CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
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

        field.addPlayer(player.getUniqueId(), MTTeam.UNASSIGNED);
        plugin.getMenuHandler().openTankMenu(player);
        return true;
    }
}
