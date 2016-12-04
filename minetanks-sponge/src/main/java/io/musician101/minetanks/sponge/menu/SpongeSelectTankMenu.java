package io.musician101.minetanks.sponge.menu;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import io.musician101.minetanks.sponge.tank.SpongeCountry;
import io.musician101.minetanks.sponge.tank.SpongeTank;
import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.minetanks.sponge.tank.SpongeTanks;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.musicianlibrary.java.minecraft.sponge.AbstractSpongeChestMenu;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;


class SpongeSelectTankMenu extends AbstractSpongeChestMenu<SpongeMineTanks>
{
    private final SpongeCountry country;
    private final SpongeBattleField field;
    private final SpongeTankType tankType;


    public SpongeSelectTankMenu(SpongeBattleField field, SpongeCountry country, SpongeTankType tankType, Player player)
    {
        super(player, InventoryArchetypes.DOUBLE_CHEST, "Tank Select", SpongeMineTanks.instance());
        this.field = field;
        this.country = country;
        this.tankType = tankType;
    }

    @Override
    protected void build()
    {
        int slot = 0;
        for (SpongeTank tank : SpongeTanks.getValues())
            if ((country == null || country.getName().equals(tank.getCountry().getName())) && (tankType == null || tankType.getName().equals(tank.getType().getName())))
                set(slot++, tank.getItem(), player ->
                {
                    SpongePlayerTank pt = field.getPlayerTank(player.getUniqueId());
                    pt.setTank(tank);
                    player.getInventory().first().set(MTUtils.createCustomItem(tank.getType().getItem().getItem(), CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(tank)));
                    player.sendMessage(TextUtils.greenText(CommonMessages.tankSelection1(tank)));
                    player.sendMessage(TextUtils.greenText(CommonMessages.TANK_SELECTION_2));
                    player.sendMessage(TextUtils.greenText(CommonMessages.TANK_SELECTION_3));
                });

        set(inventory.size() - 1, MTUtils.createCustomItem(ItemTypes.ARROW, "Back", "Go back to the previous menu"), player ->
        {
            if (country == null)
                new SpongeSelectTankTypeMenu(field, player);
            else if (tankType == null)
                new SpongeSelectCountryMenu(field, player);
        });
    }
}
