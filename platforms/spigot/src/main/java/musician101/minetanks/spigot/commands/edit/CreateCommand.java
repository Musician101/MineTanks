package musician101.minetanks.spigot.commands.edit;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.battlefield.BattleFieldStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CreateCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public CreateCommand(MineTanks plugin)
    {
        super(CommonCommands.CREATE, CommonCommands.CREATE, Arrays.asList(new CommandArgument("/" + CommonCommands.MT), new CommandArgument(CommonCommands.CREATE, new CommandArgument(CommonCommands.NAME, Syntax.REPLACE, Syntax.REQUIRED))), 1, CommonPermissions.EDIT_PERM, true, ChatColor.RED + CommonMessages.NO_PERMISSION, ChatColor.RED + CommonMessages.PLAYER_ONLY);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + CommonMessages.FIELD_NOT_SPECIFIED))
            return false;

        BattleFieldStorage fieldStorage = plugin.getFieldStorage();
        if (!fieldStorage.createField(args[0]))
        {
            player.sendMessage(ChatColor.RED + CommonMessages.FIELD_EXISTS);
            return false;
        }

        player.sendMessage(new String[]{ChatColor.GREEN + SpigotReference.battleField(CommonMessages.FIELD_CREATED, fieldStorage.getField(args[0])), ChatColor.GREEN + CommonMessages.FIELD_CREATED_2});
        return true;
    }
}
