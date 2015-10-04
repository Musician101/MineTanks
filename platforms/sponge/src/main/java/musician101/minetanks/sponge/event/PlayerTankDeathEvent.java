package musician101.minetanks.sponge.event;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.event.Event;
import org.spongepowered.api.util.event.callback.CallbackList;

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
    public CallbackList getCallbacks()
    {
        return null;
    }
}
