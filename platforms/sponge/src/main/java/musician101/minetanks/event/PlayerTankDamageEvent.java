package musician101.minetanks.event;

import musician101.minetanks.spigot.battlefield.Battlefield;
import org.spongepowered.api.util.event.Event;
import org.spongepowered.api.util.event.callback.CallbackList;

import java.util.UUID;

public class PlayerTankDamageEvent implements Event
{
    int damage;
    PlayerTankDamageCause cause;
    UUID dmgd;
    UUID dmgr;
    String field;

    public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID dmgd, Battlefield field, int damage)
    {
        this(cause, dmgd, null, field, damage);
    }

    public PlayerTankDamageEvent(PlayerTankDamageCause cause, UUID dmgd, UUID damager, Battlefield field, int damage)
    {
        this.dmgd = dmgd;
        this.dmgr = damager;
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
        return dmgd;
    }

    public UUID getDamager()
    {
        return dmgr;
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
    public CallbackList getCallbacks()
    {
        return null;
    }

    public enum PlayerTankDamageCause
    {
        FALL,
        PENETRATION,
        RAM,
        SPLASH;
    }
}
