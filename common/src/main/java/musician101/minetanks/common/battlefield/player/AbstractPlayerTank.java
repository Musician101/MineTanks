package musician101.minetanks.common.battlefield.player;

import musician101.minetanks.common.tank.AbstractTank;

import java.util.UUID;

public abstract class AbstractPlayerTank<T extends AbstractTank>
{
    boolean isReady = false;
    protected int clipSize = 1;
    MTTeam team;
    T tank;
    UUID player;

    protected AbstractPlayerTank(UUID player, MTTeam team)
    {
        this.player = player;
        this.team = team;
    }

    public boolean isReady()
    {
        return isReady;
    }

    public MTTeam getTeam()
    {
        return team;
    }

    public int getClipSize()
    {
        return clipSize;
    }

    public T getTank()
    {
        return tank;
    }

    public void setTank(T tank)
    {
        this.tank = tank;
    }

    public void setClipSize(int clipSize)
    {
        this.clipSize = clipSize;
    }

    public void setTeam(MTTeam team)
    {
        this.team = team;
    }

    public void setReady(boolean isReady)
    {
        this.isReady = isReady;
    }

    public void killed()
    {
        this.team = MTTeam.SPECTATOR;
        tank = null;
    }

    public enum MTTeam
    {
        ASSIGNED(false),
        SPECTATOR(),
        UNASSIGNED();

        boolean canExit;

        MTTeam()
        {
            this(true);
        }

        MTTeam(boolean canExit)
        {
            this.canExit = canExit;
        }

        public boolean canExit()
        {
            return canExit;
        }
    }
}
