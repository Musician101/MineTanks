package musician101.minetanks.spigot.battlefield;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.spigot.util.MTUtils;
import musician101.minetanks.spigot.util.Region;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.world.Location;

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
				try
				{
					JSONParser parser = new JSONParser();
					JSONObject field = (JSONObject) parser.parse(new FileReader(file));
					String name = file.getName().replace(".yml", "");
					boolean enabled = Boolean.valueOf(field.get("enabled").toString());
					Region region = null;
					Location greenSpawn = null;
					Location redSpawn = null;
					Location spectators = null;
					
					if (field.containsKey("cuboid"))
						region = Region.deserialize((JSONObject) field.get("cuboid"));
					
					if (field.containsKey("greenSpawn"))
						greenSpawn = MTUtils.deserializeLocation((JSONObject) field.get("greenSpawn"));
					
					if (field.containsKey("redSpawn"))
						redSpawn = MTUtils.deserializeLocation((JSONObject) field.get("redSpawn"));
					
					if (field.containsKey("spectators"))
						spectators = MTUtils.deserializeLocation((JSONObject) field.get("spectators"));
					
					if (!createField(name, enabled, region, greenSpawn, redSpawn, spectators))
						MineTanks.getLogger().warn("Failed to load " + file.getName());
				}
				catch (IOException | ParseException e)
				{
					MineTanks.getLogger().warn("Failed to load " + file.getName());
				}
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
