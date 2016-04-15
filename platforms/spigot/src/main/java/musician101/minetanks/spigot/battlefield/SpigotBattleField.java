package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.common.battlefield.AbstractBattleField;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.menu.MainSelectionMenu;
import musician101.minetanks.spigot.scoreboard.SpigotMTScoreboard;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import musician101.minetanks.spigot.util.MTUtils;
import musician101.minetanks.spigot.util.SpigotRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpigotBattleField extends AbstractBattleField<SpigotPlayerTank, SpigotRegion, SpigotMTScoreboard, Material, Location, BlockBreakEvent, BlockPlaceEvent, PlayerInteractEvent, PlayerDropItemEvent, PlayerJoinEvent, PlayerQuitEvent, PlayerMoveEvent, PlayerTeleportEvent, EntityShootBowEvent, EntityDamageByEntityEvent, ProjectileHitEvent, BlockExplodeEvent> implements Listener
{
    private final SpigotMineTanks plugin;
    private final Map<UUID, Integer> arrows = new HashMap<>();

    public SpigotBattleField(SpigotMineTanks plugin, String name, boolean enabled, SpigotRegion region, Location greenSpawn, Location redSpawn, Location spectators)
    {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new SpigotMTScoreboard());
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean addPlayer(UUID uuid, MTTeam team)
    {
        Player player = Bukkit.getPlayer(uuid);
        if (!players.containsKey(uuid))
            if (!plugin.getInventoryStorage().save(uuid))
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
    public boolean removePlayer(UUID uuid)
    {
        SpigotPlayerTank pt = players.get(uuid);
        if (pt == null)
            return false;

        plugin.getInventoryStorage().load(uuid);
        if (getScoreboard().isOnGreen(uuid) || getScoreboard().isOnRed(uuid))
            getScoreboard().playerDeath(uuid);

        players.remove(uuid);
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
            SpigotPlayerTank pt = this.players.get(player);
            if (pt.getTeam() != MTTeam.SPECTATOR && !pt.isReady())
                return;

            if (pt.getTeam() != MTTeam.SPECTATOR)
                players.add(player);
        }

        Collections.shuffle(players);
        for (UUID uuid : players)
        {
            SpigotPlayerTank pt = this.players.get(uuid);
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
            final SpigotPlayerTank pt = this.players.get(uuid);
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
                pt.isReloading(plugin);
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
                SpigotPlayerTank pt = players.get(uuid);
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
    public void playerKilled(UUID uuid)
    {
        players.get(uuid).killed();
        getScoreboard().playerDeath(uuid);
        Player player = Bukkit.getPlayer(uuid);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.teleport(getSpectators());
        endMatch();
    }

    @Override
    protected boolean isSword(Material material)
    {
        //Stick is the default item if a player hasn't chosen a tank.
        return material == Material.STICK || material == Material.WOOD_SWORD || material == Material.STONE_SWORD || material == Material.IRON_SWORD || material == Material.GOLD_SWORD || material == Material.DIAMOND_SWORD;
    }

    @Override
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (!players.containsKey(player.getUniqueId()))
            return;

        if (!getRegion().isInRegion(player.getLocation()))
            return;

        if (player.hasPermission(CommonPermissions.EDIT_PERM) && !isEnabled())
            return;

        event.setCancelled(true);
    }

    @Override
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (!players.containsKey(player.getUniqueId()))
            return;

        if (!getRegion().isInRegion(player.getLocation()))
            return;

        if (player.hasPermission(CommonPermissions.EDIT_PERM) && !isEnabled())
            return;

        event.setCancelled(true);
    }

    @Override
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event)
    {
        if (!event.hasItem())
            return;

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        if (!players.containsKey(player.getUniqueId()))
            return;

        ItemStack itemStack = event.getItem();
        if (!isSword(itemStack.getType()) && itemStack.getType() != Material.WATCH)
            return;

        if (inProgress())
            return;

        SpigotPlayerTank pt = players.get(player.getUniqueId());
        if (pt == null)
            return;

        if (event.getMaterial() == Material.WATCH)
        {
            if (pt.isReady())
            {
                pt.setReady(false);
                player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.READY_UP, CommonItemText.NOT_READY));
                return;
            }

            pt.setReady(true);
            player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.UNREADY, CommonItemText.READY));
            startMatch();
            return;
        }

        if (pt.isReady())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.MUST_UNREADY);
            return;
        }

        new MainSelectionMenu(plugin, this, player.getUniqueId()).open(player);
    }

    @Override
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event)
    {
        event.setCancelled(players.containsKey(event.getPlayer().getUniqueId()));
    }

    @Override
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        File file = plugin.getInventoryStorage().getPlayerFile(player.getUniqueId());
        if (!file.exists())
            return;

        plugin.getInventoryStorage().load(player.getUniqueId());
        player.sendMessage(ChatColor.GREEN + CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED);
    }

    @Override
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();
        if (getPlayers().containsKey(uuid))
            removePlayer(uuid);
    }

    @Override
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        SpigotPlayerTank pt = players.get(player.getUniqueId());
        if (pt == null)
            return;

        SpigotRegion region = getRegion();
        if (region.isInRegion(player.getLocation()))
            return;

        player.sendMessage(ChatColor.RED + CommonMessages.OUT_OF_BOUNDS);
        int x = 0, z = 0;
        Location location = player.getLocation();
        if (location.getX() > region.getMaxX())
            x = -1;
        if (location.getX() < region.getMinX())
            x = 1;
        if (location.getZ() > region.getMaxZ())
            z = -1;
        if (location.getZ() < region.getMinZ())
            z = 1;

        player.setVelocity(new Vector(x, 0, z));
    }

    @Override
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        if (event.getCause() == TeleportCause.PLUGIN)
            event.setCancelled(players.containsKey(event.getPlayer().getUniqueId()));
    }

    @Override
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        SpigotPlayerTank pt = players.get(player.getUniqueId());
        if (pt == null)
            return;

        if (pt.getTeam() == MTTeam.SPECTATOR)
            return;

        SpigotTank tank = pt.getTank();
        event.setCancelled(pt.isReloading(plugin));
        if (event.isCancelled())
            return;

        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents())
        {
            if (item == null)
                continue;

            if (item.getType() == Material.BOW && tank.getCannon() instanceof SpigotAutoLoader)
                continue;

            SpigotAutoLoader cannon = (SpigotAutoLoader) tank.getCannon();
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Arrays.asList(CommonItemText.CANNON,
                    CommonItemText.clipSize(pt.getClipSize(), cannon.getClipSize()),
                    CommonItemText.cycleTime(cannon.getCycleTime()),
                    CommonItemText.clipReloadTime(cannon.getReloadTime())));

            item.setItemMeta(meta);
        }
    }

    @Override
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        Entity entityDamager = event.getDamager();
        if (!(event.getEntity() instanceof Player) && (!(entityDamager instanceof Arrow) || !(entityDamager instanceof Player || event.getCause() != DamageCause.BLOCK_EXPLOSION) || event.getCause() != DamageCause.FALL))
            return;

        UUID damaged = event.getEntity().getUniqueId();
        if (players.get(damaged) != null)
            return;

        double damage = event.getDamage() * 2;
        if (entityDamager instanceof Arrow)
        {
            Arrow arrow = (Arrow) entityDamager;
            if (arrow.getShooter() instanceof Player)
            {
                UUID damager = ((Player) arrow.getShooter()).getUniqueId();
                if (players.get(damager) != null)
                {
                    playerHit(damager, damaged, damage);
                    arrows.put(arrow.getUniqueId(), new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for (Arrow a : getRegion().getWorld().getEntitiesByClass(Arrow.class))
                            {
                                if (!arrows.containsKey(a.getUniqueId()))
                                    continue;

                                if (getRegion().isInRegion(a.getLocation()))
                                    continue;

                                a.remove();
                                arrows.remove(a.getUniqueId());
                                cancel();
                            }
                        }
                    }.runTaskTimerAsynchronously(plugin, 1, Integer.MAX_VALUE).getTaskId());
                }
            }
        }
        else if (entityDamager instanceof Player)
        {
            if (players.get(entityDamager.getUniqueId()) != null)
            {
                UUID damager = entityDamager.getUniqueId();
                meleeHit(damaged, damager, damage);
            }
        }
        else if (event.getCause() == DamageCause.BLOCK_EXPLOSION)
        {
            Arrow arrow = null;
            for (UUID uuid : arrows.keySet())
                if (event.getDamager().getUniqueId().equals(uuid))
                    arrow = (Arrow) event.getDamager();

            if (arrow == null)
                return;

            UUID damager = ((Player) arrow.getShooter()).getUniqueId();
            playerHit(damaged, damager, damage);
            Bukkit.getScheduler().cancelTask(arrows.remove(arrow.getUniqueId()));
        }
        else if (event.getCause() == DamageCause.FALL)
            gravityHit(damaged, damage);
        else
            return;

        event.setCancelled(true);
    }

    @Override
    protected void gravityHit(UUID uuid, double damage)
    {
        double dmg = damage * 5;
        SpigotMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(uuid, (int) (sb.getPlayerHealth(uuid) - dmg));
        //TODO need to create a new message and custom code for accidental/purposeful suicide
        getPlayers().keySet().forEach(id -> Bukkit.getPlayer(uuid).sendMessage("need a message here"));
    }

    @Override
    protected void meleeHit(UUID rammed, UUID rammer, double damage)
    {
        double rammerDmg = damage * players.get(rammer).getTank().getType().getRamModifier();
        double rammedDmg = damage * players.get(rammed).getTank().getType().getRamModifier();
        if (rammerDmg > 0)
            playerHit(rammed, rammer, rammerDmg);

        if (rammedDmg > 0)
            playerHit(rammer, rammed, rammedDmg);
    }

    @Override
    protected void playerHit(UUID damaged, UUID damager, double damage)
    {
        SpigotMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(damaged, (int) (sb.getPlayerHealth(damaged) - (damage * 2) * 20));
        if (sb.getPlayerHealth(damaged) <= 0)
            triggerPlayerDeath(damaged, damager);
    }

    @Override
    protected void triggerPlayerDeath(UUID killerId, UUID killedId)
    {
        Player killed = Bukkit.getPlayer(killedId);
        Player killer = Bukkit.getPlayer(killerId);
        SpigotMTScoreboard sb = getScoreboard();
        String killedMsg = (sb.isOnGreen(killedId) ? ChatColor.GREEN : ChatColor.RED) + killed.getName();
        String killerMsg = (sb.isOnGreen(killerId) ? ChatColor.GREEN : ChatColor.RED) + killer.getName();
        getPlayers().keySet().forEach(uuid -> Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + CommonMessages.PREFIX + killedMsg + ChatColor.GOLD + " was killed by " + killerMsg + ChatColor.GOLD + "."));
    }

    @Override
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (!(event.getEntity().getShooter() instanceof Player))
                    return;

                if (!(event.getEntity() instanceof Arrow))
                    return;

                Arrow arrow = (Arrow) event.getEntity();
                if (!arrows.containsKey(arrow.getUniqueId()))
                    return;

                Player player = (Player) arrow.getShooter();
                if (!players.containsKey(player.getUniqueId()))
                    return;

                //TODO need to test explosion with 0F
                arrow.getWorld().createExplosion(arrow.getLocation(), 0F);
                Bukkit.getScheduler().cancelTask(arrows.get(arrow.getUniqueId()));
                arrow.remove();
            }
        }.runTaskLater(plugin, 1L);
    }

    @Override
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event)
    {
        if (event.isCancelled())
            return;

        if (getRegion().isInRegion(event.getBlock().getLocation()))
            event.blockList().clear();
    }
}
