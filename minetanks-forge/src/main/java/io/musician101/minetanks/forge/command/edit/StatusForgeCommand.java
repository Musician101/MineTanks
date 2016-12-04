package io.musician101.minetanks.forge.command.edit;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;






import java.util.Arrays;

public class StatusForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public StatusForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.STATUS_NAME, CommonCommands.STATUS_DESC, new ForgeCommandUsage(Arrays.asList(new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.STATUS_NAME), new ForgeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new ForgeCommandPermissions(CommonPermissions.EDIT_PERM, false, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        ForgeBattleField field = ForgeMineTanks.instance.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        sender.sendMessage(new String[]{ChatColor.GREEN + CommonMessages.statusOfField(field),
                ChatColor.GREEN + CommonMessages.statusOfFieldEnabled(field),
                ChatColor.GREEN + CommonMessages.statusOfFieldRegion(field),
                ChatColor.GREEN + CommonMessages.statusOfFieldGreenSpawn(field),
                ChatColor.GREEN + CommonMessages.statusOfFieldRedSpawn(field),
                ChatColor.GREEN + CommonMessages.statusOfFieldSpectatorsSpawn(field)});

        return true;
    }
}
