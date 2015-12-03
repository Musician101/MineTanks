package musician101.minetanks.spigot.commands.edit;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class EnableCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public EnableCommand(MineTanks plugin)
    {
        super(CommonCommands.ENABLE_NAME, CommonCommands.ENABLE_DESC, Arrays.asList(new CommandArgument("/" + CommonCommands.MT), new CommandArgument(CommonCommands.ENABLE_NAME), new CommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)), 2, CommonPermissions.EDIT_PERM, false, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (!minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        BattleField field = plugin.getFieldStorage().getField(args[1]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        if (field.isEnabled())
            field.setEnabled(false);
        else
            field.setEnabled(true);

        sender.sendMessage(SpigotReference.battleField(SpigotReference.enabled(CommonMessages.FIELD_ENABLED, field), field));
        return true;
    }
}
