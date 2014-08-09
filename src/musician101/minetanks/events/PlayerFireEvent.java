package musician101.minetanks.events;

import musician101.minetanks.battlefield.player.PlayerTank;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerFireEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	Player player;
	PlayerTank pt;
	boolean isCancelled = true;
	
	public PlayerFireEvent(Player player, PlayerTank pt)
	{
		this.player = player;
		this.pt = pt;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public PlayerTank getPlayerTank()
	{
		return pt;
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

	@Override
	public boolean isCancelled()
	{
		return false;
	}

	@Override
	public void setCancelled(boolean isCancelled)
	{
		this.isCancelled = isCancelled;
	}
}
