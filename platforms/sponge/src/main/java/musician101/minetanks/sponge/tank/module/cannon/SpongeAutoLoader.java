package musician101.minetanks.sponge.tank.module.cannon;

import musician101.common.java.util.ListUtil;
import musician101.minetanks.sponge.SpongeMineTanks;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

public class SpongeAutoLoader extends SpongeCannon
{
    private final double cycleTime;
    private final int clipSize;

    public SpongeAutoLoader(String name, int ammoCount, double reloadTime, double cycleTime, int clipSize)
    {
        super(name, ammoCount, reloadTime);
        this.clipSize = clipSize;
        this.cycleTime = cycleTime;
    }

    public double getCycleTime()
    {
        return cycleTime;
    }

    public int getClipSize()
    {
        return clipSize;
    }

    @Override
    public ItemStack getItem()
    {
        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        ItemStackBuilder isb = gr.createItemBuilder().fromItemStack(super.getItem());
        LoreData lore = gr.getManipulatorRegistry().getBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(gr.createValueBuilder().createListValue(Keys.ITEM_LORE, new ListUtil<Text>(Texts.of("Your Cannon"), Texts.of("Clip Size: " + clipSize + "/" + clipSize), Texts.of("Clip Reload Time: " + getReloadTime()))));
        return isb.itemData(lore).build();
    }
}
