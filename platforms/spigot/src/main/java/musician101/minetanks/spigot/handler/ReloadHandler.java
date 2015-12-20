package musician101.minetanks.spigot.handler;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class ReloadHandler
{
    private final SpigotMineTanks plugin;
    private final Player player;
    private final int reloadTime;
    private final int clipSize;
    private final int maxClipSize;
    private int cycleTime;

    public ReloadHandler(SpigotMineTanks plugin, Player player, SpigotCannon cannon)
    {
        this.plugin = plugin;
        this.player = player;
        this.reloadTime = (int) Math.round(cannon.getReloadTime());
        if (cannon instanceof SpigotAutoLoader)
        {
            SpigotAutoLoader al = (SpigotAutoLoader) cannon;
            this.cycleTime = (int) Math.round(al.getCycleTime());
            this.clipSize = al.getClipSize();
            this.maxClipSize = al.getClipSize();
        }
        else
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
                                    meta.setLore(Arrays.asList(CommonItemText.CANNON, CommonItemText.clipSize(maxClipSize, maxClipSize), CommonItemText.cycleTime(cycleTime), CommonItemText.reloadTime(reloadTime)));
                                    item.setItemMeta(meta);
                                }
                            }

                            for (String name : plugin.getFieldStorage().getFields().keySet())
                            {
                                SpigotBattleField bf = plugin.getFieldStorage().getField(name);
                                if (bf.getPlayerTank(player.getUniqueId()) != null)
                                {
                                    bf.getPlayerTank(player.getUniqueId()).setClipSize(maxClipSize);
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
            //TODO debug code
            plugin.getLogger().info("quack");
        }
        return false;
    }
}
