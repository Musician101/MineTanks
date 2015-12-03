package musician101.minetanks.spigot.commands.edit;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class ForceEndCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public ForceEndCommand(MineTanks plugin)
    {
        super(CommonCommands.FORCE_END_NAME, CommonCommands.FORCE_END_DESC, Arrays.asList(new CommandArgument("/" + CommonCommands.MT), new CommandArgument(CommonCommands.FORCE_END_NAME), new CommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)), 1, CommonPermissions.EDIT_PERM, false, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        BattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        field.endMatch(true);
        field.setInProgress(false);
        sender.sendMessage(ChatColor.GREEN + CommonMessages.MATCH_TERMINATED);
        return true;
    }
}
