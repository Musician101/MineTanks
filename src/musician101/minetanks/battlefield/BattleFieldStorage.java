package musician101.minetanks.battlefield;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import musician101.minetanks.MineTanks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class BattleFieldStorage
{
	MineTanks plugin;
	Map<String, Battlefield> fields = new HashMap<String, Battlefield>();
	Battlefield edit;
	
	public BattleFieldStorage(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean createField(String name)
	{
		return createField(name, false, null, null, null, null, null);
	}
	
	public boolean createField(String name, boolean enabled, Location p1, Location p2, Location greenSpawn, Location redSpawn, Location spectators)
	{
		for (String field : fields.keySet())
			if (getField(field).equals(name))
				return false;
		
		Battlefield field = new Battlefield(plugin, name, enabled, p1, p2, greenSpawn, redSpawn, spectators); 
		fields.put(name, field);
		edit = field;
		return true;
	}
	
	public boolean removeField(String field)
	{
		if (!fields.containsKey(field))
			return false;
		
		fields.remove(field);
		new File(plugin.getDataFolder() + File.separator + "battlefields", field + ".yml").delete();
		return true;
	}
	
	public Battlefield getField(String name)
	{
		if (!fields.containsKey(name))
			return null;
		
		return fields.get(name);
	}
	
	public Map<String, Battlefield> getFields()
	{
		return fields;
	}
	
	public void loadFromFiles()
	{
		File files = new File(plugin.getDataFolder() + File.separator + "battlefields");
		for (File file : files.listFiles())
		{
			if (file.getName().endsWith(".yml"))
			{
				YamlConfiguration field = YamlConfiguration.loadConfiguration(file);
				String name = file.getName().replace(".yml", "");
				boolean enabled = field.getBoolean("enabled");
				Location p1 = null;
				Location p2 = null;
				Location greenSpawn = null;
				Location redSpawn = null;
				Location spectators = null;
				
				if (field.isSet("world"))
				{
					if (field.isSet("p1.x"))
						p1 = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("p1.x"), field.getInt("p1.y"), field.getInt("p1.z"));
					
					if (field.isSet("p2.x"))
						p2 = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("p2.x"), field.getInt("p2.y"), field.getInt("p2.z"));
					
					if (field.isSet("greenSpawn.x"))
						greenSpawn = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("greenSpawn.x"), field.getInt("greenSpawn.y"), field.getInt("greenSpawn.z"));
					
					if (field.isSet("redSpawn.x"))
						redSpawn = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("redSpawn.x"), field.getInt("redSpawn.y"), field.getInt("redSpawn.z"));
					
					if (field.isSet("spectators.x"))
						spectators = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("spectators.x"), field.getInt("spectators.y"), field.getInt("spectators.z"));
				}
				
				if (!createField(name, enabled, p1, p2, greenSpawn, redSpawn, spectators))
					plugin.getLogger().warning("Failed to load " + file.getName());
			}
		}
	}
	
	public void saveToFiles()
	{
		for (String name : fields.keySet())
			fields.get(name).saveToFile();
	}
	
	public Battlefield getEdit()
	{
		return edit;
	}
	
	public boolean setEdit(String name)
	{
		for (String field : getFields().keySet())
		{
			if (name.equalsIgnoreCase(field))
			{
				edit = getField(field);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canPlayerExit(UUID player)
	{
		for (String name : fields.keySet())
		{
			Battlefield field = getField(name);
			if (field.getPlayers().containsKey(player))
				return field.getPlayer(player).getTeam().canExit();
		}
		
		return false;
	}
}
