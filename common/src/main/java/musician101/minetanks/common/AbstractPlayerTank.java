package musician101.minetanks.common;

import musician101.minetanks.common.tank.AbstractTank;

import java.util.UUID;

public abstract class AbstractPlayerTank
{
	boolean isReady = false;
	MTTeam team;
    protected AbstractTank tank;
    UUID player;
	
	protected AbstractPlayerTank(UUID player, MTTeam team)
	{
        this.player = player;
		this.team = team;
	}
	
	public MTTeam getTeam()
	{
		return team;
	}
	
	public void setTank(AbstractTank tank)
	{
		this.tank = tank;
	}
	
	public void setTeam(MTTeam team)
	{
		this.team = team;
	}
	
	public boolean isReady()
	{
		return isReady;
	}
	
	public void setReady(boolean isReady)
	{
		this.isReady = isReady;
	}
	
	public void killed()
	{
		this.team = MTTeam.SPECTATOR;
		this.tank = null;
	}
	
	public enum MTTeam
	{
		ASSIGNED(false),
		SPECTATOR(),
		UNASSIGNED();
		
		boolean canExit;
		
		MTTeam()
		{
			this(true);
		}
		
		MTTeam(boolean canExit)
		{
			this.canExit = canExit;
		}
		
		public boolean canExit()
		{
			return canExit;
		}
	}
}
