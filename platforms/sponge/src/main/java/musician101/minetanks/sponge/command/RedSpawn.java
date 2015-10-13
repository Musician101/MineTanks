package musician101.minetanks.sponge.command;

import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.exception.FieldDoesNotExistException;
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;

public class RedSpawn extends SubCommand
{
    public RedSpawn(String name, String description, String usage, List<String> aliases)
    {
        super(name, description, usage, aliases);
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

        if (field.getRegion() == null || !player.getWorld().getName().equals(field.getRegion().getWorld().getName()))
        {
            player.sendMessage(Messages.MISSING_CUBOID);
            return;
        }

        if (!field.getRegion().isInRegion(player.getLocation()))
        {
            player.sendMessage(Messages.LOCATION_NOT_IN_CUBOID);
            return;
        }

        field.setRedSpawn(player.getLocation());
        player.sendMessage(Messages.POSITIVE_PREFIX + " Red Spawn point set.");
    }

}
