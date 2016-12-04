package io.musician101.minetanks.forge.command.edit;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleFieldStorage;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;







import java.util.Arrays;

public class CreateForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public CreateForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.CREATE_NAME, CommonCommands.CREATE_DESC, new ForgeCommandUsage(Arrays.asList(new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.CREATE_NAME), new ForgeCommandArgument(CommonCommands.NAME, Syntax.REPLACE, Syntax.REQUIRED)), 1), new ForgeCommandPermissions(CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        ForgeBattleFieldStorage fieldStorage = ForgeMineTanks.instance.getFieldStorage();
        if (!fieldStorage.createField(args[0]))
        {
            player.sendMessage(ChatColor.RED + CommonMessages.FIELD_EXISTS);
            return false;
        }

        player.sendMessage(new String[]{ChatColor.GREEN + CommonMessages.fieldCreated(fieldStorage.getField(args[0])), ChatColor.GREEN + CommonMessages.FIELD_CREATED_STATUS});
        return true;
    }
}
