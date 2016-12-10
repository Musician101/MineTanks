package io.musician101.minetanks.sponge.util;

import java.util.Collections;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class MTUtils {

    private MTUtils() {

    }

    public static ItemStack createCustomItem(ItemType type, String displayName, String description) {
        ItemStack itemStack = ItemStack.of(type, 1);
        itemStack.offer(Keys.DISPLAY_NAME, Text.of(displayName));
        itemStack.offer(Keys.ITEM_LORE, Collections.singletonList(Text.of(description)));
        return itemStack;
    }
}
