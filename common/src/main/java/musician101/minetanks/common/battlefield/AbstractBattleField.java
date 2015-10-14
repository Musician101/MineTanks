package musician101.minetanks.common.battlefield;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.common.util.IMTScoreboard;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractBattleField
{
    boolean enabled;
    boolean inProgress = false;
    protected Map<UUID, AbstractPlayerTank> players = new HashMap<>();
    protected int unassigned = 0;
    String name;

    protected AbstractBattleField(String name, boolean enabled)
    {
        this.name = name;
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean inProgress()
    {
        return inProgress;
    }

    public void setInProgress(boolean inProgress)
    {
        this.inProgress = inProgress;
    }

    public abstract boolean addPlayer(UUID playerId, MTTeam team);

    public abstract boolean removePlayer(UUID playerId);

    public abstract boolean isReady();

    public abstract boolean canPlayerExit(UUID player);

    public Map<UUID, AbstractPlayerTank> getPlayers()
    {
        return players;
    }

    public String getName()
    {
        return name;
    }

    public abstract void saveToFile(File battlefields);

    public abstract void startMatch();

    public abstract void endMatch();

    public abstract void endMatch(boolean forced);

    public abstract void playerKilled(UUID player);
}
