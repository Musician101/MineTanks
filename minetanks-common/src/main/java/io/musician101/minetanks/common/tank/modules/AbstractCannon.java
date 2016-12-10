package io.musician101.minetanks.common.tank.modules;

import io.musician101.minetanks.common.tank.Info;


public abstract class AbstractCannon<I> extends Info<I> {

    private final int ammoCount;
    private final double reloadTime;

    protected AbstractCannon(String name, int ammoCount, double reloadTime) {
        super(name);
        this.ammoCount = ammoCount;
        this.reloadTime = reloadTime;
    }

    public int getAmmoCount() {
        return ammoCount;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    protected abstract void parseCannon();
}
