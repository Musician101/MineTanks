package io.musician101.minetanks.forge.command.participate;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;







import java.util.Arrays;

public class JoinForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public JoinForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.JOIN_NAME, CommonCommands.JOIN_DESC, new ForgeCommandUsage(Arrays.asList(new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.JOIN_NAME), new ForgeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new ForgeCommandPermissions(CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        ForgeBattleField field = ForgeMineTanks.instance.getFieldStorage().getField(args[0]);
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
