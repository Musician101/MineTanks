package musician101.minetanks.command;

import musician101.minetanks.MineTanks;
import musician101.minetanks.exception.FieldDoesNotExistException;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.spigot.battlefield.Battlefield;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;

public class Enable extends SubCommand
{
    public Enable(String name, String description, List<String> aliases)
    {
        super(name, description, aliases);
    }

    @Override
    public void execute(CommandSource source, List<String> args) throws Exception
    {
        Player player = (Player) source;
        if (!player.hasPermission(getPermission()))
            throw new NoPermissionException(Messages.NO_PERMISSION);

        Battlefield field = MineTanks.getFieldStorage().getEdit();
        if (field == null)
            throw new FieldDoesNotExistException(Messages.NEGATIVE_PREFIX + "Error: No fields have been created.");

        if (field.isEnabled())
        {
            field.setEnabled(false);
            player.sendMessage(Messages.POSITIVE_PREFIX + field.getName() + " has been disabled.");
        } else if (!field.isEnabled())
        {
            field.setEnabled(true);
            player.sendMessage(Messages.POSITIVE_PREFIX + field.getName() + " has been enabled.");
        }
    }

}
