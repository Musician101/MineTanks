package musician101.minetanks.sponge.command.edit;

import musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class ForceEndCommand extends AbstractSpongeCommand
{
    public ForceEndCommand()
    {
        super(CommonCommands.FORCE_END_NAME, CommonCommands.FORCE_END_DESC, Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.FORCE_END_NAME), new SpongeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1, CommonPermissions.EDIT_PERM, false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        if (minArgsMet(source, args.length, TextUtils.redText(CommonMessages.FIELD_NOT_SPECIFIED)))
            return CommandResult.empty();

        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            source.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
            return CommandResult.empty();
        }

        field.endMatch(true);
        field.setInProgress(false);
        source.sendMessage(TextUtils.greenText(CommonMessages.MATCH_TERMINATED));
        return CommandResult.success();
    }
}
