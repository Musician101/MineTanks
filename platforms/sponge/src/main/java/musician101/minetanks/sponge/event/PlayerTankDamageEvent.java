package musician101.minetanks.sponge.event;

import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import org.spongepowered.api.event.impl.AbstractEvent;

import java.util.UUID;

public class PlayerTankDamageEvent extends AbstractEvent
{
    private final int damage;
    private final PlayerTankDamageCause cause;
    private final UUID damaged;
    private final UUID damager;
    private final String field;

    public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID damaged, SpongeBattleField field, int damage)
    {
        this(cause, damaged, null, field, damage);
    }

    public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID damaged, UUID damager, SpongeBattleField field, int damage)
    {
        super();
        this.damaged = damaged;
        this.damager = damager;
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

    public enum PlayerTankDamageCause
    {
        FALL,
        PENETRATION,
        RAM,
        SPLASH
    }
}
