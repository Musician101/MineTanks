package musician101.minetanks.spigot.battlefield.player;

import musician101.minetanks.common.AbstractPlayerTank;
import musician101.minetanks.common.tank.*;
import musician101.minetanks.spigot.tank.Tank;
import musician101.minetanks.spigot.tank.modules.cannon.*;

import java.util.UUID;

public class PlayerTank extends AbstractPlayerTank
{
	public PlayerTank(UUID player, MTTeam team)
	{
        super(player, team);
	}

	public Tank getTank()
	{
		return (Tank) tank;
	}

    @Override
    public void setTank(AbstractTank tank)
    {
        this.tank = tank;
        Tank t = (Tank) tank;
        if (t.getCannon() instanceof AutoLoader)
            this.clipSize = ((AutoLoader) t.getCannon()).getClipSize();
        else
            this.clipSize = 1;
    }
}
