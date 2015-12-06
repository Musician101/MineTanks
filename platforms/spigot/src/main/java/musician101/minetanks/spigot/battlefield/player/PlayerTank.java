package musician101.minetanks.spigot.battlefield.player;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.spigot.tank.Tank;
import musician101.minetanks.spigot.tank.modules.cannon.AutoLoader;

import java.util.UUID;

//TODO change this to SpigotPlayerTank
public class PlayerTank extends AbstractPlayerTank<Tank>
{
    public PlayerTank(UUID player, MTTeam team)
    {
        super(player, team);
    }

    @Override
    public void setTank(Tank tank)
    {
        super.setTank(tank);
        if (tank.getCannon() instanceof AutoLoader)
            this.clipSize = ((AutoLoader) tank.getCannon()).getClipSize();
        else
            this.clipSize = 1;
    }
}
