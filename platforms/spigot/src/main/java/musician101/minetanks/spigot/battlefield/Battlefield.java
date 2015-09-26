package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.common.AbstractBattleField;
import musician101.minetanks.common.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.player.PlayerTank;
import musician101.minetanks.spigot.handlers.ReloadHandler;
import musician101.minetanks.spigot.scoreboards.MTScoreboard;
import musician101.minetanks.spigot.tank.Tank;
import musician101.minetanks.spigot.util.Cuboid;
import musician101.minetanks.spigot.util.MTUtils;
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

public class BattleField extends AbstractBattleField
{
    private boolean inProgress = false;
    private Cuboid cuboid;
    private Location greenSpawn;
    private Location redSpawn;
    private Location spectators;
    private final MineTanks plugin;
    private final MTScoreboard sb;
    
    public BattleField(MineTanks plugin, String name, boolean enabled, Cuboid cuboid, Location greenSpawn, Location redSpawn, Location spectators)
    {
        super(name, enabled);
        this.plugin = plugin;
        this.cuboid = cuboid;
        this.greenSpawn = greenSpawn;
        this.redSpawn = redSpawn;
        this.spectators = spectators;
        this.sb = new MTScoreboard();
    }

    public PlayerTank getPlayer(UUID player)
    {
        return getPlayers().containsKey(player) ? (PlayerTank) getPlayers().get(player) : null;
    }

    public Cuboid getCuboid()
    {
        return cuboid;
    }

    public void setCuboid(Cuboid cuboid)
    {
        this.cuboid = cuboid;
    }

    public Location getGreenSpawn()
    {
        return greenSpawn;
    }

    public void setGreenSpawn(Location loc)
    {
        greenSpawn = loc;
    }

    public Location getRedSpawn()
    {
        return redSpawn;
    }

    public void setRedSpawn(Location loc)
    {
        redSpawn = loc;
    }

    public Location getSpectators()
    {
        return spectators;
    }

    public void setSpectators(Location loc)
    {
        spectators = loc;
    }

    @Override
    public boolean addPlayer(UUID playerId, MTTeam team)
    {
        Player player = Bukkit.getPlayer(playerId);
        if (!plugin.getInventoryStorage().saveInventory(player))
            return false;

        if (team == MTTeam.SPECTATOR)
        {
            player.teleport(spectators);
            player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " You are now spectating in " + getName() + ".");
        }
        else
        {
            player.getInventory().setItem(0, MTUtils.createCustomItem(Material.STICK, "Open Hangar", "Tank: None"));
            player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Ready Up", "You are currently not ready."));
            unassigned++;
        }

        getPlayers().put(player.getUniqueId(), new PlayerTank(player.getUniqueId(), team));
        return true;
    }

    //TODO strip players of potion effects and items in case the match was forcibly ended
    @Override
    public boolean removePlayer(UUID playerId)
    {
        Player player = Bukkit.getPlayer(playerId);
        PlayerTank pt = getPlayer(player.getUniqueId());
        if (pt == null)
            return false;

        plugin.getInventoryStorage().returnInventory(player);

        if (sb.isOnGreen(player) || sb.isOnRed(player))
            sb.playerDeath(player);

        getPlayers().remove(player.getUniqueId());
        if (unassigned > 0)
            unassigned--;

        return true;
    }

    @Override
    public boolean isReady()
    {
        return cuboid != null && greenSpawn != null && redSpawn != null && spectators != null && isEnabled();

    }

    @Override
    public void saveToFile(File battlefields)
    {
        File file = new File(battlefields, getName() + ".yml");
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            plugin.getLogger().warning("Error: Failed to create file: " + file.getName());
            return;
        }

        YamlConfiguration field = YamlConfiguration.loadConfiguration(file);
        if (cuboid != null)
            field.set("cuboid", cuboid.serialize());

        if (greenSpawn != null)
        {
            field.set("greenSpawn.world", greenSpawn.getWorld().getName());
            field.set("greenSpawn.x", greenSpawn.getX());
            field.set("greenSpawn.y", greenSpawn.getY());
            field.set("greenSpawn.z", greenSpawn.getZ());
        }

        if (redSpawn != null)
        {
            field.set("redSpawn.world", redSpawn.getWorld().getName());
            field.set("redSpawn.x", redSpawn.getX());
            field.set("redSpawn.y", redSpawn.getY());
            field.set("redSpawn.z", redSpawn.getZ());
        }

        if (spectators != null)
        {
            field.set("spectators.world", spectators.getWorld().getName());
            field.set("spectators.x", spectators.getX());
            field.set("spectators.y", spectators.getY());
            field.set("spectators.z", spectators.getZ());
        }

        field.set("enabled", isEnabled());
        try
        {
            field.save(file);
        }
        catch (IOException e)
        {
            plugin.getLogger().warning("Error: Could not save " + file.getName());
        }
    }

    @Override
    public boolean canPlayerExit(UUID player)
    {
        return getPlayer(player).getTeam().canExit();
    }

    @Override
    public void startMatch()
    {
        List<UUID> players = new ArrayList<>();
        for (UUID player : getPlayers().keySet())
        {
            if (!getPlayer(player).isReady())
                return;

            players.add(player);
        }

        Collections.shuffle(players);

        for (UUID uuid : players)
        {
            PlayerTank pt = getPlayer(uuid);
            if (sb.getGreenTeamSize() == 0 && sb.getRedTeamSize() == 0 && unassigned < 2)
                return;

            if (sb.getGreenTeamSize() == sb.getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                unassigned--;
            }
            else if (sb.getGreenTeamSize() <= sb.getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                sb.addGreenPlayer(Bukkit.getPlayer(uuid));
                Bukkit.getPlayer(uuid).addPotionEffect(pt.getTank().getSpeedEffect());
                unassigned--;
            }
            else if (sb.getGreenTeamSize() >= sb.getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                sb.addRedPlayer(Bukkit.getPlayer(uuid));
                Bukkit.getPlayer(uuid).addPotionEffect(pt.getTank().getSpeedEffect());
                unassigned--;
            }
        }

        inProgress = true;
        for (UUID uuid : players)
        {
            final PlayerTank pt = getPlayer(uuid);
            final Tank tank = pt.getTank();
            final Player player = Bukkit.getPlayer(uuid);
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (sb.isOnGreen(player))
                    player.teleport(greenSpawn);

                if (sb.isOnRed(player))
                    player.teleport(redSpawn);

                player.setScoreboard(sb.getScoreboard());
                sb.setPlayerHealth(uuid, tank.getHealth());
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.getInventory().setContents(tank.getWeapons().getContents());
                        player.getInventory().setArmorContents(tank.getArmor());
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
        if ((sb.getGreenTeamSize() != 0 && sb.getRedTeamSize() != 0) || !inProgress)
            return;

        if (sb.getGreenTeamSize() == 0 || sb.getRedTeamSize() == 0)
        {
            inProgress = false;
            for (UUID uuid : getPlayers().keySet())
            {
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(spectators);
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);
                player.getInventory().clear();
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.SPEED);
                PlayerTank pt = getPlayer(uuid);
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTank(null);
                sb.playerDeath(player);
                if (sb.getGreenTeamSize() == 0)
                    player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Red team wins!");
                else if (sb.getRedTeamSize() == 0)
                    player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Green team wins!");
            }
        }
    }

    @Override
    public void playerKilled(UUID player)
    {
        getPlayer(player).killed();
        sb.playerDeath(Bukkit.getPlayer(player));
        Bukkit.getPlayer(player).teleport(spectators);
    }

    public MTScoreboard getScoreboard()
    {
        return sb;
    }
}
