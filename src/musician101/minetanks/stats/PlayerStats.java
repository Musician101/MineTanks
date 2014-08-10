package musician101.minetanks.stats;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.tankinfo.tanks.ITank;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerStats
{
	MineTanks plugin;
	UUID player;
	List<ITank> unlockedTanks;
	int xp;
	int money;
	int balancer = 20;
	
	public PlayerStats(MineTanks plugin, UUID player, List<ITank> unlockedTanks, int xp, int money)
	{
		this.plugin = plugin;
		this.player = player;
		this.unlockedTanks = unlockedTanks;
		this.xp = xp;
		this.money = money;
	}
	
	public void saveToFile()
	{
		File file = new File(plugin.getDataFolder() + File.separator + "playerstats", player.toString() + ".yml");
		try
		{
			file.createNewFile();
		}
		catch (IOException e)
		{
			plugin.getLogger().warning("Error: Failed to create file: " + file.getName());
			return;
		}
		
		YamlConfiguration stats = YamlConfiguration.loadConfiguration(file);
		stats.set("xp", xp);
		stats.set("money", money);
		List<String> tanks = new ArrayList<String>();
		for (ITank tank : unlockedTanks)
			tanks.add(tank.getName());
		
		stats.set("tanks", tanks);
		
		try
		{
			stats.save(file);
		}
		catch (IOException e)
		{
			plugin.getLogger().warning("Error: Could not save " + file.getName());
		}
	}
	
	public UUID getPlayer()
	{
		return player;
	}
	
	public List<ITank> getTanks()
	{
		return unlockedTanks;
	}
	
	public int getXp()
	{
		return xp;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public void addMoneyFromHit(int damage)
	{
		money = money + (damage * balancer);
	}
	
	public void addXpFromHit(PlayerTank dmgd, PlayerTank dmgr, int damage)
	{
		int dmgModifier = Math.abs(dmgr.getTank().getLevel() - dmgd.getTank().getLevel());
		xp = xp + (damage * balancer * dmgModifier);
	}
	
	public void addXpOnKill(PlayerTank dmgd, PlayerTank dmgr)
	{
		xp = xp + (5 * Math.abs(dmgr.getTank().getLevel() - dmgd.getTank().getLevel()));
	}
	
	public void subtractMoneyFromHit(int damage)
	{
		money = money - 4 * (damage * balancer);
	}
	
	public void subtractXpFromHit(PlayerTank dmgd, PlayerTank dmgr, int damage)
	{
		int dmgModifier = Math.abs(dmgr.getTank().getLevel() - dmgd.getTank().getLevel());
		xp = xp - 2 * (damage * balancer * dmgModifier);
	}
}
