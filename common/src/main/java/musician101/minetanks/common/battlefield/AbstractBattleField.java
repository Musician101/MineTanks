package musician101.minetanks.common.battlefield;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.common.util.AbstractRegion;
import musician101.minetanks.common.util.AbstractScoreboard;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractBattleField<P extends AbstractPlayerTank, R extends AbstractRegion, S extends AbstractScoreboard, L>
{
    private boolean enabled;
    private boolean inProgress = false;
    protected final Map<UUID, P> players = new HashMap<>();
    private final S scoreboard;
    protected int unassigned = 0;
    private L greenSpawn;
    private L redSpawn;
    private L spectators;
    private R region;
    private final String name;

    protected AbstractBattleField(String name, boolean enabled, R region, L greenSpawn, L redSpawn, L spectators, S scoreboard)
    {
        this.name = name;
        this.enabled = enabled;
        this.region = region;
        this.greenSpawn = greenSpawn;
        this.redSpawn = redSpawn;
        this.spectators = spectators;
        this.scoreboard = scoreboard;
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

    public boolean canPlayerExit(UUID playerId)
    {
        return getPlayerTank(playerId).getTeam().canExit();
    }

    public L getGreenSpawn()
    {
        return greenSpawn;
    }

    public void setGreenSpawn(L greenSpawn)
    {
        this.greenSpawn = greenSpawn;
    }

    public L getRedSpawn()
    {
        return redSpawn;
    }

    public void setRedSpawn(L redSpawn)
    {
        this.redSpawn = redSpawn;
    }

    public L getSpectators()
    {
        return spectators;
    }

    public void setSpectators(L spectators)
    {
        this.spectators = spectators;
    }
    
    public Map<UUID, P> getPlayers()
    {
        return players;
    }

    public P getPlayerTank(UUID playerId)
    {
        return players.get(playerId);
    }

    public R getRegion()
    {
        return region;
    }

    public void setRegion(R region)
    {
        this.region = region;
    }

    public S getScoreboard()
    {
        return scoreboard;
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
