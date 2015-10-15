package musician101.minetanks.sponge.handler;

import musician101.common.java.util.ListUtil;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.scheduler.TaskBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ReloadHandler
{
    Player player;
    SpongeCannon cannon;
    int clipSize;

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
            TaskBuilder tb = SpongeMineTanks.getGame().getScheduler().createTaskBuilder();
            tb.name("SpongeMineTanks-ReloadHandler-" + player.getName());
            tb.interval(1L, TimeUnit.SECONDS);
            tb.delay(1L);
            tb.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    ExperienceHolderData expData = player.get(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get();
                    int level = expData.level().get();
                    expData.set(Keys.EXPERIENCE_LEVEL, level--);
                    player.setRawData(expData.toContainer());
                    if (level == 0)
                    {
                        if (cannon instanceof SpongeCannon && clipSize == 0)
                        {
                            for (Inventory slot : player.getInventory().slots())
                            {
                                Optional<ItemStack> iso = slot.peek();
                                if (iso.isPresent() && iso.get().getItem() == ItemTypes.BOW)
                                {
                                    SpongeAutoLoader autoLoader = (SpongeAutoLoader) cannon;
                                    ItemStack item = slot.peek().get();
                                    LoreData lore = item.get(CatalogItemData.LORE_DATA).get();
                                    lore.set(SpongeMineTanks.getGame().getRegistry().createValueBuilder().createListValue(Keys.ITEM_LORE, new ListUtil<Text>(Texts.of("Your Cannon"), Texts.of("Clip Size: " + autoLoader.getClipSize() + "/" + autoLoader.getClipSize()), Texts.of("Cycle Time: " + autoLoader.getCycleTime()), Texts.of("Clip Reload Time: " + autoLoader.getClipSize()))));
                                    item.setRawData(lore.toContainer());
                                }
                            }

                            for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
                            {
                                SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(name);
                                if (field.getPlayer(player.getUniqueId()) != null)
                                {
                                    SpongePlayerTank pt = field.getPlayer(player.getUniqueId());
                                    pt.setClipSize(cannon instanceof SpongeAutoLoader ? ((SpongeAutoLoader) cannon).getClipSize() : 1);
                                }
                            }
                        }
                    }
                }
            });
            tb.submit(SpongeMineTanks.getPluginContainer());
        }
        catch (NullPointerException e)
        {
            //TODO debug code
            SpongeMineTanks.getLogger().debug("quack");
        }
        return false;
    }
}
