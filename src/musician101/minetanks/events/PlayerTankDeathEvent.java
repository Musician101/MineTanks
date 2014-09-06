package musician101.minetanks.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTankDeathEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	String field;
	Player killed;
	Player killer;
	
	public PlayerTankDeathEvent(String field, Player killed, Player killer)
	{
		this.field = field;
		this.killed = killed;
		this.killer = killer;
	}
	
	public String getField()
	{
		return field;
	}
	
	public Player getKilled()
	{
		return killed;
	}
	
	public Player getKiller()
	{
		return killer;
	}
	
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}
