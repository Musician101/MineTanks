package io.musician101.minetanks.sponge.tank.module;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractEngine;
import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.minetanks.sponge.tank.SpongeTankTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Collections;

public class SpongeEngine extends AbstractEngine<ItemStack>
{
    public SpongeEngine(String name, SpongeTankType type)
    {
        super(name);
        parseEngine(type);
    }

    private void parseEngine(SpongeTankType type)
    {
        ItemType itemType = ItemTypes.STICK;
        if (type == SpongeTankTypes.LIGHT)
            itemType = ItemTypes.LEATHER_LEGGINGS;
        else if (type == SpongeTankTypes.MEDIUM)
            itemType = ItemTypes.IRON_LEGGINGS;
        else if (type == SpongeTankTypes.HEAVY)
            itemType = ItemTypes.DIAMOND_LEGGINGS;
        else if (type == SpongeTankTypes.TD)
            itemType = ItemTypes.CHAINMAIL_LEGGINGS;
        else if (type == SpongeTankTypes.ARTY)
            itemType = ItemTypes.GOLDEN_LEGGINGS;

        ItemStack itemStack = ItemStack.of(itemType, 1);
        itemStack.offer(Keys.DISPLAY_NAME, Text.builder(getName()).color(TextColors.GREEN).build());
        itemStack.offer(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, 10)));
        itemStack.offer(Keys.ITEM_LORE, Collections.singletonList(Text.of(CommonItemText.ENGINE)));
        setItem(itemStack);
    }
}
