package io.musician101.minetanks.forge.tank.modules;

import io.musician101.minetanks.common.tank.modules.AbstractModules;
import io.musician101.minetanks.forge.tank.modules.cannon.ForgeCannon;
import io.musician101.minetanks.forge.tank.modules.tracks.ForgeTrackz;
import io.musician101.minetanks.forge.tank.modules.turret.ForgeTurret;

public class ForgeModules extends AbstractModules<ForgeCannon, ForgeEngine, ForgeRadio, ForgeTrackz, ForgeTurret>
{
    public ForgeModules(ForgeCannon cannon, ForgeEngine engine, ForgeRadio radio, ForgeTrackz tracks, ForgeTurret turret)
    {
        super(cannon, engine, radio, tracks, turret);
    }
}
