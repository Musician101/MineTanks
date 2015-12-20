package musician101.minetanks.sponge.event;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.impl.AbstractEvent;

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
