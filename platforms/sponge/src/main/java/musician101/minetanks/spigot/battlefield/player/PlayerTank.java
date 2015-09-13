package musician101.minetanks.spigot.battlefield.player;

import musician101.minetanks.spigot.tank.Tanks;

import java.util.UUID;

public class PlayerTank
{
    private Tanks tank;
    private boolean isReady;
    private MTTeam team;
    private int clipSize = 0;

    public PlayerTank(UUID player, MTTeam team)
    {
        this.team = team;
        this.isReady = false;
    }

    public Tanks getTank()
    {
        return tank;
    }

    public MTTeam getTeam()
    {
        return team;
    }

    public void setTank(Tanks tank)
    {
        this.tank = tank;
    }

    public void setClipSize(int size)
    {
        this.clipSize = size;
    }

    public int getClipSize()
    {
        return clipSize;
    }

    public void setTeam(MTTeam team)
    {
        this.team = team;
    }

    public boolean isReady()
    {
        return isReady;
    }

    public void setReady(boolean isReady)
    {
        this.isReady = isReady;
    }

    public void killed()
    {
        this.team = MTTeam.SPECTATOR;
        this.tank = null;
    }

    public enum MTTeam
    {
        ASSIGNED(false),
        SPECTATOR(),
        UNASSIGNED();

        private boolean canExit;

        private MTTeam()
        {
            this(true);
        }

        private MTTeam(boolean canExit)
        {
            this.canExit = canExit;
        }

        public boolean canExit()
        {
            return canExit;
        }
    }
}
