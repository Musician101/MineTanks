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
import musician101.minetanks.sponge.util.SpongeRegion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RegionSpongeCommand extends AbstractSpongeCommand
{
    public RegionSpongeCommand()
    {
        super(CommonCommands.REGION_NAME, CommonCommands.REGION_DESC, Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.REGION_NAME), new SpongeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED), new SpongeCommandArgument(CommonCommands.RADIUS, Syntax.REPLACE, Syntax.REQUIRED)), 2, CommonPermissions.EDIT_PERM, true, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        Player player = (Player) source;
        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            source.sendMessage(Texts.builder(CommonMessages.FIELD_DNE).color(TextColors.RED).build());
            return CommandResult.empty();
        }

        if (args.length >= 2 && args.length < 4)
        {
            int radius;
            try
            {
                radius = Integer.valueOf(args[1]);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(TextUtils.redText(CommonMessages.notANumber(args[1])));
                return CommandResult.empty();
            }

            field.setRegion(SpongeRegion.createFromLocationRadius(player.getLocation(), radius));
            player.sendMessage(TextUtils.greenText(CommonMessages.REGION_SET));
            return CommandResult.success();
        }
        else if (args.length >= 4)
        {
            int xRadius;
            int yRadius;
            int zRadius;
            try
            {
                xRadius = Integer.valueOf(args[1]);
                yRadius = Integer.valueOf(args[2]);
                zRadius = Integer.valueOf(args[3]);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(TextUtils.redText(CommonMessages.NOT_A_NUMBER_MULTI));
                return CommandResult.empty();
            }

            field.setRegion(SpongeRegion.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
            player.sendMessage(TextUtils.greenText(CommonMessages.REGION_SET));
            return CommandResult.success();
        }

        player.sendMessage(TextUtils.redText(CommonMessages.NOT_ENOUGH_ARGS));
        return CommandResult.empty();
    }
}
