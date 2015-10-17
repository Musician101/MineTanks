package musician101.minetanks.sponge.util;

import musician101.minetanks.sponge.SpongeMineTanks;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulatorRegistry;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MTUtils
{
    public static ItemStack createCustomItem(ItemType type)
    {
        return SpongeMineTanks.getGame().getRegistry().createItemBuilder().itemType(type).build();
    }

    public static ItemStack createCustomItem(ItemType type, int quantity)
    {
        return SpongeMineTanks.getGame().getRegistry().createItemBuilder().itemType(type).quantity(quantity).build();
    }

    public static ItemStack createCustomItem(ItemType type, String displayName, List<String> description)
    {
        return createCustomItem(type, 1, displayName, description);
    }

    public static ItemStack createCustomItem(ItemType type, int quantity, String displayName, List<String> description)
    {
        List<Text> loreText = new ArrayList<>();
        description.forEach(string -> loreText.add(Texts.of(string)));
        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        DataManipulatorRegistry dmr = gr.getManipulatorRegistry();
        LoreData loreData = dmr.getBuilder(CatalogItemData.LORE_DATA).get().create();
        loreData.set(gr.createValueBuilder().createListValue(Keys.ITEM_LORE, loreText));
        DisplayNameData nameData = dmr.getBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        nameData.set(gr.createValueBuilder().createValue(Keys.DISPLAY_NAME, Texts.builder().append(Texts.of(displayName)).build()));
        ItemStackBuilder isb = SpongeMineTanks.getGame().getRegistry().createItemBuilder();
        isb.itemType(type);
        isb.quantity(quantity);
        return isb.build();
    }

    public static Player getPlayer(UUID playerId)
    {
        return SpongeMineTanks.getGame().getServer().getPlayer(playerId).get();
    }
}
