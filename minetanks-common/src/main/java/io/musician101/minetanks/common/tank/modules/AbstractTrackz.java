package io.musician101.minetanks.common.tank.modules;

import io.musician101.minetanks.common.tank.AbstractTankType;
import io.musician101.minetanks.common.tank.Info;


public abstract class AbstractTrackz<I, T extends AbstractTankType<I>> extends Info<I> {

    protected AbstractTrackz(String name) {
        super(name);
    }


    protected abstract void parseTracks(T tankType);
}
