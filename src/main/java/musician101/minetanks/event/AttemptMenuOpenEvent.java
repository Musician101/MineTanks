package musician101.minetanks.event;

import java.util.UUID;

import musician101.minetanks.battlefield.player.PlayerTank;

import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.util.event.Event;
import org.spongepowered.api.util.event.callback.CallbackList;

public class AttemptMenuOpenEvent implements Event
{
	boolean isCancelled;
	ItemTypes material;
	String field;
	PlayerTank pt;
	UUID player;
	
	public AttemptMenuOpenEvent(ItemTypes type, String field, PlayerTank pt, UUID player)
	{
		this.material = type;
		this.field = field;
		this.pt = pt;
		this.player = player;
		
	}
	
	public ItemTypes getMaterial()
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
	public CallbackList getCallbacks()
	{
		return null;
	}
}
