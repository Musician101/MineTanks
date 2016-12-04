package io.musician101.minetanks.forge.command.edit;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.minetanks.forge.util.ForgeRegion;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;







import java.util.Arrays;

public class RegionForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public RegionForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.REGION_NAME, CommonCommands.REGION_DESC, new ForgeCommandUsage(Arrays.asList(new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.REGION_NAME), new ForgeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED), new ForgeCommandArgument(CommonCommands.RADIUS, Syntax.REPLACE, Syntax.REQUIRED)), 2), new ForgeCommandPermissions(CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.NOT_ENOUGH_ARGS))
            return false;

        Player player = (Player) sender;
        ForgeBattleField field = ForgeMineTanks.instance.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        if (args.length >= 2 && args.length < 4)
        {
            int radius;
            try
            {
                radius = Integer.valueOf(args[1]);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(ChatColor.RED + CommonMessages.notANumber(args[1]));
                return false;
            }

            field.setRegion(ForgeRegion.createFromLocationRadius(player.getLocation(), radius));
            player.sendMessage(ChatColor.GREEN + CommonMessages.REGION_SET);
            return true;
        }
        else if (args.length >= 4)
        {
            int xRadius;
            int yRadius;
            int zRadius;
            try
            {
                xRadius = Integer.valueOf(args[1]);
                yRadius = Integer.valueOf(args[2]);
                zRadius = Integer.valueOf(args[3]);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(ChatColor.RED + CommonMessages.NOT_A_NUMBER_MULTI);
                return false;
            }

            field.setRegion(ForgeRegion.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
            player.sendMessage(ChatColor.GREEN + CommonMessages.REGION_SET);
            return true;
        }

        player.sendMessage(ChatColor.RED + CommonMessages.NOT_ENOUGH_ARGS);
        return false;
    }
}
