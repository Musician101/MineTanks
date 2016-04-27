package io.musician101.minetanks.common.tank.modules;

import io.musician101.minetanks.common.tank.AbstractTankType;
import io.musician101.minetanks.common.tank.Info;

public abstract class AbstractTrackz<I> extends Info<I>
{
    protected AbstractTrackz(String name)
    {
        super(name);
    }

    protected abstract <T extends AbstractTankType> void parseTracks(T tankType);
}
