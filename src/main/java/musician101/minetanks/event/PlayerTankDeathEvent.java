package musician101.minetanks.event;

import org.spongepowered.api.entity.Player;
import org.spongepowered.api.util.event.Event;
import org.spongepowered.api.util.event.Result;

public class PlayerTankDeathEvent implements Event
{
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
	public boolean isCancellable()
	{
		return false;
	}

	@Override
	public Result getResult()
	{
		return null;
	}

	@Override
	public void setResult(Result result)
	{
		//NOOP
	}
}
