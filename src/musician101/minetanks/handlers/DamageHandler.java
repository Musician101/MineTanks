package musician101.minetanks.handlers;

import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.events.PlayerTankDeathEvent;
import musician101.minetanks.scoreboards.MTScoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DamageHandler
{
	MineTanks plugin;
	
	public DamageHandler(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	public void meleeHitEnemy(Battlefield field, UUID rammed, UUID rammer, int damage)
	{
		double rammerDmg = damage * 20;
		double rammedDmg = damage * 15;
		if (rammerDmg > 0)
			playerHitEnemy(field, rammed, rammer, (int) rammerDmg);
		
		if (rammedDmg > 0)
			playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
	}
	
	public void playerHitEnemy(Battlefield field, UUID dmgd, UUID dmgr, int damage)
	{
		MTScoreboard sb = field.getScoreboard();
		sb.setPlayerHealth(dmgd, sb.getPlayerHealth(dmgd) - ((int) (damage * 2) * 20));
		if (sb.getPlayerHealth(dmgd) <= 0)
			Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(dmgd), Bukkit.getPlayer(dmgr)));
	}
	
	public void gravityHit(Battlefield field, UUID player, int damage)
	{
		PlayerTank pt = field.getPlayer(player);
		double dmg = 0.5 * pt.getTank().getWeight() * (pt.getTank().getSpeedEffect().getAmplifier()^2);
		MTScoreboard sb = field.getScoreboard();
		sb.setPlayerHealth(player, sb.getPlayerHealth(player) - (int) dmg);
	}
	
	public void meleeHitFriendly(Battlefield field, UUID rammed, UUID rammer, int damage)
	{
		double rammerDmg = damage * 20;
		double rammedDmg = damage * 15;
		if (rammerDmg > 0)
			playerHitEnemy(field, rammed, rammer, (int) rammerDmg);
		
		if (rammedDmg > 0)
			playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
	}
	
	public void playerHitFriendly(Battlefield field, UUID dmgd, UUID dmgr, int damage)
	{
		MTScoreboard sb = field.getScoreboard();
		sb.setPlayerHealth(dmgd, sb.getPlayerHealth(dmgd) - ((int) (damage * 2) * 20));
		if (sb.getPlayerHealth(dmgd) <= 0)
		{
			Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(dmgd), Bukkit.getPlayer(dmgr)));
			for (Player player : Bukkit.getOnlinePlayers())
			{
				if (field.getPlayer(player.getUniqueId()) != null)
				{
					String dmgdMsg = (sb.isOnGreen(Bukkit.getPlayer(dmgd)) ? ChatColor.GREEN + Bukkit.getPlayer(dmgd).getName() : ChatColor.RED + Bukkit.getPlayer(dmgd).getName());
					String dmgrMsg = (sb.isOnGreen(Bukkit.getPlayer(dmgr)) ? ChatColor.GREEN + Bukkit.getPlayer(dmgr).getName() : ChatColor.RED + Bukkit.getPlayer(dmgr).getName());
					player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + ChatColor.RESET + " " + dmgdMsg + ChatColor.RESET + " was killed by " + dmgrMsg + ChatColor.RESET + ".");
				}
			}
		}
	}
}
