package musician101.minetanks.spigot.commands.participate;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.common.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SpectateCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public SpectateCommand(MineTanks plugin)
    {
        super("spectate", "Spectate the selected battlefield.", Arrays.asList(new CommandArgument("/mt"), new CommandArgument("spectate"), new CommandArgument("field", Syntax.REPLACE, Syntax.REQUIRED)), 1, "minetanks.participate", true, ChatColor.RED + "No Permission", ChatColor.RED + "Player Only");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        Player player = (Player) sender;
        if (!minArgsMet(player, args.length, ChatColor.RED + plugin.getPrefix() + " Error: Field not specified."))
            return false;

        BattleField field = plugin.getFieldStorage().getField(args[1]);
        if (field == null)
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
            return false;
        }

        if (!field.isReady())
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, this field is not ready.");
            return false;
        }

        field.addPlayer(player.getUniqueId(), MTTeam.SPECTATOR);
        return true;
    }
}
