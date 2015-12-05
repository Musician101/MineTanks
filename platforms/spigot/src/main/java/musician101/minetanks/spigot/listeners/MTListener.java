package musician101.minetanks.spigot.listeners;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.battlefield.player.PlayerTank;
import musician101.minetanks.spigot.events.AttemptMenuOpenEvent;
import musician101.minetanks.spigot.events.PlayerTankDamageEvent;
import musician101.minetanks.spigot.events.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.spigot.handlers.ExplosionTracker;
import musician101.minetanks.spigot.handlers.ReloadHandler;
import musician101.minetanks.spigot.tank.Tank;
import musician101.minetanks.spigot.tank.modules.cannon.AutoLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
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
    private final MineTanks plugin;

    public MTListener(MineTanks plugin)
    {
        this.plugin = plugin;
    }

    private boolean isInField(UUID playerId)
    {
        for (String name : plugin.getFieldStorage().getFields().keySet())
            if (plugin.getFieldStorage().getField(name).getPlayer(playerId) != null)
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
            BattleField field = plugin.getFieldStorage().getField(name);
            double x = event.getBlock().getX();
            double y = event.getBlock().getY();
            double z = event.getBlock().getZ();
            double minX = field.getSpigotRegion().getMinX();
            double maxX = field.getSpigotRegion().getMaxX();
            double minY = field.getSpigotRegion().getMinY();
            double maxY = field.getSpigotRegion().getMaxY();
            double minZ = field.getSpigotRegion().getMinZ();
            double maxZ = field.getSpigotRegion().getMaxZ();
            if ((x >= minX && x <= maxX) && (y >= minY && y <= maxY) && (z >= minZ && z <= maxZ))
            {
                if (event.getPlayer().hasPermission(CommonPermissions.EDIT_PERM) && !field.isEnabled())
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
            BattleField field = plugin.getFieldStorage().getField(name);
            double x = event.getBlock().getX();
            double y = event.getBlock().getY();
            double z = event.getBlock().getZ();
            double minX = field.getSpigotRegion().getMinX();
            double maxX = field.getSpigotRegion().getMaxX();
            double minY = field.getSpigotRegion().getMinY();
            double maxY = field.getSpigotRegion().getMaxY();
            double minZ = field.getSpigotRegion().getMinZ();
            double maxZ = field.getSpigotRegion().getMaxZ();
            if ((x >= minX && x <= maxX) && (y >= minY && y <= maxY) && (z >= minZ && z <= maxZ))
            {
                if (event.getPlayer().hasPermission(CommonPermissions.EDIT_PERM) && !field.isEnabled())
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
            BattleField field = plugin.getFieldStorage().getField(name);
            if (field.inProgress())
                return;

            if (field.getPlayer(event.getPlayer().getUniqueId()) != null)
                Bukkit.getPluginManager().callEvent(new AttemptMenuOpenEvent(event.getItem().getType(), field.getName(), field.getPlayer(player.getUniqueId()), player.getUniqueId()));
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
        File file = plugin.getInventoryStorage().getPlayerFile(player);
        if (!file.exists())
            return;

        player.sendMessage(ChatColor.GREEN + CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED);
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        for (int slot = 0; slot < player.getInventory().getSize(); slot++)
            player.getInventory().setItem(slot, yml.getItemStack(CommonConfig.INVENTORY + slot));

        ItemStack[] armor = new ItemStack[4];
        for (int slot = 0; slot < player.getInventory().getArmorContents().length; slot++)
            armor[slot] = yml.getItemStack(CommonConfig.ARMOR + slot);

        player.getInventory().setArmorContents(armor);
        player.teleport(Location.deserialize(yml.getValues(true)));
        file.delete();
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event)
    {
        for (String name : plugin.getFieldStorage().getFields().keySet())
        {
            if (plugin.getFieldStorage().getField(name).getPlayer(event.getPlayer().getUniqueId()) != null)
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
            BattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayer(player.getUniqueId()) != null)
            {
                PlayerTank pt = field.getPlayer(player.getUniqueId());
                if (pt.getTeam() == MTTeam.SPECTATOR)
                    return;

                double minX = field.getSpigotRegion().getMinX();
                double maxX = field.getSpigotRegion().getMaxX();
                double minZ = field.getSpigotRegion().getMinZ();
                double maxZ = field.getSpigotRegion().getMaxZ();
                double x = player.getLocation().getX();
                double z = player.getLocation().getZ();
                double correction = 2.0;
                if (x <= minX || x >= maxX || z <= minZ || z >= maxZ)
                {
                    if (x <= minX)
                        x = minX + correction;
                    else if (x >= maxX)
                        x = maxX - correction;
                    if (z <= minZ)
                        z = minZ + correction;
                    else if (z >= maxZ)
                        z = maxZ - correction;

                    player.sendMessage(ChatColor.RED + CommonMessages.OUT_OF_BOUNDS);
                    player.teleport(new Location(player.getLocation().getWorld(), x, player.getLocation().getY(), z));
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
            BattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayer(player.getUniqueId()) != null)
            {
                PlayerTank pt = field.getPlayer(player.getUniqueId());
                if (pt.getTeam() == MTTeam.SPECTATOR)
                    return;

                Tank tank = pt.getTank();
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
                        else if (item.getType() == Material.BOW && tank.getCannon() instanceof AutoLoader)
                        {
                            AutoLoader cannon = (AutoLoader) tank.getCannon();
                            ItemMeta meta = item.getItemMeta();
                            meta.setLore(Arrays.asList(CommonItemText.CANNON, SpigotReference.autoLoaderClipSize(CommonItemText.CLIP_SIZE, pt.getClipSize(), cannon.getClipSize()), SpigotReference.number(CommonItemText.CYCLE_TIME, cannon.getCycleTime()), SpigotReference.number(CommonItemText.CLIP_RELOAD_TIME, cannon.getReloadTime())));
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
            BattleField field = plugin.getFieldStorage().getField(name);
            if (field.getPlayer(damaged) != null)
            {
                int damage = (int) event.getDamage() * 2;
                if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player && field.getPlayer(((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId()) != null)
                {
                    Arrow arrow = (Arrow) event.getDamager();
                    UUID damager = ((Player) arrow.getShooter()).getUniqueId();
                    Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.PENETRATION, damaged, damager, field, damage));
                    ExplosionTracker.addArrow(arrow);
                }
                else if (event.getDamager() instanceof Player && field.getPlayer(event.getDamager().getUniqueId()) != null)
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
                if (plugin.getFieldStorage().getField(name).getSpigotRegion().isInRegion(block.getLocation()))
                {
                    event.blockList().clear();
                    return;
                }
            }
        }
    }
}
