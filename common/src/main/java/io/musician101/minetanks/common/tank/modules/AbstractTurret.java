package io.musician101.minetanks.common.tank.modules;

import io.musician101.minetanks.common.tank.AbstractTankType;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.common.tank.Info;

public abstract class AbstractTurret<I> extends Info<I>
{
    private final Armor armor;

    protected AbstractTurret(String name, Armor armor)
    {
        super(name);
        this.armor = armor;
    }

    public Armor getArmor()
    {
        return armor;
    }

    protected abstract <T extends AbstractTankType> void parseTurret(T tankType);
}
