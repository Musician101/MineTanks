package musician101.minetanks.sponge.util;

import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.common.util.AbstractInventoryStorage;
import musician101.minetanks.sponge.SpongeMineTanks;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.Location;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Game game = SpongeMineTanks.getGame();
        Player player = game.getServer().getPlayer(playerId).get();
        if (!file.exists())
        {
            player.sendMessage(Texts.of(SpongeMineTanks.getPrefix() + "Your file could not be found. Please contact an administrator immediately."));
            return;
        }

        try
        {
            SpongeJSONConfig sjc = SpongeJSONConfig.load(file);
            for (ItemStack itemStack : sjc.getItemStackList("inventory"))
                player.getInventory().set(itemStack);

            if (sjc.containsKey("helmet"))
                player.setHelmet(sjc.getItemStack("helmet"));

            if (sjc.containsKey("chestplate"))
                player.setChestplate(sjc.getItemStack("chestplate"));

            if (sjc.containsKey("leggings"))
                player.setLeggings(sjc.getItemStack("leggings"));

            if (sjc.containsKey("boots"))
                player.setBoots(sjc.getItemStack("boots"));

            GameRegistry gr = game.getRegistry();
            PotionEffectData data = gr.getManipulatorRegistry().getBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create();
            data.set(gr.createValueBuilder().createListValue(Keys.POTION_EFFECTS, sjc.getPotionEffectsList("potion_effects")));
            player.setRawData(data.toContainer());
            file.delete();
        }
        catch (IOException | ParseException e)
        {
            player.sendMessage(Texts.of(SpongeMineTanks.getPrefix() + "There was an error reading your file. Please contact an administrator immediately."));
        }
    }

    @Override
    public boolean save(UUID playerId)
    {
        Player player = SpongeMineTanks.getGame().getServer().getPlayer(playerId).get();
        Inventory inv = player.getInventory();
        SpongeJSONConfig sjc = new SpongeJSONConfig();
        List<ItemStack> items = new ArrayList<>();
        for (Inventory slot : inv.slots())
            if (slot.peek().isPresent())
                items.add(slot.peek().get());

        sjc.setItemStackList("items", items);
        if (player.getHelmet().isPresent())
            sjc.setItemStack("helmet", player.getHelmet().get());

        if (player.getChestplate().isPresent())
            sjc.setItemStack("chestplate", player.getChestplate().get());

        if (player.getLeggings().isPresent())
            sjc.setItemStack("leggings", player.getLeggings().get());

        if (player.getBoots().isPresent())
            sjc.setItemStack("boots", player.getBoots().get());

        ExperienceHolderData exp = player.get(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get();
        sjc.set("exp", exp.totalExperience());

        PotionEffectData potions = player.get(CatalogEntityData.POTION_EFFECT_DATA).get();
        sjc.setPotionEffectsList("potion_effects", potions.effects().get());

        Location loc = player.getLocation();
        Map<String, Object> locMap = new HashMap<>();
        locMap.put("world", player.getWorld().getName());
        locMap.put("x", loc.getX());
        locMap.put("y", loc.getY());
        locMap.put("z", loc.getZ());
        sjc.set("location", locMap);

        try
        {
            File file = getPlayerFile(playerId);
            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write(sjc.toJSONString());
            fw.close();
        }
        catch (IOException e)
        {
            player.sendMessage(Texts.of(SpongeMineTanks.getPrefix() + " An error occurred while saving your data. You can not join a battlefield at this time."));
            return false;
        }

        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);

        player.setRawData(SpongeMineTanks.getGame().getRegistry().getManipulatorRegistry().getBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());
        player.setRawData(SpongeMineTanks.getGame().getRegistry().getManipulatorRegistry().getBuilder(CatalogEntityData.EXPERIENCE_HOLDER_DATA).get().create().toContainer());
        return true;
    }
}
