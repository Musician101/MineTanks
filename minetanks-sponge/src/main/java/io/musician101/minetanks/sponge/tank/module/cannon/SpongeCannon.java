package io.musician101.minetanks.sponge.tank.module.cannon;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractCannon;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;
import java.util.Collections;

public class SpongeCannon extends AbstractCannon<ItemStack>
{
    public SpongeCannon(String name, int ammoCount, double reloadTime)
    {
        super(name, ammoCount, reloadTime);
        parseCannon();
    }

    @Override
    protected void parseCannon()
    {
        ItemStack itemStack = ItemStack.of(ItemTypes.BOW, 1);
        itemStack.offer(Keys.DISPLAY_NAME, Text.builder(getName()).color(TextColors.GREEN).build());
        itemStack.offer(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.INFINITY, 1)));
        itemStack.offer(Keys.ITEM_LORE, Arrays.asList(Text.of(CommonItemText.CANNON), Text.of(CommonItemText.reloadTime(getReloadTime()))));
        itemStack.offer(Keys.UNBREAKABLE, true);
        setItem(itemStack);
    }
}
