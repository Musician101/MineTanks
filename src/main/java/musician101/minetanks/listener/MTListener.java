package musician101.minetanks.listener;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.battlefield.player.PlayerTank.MTTeam;
import musician101.minetanks.event.AttemptMenuOpenEvent;
import musician101.minetanks.event.PlayerTankDamageEvent;
import musician101.minetanks.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.handler.ExplosionTracker;
import musician101.minetanks.handler.ReloadHandler;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.tank.Tanks;
import musician101.minetanks.tank.module.Cannon.CannonTypes;
import musician101.minetanks.util.MTUtils;
import musician101.minetanks.util.Region;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mockito.cglib.core.Block;
import org.spongepowered.api.entity.EntityInteractionType;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.projectile.Arrow;
import org.spongepowered.api.event.block.BlockBreakEvent;
import org.spongepowered.api.event.block.BlockPlaceEvent;
import org.spongepowered.api.event.entity.ProjectileLaunchEvent;
import org.spongepowered.api.event.player.PlayerDropItemEvent;
import org.spongepowered.api.event.player.PlayerInteractEvent;
import org.spongepowered.api.event.player.PlayerJoinEvent;
import org.spongepowered.api.event.player.PlayerMoveEvent;
import org.spongepowered.api.event.player.PlayerQuitEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.event.Subscribe;
import org.spongepowered.api.world.Location;

public class MTListener
{
	private boolean isInField(UUID playerId)
	{
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
			if (MineTanks.getFieldStorage().getField(name).getPlayer(playerId) != null)
				return true;
		
		return false;
	}
	
	private boolean isSword(ItemType type)
	{
		//It's technically a sword without a blade.
		//Stick is the default item if a player hasn't chosen a tank.
		if (type == ItemTypes.STICK)
			return true;
		
		if (type == ItemTypes.WOODEN_SWORD)
			return true;
		
		if (type == ItemTypes.STONE_SWORD)
			return true;
		
		if (type == ItemTypes.IRON_SWORD)
			return true;
		
		if (type == ItemTypes.GOLDEN_SWORD)
			return true;
		
		if (type == ItemTypes.DIAMOND_SWORD)
			return true;
		
		return false;
	}
	
