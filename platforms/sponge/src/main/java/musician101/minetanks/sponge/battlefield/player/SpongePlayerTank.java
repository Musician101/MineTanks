package musician101.minetanks.sponge.battlefield.player;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.sponge.tank.Tank;

import java.util.UUID;

public class SpongePlayerTank extends AbstractPlayerTank
{
    public SpongePlayerTank(UUID player, MTTeam team)
    {
        super(player, team);
    }

    public Tank getTank()
    {
        return (Tank) tank;
    }
}
