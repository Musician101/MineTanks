package musician101.minetanks.listeners;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.handlers.ReloadHandler;
import musician101.minetanks.menu.Menus;
import musician101.minetanks.tankinfo.TankTypes;
import musician101.minetanks.util.MTUtils;

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

public class FieldListener implements Listener
{
	MineTanks plugin;
	UUID uuid;
	
	public FieldListener(MineTanks plugin)
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
		
		if (isSword(event.getItem().getType()))
		{
			for (String name : plugin.fieldStorage.getFields().keySet())
			{
				PlayerTank pt = plugin.fieldStorage.getField(name).getPlayer(player.getUniqueId());
				if (pt != null)
				{
					if (pt.isReady())
					{
						player.sendMessage(ChatColor.RED + plugin.prefix + " You must unready to change your tank.");
						return;
					}
					
					Menus.countrySelection.open(event.getPlayer());
					return;
				}
			}
		}
		
		if (event.getItem().getType() == Material.WATCH)
		{
			for (String name : plugin.fieldStorage.getFields().keySet())
			{
				Battlefield field = plugin.fieldStorage.getField(name);
				PlayerTank pt = field.getPlayer(player.getUniqueId());
				if (pt != null)
				{
					if (pt.isReady())
					{
						pt.setReady(false);
						player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Ready Up", "You are currently not ready."));
						return;
					}
					
					pt.setReady(true);
					player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Unready", "You are currently ready."));
					field.startMatch();
				}
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
			{
				player.getInventory().clear();
				player.getInventory().setHelmet(null);
				player.getInventory().setChestplate(null);
				player.getInventory().setLeggings(null);
				player.getInventory().setBoots(null);
				field.playerKilled(player.getUniqueId());
				field.endMatch();
				return;
			}
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
		
		final Player player = (Player) event.getEntity();
		for (String name : plugin.fieldStorage.getFields().keySet())
		{
			PlayerTank pt = plugin.fieldStorage.getField(name).getPlayer(player.getUniqueId());
			if (pt != null)
			{	
				int reloadTime = ((Double) pt.getTank().reloadTime()).intValue();
				ReloadHandler reload = new ReloadHandler(plugin, player, reloadTime);
				event.setCancelled(reload.reload());
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{	
		if (!(event.getEntity() instanceof Player))
			return;
		
		Player dmgd = (Player) event.getEntity();
		if (event.getCause() == DamageCause.BLOCK_EXPLOSION)
		{
			for (String name : plugin.fieldStorage.getFields().keySet())
			{
				Battlefield field = plugin.fieldStorage.getField(name);
				if (field.getPlayer(dmgd.getUniqueId()) != null && field.getPlayer(uuid) != null)
				{
					PlayerTank ptdd = field.getPlayer(dmgd.getUniqueId());
					PlayerTank ptdr = field.getPlayer(uuid);
					MTUtils.playerHit(plugin, field, dmgd.getUniqueId(), ptdd, uuid, ptdr, (int) (event.getDamage() * 2));
					event.setCancelled(true);
					return;
				}
			}
		}
		
		if (event.getCause() == DamageCause.ENTITY_ATTACK)
		{
			if (!(event.getDamager() instanceof Player))
				return;
			
			for (String name : plugin.fieldStorage.getFields().keySet())
			{
				Battlefield field = plugin.fieldStorage.getField(name);
				Player dmgr = (Player) event.getDamager();
				if (field.getPlayer(dmgd.getUniqueId()) != null && field.getPlayer(dmgr.getUniqueId()) != null)
				{
					PlayerTank ptdd = field.getPlayer(dmgd.getUniqueId());
					PlayerTank ptdr = field.getPlayer(dmgr.getUniqueId());
					MTUtils.meleeHit(plugin, field, dmgr.getUniqueId(), ptdr, dmgd.getUniqueId(), ptdd);
					event.setCancelled(true);
					return;
				}
			}
		}
		
		if (event.getCause() == DamageCause.FALL)
		{
			for (String name : plugin.fieldStorage.getFields().keySet())
			{
				Battlefield field = plugin.fieldStorage.getField(name);
				if (field.getPlayer(dmgd.getUniqueId()) != null)
				{
					PlayerTank pt = field.getPlayer(dmgd.getUniqueId());
					MTUtils.gravityHit(plugin, field, dmgd.getUniqueId(), pt, (int) (event.getDamage() * 2));
					event.setCancelled(true);
					return;
				}
			}
		}
		
		if (event.getCause() == DamageCause.PROJECTILE)
		{
			if (!(event.getDamager() instanceof Arrow))
				return;
			
			Arrow arrow = (Arrow) event.getDamager();
			if (!(arrow.getShooter() instanceof Player))
				return;
			
			Player dmgr = (Player) arrow.getShooter();
			for (String name : plugin.fieldStorage.getFields().keySet())
			{
				Battlefield field = plugin.fieldStorage.getField(name);
				if (field.getPlayer(dmgd.getUniqueId()) != null && field.getPlayer(dmgr.getUniqueId()) != null)
				{
					PlayerTank ptdd = field.getPlayer(dmgd.getUniqueId());
					PlayerTank ptdr = field.getPlayer(dmgr.getUniqueId());
					MTUtils.playerHit(plugin, field, dmgd.getUniqueId(), ptdd, dmgr.getUniqueId(), ptdr, (int) (event.getDamage() * 2));
					if (ptdr.getTank().getType() == TankTypes.ARTY)
						MTUtils.ammoExplosion(plugin, dmgd.getLocation(), ptdr.getTank().getLevel(), true);
					
					event.setCancelled(true);
					return;
				}
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
				event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 1F);
				if (!(event.getEntity().getShooter() instanceof Player))
					return;
				
				Player player = (Player) event.getEntity().getShooter();
				uuid = player.getUniqueId();
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
