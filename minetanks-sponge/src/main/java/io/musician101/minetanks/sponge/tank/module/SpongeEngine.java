package io.musician101.minetanks.sponge.tank.module;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractEngine;
import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import java.util.Collections;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;


public class SpongeEngine extends AbstractEngine<ItemStack, SpongeTankType> {

    public SpongeEngine(String name, SpongeTankType type) {
        super(name);
        parseEngine(type);
    }

    @Override
    protected void parseEngine(SpongeTankType type) {
        ItemStack itemStack = ItemStack.of(type.getItem().getItem(), 1);
        itemStack.offer(Keys.DISPLAY_NAME, TextUtils.greenText(getName()));
        itemStack.offer(Keys.ITEM_LORE, Collections.singletonList(Text.of(CommonItemText.ENGINE)));
        itemStack.offer(Keys.UNBREAKABLE, true);
        setItem(itemStack);
    }
}
