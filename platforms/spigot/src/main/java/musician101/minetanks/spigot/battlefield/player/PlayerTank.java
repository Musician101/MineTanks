package musician101.minetanks.spigot.battlefield.player;

import musician101.minetanks.common.AbstractPlayerTank;
import musician101.minetanks.spigot.tank.Tank;

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
