package musician101.minetanks.sponge.command;

import musician101.minetanks.sponge.MineTanks;
import musician101.minetanks.sponge.exception.FieldDoesNotExistException;
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.battlefield.Battlefield;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ForceEnd extends SubCommand
{
    public ForceEnd(String name, String description, String usage, List<String> aliases)
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

        Battlefield field = MineTanks.getFieldStorage().getField(args.get(0));
        if (field == null)
            throw new FieldDoesNotExistException(Messages.FIELD_DNE);

        List<UUID> uuids = new ArrayList<UUID>();
        for (UUID uuid : field.getPlayers().keySet())
            uuids.add(uuid);

        for (UUID uuid : uuids)
        {
            Player p = MineTanks.getGame().getServer().get().getPlayer(uuid).get();
            p.sendMessage(Messages.NEGATIVE_PREFIX + "An admin has forcibly terminated the match.");
            field.removePlayer(p);
        }

        field.setInProgress(false);
        player.sendMessage(Messages.POSITIVE_PREFIX + "Match terminated.");
    }

}
