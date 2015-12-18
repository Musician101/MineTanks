package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.battlefield.AbstractBattleField;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.handler.ReloadHandler;
import musician101.minetanks.spigot.scoreboard.MTScoreboard;
import musician101.minetanks.spigot.tank.Tank;
import musician101.minetanks.spigot.util.MTUtils;
import musician101.minetanks.spigot.util.SpigotRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

//TODO Rename SpigotBattleField
public class SpigotBattleField extends AbstractBattleField<SpigotPlayerTank, SpigotRegion, MTScoreboard, Location>
{
    private final SpigotMineTanks plugin;

    public SpigotBattleField(SpigotMineTanks plugin, String name, boolean enabled, SpigotRegion region, Location greenSpawn, Location redSpawn, Location spectators)
    {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new MTScoreboard());
        this.plugin = plugin;
    }

    @Override
    public boolean addPlayer(UUID playerId, MTTeam team)
    {
        Player player = Bukkit.getPlayer(playerId);
        if (!plugin.getInventoryStorage().save(playerId))
            return false;

        if (team == MTTeam.SPECTATOR)
        {
            player.teleport(getSpectators());
            player.sendMessage(CommonMessages.fieldSpectating(this));
        }
        else
        {
            player.getInventory().setItem(0, MTUtils.createCustomItem(Material.STICK, CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(null)));
            player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            unassigned++;
        }

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
        if (unassigned > 0)
            unassigned--;

        return true;
    }

    @Override
    public void saveToFile(File battlefields)
    {
        File file = new File(battlefields, CommonConfig.battlefieldFile(this, "yml"));
        try
        {
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
            if (!getPlayerTank(player).isReady())
                return;

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
            final Tank tank = pt.getTank();
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
                        tank.getWeapons().forEach(item -> player.getInventory().addItem(item));
                        player.getInventory().setHelmet(tank.getHelmet());
                        player.getInventory().setChestplate(tank.getChestplate());
                        player.getInventory().setLeggings(tank.getLeggings());
                        player.getInventory().setBoots(tank.getBoots());
                    }
                }.runTaskLater(plugin, 1L);
                new ReloadHandler(plugin, player, tank.getCannon()).isReloading();
            }
            else
            {
                pt.setTank(null);
                player.getInventory().clear();
            }
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
        if ((getScoreboard().getGreenTeamSize() != 0 && getScoreboard().getRedTeamSize() != 0) || !inProgress())
            return;

        if (forced || getScoreboard().getGreenTeamSize() == 0 || getScoreboard().getRedTeamSize() == 0)
        {
            setInProgress(false);
            for (UUID uuid : players.keySet())
            {
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(getSpectators());
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);
                player.getInventory().clear();
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.SPEED);
                SpigotPlayerTank pt = getPlayerTank(uuid);
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
