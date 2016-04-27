package io.musician101.minetanks.sponge.command.edit;

import io.musician101.common.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandUsage;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import io.musician101.common.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.common.java.minecraft.sponge.TextUtils;
import io.musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RemoveSpongeCommand extends AbstractSpongeCommand<SpongeMineTanks>
{
    public RemoveSpongeCommand(SpongeMineTanks plugin)
    {
        super(plugin, CommonCommands.REMOVE_NAME, TextUtils.aquaText(CommonCommands.REMOVE_DESC), new SpongeCommandUsage(Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.REMOVE_NAME), new SpongeCommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)), 1), new SpongeCommandPermissions(CommonPermissions.EDIT_PERM, false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY)));
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

        SpongeBattleFieldStorage fieldStorage = plugin.getFieldStorage();
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
