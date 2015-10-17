package musician101.minetanks.sponge.handler;

import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.tank.Tank;
import musician101.minetanks.sponge.tank.Tanks;
import musician101.minetanks.sponge.util.IconMenu.OptionClickEvent;
import musician101.minetanks.sponge.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;

import java.util.Arrays;
import java.util.Collections;

public class TankSelectionHandler implements OptionClickEventHandler
{
    @Override
    public void onOptionClick(OptionClickEvent event)
    {
        String name = event.getName();
        Player player = event.getPlayer();
        Tank tank = null;
        for (Tank t : Tanks.tankList)
            if (name.equals(t.getName()))
                tank = t;

        if (tank == null)
            return;

        SpongeBattleFieldStorage fieldStorage = SpongeMineTanks.getFieldStorage();
        for (String fieldName : fieldStorage.getFields().keySet())
        {
            SpongeBattleField field = fieldStorage.getField(fieldName);
            if (field.getPlayer(player.getUniqueId()) != null)
            {
                player.getInventory().set(MTUtils.createCustomItem(tank.getType().getItem().getItem(), "Open Hangar", Collections.singletonList("Tank: " + tank.getName())));
                player.sendMessage(Texts.of(Messages.POSITIVE_PREFIX + "You have chosen the " + tank.getName() + "."));
                player.sendMessage(Texts.of(Messages.POSITIVE_PREFIX + "If you wish to choose another tank, right click with the 'Open Hangar' item."));
                player.sendMessage(Texts.of(Messages.POSITIVE_PREFIX + "When you are ready, simply right click the 'Ready' item."));
            }
        }
    }
}
