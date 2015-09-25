package musician101.minetanks.spigot.commands;

import musician101.minetanks.spigot.MineTanks;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;

public class MTCommands extends AbstractSpigotCommand
{
    public MTCommands(MineTanks plugin)
    {
        super(plugin, "minetanks", "Minecraft PvP plugin influenced by Wargaming's World of Tanks.", Collections.singletonList("/minetanks"), 0, "minetanks", false, Arrays.asList(new JoinCommand(plugin), new LeaveCommand(plugin), new RemoveCommand(plugin), new SpectateCommand(plugin), new CreateCommand(plugin), new CuboidCommand(plugin), new EnableCommand(plugin), new ForceEndCommand(plugin), new GreenSpawnCommand(plugin), new RedSpawnCommand(plugin), new SpectatorsCommand(plugin), new StatusCommand(plugin)));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase("help"))
                return new HelpCommand(plugin, this).onCommand(sender, moveArguments(args));
            
            for (AbstractSpigotCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.onCommand(sender, moveArguments(args));
        }

        sender.sendMessage(new HelpCommand(plugin, this).getCommandHelpInfo());
        return true;
    }

}
