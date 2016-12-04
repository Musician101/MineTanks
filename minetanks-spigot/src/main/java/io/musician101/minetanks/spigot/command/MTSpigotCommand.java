package io.musician101.minetanks.spigot.command;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.command.edit.CreateSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.EnableSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.ForceEndSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.GreenSpawnSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.RedSpawnSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.RegionSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.RemoveSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.SpectatorsSpigotCommand;
import io.musician101.minetanks.spigot.command.edit.StatusSpigotCommand;
import io.musician101.minetanks.spigot.command.participate.JoinSpigotCommand;
import io.musician101.minetanks.spigot.command.participate.LeaveSpigotCommand;
import io.musician101.minetanks.spigot.command.participate.SpectateSpigotCommand;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandUsage;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotHelpCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;

public class MTSpigotCommand extends AbstractSpigotCommand
{
    public MTSpigotCommand()
    {
        super(CommonReference.ID, CommonReference.DESCRIPTION, new SpigotCommandUsage(Collections.singletonList(new SpigotCommandArgument(CommonCommands.MT_CMD))), new SpigotCommandPermissions("", false, "", ""), Arrays.asList(new JoinSpigotCommand(), new LeaveSpigotCommand(), new RemoveSpigotCommand(), new SpectateSpigotCommand(), new CreateSpigotCommand(), new RegionSpigotCommand(), new EnableSpigotCommand(), new ForceEndSpigotCommand(), new GreenSpawnSpigotCommand(), new RedSpawnSpigotCommand(), new SpectatorsSpigotCommand(), new StatusSpigotCommand()));
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(CommonCommands.HELP))
                return new SpigotHelpCommand<>(SpigotMineTanks.instance(), this).onCommand(sender, moveArguments(args));

            for (AbstractSpigotCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.onCommand(sender, moveArguments(args));
        }

        sender.sendMessage(new SpigotHelpCommand<>(SpigotMineTanks.instance(), this).getCommandHelpInfo());
        return true;
    }
}
