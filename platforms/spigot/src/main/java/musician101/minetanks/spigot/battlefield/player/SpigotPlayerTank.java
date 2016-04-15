package musician101.minetanks.spigot.battlefield.player;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SpigotPlayerTank extends AbstractPlayerTank<SpigotTank, SpigotMineTanks, Integer>
{
    public SpigotPlayerTank(UUID player, MTTeam team)
    {
        super(player, team);
    }

    @Override
    public void setTank(SpigotTank tank)
    {
        super.setTank(tank);
        if (tank == null)
        {
            clipSize = 0;
            return;
        }

        if (tank.getCannon() instanceof SpigotAutoLoader)
            this.clipSize = ((SpigotAutoLoader) tank.getCannon()).getClipSize();
        else
            this.clipSize = 1;
    }

    @Override
    public boolean isReloading(SpigotMineTanks plugin)
    {
        Player player = Bukkit.getPlayer(getPlayerId());
        if (player.getLevel() > 0)
            return true;

        SpigotCannon cannon = getTank().getCannon();
        int maxClipSize = cannon instanceof SpigotAutoLoader ? ((SpigotAutoLoader) cannon).getClipSize() : 1;
        int time = (int) (!(cannon instanceof SpigotAutoLoader) || clipSize == 1 || clipSize == maxClipSize ? cannon.getReloadTime() : ((SpigotAutoLoader) cannon).getCycleTime());
        player.setLevel(time);
        reloadTaskID = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.setLevel(player.getLevel() - 1);
                if (player.getLevel() < 1)
                {
                    if (maxClipSize > 1)
                    {
                        if (clipSize == 0)
                            clipSize = maxClipSize;
                        else
                            clipSize--;
                    }
                }

                reloadTaskID = -1;
                cancel();
            }
        }.runTaskTimerAsynchronously(plugin, 1, time).getTaskId();
        return false;
    }

    @Override
    public void cancelReload()
    {
        if (reloadTaskID != -1)
            Bukkit.getScheduler().cancelTask(reloadTaskID);
    }

    @Override
    public void killed()
    {
        super.killed();
        cancelReload();
    }
}
