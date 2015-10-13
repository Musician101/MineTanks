package musician101.minetanks.sponge.command;

import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.exception.FieldDoesNotExistException;
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
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

        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getEdit();
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
