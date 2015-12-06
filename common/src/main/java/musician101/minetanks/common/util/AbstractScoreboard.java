package musician101.minetanks.common.util;

import java.util.UUID;

public abstract class AbstractScoreboard<S>
{
    protected S scoreboard;

    protected AbstractScoreboard(S scoreboard)
    {
        this.scoreboard = scoreboard;
    }

    public abstract boolean isOnGreen(UUID playerId);

    public abstract boolean isOnRed(UUID playerId);

    public abstract int getGreenTeamSize();

    public abstract int getRedTeamSize();

    public abstract int getPlayerHealth(UUID playerId);

    public S getScoreboard()
    {
        return scoreboard;
    }

    public abstract void addGreenPlayer(UUID playerId);

    public abstract void addRedPlayer(UUID playerId);

    public abstract void playerDeath(UUID playerId);

    public abstract void setPlayerHealth(UUID playerId, int health);
}
