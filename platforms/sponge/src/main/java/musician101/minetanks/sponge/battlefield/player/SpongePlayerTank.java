package musician101.minetanks.sponge.battlefield.player;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.tank.SpongeTank;

import java.util.UUID;

public class SpongePlayerTank extends AbstractPlayerTank<SpongeTank, SpongeMineTanks, UUID>
{
    public SpongePlayerTank(UUID player, MTTeam team)
    {
        super(player, team);
    }

    @Override
    public boolean isReloading(SpongeMineTanks plugin)
    {
        //TODO not implemented yet
        return false;
    }

    @Override
    public void cancelReload()
    {
        //TODO not implemented yet
    }
}
