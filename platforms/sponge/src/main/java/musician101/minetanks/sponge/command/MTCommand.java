package musician101.minetanks.sponge.command;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import musician101.common.java.minecraft.sponge.command.SpongeHelpCommand;
import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.sponge.command.edit.CreateCommand;
import musician101.minetanks.sponge.command.edit.EnableCommand;
import musician101.minetanks.sponge.command.edit.ForceEndCommand;
import musician101.minetanks.sponge.command.edit.GreenSpawnCommand;
import musician101.minetanks.sponge.command.edit.RedSpawnCommand;
import musician101.minetanks.sponge.command.edit.RegionCommand;
import musician101.minetanks.sponge.command.edit.RemoveCommand;
import musician101.minetanks.sponge.command.edit.SpectatorsCommand;
import musician101.minetanks.sponge.command.edit.StatusCommand;
import musician101.minetanks.sponge.command.participate.JoinCommand;
import musician101.minetanks.sponge.command.participate.LeaveCommand;
import musician101.minetanks.sponge.command.participate.SpectateCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;

public class MTCommand extends AbstractSpongeCommand
{
    public MTCommand()
    {
        super(CommonReference.ID, CommonReference.DESCRIPTION, Collections.singletonList(new SpongeCommandArgument(CommonCommands.MT_CMD)), 0, "", false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY), Arrays.asList(new JoinCommand(), new LeaveCommand(), new RemoveCommand(), new SpectateCommand(), new CreateCommand(), new RegionCommand(), new EnableCommand(), new ForceEndCommand(), new GreenSpawnCommand(), new RedSpawnCommand(), new SpectatorsCommand(), new StatusCommand()));
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
