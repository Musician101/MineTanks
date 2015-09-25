package musician101.minetanks.spigot.events;

import musician101.minetanks.spigot.battlefield.player.PlayerTank;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class AttemptMenuOpenEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final Material material;
    private final String field;
    private final PlayerTank pt;
    private final UUID player;

    public AttemptMenuOpenEvent(Material material, String field, PlayerTank pt, UUID player)
    {
        this.material = material;
        this.field = field;
        this.pt = pt;
        this.player = player;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public Material getMaterial()
    {
        return material;
    }

    public String getField()
    {
        return field;
    }

    public PlayerTank getPlayerTank()
    {
        return pt;
    }

    public UUID getPlayer()
    {
        return player;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }
}
