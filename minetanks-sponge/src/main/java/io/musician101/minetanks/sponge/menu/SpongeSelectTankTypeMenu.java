package io.musician101.minetanks.sponge.menu;

import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.minetanks.sponge.tank.SpongeTankTypes;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.musicianlibrary.java.minecraft.sponge.AbstractSpongeChestMenu;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Collections;


class SpongeSelectTankTypeMenu extends AbstractSpongeChestMenu<SpongeMineTanks>
{
    private final SpongeBattleField field;


    public SpongeSelectTankTypeMenu(SpongeBattleField field, Player player)
    {
        super(player, InventoryArchetypes.MENU_ROW, "Select Tank Type", SpongeMineTanks.instance());
        //TODO add GUI for all commands
        this.field = field;
    }

    @Override
    protected void build()
    {
        for (int slot = 0; slot < inventory.size() - 1; slot++)
        {
            try
            {
                SpongeTankType spigotTankType = SpongeTankTypes.getValues().get(slot);
                set(slot, spigotTankType.getItem(), player -> new SpongeSelectTankMenu(field, null, spigotTankType, player));
            }
            catch (IndexOutOfBoundsException e)//NOSONAR
            {
                set(slot, ItemStack.of(ItemTypes.GLASS_PANE, 1));
            }
        }

        ItemStack back = ItemStack.of(ItemTypes.ARROW, 1);
        back.offer(Keys.DISPLAY_NAME, Text.of("Back"));
        back.offer(Keys.ITEM_LORE, Collections.singletonList(Text.of("Go back to the previous menu.")));
        set(inventory.size() - 1, MTUtils.createCustomItem(ItemTypes.ARROW, "Back", "Go back to the previous menu"), player -> new SpongeMainSelectionMenu(field, player));
    }
}
