package io.musician101.minetanks.sponge.command;

import io.musician101.common.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandUsage;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.command.edit.CreateSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.EnableSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.ForceEndSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.GreenSpawnSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.RegionSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.SpectatorsSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.StatusSpongeCommand;
import io.musician101.minetanks.sponge.command.participate.LeaveSpongeCommand;
import io.musician101.minetanks.sponge.command.participate.SpectateSpongeCommand;
import io.musician101.common.java.minecraft.sponge.TextUtils;
import io.musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import io.musician101.common.java.minecraft.sponge.command.SpongeHelpCommand;
import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.sponge.command.edit.RedSpawnSpongeCommand;
import io.musician101.minetanks.sponge.command.edit.RemoveSpongeCommand;
import io.musician101.minetanks.sponge.command.participate.JoinSpongeCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;

public class MTSpongeCommand extends AbstractSpongeCommand<SpongeMineTanks>
{
    public MTSpongeCommand(SpongeMineTanks plugin)
    {
        super(plugin, CommonReference.ID, TextUtils.aquaText(CommonReference.DESCRIPTION), new SpongeCommandUsage(Collections.singletonList(new SpongeCommandArgument(CommonCommands.MT_CMD))), new SpongeCommandPermissions("", false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY)), Arrays.asList(new JoinSpongeCommand(plugin), new LeaveSpongeCommand(plugin), new RemoveSpongeCommand(plugin), new SpectateSpongeCommand(plugin), new CreateSpongeCommand(plugin), new RegionSpongeCommand(plugin), new EnableSpongeCommand(plugin), new ForceEndSpongeCommand(plugin), new GreenSpawnSpongeCommand(plugin), new RedSpawnSpongeCommand(plugin), new SpectatorsSpongeCommand(plugin), new StatusSpongeCommand(plugin)));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws CommandException
    {
        String[] args = splitArgs(arguments);
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(CommonCommands.HELP))
                return new SpongeHelpCommand<>(plugin, this, source).process(source, moveArguments(args));

            for (AbstractSpongeCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.process(source, moveArguments(args));
        }

        source.sendMessage(new SpongeHelpCommand<>(plugin, this, source).getUsage(source));
        return CommandResult.success();
    }
}
