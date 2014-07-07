package musician101.minetanks.handlers;

import musician101.minetanks.MineTanks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ReloadHandler
{
	MineTanks plugin;
	Player player;
	int reloadTime;
	
	public ReloadHandler(MineTanks plugin, Player player, int reloadTime)
	{
		this.plugin = plugin;
		this.player = player;
		this.reloadTime = reloadTime;
	}
	
	public boolean reload()
	{
		if (player.getLevel() != 0)
			return true;
		
		player.setLevel(reloadTime);
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				player.setLevel(player.getLevel() - 1);
				if (player.getLevel() == 0)
					cancel();
			}
		}.runTaskTimer(plugin, 1L, reloadTime);
		return false;
	}
}
