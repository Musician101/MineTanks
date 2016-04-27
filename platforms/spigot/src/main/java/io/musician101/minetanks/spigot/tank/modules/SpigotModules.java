package io.musician101.minetanks.spigot.tank.modules;

import io.musician101.minetanks.common.tank.modules.AbstractModules;
import io.musician101.minetanks.spigot.tank.modules.cannon.SpigotCannon;
import io.musician101.minetanks.spigot.tank.modules.tracks.SpigotTrackz;
import io.musician101.minetanks.spigot.tank.modules.turret.SpigotTurret;

public class SpigotModules extends AbstractModules<SpigotCannon, SpigotEngine, SpigotRadio, SpigotTrackz, SpigotTurret>
{
    public SpigotModules(SpigotCannon cannon, SpigotEngine engine, SpigotRadio radio, SpigotTrackz tracks, SpigotTurret turret)
    {
        super(cannon, engine, radio, tracks, turret);
    }
}
