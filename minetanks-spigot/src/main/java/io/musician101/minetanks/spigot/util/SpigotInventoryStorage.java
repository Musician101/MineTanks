package io.musician101.minetanks.spigot.util;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonStorage;
import io.musician101.minetanks.common.util.AbstractInventoryStorage;
import io.musician101.minetanks.spigot.SpigotMineTanks;
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

public class SpigotInventoryStorage extends AbstractInventoryStorage<Player>
{
    public SpigotInventoryStorage(SpigotMineTanks plugin)
    {
        super(new File(plugin.getDataFolder(), CommonStorage.INVENTORY));
    }

    @Override
    public void load(Player player)
    {
        File file = getPlayerFile(player.getUniqueId());
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
                player.addPotionEffect(PotionEffectType.getByName(pe.get(CommonConfig.TYPE).toString().toUpperCase()).createEffect(Integer.parseInt(pe.get(CommonConfig.DURATION).toString()), Integer.parseInt(pe.get(CommonConfig.AMPLIFIER).toString())), true);

            //TODO save player's gamemode
            //player.teleport(Location.deserialize(yml.getConfigurationSection(CommonConfig.LOCATION).getValues(true)));
            Location location = Location.deserialize(yml.getConfigurationSection(CommonConfig.LOCATION).getValues(true));
            player.sendMessage("world: " + location.getWorld().getName());
            player.sendMessage("x: " + location.getX());
            player.sendMessage("y: " + location.getY());
            player.sendMessage("z: " + location.getZ());
            player.sendMessage("yaw: " + location.getYaw());
            player.sendMessage("pitch: " + location.getPitch());
            player.teleport(location);
            player.setExp(Float.parseFloat(yml.getString(CommonConfig.XP)));
            //noinspection ResultOfMethodCallIgnored
            file.delete();//NOSONAR
        }
    }

    @Override
    public boolean save(Player player)
    {
        //TODO need to save offhand item to inventory files
        //TODO save player's gamemode
        File file = getPlayerFile(player.getUniqueId());
        try
        {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();//NOSONAR
        }
        catch (IOException e)//NOSONAR
        {
            player.sendMessage(ChatColor.RED + CommonMessages.fileSaveFailed(file));
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
            pe.put(CommonConfig.TYPE, effect.getType().getName());
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
        catch (IOException e)//NOSONAR
        {
            player.sendMessage(ChatColor.RED + CommonMessages.fileSaveFailed(file));
            //noinspection ResultOfMethodCallIgnored
            file.delete();//NOSONAR
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
}
