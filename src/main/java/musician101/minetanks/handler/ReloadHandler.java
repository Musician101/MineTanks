package musician101.minetanks.handler;

import java.util.Arrays;

import org.spongepowered.api.entity.Player;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.tank.module.Cannon.CannonTypes;

public class ReloadHandler
{
	Player player;
	CannonTypes type;
	int reloadTime, cycleTime, clipSize, maxClipSize;
	
	public ReloadHandler(Player player, CannonTypes type, int reloadTime, int cycleTime, int clipSize, int maxClipSize)
	{
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
			//TODO convert to Sponge Task
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
							
							for (String name : MineTanks.getFieldStorage().getFields().keySet())
							{
								Battlefield field = MineTanks.getFieldStorage().getField(name);
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
