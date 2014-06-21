package musician101.minetanks.battlefield;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.PlayerTank.Team;
import musician101.minetanks.util.MTUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BattleField
{
	private MineTanks plugin;
	private String name;
	private Location p1, p2, greenSpawn, redSpawn, spectators;
	private List<PlayerTank> players = new ArrayList<PlayerTank>();
	private boolean enabled;
	private int redTeam = 0;
	private int greenTeam = 0;
	private int unassigned = 0;
	private boolean inProgress = false;
	String worldName;
	
	public BattleField(MineTanks plugin, String name, boolean enabled, Location p1, Location p2, Location p3, Location greenSpawn, Location redSpawn, Location spectators)
	{
		this.plugin = plugin;
		this.name = name;
		this.enabled = enabled;
		this.p1 = p1;
		this.p2 = p2;
		this.greenSpawn = greenSpawn;
		this.redSpawn = redSpawn;
		this.spectators = spectators;
	}
	
	public List<PlayerTank> getPlayers()
	{
		return players;
	}
	
	public PlayerTank getPlayer(UUID player)
	{
		for (PlayerTank pt : players)
			if (pt.getPlayerId() == player)
				return pt;
		
		return null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getWorldName()
	{
		return worldName;
	}
	
	public Location getPoint1()
	{
		return p1;
	}
	
	public void setPoint1(Location loc)
	{
		p1 = loc;
		if (worldName != null || worldName == "")
			worldName = loc.getWorld().getName();
	}
	
	public Location getPoint2()
	{
		return p2;
	}
	
	public void setPoint2(Location loc)
	{
		p2 = loc;
		if (worldName != null || worldName == "")
			worldName = loc.getWorld().getName();
	}
	
	public Location getGreenSpawn()
	{
		return greenSpawn;
	}
	
	public void setGreenSpawn(Location loc)
	{
		greenSpawn = loc;
		if (worldName != null || worldName == "")
			worldName = loc.getWorld().getName();
	}
	
	public Location getRedSpawn()
	{
		return redSpawn;
	}
	
	public void setRedSpawn(Location loc)
	{
		redSpawn = loc;
		if (worldName != null || worldName == "")
			worldName = loc.getWorld().getName();
	}
	
	public Location getSpectators()
	{
		return spectators;
	}
	
	public void setSpectators(Location loc)
	{
		spectators = loc;
		if (worldName != null || worldName == "")
			worldName = loc.getWorld().getName();
	}
	
	public boolean addPlayer(Player player, Team team)
	{
		File file = new File(plugin.getDataFolder() + File.separator + "inventorystorage", player.getUniqueId().toString() + ".yml");
		try
		{
			file.createNewFile();
		}
		catch (IOException e)
		{
			player.sendMessage(ChatColor.RED + plugin.prefix + " Error: An internal error has prevented you from joining the game.");
			return false;
		}
		
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		Inventory inv = player.getInventory();
		for (int slot = 0; slot < inv.getSize(); slot++)
			yml.set("inventory." + slot, inv.getItem(slot));
		
		for (int slot = 0; slot < player.getInventory().getArmorContents().length; slot++)
			yml.set("armor." + slot, player.getInventory().getArmorContents()[slot]);
		
		List<Map<String, Object>> effects = new ArrayList<Map<String, Object>>();
		for (PotionEffect effect : player.getActivePotionEffects())
		{
			Map<String, Object> pe = new HashMap<String, Object>();
			pe.put("ambient", effect.isAmbient());
			pe.put("type", effect.getType().toString());
			pe.put("duration", effect.getDuration());
			pe.put("amplifier", effect.getAmplifier());
			effects.add(pe);
			player.removePotionEffect(effect.getType());
		}
		
		yml.set("effects", effects);
		
		Location loc = player.getLocation();
		Map<String, Object> pl = new HashMap<String, Object>();
		pl.put("world", loc.getWorld().getName());
		pl.put("x", loc.getX());
		pl.put("y", loc.getY());
		pl.put("z", loc.getZ());
		pl.put("yaw", loc.getYaw());
		pl.put("pitch", loc.getPitch());
		yml.set("location", pl);
		
		try
		{
			yml.save(file);
		}
		catch (IOException e)
		{
			player.sendMessage(ChatColor.RED + plugin.prefix + " Error: An internal error has prevented you from joining the game.");
			for (Map<String, Object> effect : effects)
			{
				PotionEffectType type = PotionEffectType.getByName(effect.get("type").toString());
				boolean ambient = Boolean.valueOf(effect.get("ambient").toString());
				int duration = Integer.valueOf(effect.get("duration").toString());
				int amplifier = Integer.valueOf(effect.get("amplifier").toString());
				player.addPotionEffect(new PotionEffect(type, duration, amplifier, ambient), true);
			}
			
			file.delete();
			return false;
		}
		
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getInventory().setItem(0, MTUtils.createCustomItem(Material.STICK, "Open Hangar", "Tank: None"));
		player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Ready Up", "You are currently not ready."));
		unassigned++;
		return players.add(new PlayerTank(player.getUniqueId(), team));
	}
	
	public boolean removePlayer(Player player)
	{
		PlayerTank pt = getPlayer(player.getUniqueId());
		if (pt != null)
		{
			File file = new File(plugin.getDataFolder() + File.separator + "inventorystorage", player.getUniqueId().toString() + ".yml");
			if (file.exists())
			{
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
				player.getInventory().clear();
				for (int slot = 0; slot < player.getInventory().getSize(); slot++)
					player.getInventory().setItem(slot, yml.getItemStack("inventory." + slot));
				
				ItemStack[] armor = new ItemStack[4];
				for (int slot = 0; slot < armor.length; slot++)
					armor[slot] = yml.getItemStack("armor." + slot);
				
				player.getInventory().setArmorContents(armor);
			}
			
			if (pt.getTeam() == Team.GREEN)
				greenTeam--;
			
			if (pt.getTeam() == Team.RED)
				redTeam--;
			
			return players.remove(pt);
		}
		
		return false;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public boolean isReady()
	{
		if (p1 == null)
			return false;
		
		if (p2 == null)
			return false;
		
		if (greenSpawn == null)
			return false;
		
		if (redSpawn == null)
			return false;
		
		if (spectators == null)
			return false;
		
		if (!enabled)
			return false;
		
		return true;
	}
	
	public void saveToFile()
	{
		File file = new File(plugin.getDataFolder() + File.separator + "battlefields", this.name + ".yml");
		try
		{
			file.createNewFile();
		}
		catch (IOException e)
		{
			plugin.getLogger().warning("Error: Failed to create file: " + file.getName());
			return;
		}
		
		YamlConfiguration field = YamlConfiguration.loadConfiguration(file);
		if (p1 != null)
		{
			if (!field.isSet("world"))
				field.set("world", p1.getWorld().getName());
			
			field.set("p1", p1.getX());
			field.set("p1.y", p1.getY());
			field.set("p1.z", p1.getZ());
		}
		
		if (p2 != null)
		{
			if (!field.isSet("world"))
				field.set("world", p2.getWorld().getName());
			
			field.set("p2", p2.getX());
			field.set("p2.y", p2.getY());
			field.set("p2.z", p2.getZ());
		}
		
		if (greenSpawn != null)
		{
			if (!field.isSet("world"))
				field.set("world", greenSpawn.getWorld().getName());
			
			field.set("greenSpawn", greenSpawn.getX());
			field.set("greenSpawn.y", greenSpawn.getY());
			field.set("greenSpawn.z", greenSpawn.getZ());
		}
		
		if (redSpawn != null)
		{
			if (!field.isSet("world"))
				field.set("world", redSpawn.getWorld().getName());
			
			field.set("redSpawn", redSpawn.getX());
			field.set("redSpawn.y", redSpawn.getY());
			field.set("redSpawn.z", redSpawn.getZ());
		}
		
		if (spectators != null)
		{
			if (!field.isSet("world"))
				field.set("world", spectators.getWorld().getName());
			
			field.set("spectators", spectators.getX());
			field.set("spectators.y", spectators.getY());
			field.set("spectators.z", spectators.getZ());
		}
		
		try
		{
			field.save(file);
		}
		catch (IOException e)
		{
			plugin.getLogger().warning("Error: Could not save " + file.getName());
		}
	}

	public boolean canPlayerExit(UUID player)
	{
		PlayerTank pt = getPlayer(player);
		if (pt != null)
			return pt.getTeam().canExit();
		
		return false;
	}

	public void startMatch()
	{
		for (PlayerTank pt : players)
			if (!pt.isReady())
				return;
		
		Collections.shuffle(players);
		
		for (PlayerTank pt : players)
		{
			if (greenTeam == 0 && redTeam == 0 && unassigned < 2)
				return;
			
			if (greenTeam == redTeam && unassigned == 1)
			{
				pt.setTeam(Team.SPECTATOR);
				unassigned--;
			}
			else if (greenTeam <= redTeam)
			{
				pt.setTeam(Team.GREEN);
				greenTeam++;
				unassigned--;
			}
			else if (greenTeam >= redTeam)
			{
				pt.setTeam(Team.RED);
				redTeam++;
				unassigned--;
			}
		}
		
		inProgress = true;
		for (PlayerTank pt : players)
		{
			Player player = Bukkit.getPlayer(pt.getPlayerId());
			if (pt.getTeam() == Team.GREEN)
				player.teleport(greenSpawn);
			
			if (pt.getTeam() == Team.RED)
				player.teleport(redSpawn);
			
			player.getInventory().setContents(pt.getTank().getWeapons().getContents());
			player.getInventory().setContents(pt.getTank().getArmor());
		}
	}
	
	public void endMatch()
	{
		if (greenTeam != 0 && redTeam != 0)
			return;
		
		inProgress = false;
		
		if (greenTeam == 0)
		{
			for (PlayerTank pt : players)
			{
				Player player = Bukkit.getPlayer(pt.getPlayerId());
				player.sendMessage(ChatColor.RED + plugin.prefix + " Red team wins!");
				removePlayer(player);
			}
			
			return;
		}
		
		if (redTeam == 0)
		{
			for (PlayerTank pt : players)
			{
				Player player = Bukkit.getPlayer(pt.getPlayerId());
				player.sendMessage(ChatColor.GREEN + plugin.prefix + " Green team wins!");
				removePlayer(player);
			}
			
			return;
		}
	}
	
	public boolean inProgress()
	{
		return inProgress;
	}

	public void playerKilled(PlayerTank player)
	{
		player.killed();
		Bukkit.getPlayer(player.getPlayerId()).teleport(spectators);
	}
}
