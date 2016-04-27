package io.musician101.minetanks.common.tank.modules;

public class AbstractModules<C extends AbstractCannon, E extends AbstractEngine, R extends AbstractRadio, T extends AbstractTrackz, U extends AbstractTurret>
{
    private C cannon;
    private E engine;
    private R radio;
    private T tracks;
    private U turret;

    protected AbstractModules(C cannon, E engine, R radio, T tracks, U turret)
    {
        this.cannon = cannon;
        this.engine = engine;
        this.radio = radio;
        this.tracks = tracks;
        this.turret = turret;
    }

    public C getCannon()
    {
        return cannon;
    }

    public E getEngine()
    {
        return engine;
    }

    public R getRadio()
    {
        return radio;
    }

    public T getTracks()
    {
        return tracks;
    }

    public U getTurret()
    {
        return turret;
    }
}
