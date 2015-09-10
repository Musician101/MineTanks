package musician101.minetanks.listener;

import java.util.Arrays;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.spigot.battlefield.Battlefield;
import musician101.minetanks.spigot.battlefield.player.PlayerTank;
import musician101.minetanks.event.AttemptMenuOpenEvent;
import musician101.minetanks.event.PlayerTankDamageEvent;
import musician101.minetanks.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.event.PlayerTankDeathEvent;
import musician101.minetanks.handler.DamageHandler;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.scoreboard.MTScoreboard;
import musician101.minetanks.spigot.util.MTUtils;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.event.Subscribe;

public class BattlefieldListener
{
	//TODO inventory handling not implemented
	@Subscribe
	public void onAttemptMenuOpen(AttemptMenuOpenEvent event)
	{
		Battlefield field = MineTanks.getFieldStorage().getField(event.getField());
		Player player = MineTanks.getGame().getServer().get().getPlayer(event.getPlayer()).get();
		PlayerTank pt = event.getPlayerTank();
		if (event.getItemType() == ItemTypes.CLOCK)
		{
			if (pt.isReady())
			{
				pt.setReady(false);
				player.getInventory().setItem(1, MTUtils.createCustomItem(ItemTypes.CLOCK, "Ready Up", Arrays.asList("You are currently not ready.")));
				return;
			}
			
			pt.setReady(true);
			player.getInventory().setItem(1, MTUtils.createCustomItem(ItemTypes.CLOCK, "Unready", Arrays.asList("You are currently ready.")));
			field.startMatch();
			return;
		}
		
		if (pt.isReady())
		{
			player.sendMessage(Messages.NEGATIVE_PREFIX + "You must unready to change your tank.");
			return;
		}
		
		MineTanks.openTankMenu(player);
		return;
	}
	
	@Subscribe
	public void onPlayerDeath(PlayerTankDeathEvent event)
	{
		Battlefield field = MineTanks.getFieldStorage().getField(event.getField());
		Player killed = event.getKilled();
		Player killer = event.getKiller();
		MTScoreboard sb = field.getScoreboard();
		String dmgdMsg = (sb.isOnGreen(killed) ? TextColors.GREEN + killed.getName() : TextColors.RED + killed.getName());
		String dmgrMsg = (sb.isOnGreen(killer) ? TextColors.GREEN + killer.getName() : TextColors.RED + killer.getName());
		for (Player player : MineTanks.getGame().getServer().get().getOnlinePlayers())
			if (field.getPlayer(player.getUniqueId()) != null)
				player.sendMessage(TextColors.GREEN + MineTanks.getPrefix() + TextColors.RESET + " " + dmgdMsg + TextColors.RESET + " was killed by " + dmgrMsg + TextColors.RESET + ".");
		
		killed.getInventory().clear();
		killed.getInventory().setHelmet(null);
		killed.getInventory().setChestplate(null);
		killed.getInventory().setLeggings(null);
		killed.getInventory().setBoots(null);
		field.playerKilled(killed.getUniqueId());
		field.endMatch();
	}
	
	@Subscribe
	public void onPlayerDamageEvent(PlayerTankDamageEvent event)
	{
		DamageHandler dh = new DamageHandler();
		Battlefield field = MineTanks.getFieldStorage().getField(event.getField());
		UUID dmgd = event.getDamagedPlayer();
		if (event.getCause() == PlayerTankDamageCause.FALL)
			dh.gravityHit(field, dmgd, event.getDamage());
		
		UUID dmgr = event.getDamager();
		int damage = event.getDamage();
		MTScoreboard sb = field.getScoreboard();
		if (sb.getPlayerHealth(dmgd) <= 0 || sb.getPlayerHealth(dmgr) <=0)
			return;
		
		if ((sb.isOnGreen(MineTanks.getGame().getServer().get().getPlayer(dmgr).get()) && sb.isOnGreen(MineTanks.getGame().getServer().get().getPlayer(dmgd).get())) || (sb.isOnRed(MineTanks.getGame().getServer().get().getPlayer(dmgr).get()) && sb.isOnRed(MineTanks.getGame().getServer().get().getPlayer(dmgd).get())))
		{
			if (event.getCause() == PlayerTankDamageCause.RAM)
				dh.meleeHitFriendly(field, dmgr, dmgd, damage);
			
			if (event.getCause() == PlayerTankDamageCause.SPLASH)
				dh.playerHitFriendly(field, dmgd, dmgr, damage);
			
			if (event.getCause() == PlayerTankDamageCause.PENETRATION)
				dh.playerHitFriendly(field, dmgd, dmgr, damage);
			
			return;
		}
		
		if (event.getCause() == PlayerTankDamageCause.RAM)
			dh.meleeHitEnemy(field, dmgd, dmgr, damage);
		
		if (event.getCause() == PlayerTankDamageCause.SPLASH)
			dh.playerHitEnemy(field, dmgd, dmgr, damage);
		
		if (event.getCause() == PlayerTankDamageCause.PENETRATION)
			dh.playerHitEnemy(field, dmgd, dmgr, damage);
	}
}
