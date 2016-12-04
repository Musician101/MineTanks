package io.musician101.minetanks.forge.command.edit;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;




import org.bukkit.Location;



import java.util.Arrays;

public class SpectatorsForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public SpectatorsForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.SPECTATORS_NAME, CommonCommands.SPECTATORS_DESC, new ForgeCommandUsage(Arrays.asList(new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.SPECTATORS_NAME), new ForgeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new ForgeCommandPermissions(CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        Player player = (Player) sender;
        ForgeBattleField field = ForgeMineTanks.instance.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        Location loc = player.getLocation();
        if (field.getRegion() == null || !field.getRegion().isInRegion(loc))
        {
            player.sendMessage(ChatColor.RED + CommonMessages.LOCATION_NOT_IN_REGION);
            return false;
        }

        field.setSpectators(loc);
        player.sendMessage(ChatColor.GREEN + CommonMessages.SPECTATORS_SPAWN_SET);
        return true;
    }
}
