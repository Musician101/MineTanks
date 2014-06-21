package musician101.minetanks.battlefield;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import musician101.minetanks.MineTanks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class BattleFieldStorage
{
	MineTanks plugin;
	List<BattleField> fields = new ArrayList<BattleField>();
	BattleField edit;
	
	public BattleFieldStorage(MineTanks plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean createField(String name)
	{
		return createField(name, false, null, null, null, null, null, null);
	}
	
	public boolean createField(String name, boolean enabled, Location p1, Location p2, Location p3, Location greenSpawn, Location redSpawn, Location spectators)
	{
		for (BattleField field : fields)
			if (field.getName().equals(name))
				return false;
		
		BattleField field = new BattleField(this.plugin, name, enabled, p1, p2, p3, greenSpawn, redSpawn, spectators); 
		fields.add(field);
		edit = field;
		return true;
	}
	
	public boolean removeField(String name)
	{
		for (BattleField field : fields)
		{
			if (field.getName().equals(name))
			{
				fields.remove(field);
				new File(plugin.getDataFolder() + File.separator + "battlefields", name + ".yml").delete();
				return true;
			}
		}
		
		return false;
	}
	
	public BattleField getField(String name)
	{
		for (BattleField field : fields)
		{
			if (field.getName().equals(name))
				return field;
		}
		
		return null;
	}
	
	public List<BattleField> getFields()
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
				Location p3 = null;
				Location greenSpawn = null;
				Location redSpawn = null;
				Location spectators = null;
				
				if (field.isSet("world"))
				{
					if (field.isSet("p1.x"))
						p1 = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("p1.x"), field.getInt("p1.y"), field.getInt("p1.z"));
					
					if (field.isSet("p2.x"))
						p2 = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("p2.x"), field.getInt("p2.y"), field.getInt("p2.z"));
					
					if (field.isSet("p3.x"))
						p3 = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("p3.x"), field.getInt("p3.y"), field.getInt("p3.z"));
					
					if (field.isSet("greenSpawn.x"))
						greenSpawn = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("greenSpawn.x"), field.getInt("greenSpawn.y"), field.getInt("greenSpawn.z"));
					
					if (field.isSet("redSpawn.x"))
						redSpawn = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("redSpawn.x"), field.getInt("redSpawn.y"), field.getInt("redSpawn.z"));
					
					if (field.isSet("spectators.x"))
						spectators = new Location(Bukkit.getWorld(field.getString("world")), field.getInt("spectators.x"), field.getInt("spectators.y"), field.getInt("spectators.z"));
				}
				
				if (!createField(name, enabled, p1, p2, p3, greenSpawn, redSpawn, spectators))
					plugin.getLogger().warning("Failed to load " + file.getName());
			}
		}
	}
	
	public void saveToFiles()
	{
		for (BattleField field : fields)
			field.saveToFile();
	}
	
	public BattleField getEdit()
	{
		return edit;
	}
	
	public boolean setEdit(String name)
	{
		for (BattleField field : fields)
		{
			if (field.getName().equals(name))
			{
				edit = field;
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canPlayerExit(UUID player)
	{
		for (BattleField field : fields)
			if (field.getPlayer(player) != null)
				if (field.getPlayer(player).getPlayerId() == player)
					return field.getPlayer(player).getTeam().canExit();
		
		return false;
	}
}
