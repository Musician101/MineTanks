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
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SpectatorsCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public SpectatorsCommand(MineTanks plugin)
    {
        super(CommonCommands.SPECTATORS_NAME, CommonCommands.SPECTATORS_DESC, Arrays.asList(new CommandArgument("/" + CommonCommands.MT), new CommandArgument(CommonCommands.SPECTATORS_NAME), new CommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1, CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (minArgsMet(sender, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        Player player = (Player) sender;
        BattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
            return false;
        }

        Location loc = player.getLocation();
        if (field.getSpigotRegion() == null || !field.getSpigotRegion().isInRegion(loc))
        {
            player.sendMessage(ChatColor.RED + CommonMessages.LOCATION_NOT_IN_REGION);
            return false;
        }

        field.setSpectators(loc);
        player.sendMessage(ChatColor.GREEN + CommonMessages.SPECTATORS_SPAWN_SET);
        return true;
    }
}
