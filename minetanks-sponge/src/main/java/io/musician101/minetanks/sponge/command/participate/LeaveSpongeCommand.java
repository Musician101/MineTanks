package io.musician101.minetanks.sponge.command.participate;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
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

public class LeaveSpongeCommand extends AbstractSpongeCommand
{
    public LeaveSpongeCommand()
    {
        super(CommonCommands.LEAVE_NAME, TextUtils.aquaText(CommonCommands.LEAVE_DESC), new SpongeCommandUsage(Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.LEAVE_NAME))), new SpongeCommandPermissions(CommonPermissions.PARTICIPATE_PERM, true, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY)));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        if (!testPermission(source))
            return CommandResult.empty();

        Player player = (Player) source;
        SpongeBattleFieldStorage fieldStorage = SpongeMineTanks.instance().getFieldStorage();
        if (fieldStorage.canPlayerExit(player.getUniqueId()))
        {
            player.sendMessage(TextUtils.redText(CommonMessages.NO_PERMISSION));
            return CommandResult.empty();
        }

        for (String name : fieldStorage.getFields().keySet())
        {
            SpongeBattleField field = fieldStorage.getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                player.sendMessage(TextUtils.greenText(CommonMessages.LEFT_FIELD));
                field.removePlayer(player);
                return CommandResult.success();
            }
        }

        player.sendMessage(TextUtils.redText(CommonMessages.NOT_IN_A_FIELD));
        return CommandResult.empty();
    }
}
