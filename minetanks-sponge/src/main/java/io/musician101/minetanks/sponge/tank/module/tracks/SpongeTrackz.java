package io.musician101.minetanks.sponge.tank.module.tracks;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractTrackz;
import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Collections;


public class SpongeTrackz extends AbstractTrackz<ItemStack, SpongeTankType>
{

    public SpongeTrackz(String name, SpongeTankType type)
    {
        super(name);
        parseTracks(type);
    }

    @Override
    protected void parseTracks(SpongeTankType type)
    {
        ItemStack itemStack = ItemStack.of(type.getItem().getItem(), 1);
        itemStack.offer(Keys.DISPLAY_NAME, TextUtils.greenText(getName()));
        itemStack.offer(Keys.ITEM_LORE, Collections.singletonList(Text.of(CommonItemText.TRACKS)));
        itemStack.offer(Keys.UNBREAKABLE, true);
        setItem(itemStack);
    }
}
