package musician101.minetanks.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTankDeathEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	String field;
	Player player;
	
	public PlayerTankDeathEvent(String field, Player player)
	{
		this.field = field;
		this.player = player;
	}
	
	public String getField()
	{
		return field;
	}
	
	public Player getPlayer()
	{
		return player;
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
