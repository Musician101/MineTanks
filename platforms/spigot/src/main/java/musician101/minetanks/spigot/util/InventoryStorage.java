package musician101.minetanks.spigot.util;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonStorage;
import musician101.minetanks.common.util.AbstractInventoryStorage;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.SpigotReference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InventoryStorage extends AbstractInventoryStorage
{
    public InventoryStorage(SpigotMineTanks plugin)
    {
        super(new File(plugin.getDataFolder(), CommonStorage.INVENTORY));
    }

    @Override
    public void load(UUID playerId)
    {
        Player player = Bukkit.getPlayer(playerId);
        File file = getPlayerFile(playerId);
        if (file.exists())
        {
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            player.getInventory().clear();
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.SPEED);

            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            for (int slot = 0; slot < player.getInventory().getSize(); slot++)
                player.getInventory().setItem(slot, yml.getItemStack(CommonConfig.INVENTORY + slot));

            ItemStack[] armor = new ItemStack[4];
            for (int slot = 0; slot < armor.length; slot++)
                armor[slot] = yml.getItemStack(CommonConfig.ARMOR + slot);

            player.getInventory().setArmorContents(armor);
            for (Map pe : yml.getMapList(CommonConfig.EFFECTS))
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(pe.get(CommonConfig.TYPE).toString().toUpperCase()), Integer.parseInt(pe.get(CommonConfig.DURATION).toString()), Integer.parseInt(pe.get(CommonConfig.AMPLIFIER).toString()), true), true);

            player.teleport(Location.deserialize(yml.getConfigurationSection(CommonConfig.LOCATION).getValues(true)));
            player.setExp(Float.parseFloat(yml.getString(CommonConfig.XP)));
            file.delete();
        }
    }

    @Override
    public boolean save(UUID playerId)
    {
        Player player = Bukkit.getPlayer(playerId);
        File file = getPlayerFile(playerId);
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            player.sendMessage(ChatColor.RED + SpigotReference.file(CommonMessages.FILE_CREATE_FAIL, file));
            return false;
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        PlayerInventory inv = player.getInventory();
        for (int slot = 0; slot < inv.getSize(); slot++)
            yml.set(CommonConfig.INVENTORY + slot, inv.getItem(slot));

        for (int slot = 0; slot < inv.getArmorContents().length; slot++)
            yml.set(CommonConfig.ARMOR + slot, player.getInventory().getArmorContents()[slot]);

        List<Map<String, Object>> effects = new ArrayList<>();
        for (PotionEffect effect : player.getActivePotionEffects())
        {
            Map<String, Object> pe = new HashMap<>();
            pe.put(CommonConfig.TYPE, effect.getType().toString());
            pe.put(CommonConfig.DURATION, effect.getDuration());
            pe.put(CommonConfig.AMPLIFIER, effect.getAmplifier());
            effects.add(pe);
        }

        yml.set(CommonConfig.EFFECTS, effects);
        yml.set(CommonConfig.LOCATION, player.getLocation().serialize());
        yml.set(CommonConfig.XP, player.getExp());
        try
        {
            yml.save(file);
        }
        catch (IOException e)
        {
            player.sendMessage(ChatColor.RED + SpigotReference.file(CommonMessages.FILE_SAVE_FAIL, file));
            file.delete();
            return false;
        }

        player.setExp(0F);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        return true;
    }

    @Override
    public File getPlayerFile(UUID playerId)
    {
        return new File(getStorageDir(), CommonConfig.playerFileYAML(playerId));
    }
}
