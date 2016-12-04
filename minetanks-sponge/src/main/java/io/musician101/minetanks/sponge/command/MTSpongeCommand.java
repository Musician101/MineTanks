package io.musician101.minetanks.sponge.command;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.command.edit.CreateSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.EnableSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.ForceEndSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.GreenSpawnSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.RedSpawnSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.RegionSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.RemoveSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.SpectatorsSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.StatusSpongeCommand;
import io.musician101.minetanks.sponge.command.participate.JoinSpongeCommand;
import io.musician101.minetanks.sponge.command.participate.LeaveSpongeCommand;
import io.musician101.minetanks.sponge.command.participate.SpectateSpongeCommand;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.AbstractSpongeCommand;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandUsage;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeHelpCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;


public class MTSpongeCommand extends AbstractSpongeCommand
{

    public MTSpongeCommand()
    {
        super(CommonReference.ID, TextUtils.aquaText(CommonReference.DESCRIPTION), new SpongeCommandUsage(Collections.singletonList(new SpongeCommandArgument(CommonCommands.MT_CMD))), new SpongeCommandPermissions("", false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY)), Arrays.asList(new JoinSpongeCommand(), new LeaveSpongeCommand(), new RemoveSpongeCommand(), new SpectateSpongeCommand(), new CreateSpongeCommand(), new RegionSpongeCommand(), new EnableSpongeCommand(), new ForceEndSpongeCommand(), new GreenSpawnSpongeCommand(), new RedSpawnSpongeCommand(), new SpectatorsSpongeCommand(), new StatusSpongeCommand()));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws CommandException
    {
        String[] args = splitArgs(arguments);
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(CommonCommands.HELP))
                return new SpongeHelpCommand(this, source, SpongeMineTanks.getPluginContainer()).process(source, moveArguments(args));

            for (AbstractSpongeCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.process(source, moveArguments(args));
        }

        source.sendMessage(new SpongeHelpCommand(this, source, SpongeMineTanks.getPluginContainer()).getUsage(source));
        return CommandResult.success();
    }
}
