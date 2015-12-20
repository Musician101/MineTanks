package musician101.minetanks.spigot.command;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.common.java.minecraft.spigot.command.SpigotHelpCommand;
import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.command.edit.CreateSpigotCommand;
import musician101.minetanks.spigot.command.edit.RegionSpigotCommand;
import musician101.minetanks.spigot.command.edit.EnableSpigotCommand;
import musician101.minetanks.spigot.command.edit.ForceEndSpigotCommand;
import musician101.minetanks.spigot.command.edit.GreenSpawnSpigotCommand;
import musician101.minetanks.spigot.command.edit.RedSpawnSpigotCommand;
import musician101.minetanks.spigot.command.edit.RemoveSpigotCommand;
import musician101.minetanks.spigot.command.edit.SpectatorsSpigotCommand;
import musician101.minetanks.spigot.command.edit.StatusSpigotCommand;
import musician101.minetanks.spigot.command.participate.JoinSpigotCommand;
import musician101.minetanks.spigot.command.participate.LeaveSpigotCommand;
import musician101.minetanks.spigot.command.participate.SpectateSpigotCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;

public class MTSpigotCommand extends AbstractSpigotCommand
{
    public MTSpigotCommand(SpigotMineTanks plugin)
    {
        super(CommonReference.ID, CommonReference.DESCRIPTION, Collections.singletonList(new SpigotCommandArgument(CommonCommands.MT_CMD)), 0, "", false, "", "", Arrays.asList(new JoinSpigotCommand(plugin), new LeaveSpigotCommand(plugin), new RemoveSpigotCommand(plugin), new SpectateSpigotCommand(plugin), new CreateSpigotCommand(plugin), new RegionSpigotCommand(plugin), new EnableSpigotCommand(plugin), new ForceEndSpigotCommand(plugin), new GreenSpawnSpigotCommand(plugin), new RedSpawnSpigotCommand(plugin), new SpectatorsSpigotCommand(plugin), new StatusSpigotCommand(plugin)));
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
