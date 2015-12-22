package musician101.minetanks.sponge.util;

import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStack.Builder;
import org.spongepowered.api.text.Texts;

import java.util.Collections;
import java.util.UUID;

public class MTUtils
{
    public static ItemStack createCustomItem(ItemType type, String displayName, String description)
    {
        DataManager dm = Sponge.getDataManager();
        LoreData loreData = dm.getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        GameRegistry gr = Sponge.getGame().getRegistry();
        loreData.set(gr.getValueFactory().createListValue(Keys.ITEM_LORE, Collections.singletonList(Texts.of(description))));
        DisplayNameData nameData = dm.getManipulatorBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        nameData.set(gr.getValueFactory().createValue(Keys.DISPLAY_NAME, Texts.of(displayName)));
        Builder isb = ItemStack.builder();
        isb.itemType(type);
        return isb.build();
    }

    public static Player getPlayer(UUID playerId)
    {
        return Sponge.getGame().getServer().getPlayer(playerId).get();
    }
}
