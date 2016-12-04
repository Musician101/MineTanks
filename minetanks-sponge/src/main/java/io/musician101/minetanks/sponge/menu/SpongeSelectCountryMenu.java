package io.musician101.minetanks.sponge.menu;

import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.tank.SpongeCountries;
import io.musician101.minetanks.sponge.tank.SpongeCountry;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.musicianlibrary.java.minecraft.sponge.AbstractSpongeChestMenu;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;


class SpongeSelectCountryMenu extends AbstractSpongeChestMenu<SpongeMineTanks>
{
    private final SpongeBattleField field;


    public SpongeSelectCountryMenu(SpongeBattleField field, Player player)
    {
        super(player, InventoryArchetypes.MENU_ROW, "Select Country", SpongeMineTanks.instance());
        this.field = field;
    }

    @Override
    protected void build()
    {
        int slot = 0;
        for (SpongeCountry country : SpongeCountries.getValues())
            set(slot++, country.getItem(), player -> new SpongeSelectTankMenu(field, country, null, player));

        set(inventory.size() - 1, MTUtils.createCustomItem(ItemTypes.ARROW, "Back", "Go back to the previous menu"), player -> new SpongeMainSelectionMenu(field, player));
    }
}
