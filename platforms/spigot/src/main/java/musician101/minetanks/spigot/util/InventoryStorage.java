package musician101.minetanks.spigot.util;

import musician101.minetanks.spigot.MineTanks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class InventoryStorage
{
    File storageDir;
    MineTanks plugin;

    public InventoryStorage(MineTanks plugin)
    {
        this.plugin = plugin;
        storageDir = new File(plugin.getDataFolder(), "inventorystorage");
        storageDir.mkdirs();
    }

    public void saveInventory(Player player)
    {
        File file = new File(storageDir, player.getUniqueId() + ".yml");
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: An internal error occurred.");
            return;
        }
    }
}
