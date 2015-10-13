package musician101.minetanks.sponge.command;

import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.exception.FieldDoesNotExistException;
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank.MTTeam;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;

public class Join extends SubCommand
{
    public Join(String name, String description, String usage, List<String> aliases)
    {
        super(name, description, usage, aliases);
    }

    @Override
    public void execute(CommandSource source, List<String> args) throws Exception
    {
        Player player = (Player) source;
        if (!player.hasPermission(getPermission()))
            throw new NoPermissionException(Messages.NO_PERMISSION);

        if (args.size() < 1)
            throw new NotEnoughArgumentsException(Messages.NEGATIVE_PREFIX + "Error: Field not specified.");

        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(args.get(0));
        if (field == null)
            throw new FieldDoesNotExistException(Messages.FIELD_DNE);

        if (!field.isReady())
        {
            player.sendMessage(Messages.FIELD_NOT_READY);
            return;
        }

        if (field.inProgress())
        {
            player.sendMessage(Messages.MATCH_IN_PROGRESS);
            return;
        }

        field.addPlayer(player, MTTeam.UNASSIGNED);
        SpongeMineTanks.openTankMenu(player);
    }
}
