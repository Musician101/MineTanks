package io.musician101.minetanks.common.battlefield;

import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.common.util.AbstractRegion;
import io.musician101.minetanks.common.util.AbstractScoreboard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @param <A> The type of Plugin instance.
 * @param <T> The type of {@link AbstractPlayerTank}
 * @param <R> The type of {@link AbstractRegion}
 * @param <S> The type of {@link AbstractScoreboard}
 * @param <I> The type of ItemStack (org.bukkit.inventory.ItemStack or org.spongepowered.api.item.inventory.ItemStack)
 * @param <L> The type of Location (org.bukkit.Location or org.spongepowered.api.world.Location)
 * @param <B> The type of Block Break Event (org.bukkit.event.block.BlockBreakEvent or org.spongepowered.api.event.block.ChangeBlockEvent.Break)
 * @param <P> The type of Block Place Event (org.bukkit.event.block.BlockPlaceEvent or org.spongepowered.api.event.block.ChangeBlockEvent.Place)
 * @param <N> The type of Block Interact Event (org.bukkit.event.player.PlayerInteractEvent or org.spongepowered.api.event.block.InteractBlockEvent)
 * @param <D> The type of Item Drop Event (org.bukkit.event.player.PlayerDropItemEvent or org.spongepowered.api.event.item.inventory.DropItemEvent.Dispense)
 * @param <J> The type of Player Join Event (org.bukkit.event.player.PlayerJoinEvent or org.spongepowered.api.event.network.ClientConnectionEvent.Join)
 * @param <Q> The type of Player Quit Event (org.bukkit.event.player.PlayerQuitEvent or org.spongepowered.api.event.network.ClientConnectionEvent.Quit)
 * @param <M> The type of Player Move Event (org.bukkit.event.player.PlayerMoveEvent or org.spongepowered.api.event.entity.DisplaceEntityEvent.Move.TargetPlayer)
 * @param <E> The type of Player Teleport Event (org.bukkit.event.player.PlayerTeleportEvent or org.spongepowered.api.event.entity.DisplaceEntityEvent.Teleport.TargetPlayer)
 * @param <H> The type of Bow Shoot Event (org.bukkit.event.player.EntityShootBowEvent or org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent)
 * @param <Y> The type of Entity Damage Entity Event (org.bukkit.event.entity.EntityDamageByEntityEvent or org.spongepowered.api.event.entity.DisplaceEntityEvent)
 * @param <O> The type of Entity Collide With Block Event (org.bukkit.event.entity.ProjectileHitEvent or org.spongepowered.api.event.block.CollideBlockEvent)
 * @param <X> The type of Block Explode Event (org.bukkit.event.block.BlockExplodeEvent or org.spongepowered.api.event.world.ExplosionEvent.Detonate)
 */
public abstract class AbstractBattleField<A, T extends AbstractPlayerTank, R extends AbstractRegion, S extends AbstractScoreboard, I, L, B, P, N, D, J, Q, M, E, H, Y, O, X>
{
    protected A plugin;
    private boolean enabled = false;
    private boolean inProgress = false;
    protected final Map<UUID, T> players = new HashMap<>();
    private final S scoreboard;
    protected int unassigned = 0;
    private L greenSpawn;
    private L redSpawn;
    private L spectators;
    private R region;
    private final String name;

    protected AbstractBattleField(A plugin, String name, boolean enabled, R region, L greenSpawn, L redSpawn, L spectators, S scoreboard)
    {
        this.plugin = plugin;
        this.name = name;
        this.enabled = enabled;
        this.region = region;
        this.greenSpawn = greenSpawn;
        this.redSpawn = redSpawn;
        this.spectators = spectators;
        this.scoreboard = scoreboard;
    }

    protected abstract boolean menuItem(I itemType);

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
    
    public Map<UUID, T> getPlayers()
    {
        return players;
    }

    public T getPlayerTank(UUID uuid)
    {
        return players.get(uuid);
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

    public abstract void endMatch();

    public abstract void endMatch(boolean forced);

    public abstract void saveToFile(File battlefields);

    private List<UUID> getNonSpectatorPlayers()
    {
        List<UUID> list = new ArrayList<>();
        for (Entry<UUID, T> entry : players.entrySet())
        {
            T pt = entry.getValue();
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (!pt.isReady())
                    return Collections.emptyList();

                list.add(entry.getKey());
            }
        }

        return list;
    }

    private boolean assignTeams(List<UUID> playersList)
    {
        Collections.shuffle(playersList);
        for (UUID uuid : playersList)
        {
            T pt = players.get(uuid);
            if (getScoreboard().getGreenTeamSize() == 0 && getScoreboard().getRedTeamSize() == 0 && unassigned < 2)
                return false;

            if (getScoreboard().getGreenTeamSize() == getScoreboard().getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTeam(null);
                unassigned--;
            }
            else if (getScoreboard().getGreenTeamSize() <= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addGreenPlayer(uuid);
                pt.getTank().applySpeedEffect(uuid);
                unassigned--;
            }
            else if (getScoreboard().getGreenTeamSize() >= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addRedPlayer(uuid);
                pt.getTank().applySpeedEffect(uuid);
                unassigned--;
            }
        }

        return true;
    }

    protected abstract void setUpPlayers(List<UUID> playersList);

    private void setPlayerScoreboards()
    {
        players.keySet().forEach(scoreboard::setPlayerScoreboard);
    }

    public void startMatch()
    {
        List<UUID> playersList = getNonSpectatorPlayers();
        if (playersList.isEmpty())
            return;

        if (!assignTeams(playersList))
            return;

        setInProgress(true);
        setUpPlayers(playersList);
        setPlayerScoreboards();
    }

    public abstract void playerKilled(UUID player);

    @SuppressWarnings("unused")
    public abstract void onBlockBreak(B event);

    @SuppressWarnings("unused")
    public abstract void onBlockPlace(P event);

    protected abstract boolean handleWatch(UUID uuid);

    protected abstract boolean processBlockInteract(UUID uuid);

    @SuppressWarnings("unused")
    public abstract void onBlockInteract(N event);

    @SuppressWarnings("unused")
    public abstract void onItemDrop(D event);

    @SuppressWarnings("unused")
    public abstract void onPlayerJoin(J event);

    @SuppressWarnings("unused")
    public abstract void onPlayerQuit(Q event);

    @SuppressWarnings("unused")
    public abstract void onPlayerMove(M event);

    @SuppressWarnings("unused")
    public abstract void onPlayerTeleport(E event);

    protected abstract void arrowIsDamager(UUID damager, double damage, UUID damaged);

    protected abstract void playerIsDamager(UUID damager, double damage, UUID damaged);

    protected abstract void blockExplosion(UUID damager, double damage, UUID damaged);

    @SuppressWarnings("unused")
    public abstract void onBowShoot(H event);

    @SuppressWarnings("unused")
    public abstract void onEntityDamage(Y event);

    @SuppressWarnings("unused")
    public abstract void onProjectileHit(O event);

    @SuppressWarnings("unused")
    public abstract void onBlockExplode(X event);

    protected abstract void gravityHit(UUID uuid, double damage);

    protected abstract void meleeHit(UUID rammed, UUID rammer, double damage);

    protected abstract void playerHit(UUID rammed, UUID rammer, double damage);

    protected abstract void triggerPlayerDeath(UUID killerId, UUID killedId);
}
