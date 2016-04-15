package musician101.minetanks.sponge.handler;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import musician101.minetanks.sponge.tank.SpongeTank;
import musician101.minetanks.sponge.tank.SpongeTanks;
import musician101.minetanks.sponge.util.IconMenu.OptionClickEvent;
import musician101.minetanks.sponge.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.entity.living.player.Player;

public class TankSelectionHandler implements OptionClickEventHandler
{
    @Override
    public void onOptionClick(OptionClickEvent event)
    {
        String name = event.getName();
        Player player = event.getPlayer();
        SpongeTank tank = null;
        for (SpongeTank t : SpongeTanks.ALL)
            if (name.equals(t.getName()))
                tank = t;

        if (tank == null)
            return;

        SpongeBattleFieldStorage fieldStorage = SpongeMineTanks.getFieldStorage();
        for (String fieldName : fieldStorage.getFields().keySet())
        {
            SpongeBattleField field = fieldStorage.getField(fieldName);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                player.getInventory().set(MTUtils.createCustomItem(tank.getType().getItem().getItem(), CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(tank)));
                player.sendMessages(TextUtils.greenText(CommonMessages.tankSelection1(tank)), TextUtils.greenText(CommonMessages.TANK_SELECTION_2), TextUtils.greenText(CommonMessages.TANK_SELECTION_3));
            }
        }
    }
}
