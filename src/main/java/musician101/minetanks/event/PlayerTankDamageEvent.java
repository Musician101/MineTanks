package musician101.minetanks.event;

import java.util.UUID;

import musician101.minetanks.battlefield.Battlefield;

import org.spongepowered.api.util.event.Event;
import org.spongepowered.api.util.event.Result;

public class PlayerTankDamageEvent implements Event
{
	int damage;
	PlayerTankDamageCause cause;
	UUID dmgd;
	UUID dmgr;
	String field;
	
	public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID dmgd, Battlefield field, int damage)
	{
		this(cause, dmgd, null, field, damage);
	}
	
	public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID dmgd, UUID damager, Battlefield field, int damage)
	{
		this.dmgd = dmgd;
		this.dmgr = damager;
		this.field = field.getName();
		this.damage = damage;
		this.cause = cause;
	}
	
	public PlayerTankDamageCause getCause()
	{
		return cause;
	}
	
	public UUID getDamagedPlayer()
	{
		return dmgd;
	}
	
	public UUID getDamager()
	{
		return dmgr;
	}
	
	public String getField()
	{
		return field;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	@Override
	public boolean isCancellable()
	{
		return false;
	}

	@Override
	public Result getResult()
	{
		return Result.ALLOW;
	}

	@Override
	public void setResult(Result result)
	{
		//NOOP
	}
	
	public enum PlayerTankDamageCause
	{
		FALL,
		PENETRATION,
		RAM,
		SPLASH;
	}
}
