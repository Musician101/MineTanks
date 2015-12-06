package musician101.minetanks.spigot.event;

import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PlayerTankDamageEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final int damage;
    private final PlayerTankDamageCause cause;
    private final UUID damaged;
    private final UUID damager;
    private final String field;

    public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID damaged, SpigotBattleField field, int damage)
    {
        this(cause, damaged, null, field, damage);
    }

    public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID damaged, UUID damager, SpigotBattleField field, int damage)
    {
        this.damaged = damaged;
        this.damager = damager;
        this.field = field.getName();
        this.damage = damage;
        this.cause = cause;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public PlayerTankDamageCause getCause()
    {
        return cause;
    }

    public UUID getDamagedPlayer()
    {
        return damaged;
    }

    public UUID getDamager()
    {
        return damager;
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

    public enum PlayerTankDamageCause
    {
        FALL,
        PENETRATION,
        RAM,
        SPLASH
    }
}
