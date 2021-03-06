package io.musician101.minetanks.spigot.battlefield.player;

import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.tank.SpigotTank;
import io.musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import io.musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpigotPlayerTank extends AbstractPlayerTank<SpigotTank, Integer> {

    public SpigotPlayerTank(UUID player, MTTeam team) {
        super(player, team);
    }

    @Override
    public void cancelReload() {
        if (reloadTaskID != -1) {
            Bukkit.getScheduler().cancelTask(reloadTaskID);
            reloadTaskID = -1;
        }
    }

    @Override
    public boolean isReloading() {
        Player player = Bukkit.getPlayer(getPlayerId());
        if (player.getLevel() > 0)
            return true;

        SpigotCannon cannon = getTank().getCannon();
        //TODO reload timers still not working for some reason
        //int time = (int) (!(cannon instanceof SpigotAutoLoader) || clipSize == 1 ? cannon.getReloadTime() : ((SpigotAutoLoader) cannon).getCycleTime());
        int time;
        if (clipSize == 1) {
            if (!(cannon instanceof SpigotAutoLoader))
                time = (int) (cannon.getReloadTime() * 2);
            else
                time = (int) (cannon.getReloadTime() / 2);
        }
        else {
            time = (int) (((SpigotAutoLoader) cannon).getCycleTime() * 2);
        }

        player.setLevel(time);
        reloadTaskID = new BukkitRunnable() {
            @Override
            public void run() {
                player.setLevel(player.getLevel() - 1);
                Bukkit.getLogger().info(player.getName() + " " + player.getLevel());
                if (player.getLevel() < 1)
                    cancelReload();
            }
        }.runTaskTimer(SpigotMineTanks.instance(), 1L, time).getTaskId();
        return false;
    }

    @Override
    public void killed() {
        super.killed();
        cancelReload();
    }

    @Override
    public void setTank(SpigotTank tank) {
        super.setTank(tank);
        if (tank == null)
            clipSize = 0;
        else if (tank.getCannon() instanceof SpigotAutoLoader)
            this.clipSize = ((SpigotAutoLoader) tank.getCannon()).getClipSize();
        else
            this.clipSize = 1;
    }
}
