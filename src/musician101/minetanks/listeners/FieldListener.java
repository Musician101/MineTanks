package musician101.minetanks.listeners;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.BattleField;
import musician101.minetanks.battlefield.PlayerTank;
import musician101.minetanks.menu.Menus;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class FieldListener implements Listener
{
	MineTanks plugin;
	
	public FieldListener(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	private boolean isInField(UUID playerId)
	{
		for (BattleField field : plugin.fieldStorage.getFields())
			if (field.getPlayer(playerId) != null)
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
		if (!isInField(event.getPlayer().getUniqueId()))
			return;
			
		if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		if (!isSword(event.getItem().getType()) || event.getItem().getType() != Material.WATCH)
			return;
		
		if (isSword(event.getItem().getType()))
		{
			Menus.countrySelection.open(event.getPlayer());
			return;
		}
		
		if (event.getItem().getType() == Material.WATCH)
		{
			for (BattleField field : plugin.fieldStorage.getFields())
			{
				PlayerTank pt = field.getPlayer(event.getPlayer().getUniqueId());
				if (pt != null)
				{
					pt.setReady(true);
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
		file.delete();
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		for (BattleField field : plugin.fieldStorage.getFields())
		{
			PlayerTank pt = field.getPlayer(player.getUniqueId());
			if (pt != null)
			{
				field.playerKilled(pt);
				field.endMatch();
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event)
	{
		for (BattleField field : plugin.fieldStorage.getFields())
		{
			PlayerTank pt = field.getPlayer(event.getPlayer().getUniqueId());
			if (pt != null)
			{
				field.removePlayer(event.getPlayer());
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if (!isInField(event.getPlayer().getUniqueId()))
			return;
		
		Player player = event.getPlayer();
		for (BattleField field : plugin.fieldStorage.getFields())
		{
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
}
