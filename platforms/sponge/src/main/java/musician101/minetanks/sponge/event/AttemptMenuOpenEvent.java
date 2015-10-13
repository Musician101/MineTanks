package musician101.minetanks.sponge.event;

import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.util.event.Event;
import org.spongepowered.api.util.event.callback.CallbackList;

import java.util.UUID;

public class AttemptMenuOpenEvent implements Event
{
    boolean isCancelled;
    ItemType material;
    String field;
    SpongePlayerTank pt;
    UUID player;

    public AttemptMenuOpenEvent(ItemType type, String field, SpongePlayerTank pt, UUID player)
    {
        this.material = type;
        this.field = field;
        this.pt = pt;
        this.player = player;

    }

    public ItemType getItemType()
    {
        return material;
    }

    public String getField()
    {
        return field;
    }

    public SpongePlayerTank getPlayerTank()
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
