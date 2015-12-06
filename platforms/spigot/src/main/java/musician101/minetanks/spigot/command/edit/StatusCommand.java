package musician101.minetanks.spigot.command.edit;

import musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


import java.util.Arrays;

public class StatusCommand extends AbstractSpigotCommand
{
    private final SpigotMineTanks plugin;

    public StatusCommand(SpigotMineTanks plugin)
    {
        super(CommonCommands.STATUS_NAME, CommonCommands.STATUS_DESC, Arrays.asList(new SpigotCommandArgument("/" + CommonCommands.MT), new SpigotCommandArgument(CommonCommands.STATUS_NAME), new SpigotCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1, CommonPermissions.EDIT_PERM, false, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        SpigotBattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        sender.sendMessage(new String[]{ChatColor.GREEN + SpigotReference.battleField(CommonMessages.STATUS_OF_FIELD, field),
                ChatColor.GREEN + SpigotReference.enabled(CommonMessages.STATUS_OF_FIELD_ENABLED, field),
                ChatColor.GREEN + SpigotReference.set(CommonMessages.STATUS_OF_FIELD_REGION, field.getRegion() == null),
                ChatColor.GREEN + SpigotReference.set(CommonMessages.STATUS_OF_FIELD_GREEN, field.getGreenSpawn() == null),
                ChatColor.GREEN + SpigotReference.set(CommonMessages.STATUS_OF_FIELD_RED, field.getRedSpawn() == null),
                ChatColor.GREEN + SpigotReference.set(CommonMessages.STATUS_OF_FIELD_SPECTATORS, field.getSpectators() == null)});


        return true;
    }
}
