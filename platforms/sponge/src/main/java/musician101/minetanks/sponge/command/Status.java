package musician101.minetanks.sponge.command;

import musician101.minetanks.sponge.MineTanks;
import musician101.minetanks.sponge.exception.FieldDoesNotExistException;
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.battlefield.Battlefield;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;

public class Status extends SubCommand
{

    public Status(String name, String description, List<String> aliases)
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

        if (field.isReady())
        {
            player.sendMessage(Messages.POSITIVE_PREFIX + field.getName() + " is ready for battle.");
            return;
        }

        player.sendMessage(new String[]{Messages.POSITIVE_PREFIX + "Status of " + field.getName(), Messages.POSITIVE_PREFIX + "Enabled: " + (field.isEnabled() ? TextColors.RED + "Yes" : TextColors.GREEN + "No"), Messages.POSITIVE_PREFIX + "Cuboid: " + (field.getRegion() == null ? TextColors.RED + "Not Set" : TextColors.GREEN + "Set"), Messages.POSITIVE_PREFIX + "Green Spawn: " + (field.getGreenSpawn() == null ? TextColors.RED + "Not Set" : TextColors.GREEN + "Set"), Messages.POSITIVE_PREFIX + "Red Spawn: " + (field.getRedSpawn() == null ? TextColors.RED + "Not Set" : TextColors.GREEN + "Set"), Messages.POSITIVE_PREFIX + "Spectators Spawn: " + (field.getSpectators() == null ? TextColors.RED + "Not Set" : TextColors.GREEN + "Set"), Messages.POSITIVE_PREFIX + "Match in progress: " + (field.inProgress() ? TextColors.RED + "Yes" : TextColors.GREEN + "No")});
    }

}
