package musician101.minetanks.sponge.command;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import musician101.common.java.minecraft.sponge.command.SpongeHelpCommand;
import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.sponge.command.edit.CreateSpongeCommand;
import musician101.minetanks.sponge.command.edit.EnableSpongeCommand;
import musician101.minetanks.sponge.command.edit.ForceEndSpongeCommand;
import musician101.minetanks.sponge.command.edit.GreenSpawnSpongeCommand;
import musician101.minetanks.sponge.command.edit.RedSpawnSpongeCommand;
import musician101.minetanks.sponge.command.edit.RegionSpongeCommand;
import musician101.minetanks.sponge.command.edit.RemoveSpongeCommand;
import musician101.minetanks.sponge.command.edit.SpectatorsSpongeCommand;
import musician101.minetanks.sponge.command.edit.StatusSpongeCommand;
import musician101.minetanks.sponge.command.participate.JoinSpongeCommand;
import musician101.minetanks.sponge.command.participate.LeaveSpongeCommand;
import musician101.minetanks.sponge.command.participate.SpectateSpongeCommand;
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
        super(CommonReference.ID, CommonReference.DESCRIPTION, Collections.singletonList(new SpongeCommandArgument(CommonCommands.MT_CMD)), 0, "", false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY), Arrays.asList(new JoinSpongeCommand(), new LeaveSpongeCommand(), new RemoveSpongeCommand(), new SpectateSpongeCommand(), new CreateSpongeCommand(), new RegionSpongeCommand(), new EnableSpongeCommand(), new ForceEndSpongeCommand(), new GreenSpawnSpongeCommand(), new RedSpawnSpongeCommand(), new SpectatorsSpongeCommand(), new StatusSpongeCommand()));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws CommandException
    {
        String[] args = splitArgs(arguments);
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(CommonCommands.HELP))
                return new SpongeHelpCommand(this, source).process(source, moveArguments(args));

            for (AbstractSpongeCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.process(source, moveArguments(args));
        }

        source.sendMessage(new SpongeHelpCommand(this, source).getUsage(source));
        return CommandResult.success();
    }
}
