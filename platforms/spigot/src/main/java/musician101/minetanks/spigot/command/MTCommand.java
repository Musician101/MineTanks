package musician101.minetanks.spigot.command;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.common.java.minecraft.spigot.command.SpigotHelpCommand;
import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.command.edit.CreateCommand;
import musician101.minetanks.spigot.command.edit.RegionCommand;
import musician101.minetanks.spigot.command.edit.EnableCommand;
import musician101.minetanks.spigot.command.edit.ForceEndCommand;
import musician101.minetanks.spigot.command.edit.GreenSpawnCommand;
import musician101.minetanks.spigot.command.edit.RedSpawnCommand;
import musician101.minetanks.spigot.command.edit.RemoveCommand;
import musician101.minetanks.spigot.command.edit.SpectatorsCommand;
import musician101.minetanks.spigot.command.edit.StatusCommand;
import musician101.minetanks.spigot.command.participate.JoinCommand;
import musician101.minetanks.spigot.command.participate.LeaveCommand;
import musician101.minetanks.spigot.command.participate.SpectateCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;

public class MTCommand extends AbstractSpigotCommand
{
    public MTCommand(SpigotMineTanks plugin)
    {
        super(CommonReference.ID, CommonReference.DESCRIPTION, Collections.singletonList(new SpigotCommandArgument(CommonCommands.MT_CMD)), 0, "", false, "", "", Arrays.asList(new JoinCommand(plugin), new LeaveCommand(plugin), new RemoveCommand(plugin), new SpectateCommand(plugin), new CreateCommand(plugin), new RegionCommand(plugin), new EnableCommand(plugin), new ForceEndCommand(plugin), new GreenSpawnCommand(plugin), new RedSpawnCommand(plugin), new SpectatorsCommand(plugin), new StatusCommand(plugin)));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(CommonCommands.HELP))
                return new SpigotHelpCommand(this).onCommand(sender, moveArguments(args));

            for (AbstractSpigotCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.onCommand(sender, moveArguments(args));
        }

        sender.sendMessage(new SpigotHelpCommand(this).getCommandHelpInfo());
        return true;
    }
}
