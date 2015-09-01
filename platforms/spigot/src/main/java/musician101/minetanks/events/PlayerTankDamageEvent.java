package musician101.minetanks.events;

import java.util.UUID;

import musician101.minetanks.battlefield.Battlefield;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTankDamageEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
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
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	
	public enum PlayerTankDamageCause
	{
		FALL,
		PENETRATION,
		RAM,
		SPLASH;
	}
}