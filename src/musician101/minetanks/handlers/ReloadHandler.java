package musician101.minetanks.handlers;

import java.util.Arrays;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.tankinfo.modules.Cannons.CannonTypes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ReloadHandler
{
	MineTanks plugin;
	Player player;
	CannonTypes type;
	int reloadTime, cycleTime, clipSize, maxClipSize;
	
	public ReloadHandler(MineTanks plugin, Player player, CannonTypes type, int reloadTime, int cycleTime, int clipSize, int maxClipSize)
	{
		this.plugin = plugin;
		this.player = player;
		this.type = type;
		this.reloadTime = reloadTime;
		this.cycleTime = cycleTime;
		this.clipSize = clipSize;
		this.maxClipSize = maxClipSize;
	}
	
	public boolean isReloading()
	{
		if (player.getLevel() > 0)
			return true;
		
		int time = 0;
		if (clipSize == 0)
			time = reloadTime;
		else
			time = cycleTime;
		
		player.setLevel(time);
		try
		{
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					player.setLevel(player.getLevel() - 1);
					if (player.getLevel() == 0)
					{
						if (type == CannonTypes.AUTO_LOADER && clipSize == 0)
						{
							for (ItemStack item : player.getInventory().getContents())
							{
								if (item != null && item.getType() == Material.BOW)
								{
									ItemMeta meta = item.getItemMeta();
									meta.setLore(Arrays.asList("Your Cannon", "Clip Size: " + maxClipSize + "/" + maxClipSize,"Cycle Time: " + cycleTime, "Clip Reload Time: " + reloadTime));
									item.setItemMeta(meta);
								}
							}
							
							for (String name : plugin.getFieldStorage().getFields().keySet())
							{
								Battlefield field = plugin.getFieldStorage().getField(name);
								if (field.getPlayer(player.getUniqueId()) != null)
								{
									PlayerTank pt = field.getPlayer(player.getUniqueId());
									pt.setClipSize(pt.getTank().getClipSize());
								}
							}
						}
						cancel();
					}
				}
			}.runTaskTimer(plugin, 1L, time);
		}
		catch (NullPointerException e){}
		return false;
	}
}
