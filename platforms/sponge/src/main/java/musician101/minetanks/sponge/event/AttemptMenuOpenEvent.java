package musician101.minetanks.sponge.event;

import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.item.ItemType;

import javax.annotation.Nonnull;
import java.util.UUID;

public class AttemptMenuOpenEvent extends AbstractEvent implements Cancellable
{
    private boolean isCancelled = false;
    private final ItemType itemType;
    private final String field;
    private final SpongePlayerTank pt;
    private final UUID playerId;

    public AttemptMenuOpenEvent(ItemType itemType, String field, SpongePlayerTank pt, UUID playerId)
    {
        super();
        this.itemType = itemType;
        this.field = field;
        this.pt = pt;
        this.playerId = playerId;
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

    @Nonnull
    @Override
    public Cause getCause()
    {
        return Cause.of(MTUtils.getPlayer(playerId), itemType);
    }

    public ItemType getItemType()
    {
        return itemType;
    }

    public String getField()
    {
        return field;
    }

    public SpongePlayerTank getPlayerTank()
    {
        return pt;
    }

    public UUID getPlayerId()
    {
        return playerId;
    }
}
