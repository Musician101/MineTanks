package musician101.minetanks.common.tank;

import musician101.minetanks.common.tank.modules.AbstractCannon;
import musician101.minetanks.common.tank.modules.AbstractEngine;
import musician101.minetanks.common.tank.modules.AbstractRadio;
import musician101.minetanks.common.tank.modules.AbstractTrackz;
import musician101.minetanks.common.tank.modules.AbstractTurret;

import java.util.List;

public abstract class AbstractTank<Y extends AbstractTankType, C extends AbstractCannon, E extends AbstractEngine, R extends AbstractRadio, T extends AbstractTrackz, U extends AbstractTurret, I> extends Info
{
    protected final Armor armor;
    protected final C cannon;
    protected final E engine;
    private final int health;
    private final int speed;
    protected final R radio;
    protected final T tracks;
    protected final U turret;
    private final Y type;
    private final String[] description;

    protected AbstractTank(String name, Y type, int health, Armor armor, int speed, C cannon, E engine, R radio, T tracks, U turret, String... description)
    {
        super(name);
        this.type = type;
        this.health = health;
        this.armor = armor;
        this.speed = speed / 5;
        this.cannon = cannon;
        this.engine = engine;
        this.radio = radio;
        this.tracks = tracks;
        this.turret = turret;
        this.description = description;
    }

    public C getCannon()
    {
        return cannon;
    }

    public int getHealth()
    {
        return health;
    }

    public int getSpeed()
    {
        return speed;
    }

    public abstract I getHelmet();

    public abstract I getChestplate();

    public abstract I getLeggings();

    public abstract I getBoots();

    public abstract List<I> getWeapons();

    public String[] getDescription()
    {
        return description;
    }

    public Y getType()
    {
        return type;
    }
}
