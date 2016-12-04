package io.musician101.minetanks.forge.command.edit;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;






import java.util.Arrays;

public class RemoveForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public RemoveForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.REMOVE_NAME, CommonCommands.REMOVE_DESC, new ForgeCommandUsage(Arrays.asList(new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.REMOVE_NAME), new ForgeCommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)), 1), new ForgeCommandPermissions(CommonPermissions.EDIT_PERM, false, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        ForgeBattleField field = ForgeMineTanks.instance.getFieldStorage().getField(args[0]);
        if (!ForgeMineTanks.instance.getFieldStorage().removeField(field.getName()))
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        sender.sendMessage(ChatColor.GREEN + CommonMessages.fieldDeleted(field));
        return true;
    }
}
