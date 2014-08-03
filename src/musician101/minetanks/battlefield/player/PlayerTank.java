package musician101.minetanks.battlefield.player;

import java.util.UUID;

import musician101.minetanks.tankinfo.tanks.ITank;

public class PlayerTank
{
	private ITank tank;
	private boolean isReady;
	private MTTeam team;
	
	public PlayerTank(UUID player, MTTeam team)
	{
		this.team = team;
		this.isReady = true;
	}
	
	public ITank getTank()
	{
		return tank;
	}
	
	public MTTeam getTeam()
	{
		return team;
	}
	
	public void setTank(ITank tank)
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
		
		private boolean canExit;
		
		private MTTeam()
		{
			this(true);
		}
		
		private MTTeam(boolean canExit)
		{
			this.canExit = canExit;
		}
		
		public boolean canExit()
		{
			return canExit;
		}
	}
}
