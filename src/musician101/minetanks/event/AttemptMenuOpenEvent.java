package musician101.minetanks.event;

import java.util.UUID;

import musician101.minetanks.battlefield.player.PlayerTank;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AttemptMenuOpenEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	Material material;
	String field;
	PlayerTank pt;
	UUID player;
	
	public AttemptMenuOpenEvent(Material material, String field, PlayerTank pt, UUID player)
	{
		this.material = material;
		this.field = field;
		this.pt = pt;
		this.player = player;
	}
	
	public Material getMaterial()
	{
		return material;
	}
	
	public String getField()
	{
		return field;
	}
	
	public PlayerTank getPlayerTank()
	{
		return pt;
	}
	
	public UUID getPlayer()
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