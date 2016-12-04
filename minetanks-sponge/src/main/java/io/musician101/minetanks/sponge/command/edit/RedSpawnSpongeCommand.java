package io.musician101.minetanks.sponge.command.edit;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RedSpawnSpongeCommand extends AbstractSpongeCommand
{
    public RedSpawnSpongeCommand()
    {
        super(CommonCommands.RED_SPAWN_NAME, TextUtils.aquaText(CommonCommands.RED_SPAWN_DESC), new SpongeCommandUsage(Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.RED_SPAWN_NAME), new SpongeCommandArgument(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)), 1), new SpongeCommandPermissions(CommonPermissions.EDIT_PERM, true, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY)));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        if (minArgsMet(source, args.length, Text.of(CommonMessages.FIELD_NOT_SPECIFIED)))
            return CommandResult.empty();

        Player player = (Player) source;
        SpongeBattleField field = SpongeMineTanks.instance().getFieldStorage().getField(args[0]);
        if (field == null)
        {
            source.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
            return CommandResult.empty();
        }

        Location<World> loc = player.getLocation();
        if (field.getRegion() == null || !field.getRegion().isInRegion(loc))
        {
            player.sendMessage(TextUtils.redText(CommonMessages.LOCATION_NOT_IN_REGION));
            return CommandResult.empty();
        }

        field.setRedSpawn(loc);
        player.sendMessage(TextUtils.greenText(CommonMessages.RED_SPAWN_SET));
        return CommandResult.success();
    }
}
