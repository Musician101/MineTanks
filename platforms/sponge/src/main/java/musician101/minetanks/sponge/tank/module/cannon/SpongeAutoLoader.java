package musician101.minetanks.sponge.tank.module.cannon;

import musician101.common.java.util.ListUtil;
import musician101.minetanks.common.CommonReference.CommonItemText;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.item.inventory.ItemStack;
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
        Game game = Sponge.getGame();
        GameRegistry gr = game.getRegistry();
        ItemStack.Builder isb = ItemStack.builder().fromItemStack(super.getItem());
        LoreData lore = game.getDataManager().getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(gr.getValueFactory().createListValue(Keys.ITEM_LORE,
                new ListUtil<>(Texts.of(CommonItemText.CANNON),
                        Texts.of(CommonItemText.clipSize(clipSize, clipSize)),
                        Texts.of(CommonItemText.cycleTime(cycleTime)),
                        Texts.of(CommonItemText.reloadTime(getReloadTime())))));

        setItem(isb.itemData(lore).build());
    }
}
