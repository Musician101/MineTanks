package musician101.minetanks.sponge.command.participate;

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
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class LeaveSpongeCommand extends AbstractSpongeCommand
{
    public LeaveSpongeCommand()
    {
        super(CommonCommands.LEAVE_NAME, CommonCommands.LEAVE_DESC, Arrays.asList(new SpongeCommandArgument(CommonCommands.MT_CMD), new SpongeCommandArgument(CommonCommands.LEAVE_NAME)), 0, CommonPermissions.PARTICIPATE_PERM, true, TextUtils.redText(CommonMessages.NO_PERMISSION), TextUtils.redText(CommonMessages.PLAYER_ONLY));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        if (!testPermission(source))
            return CommandResult.empty();

        Player player = (Player) source;
        SpongeBattleFieldStorage fieldStorage = SpongeMineTanks.getFieldStorage();
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
                field.removePlayer(player.getUniqueId());
                return CommandResult.success();
            }
        }

        player.sendMessage(TextUtils.redText(CommonMessages.NOT_IN_A_FIELD));
        return CommandResult.empty();
    }
}
