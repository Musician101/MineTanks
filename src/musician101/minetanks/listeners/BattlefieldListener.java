package musician101.minetanks.listeners;

import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.events.AttemptMenuOpenEvent;
import musician101.minetanks.events.PlayerFireEvent;
import musician101.minetanks.events.PlayerTankDamageEvent;
import musician101.minetanks.events.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.events.PlayerTankDeathEvent;
import musician101.minetanks.handlers.DamageHandler;
import musician101.minetanks.handlers.ReloadHandler;
import musician101.minetanks.menu.Menus;
import musician101.minetanks.scoreboards.MTScoreboard;
import musician101.minetanks.util.MTUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BattlefieldListener implements Listener
{
	MineTanks plugin;
	
	public BattlefieldListener(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onAttemptMenuOpen(AttemptMenuOpenEvent event)
	{
		Battlefield field = plugin.getFieldStorage().getField(event.getField());
		Player player = Bukkit.getPlayer(event.getPlayer());
		PlayerTank pt = event.getPlayerTank();
		if (event.getMaterial () == Material.WATCH)
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
			return;
		}
		
		if (pt.isReady())
		{
			player.sendMessage(ChatColor.RED + plugin.getPrefix() + " You must unready to change your tank.");
			return;
		}
		
		Menus.countrySelection.open(player);
		return;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerTankDeathEvent event)
	{
		Battlefield field = plugin.getFieldStorage().getField(event.getField());
		Player player = event.getPlayer();
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		field.playerKilled(player.getUniqueId());
		field.endMatch();
	}
	
	@EventHandler
	public void onPlayerFire(PlayerFireEvent event)
	{
		int reloadTime = ((Double) event.getPlayerTank().getTank().reloadTime()).intValue();
		ReloadHandler reload = new ReloadHandler(plugin, event.getPlayer(), reloadTime);
		event.setCancelled(reload.isReloading());
		if (!event.isCancelled())
		{
			Inventory inv = event.getPlayer().getInventory();
			for (ItemStack item : inv.getContents())
			{
				if (item.getType() == Material.ARROW)
				{
					item.setAmount(item.getAmount() - 1);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamageEvent(PlayerTankDamageEvent event)
	{
		DamageHandler dh = new DamageHandler(plugin);
		Battlefield field = plugin.getFieldStorage().getField(event.getField());
		UUID dmgd = event.getDamagedPlayer();
		if (event.getCause() == PlayerTankDamageCause.FALL)
			dh.gravityHit(field, dmgd, event.getDamage());
		
		UUID dmgr = event.getDamager();
		int damage = event.getDamage();
		MTScoreboard sb = field.getScoreboard();
		if ((sb.isOnGreen(Bukkit.getPlayer(dmgr)) && sb.isOnGreen(Bukkit.getPlayer(dmgd))) || (sb.isOnRed(Bukkit.getPlayer(dmgr)) && sb.isOnRed(Bukkit.getPlayer(dmgd))))
		{
			if (event.getCause() == PlayerTankDamageCause.RAM)
				dh.meleeHitFriendly(field, dmgr, dmgd);
			
			if (event.getCause() == PlayerTankDamageCause.SPLASH)
				dh.playerHitFriendly(field, dmgd, dmgr, damage);
			
			if (event.getCause() == PlayerTankDamageCause.PENETRATION)
				dh.playerHitFriendly(field, dmgd, dmgr, damage);
			
			return;
		}
		
		if (event.getCause() == PlayerTankDamageCause.RAM)
			dh.meleeHitEnemy(field, dmgd, dmgr);
		
		if (event.getCause() == PlayerTankDamageCause.SPLASH)
			dh.playerHitEnemy(field, dmgd, dmgr, damage);
		
		if (event.getCause() == PlayerTankDamageCause.PENETRATION)
			dh.playerHitEnemy(field, dmgd, dmgr, damage);
	}
}
