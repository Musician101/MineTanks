package musician101.minetanks.sponge.command.edit;

import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.lib.SpongeReference.SpongeCommands;
import musician101.minetanks.sponge.lib.SpongeReference.SpongeMessages;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class EnableCommand extends AbstractSpongeCommand
{
    public EnableCommand()
    {
        super(CommonCommands.ENABLE_NAME, SpongeCommands.ENABLE_DESC, Arrays.asList(Texts.of("/mt"), SpongeCommands.ENABLE_NAME, Texts.of("field")), 2, CommonPermissions.EDIT_PERM, false, SpongeMessages.NO_PERMISSION, SpongeMessages.PLAYER_ONLY);
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        if (!minArgsMet(source, args.length, SpongeMessages.NOT_ENOUGH_ARGS))
            return CommandResult.empty();

        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(args[1]);
        if (field == null)
        {
            source.sendMessage(SpongeMessages.FIELD_DNE);
            return CommandResult.empty();
        }

        if (field.isEnabled())
            field.setEnabled(false);
        else
            field.setEnabled(true);

        source.sendMessage(SpongeMessages.fieldEnabled(field));
        return CommandResult.success();
    }
}
