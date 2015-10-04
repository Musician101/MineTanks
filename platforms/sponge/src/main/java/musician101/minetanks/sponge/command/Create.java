package musician101.minetanks.sponge.command;

import musician101.minetanks.sponge.MineTanks;
import musician101.minetanks.sponge.exception.FieldAlreadyExistsException;
import musician101.minetanks.sponge.lib.Reference.Messages;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;

public class Create extends SubCommand
{
    public Create(String name, String description, String usage, List<String> aliases)
    {
        super(name, description, usage, aliases);
    }

    @Override
    public void execute(CommandSource source, List<String> args) throws Exception
    {
        //TODO permissions not implemented
        Player player = (Player) source;
        if (!player.hasPermission(getPermission()))
            throw new NoPermissionException(Messages.NO_PERMISSION);

        if (args.size() < 1)
            throw new NotEnoughArgumentsException(Messages.NEGATIVE_PREFIX + "Error: Field not specified.");

        if (!MineTanks.getFieldStorage().createField(args.get(0)))
            throw new FieldAlreadyExistsException(String.format(Messages.FIELD_ALREADY_EXISTS, args.get(0)));

        MineTanks.getFieldStorage().createField(args.get(0));
        player.sendMessage(new String[]{Messages.POSITIVE_PREFIX + args.get(0) + " successfully created", Messages.POSITIVE_PREFIX + "Check the status of the battlefield by using /mt status."});
    }
}
