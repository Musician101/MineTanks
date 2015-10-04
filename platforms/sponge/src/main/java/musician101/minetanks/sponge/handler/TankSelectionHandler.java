package musician101.minetanks.sponge.handler;

import musician101.minetanks.sponge.MineTanks;
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.battlefield.Battlefield;
import musician101.minetanks.sponge.tank.Tanks;
import musician101.minetanks.sponge.util.IconMenu.OptionClickEvent;
import musician101.minetanks.sponge.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.entity.player.Player;

import java.util.Arrays;

public class TankSelectionHandler implements OptionClickEventHandler
{
    @Override
    public void onOptionClick(OptionClickEvent event)
    {
        int slot = event.getPosition();
        Player player = event.getPlayer();
        Tanks tank = null;
        for (Tanks t : Tanks.values())
            if (slot == t.getId())
                tank = t;

        if (tank == null)
            return;

        Battlefield field = MineTanks.getFieldStorage().getPlayerField(player.getUniqueId());
        field.getPlayer(player.getUniqueId()).setTank(tank);
        player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getIcon().getItem(), "Open Hangar", Arrays.asList("Tank: " + tank.getName())));
        player.sendMessage(Messages.POSITIVE_PREFIX + "You have chosen the " + tank.getName() + ".");
        player.sendMessage(Messages.POSITIVE_PREFIX + "If you wish to choose another tank, right click with the 'Open Hangar' item.");
        player.sendMessage(Messages.POSITIVE_PREFIX + "When you are ready, simply right click the 'Ready' item.");
    }
}