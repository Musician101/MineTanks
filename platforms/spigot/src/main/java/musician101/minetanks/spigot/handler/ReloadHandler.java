package musician101.minetanks.spigot.handler;

import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReloadHandler
{
    private final SpigotMineTanks plugin;
    private final Player player;
    private int clipSize;
    private int cycleTime;
    private int maxClipSize;
    private int reloadTime;
    private int taskId;

    public ReloadHandler(SpigotMineTanks plugin, Player player, SpigotCannon cannon)
    {
        this.plugin = plugin;
        this.player = player;
        this.reloadTime = (int) cannon.getReloadTime();
        if (cannon instanceof SpigotAutoLoader)
        {
            SpigotAutoLoader al = (SpigotAutoLoader) cannon;
            this.cycleTime = (int) al.getCycleTime();
            this.clipSize = al.getClipSize();
            this.maxClipSize = al.getClipSize();
        }
        else
        {
            this.clipSize = 1;
            this.maxClipSize = 1;
        }
    }

    public boolean isReloading(SpigotPlayerTank pt)
    {
        if (player.getLevel() > 0)
            return true;

        int time;
        if (clipSize == 1 || pt.getClipSize() == maxClipSize)
            time = reloadTime;
        else
            time = cycleTime;

        player.setLevel(time);
        try
        {
            taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
            {
                player.setLevel(player.getLevel() - 1);
                if (player.getLevel() < 1)
                {
                    if (maxClipSize > 1)
                    {
                        if (pt.getClipSize() == 0)
                            //TODO check writing potion effects to file
                            pt.setClipSize(maxClipSize);
                        else
                            //TODO check win condition for draw
                            pt.setClipSize(pt.getClipSize() - 1);
                    }
                    Bukkit.getScheduler().cancelTask(taskId);
                }
                //TODO cancel all reloads when match ends
            }, 20, time);
        }
        catch (NullPointerException e)
        {
            //TODO debug code
            plugin.getLogger().info("quack");
        }
        return false;
    }
}
