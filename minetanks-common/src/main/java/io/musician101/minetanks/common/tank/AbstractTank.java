package io.musician101.minetanks.common.tank;

import io.musician101.minetanks.common.tank.modules.AbstractCannon;
import io.musician101.minetanks.common.tank.modules.AbstractEngine;
import io.musician101.minetanks.common.tank.modules.AbstractModules;
import io.musician101.minetanks.common.tank.modules.AbstractRadio;
import io.musician101.minetanks.common.tank.modules.AbstractTrackz;
import io.musician101.minetanks.common.tank.modules.AbstractTurret;
import java.util.List;


public abstract class AbstractTank<C extends AbstractCannon<I>, E extends AbstractEngine<I, Y>, I, M extends AbstractModules<C, E, R, T, U>, O extends AbstractCountry<I>, P, R extends AbstractRadio<I, Y>, T extends AbstractTrackz<I, Y>, U extends AbstractTurret<I, Y>, Y extends AbstractTankType<I>> extends Info<I> {

    protected final Armor armor;
    protected final C cannon;
    protected final E engine;
    protected final R radio;
    protected final T tracks;
    protected final U turret;
    private final O country;
    private final int health;
    private final int speed;
    private final Y type;

    protected AbstractTank(String name, O country, Y type, int health, Armor armor, int speed, M modules) {
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

    public abstract void applySpeedEffect(P player);

    public I getBoots() {
        return parseArmorValue(tracks.getItem());
    }

    public C getCannon() {
        return cannon;
    }

    public I getChestplate() {
        return parseArmorValue(radio.getItem());
    }

    public O getCountry() {
        return country;
    }

    public int getHealth() {
        return health;
    }

    public I getHelmet() {
        return parseArmorValue(turret.getItem());
    }

    public I getLeggings() {
        return parseArmorValue(parseArmorValue(parseSpeedValue(engine.getItem())));
    }

    protected int getSpeed() {
        return speed;
    }

    public Y getType() {
        return type;
    }

    public abstract List<I> getWeapons();

    protected abstract I parseArmorValue(I item);

    protected abstract I parseArmorValue(I item, Armor armor);

    protected abstract I parseSpeedValue(I item);
}
