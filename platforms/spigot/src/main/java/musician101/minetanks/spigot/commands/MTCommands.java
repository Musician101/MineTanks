package musician101.minetanks.spigot.commands;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.commands.edit.CreateCommand;
import musician101.minetanks.spigot.commands.edit.RegionCommand;
import musician101.minetanks.spigot.commands.edit.EnableCommand;
import musician101.minetanks.spigot.commands.edit.ForceEndCommand;
import musician101.minetanks.spigot.commands.edit.GreenSpawnCommand;
import musician101.minetanks.spigot.commands.edit.RedSpawnCommand;
import musician101.minetanks.spigot.commands.edit.RemoveCommand;
import musician101.minetanks.spigot.commands.edit.SpectatorsCommand;
import musician101.minetanks.spigot.commands.edit.StatusCommand;
import musician101.minetanks.spigot.commands.participate.JoinCommand;
import musician101.minetanks.spigot.commands.participate.LeaveCommand;
import musician101.minetanks.spigot.commands.participate.SpectateCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;

public class MTCommands extends AbstractSpigotCommand
{
    MineTanks plugin;

    public MTCommands(MineTanks plugin)
    {
        super(CommonReference.ID, CommonReference.DESCRIPTION, Collections.singletonList(new SpigotCommandArgument("/" + CommonReference.ID)), 0, "", false, "", "", Arrays.asList(new JoinCommand(plugin), new LeaveCommand(plugin), new RemoveCommand(plugin), new SpectateCommand(plugin), new CreateCommand(plugin), new RegionCommand(plugin), new EnableCommand(plugin), new ForceEndCommand(plugin), new GreenSpawnCommand(plugin), new RedSpawnCommand(plugin), new SpectatorsCommand(plugin), new StatusCommand(plugin)));
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(CommonCommands.HELP))
                return new HelpCommand(plugin, this).onCommand(sender, moveArguments(args));

            for (AbstractSpigotCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.onCommand(sender, moveArguments(args));
        }

        sender.sendMessage(new HelpCommand(plugin, this).getCommandHelpInfo());
        return true;
    }
}
