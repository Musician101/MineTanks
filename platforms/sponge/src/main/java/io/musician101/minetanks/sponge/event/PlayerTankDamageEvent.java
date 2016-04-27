package io.musician101.minetanks.sponge.event;

import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.impl.AbstractEvent;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PlayerTankDamageEvent extends AbstractEvent
{
    private final int damage;
    private final PlayerTankDamageCause damageType;
    private final UUID damaged;
    private final UUID damager;
    private final String field;

    public PlayerTankDamageEvent(PlayerTankDamageCause damageType, UUID damaged, SpongeBattleField field, int damage)
    {
        this(damageType, damaged, null, field, damage);
    }

    public PlayerTankDamageEvent(PlayerTankDamageCause damageType, UUID damaged, UUID damager, SpongeBattleField field, int damage)
    {
        super();
        this.damaged = damaged;
        this.damager = damager;
        this.field = field.getName();
        this.damage = damage;
        this.damageType = damageType;
    }

    @Nonnull
    @Override
    public Cause getCause()
    {
        return Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(damager)), NamedCause.of("PlayerTankDamageCause", damageType), NamedCause.simulated(Sponge.getServer().getPlayer(damaged)));
    }

    public int getDamage()
    {
        return damage;
    }

    public PlayerTankDamageCause getDamageType()
    {
        return damageType;
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

    public enum PlayerTankDamageCause
    {
        FALL,
        PENETRATION,
        RAM,
        SPLASH
    }
}
