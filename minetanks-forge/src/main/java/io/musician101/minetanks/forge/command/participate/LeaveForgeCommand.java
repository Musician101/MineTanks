package io.musician101.minetanks.forge.command.participate;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.minetanks.forge.battlefield.ForgeBattleFieldStorage;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;







import java.util.Arrays;

public class LeaveForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public LeaveForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.LEAVE_NAME, CommonCommands.LEAVE_DESC, new ForgeCommandUsage(Arrays.asList(new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.LEAVE_NAME))), new ForgeCommandPermissions(CommonPermissions.PARTICIPATE_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        ForgeBattleFieldStorage storage = ForgeMineTanks.instance.getFieldStorage();
        for (String name : storage.getFields().keySet())
        {
            ForgeBattleField field = storage.getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                if (!ForgeMineTanks.instance.getFieldStorage().canPlayerExit(player.getUniqueId()))
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
