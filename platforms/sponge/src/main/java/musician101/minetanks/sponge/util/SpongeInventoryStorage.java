package musician101.minetanks.sponge.util;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.util.AbstractInventoryStorage;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpongeInventoryStorage extends AbstractInventoryStorage
{
    public SpongeInventoryStorage(File storageDir)
    {
        super(storageDir);
    }

    @Override
    public void load(UUID playerId)
    {
        File file = getPlayerFile(playerId);
        Game game = Sponge.getGame();
        Player player = game.getServer().getPlayer(playerId).get();
        if (!file.exists())
        {
            player.sendMessage(TextUtils.redText(CommonMessages.fileLoadFailed(file)));
            return;
        }

        try
        {
            SpongeJSONConfig sjc = SpongeJSONConfig.load(file);
            for (ItemStack itemStack : sjc.getItemStackList(CommonConfig.INVENTORY.replace(".", "")))
                player.getInventory().set(itemStack);

            if (sjc.containsKey(CommonConfig.HELMET))
                player.setHelmet(sjc.getItemStack(CommonConfig.HELMET));

            if (sjc.containsKey(CommonConfig.CHESTPLATE))
                player.setChestplate(sjc.getItemStack(CommonConfig.CHESTPLATE));

            if (sjc.containsKey(CommonConfig.LEGGINGS))
                player.setLeggings(sjc.getItemStack(CommonConfig.LEGGINGS));

            if (sjc.containsKey(CommonConfig.BOOTS))
                player.setBoots(sjc.getItemStack(CommonConfig.BOOTS));

            PotionEffectData data = game.getDataManager().getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create();
            data.set(game.getRegistry().getValueFactory().createListValue(Keys.POTION_EFFECTS, sjc.getPotionEffectsList(CommonConfig.EFFECTS)));
            player.setRawData(data.toContainer());
            file.delete();
        }
        catch (IOException | ParseException e)
        {
            player.sendMessage(TextUtils.redText(CommonMessages.fileLoadFailed(file)));
        }
    }

    @Override
    public boolean save(UUID playerId)
    {
        Player player = MTUtils.getPlayer(playerId);
        Inventory inv = player.getInventory();
        SpongeJSONConfig sjc = new SpongeJSONConfig();
        List<ItemStack> items = new ArrayList<>();
        for (Inventory slot : inv.slots())
            if (slot.peek() != null)
                items.add(slot.peek());

        sjc.setItemStackList(CommonConfig.INVENTORY.replace(".", ""), items);
        if (player.getHelmet().isPresent())
            sjc.setItemStack(CommonConfig.HELMET, player.getHelmet().get());

        if (player.getChestplate().isPresent())
            sjc.setItemStack(CommonConfig.CHESTPLATE, player.getChestplate().get());

        if (player.getLeggings().isPresent())
            sjc.setItemStack(CommonConfig.LEGGINGS, player.getLeggings().get());

        if (player.getBoots().isPresent())
            sjc.setItemStack(CommonConfig.BOOTS, player.getBoots().get());

        ExperienceHolderData exp = player.get(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get();
        sjc.set(CommonConfig.XP, exp.totalExperience());

        PotionEffectData potions = player.get(CatalogEntityData.POTION_EFFECT_DATA).get();
        sjc.setPotionEffectsList(CommonConfig.EFFECTS, potions.effects().get());
        sjc.set(CommonConfig.LOCATION, player.getLocation().toContainer().toString());

        File file = getPlayerFile(playerId);
        try
        {
            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write(sjc.toJSONString());
            fw.close();
        }
        catch (IOException e)
        {
            player.sendMessage(TextUtils.redText(CommonMessages.fileSaveFailed(file)));
            return false;
        }

        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);

        DataManager dm = Sponge.getGame().getDataManager();
        player.setRawData(dm.getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());
        player.setRawData(dm.getManipulatorBuilder(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get().create().toContainer());
        return true;
    }
}
