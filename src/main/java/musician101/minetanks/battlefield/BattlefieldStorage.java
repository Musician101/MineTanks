package musician101.minetanks.battlefield;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.util.Region;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class BattlefieldStorage
{
	Map<String, Battlefield> fields = new HashMap<String, Battlefield>();
	Battlefield edit;
	
	public boolean createField(String name)
	{
		return createField(name, false, null, null, null, null);
	}
	
	public boolean createField(String name, boolean enabled, Region region, Location greenSpawn, Location redSpawn, Location spectators)
	{
		for (String field : fields.keySet())
			if (field.equalsIgnoreCase(name))
				return false;
		
		Battlefield field = new Battlefield(name, enabled, region, greenSpawn, redSpawn, spectators); 
		fields.put(name, field);
		edit = field;
		return true;
	}
	
	public boolean removeField(String field)
	{
		for (String name : fields.keySet())
		{
			if (name.equalsIgnoreCase(field))
			{
				fields.remove(field);
				return new File(MineTanks.battlefields, field + ".yml").delete();
			}
		}
		
		return false;
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
		for (File file : MineTanks.battlefields.listFiles())
		{
			if (file.getName().endsWith(".yml"))
			{
				YamlConfiguration field = YamlConfiguration.loadConfiguration(file);
				String name = file.getName().replace(".yml", "");
				boolean enabled = field.getBoolean("enabled");
				Region region = null;
				Location greenSpawn = null;
				Location redSpawn = null;
				Location spectators = null;
				
				if (field.isSet("cuboid"))
					region = Region.deserialize(field.getConfigurationSection("cuboid").getValues(false));
				
				if (field.isSet("greenSpawn.x"))
					greenSpawn = new Location(MineTanks.getGame().getWorld(field.getString("greenSpawn.world")), field.getInt("greenSpawn.x"), field.getInt("greenSpawn.y"), field.getInt("greenSpawn.z"));
				
				if (field.isSet("redSpawn.x"))
					redSpawn = new Location(MineTanks.getGame().getWorld(field.getString("redSpawn.world")), field.getInt("redSpawn.x"), field.getInt("redSpawn.y"), field.getInt("redSpawn.z"));
				
				if (field.isSet("spectators.x"))
					spectators = new Location(MineTanks.getGame().getWorld(field.getString("spectators.world")), field.getInt("spectators.x"), field.getInt("spectators.y"), field.getInt("spectators.z"));
				
				if (!createField(name, enabled, world, region, greenSpawn, redSpawn, spectators))
					MineTanks.getLogger().warn("Failed to load " + file.getName());
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
	
	public Battlefield getPlayerField(UUID player)
	{
		for (Battlefield field : fields.values())
			if (field.getPlayer(player) != null)
				return field;
		
		return null;
	}
}
