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
import io.musician101.minetanks.spigot.util.SpigotInventoryStorage;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;

public class SpigotBattleField extends AbstractBattleField<Player, SpigotPlayerTank, SpigotRegion, SpigotMTScoreboard, Material, Location, BlockBreakEvent, BlockPlaceEvent, PlayerInteractEvent, PlayerDropItemEvent, PlayerJoinEvent, PlayerQuitEvent, PlayerMoveEvent, PlayerTeleportEvent, EntityShootBowEvent, EntityDamageByEntityEvent, ProjectileHitEvent, BlockExplodeEvent> implements Listener
{
    private final Map<UUID, Integer> arrows = new HashMap<>();

    public SpigotBattleField(String name, boolean enabled, SpigotRegion region, Location greenSpawn, Location redSpawn, Location spectators)
    {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new SpigotMTScoreboard());
        SpigotMineTanks plugin = SpigotMineTanks.instance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean addPlayer(Player player, MTTeam team)
    {
        UUID uuid = player.getUniqueId();
        if (!players.containsKey(uuid) && !SpigotMineTanks.instance().getInventoryStorage().save(player))
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
    protected void arrowIsDamager(UUID damager, double damage, Player damaged)
    {
        Arrow arrow = null;
        for (Entity entity : getRegion().getWorld().getEntitiesByClass(Arrow.class))
            if (damager.equals(entity.getUniqueId()))
                arrow = (Arrow) entity;

        if (arrow == null)
            return;

        if (arrow.getShooter() instanceof Player && players.get(((Player) arrow.getShooter()).getUniqueId()) != null)
        {
            playerHit((Player) arrow.getShooter(), damaged, damage);
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
            }.runTaskTimerAsynchronously(SpigotMineTanks.instance(), 1, Integer.MAX_VALUE).getTaskId());
        }
    }

    @Override
    protected boolean assignTeams(List<UUID> playersList)
    {
        Collections.shuffle(playersList);
        for (UUID uuid : playersList)
        {
            SpigotPlayerTank pt = players.get(uuid);
            if (getScoreboard().getGreenTeamSize() == 0 && getScoreboard().getRedTeamSize() == 0 && unassigned < 2)
                return false;

            Player player = Bukkit.getPlayer(uuid);
            if (getScoreboard().getGreenTeamSize() == getScoreboard().getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTeam(null);
            }
            else if (getScoreboard().getGreenTeamSize() <= getScoreboard().getRedTeamSize())
            {
                getScoreboard().addGreenPlayer(player);
                pt.setTeam(MTTeam.ASSIGNED);
                pt.getTank().applySpeedEffect(uuid);
            }
            else if (getScoreboard().getGreenTeamSize() >= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addRedPlayer(player);
                pt.getTank().applySpeedEffect(uuid);
            }

            unassigned--;
        }

        return true;
    }

    @Override
    protected void blockExplosion(Player damager, double damage, Player damaged)
    {
        playerHit(damaged, damager, damage);
        Bukkit.getScheduler().cancelTask(arrows.remove(damager.getUniqueId()));
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
                getScoreboard().playerDeath(player);
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
    protected void gravityHit(Player player, double damage)
    {
        double dmg = damage * 5;
        SpigotMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(player, (int) (sb.getPlayerHealth(player) - dmg));
        //TODO need to create a new message and custom code for accidental/purposeful suicide
        getPlayers().keySet().forEach(id -> Bukkit.getPlayer(id).sendMessage("need a message here"));
    }

    @Override
    protected boolean handleWatch(Player player)
    {
        SpigotPlayerTank pt = players.get(player.getUniqueId());
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
    protected boolean isMenuItem(Material material)
    {
        //Stick is the default item if a player hasn't chosen a tank.
        return Arrays.asList(Material.STICK, Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.WATCH).contains(material);
    }

    @Override
    protected void meleeHit(Player rammed, Player rammer, double damage)
    {
        double rammerDmg = damage * players.get(rammer.getUniqueId()).getTank().getType().getRamModifier();
        double rammedDmg = damage * players.get(rammed.getUniqueId()).getTank().getType().getRamModifier();
        if (rammerDmg > 0)
            playerHit(rammed, rammer, rammerDmg);

        if (rammedDmg > 0)
            playerHit(rammer, rammed, rammedDmg);
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
    public void onBlockExplode(BlockExplodeEvent event)
    {
        if (event.isCancelled())
            return;

        if (getRegion().isInRegion(event.getBlock().getLocation()))
            event.blockList().clear();
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
        if (!isMenuItem(material) && inProgress())
            return;

        if (!processBlockInteract(player))
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
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event)//NOSONAR
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
        event.setCancelled(pt.isReloading());
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
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)//NOSONAR
    {
        if (!inProgress())
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        Entity entityDamager = event.getDamager();
        if (!(entityDamager instanceof Arrow) && !(entityDamager instanceof Player) && event.getCause() != DamageCause.BLOCK_EXPLOSION && event.getCause() != DamageCause.FALL)
            return;

        //TODO using spectate command on a match in progress doesn't assign the scoreboard=
        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();
        UUID uuid = event.getEntity().getUniqueId();
        if (!players.containsKey(uuid))
            return;

        SpigotPlayerTank damagedPT = players.get(uuid);
        if (damagedPT.getTeam() != MTTeam.ASSIGNED)
            return;

        DamageCause cause = event.getCause();
        double damage = event.getDamage();
        if (entityDamager instanceof Arrow)
            arrowIsDamager(damager.getUniqueId(), damage, damaged);
        else if (entityDamager instanceof Player)
            playerIsDamager(damaged, damage, damager);
        else if (cause == DamageCause.FALL)
            gravityHit(damaged, damage);
        else if (cause == DamageCause.BLOCK_EXPLOSION)
            blockExplosion(damaged, damage, damager);

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
        SpigotInventoryStorage invStorage = SpigotMineTanks.instance().getInventoryStorage();
        File file = invStorage.getPlayerFile(player.getUniqueId());
        if (!file.exists())
            return;

        invStorage.load(player);
        player.sendMessage(ChatColor.GREEN + CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED);
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
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (getPlayers().containsKey(uuid))
            removePlayer(player);
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
        }.runTaskLater(SpigotMineTanks.instance(), 1L);
    }

    @Override
    protected void playerHit(Player damaged, Player damager, double damage)
    {
        SpigotMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(damaged, (int) (sb.getPlayerHealth(damaged) - (damage * 2) * 20));
        if (sb.getPlayerHealth(damaged) <= 0)
            triggerPlayerDeath(damager, damaged);
    }

    @Override
    protected void playerIsDamager(Player damager, double damage, Player damaged)
    {
        if (players.get(damager.getUniqueId()) != null)
            meleeHit(damaged, damager, damage);
    }

    @Override
    public void playerKilled(Player player)
    {
        players.get(player.getUniqueId()).killed();
        getScoreboard().playerDeath(player);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.teleport(getSpectators());
        endMatch();
    }

    @Override
    protected boolean processBlockInteract(Player player)
    {
        Material material = player.getInventory().getItemInMainHand().getType();
        SpigotPlayerTank pt = players.get(player.getUniqueId());
        if (material == Material.WATCH)
        {
            return handleWatch(player);
        }
        else if (pt.isReady())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.MUST_UNREADY);
            return false;
        }

        new MainSelectionMenu(this, player);
        return true;
    }

    @Override
    public boolean removePlayer(Player player)
    {
        UUID uuid = player.getUniqueId();
        SpigotPlayerTank pt = players.get(uuid);
        if (pt == null)
            return false;

        if (getScoreboard().isOnGreen(player) || getScoreboard().isOnRed(player))
            getScoreboard().playerDeath(player);

        players.remove(uuid);
        SpigotMineTanks.instance().getInventoryStorage().load(player);
        if (unassigned > 0 && pt.getTeam() != MTTeam.SPECTATOR)
            unassigned--;

        return true;
    }

    @Override
    public void saveToFile(File battlefields)
    {
        Logger logger = SpigotMineTanks.instance().getLogger();
        File file = new File(battlefields, CommonConfig.battlefieldFile(this));
        try
        {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();//NOSONAR
        }
        catch (IOException e)//NOSONAR
        {
            logger.warning(CommonMessages.fileCreateFailed(file));
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
        catch (IOException e)//NOSONAR
        {
            logger.warning(CommonMessages.fileSaveFailed(file));
        }
    }

    @Override
    protected void setPlayerScoreboards()
    {
        players.keySet().stream()
                .map(Bukkit::getPlayer)
                .forEach(getScoreboard()::setPlayerScoreboard);
    }

    @Override
    protected void setUpPlayers(List<UUID> playersList)
    {
        for (UUID uuid : playersList)
        {
            SpigotPlayerTank pt = players.get(uuid);
            SpigotTank tank = pt.getTank();
            Player player = Bukkit.getPlayer(uuid);
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (getScoreboard().isOnGreen(player))
                    player.teleport(getGreenSpawn());
                else if (getScoreboard().isOnRed(player))
                    player.teleport(getRedSpawn());

                getScoreboard().setPlayerHealth(player, tank.getHealth());
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
                }.runTaskLater(SpigotMineTanks.instance(), 1L);
                pt.isReloading();
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
    protected void triggerPlayerDeath(Player killer, Player killed)
    {
        SpigotMTScoreboard sb = getScoreboard();
        String killedMsg = (sb.isOnGreen(killed) ? ChatColor.GREEN : ChatColor.RED) + killed.getName();
        String killerMsg = (sb.isOnGreen(killer) ? ChatColor.GREEN : ChatColor.RED) + killer.getName();
        getPlayers().keySet().forEach(uuid ->
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + CommonMessages.PREFIX + killedMsg + ChatColor.GOLD + " was killed by " + killerMsg + ChatColor.GOLD + "."));
        playerKilled(killed);
    }
}
