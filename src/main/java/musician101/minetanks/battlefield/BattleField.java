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
import musician101.minetanks.handler.ReloadHandler;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.scoreboard.MTScoreboard;
import musician101.minetanks.tank.Tanks;
import musician101.minetanks.util.CuboidUtil;
import musician101.minetanks.util.MTUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Battlefield
{
	private MineTanks plugin;
	private String name;
	private Location greenSpawn, redSpawn, spectators;
	private Map<UUID, PlayerTank> players = new HashMap<UUID, PlayerTank>();
	private boolean enabled;
	private int unassigned = 0;
	private MTScoreboard sb;
	private boolean inProgress = false;
	private CuboidUtil cuboid;
	
	public Battlefield(MineTanks plugin, String name, boolean enabled, CuboidUtil cuboid, Location greenSpawn, Location redSpawn, Location spectators)
	{
		this.plugin = plugin;
		this.name = name;
		this.enabled = enabled;
		this.cuboid = cuboid;
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
		
	public CuboidUtil getCuboid()
	{
		return cuboid;
	}
	
	public void setCuboid(CuboidUtil cuboid)
	{
		this.cuboid = cuboid;
	}
	
	public Location getGreenSpawn()
	{
		return greenSpawn;
	}
	
	public void setGreenSpawn(Location loc)
	{
		greenSpawn = loc;
	}
	
	public Location getRedSpawn()
	{
		return redSpawn;
	}
	
	public void setRedSpawn(Location loc)
	{
		redSpawn = loc;
	}
	
	public Location getSpectators()
	{
		return spectators;
	}
	
	public void setSpectators(Location loc)
	{
		spectators = loc;
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
			player.sendMessage(Messages.NEGATIVE_PREFIX + "Error: An internal error has prevented you from joining the game.");
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
			player.sendMessage(Messages.NEGATIVE_PREFIX + "Error: An internal error has prevented you from joining the game.");
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
		
		player.setExp(0F);
		player.setLevel(0);
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		if (team == MTTeam.SPECTATOR)
		{
			player.teleport(spectators);
			player.sendMessage(Messages.POSITIVE_PREFIX + "You are now spectating in " + name + ".");
		}
		else
		{
			player.getInventory().setItem(0, MTUtils.createCustomItem(Material.STICK, "Open Hangar", "Tank: None"));
			player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Ready Up", "You are currently not ready."));
			unassigned++;
		}
		
		players.put(player.getUniqueId(), new PlayerTank(player.getUniqueId(), team));
		return true;
	}
	
	public boolean removePlayer(Player player)
	{
		PlayerTank pt = getPlayer(player.getUniqueId());
		if (pt == null)
			return false;
		
		player.removePotionEffect(PotionEffectType.SLOW);
		player.removePotionEffect(PotionEffectType.SPEED);
		
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
			file.delete();
		}
		
		if (sb.isOnGreen(player) || sb.isOnRed(player))
			sb.playerDeath(player);
		
		players.remove(player.getUniqueId());
		if (unassigned > 0)
			unassigned--;
		
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
		if (cuboid == null)
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
		if (cuboid != null)
			field.set("cuboid", cuboid.serialize());
		
		if (greenSpawn != null)
		{
			field.set("greenSpawn.world", greenSpawn.getWorld().getName());
			field.set("greenSpawn.x", greenSpawn.getX());
			field.set("greenSpawn.y", greenSpawn.getY());
			field.set("greenSpawn.z", greenSpawn.getZ());
		}
		
		if (redSpawn != null)
		{
			field.set("redSpawn.world", redSpawn.getWorld().getName());
			field.set("redSpawn.x", redSpawn.getX());
			field.set("redSpawn.y", redSpawn.getY());
			field.set("redSpawn.z", redSpawn.getZ());
		}
		
		if (spectators != null)
		{
			field.set("spectators.world", spectators.getWorld().getName());
			field.set("spectators.x", spectators.getX());
			field.set("spectators.y", spectators.getY());
			field.set("spectators.z", spectators.getZ());
		}
		
		field.set("enabled", enabled);
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
				Bukkit.getPlayer(uuid).addPotionEffect(pt.getTank().getSpeedEffect());
				unassigned--;
			}
			else if (sb.getGreenTeamSize() >= sb.getRedTeamSize())
			{
				pt.setTeam(MTTeam.ASSIGNED);
				sb.addRedPlayer(Bukkit.getOfflinePlayer(uuid));
				Bukkit.getPlayer(uuid).addPotionEffect(pt.getTank().getSpeedEffect());
				unassigned--;
			}
		}
		
		inProgress = true;
		for (UUID uuid : players)
		{
			final PlayerTank pt = getPlayer(uuid);
			final Tanks tank = pt.getTank();
			final Player player = Bukkit.getPlayer(uuid);
			if (pt.getTeam() != MTTeam.SPECTATOR)
			{
				if (sb.isOnGreen(player))
					player.teleport(greenSpawn);
				
				if (sb.isOnRed(player))
					player.teleport(redSpawn);
				
				player.setScoreboard(sb.getScoreboard());
				sb.setPlayerHealth(uuid, tank.getHealth());
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						player.getInventory().setContents(tank.getWeapons().getContents());
						player.getInventory().setArmorContents(tank.getArmor());
					}
				}.runTaskLater(plugin, 1L);
				new ReloadHandler(plugin, player, tank.getCannonType(), tank.reloadTime(), tank.cycleTime(), pt.getClipSize(), tank.getClipSize()).isReloading();
			}
			else
			{
				pt.setTank(null);
				player.getInventory().clear();
			}
		}
	}
	
	public void endMatch()
	{
		if ((sb.getGreenTeamSize() != 0 && sb.getRedTeamSize() != 0) || !inProgress)
			return;
		
		if (sb.getGreenTeamSize() == 0 || sb.getRedTeamSize() == 0)
		{
			inProgress = false;
			for (UUID uuid : players.keySet())
			{
				Player player = Bukkit.getPlayer(uuid);
				player.teleport(spectators);
				player.getInventory().setHelmet(null);
				player.getInventory().setChestplate(null);
				player.getInventory().setLeggings(null);
				player.getInventory().setBoots(null);
				player.getInventory().clear();
				player.removePotionEffect(PotionEffectType.SLOW);
				player.removePotionEffect(PotionEffectType.SPEED);
				PlayerTank pt = getPlayer(uuid);
				pt.setTeam(MTTeam.SPECTATOR);
				pt.setTank(null);
				sb.playerDeath(player);
				if (sb.getGreenTeamSize() == 0)
					player.sendMessage(Messages.NEGATIVE_PREFIX + "Red team wins!");
				else if (sb.getRedTeamSize() == 0)
					player.sendMessage(Messages.POSITIVE_PREFIX + "Green team wins!");
			}
		}
	}
	
	public boolean inProgress()
	{
		return inProgress;
	}
	
	public void setInProgress(boolean inProgress)
	{
		this.inProgress = inProgress;
	}
	
	public void playerKilled(UUID player)
	{
		getPlayer(player).killed();
		sb.playerDeath(Bukkit.getPlayer(player));
		Bukkit.getPlayer(player).teleport(spectators);
	}
	
	public MTScoreboard getScoreboard()
	{
		return sb;
	}
}
