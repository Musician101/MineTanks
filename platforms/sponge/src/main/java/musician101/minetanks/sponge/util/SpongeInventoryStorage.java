package musician101.minetanks.sponge.util;

import com.google.common.reflect.TypeToken;
import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.util.AbstractInventoryStorage;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.data.translator.ConfigurateTranslator;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Player player = MTUtils.getPlayer(playerId);
        if (!file.exists())
        {
            player.sendMessage(TextUtils.redText(CommonMessages.fileLoadFailed(file)));
            return;
        }

        try
        {
            ConfigurationLoader<CommentedConfigurationNode> cl = HoconConfigurationLoader.builder().setFile(file).build();
            ConfigurationNode node = cl.load();
            node.getNode(CommonConfig.INVENTORY.replace(".", "")).getList(TypeToken.of(ConfigurationNode.class)).forEach(itemNode -> player.getInventory().set(getItem(itemNode)));

            if (!node.getNode(CommonConfig.HELMET).isVirtual())
                player.setHelmet(getItem(node.getNode(CommonConfig.HELMET)));

            if (!node.getNode(CommonConfig.CHESTPLATE).isVirtual())
                player.setChestplate(getItem(node.getNode(CommonConfig.CHESTPLATE)));

            if (!node.getNode(CommonConfig.LEGGINGS).isVirtual())
                player.setLeggings(getItem(node.getNode(CommonConfig.LEGGINGS)));

            if (!node.getNode(CommonConfig.BOOTS).isVirtual())
                player.setBoots(getItem(node.getNode(CommonConfig.BOOTS)));

            ConfigurateTranslator ct = ConfigurateTranslator.instance();
            PotionEffectData potionEffectData = Sponge.getDataManager().getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create();
            List<PotionEffect> effects = new ArrayList<>();
            node.getNode(CommonConfig.EFFECTS).getList(TypeToken.of(ConfigurationNode.class)).forEach(effect -> effects.add(PotionEffect.builder().build(ct.translateFrom(effect)).get()));
            potionEffectData.set(Sponge.getGame().getRegistry().getValueFactory().createListValue(Keys.POTION_EFFECTS, effects));
            ExperienceHolderData experienceHolderData = Sponge.getDataManager().getManipulatorBuilder(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get().create();
            experienceHolderData.from((DataContainer) ct.translateFrom(node.getNode(CommonConfig.XP)));
            DataContainer data = new MemoryDataContainer();
            data.set(DataQuery.of(), potionEffectData);
            data.set(DataQuery.of(), experienceHolderData);
            player.setRawData(data);
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
        catch (IOException | ObjectMappingException e)
        {
            player.sendMessage(TextUtils.redText(CommonMessages.fileLoadFailed(file)));
        }
    }

    private ItemStack getItem(ConfigurationNode node)
    {
        return ItemStack.builder().fromContainer(ConfigurateTranslator.instance().translateFrom(node)).build();
    }

    @Override
    public boolean save(UUID playerId)
    {
        Player player = MTUtils.getPlayer(playerId);
        File file = getPlayerFile(playerId);
        ConfigurationLoader<CommentedConfigurationNode> cl = HoconConfigurationLoader.builder().setFile(file).build();
        Inventory inv = player.getInventory();
        try
        {
            ConfigurationNode node = cl.load();
            ConfigurateTranslator ct = ConfigurateTranslator.instance();
            List<ConfigurationNode> items = new ArrayList<>();
            for (Inventory slot : inv.slots())
            {
                Optional<ItemStack> itemStackOptional = slot.peek();
                if (itemStackOptional.isPresent())
                    items.add(ct.translateData(itemStackOptional.get().toContainer()));
            }

            node.getNode(CommonConfig.INVENTORY.replace(".", "")).setValue(items);
            if (player.getHelmet().isPresent())
                node.getNode(CommonConfig.HELMET).setValue(ct.translateData(player.getHelmet().get().toContainer()));

            if (player.getChestplate().isPresent())
                node.getNode(CommonConfig.CHESTPLATE).setValue(ct.translateData(player.getChestplate().get().toContainer()));

            if (player.getLeggings().isPresent())
                node.getNode(CommonConfig.LEGGINGS).setValue(ct.translateData(player.getLeggings().get().toContainer()));

            if (player.getBoots().isPresent())
                node.getNode(CommonConfig.BOOTS).setValue(ct.translateData(player.getBoots().get().toContainer()));

            ExperienceHolderData exp = player.get(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get();
            node.getNode(CommonConfig.XP).setValue(ct.translateData(exp.toContainer()));

            PotionEffectData potions = player.get(CatalogEntityData.POTION_EFFECT_DATA).get();
            node.getNode(CommonConfig.EFFECTS).setValue(ct.translateData(potions.toContainer()));
            node.getNode(CommonConfig.LOCATION).setValue(player.getLocation().toContainer());
            cl.save(node);
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

        DataManager dm = Sponge.getDataManager();
        player.setRawData(dm.getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());
        player.setRawData(dm.getManipulatorBuilder(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get().create().toContainer());
        return true;
    }
}