	@Subscribe
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event.isCancelled())
			return;
		
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
		{
			Battlefield field = MineTanks.getFieldStorage().getField(name);
			double x = event.getBlock().getX();
			double y = event.getBlock().getY();
			double z = event.getBlock().getZ();
			double minX = field.getRegion().getMinX();
			double maxX = field.getRegion().getMaxX();
			double minY = field.getRegion().getMinY();
			double maxY = field.getRegion().getMaxY();
			double minZ = field.getRegion().getMinZ();
			double maxZ = field.getRegion().getMaxZ();
			if ((x >= minX && x <= maxX) && (y >= minY && y <= maxY) && (z >= minZ && z <= maxZ))
			{
				if (event.getPlayer().hasPermission("minetanks.edit") && !field.isEnabled())
					return;
				
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@Subscribe
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (event.isCancelled())
			return;
		
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
		{
			Battlefield field = MineTanks.getFieldStorage().getField(name);
			double x = event.getBlock().getX();
			double y = event.getBlock().getY();
			double z = event.getBlock().getZ();
			double minX = field.getRegion().getMinX();
			double maxX = field.getRegion().getMaxX();
			double minY = field.getRegion().getMinY();
			double maxY = field.getRegion().getMaxY();
			double minZ = field.getRegion().getMinZ();
			double maxZ = field.getRegion().getMaxZ();
			if ((x >= minX && x <= maxX) && (y >= minY && y <= maxY) && (z >= minZ && z <= maxZ))
			{
				if (event.getPlayer().hasPermission("minetanks.edit") && !field.isEnabled())
					return;
				
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@Subscribe
	public void onBlockInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemType type = (player.getItemInHand().isPresent() ? player.getItemInHand().get().getItem() : null);
		if (type == null || type == ItemTypes.BOW)
			return;
		
		if (event.getInteractionType() != EntityInteractionType.RIGHT_CLICK)
			return;
		
		if (!isSword(type) && type != ItemTypes.CLOCK)
			return;
		
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
		{
			Battlefield field = MineTanks.getFieldStorage().getField(name);
			if (field.inProgress())
				return;
			
			if (field.getPlayer(event.getPlayer().getUniqueId()) != null)
				MineTanks.getGame().getEventManager().post(new AttemptMenuOpenEvent(type, field.getName(), field.getPlayer(player.getUniqueId()), player.getUniqueId()));
		}
	}
	
	@Subscribe
	public void onItemDrop(PlayerDropItemEvent event)
	{
		event.setCancelled(isInField(event.getPlayer().getUniqueId()));
	}
	
	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		File file = new File(MineTanks.inventoryStorage, player.getUniqueId().toString() + ".yml");
		if (!file.exists())
			return;
		
		player.sendMessage(Messages.POSITIVE_PREFIX + "You logged off with items still stored away. They will now be returned to you.");
		//TODO need to learn how to mess with ConfigFile
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(new FileReader(file));
		for (int slot = 0; slot < player.getInventory().getSize(); slot++)
			player.getInventory().setItem(slot, json.getItemStack("inventory." + slot));
		
		ItemStack[] armor = new ItemStack[4];
		for (int slot = 0; slot < player.getInventory().getArmorContents().length; slot++)
			armor[slot] = json.getItemStack("armor." + slot);
		
		player.getInventory().setArmorContents(armor);
		player.teleport(MTUtils.deserializeLocation(json));
		file.delete();
	}
		
	@Subscribe
	public void onPlayerDisconnect(PlayerQuitEvent event)
	{
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
		{
			if (MineTanks.getFieldStorage().getField(name).getPlayer(event.getPlayer().getUniqueId()) != null)
			{
				MineTanks.getFieldStorage().getField(name).removePlayer(event.getPlayer());
				return;
			}
		}
	}
	
	@Subscribe
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
		{
			Battlefield field = MineTanks.getFieldStorage().getField(name);
			if (field.getPlayer(player.getUniqueId()) != null)
			{
				PlayerTank pt = field.getPlayer(player.getUniqueId());
				if (pt.getTeam() == MTTeam.SPECTATOR)
					return;
				
				Region region = field.getRegion();
				double minX = region.getMinX();
				double maxX = region.getMaxX();
				double minZ = region.getMinZ();
				double maxZ = region.getMaxZ();
				double x = player.getLocation().getPosition().getX();
				double z = player.getLocation().getPosition().getZ();
				double correction = 2.0;
				if (!region.isInRegion(player.getLocation()))
				{
					if (x <= minX)
						x = minX + correction;
					else if (x >= maxX)
						x = maxX - correction;
					if (z <= minZ)
						z = minZ + correction;
					else if (z >= maxZ)
						z = maxZ - correction;
					
					player.sendMessage(Messages.NEGATIVE_PREFIX + "Out of bounds!");
					player.teleport(x, player.getLocation().getPosition().getY(), z, player.getWorld());
					return;
				}
			}
		}
	}
	
	//TODO not implemented
	@Subscribe
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		if (event.getCause() == TeleportCause.COMMAND)
			event.setCancelled(isInField(event.getPlayer().getUniqueId()));
	}
	
	@Subscribe
	public void onBowShoot(ProjectileLaunchEvent event)
	{
		if (!(event.getEntity() instanceof Player))
			return;
		
		Player player = (Player) event.getEntity();
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
		{
			Battlefield field = MineTanks.getFieldStorage().getField(name);
			if (field.getPlayer(player.getUniqueId()) != null)
			{
				PlayerTank pt = field.getPlayer(player.getUniqueId());
				if (pt.getTeam() == MTTeam.SPECTATOR)
					return;
				
				Tanks tank = pt.getTank();
				pt.setClipSize(pt.getClipSize() - 1);
				ReloadHandler reload = new ReloadHandler(player, tank.getCannonType(), tank.reloadTime(), tank.cycleTime(), pt.getClipSize(), tank.getClipSize());
				//TODO does not extend Cancellable
				event.setCancelled(reload.isReloading());
				if (!event.isCancelled())
				{
					//TODO inventory api in the works
					Inventory inv = player.getInventory();
					for (ItemStack item : inv.getContents())
					{
						if (item == null)
							continue;
						if (item.getItem() == ItemTypes.ARROW)
							item.setQuantity(item.getQuantity() - 1);
						else if (item.getItem() == ItemTypes.BOW && tank.getCannonType() == CannonTypes.AUTO_LOADER)
						{
							//TODO NBT api perhaps?
							ItemMeta meta = item.getItemMeta();
							meta.setLore(Arrays.asList("Your Cannon", "Clip Size: " + pt.getClipSize() + "/" + tank.getClipSize(),"Cycle Time: " + tank.cycleTime(), "Clip Reload Time: " + tank.reloadTime()));
							item.setItemMeta(meta);
						}
					}
				}
				return;
			}
		}
	}
	
	//TODO not implemented
	@Subscribe
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		if (!(event.getEntity() instanceof Player) && (!(event.getDamager() instanceof Arrow) || !(event.getDamager() instanceof Player || event.getCause() != DamageCause.BLOCK_EXPLOSION) || event.getCause() != DamageCause.FALL))
			return;
		
		UUID dmgd = event.getEntity().getUniqueId();
		for (String name : MineTanks.getFieldStorage().getFields().keySet())
		{
			Battlefield field = MineTanks.getFieldStorage().getField(name);
			if (field.getPlayer(dmgd) != null)
			{
				int damage = (int) event.getDamage() * 2;
				if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player && field.getPlayer(((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId()) != null)
				{
					Arrow arrow = (Arrow) event.getDamager();
					UUID dmgr = ((Player) arrow.getShooter()).getUniqueId();
					MineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.PENETRATION, dmgd, dmgr, field, damage));
					ExplosionTracker.addArrow(arrow);
				}
				else if (event.getDamager() instanceof Player && field.getPlayer(event.getDamager().getUniqueId()) != null)
				{
					UUID dmgr = event.getDamager().getUniqueId();
					MineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.RAM, dmgd, dmgr, field, damage));
				}
				else if (event.getCause() == DamageCause.ENTITY_EXPLOSION)
				{
					Arrow arrow = null;
					for (Arrow a : ExplosionTracker.getTracker())
						if (event.getDamager().getUniqueId() == a.getUniqueId())
							arrow = a;
					
					UUID dmgr = ((Arrow) arrow.getShooter()).getUniqueId();
					MineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.SPLASH, dmgd, dmgr, field, damage));
					ExplosionTracker.removeArrow(arrow);
				}
				else if (event.getCause() == DamageCause.FALL)
					MineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.FALL, dmgd, field, damage));
				
				event.setCancelled(true);
				return;
			}
		}
	}
	
	//TODO not implemented
	@Subscribe
	public void onArrowHitBlock(final ProjectileHitEvent event)
	{
		//TODO convert to Sponge Task
		MineTanks.getGame().getScheduler().runTaskAfter(MineTanks.getPluginContainer(),
				new Runnable()
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
				}, 1L);
	}
	
	//TODO not implemented
	@Subscribe
	public void onExplosion(EntityExplodeEvent event)
	{
		if (event.isCancelled())
			return;
		
		for (Block block : event.blockList())
		{
			for (String name : MineTanks.getFieldStorage().getFields().keySet())
			{
				for (Block b : MineTanks.getFieldStorage().getField(name).getRegion().getBlocks())
				{
					if (block.getLocation() == b.getLocation())
					{
						event.blockList().clear();
						return;
					}
				}
			}
		}
	}
}
