package musician101.minetanks.listeners;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.events.AttemptMenuOpenEvent;
import musician101.minetanks.events.PlayerFireEvent;
import musician101.minetanks.events.PlayerTankDamageEvent;
import musician101.minetanks.events.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.events.PlayerTankDeathEvent;
import musician101.minetanks.handlers.ExplosionTracker;

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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MTListener implements Listener
{
	MineTanks plugin;
	
	public MTListener(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	private boolean isInField(UUID playerId)
	{
		for (String name : plugin.fieldStorage.getFields().keySet())
			if (plugin.fieldStorage.getField(name).getPlayer(playerId) != null)
				return true;
		
		return false;
	}
	
	private boolean isSword(Material material)
	{
		//It's technically a sword without a blade.
		//Stick is the default item if a player hasn't chosen a tank.
		if (material == Material.STICK)
			return true;
		
		if (material == Material.WOOD_SWORD)
			return true;
		
		if (material == Material.STONE_SWORD)
			return true;
		
		if (material == Material.IRON_SWORD)
			return true;
		
		if (material == Material.GOLD_SWORD)
			return true;
		
		if (material == Material.DIAMOND_SWORD)
			return true;
		
		return false;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		event.setCancelled(isInField(event.getPlayer().getUniqueId()));
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		event.setCancelled(isInField(event.getPlayer().getUniqueId()));
	}
	
	@EventHandler
	public void onBlockInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		if (!isSword(event.getItem().getType()) || event.getItem().getType() != Material.WATCH)
			return;
		
		for (String name : plugin.fieldStorage.getFields().keySet())
		{
			Battlefield field = plugin.fieldStorage.getField(name);
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
		if (!plugin.statStorage.addPlayer(player.getUniqueId()))
			player.sendMessage(ChatColor.GREEN + plugin.prefix + " There was an error loading your statistics. Please contact an admin immediately.");
		
		File file = new File(plugin.getDataFolder() + File.separator + "InventoryStorage", player.getUniqueId().toString() + ".yml");
		if (!file.exists())
			return;
		
		player.sendMessage(ChatColor.GREEN + plugin.prefix + " You logged off with items still stored away. They will now be returned to you.");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		for (int slot = 0; slot < player.getInventory().getSize(); slot++)
			player.getInventory().setItem(slot, yml.getItemStack("inventory." + slot));
		
		ItemStack[] armor = new ItemStack[4];
		for (int slot = 0; slot < player.getInventory().getArmorContents().length; slot++)
			armor[slot] = yml.getItemStack("armor." + slot);
		
		player.getInventory().setArmorContents(armor);
		player.teleport(new Location(Bukkit.getWorld(yml.getString("world")), yml.getDouble("x"), yml.getDouble("y"), yml.getDouble("z")));
		file.delete();
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		for (String name : plugin.fieldStorage.getFields().keySet())
		{
			Battlefield field = plugin.fieldStorage.getField(name);
			if (field.getPlayer(player.getUniqueId()) != null)
				Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), player));
		}
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event)
	{
		for (String name : plugin.fieldStorage.getFields().keySet())
		{
			if (plugin.fieldStorage.getField(name).getPlayer(event.getPlayer().getUniqueId()) != null)
			{
				plugin.fieldStorage.getField(name).removePlayer(event.getPlayer());
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		for (String name : plugin.fieldStorage.getFields().keySet())
		{
			Battlefield field = plugin.fieldStorage.getField(name);
			if (field.getPlayer(player.getUniqueId()) != null)
			{
				Location loc = player.getLocation();
				double[] x = new double[2];
				x[0] = field.getPoint1().getX();
				x[1] = field.getPoint2().getX();
				Arrays.sort(x);
				double[] z = new double[2];
				z[0] = field.getPoint1().getZ();
				z[1] = field.getPoint2().getZ();
				Arrays.sort(z);
				if (loc.getX() < x[0] || loc.getX() > x[1] || loc.getZ() < z[0] || loc.getZ() > z[1])
				{
					player.sendMessage(ChatColor.RED + plugin.prefix + " Out of bounds!");
					event.setCancelled(true);
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
		for (String name : plugin.fieldStorage.getFields().keySet())
		{
			Battlefield field = plugin.fieldStorage.getField(name);
			if (field.getPlayer(player.getUniqueId()) != null)
			{
				PlayerFireEvent ev = new PlayerFireEvent(player, field.getPlayer(player.getUniqueId()));
				Bukkit.getPluginManager().callEvent(ev);
				event.setCancelled(ev.isCancelled());
				return;
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		if (!(event.getEntity() instanceof Player) && (!(event.getDamager() instanceof Arrow) || !(event.getDamager() instanceof Player || event.getCause() != DamageCause.BLOCK_EXPLOSION) || event.getCause() != DamageCause.FALL))
			return;
		
		UUID dmgd = event.getEntity().getUniqueId();
		for (String name : plugin.fieldStorage.getFields().keySet())
		{
			Battlefield field = plugin.fieldStorage.getField(name);
			if (field.getPlayer(dmgd) != null)
			{
				int damage = (int) event.getDamage() * 2;
				if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player && field.getPlayer(((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId()) != null)
				{
					Arrow arrow = (Arrow) event.getDamager();
					UUID dmgr = ((Player) arrow.getShooter()).getUniqueId();
					Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.PENETRATION, dmgd, dmgr, field, damage));
					ExplosionTracker.addArrow(arrow);
				}
				else if (event.getDamager() instanceof Player && field.getPlayer(event.getDamager().getUniqueId()) != null)
				{
					UUID dmgr = event.getDamager().getUniqueId();
					Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.RAM, dmgd, dmgr, field, damage));
				}
				else if (event.getCause() == DamageCause.ENTITY_EXPLOSION)
				{
					Arrow arrow = null;
					for (Arrow a : ExplosionTracker.getTracker())
						if (event.getDamager().getUniqueId() == a.getUniqueId())
							arrow = a;
					
					UUID dmgr = ((Arrow) arrow.getShooter()).getUniqueId();
					Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.SPLASH, dmgd, dmgr, field, damage));
					ExplosionTracker.removeArrow(arrow);
				}
				else if (event.getCause() == DamageCause.FALL)
					Bukkit.getPluginManager().callEvent(new PlayerTankDamageEvent(PlayerTankDamageCause.FALL, dmgd, field, damage));
				
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
			}
		}.runTaskLater(plugin, 1L);
	}
	
	@EventHandler
	public void onExplosion(EntityExplodeEvent event)
	{
		if (!(event.getEntity() instanceof Arrow))
			return;
		
		if (event.isCancelled())
			return;
		
		for (Block block : event.blockList())
		{
			for (String name : plugin.fieldStorage.getFields().keySet())
			{
				Battlefield field = plugin.fieldStorage.getField(name);
				Location loc = block.getLocation();
				double[] x = new double[2];
				x[0] = field.getPoint1().getX();
				x[1] = field.getPoint2().getX();
				Arrays.sort(x);
				double[] z = new double[2];
				z[0] = field.getPoint1().getZ();
				z[1] = field.getPoint2().getZ();
				Arrays.sort(z);
				if (loc.getX() < x[0] || loc.getX() > x[1] || loc.getZ() < z[0] || loc.getZ() > z[1])
					event.setCancelled(true);
			}
		}
	}
}
