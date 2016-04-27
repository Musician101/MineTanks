package io.musician101.minetanks.sponge.command.edit;

import io.musician101.common.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandUsage;
import io.musician101.minetanks.sponge.SpongeMineTanks;
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

public class EnableSpongeCommand extends AbstractSpongeCommand<SpongeMineTanks>
{
    public EnableSpongeCommand(SpongeMineTanks plugin)
    {
        super(plugin, CommonCommands.ENABLE_NAME, TextUtils.aquaText(CommonCommands.ENABLE_DESC), new SpongeCommandUsage(Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.ENABLE_NAME), new SpongeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new SpongeCommandPermissions(CommonPermissions.EDIT_PERM, false, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY)));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        if (!minArgsMet(source, args.length, TextUtils.redText(CommonMessages.NOT_ENOUGH_ARGS)))
            return CommandResult.empty();

        SpongeBattleField field = plugin.getFieldStorage().getField(args[1]);
        if (field == null)
        {
            source.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
            return CommandResult.empty();
        }

        if (field.isEnabled())
            field.setEnabled(false);
        else
            field.setEnabled(true);

        source.sendMessage(TextUtils.greenText(CommonMessages.fieldEnabled(field)));
        return CommandResult.success();
    }
}
