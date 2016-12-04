package io.musician101.minetanks.sponge.command.participate;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.AbstractSpongeCommand;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandUsage;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class SpectateSpongeCommand extends AbstractSpongeCommand
{
    public SpectateSpongeCommand()
    {
        super(CommonCommands.SPECTATE_NAME, TextUtils.aquaText(CommonCommands.SPECTATORS_DESC), new SpongeCommandUsage(Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.SPECTATE_NAME), new SpongeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new SpongeCommandPermissions(CommonPermissions.PARTICIPATE_PERM, true, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY)));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        Player player = (Player) source;
        if (!minArgsMet(player, args.length, TextUtils.redText(CommonMessages.FIELD_NOT_SPECIFIED)))
            return CommandResult.empty();

        SpongeBattleField field = SpongeMineTanks.instance().getFieldStorage().getField(args[1]);
        if (field == null)
        {
            player.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
            return CommandResult.empty();
        }

        if (!field.isEnabled())
        {
            player.sendMessage(TextUtils.redText(CommonMessages.FIELD_DISABLED));
            return CommandResult.empty();
        }

        if (!field.isReady())
        {
            player.sendMessage(TextUtils.redText(CommonMessages.FIELD_NOT_READY));
            return CommandResult.empty();
        }

        field.addPlayer(player, MTTeam.SPECTATOR);
        return CommandResult.success();
    }
}
