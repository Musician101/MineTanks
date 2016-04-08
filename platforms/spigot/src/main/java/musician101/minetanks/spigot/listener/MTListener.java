package musician101.minetanks.spigot.listener;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.battlefield.SpigotBattleFieldStorage;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.event.AttemptMenuOpenEvent;
import musician101.minetanks.spigot.event.PlayerTankDamageEvent;
import musician101.minetanks.spigot.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.spigot.handler.ExplosionTracker;
import musician101.minetanks.spigot.handler.ReloadHandler;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import musician101.minetanks.spigot.util.SpigotRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public class MTListener implements Listener
{
    private final SpigotMineTanks plugin;

    public MTListener(SpigotMineTanks plugin)
    {
        this.plugin = plugin;
    }

    private boolean isInField(UUID playerId)
    {
        for (String name : plugin.getFieldStorage().getFields().keySet())
            if (plugin.getFieldStorage().getField(name).getPlayerTank(playerId) != null)
                return true;

        return false;
    }

    private boolean isSword(Material material)
    {
        //Stick is the default item if a player hasn't chosen a tank.
        return material == Material.STICK || material == Material.WOOD_SWORD || material == Material.STONE_SWORD || material == Material.IRON_SWORD || material == Material.GOLD_SWORD || material == Material.DIAMOND_SWORD;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        if (event.isCancelled())
            return;

        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            Player player = event.getPlayer();
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.getRegion().isInRegion(player.getLocation()))
            {
                if (player.hasPermission(CommonPermissions.EDIT_PERM) && !field.isEnabled())
                    return;

                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (event.isCancelled())
            return;

        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            Player player = event.getPlayer();
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.getRegion().isInRegion(player.getLocation()))
            {
                if (player.hasPermission(CommonPermissions.EDIT_PERM) && !field.isEnabled())
                    return;

                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getType() == Material.BOW)
            return;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (!isSword(event.getItem().getType()) && event.getItem().getType() != Material.WATCH)
            return;

        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.inProgress())
                return;

            if (field.getPlayerTank(event.getPlayer().getUniqueId()) != null)
            {
                Bukkit.getPluginManager().callEvent(new AttemptMenuOpenEvent(event.getItem().getType(), field.getName(), field.getPlayerTank(player.getUniqueId()), player.getUniqueId()));
                return;
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event)
    {
        event.setCancelled(isInField(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        File file = plugin.getInventoryStorage().getPlayerFile(player.getUniqueId());
        if (!file.exists())
            return;

        player.sendMessage(ChatColor.GREEN + CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED);
        plugin.getInventoryStorage().load(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event)
    {
        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            if (plugin.getFieldStorage().getField(name).getPlayerTank(event.getPlayer().getUniqueId()) != null)
            {
                plugin.getFieldStorage().getField(name).removePlayer(event.getPlayer().getUniqueId());
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                SpigotPlayerTank pt = field.getPlayerTank(player.getUniqueId());
                if (pt.getTeam() != MTTeam.ASSIGNED)
                    return;

                SpigotRegion region = field.getRegion();
                if (!region.isInRegion(player.getLocation()))
                {
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
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        if (event.getCause() == TeleportCause.COMMAND)
            event.setCancelled(isInField(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                SpigotPlayerTank pt = field.getPlayerTank(player.getUniqueId());
                if (pt.getTeam() == MTTeam.SPECTATOR)
                    return;

                SpigotTank tank = pt.getTank();
                ReloadHandler reload = new ReloadHandler(plugin, player, tank.getCannon());
                event.setCancelled(reload.isReloading(pt));
                if (!event.isCancelled())
                {
                    Inventory inv = player.getInventory();
                    for (ItemStack item : inv.getContents())
                    {
                        if (item == null)
                            continue;

                        if (item.getType() == Material.ARROW)
                            item.setAmount(item.getAmount() - 1);
                        else if (item.getType() == Material.BOW && tank.getCannon() instanceof SpigotAutoLoader)
                        {
                            SpigotAutoLoader cannon = (SpigotAutoLoader) tank.getCannon();
                            ItemMeta meta = item.getItemMeta();
                            meta.setLore(Arrays.asList(CommonItemText.CANNON,
                                    CommonItemText.clipSize(pt.getClipSize(), cannon.getClipSize()),
                                    CommonItemText.cycleTime(cannon.getCycleTime()),
                                    CommonItemText.clipReloadTime(cannon.getReloadTime())));

                            item.setItemMeta(meta);
                        }
                    }
                }
                return;
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        Entity entityDamager = event.getDamager();
        if (!(event.getEntity() instanceof Player) && (!(entityDamager instanceof Arrow) || !(entityDamager instanceof Player || event.getCause() != DamageCause.BLOCK_EXPLOSION) || event.getCause() != DamageCause.FALL))
            return;

        UUID damaged = event.getEntity().getUniqueId();
        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayerTank(damaged) != null)
            {
                int damage = (int) event.getDamage() * 2;
                if (entityDamager instanceof Arrow && ((Arrow) entityDamager).getShooter() instanceof Player && field.getPlayerTank(((Player) ((Arrow) entityDamager).getShooter()).getUniqueId()) != null)
                {
                    Arrow arrow = (Arrow) entityDamager;
                    UUID damager = ((Player) arrow.getShooter()).getUniqueId();
                    Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.PENETRATION, damaged, damager, field, damage));
                    ExplosionTracker.addArrow(arrow);
                }
                else if (entityDamager instanceof Player && field.getPlayerTank(entityDamager.getUniqueId()) != null)
                {
                    UUID damager = entityDamager.getUniqueId();
                    Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.RAM, damaged, damager, field, damage));
                }
                else if (event.getCause() == DamageCause.ENTITY_EXPLOSION)
                {
                    Arrow arrow = null;
                    for (UUID uuid : ExplosionTracker.getTracker())
                        if (event.getDamager().getUniqueId().equals(uuid))
                            arrow = (Arrow) event.getDamager();

                    if (arrow == null)
                        return;

                    UUID damager = ((Arrow) arrow.getShooter()).getUniqueId();
                    Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.SPLASH, damaged, damager, field, damage));
                    ExplosionTracker.removeArrow(arrow);
                }
                else if (event.getCause() == DamageCause.FALL)
                    Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.FALL, damaged, field, damage));

                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onArrowHitBlock(final ProjectileHitEvent event)
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
                Player player = (Player) arrow.getShooter();
                if (!isInField(player.getUniqueId()))
                    return;

                ExplosionTracker.addArrow(arrow);
                //TODO need to test
                arrow.getWorld().createExplosion(arrow.getLocation(), 0F);
                arrow.remove();
            }
        }.runTaskLater(plugin, 1L);
    }

    @EventHandler
    public void onExplosion(BlockExplodeEvent event)
    {
        if (event.isCancelled())
            return;

        SpigotBattleFieldStorage fieldStorage = plugin.getFieldStorage();
        for (String name : fieldStorage.getFields().keySet())
        {
            SpigotBattleField field = fieldStorage.getField(name);
            if (field.getRegion().isInRegion(event.getBlock().getLocation()))
                event.blockList().clear();
        }
    }
}
