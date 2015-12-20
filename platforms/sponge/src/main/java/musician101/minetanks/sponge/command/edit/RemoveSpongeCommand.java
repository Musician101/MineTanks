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
import musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RemoveSpongeCommand extends AbstractSpongeCommand
{
    public RemoveSpongeCommand()
    {
        super(CommonCommands.REMOVE_NAME, CommonCommands.REMOVE_DESC, Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.REMOVE_NAME), new SpongeCommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)), 1, CommonPermissions.EDIT_PERM, false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY));
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

        SpongeBattleFieldStorage fieldStorage = SpongeMineTanks.getFieldStorage();
        SpongeBattleField field = fieldStorage.getField(args[0]);
        if (!fieldStorage.removeField(field.getName()))
        {
            source.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
            return CommandResult.empty();
        }

        source.sendMessage(TextUtils.greenText(CommonMessages.fieldDeleted(field)));
        return CommandResult.success();
    }
}
