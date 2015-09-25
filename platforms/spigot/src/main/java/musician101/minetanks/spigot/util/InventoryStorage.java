package musician101.minetanks.spigot.util;

import musician101.minetanks.common.AbstractStorage;
import musician101.minetanks.spigot.MineTanks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
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

public class InventoryStorage extends AbstractStorage
{
    private final MineTanks plugin;

    public InventoryStorage(MineTanks plugin)
    {
        super(new File(plugin.getDataFolder(), "inventorystorage"));
        this.plugin = plugin;
    }

    public void returnInventory(Player player)
    {
        File file = getFile(player);
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
                player.getInventory().setItem(slot, yml.getItemStack("inventory." + slot));

            ItemStack[] armor = new ItemStack[4];
            for (int slot = 0; slot < armor.length; slot++)
                armor[slot] = yml.getItemStack("armor." + slot);

            player.getInventory().setArmorContents(armor);
            for (Map pe : yml.getMapList("effects"))
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(pe.get("type").toString().toUpperCase()), Integer.parseInt(pe.get("duration").toString()), Integer.parseInt(pe.get("amplifier").toString()), true), true);

            ConfigurationSection locCS = yml.getConfigurationSection("location");
            player.teleport(new Location(Bukkit.getWorld(locCS.getString("world")), locCS.getDouble("x"), locCS.getDouble("y"), locCS.getDouble("z"), Float.parseFloat(locCS.getString("yaw")), Float.parseFloat(locCS.getString("pitch"))));

            player.setExp(Float.parseFloat(yml.getString("xp")));
            file.delete();
        }
    }

    public boolean saveInventory(Player player)
    {
        File file = getFile(player);
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: An internal error occurred.");
            return false;
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        PlayerInventory inv = player.getInventory();
        for (int slot = 0; slot < inv.getSize(); slot++)
            yml.set("inventory." + slot, inv.getItem(slot));

        for (int slot = 0; slot < inv.getArmorContents().length; slot++)
            yml.set("armor." + slot, player.getInventory().getArmorContents()[slot]);

        List<Map<String, Object>> effects = new ArrayList<>();
        for (PotionEffect effect : player.getActivePotionEffects())
        {
            Map<String, Object> pe = new HashMap<>();
            pe.put("ambient", effect.isAmbient());
            pe.put("type", effect.getType().toString());
            pe.put("duration", effect.getDuration());
            pe.put("amplifier", effect.getAmplifier());
            effects.add(pe);
        }

        yml.set("effects", effects);

        Location loc = player.getLocation();
        Map<String, Object> pl = new HashMap<>();
        pl.put("world", loc.getWorld().getName());
        pl.put("x", loc.getX());
        pl.put("y", loc.getY());
        pl.put("z", loc.getZ());
        pl.put("yaw", loc.getYaw());
        pl.put("pitch", loc.getPitch());
        yml.set("location", pl);
        yml.set("xp", player.getExp());

        try
        {
            yml.save(file);
        }
        catch (IOException e)
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: An internal error has prevented you from joining the game.");
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

    public File getFile(Player player)
    {
        return new File(getStorageDir(), player.getUniqueId().toString() + ".yml");
    }
}
