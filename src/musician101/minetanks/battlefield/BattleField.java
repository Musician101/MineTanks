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
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.battlefield.player.PlayerTank.MTTeam;
import musician101.minetanks.handlers.ReloadHandler;
import musician101.minetanks.scoreboards.MTScoreboard;
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

public class Battlefield
{
	private MineTanks plugin;
	private String name;
	private Location p1, p2, greenSpawn, redSpawn, spectators;
	private Map<UUID, PlayerTank> players = new HashMap<UUID, PlayerTank>();
	private boolean enabled;
	private int unassigned = 0;
	private MTScoreboard sb;
	private boolean inProgress = false;
	String worldName;
	List<ItemStack> awards = new ArrayList<ItemStack>();
	
	public Battlefield(MineTanks plugin, String name, boolean enabled, Location p1, Location p2, Location greenSpawn, Location redSpawn, Location spectators)
	{
		this.plugin = plugin;
		this.name = name;
		this.enabled = enabled;
		this.p1 = p1;
		this.p2 = p2;
		this.greenSpawn = greenSpawn;
		this.redSpawn = redSpawn;
		this.spectators = spectators;
		this.sb = new MTScoreboard();
	}
	
	public Map<UUID, PlayerTank> getPlayers()
	{
		return players;
	}
	
	public PlayerTank getPlayer(UUID player)
	{
		return players.get(player);
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
	
	public boolean addPlayer(Player player, MTTeam team)
	{
		File file = new File(plugin.getDataFolder() + File.separator + "inventorystorage", player.getUniqueId().toString() + ".yml");
		try
		{
			file.createNewFile();
		}
		catch (IOException e)
		{
			player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: An internal error has prevented you from joining the game.");
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
		yml.set("xp", player.getExp());
		
		try
		{
			yml.save(file);
		}
		catch (IOException e)
		{
			player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Error: An internal error has prevented you from joining the game.");
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
		
		player.setExp(0);
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getInventory().setItem(0, MTUtils.createCustomItem(Material.STICK, "Open Hangar", "Tank: None"));
		player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Ready Up", "You are currently not ready."));
		unassigned++;
		players.put(player.getUniqueId(), new PlayerTank(player.getUniqueId(), team));
		return true;
	}
	
	public void removePlayer(Player player, boolean getPrize)
	{
		if (!removePlayer(player) || !getPrize)
			return;
		
		for (ItemStack item : awards)
			player.getInventory().addItem(item);
	}
	
	public boolean removePlayer(Player player)
	{
		PlayerTank pt = getPlayer(player.getUniqueId());
		if (pt == null)
			return false;
		
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
		
		if (sb.isOnGreen(player) || sb.isOnRed(player))
			sb.playerDeath(player);
		
		players.remove(player.getUniqueId());
		return true;
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
			
			field.set("p1.x", p1.getX());
			field.set("p1.y", p1.getY());
			field.set("p1.z", p1.getZ());
		}
		
		if (p2 != null)
		{
			if (!field.isSet("world"))
				field.set("world", p2.getWorld().getName());
			
			field.set("p2.x", p2.getX());
			field.set("p2.y", p2.getY());
			field.set("p2.z", p2.getZ());
		}
		
		if (greenSpawn != null)
		{
			if (!field.isSet("world"))
				field.set("world", greenSpawn.getWorld().getName());
			
			field.set("greenSpawn.x", greenSpawn.getX());
			field.set("greenSpawn.y", greenSpawn.getY());
			field.set("greenSpawn.z", greenSpawn.getZ());
		}
		
		if (redSpawn != null)
		{
			if (!field.isSet("world"))
				field.set("world", redSpawn.getWorld().getName());
			
			field.set("redSpawn.x", redSpawn.getX());
			field.set("redSpawn.y", redSpawn.getY());
			field.set("redSpawn.z", redSpawn.getZ());
		}
		
		if (spectators != null)
		{
			if (!field.isSet("world"))
				field.set("world", spectators.getWorld().getName());
			
			field.set("spectators.x", spectators.getX());
			field.set("spectators.y", spectators.getY());
			field.set("spectators.z", spectators.getZ());
		}
		
		field.set("awards", awards);
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
		List<UUID> players = new ArrayList<UUID>();
		for (UUID player : this.players.keySet())
		{
			if (!getPlayer(player).isReady())
				return;
			
			players.add(player);
		}
		
		Collections.shuffle(players);
		
		for (UUID uuid : players)
		{
			PlayerTank pt = getPlayer(uuid);
			if (sb.getGreenTeamSize() == 0 && sb.getRedTeamSize() == 0 && unassigned < 2)
				return;
			
			if (sb.getGreenTeamSize() == sb.getRedTeamSize() && unassigned == 1)
			{
				pt.setTeam(MTTeam.SPECTATOR);
				unassigned--;
			}
			else if (sb.getGreenTeamSize() <= sb.getRedTeamSize())
			{
				pt.setTeam(MTTeam.ASSIGNED);
				sb.addGreenPlayer(Bukkit.getOfflinePlayer(uuid));
				unassigned--;
			}
			else if (sb.getGreenTeamSize() >= sb.getRedTeamSize())
			{
				pt.setTeam(MTTeam.ASSIGNED);
				sb.addRedPlayer(Bukkit.getOfflinePlayer(uuid));
				unassigned--;
			}
		}
		
		inProgress = true;
		for (UUID uuid : players)
		{
			PlayerTank pt = getPlayer(uuid);
			Player player = Bukkit.getPlayer(uuid);
			if (sb.isOnGreen(player))
				player.teleport(greenSpawn);
			
			if (sb.isOnRed(player))
				player.teleport(redSpawn);
			
			player.setScoreboard(sb.getScoreboard());
			sb.setPlayerHealth(uuid, pt.getTank().getHealth());
			player.getInventory().setContents(pt.getTank().getWeapons().getContents());
			player.getInventory().setContents(pt.getTank().getArmor());
			new ReloadHandler(plugin, player, ((Double) pt.getTank().reloadTime()).intValue()).isReloading();
		}
	}
	
	public void endMatch()
	{
		if (sb.getGreenTeamSize() != 0 && sb.getRedTeamSize() != 0)
			return;
		
		if (sb.getGreenTeamSize() == 0)
		{
			for (UUID uuid : players.keySet())
			{
				Player player = Bukkit.getPlayer(uuid);
				player.sendMessage(ChatColor.RED + plugin.getPrefix() + " Red team wins!");
				removePlayer(player, sb.isOnRed(player));
			}
			
			inProgress = false;
			return;
		}
		
		if (sb.getRedTeamSize() == 0)
		{
			for (UUID uuid : players.keySet())
			{
				Player player = Bukkit.getPlayer(uuid);
				player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Green team wins!");
				removePlayer(player, sb.isOnGreen(player));
			}
			
			inProgress = false;
			return;
		}
	}
	
	public boolean inProgress()
	{
		return inProgress;
	}

	public void playerKilled(UUID player)
	{
		getPlayer(player).killed();
		Bukkit.getPlayer(player).teleport(spectators);
	}
	
	public MTScoreboard getScoreboard()
	{
		return sb;
	}
}
