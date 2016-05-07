package io.musician101.minetanks.common.tank;

import io.musician101.minetanks.common.tank.modules.AbstractEngine;
import io.musician101.minetanks.common.tank.modules.AbstractModules;
import io.musician101.minetanks.common.tank.modules.AbstractTrackz;
import io.musician101.minetanks.common.tank.modules.AbstractTurret;
import io.musician101.minetanks.common.tank.modules.AbstractCannon;
import io.musician101.minetanks.common.tank.modules.AbstractRadio;

import java.util.List;
import java.util.UUID;

public abstract class AbstractTank<O extends AbstractCountry<I>, Y extends AbstractTankType<I>, M extends AbstractModules<C, E, R, T, U>, C extends AbstractCannon<I>, E extends AbstractEngine<I, Y>, R extends AbstractRadio<I, Y>, T extends AbstractTrackz<I, Y>, U extends AbstractTurret<I, Y>, I> extends Info<I>
{
    protected final Armor armor;
    protected final C cannon;
    private final O country;
    protected final E engine;
    private final int health;
    private final int speed;
    protected final R radio;
    protected final T tracks;
    protected final U turret;
    private final Y type;

    protected AbstractTank(String name, O country, Y type, int health, Armor armor, int speed, M modules)
    {
        super(name);
        this.country = country;
        this.type = type;
        this.health = health;
        this.armor = armor;
        this.speed = speed / 10;
        this.cannon = modules.getCannon();
        this.engine = modules.getEngine();
        this.radio = modules.getRadio();
        this.tracks = modules.getTracks();
        this.turret = modules.getTurret();
    }

    protected abstract I parseArmorValue(I item);

    protected abstract I parseArmorValue(I item, Armor armor);

    protected abstract I parseSpeedValue(I item);

    public C getCannon()
    {
        return cannon;
    }

    public O getCountry()
    {
        return country;
    }

    public int getHealth()
    {
        return health;
    }

    protected int getSpeed()
    {
        return speed;
    }

    public abstract I getHelmet();

    public abstract I getChestplate();

    public abstract I getLeggings();

    public abstract I getBoots();

    public abstract List<I> getWeapons();

    public Y getType()
    {
        return type;
    }

    public abstract void applySpeedEffect(UUID uuid);
}
