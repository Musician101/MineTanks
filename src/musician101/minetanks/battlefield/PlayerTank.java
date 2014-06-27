package musician101.minetanks.battlefield;

import java.util.UUID;

import musician101.minetanks.tankinfo.tanks.ITank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerTank
{
	private UUID player;
	private ITank tank;
	private boolean isReady;
	private MTTeam team;
	
	public PlayerTank(UUID player, MTTeam team)
	{
		this.team = team;
		this.player = player;
		this.isReady = true;
	}
	
	public UUID getPlayerId()
	{
		return player;
	}
	
	public ITank getTank()
	{
		return tank;
	}
	
	public MTTeam getTeam()
	{
		return team;
	}
	
	public void setTank(ITank tank)
	{
		this.tank = tank;
	}
	
	public void setTeam(MTTeam team)
	{
		this.team = team;
	}
	
	public boolean isReady()
	{
		return isReady;
	}
	
	public void setReady(boolean isReady)
	{
		this.isReady = isReady;
	}
	
	public void killed()
	{
		Player player = Bukkit.getPlayer(this.player);
		for (int slot = 0; slot < player.getInventory().getSize(); slot++)
			player.getInventory().setItem(0, new ItemStack(Material.AIR));
		
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		this.team = MTTeam.SPECTATOR;
		this.tank = null;
	}
	
	public enum MTTeam
	{
		ASSIGNED(false),
		SPECTATOR(),
		UNASSIGNED();
		
		private boolean canExit;
		
		private MTTeam()
		{
			this(true);
		}
		
		private MTTeam(boolean canExit)
		{
			this.canExit = canExit;
		}
		
		public boolean canExit()
		{
			return canExit;
		}
	}
}
