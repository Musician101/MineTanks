package io.musician101.minetanks.sponge.event;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.impl.AbstractEvent;

import javax.annotation.Nonnull;

public class PlayerTankDeathEvent extends AbstractEvent
{
    private final String field;
    private final Player killed;
    private final Player killer;

    public PlayerTankDeathEvent(String field, Player killed, Player killer)
    {
        super();
        this.field = field;
        this.killed = killed;
        this.killer = killer;
    }

    @Nonnull
    @Override
    public Cause getCause()
    {
        return Cause.of(NamedCause.simulated(killed), NamedCause.simulated(killer));
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
}
