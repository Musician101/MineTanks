package io.musician101.minetanks.spigot.battlefield;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.AbstractBattleField;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import io.musician101.minetanks.spigot.menu.MainSelectionMenu;
import io.musician101.minetanks.spigot.scoreboard.SpigotMTScoreboard;
import io.musician101.minetanks.spigot.tank.SpigotTank;
import io.musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import io.musician101.minetanks.spigot.util.MTUtils;
import io.musician101.minetanks.spigot.util.SpigotRegion;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class SpigotBattleField extends AbstractBattleField<SpigotMineTanks, SpigotPlayerTank, SpigotRegion, SpigotMTScoreboard, Material, Location, BlockBreakEvent, BlockPlaceEvent, PlayerInteractEvent, PlayerDropItemEvent, PlayerJoinEvent, PlayerQuitEvent, PlayerMoveEvent, PlayerTeleportEvent, EntityShootBowEvent, EntityDamageByEntityEvent, ProjectileHitEvent, BlockExplodeEvent> implements Listener
{
    private final Map<UUID, Integer> arrows = new HashMap<>();

    public SpigotBattleField(SpigotMineTanks plugin, String name, boolean enabled, SpigotRegion region, Location greenSpawn, Location redSpawn, Location spectators)
    {
        super(plugin, name, enabled, region, greenSpawn, redSpawn, spectators, new SpigotMTScoreboard());
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean addPlayer(UUID uuid, MTTeam team)
    {
        Player player = Bukkit.getPlayer(uuid);
        if (!players.containsKey(uuid) && !plugin.getInventoryStorage().save(uuid))
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

        if (getScoreboard().isOnGreen(uuid) || getScoreboard().isOnRed(uuid))
            getScoreboard().playerDeath(uuid);

        players.remove(uuid);
        plugin.getInventoryStorage().load(uuid);
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
    protected void setUpPlayers(List<UUID> playersList)
    {
        for (UUID uuid : playersList)
        {
            final SpigotPlayerTank pt = players.get(uuid);
            final SpigotTank tank = pt.getTank();
            final Player player = Bukkit.getPlayer(uuid);
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (getScoreboard().isOnGreen(uuid))
                    player.teleport(getGreenSpawn());
                else if (getScoreboard().isOnRed(uuid))
                    player.teleport(getRedSpawn());

                getScoreboard().setPlayerHealth(uuid, tank.getHealth());
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.getInventory().clear();
                        tank.getWeapons().forEach(player.getInventory()::addItem);
                        player.getInventory().setHelmet(tank.getHelmet());
                        player.getInventory().setChestplate(tank.getChestplate());
                        player.getInventory().setLeggings(tank.getLeggings());
                        player.getInventory().setBoots(tank.getBoots());
                    }
                }.runTaskLater(plugin, 1L);
                pt.isReloading(plugin);
                //TODO spectators tank doesn't get set to null and still receives gear
                //TODO field counts spectator as unassigned
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
        if (!inProgress())
            return;

        if (forced || getScoreboard().getGreenTeamSize() == 0 || getScoreboard().getRedTeamSize() == 0)
        {
            setInProgress(false);
            for (Entry<UUID, SpigotPlayerTank> entry : players.entrySet())
            {
                Player player = Bukkit.getPlayer(entry.getKey());
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);
                player.getInventory().clear();
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.SPEED);
                SpigotPlayerTank pt = entry.getValue();
                player.teleport(getSpectators());
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTank(null);
                getScoreboard().playerDeath(entry.getKey());
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
    protected boolean menuItem(Material material)
    {
        //Stick is the default item if a player hasn't chosen a tank.
        return Arrays.asList(Material.STICK, Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.WATCH).contains(material);
    }

    @Override
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
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
        Player player = event.getPlayer();
        if (!getRegion().isInRegion(player.getLocation()))
            return;

        if (player.hasPermission(CommonPermissions.EDIT_PERM) && !isEnabled())
            return;

        event.setCancelled(true);
    }

    @Override
    protected boolean handleWatch(UUID uuid)
    {
        Player player = Bukkit.getPlayer(uuid);
        SpigotPlayerTank pt = players.get(uuid);
        if (pt.getTank() == null)
        {
            //TODO need to make this a CommonMessage
            player.sendMessage(ChatColor.RED + CommonMessages.PREFIX + "You cannot ready until you have selected a tank.");
            return false;
        }
        else if (pt.isReady())
        {
            pt.setReady(false);
            player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            return true;
        }

        pt.setReady(true);
        player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.UNREADY, CommonItemText.READY));
        startMatch();
        return true;
    }

    @Override
    protected boolean processBlockInteract(UUID uuid)
    {
        Player player = Bukkit.getPlayer(uuid);
        //When updating to 1.9, drop getItemInHand()
        //noinspection deprecation
        @SuppressWarnings("deprecated")
        Material material = player.getItemInHand().getType();
        SpigotPlayerTank pt = players.get(uuid);
        if (material == Material.WATCH)
        {
            return handleWatch(uuid);
        }
        else if (pt.isReady())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.MUST_UNREADY);
            return false;
        }

        new MainSelectionMenu(plugin, this, player.getUniqueId()).open(player);
        return true;
    }

    @Override
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event)
    {
        if (!event.hasItem())
            return;

        Player player = event.getPlayer();
        if (!players.containsKey(player.getUniqueId()))
            return;

        Material material = event.getMaterial();
        if (!menuItem(material) && inProgress())
            return;

        if (!processBlockInteract(player.getUniqueId()))
            return;

        event.setCancelled(true);
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
        int x = 0;
        int z = 0;
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
        if (event.getCause() != TeleportCause.PLUGIN)
            event.setCancelled(players.containsKey(event.getPlayer().getUniqueId()));
    }

    @Override
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        if (!players.containsKey(player.getUniqueId()))
            return;

        SpigotPlayerTank pt = players.get(player.getUniqueId());
        if (pt.getTeam() == MTTeam.SPECTATOR)
            return;

        SpigotTank tank = pt.getTank();
        event.setCancelled(pt.isReloading(plugin));
        if (event.isCancelled())
            return;

        if (tank.getCannon() instanceof SpigotAutoLoader)
        {
            ItemStack itemStack = event.getBow();
            SpigotAutoLoader cannon = (SpigotAutoLoader) tank.getCannon();
            if (pt.getClipSize() < 1)
                pt.setClipSize(cannon.getClipSize());
            else
                pt.setClipSize(pt.getClipSize() - 1);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(Arrays.asList(CommonItemText.CANNON,
                    CommonItemText.clipSize(pt.getClipSize(), cannon.getClipSize()),
                    CommonItemText.cycleTime(cannon.getCycleTime()),
                    CommonItemText.clipReloadTime(cannon.getReloadTime())));

            itemStack.setItemMeta(meta);
        }

        Inventory inv = player.getInventory();
        int slot = inv.first(Material.ARROW);
        if (slot == -1)
            return;

        ItemStack itemStack = inv.getItem(slot);
        itemStack.setAmount(itemStack.getAmount() - 1);
    }

    @Override
    protected void arrowIsDamager(UUID damager, double damage, UUID damaged)
    {
        Arrow arrow = null;
        for (Entity entity : getRegion().getWorld().getEntitiesByClass(Arrow.class))
            if (damager.equals(entity.getUniqueId()))
                arrow = (Arrow) entity;

        if (arrow == null)
            return;

        if (arrow.getShooter() instanceof Player && players.get(((Player) arrow.getShooter()).getUniqueId()) != null)
        {
            playerHit(((Player) arrow.getShooter()).getUniqueId(), damaged, damage);
            arrows.put(arrow.getUniqueId(), new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    getRegion().getWorld().getEntitiesByClass(Arrow.class).stream().filter(a -> arrows.containsKey(a.getUniqueId()) && getRegion().isInRegion(a.getLocation())).forEach(a ->
                    {
                        a.remove();
                        arrows.remove(a.getUniqueId());
                        cancel();
                    });
                }
            }.runTaskTimerAsynchronously(plugin, 1, Integer.MAX_VALUE).getTaskId());
        }
    }

    @Override
    protected void playerIsDamager(UUID damager, double damage, UUID damaged)
    {
        if (players.get(damager) != null)
            meleeHit(damaged, damager, damage);
    }

    @Override
    protected void blockExplosion(UUID damager, double damage, UUID damaged)
    {
        playerHit(damaged, damager, damage);
        Bukkit.getScheduler().cancelTask(arrows.remove(damager));
    }

    @Override
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if (!inProgress())
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        Entity entityDamager = event.getDamager();
        if (!(entityDamager instanceof Arrow) && !(entityDamager instanceof Player) && event.getCause() != DamageCause.BLOCK_EXPLOSION && event.getCause() != DamageCause.FALL)
            return;

        //TODO using spectate command on a match in progress doesn't assign the scoreboard=
        UUID damaged = event.getEntity().getUniqueId();
        if (!players.containsKey(damaged))
            return;

        SpigotPlayerTank damagedPT = players.get(damaged);
        if (damagedPT.getTeam() != MTTeam.ASSIGNED)
            return;

        DamageCause cause = event.getCause();
        double damage = event.getDamage();
        if (entityDamager instanceof Arrow)
            arrowIsDamager(entityDamager.getUniqueId(), damage, damaged);
        else if (entityDamager instanceof Player)
            playerIsDamager(entityDamager.getUniqueId(), damage, damaged);
        else if (cause == DamageCause.FALL)
            gravityHit(damaged, damage);
        else if (cause == DamageCause.BLOCK_EXPLOSION)
            blockExplosion(entityDamager.getUniqueId(), damage, damaged);

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
            triggerPlayerDeath(damager, damaged);
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
        playerKilled(killedId);
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
                if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Arrow)
                {
                    Arrow arrow = (Arrow) event.getEntity();
                    if (!arrows.containsKey(arrow.getUniqueId()))
                        return;

                    Player player = (Player) arrow.getShooter();
                    if (!players.containsKey(player.getUniqueId()))
                        return;

                    arrow.getWorld().createExplosion(arrow.getLocation(), 1F);
                    Bukkit.getScheduler().cancelTask(arrows.get(arrow.getUniqueId()));
                    arrow.remove();
                }
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
