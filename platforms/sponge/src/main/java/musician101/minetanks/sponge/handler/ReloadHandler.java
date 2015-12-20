package musician101.minetanks.sponge.handler;

import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Texts;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ReloadHandler
{
    private final Player player;
    private final SpongeCannon cannon;
    private final int clipSize;

    public ReloadHandler(Player player, SpongeCannon cannon, int clipSize)
    {
        this.player = player;
        this.cannon = cannon;
        this.clipSize = clipSize;
    }

    public boolean isReloading()
    {
        ExperienceHolderData expData = player.get(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get();
        int level = expData.level().get();
        if (level > 0)
            return true;

        int time;
        if (clipSize == 0)
            time = (int) cannon.getReloadTime();
        else
        {
            if (cannon instanceof SpongeAutoLoader)
                time = (int) ((SpongeAutoLoader) cannon).getCycleTime();
            else
                time = (int) cannon.getReloadTime();
        }

        expData.set(Keys.EXPERIENCE_LEVEL, time);
        player.setRawData(expData.toContainer());
        try
        {
            Task.Builder tb = Task.builder();
            tb.name("SpongeMineTanks-ReloadHandler-" + player.getName());
            tb.interval(1L, TimeUnit.SECONDS);
            tb.delayTicks(1L);
            tb.execute(() -> {
                ExperienceHolderData expData1 = player.get(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get();
                int level1 = expData1.level().get();
                expData1.set(Keys.EXPERIENCE_LEVEL, level1--);
                player.setRawData(expData1.toContainer());
                if (level1 == 0)
                {
                    if (cannon instanceof SpongeCannon && clipSize == 0)
                    {
                        for (Inventory slot : player.getInventory().slots())
                        {
                            ItemStack item = slot.peek();
                            if (item != null && item.getItem() == ItemTypes.BOW)
                            {
                                SpongeAutoLoader autoLoader = (SpongeAutoLoader) cannon;
                                LoreData lore = item.get(CatalogItemData.LORE_DATA).get();
                                lore.set(Sponge.getGame().getRegistry().getValueFactory().createListValue(Keys.ITEM_LORE,
                                        Arrays.asList(Texts.of(CommonItemText.CANNON),
                                                Texts.of(CommonItemText.clipSize(autoLoader.getClipSize(), autoLoader.getClipSize())),
                                                Texts.of(CommonItemText.cycleTime(autoLoader.getCycleTime())),
                                                Texts.of(CommonItemText.clipReloadTime(autoLoader.getClipSize())))));

                                item.setRawData(lore.toContainer());
                            }
                        }

                        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
                        {
                            SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(name);
                            if (field.getPlayerTank(player.getUniqueId()) != null)
                            {
                                SpongePlayerTank pt = field.getPlayerTank(player.getUniqueId());
                                pt.setClipSize(cannon instanceof SpongeAutoLoader ? ((SpongeAutoLoader) cannon).getClipSize() : 1);
                            }
                        }
                    }
                }
            });
            tb.submit(Sponge.getGame().getPluginManager().getPlugin(CommonReference.ID));
        }
        catch (NullPointerException e)
        {
            //TODO debug code
            SpongeMineTanks.getLogger().debug("quack");
        }
        return false;
    }
}
