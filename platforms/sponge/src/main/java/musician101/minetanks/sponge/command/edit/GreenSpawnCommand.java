package musician101.minetanks.sponge.command.edit;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.CommandArgument;
import musician101.common.java.minecraft.spigot.command.CommandArgument.Syntax;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GreenSpawnCommand extends AbstractSpigotCommand
{
    MineTanks plugin;

    public GreenSpawnCommand(MineTanks plugin)
    {
        super("greenspawn", "Set the green team's spawn point of the currently selected battlefield.", Arrays.asList(new CommandArgument("/mt"), new CommandArgument("greenspawn"), new CommandArgument("field", Syntax.REQUIRED, Syntax.REPLACE)), 1, "minetanks.edit", true, ChatColor.RED + "No Permission", ChatColor.RED + "Player Only");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        if (minArgsMet(sender, args.length, ChatColor.RED + plugin.getPrefix() + " Error: Field not specified."))
            return false;

        Player player = (Player) sender;
        BattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            sender.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
            return false;
        }

        Location loc = player.getLocation();
        if (field.getSpigotRegion() == null || !field.getSpigotRegion().isInRegion(loc))
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: The location is not inside the field's region.");
            return false;
        }

        field.setGreenSpawn(loc);
        player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Green Spawn point set.");
        return true;
    }
}