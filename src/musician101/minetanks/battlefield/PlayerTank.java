package musician101.minetanks.battlefield;

import java.util.UUID;

import musician101.minetanks.tankinfo.tanks.ITank;

public class PlayerTank
{
	private UUID player;
	private ITank tank;
	private boolean isReady;
	private Team team;
	
	public PlayerTank(UUID player, Team team)
	{
		this.team = team;
		this.player = player;
		this.isReady = true;
	}
	
	public UUID getPlayerId()
	{
		return player;
	}
	
	public ITank getTank()
	{
		return tank;
	}
	
	public Team getTeam()
	{
		return team;
	}
	
	public void setTank(ITank tank)
	{
		this.tank = tank;
	}
	
	public void setTeam(Team team)
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
		this.team = Team.SPECTATOR;
		this.tank = null;
	}
	
	public enum Team
	{
		GREEN("Green Team", false),
		RED("Red Team", false),
		SPECTATOR("Spectators"),
		UNASSIGNED("Unassigned");
		
		private String name;
		private boolean canExit;
		
		private Team(String name)
		{
			this(name, true);
		}
		
		private Team(String name, boolean canExit)
		{
			this.name = name;
			this.canExit = canExit;
		}
		
		public String getName()
		{
			return name;
		}
		
		public boolean canExit()
		{
			return canExit;
		}
	}
}
