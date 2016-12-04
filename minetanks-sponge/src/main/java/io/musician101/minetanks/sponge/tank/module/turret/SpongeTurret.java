package io.musician101.minetanks.sponge.tank.module.turret;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.common.tank.modules.AbstractTurret;
import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Collections;


public class SpongeTurret extends AbstractTurret<ItemStack, SpongeTankType>
{

    public SpongeTurret(String name, SpongeTankType type, Armor armor)
    {
        super(name, armor);
        parseTurret(type);
    }

    @Override
    protected void parseTurret(SpongeTankType type)
    {
        ItemStack itemStack = ItemStack.of(type.getItem().getItem(), 1);
        itemStack.offer(Keys.DISPLAY_NAME, TextUtils.greenText(getName()));
        itemStack.offer(Keys.ITEM_LORE, Collections.singletonList(Text.of(CommonItemText.TURRET)));
        itemStack.offer(Keys.UNBREAKABLE, true);
        setItem(itemStack);
    }
}
