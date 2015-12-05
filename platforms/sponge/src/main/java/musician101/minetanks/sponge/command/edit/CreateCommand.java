package musician101.minetanks.sponge.command.edit;

import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.Constants;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class CreateCommand extends AbstractSpongeCommand
{
    public CreateCommand()
    {
        super("create", Texts.of(CommonCommands.CREATE_DESC), Arrays.asList(Texts.of("/mt"), Texts.of("create"), Texts.of("name")), 1, "minetanks.edit", false, MTUtils.buildText(CommonMessages.NO_PERMISSION, TextColors.RED), MTUtils.buildText(CommonMessages.PLAYER_ONLY, TextColors.RED));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        if (!minArgsMet(source, args.length, Texts.builder().append(Texts.of(SpongeMineTanks.getPrefix() + " Error: Field not specified.")).color(TextColors.RED).build()))
            return CommandResult.empty();

        if (!SpongeMineTanks.getFieldStorage().createField(args[0]))
        {
            source.sendMessage(MTUtils.buildText(CommonMessages.FIELD_DNE, TextColors.RED));
            return CommandResult.empty();
        }

        source.sendMessage(MTUtils.buildText(CommonMessages.FIELD_CREATED.replace(Constants.FIELD.toString(), args[0]), TextColors.GREEN), MTUtils.buildText(CommonMessages.CHECK_FIELD_STATUS, TextColors.GREEN));
        return CommandResult.success();
    }
}
