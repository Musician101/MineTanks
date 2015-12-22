package musician101.minetanks.spigot.listener;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.event.AttemptMenuOpenEvent;
import musician101.minetanks.spigot.event.PlayerTankDamageEvent;
import musician101.minetanks.spigot.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.spigot.handler.ExplosionTracker;
import musician101.minetanks.spigot.handler.ReloadHandler;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.tank.modules.cannon.SpigotAutoLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
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
                if (pt.getTeam() == MTTeam.SPECTATOR)
                    return;

                if (field.getRegion().isInRegion(player.getLocation()))
                {
                    player.sendMessage(ChatColor.RED + CommonMessages.OUT_OF_BOUNDS);
                    player.teleport(player.getLocation().subtract(player.getVelocity()));
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
                event.setCancelled(reload.isReloading());
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
        if (!(event.getEntity() instanceof Player) && (!(event.getDamager() instanceof Arrow) || !(event.getDamager() instanceof Player || event.getCause() != DamageCause.BLOCK_EXPLOSION) || event.getCause() != DamageCause.FALL))
            return;

        UUID damaged = event.getEntity().getUniqueId();
        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            SpigotBattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayerTank(damaged) != null)
            {
                int damage = (int) event.getDamage() * 2;
                if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player && field.getPlayerTank(((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId()) != null)
                {
                    Arrow arrow = (Arrow) event.getDamager();
                    UUID damager = ((Player) arrow.getShooter()).getUniqueId();
                    Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.PENETRATION, damaged, damager, field, damage));
                    ExplosionTracker.addArrow(arrow);
                }
                else if (event.getDamager() instanceof Player && field.getPlayerTank(event.getDamager().getUniqueId()) != null)
                {
                    UUID damager = event.getDamager().getUniqueId();
                    Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.RAM, damaged, damager, field, damage));
                }
                else if (event.getCause() == DamageCause.ENTITY_EXPLOSION)
                {
                    Arrow arrow = null;
                    for (Arrow a : ExplosionTracker.getTracker())
                        if (event.getDamager().getUniqueId() == a.getUniqueId())
                            arrow = a;

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

                Player player = (Player) event.getEntity().getShooter();
                if (!isInField(player.getUniqueId()))
                    return;

                Arrow arrow = (Arrow) event.getEntity();
                ExplosionTracker.addArrow(arrow);
                event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 1F);
                event.getEntity().remove();
            }
        }.runTaskLater(plugin, 1L);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event)
    {
        if (event.isCancelled())
            return;

        for (Block block : event.blockList())
        {
            for (String name : plugin.getFieldStorage().getFields().keySet())
            {
                if (plugin.getFieldStorage().getField(name).getRegion().isInRegion(block.getLocation()))
                {
                    event.blockList().clear();
                    return;
                }
            }
        }
    }
}
