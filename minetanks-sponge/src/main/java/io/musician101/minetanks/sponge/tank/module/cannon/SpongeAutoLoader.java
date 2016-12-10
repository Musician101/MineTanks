package io.musician101.minetanks.sponge.tank.module.cannon;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import java.util.Arrays;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;


public class SpongeAutoLoader extends SpongeCannon {

    private final int clipSize;
    private final double cycleTime;


    public SpongeAutoLoader(String name, int ammoCount, double reloadTime, double cycleTime, int clipSize) {
        super(name, ammoCount, reloadTime);
        this.clipSize = clipSize;
        this.cycleTime = cycleTime;
        parseCannon();
    }

    public int getClipSize() {
        return clipSize;
    }

    public double getCycleTime() {
        return cycleTime;
    }

    @Override
    protected void parseCannon() {
        ItemStack itemStack = ItemStack.builder().fromItemStack(super.getItem()).build();
        itemStack.offer(Keys.ITEM_LORE, Arrays.asList(Text.of(CommonItemText.CANNON), Text.of(CommonItemText.clipSize(clipSize, clipSize)), Text.of(CommonItemText.cycleTime(cycleTime)), Text.of(CommonItemText.reloadTime(getReloadTime()))));
        setItem(itemStack);
    }
}
