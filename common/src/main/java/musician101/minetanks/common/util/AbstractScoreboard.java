package musician101.minetanks.common.util;

import java.util.UUID;

public abstract class AbstractScoreboard<Scoreboard>
{
    protected Scoreboard scoreboard;

    protected AbstractScoreboard()
    {

    }

    public abstract boolean isOnGreen(UUID playerId);

    public abstract boolean isOnRed(UUID playerId);

    public abstract int getGreenTeamSize();

    public abstract int getRedTeamSize();

    public abstract int getPlayerHealth(UUID playerId);

    public Scoreboard getScoreboard()
    {
        return scoreboard;
    }

    public abstract void addGreenPlayer(UUID playerId);

    public abstract void addRedPlayer(UUID playerId);

    public abstract void playerDeath(UUID playerId);

    public abstract void setPlayerHealth(UUID playerId, int health);
}
