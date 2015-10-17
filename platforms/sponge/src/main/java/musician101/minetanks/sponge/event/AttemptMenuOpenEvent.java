package musician101.minetanks.sponge.event;

import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.item.ItemType;

import java.util.UUID;

public class AttemptMenuOpenEvent extends AbstractEvent implements Cancellable
{
    boolean isCancelled = false;
    ItemType material;
    String field;
    SpongePlayerTank pt;
    UUID player;

    public AttemptMenuOpenEvent(ItemType type, String field, SpongePlayerTank pt, UUID player)
    {
        super();
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
    public boolean isCancelled()
    {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled)
    {
        this.isCancelled = isCancelled;
    }
}
