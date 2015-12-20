package musician101.minetanks.spigot.battlefield.player;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;

import java.util.UUID;

//TODO change this to SpigotPlayerTank
public class SpigotPlayerTank extends AbstractPlayerTank<SpigotTank>
{
    public SpigotPlayerTank(UUID player, MTTeam team)
    {
        super(player, team);
    }

    @Override
    public void setTank(SpigotTank tank)
    {
        super.setTank(tank);
        if (tank.getCannon() instanceof SpigotAutoLoader)
            this.clipSize = ((SpigotAutoLoader) tank.getCannon()).getClipSize();
        else
            this.clipSize = 1;
    }
}
