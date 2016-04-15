package musician101.minetanks.common.battlefield;

import musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.common.util.AbstractRegion;
import musician101.minetanks.common.util.AbstractScoreboard;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractBattleField<PlayerTank extends AbstractPlayerTank, Region extends AbstractRegion, Scoreboard extends AbstractScoreboard, ItemType, Location, Break, Place, Interact, Drop, Join, Quit, Move, Teleport, ShootBow, EntityDamage, ProjectileHit, BlockExplode>
{
    private boolean enabled;
    private boolean inProgress = false;
    protected final Map<UUID, PlayerTank> players = new HashMap<>();
    private final Scoreboard scoreboard;
    protected int unassigned = 0;
    private Location greenSpawn;
    private Location redSpawn;
    private Location spectators;
    private Region region;
    private final String name;

    protected AbstractBattleField(String name, boolean enabled, Region region, Location greenSpawn, Location redSpawn, Location spectators, Scoreboard scoreboard)
    {
        this.name = name;
        this.enabled = enabled;
        this.region = region;
        this.greenSpawn = greenSpawn;
        this.redSpawn = redSpawn;
        this.spectators = spectators;
        this.scoreboard = scoreboard;
    }

    protected abstract boolean isSword(ItemType itemType);

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

    public abstract boolean addPlayer(UUID uuid, MTTeam team);

    public abstract boolean removePlayer(UUID uuid);

    public boolean isReady()
    {
        return region != null && greenSpawn != null && redSpawn != null && spectators != null && isEnabled();
    }

    public boolean canPlayerExit(UUID uuid)
    {
        return getPlayerTank(uuid).getTeam().canExit();
    }

    public Location getGreenSpawn()
    {
        return greenSpawn;
    }

    public void setGreenSpawn(Location greenSpawn)
    {
        this.greenSpawn = greenSpawn;
    }

    public Location getRedSpawn()
    {
        return redSpawn;
    }

    public void setRedSpawn(Location redSpawn)
    {
        this.redSpawn = redSpawn;
    }

    public Location getSpectators()
    {
        return spectators;
    }

    public void setSpectators(Location spectators)
    {
        this.spectators = spectators;
    }
    
    public Map<UUID, PlayerTank> getPlayers()
    {
        return players;
    }

    public PlayerTank getPlayerTank(UUID uuid)
    {
        return players.get(uuid);
    }

    public Region getRegion()
    {
        return region;
    }

    public void setRegion(Region region)
    {
        this.region = region;
    }

    public Scoreboard getScoreboard()
    {
        return scoreboard;
    }

    public String getName()
    {
        return name;
    }

    public abstract void endMatch();

    public abstract void endMatch(boolean forced);

    public abstract void saveToFile(File battlefields);

    public abstract void startMatch();

    public abstract void playerKilled(UUID player);

    public abstract void onBlockBreak(Break event);

    public abstract void onBlockPlace(Place event);

    public abstract void onBlockInteract(Interact event);

    public abstract void onItemDrop(Drop event);

    public abstract void onPlayerJoin(Join event);

    public abstract void onPlayerQuit(Quit event);

    public abstract void onPlayerMove(Move event);

    public abstract void onPlayerTeleport(Teleport event);

    public abstract void onBowShoot(ShootBow event);

    public abstract void onEntityDamage(EntityDamage event);

    public abstract void onProjectileHit(ProjectileHit event);

    public abstract void onBlockExplode(BlockExplode event);

    protected abstract void gravityHit(UUID uuid, double damage);

    protected abstract void meleeHit(UUID rammed, UUID rammer, double damage);

    protected abstract void playerHit(UUID rammed, UUID rammer, double damage);

    protected abstract void triggerPlayerDeath(UUID killerId, UUID killedId);
}
