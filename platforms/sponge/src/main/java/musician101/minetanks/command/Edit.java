package musician101.minetanks.command;

import musician101.minetanks.MineTanks;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;

//TODO get rid of edit command in favor of having an additional argument to specify fields
@Deprecated
public class Edit extends SubCommand
{
    public Edit(String name, String description, String usage, List<String> aliases)
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

        if (!MineTanks.getFieldStorage().setEdit(args.get(0)))
            throw new FieldDoesNotExistException(Messages.FIELD_DNE);

        player.sendMessage(Messages.POSITIVE_PREFIX + args.get(0) + " is now being edited.");
    }

}
