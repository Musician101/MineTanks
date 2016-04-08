package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.battlefield.AbstractBattleField;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.handler.ReloadHandler;
import musician101.minetanks.spigot.scoreboard.SpigotMTScoreboard;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.util.MTUtils;
import musician101.minetanks.spigot.util.SpigotRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
//TODO check when arrows leave the region and despawn and unregister the arrow
public class SpigotBattleField extends AbstractBattleField<SpigotPlayerTank, SpigotRegion, SpigotMTScoreboard, Location>
{
    private final SpigotMineTanks plugin;

    public SpigotBattleField(SpigotMineTanks plugin, String name, boolean enabled, SpigotRegion region, Location greenSpawn, Location redSpawn, Location spectators)
    {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new SpigotMTScoreboard());
        this.plugin = plugin;
    }

    @Override
    public boolean addPlayer(UUID playerId, MTTeam team)
    {
        Player player = Bukkit.getPlayer(playerId);
        if (!players.containsKey(playerId))
            if (!plugin.getInventoryStorage().save(playerId))
                return false;

        if (team == MTTeam.SPECTATOR)
            player.sendMessage(ChatColor.GREEN + CommonMessages.fieldSpectating(this));
        else
        {
            player.getInventory().setItem(0, MTUtils.createCustomItem(Material.STICK, CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(null)));
            player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            unassigned++;
        }

        player.teleport(getSpectators());
        player.setGameMode(GameMode.SURVIVAL);
        players.put(player.getUniqueId(), new SpigotPlayerTank(player.getUniqueId(), team));
        return true;
    }

    @Override
    public boolean removePlayer(UUID playerId)
    {
        SpigotPlayerTank pt = getPlayerTank(playerId);
        if (pt == null)
            return false;

        plugin.getInventoryStorage().load(playerId);
        if (getScoreboard().isOnGreen(playerId) || getScoreboard().isOnRed(playerId))
            getScoreboard().playerDeath(playerId);

        players.remove(playerId);
        if (unassigned > 0 && pt.getTeam() != MTTeam.SPECTATOR)
            unassigned--;

        return true;
    }

    @Override
    public void saveToFile(File battlefields)
    {
        File file = new File(battlefields, CommonConfig.battlefieldFile(this));
        try
        {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
        catch (IOException e)
        {
            plugin.getLogger().warning(CommonMessages.fileCreateFailed(file));
            return;
        }

        YamlConfiguration field = YamlConfiguration.loadConfiguration(file);
        if (getRegion() != null)
            field.set(CommonConfig.REGION, getRegion().serialize());

        if (getGreenSpawn() != null)
            field.set(CommonConfig.GREEN_SPAWN, getGreenSpawn().serialize());

        if (getRedSpawn() != null)
            field.set(CommonConfig.RED_SPAWN, getRedSpawn().serialize());

        if (getSpectators() != null)
            field.set(CommonConfig.SPECTATORS, getSpectators().serialize());

        field.set(CommonConfig.ENABLED, isEnabled());
        try
        {
            field.save(file);
        }
        catch (IOException e)
        {
            plugin.getLogger().warning(CommonMessages.fileSaveFailed(file));
        }
    }

    @Override
    public void startMatch()
    {
        List<UUID> players = new ArrayList<>();
        for (UUID player : this.players.keySet())
        {
            SpigotPlayerTank pt = getPlayerTank(player);
            if (pt.getTeam() != MTTeam.SPECTATOR && !pt.isReady())
                return;

            if (pt.getTeam() != MTTeam.SPECTATOR)
                players.add(player);
        }

        Collections.shuffle(players);
        for (UUID uuid : players)
        {
            SpigotPlayerTank pt = getPlayerTank(uuid);
            if (getScoreboard().getGreenTeamSize() == 0 && getScoreboard().getRedTeamSize() == 0 && unassigned < 2)
                return;

            if (getScoreboard().getGreenTeamSize() == getScoreboard().getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                unassigned--;
            }
            else if (getScoreboard().getGreenTeamSize() <= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addGreenPlayer(uuid);
                Bukkit.getPlayer(uuid).addPotionEffect(pt.getTank().getSpeedEffect());
                unassigned--;
            }
            else if (getScoreboard().getGreenTeamSize() >= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addRedPlayer(uuid);
                Bukkit.getPlayer(uuid).addPotionEffect(pt.getTank().getSpeedEffect());
                unassigned--;
            }
        }

        setInProgress(true);
        for (UUID uuid : players)
        {
            final SpigotPlayerTank pt = getPlayerTank(uuid);
            final SpigotTank tank = pt.getTank();
            final Player player = Bukkit.getPlayer(uuid);
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (getScoreboard().isOnGreen(uuid))
                    player.teleport(getGreenSpawn());

                if (getScoreboard().isOnRed(uuid))
                    player.teleport(getRedSpawn());

                player.setScoreboard(getScoreboard().getScoreboard());
                getScoreboard().setPlayerHealth(uuid, tank.getHealth());
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.getInventory().clear();
                        tank.getWeapons().forEach(item -> player.getInventory().addItem(item));
                        player.getInventory().setHelmet(tank.getHelmet());
                        player.getInventory().setChestplate(tank.getChestplate());
                        player.getInventory().setLeggings(tank.getLeggings());
                        player.getInventory().setBoots(tank.getBoots());
                    }
                }.runTaskLater(plugin, 1L);
                new ReloadHandler(plugin, player, tank.getCannon()).isReloading(pt);
            }
            else
            {
                pt.setTank(null);
                player.getInventory().clear();
            }
        }

        for (UUID uuid : this.players.keySet())
        {
            SpigotPlayerTank pt = this.players.get(uuid);
            if (pt.getTeam() == MTTeam.SPECTATOR)
                Bukkit.getPlayer(uuid).setScoreboard(getScoreboard().getScoreboard());
        }
    }

    @Override
    public void endMatch()
    {
        endMatch(false);
    }

    @Override
    public void endMatch(boolean forced)
    {
        if (!inProgress())
            return;

        if (forced || getScoreboard().getGreenTeamSize() == 0 || getScoreboard().getRedTeamSize() == 0)
        {
            setInProgress(false);
            for (UUID uuid : players.keySet())
            {
                Player player = Bukkit.getPlayer(uuid);
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);
                player.getInventory().clear();
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.SPEED);
                SpigotPlayerTank pt = getPlayerTank(uuid);
                player.teleport(getSpectators());
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTank(null);
                getScoreboard().playerDeath(uuid);
                if (forced)
                    player.sendMessage(ChatColor.GOLD + CommonMessages.MATCH_FORCE_ENDED);
                else if (getScoreboard().getGreenTeamSize() == 0)
                    player.sendMessage(ChatColor.RED + CommonMessages.RED_WINS);
                else if (getScoreboard().getRedTeamSize() == 0)
                    player.sendMessage(ChatColor.GREEN + CommonMessages.GREEN_WINS);
            }
        }
    }

    @Override
    public void playerKilled(UUID playerId)
    {
        getPlayerTank(playerId).killed();
        getScoreboard().playerDeath(playerId);
        Bukkit.getPlayer(playerId).teleport(getSpectators());
    }
}
