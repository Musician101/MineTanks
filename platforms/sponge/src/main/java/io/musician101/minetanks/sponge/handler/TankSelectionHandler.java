package io.musician101.minetanks.sponge.handler;

import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import io.musician101.minetanks.sponge.tank.SpongeTanks;
import io.musician101.minetanks.sponge.util.IconMenu.OptionClickEvent;
import io.musician101.minetanks.sponge.util.IconMenu.OptionClickEventHandler;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.common.java.minecraft.sponge.TextUtils;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.tank.SpongeTank;
import org.spongepowered.api.entity.living.player.Player;

public class TankSelectionHandler implements OptionClickEventHandler
{
    private SpongeMineTanks plugin;

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

        SpongeBattleFieldStorage fieldStorage = plugin.getFieldStorage();
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
