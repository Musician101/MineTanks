package io.musician101.minetanks.sponge.util;

import com.google.common.reflect.TypeToken;
import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.util.AbstractInventoryStorage;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SpongeInventoryStorage extends AbstractInventoryStorage<Player>
{

    public SpongeInventoryStorage(File storageDir)
    {
        super(storageDir);
    }

    private ItemStack getItem(ConfigurationNode node)
    {
        return ItemStack.builder().fromContainer(DataTranslators.CONFIGURATION_NODE.translate(node)).build();
    }

    @Override
    public void load(Player player)//NOSONAR
    {
        File file = getPlayerFile(player.getUniqueId());
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

            DataTranslator<ConfigurationNode> dt = DataTranslators.CONFIGURATION_NODE;
            if (!node.getNode(CommonConfig.EFFECTS).isVirtual())
            {
                ConfigurationNode effectsNode = node.getNode(CommonConfig.EFFECTS);
                if (!effectsNode.isVirtual() && effectsNode.hasListChildren())
                    Sponge.getDataManager().getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).ifPresent(builder ->
                            builder.build(dt.translate(effectsNode)).ifPresent(potionEffectData ->
                                    player.setRawData(potionEffectData.toContainer())));
            }

            if (!node.getNode(CommonConfig.XP).isVirtual())
            {
                ConfigurationNode xpNode = node.getNode(CommonConfig.XP);
                if (!xpNode.isVirtual() && xpNode.hasMapChildren())
                    Sponge.getDataManager().getManipulatorBuilder(CatalogEntityData.EXPERIENCE_HOLDER_DATA).ifPresent(builder ->
                            builder.build(dt.translate(xpNode)).ifPresent(experienceHolderData ->
                                    player.setRawData(experienceHolderData.toContainer())));
            }

            //noinspection ResultOfMethodCallIgnored
            file.delete();//NOSONAR
        }
        catch (IOException | ObjectMappingException e)//NOSONAR
        {
            player.sendMessage(TextUtils.redText(CommonMessages.fileLoadFailed(file)));
        }
    }

    @Override
    public boolean save(Player player)
    {
        File file = getPlayerFile(player.getUniqueId());
        ConfigurationLoader<CommentedConfigurationNode> cl = HoconConfigurationLoader.builder().setFile(file).build();
        Inventory inv = player.getInventory();
        try
        {
            ConfigurationNode node = cl.load();
            DataTranslator<ConfigurationNode> dt = DataTranslators.CONFIGURATION_NODE;
            List<ConfigurationNode> items = new ArrayList<>();
            for (Inventory slot : inv.slots())
                slot.peek().ifPresent(itemStack -> items.add(dt.translate(itemStack.toContainer())));

            node.getNode(CommonConfig.INVENTORY.replace(".", "")).setValue(items);
            player.getHelmet().ifPresent(helmet ->
                    node.getNode(CommonConfig.HELMET).setValue(dt.translate(helmet.toContainer())));

            player.getChestplate().ifPresent(chestplate ->
                    node.getNode(CommonConfig.CHESTPLATE).setValue(dt.translate(chestplate.toContainer())));

            player.getLeggings().ifPresent(leggings ->
                    node.getNode(CommonConfig.LEGGINGS).setValue(dt.translate(leggings.toContainer())));

            player.getBoots().ifPresent(boots ->
                    node.getNode(CommonConfig.BOOTS).setValue(dt.translate(boots.toContainer())));

            player.get(CatalogEntityData.EXPERIENCE_HOLDER_DATA).ifPresent(experienceHolderData ->
                    node.getNode(CommonConfig.XP).setValue(dt.translate(experienceHolderData.toContainer())));

            player.get(CatalogEntityData.POTION_EFFECT_DATA).ifPresent(potionEffectData ->
                    node.getNode(CommonConfig.EFFECTS).setValue(dt.translate(potionEffectData.toContainer())));

            node.getNode(CommonConfig.LOCATION).setValue(player.getLocation().toContainer());
            cl.save(node);
        }
        catch (IOException e)//NOSONAR
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
        dm.getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).ifPresent(builder -> player.setRawData(builder.create().toContainer()));
        dm.getManipulatorBuilder(CatalogEntityData.EXPERIENCE_HOLDER_DATA).ifPresent(builder -> player.setRawData(builder.create().toContainer()));
        return true;
    }
}
