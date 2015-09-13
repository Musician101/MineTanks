package musician101.minetanks.spigot.handlers;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.tank.modules.cannon.AutoLoader;
import musician101.minetanks.spigot.tank.modules.cannon.Cannon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class ReloadHandler
{
    MineTanks plugin;
    Player player;
    int reloadTime, cycleTime, clipSize, maxClipSize;

    public ReloadHandler(MineTanks plugin, Player player, Cannon cannon)
    {
        this.plugin = plugin;
        this.player = player;
        this.reloadTime = (int) Math.round(cannon.getReloadTime());
        if (cannon instanceof AutoLoader)
        {
            AutoLoader al = (AutoLoader) cannon;
            this.cycleTime = (int) Math.round(al.getCycleTime());
            this.clipSize = al.getClipSize();
            this.maxClipSize = al.getClipSize();
        } else
        {
            this.clipSize = 1;
            this.maxClipSize = 1;
        }
    }

    public boolean isReloading()
    {
        if (player.getLevel() > 0)
            return true;

        int time;
        if (clipSize == 0)
            time = reloadTime;
        else
            time = cycleTime;

        player.setLevel(time);
        try
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    player.setLevel(player.getLevel() - 1);
                    if (player.getLevel() == 0)
                    {
                        if (maxClipSize > 1 && clipSize == 0)
                        {
                            for (ItemStack item : player.getInventory().getContents())
                            {
                                if (item != null && item.getType() == Material.BOW)
                                {
                                    ItemMeta meta = item.getItemMeta();
                                    meta.setLore(Arrays.asList("Your Cannon", "Clip Size: " + maxClipSize + "/" + maxClipSize, "Cycle Time: " + cycleTime, "Clip Reload Time: " + reloadTime));
                                    item.setItemMeta(meta);
                                }
                            }

                            for (String name : plugin.getFieldStorage().getFields().keySet())
                            {
                                BattleField bf = plugin.getFieldStorage().getField(name);
                                if (bf.getPlayer(player.getUniqueId()) != null)
                                {
                                    bf.getPlayer(player.getUniqueId()).setClipSize(maxClipSize);
                                }
                            }
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 1L, time);
        }
        catch (NullPointerException e)
        {
        }
        return false;
    }
}
