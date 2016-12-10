package io.musician101.minetanks.sponge.battlefield.player;

import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.tank.SpongeTank;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.scheduler.Task;

public class SpongePlayerTank extends AbstractPlayerTank<SpongeTank, UUID> {

    public SpongePlayerTank(UUID uuid, MTTeam team) {
        super(uuid, team);
    }

    @Override
    public void cancelReload() {
        if (reloadTaskID != null) {
            Sponge.getScheduler().getTaskById(reloadTaskID).ifPresent(Task::cancel);
            reloadTaskID = null;
        }
    }

    @Override
    public boolean isReloading() {
        Sponge.getServer().getPlayer(getPlayerId()).ifPresent(player ->
        {
            SpongeCannon cannon = getTank().getCannon();
            int time;
            if (clipSize == 1) {
                if (cannon instanceof SpongeAutoLoader)
                    time = (int) (cannon.getReloadTime() / 2);
                else
                    time = (int) cannon.getReloadTime() * 2;
            }
            else
                time = (int) (((SpongeAutoLoader) cannon).getCycleTime() * 2);

            player.offer(Keys.EXPERIENCE_LEVEL, time);
            reloadTaskID = Task.builder()
                    .execute(() ->
                            player.get(Keys.EXPERIENCE_LEVEL).ifPresent(level ->
                            {
                                int newLevel = level - 1;
                                player.offer(Keys.EXPERIENCE_LEVEL, newLevel);
                                if (newLevel < 1)
                                    cancelReload();
                            }))
                    .name("SpongeMineTanks-IsReloading-" + player.getName())
                    .delay(1, TimeUnit.SECONDS)
                    .interval(time, TimeUnit.SECONDS)
                    .submit(SpongeMineTanks.class).getUniqueId();
        });
        return false;
    }

    @Override
    public void setTank(SpongeTank tank) {
        super.setTank(tank);
        if (tank == null)
            clipSize = 0;
        else if (tank.getCannon() instanceof SpongeAutoLoader)
            this.clipSize = ((SpongeAutoLoader) tank.getCannon()).getClipSize();
        else
            this.clipSize = 1;
    }
}
