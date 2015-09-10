package musician101.minetanks.battlefield.player;

import musician101.minetanks.common.AbstractPlayerTank;
import musician101.minetanks.tank.Tank;

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
}
