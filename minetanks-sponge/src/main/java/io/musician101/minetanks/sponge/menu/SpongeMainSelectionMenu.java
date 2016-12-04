package io.musician101.minetanks.sponge.menu;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.tank.SpongeCountries;
import io.musician101.minetanks.sponge.tank.SpongeCountry;
import io.musician101.musicianlibrary.java.minecraft.sponge.AbstractSpongeChestMenu;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpongeMainSelectionMenu extends AbstractSpongeChestMenu<SpongeMineTanks>
{
    private final SpongeBattleField field;

    public SpongeMainSelectionMenu(SpongeBattleField field, Player player)
    {
        super(player, InventoryArchetypes.MENU_ROW, CommonReference.TANK_SELECTION, SpongeMineTanks.instance());
        this.field = field;
    }

    @Override
    protected void build()
    {
        List<SpongeCountry> countries = SpongeCountries.getValues();
        ItemStack bannerStack = countries.get(new Random().nextInt(countries.size() - 1)).getItem();
        bannerStack.offer(Keys.DISPLAY_NAME, Text.of("Countries"));
        set(0, bannerStack, player -> new SpongeSelectCountryMenu(field, player));

        ItemStack minecart = ItemStack.of(ItemTypes.MINECART, 1);
        minecart.offer(Keys.DISPLAY_NAME, Text.of("Tank Types"));
        minecart.offer(Keys.ITEM_LORE, Arrays.asList(Text.of("View tanks by their types."), Text.of("Light, Medium, etc.")));
        set(1, minecart, player -> new SpongeSelectTankTypeMenu(field, player));

        for (int slot = 2; slot < inventory.size(); slot++)
            set(slot, ItemStack.of(ItemTypes.GLASS_PANE, 1));
    }
}
