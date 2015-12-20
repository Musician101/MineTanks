package musician101.minetanks.sponge.command.edit;

import musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class CreateSpongeCommand extends AbstractSpongeCommand
{
    public CreateSpongeCommand()
    {
        super(CommonCommands.CREATE_NAME, CommonCommands.CREATE_DESC, Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.CREATE_NAME), new SpongeCommandArgument(CommonCommands.NAME, Syntax.REQUIRED, Syntax.REPLACE)), 1, CommonPermissions.EDIT_PERM, false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        if (!minArgsMet(source, args.length, TextUtils.redText(CommonMessages.FIELD_NOT_SPECIFIED)))
            return CommandResult.empty();

        SpongeBattleFieldStorage fieldStorage = SpongeMineTanks.getFieldStorage();
        if (!fieldStorage.createField(args[0]))
        {
            source.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
            return CommandResult.empty();
        }

        source.sendMessages(TextUtils.greenText(CommonMessages.fieldCreated(fieldStorage.getField(args[0]))), TextUtils.greenText(CommonMessages.CHECK_FIELD_STATUS));
        return CommandResult.success();
    }
}
