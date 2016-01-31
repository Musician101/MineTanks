package musician101.minetanks.sponge.tank.module.cannon;

import musician101.common.java.util.ListUtil;
import musician101.minetanks.common.CommonReference.CommonItemText;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class SpongeAutoLoader extends SpongeCannon
{
    private final double cycleTime;
    private final int clipSize;

    public SpongeAutoLoader(String name, int ammoCount, double reloadTime, double cycleTime, int clipSize)
    {
        super(name, ammoCount, reloadTime);
        this.clipSize = clipSize;
        this.cycleTime = cycleTime;
        parseCannon();
    }

    public double getCycleTime()
    {
        return cycleTime;
    }

    public int getClipSize()
    {
        return clipSize;
    }

    private void parseCannon()
    {
        ItemStack.Builder isb = ItemStack.builder().fromItemStack(super.getItem());
        LoreData lore = Sponge.getDataManager().getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(Sponge.getGame().getRegistry().getValueFactory().createListValue(Keys.ITEM_LORE,
                new ListUtil<>(Text.of(CommonItemText.CANNON),
                        Text.of(CommonItemText.clipSize(clipSize, clipSize)),
                        Text.of(CommonItemText.cycleTime(cycleTime)),
                        Text.of(CommonItemText.reloadTime(getReloadTime())))));

        setItem(isb.itemData(lore).build());
    }
}
