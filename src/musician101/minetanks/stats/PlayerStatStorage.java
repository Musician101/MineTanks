package musician101.minetanks.stats;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.tankinfo.tanks.ChinaHeavy;
import musician101.minetanks.tankinfo.tanks.ChinaLight;
import musician101.minetanks.tankinfo.tanks.ChinaMedium;
import musician101.minetanks.tankinfo.tanks.ITank;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerStatStorage
{
	MineTanks plugin;
	Map<UUID, PlayerStats> stats = new HashMap<UUID, PlayerStats>();
	
	public PlayerStatStorage(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean addPlayer(UUID player)
	{
		return addPlayer(player, new ArrayList<ITank>(), 0, 0);
	}
	
	public boolean addPlayer(UUID player, List<ITank> unlockedTanks, int exp, int money)
	{
		if (stats.containsKey(player))
			return false;
		
		stats.put(player, new PlayerStats(plugin, player, unlockedTanks, exp, money));
		return true;
	}
	
	public boolean resetPlayer(UUID player)
	{
		if (stats.remove(player) == null)
			return false;
		
		return addPlayer(player);
	}
	
	public PlayerStats getPlayer(UUID player)
	{
		return stats.get(player);
	}
	
	public void loadFromFiles()
	{
		File files = new File(plugin.getDataFolder() + File.separator + "playerstats");
		for (File file : files.listFiles())
		{
			if (file.getName().endsWith(".yml"))
			{
				YamlConfiguration stat = YamlConfiguration.loadConfiguration(file);
				UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));
				int xp = stat.getInt("xp");
				int money = stat.getInt("money");
				List<ITank> unlockedTanks = new ArrayList<ITank>();
				for (String tank : stat.getStringList("tanks"))
				{
					for (ChinaLight cl : ChinaLight.values())
						if (cl.equals(tank))
							unlockedTanks.add(cl);
					
					for (ChinaMedium cm : ChinaMedium.values())
						if (cm.equals(tank))
							unlockedTanks.add(cm);
					
					for (ChinaHeavy ch : ChinaHeavy.values())
						if (ch.equals(tank))
							unlockedTanks.add(ch);
					
					//TODO don't forget the other tanks :D
				}
				
				if (!addPlayer(uuid, unlockedTanks, xp, money))
					plugin.getLogger().warning("Failed to load " + file.getName());
			}
		}
	}
	
	public void saveToFiles()
	{
		for (Entry<UUID, PlayerStats> entry : stats.entrySet())
			entry.getValue().saveToFile();
	}
	
	public Map<UUID, PlayerStats> getStats()
	{
		return stats;
	}
}
