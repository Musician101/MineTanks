package io.musician101.minetanks.sponge.tank.module;

import io.musician101.minetanks.common.tank.modules.AbstractModules;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import io.musician101.minetanks.sponge.tank.module.tracks.SpongeTrackz;
import io.musician101.minetanks.sponge.tank.module.turret.SpongeTurret;


public class SpongeModules extends AbstractModules<SpongeCannon, SpongeEngine, SpongeRadio, SpongeTrackz, SpongeTurret>
{

    public SpongeModules(SpongeCannon cannon, SpongeEngine engine, SpongeRadio radio, SpongeTrackz tracks, SpongeTurret turret)
    {
        super(cannon, engine, radio, tracks, turret);
    }
}
